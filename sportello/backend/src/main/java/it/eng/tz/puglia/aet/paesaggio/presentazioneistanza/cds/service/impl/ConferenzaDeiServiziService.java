package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.bean.CdsDocumentSchedulerBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler.ConferenzaDeiServiziDocumentiScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.IConferenzaDeiServiziService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ConferenzaApiPartecipantiExtendedDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.EnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConferenzaDeiServiziRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConferenzaDeiServiziRepository.StatoConferenza;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaCdsRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaCdsSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ResponseBase;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.cds.bean.ConferenzaApiCreatoreDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiDocumentazioneDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiPartecipantiDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiPartecipantiPersonaDto;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaCategoriaDocumentoEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaRuoloEntePartecipanteEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaRuoloPersonaPartecipanteEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaTipologiaDocumentazioneEnum;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Implementazione di {@link IConferenzaDeiServiziService}
 * @author Antonio La Gatta
 * @date 30 nov 2021
 */
@Service
public class ConferenzaDeiServiziService extends GenericCrudService<PraticaCdsDTO, PraticaCdsSearch, ConferenzaDeiServiziRepository> implements IConferenzaDeiServiziService{

	
	/**
	 * Logger
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ConferenzaDeiServiziService.class);
	/**
	 * orm service
	 */
	@Autowired
	private ConferenzaDeiServiziRepository dao;
	
	@Autowired
	private PraticaCdsRepository cdsRepo;
	
	@Autowired 
	private AuthClient authClient;
	
	@Autowired
	private UtenteAttributeRepository utenteAttributeRepo;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private IQueueService queueService;
	
	@Value("${spring.application.name}")
	private String codiceApplicazione;

	
	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#getConferenzaApiDto(String)
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = TX_READ)
	public ConferenzaApiDto getConferenzaApiDto(final String id, final String codiceGruppo) throws Exception{
		final StopWatch sw = LogUtil.startLog("getConferenzaApiDto ", id);
		LOGGER.info("Start getConferenzaApiDto {}", id);
		try {
			final boolean comitato = this.dao.comitato(id);
			final String idPratica = this.dao.idPratica(id);
			final ConferenzaApiDto result = new ConferenzaApiDto();
            result.setCodiceTipoConferenza (this.dao.codiceTipoConferenza(id));
            result.setLocalizzazione       (this.dao.localizzazione(id));
            result.setImpresa              (this.dao.impresa(idPratica));
            result.setIstanza              (this.dao.istanza(idPratica, id));
            result.setIncontro             (this.dao.incontro(id));
            result.setDocumentazione       (this.dao.documentazione(idPratica));
            if(comitato == false) {
            	LOGGER.info("Caso Conferenza con Enti");
            	result.setRichiedente          (this.dao.richiedente(idPratica));
            	result.setCreatore(this.dao.creatore(id));
            	result.setCodiceFiscaleCreatore(result.getCreatore().getCodiceFiscale());
            	result.setResponsabile(this.dao.responsabile(id));
            	result.setPartecipanti         (new ArrayList<>());
            	final List<ConferenzaApiPartecipantiExtendedDto> partecipanti = this.dao.partecipanti(idPratica);
            	for(final Iterator<ConferenzaApiPartecipantiExtendedDto> iterator = partecipanti.iterator(); iterator.hasNext();) {
            		final ConferenzaApiPartecipantiExtendedDto partecipante = iterator.next();
            		this.populatePartecipante(partecipante);
            		partecipante.setPecEnte("simone.verna@eng.it");
            		result.getPartecipanti().add(partecipante);
            	}
            	
            	final EnteDto ente = this.dao.autoritaCompetente(idPratica);
            	result.setCodiceFiscaleProcedente(ente.getCodiceFiscale());
            	if(IConferenzaDeiServiziService.UNITA_ORGANIZZATIVA.equals(ente.getTipoEnte())
            	|| IConferenzaDeiServiziService.COMITATO           .equals(ente.getTipoEnte())
            	)
            		result.setCodiceUfficioProcedente(ente.getCodice());
            	
            	this.getPartecipanteAutoritaCompetente(result.getPartecipanti(), id, idPratica, codiceGruppo, false);
            	LOGGER.info("Aggiunta Regione come partecipante");
            	
            }else{
//            	LOGGER.info("Caso Conferenza con Comitato");
//            	result.setFlagAbilitaModificaRichiedente(true);
//            	final ConferenzaApiCreatoreDto creatoreRdp = this.dao.creatore(idPratica);
//            	result.setRichiedente(new ConferenzaApiRichiedenteDto());
//            	result.getRichiedente().setCodiceFiscale(creatoreRdp.getCodiceFiscale());
//            	result.getRichiedente().setCognome(creatoreRdp.getCognome());
//            	result.getRichiedente().setMail(creatoreRdp.getMail());
//            	result.getRichiedente().setNome(creatoreRdp.getNome());
//            	result.getRichiedente().setTelefono(creatoreRdp.getTelefono());
//            	
//            	LOGGER.info("Aggiunto richiedente");
//            	
//            	result.setCodiceFiscaleProcedente(this.dao.codiceFiscaleComitato(idPratica));
//            	result.setCodiceUfficioProcedente(this.dao.codiceUfficioComitato(idPratica));
//            	final ConferenzaApiCreatoreDto creatore = this.dao.creatoreComitato(id);
//            	if(creatore == null) {
//            		result.setCreatore(this.dao.creatoreComitato(idPratica));
//            		result.setCodiceFiscaleCreatore(this.dao.cfRdp(idPratica));
//            	}else {
//            		result.setCreatore(creatore);
//            		result.setCodiceFiscaleCreatore(creatore.getCodiceFiscale());
//            	}
//            	final String codiceGruppoComitato = this.dao.codiceGruppoComitato(idPratica);
//            	LOGGER.info("Gruppo Comitato {}", codiceGruppoComitato);
//            	final UserPMBean segretarioComitato = this.pmService.getUtentiGruppo(codiceGruppoComitato).get(0);
//            	final UserDetail segretarioDetail = this.userService.callUserCreate(segretarioComitato.getUsernameUtente());
//            	result.setResponsabile(new ConferenzaApiResponsabileDto());
//            	result.getResponsabile().setCodiceFiscale(segretarioDetail.getFiscalCode());
//            	result.getResponsabile().setCognome(segretarioDetail.getLastName());
//            	result.getResponsabile().setMail(segretarioDetail.getEmailAddress());
//            	result.getResponsabile().setNome(segretarioDetail.getFirstName());
//            	
//            	if(this.utenteOrmService.existUserAttribute(segretarioComitato.getUsernameUtente())) {
//            		final UtenteAttributeDto attributi = this.utenteOrmService.getAttributiUtente(segretarioComitato.getUsernameUtente());
//            		result.getResponsabile().setMail(attributi.getMail());
//            	}
//            	
//            	LOGGER.info("Aggiunto Responsabile");
//            	result.setPartecipanti(new ArrayList<>());
//            	this.getPartecipanteAutoritaCompetente(result.getPartecipanti(), idPratica, true);
//        		LOGGER.info("Aggiunto Partecipante");
            }
			return result;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @param idPratica
	 * @param comitato
	 * @throws Exception
	 */
	private void getPartecipanteAutoritaCompetente(final List<ConferenzaApiPartecipantiDto> partecipanti
            									  ,final String id
			                                      ,final String idPratica
			                                      ,final String codiceGruppo
			                                      ,final boolean comitato
	) throws Exception {
		final EnteDto ente = this.dao.autoritaCompetente(idPratica);
		final ConferenzaApiPartecipantiDto partecipante = partecipanti.stream()
				                                                .filter(item ->{
				                                                	return item.getCfEnte().equals(ente.getCodiceFiscale())
				                                                		&& ((item.getUfficioEnte() == null && ente.getTipoEnte().equals(IConferenzaDeiServiziService.UNITA_ORGANIZZATIVA) == false && ente.getTipoEnte().equals(IConferenzaDeiServiziService.COMITATO) == false)
				                                                		||  ((ente.getTipoEnte().equals(IConferenzaDeiServiziService.UNITA_ORGANIZZATIVA) || ente.getTipoEnte().equals(IConferenzaDeiServiziService.COMITATO)) && ente.getCodice().equals(item.getUfficioEnte()))
				                                                		   );
				                                                }).findFirst().orElse(new ConferenzaApiPartecipantiDto())
				                                                ;
		this.populatePartecipanteAutoritaCompetente(partecipante, id, idPratica, codiceGruppo, comitato);
		if(StringUtil.isBlank(partecipante.getCfEnte())) {
			partecipante.setRuolo(ConferenzaRuoloEntePartecipanteEnum.AMMINISTRAZIONE_COMPETENTE);
			partecipante.setCfEnte(ente.getCodiceFiscale());
			if(IConferenzaDeiServiziService.UNITA_ORGANIZZATIVA.equals(ente.getTipoEnte())
			|| IConferenzaDeiServiziService.COMITATO           .equals(ente.getTipoEnte())
			)
				partecipante.setUfficioEnte(ente.getCodice());
			partecipante.setNomeEnte(ente.getNome());
			partecipante.setPecEnte(ente.getPec());
			partecipanti.add(partecipante);
		}
	}
	/**
	 * Popolamento partecipanti 
	 * @author Antonio La Gatta
	 * @param partecipante
	 * @throws Exception
	 */
	private void populatePartecipante(final ConferenzaApiPartecipantiExtendedDto partecipante) throws Exception {
		if(partecipante.getCodiceTipo().equals(UNITA_ORGANIZZATIVA)
		|| partecipante.getCodiceTipo().equals(COMITATO           )
		) {
			partecipante.setUfficioEnte(partecipante.getCodice());
		}
		partecipante.setRuolo(ConferenzaRuoloEntePartecipanteEnum.AMMINISTRAZIONE_INTERESSATA);
		ResponseBase<UtenteGruppo[]> utentiResponse = this.authClient.getUtentiGruppo(StringUtil.concateneString(LogUtil.getAuthorization()), this.codiceApplicazione , "GRUPPO?", "username?");
		final List<UtenteGruppo> utenti = utentiResponse != null && utentiResponse.getPayload() != null? Arrays.asList(utentiResponse.getPayload()) : null;
		if(ListUtil.isNotEmpty(utenti)) {
			partecipante.setPersone(new ArrayList<>());
			utenti.forEach(utente ->{
				try {
					final UtenteDTO user = this.utenteRepository.findByUsername(utente.getUsernameUtente());
					final UtenteAttributeDTO attributi = this.utenteAttributeRepo.findByUsername(utente.getUsernameUtente());
					final ConferenzaApiPartecipantiPersonaDto persona = new ConferenzaApiPartecipantiPersonaDto();
					persona.setNome(utente.getNomeUtente());
					persona.setCognome(utente.getCognomeUtente());
					persona.setCodiceFiscale(user.getCf());
					if(attributi != null
					&& (  StringUtil.isNotBlank(attributi.getMail())
					   || StringUtil.isNotBlank(attributi.getPec ())
					   )
					) {
						if(StringUtil.isNotBlank(attributi.getPec()))
							persona.setMail(attributi.getPec());
						else
							persona.setMail(attributi.getMail());
					}else {
						persona.setMail(attributi.getMail());
					}
					persona.setRuolo(ConferenzaRuoloPersonaPartecipanteEnum.SOGGETTO_ACCREDITATO);
					partecipante.getPersone().add(persona);
				}catch(final Exception e) {
					LOGGER.error("Error in recupero partecipante", e);
					throw new RuntimeException(e);
				}
			});
		}
	}
	/**
	 * Popolamento partecipante comitato
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @param partecipante
	 * @param idPratica
	 * @throws Exception
	 */
	private void populatePartecipanteAutoritaCompetente(final ConferenzaApiPartecipantiDto partecipante
													   ,final String id
			                                           ,final String idPratica
			                                           ,final String codiceGruppo
			                                           ,final boolean comitato                               
			) throws Exception {
		partecipante.setPersone(new ArrayList<>());
		List<PlainStringValueLabel> partecipanti = this.cdsRepo.listPartecipanti(id);
		partecipanti.forEach(user ->{
			try {
				final UtenteDTO utente = this.utenteRepository.findByUsername(user.getValue());
				final UtenteAttributeDTO attributes = this.utenteAttributeRepo.findByUsername(user.getValue());
				final ConferenzaApiPartecipantiPersonaDto persona = new ConferenzaApiPartecipantiPersonaDto();
				persona.setNome(utente.getNome());
				persona.setCognome(utente.getCognome());
				persona.setCodiceFiscale(utente.getCf());
				persona.setMail(attributes.getPec() != null ? attributes.getPec() : attributes.getMail());
				final int size = ListUtil.size(partecipante.getPersone()); 
				if(comitato && size < 2)//I primi due sono rdp e dirigente
					persona.setRuolo(ConferenzaRuoloPersonaPartecipanteEnum.RESPONSABILE_PROCEDIMENTO);
				else
					persona.setRuolo(ConferenzaRuoloPersonaPartecipanteEnum.SOGGETTO_ACCREDITATO);
				partecipante.getPersone().add(persona);
			}catch(final Exception e) {
				LOGGER.error("Error in recupero partecipante", e);
				throw new RuntimeException(e);
			}
		});
	}

	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#updateIdCds(java.lang.String, long)
	 */
	@Override
	@Transactional(readOnly=false, rollbackFor = Exception.class, transactionManager = TX_WRITE)
	public void updateIdCds(final String id, final long idCds) {
		final StopWatch sw = LogUtil.startLog("updateIdCds ", id, " ", idCds);
		LOGGER.info("Start updateIdCds {} {}", id, idCds);
		try {
			this.dao.updateIdCds(id, idCds);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#getIdPratica(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = TX_READ)
	public String getIdPratica(final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("getIdPratica ", id);
		LOGGER.info("Start getIdPratica {}", id);
		try {
			return this.dao.idPratica(id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#getCreatoreApiDto(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = TX_READ)
	public ConferenzaApiCreatoreDto getCreatoreApiDto(final String id) throws Exception {
		final StopWatch sw = LogUtil.startLog("getCreatoreApiDto ", id);
		LOGGER.info("Start getCreatoreApiDto {}", id);
		try {
			return this.dao.creatore(id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#idCds(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = TX_READ)
	public List<Long> idCds(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("idCds ", idPratica);
		LOGGER.info("Start idCds {}", idPratica);
		try {
			return this.dao.idCds(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#getPartecipante(java.lang.String, int)
	 */
	@Override
	public ConferenzaApiPartecipantiExtendedDto getPartecipante(final String idPratica, final int idEnte)throws Exception {
		final StopWatch sw = LogUtil.startLog("getPartecipante ", idPratica, " ", idEnte);
		LOGGER.info("Start getPartecipante {} {}", idPratica, idEnte);
		try {
//			final ConferenzaApiPartecipantiExtendedDto partecipante = this.dao.getPartecipante(idPratica, idEnte);
//           	this.populatePartecipante(partecipante);
//           	return partecipante;
			return null;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 21 mar 2022
	 * @see it.eng.tz.aet.Pratica.scheduler.service.IConferenzaDeiServiziService#idCdsNonComitato(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true, transactionManager = TX_READ)
	public List<Long> idCdsNonComitato(final String idPratica) {
		final StopWatch sw = LogUtil.startLog("idCdsNonComitato ", idPratica);
		LOGGER.info("Start idCdsNonComitato {}", idPratica);
		try {
			return this.dao.idCdsNoComitato(idPratica);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	protected ConferenzaDeiServiziRepository getDao() {
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
	
	
	/**
	 * appendi documento a cds 
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param sezione
	 * @param allegato
	 * @throws JsonProcessingException
	 * @throws SQLException
	 */
	@Override
	@Transactional(readOnly=false, transactionManager = TX_WRITE,rollbackFor = Exception.class)
	public void appendiDocumentoACds(UUID idPratica,SezioneAllegati sezione,AllegatiDTO allegato) throws JsonProcessingException, SQLException {
		List<Long> idsCds = this.dao.idCdsByPraticaEStati(idPratica.toString(), List.of(StatoConferenza.Bozza,StatoConferenza.Compilazione,StatoConferenza.Valutazione));
		if(ListUtil.isNotEmpty(idsCds)) {
			for(Long idCds:idsCds) {
				boolean isInviato = this.dao.hasCdsAllegatiInviati(idPratica, allegato.getId(), idCds);
				if(isInviato) {
					logger.warn("Allegato {} della pratica {} già inviato ala cds {}",allegato.getId(),idPratica,idCds);
					continue;
				}
				CdsDocumentSchedulerBean cdsDocBean=new CdsDocumentSchedulerBean();
				cdsDocBean.setIdConferenza(idCds);
				ConferenzaApiDocumentazioneDto docDto=new ConferenzaApiDocumentazioneDto();
				docDto.setCategoria(this.categoriaFromSezioneAllegati(sezione));
				docDto.setCmisId(allegato.getIdCms());
				docDto.setNomeFile(allegato.getNomeFile());
				docDto.setTipo(this.tipologiaFromSezioneAllegati(sezione));
				cdsDocBean.setDocumento(docDto);
				queueService.insertNewQueue(ConferenzaDeiServiziDocumentiScheduler.SPRING_ID, 
						CryptUtil.encrypt(JsonUtil.toJson(cdsDocBean)));
				String username=SecurityUtil.getUsername();
				if(StringUtil.isEmpty(username)) {
					username=UserUtil.USERNAME_BATCH;
				}
				this.dao.insertCdsAllegatiInviati(idPratica, allegato.getId(), idCds,username);
			}
		}
	}
	
	/**
	 * mapping ConferenzaCategoriaDocumentoEnum dalla nostra SezioneAllegati 
	 * @author acolaianni
	 *
	 * @param sezione
	 * @return
	 */
	private ConferenzaCategoriaDocumentoEnum categoriaFromSezioneAllegati(SezioneAllegati sezione) {
		ConferenzaCategoriaDocumentoEnum retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ALTRO;
		switch (sezione) {
		case DOC_AMMINISTRATIVA_D:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ISTANZA;
			break;
		case DOC_AMMINISTRATIVA_E:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ISTANZA;
			break;	
		case DOC_TECNICA:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ISTANZA;
			break;	
		case PARERE_SOPR:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ALTRO;
			break;
		case RELAZIONE_ENTE:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ALTRO;
			break;
		case ULTERIORE_DOCUMENTAZIONE:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ALTRO;
			break;
		case COMUNICAZIONI:
			retCat=ConferenzaCategoriaDocumentoEnum.DOCUMENTAZIONE_AGGIUNTIVA_ALTRO;
			break;	
		default:
			break;	
		}
		return retCat;
	}
	
	private ConferenzaTipologiaDocumentazioneEnum tipologiaFromSezioneAllegati(SezioneAllegati sezione) {
		ConferenzaTipologiaDocumentazioneEnum retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA;
		switch (sezione) {
		case DOC_AMMINISTRATIVA_D:
			retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA;
			break;
		case DOC_AMMINISTRATIVA_E:
			retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA;
			break;
		case DOC_TECNICA:
			retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTAZIONE_AGGIUNTIVA;
			break;
		case PARERE_SOPR:
			retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA;
			break;	
		case RELAZIONE_ENTE:
			retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA;
			break;
		case ULTERIORE_DOCUMENTAZIONE:
			retCat=ConferenzaTipologiaDocumentazioneEnum.DOCUMENTO_PRE_ISTRUTTORIA;
			break;
		default:
			break;	
		}
		return retCat;
	}
}
