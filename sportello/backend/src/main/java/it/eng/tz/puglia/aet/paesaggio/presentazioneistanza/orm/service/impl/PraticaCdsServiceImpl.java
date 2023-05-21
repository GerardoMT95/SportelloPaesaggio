package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler.ConferenzaDeiServiziScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteRepository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PraticaCdsListSettingsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioniEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaCdsRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaCdsSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaCdsService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.CdsBean;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaAttivitaEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaAzioneEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaModalitaEnum;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomUnauthorizedException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class PraticaCdsServiceImpl extends GenericCrudService<PraticaCdsDTO, PraticaCdsSearch, PraticaCdsRepository> implements IPraticaCdsService {
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PraticaCdsServiceImpl.class);
	
	/**
	 * orm service
	 */
	@Autowired
	private PraticaCdsRepository dao;
	/**
	 * Pratica orm service
	 */
	@Autowired
	private PraticaRepository praticaDao;
	/**
	 * Pratica Ente orm service
	 */
//	@Autowired
//	private IPraticaEnteOrmService PraticaEnteOrmService;
	/**
	 * queue service
	 */
	@Autowired
	private IQueueService queueService;
	
	@Autowired
	private UtenteAttributeRepository utenteAttributeRepo;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private AuthClient authClient;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private IHttpClientService clientService;
	
	@Autowired
	private ConfigurazioniEnteRepository cfgEnteRepo;
	/**
	 * pm service
	 */
//	@Autowired
//	private ICallProfileManagerService callProfileManager;	
	/**
	 * User service
	 */
//	@Autowired
//	private IUserService userService;

	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public PraticaCdsDTO getSettings(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("getSettings ", idPratica, " ", id);
		LOGGER.info("Start getSettings {} {}", idPratica, id);
		try {
			this.checkRead(idPratica);
			return this.dao.getSettings(idPratica, id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public boolean hasCds(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("hasCds ", idPratica);
		LOGGER.info("Start hasCds {}", idPratica);
		try {
			this.checkRead(idPratica);
			return this.dao.hasCds(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}


	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = TX_WRITE, rollbackFor=CustomValidationException.class)
	public void saveSettings(final String idPratica, final PraticaCdsDTO dto) throws Exception, CustomValidationException {
		final StopWatch sw = LogUtil.startLog("saveSettings ", idPratica);
		LOGGER.info("Start saveSettings {}", idPratica);
		try {
			if(!this.canCreateConferenza(idPratica))
					throw new Exception();
			if(StringUtil.isNotBlank(dto.getUsernameResponsabile())) {
				final UtenteDTO user = this.utenteRepository.findByUsername(dto.getUsernameResponsabile());
				if(StringUtil.isBlank(user.getNome()))
					throw new CustomValidationException("L'utente responsabile non possiede un nome registrato");
				dto.setNomeResponsabile(user.getNome());
				if(StringUtil.isBlank(user.getCognome()))
					throw new CustomValidationException("L'utente responsabile non possiede un cognome registrato");
				dto.setCognomeResponsabile(user.getCognome());
				if(StringUtil.isBlank(user.getCf()))
					throw new CustomValidationException("L'utente responsabile non possiede un codice fiscale registrato");
				dto.setCodiceFiscaleResponsabile(user.getCf());
				final UtenteAttributeDTO attributi = this.utenteAttributeRepo.findByUsername(dto.getUsernameResponsabile());
				if(attributi != null) {
					if(StringUtil.isNotBlank(attributi.getPec()))
						dto.setMailResponsabile(attributi.getPec());
					if(StringUtil.isNotBlank(attributi.getMail()))
						dto.setMailResponsabile(attributi.getMail());
					if(StringUtil.isNotBlank(attributi.getPhoneNumber()))
						dto.setTelefonoResponsabile(attributi.getPhoneNumber());
				} else {
					throw new CustomValidationException();
				}
			}
			this.dao.saveSettings(idPratica, dto);
			if(dto.getPartecipanti() != null && dto.getPartecipanti().size() > 0) {
				this.dao.insertPartecipanti(dto.getId(), dto.getPartecipanti());
			}
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
		
	}


	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public void avvia(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("saveSettings ", idPratica, " ", id);
		LOGGER.info("Start saveSettings {} {}", idPratica, id);
		try {
			if(!this.canCreateConferenza(idPratica))
				throw new Exception();
//			if(this.PraticaOrmService.userIsRdp(idPratica) == false){
//				throw new CustomOperationException("Solo RDP può Avviare");
//			}
			final PraticaCdsDTO result = this.dao.getSettings(idPratica, id);
			if(Boolean.valueOf(result.getCompleted()) != null && result.getCompleted()) {
				throw new CustomOperationException("Conferenza già avviata");
			}
			if(StringUtil.isBlank(result.getOrario())) {
				this.throwValidation("validazione.orario.required");
			}
			try {
				new SimpleDateFormat("HH:mm").parse(result.getOrario());
			}catch(final Exception e) {
				LOGGER.error("Error in parse orario", e);
				this.throwValidation("validazione.orario.parse");
			}
			if(StringUtil.isBlank(result.getAttivita())) 
				this.throwValidation("validazione.attivita.required");
			else
				ConferenzaAttivitaEnum.fromCodice(result.getAttivita());
			if(StringUtil.isBlank(result.getAzione())) 
				this.throwValidation("validazione.azione.required");
			else
				ConferenzaAzioneEnum.fromCodice(result.getAzione());
			
			if(StringUtil.isBlank(result.getComunePertinenza())) 
				this.throwValidation("validazione.comune.required");
			if(StringUtil.isBlank(result.getProvinciaPertinenza())) 
				this.throwValidation("validazione.provincia.required");
			if(StringUtil.isBlank(result.getIndirizzoPertinenza())) 
				this.throwValidation("validazione.indirizzo.required");
			if(StringUtil.isBlank(result.getModalitaIncontro())) 
				this.throwValidation("validazione.modalita.required");
			else
				ConferenzaModalitaEnum.fromCodice(result.getModalitaIncontro());
			
			if(StringUtil.isBlank(result.getTipo())) 
				this.throwValidation("validazione.tipo.required");
			
			final ConferenzaModalitaEnum modalita = ConferenzaModalitaEnum.fromCodice(result.getModalitaIncontro());
			
			if(modalita.equals(ConferenzaModalitaEnum.ON_LINE)) {
				if(StringUtil.isBlank(result.getLink())) 
					this.throwValidation("validazione.link.required");
			}else {
				if(StringUtil.isBlank(result.getProvincia())) 
					this.throwValidation("validazione.provincia.incontro.required");
				if(StringUtil.isBlank(result.getComune())) 
					this.throwValidation("validazione.comune.incontro.required");
				if(StringUtil.isBlank(result.getCap())) 
					this.throwValidation("validazione.cap.incontro.required");
				if(StringUtil.isBlank(result.getIndirizzo())) 
					this.throwValidation("validazione.indirizzo.incontro.required");
			}
			
//			if(result.getComitato() != null 
//			&& result.getComitato().booleanValue()
//			&& this.PraticaEnteOrmService.countComitato(idPratica) != 1
//			) {
//				LOGGER.error("Comitato mancante");
//				throw new CustomComitatoMissingException("Comitato mancante");
//			}else if(result.getComitato() == null || result.getComitato().booleanValue() == false) {
//				if(StringUtil.isBlank(result.getUsernameResponsabile()))
//					this.throwValidation("validazione.responsabile.required");
//			}
			 if(result.getComitato() == null || result.getComitato().booleanValue() == false) {
				if(StringUtil.isBlank(result.getUsernameResponsabile()))
					this.throwValidation("validazione.responsabile.required");
			}
			
			this.dao.avvia(idPratica, id);
			final CdsBean bean = new CdsBean();
			bean.setId(id);
			bean.setIdPratica(UUID.fromString(idPratica));
			final Gruppi gruppo = userUtil.getGruppo();
			final String idOrganizzazione = this.praticaDao.findEnteDelegatoById(idPratica);
			String codiceBase = gruppo.name() + idOrganizzazione;
			bean.setCodiceBase(codiceBase);
			this.queueService.insertNewQueue(ConferenzaDeiServiziScheduler.SPRING_ID, 0, bean);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
		
	}


	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PlainStringValueLabel> autocompleteProvince(final String query) {
		final StopWatch sw = LogUtil.startLog("autocompleteProvince");
		LOGGER.info("Start autocompleteProvince");
		try {
			return this.dao.autocompleteProvince(query);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PlainStringValueLabel> autocompleteComuni(final String siglaProvincia, final String query) {
		final StopWatch sw = LogUtil.startLog("autocompleteComuni");
		LOGGER.info("Start autocompleteComuni");
		try {
			return this.dao.autocompleteComuni(siglaProvincia, query);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PlainStringValueLabel> listaTipo(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("listaTipo");
		LOGGER.info("Start listaTipo");
		try {
			this.checkRead(idPratica);
			return this.dao.listaTipo(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}


	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PlainStringValueLabel> listaAttivita(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("listaAttivita");
		LOGGER.info("Start listaAttivita");
		try {
			this.checkRead(idPratica);
			return this.dao.listaAttivita(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}


	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PlainStringValueLabel> listaAzione(final String attivita, final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("listaAzione ", attivita);
		LOGGER.info("Start listaAzione {}", attivita);
		try {
			this.checkRead(idPratica);
			return this.dao.listaAzione(attivita, idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}


	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PlainStringValueLabel> listaModalita() {
		final StopWatch sw = LogUtil.startLog("listaModalita");
		LOGGER.info("Start listaModalita");
		try {
			return this.dao.listaModalita();
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	private void checkRead(final String idPratica) throws CustomUnauthorizedException{
//		this.PraticaOrmService.userCanShowPratica(idPratica);
	}

	private void checkReadComitato(final String idPratica) throws CustomUnauthorizedException{
//		this.PraticaOrmService.userCanShowPratica(idPratica);
//		if(this.PraticaOrmService.userIsEnte(idPratica)
//		&& this.PraticaOrmService.userIsEnteComitato(idPratica) == false
//		)
//			throw new CustomUnauthorizedException("Solo ente comitato può visualizzare conferenza comitato");
	}
	
	private String throwValidation(final String code) throws CustomValidationException{
		final Object[] args = null;
		final Locale locale = Locale.getDefault();
		throw new CustomValidationException(this.dao.getMessage().getMessage(code, args, locale));
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<CdsDto> infoCds(final String idPratica) throws CustomUnauthorizedException {
		final StopWatch sw = LogUtil.startLog("infoCds ", idPratica);
		LOGGER.info("Start infoCds {}", idPratica);
		try {
			this.checkRead(idPratica);
			return this.dao.infoCds(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public CdsDto infoCds(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("infoCds ", idPratica , " ", id);
		LOGGER.info("Start infoCds {} {}", idPratica, id);
		try {
			this.checkRead(idPratica);
			return this.dao.infoCds(idPratica, id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public PraticaCdsDTO getSettingsComitato(final String idPratica) throws Exception {
//		final StopWatch sw = LogUtil.startLog("getSettingsComitato ", idPratica);
//		LOGGER.info("Start getSettingsComitato {} {}", idPratica);
//		try {
//			this.checkReadComitato(idPratica);
//			final PraticaCdsDTO result = this.dao.getSettingsComitato(idPratica);
//			if(result != null)
//				return result;
//			final List<PlainStringValueLabel> tipi = this.dao.listaModalita();
//			final String modalita = tipi.stream().filter(tipo ->{return tipo.getLabel().toLowerCase().contains("fisica");}).findFirst().get().getValue();
//			final String id = this.dao.insertSettings(idPratica, modalita, true);
//			return this.dao.getSettings(idPratica, id);
//		}finally {
//			LOGGER.info(LogUtil.stopLog(sw));
//		}
		return null;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public String newSettings(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("newSettings ", idPratica);
		LOGGER.info("Start newSettings {}", idPratica);
		try {
			if(!this.canCreateConferenza(idPratica))
				throw new Exception();
			final List<PraticaCdsListSettingsDto> lista = this.dao.listSettings(idPratica);
			final Optional<PraticaCdsListSettingsDto> optional = lista.stream().filter(item ->{return item.getCompleted() == null||item.getCompleted() == false;}).findFirst();
			if(optional.isPresent()) {
				throw new CustomOperationException("Esiste Cds in bozza");
			}
			final List<PlainStringValueLabel> tipi = this.dao.listaModalita();
			final String modalita = tipi.stream().filter(tipo ->{return tipo.getDescription().toLowerCase().contains("fisica");}).findFirst().get().getValue();
			return this.dao.insertSettings(idPratica, modalita, false);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<PraticaCdsListSettingsDto> listSettings(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("listSettings ", idPratica);
		LOGGER.info("Start listSettings {}", idPratica);
		try {
			this.checkRead(idPratica);
			return this.dao.listSettings(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public void deleteSettings(final String idPratica, final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("deleteSettings ", idPratica, " ", id);
		LOGGER.info("Start deleteSettings {} {}", idPratica, id);
		try {
			if(!this.canCreateConferenza(idPratica))
				throw new Exception();
			this.dao.deleteSettings(idPratica, id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public List<PlainStringValueLabel> listaComitato(final String idPratica) throws Exception {
//		final StopWatch sw = LogUtil.startLog("listaComitato");
//		LOGGER.info("Start listaComitato");
//		try {
//			this.checkRead(idPratica);
//			final List<PlainStringValueLabel> result = new ArrayList<>();
//			this.callProfileManager.getUtentiGruppo(AetConstants.KEY_GRUPPO_COMITATO)
//			.forEach(pmUser ->{
//				final PlainStringValueLabel item = new PlainStringValueLabel();
//				item.setValue(pmUser.getUsernameUtente());
//				item.setLabel(StringUtil.concateneString(pmUser.getNomeUtente(), " ", pmUser.getCognomeUtente()));
//				result.add(item);
//			});
//			Collections.sort(result);
//			return result;
//		}finally {
//			LOGGER.info(LogUtil.stopLog(sw));
//		}
		return null;
	}

	@Override
	public List<PlainStringValueLabel> listaResponsabili(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("listaResponsabili");
		LOGGER.info("Start listaResponsabili");
		try {
			this.checkRead(idPratica);
			final Gruppi gruppo = userUtil.getGruppo();
			final String jwt = clientService.getBatchUser().getAuthorization();
			final String idOrganizzazione = this.praticaDao.findEnteDelegatoById(idPratica);
			String codiceBase = gruppo.name() + idOrganizzazione;
			List<AssUtenteGruppo> users = Arrays.asList(authClient.utentiInGruppi(jwt, codiceApplicazione, Arrays.asList(codiceBase+"_D")).getPayload());
			final List<PlainStringValueLabel> response = new ArrayList<PlainStringValueLabel>();
			if(users != null && users.size() > 0) {
				for(AssUtenteGruppo user : users) {
					final PlainStringValueLabel utente = new PlainStringValueLabel();
					utente.setDescription(user.getNome() + " " + user.getCognome());
					utente.setValue(user.getUsername());
					response.add(utente);
				}
			}
			return response;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@Override
	public List<PlainStringValueLabel> listaFunzionari(final String idPratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("listaFunzionari");
		LOGGER.info("Start listaFunzionari");
		try {
			this.checkRead(idPratica);
			final Gruppi gruppo = userUtil.getGruppo();
			final String jwt = clientService.getBatchUser().getAuthorization();
			final String idOrganizzazione = this.praticaDao.findEnteDelegatoById(idPratica);
			String codiceBase = gruppo.name() + idOrganizzazione;
			List<AssUtenteGruppo> users = Arrays.asList(authClient.utentiInGruppi(jwt, codiceApplicazione, Arrays.asList(codiceBase+"_F")).getPayload());
			final List<PlainStringValueLabel> response = new ArrayList<PlainStringValueLabel>();
			if(users != null && users.size() > 0) {
				for(AssUtenteGruppo user : users) {
					final PlainStringValueLabel utente = new PlainStringValueLabel();
					utente.setDescription(user.getNome() + " " + user.getCognome());
					utente.setValue(user.getUsername());
					response.add(utente);
				}
			}
			return response;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public Boolean canCreateConferenza(final String idPratica) throws Exception {
		final String idOrganizzazione = this.praticaDao.findEnteDelegatoById(idPratica);
		return this.cfgEnteRepo.canCreateConferenza(Integer.valueOf(idOrganizzazione));
	}



	@Override
	protected PraticaCdsRepository getDao() {
		// TODO
		return null;
	}

	@Override
	protected void validateInsertDTO(PraticaCdsDTO entity) throws CustomValidationException {
		// TODO
		
	}

	@Override
	protected void validateUpdateDTO(PraticaCdsDTO entity) throws CustomValidationException {
		// TODO
		
	}

}
