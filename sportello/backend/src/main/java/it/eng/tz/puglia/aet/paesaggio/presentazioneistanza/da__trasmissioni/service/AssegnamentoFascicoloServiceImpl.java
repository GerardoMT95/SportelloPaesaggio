package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoOperazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.AssegnamentoFascicoloFullRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.StoricoAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.FasiAssegnazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ResponseBase;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.ResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.VarieUtils;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class AssegnamentoFascicoloServiceImpl implements AssegnamentoFascicoloService {

	private static final Logger log = LoggerFactory.getLogger(AssegnamentoFascicoloServiceImpl.class);
	
	@Autowired private AssegnamentoFascicoloFullRepository repository;
	@Autowired private PraticaRepository praticaRepository;
	@Autowired private ResolveTemplateService templateresolver;
	@Autowired private ITemplateMailService templateService;
	@Autowired private ComunicazioniService comunicazioneService;
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<AssegnamentoFascicoloOldDTO> select() 					  			   throws Exception { return repository.select(); 	  	}
	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		     	count (AssegnamentoFascicoloSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   AssegnamentoFascicoloOldDTO  find  (AssegnamentoFascicoloPK 	   pk) 	   throws Exception { return repository.find  (pk); 	}
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Integer 	 			  				 	insert(AssegnamentoFascicoloOldDTO entity) throws Exception { return repository.insert(entity); }
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     	update(AssegnamentoFascicoloOldDTO entity) throws Exception { return repository.update(entity); }
//	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     	delete(AssegnamentoFascicoloSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<AssegnamentoFascicoloOldDTO> search(AssegnamentoFascicoloSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired private RemoteSchemaService remoteSchemaService;
	@Autowired private StoricoAssegnamentoService storicoAssegnamentoService;
	@Autowired private PraticaService praticaService;
	@Autowired private ConfigurazioneAssegnamentoService configurazioneAssegnamentoService;
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	@Autowired private IHttpClientService clientSvc;
	@Autowired private AuthClient profileManager;
	@Autowired private GruppiRuoliService gruppiRuoliService;
//	@Autowired private CorrispondenzaService corrispondenzaService;
//	@Autowired private TemplateDestinatarioService templateDestinatarioService;
	@Autowired private LocalizzazioneInterventoRepository localizzazioneInterventoRepository;
	@Autowired private IstrPraticaService istrPraticaService;
	
	private final String codiceAssegnazioneTemplate = "ASSEGNAMENTO_FASCICOLO";
	private final String codiceRevoceTemplate = "DISASSEGNAMENTO_FASCICOLO";
		
	
	@Override
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> tabelleAssegnamentoFascicoliSearch(TabelleAssegnamentoFascicoliSearch filter) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		PaginatedList<TabelleAssegnamentoFascicoliOldDTO> listaPaginata = null;
		PraticaSearch praticaSearch = new PraticaSearch(filter);
		listaPaginata = istrPraticaService.searchForAssegnamento(praticaSearch);
//		if (filter.isGiaAssegnato()==true)
//			listaPaginata = repository.getFascicoliAssegnati   (filter);
//		else 
//			listaPaginata = repository.getFascicoliNonAssegnati(filter, gruppiRuoliService.entiForGruppoUtenteLoggato());	
		
		if (listaPaginata!=null && listaPaginata.getList()!=null) {
			for (TabelleAssegnamentoFascicoliOldDTO fascicolo : listaPaginata.getList()) {
				
				if (fascicolo.getComuni()==null)
					fascicolo.setComuni(new ArrayList<String>());				
				
				Set<Long> idComuni = new HashSet<>();
				
				List<LocalizzazioneInterventoDTO> locInt = localizzazioneInterventoRepository.select(fascicolo.getId());
				if (locInt!=null) {
					locInt.forEach(elem-> idComuni.add(elem.getComuneId()));
				}
				
				for (Long elem : idComuni) {
					fascicolo.getComuni().add(remoteSchemaService.findEnteById(elem.intValue()).getNome());
				};
			}
		}
		return listaPaginata;
	}
	
//	@Override
//	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
//	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> tabelleAssegnamentoFascicoliSearch(TabelleAssegnamentoFascicoliSearch filter) throws Exception {
//		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//
//		PaginatedList<TabelleAssegnamentoFascicoliOldDTO> listaPaginata = null;
//		
//		if (filter.isGiaAssegnato()==true)
//			listaPaginata = repository.getFascicoliAssegnati   (filter);
//		else 
//			listaPaginata = repository.getFascicoliNonAssegnati(filter, gruppiRuoliService.entiForGruppoUtenteLoggato());	
//		
//		if (listaPaginata!=null && listaPaginata.getList()!=null) {
//			for (TabelleAssegnamentoFascicoliOldDTO fascicolo : listaPaginata.getList()) {
//				
//				if (fascicolo.getComuni()==null)
//					fascicolo.setComuni(new ArrayList<String>());				
//				
//				Set<Long> idComuni = new HashSet<>();
//				
//				List<LocalizzazioneInterventoDTO> locInt = localizzazioneInterventoRepository.select(fascicolo.getId());
//				if (locInt!=null) {
//					locInt.forEach(elem-> idComuni.add(elem.getComuneId()));
//				}
//				
//				for (Long elem : idComuni) {
//					fascicolo.getComuni().add(remoteSchemaService.findEnteById(elem.intValue()).getNome());
//				};
//			}
//		}
//		return listaPaginata;
//	}
	
	@Override
	@Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AssegnamentoFascicoloOldDTO assegnamentoFascicolo(AssegnamentoFascicoloOldDTO entity, TipoOperazione tipoOperazione) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Date dataOperazione = entity.getDataOperazione()!=null ? entity.getDataOperazione() : new Date();
		String username = entity.getUsernameUtente();
		
		if (entity.getIdOrganizzazione()==0) {
			entity.setIdOrganizzazione(userUtil.getIntegerId());
		}
		
		// cerco il numero di assegnazioni senza usare il parametro che mi torna il FE
		AssegnamentoFascicoloSearch searchOld = new AssegnamentoFascicoloSearch();
		searchOld.setIdFascicolo(entity.getIdFascicolo());
		searchOld.setIdOrganizzazione(entity.getIdOrganizzazione());
		searchOld.setFase(FasiAssegnazione.ISTRUTTORIA.name());
		searchOld.setRup(entity.isRup());
		List<AssegnamentoFascicoloOldDTO> old = this.search(searchOld).getList();
		if (old==null || old.isEmpty())
			entity.setNumAssegnazioni((short) 0);
		else
			entity.setNumAssegnazioni(old.get(0).getNumAssegnazioni());
		
		entity.setFase(FasiAssegnazione.ISTRUTTORIA.name());
		entity.setDataOperazione(dataOperazione);
		if (tipoOperazione==TipoOperazione.ASSEGNAZIONE || tipoOperazione==TipoOperazione.RIASSEGNAZIONE) {
			entity.setNumAssegnazioni(Integer.valueOf(entity.getNumAssegnazioni()+1).shortValue());
		}
		else {	// DISASSEGNAZIONE
			entity.setUsernameUtente(null);
			entity.setDenominazioneUtente(null);
		}
		
		AssegnamentoFascicoloSearch searchAF = new AssegnamentoFascicoloSearch();
		searchAF.setIdFascicolo(entity.getIdFascicolo());
		searchAF.setIdOrganizzazione(entity.getIdOrganizzazione());
		searchAF.setRup(entity.isRup());
		
		if (tipoOperazione==TipoOperazione.RIASSEGNAZIONE) {
			AssegnamentoFascicoloOldDTO assegnOLD = null;
			List<AssegnamentoFascicoloOldDTO> tmp = this.search(searchAF).getList();
			if(tmp != null && tmp.size() > 0)
			{
				assegnOLD = tmp.get(0);
				if (assegnOLD.getUsernameUtente() != null &&
					assegnOLD.getUsernameUtente().equals(entity.getUsernameUtente())) {
					log.info("Riassegnato il fascicolo allo stesso Funzionario/Rup di prima!");
				//	throw new CustomOperationToAdviceException("Impossibile riassegnare il fascicolo allo stesso funzionario di prima!");
				}
				else {
					inviaEmail(entity.getIdFascicolo(), entity.getIdOrganizzazione(), assegnOLD.getUsernameUtente(), TipoOperazione.DISASSEGNAZIONE);	// TODO: da rimettere
				}
			}
		}
		
		if (this.count(searchAF)==0) {
			this.insert(entity);
		} else {
			this.update(entity);
		}
		
		StoricoAssegnamentoOldDTO storico = new StoricoAssegnamentoOldDTO();
		storico.setIdFascicolo(entity.getIdFascicolo());
		storico.setIdOrganizzazione(entity.getIdOrganizzazione());
		storico.setFase(FasiAssegnazione.ISTRUTTORIA.name());
		storico.setDataOperazione(dataOperazione);
		storico.setUsernameUtente(entity.getUsernameUtente());
		storico.setDenominazioneUtente(entity.getDenominazioneUtente());
		storico.setTipoOperazione(tipoOperazione);
		storico.setRup(entity.isRup());
		storicoAssegnamentoService.insert(storico);
		inviaEmail(entity.getIdFascicolo(), entity.getIdOrganizzazione(),  username, tipoOperazione);
		return entity;
	}
	
	private void inviaEmail(UUID idFascicolo, Integer idOrganizzazione,  String usernameUtente, TipoOperazione tipoOperazione) throws Exception 
	{	
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");	
		
		if(StringUtil.isNotBlank(usernameUtente) && idOrganizzazione != null)
		{
			PraticaDTO pratica = praticaRepository.find(idFascicolo);
			String codice = tipoOperazione.equals(TipoOperazione.DISASSEGNAZIONE) ? codiceRevoceTemplate : codiceAssegnazioneTemplate;
			TemplateEmailDestinatariDto template = templateService.info(idOrganizzazione, codice);
			DettaglioCorrispondenzaDTO comunicazione = new DettaglioCorrispondenzaDTO();
			CorrispondenzaDTO corrispondenza = new CorrispondenzaDTO();

			// 2) cerco il destinatario diretto dell'assegnamento\disassegnamento
			String emailDestinatario = null;
			String denominazioneDestinatario = null;
			
			String authorization=clientSvc.getBatchUser().getAuthorization();
			String oldAuth=LogUtil.getAuthorization();
			UtenteGruppo[] infoUtenti=null;
			try {
				LogUtil.addAuthorization(authorization);
				//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
				ResponseBase<UtenteGruppo[]> response = profileManager.getUtentiGruppo(authorization, props.getCodiceApplicazioneProfile(), null, usernameUtente);
				infoUtenti = response.getPayload();
			} catch(Throwable e) {
				throw e;
			} finally {
				LogUtil.addAuthorization(oldAuth);
			}
			if (infoUtenti == null || infoUtenti[0] == null) 
			{
				log.error("Utente {} non trovato sul PM");
				throw new Exception("Utente " + usernameUtente + " non trovato sul PM");
			}
			final String nomeUtente = infoUtenti[0].getCognomeUtente();
			final String cognomeUtente = infoUtenti[0].getNomeUtente();
			Function<String, String> fn = (placeholder) ->
			{
				PlaceHolder p = PlaceHolder.valueOf(placeholder);
				switch(p)
				{
				case NOME_COGNOME_USER_ASSEGNATARIO:
					return nomeUtente + " " + cognomeUtente + "[" + usernameUtente + "]";
				default:
					return placeholder;
				}
			};
			List<DestinatarioDTO> destinatariDefault = new ArrayList<DestinatarioDTO>();
			if(template.getDestinatari() != null)
				destinatariDefault = template.getDestinatari().stream().map(DestinatarioDTO::new).collect(Collectors.toList());
			List<DestinatarioDTO> destinatario = null;
			if(StringUtil.isNotBlank(infoUtenti[0].getEmail()))
			{
				DestinatarioDTO tmp = new DestinatarioDTO();
				tmp.setEmail(emailDestinatario);
				tmp.setNome(denominazioneDestinatario);
				tmp.setPec(false);
				tmp.setTipo(TipoDestinatario.TO);
				destinatario = Collections.singletonList(tmp);

			}
			corrispondenza.setOggetto(templateresolver.risolviTesto(template.getTemplate().getPlaceholders(), template.getTemplate().getOggetto(), pratica, fn));
			corrispondenza.setTesto(templateresolver.risolviTesto(template.getTemplate().getPlaceholders(), template.getTemplate().getTesto(), pratica, fn));
			corrispondenza.setRiservata(true);
			corrispondenza.setComunicazione(false);
			corrispondenza.setCodiceTemplate(codice);
			comunicazione.setCorrispondenza(corrispondenza);
			comunicazione.setDestinatari(VarieUtils.eliminaDuplicati(destinatariDefault, destinatario));
			
			if(ListUtil.isNotEmpty(comunicazione.getDestinatari())) {
				comunicazioneService.createAndSendComunication(comunicazione, idFascicolo, idOrganizzazione.longValue(), false);	
			}else {
				log.info("Nessun destinatario per la mail, non verrà inviata...");
			}
		}
	}	
	
	@Override
	@Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void assegnamentoAutomaticoInTrasmissione(UUID idFascicolo/*, boolean ritrasmissione*/) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<Long> enti = new ArrayList<>();
		List<LocalizzazioneInterventoDTO> localizzazioni = localizzazioneInterventoRepository.select(idFascicolo);
		localizzazioni.forEach(localizz->{
			enti.add(localizz.getComuneId());
		});
		
		Set<Integer> idEnti = new HashSet<Integer>();
		enti.forEach(elem->{
			idEnti.add(Math.toIntExact(elem));
		});
		List<Integer> orgCompetenti = remoteSchemaService.getOrganizzazioniCompetentiOnEnti(idEnti, props.getCodiceApplicazione());
		
		for (int organizzazione : orgCompetenti) {
		/*	if (ritrasmissione==true) {
				AssegnamentoFascicoloSearch searchAF = new AssegnamentoFascicoloSearch();
				searchAF.setIdFascicolo(idFascicolo);
				searchAF.setIdOrganizzazione(organizzazione);
				searchAF.setFase("ISTRUTTORIA");
				if (this.count(searchAF)!=0) {	// se ho già assegnato\disassegnato\riassegnato almeno una volta, rimane tutto com'è
					continue;
				}
			}	*/
			ConfigurazioneAssegnamentoDTO configAssegnamento = configurazioneAssegnamentoService.getConfigurazioneForOrganizzazione(organizzazione);
			if (configAssegnamento!=null && configAssegnamento.getCriterioAssegnamento()!=null && configAssegnamento.getValoriAssegnati()!=null) {
					 if (configAssegnamento.getCriterioAssegnamento()==TipoAssegnamento.LOCALIZZAZIONE   ) {
						Set<Integer> idComuni = new HashSet<>();
						List<LocalizzazioneInterventoDTO> locInt = localizzazioneInterventoRepository.select(idFascicolo);
						locInt.forEach(elem-> idComuni.add((int) elem.getComuneId()));
						Set<String> usernameFunzionari = new HashSet<>();
						Set<String> usernameRups = new HashSet<>();
						for (ValoriAssegnamentoNewDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
							if (StringUtil.isNotEmpty(valoreAssegnato.getUsernameFunzionario()) && 
								valoreAssegnato.getTipoAssegnamento()==TipoAssegnamento.LOCALIZZAZIONE &&
								idComuni.contains(valoreAssegnato.getIdComuneTipoProcedimento())) 
							{
								usernameFunzionari.add(valoreAssegnato.getUsernameFunzionario());
							}
							if (StringUtil.isNotEmpty(valoreAssegnato.getUsernameRup()) && 
								valoreAssegnato.getTipoAssegnamento()==TipoAssegnamento.LOCALIZZAZIONE &&
								idComuni.contains(valoreAssegnato.getIdComuneTipoProcedimento())) 
							{
								usernameRups.add(valoreAssegnato.getUsernameRup());
							}
						}
						if (!usernameFunzionari.isEmpty()) {
							// scelgo il funzionario casualmente tra quelli possibili
							List<String> listaUsernameFunzionari = new ArrayList<>(usernameFunzionari);
						    String usernameFunzionario = listaUsernameFunzionari.get((new Random()).nextInt(listaUsernameFunzionari.size()));
							String denominazioneFunzionario = null;
						    for (ValoriAssegnamentoNewDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
								if (usernameFunzionario.equals(valoreAssegnato.getUsernameFunzionario())) {
									denominazioneFunzionario = valoreAssegnato.getDenominazioneFunzionario();
									break;
								}
							}
							AssegnamentoFascicoloOldDTO assegnamentoDTO = new AssegnamentoFascicoloOldDTO();
							assegnamentoDTO.setIdFascicolo(idFascicolo);
							assegnamentoDTO.setIdOrganizzazione(organizzazione);
							assegnamentoDTO.setUsernameUtente(usernameFunzionario);
							assegnamentoDTO.setDenominazioneUtente(denominazioneFunzionario);
							assegnamentoDTO.setRup(false);
							assegnamentoDTO.setNumAssegnazioni((short) 0);
							assegnamentoFascicolo(assegnamentoDTO, TipoOperazione.ASSEGNAZIONE);
						}
						if (!usernameRups.isEmpty()) {
							// scelgo il rup casualmente tra quelli possibili
							List<String> listaUsernameRup = new ArrayList<>(usernameRups);
						    String usernameRup = listaUsernameRup.get((new Random()).nextInt(listaUsernameRup.size()));
							String denominazioneRup = null;
						    for (ValoriAssegnamentoNewDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
								if (usernameRup.equals(valoreAssegnato.getUsernameRup())) {
									denominazioneRup = valoreAssegnato.getDenominazioneRup();
									break;
								}
							}
							AssegnamentoFascicoloOldDTO assegnamentoDTO = new AssegnamentoFascicoloOldDTO();
							assegnamentoDTO.setIdFascicolo(idFascicolo);
							assegnamentoDTO.setIdOrganizzazione(organizzazione);
							assegnamentoDTO.setUsernameUtente(usernameRup);
							assegnamentoDTO.setDenominazioneUtente(denominazioneRup);
							assegnamentoDTO.setRup(true);
							assegnamentoDTO.setNumAssegnazioni((short) 0);
							this.assegnamentoFascicolo(assegnamentoDTO, TipoOperazione.ASSEGNAZIONE);
						}
				}
				else if (configAssegnamento.getCriterioAssegnamento()==TipoAssegnamento.TIPO_PROCEDIMENTO) {
					PraticaDTO pratica = new PraticaDTO();
					pratica.setId(idFascicolo);
					int tipoProcedimento = praticaService.find(pratica).getTipoProcedimento();
						for (ValoriAssegnamentoNewDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
							if (StringUtil.isNotEmpty(valoreAssegnato.getUsernameFunzionario()) && 
								valoreAssegnato.getTipoAssegnamento()==TipoAssegnamento.TIPO_PROCEDIMENTO &&
								valoreAssegnato.getIdComuneTipoProcedimento()==tipoProcedimento) 
									{
										AssegnamentoFascicoloOldDTO assegnamentoDTO = new AssegnamentoFascicoloOldDTO();
										assegnamentoDTO.setIdFascicolo(idFascicolo);
										assegnamentoDTO.setIdOrganizzazione(organizzazione);
										assegnamentoDTO.setUsernameUtente(valoreAssegnato.getUsernameFunzionario());
										assegnamentoDTO.setDenominazioneUtente(valoreAssegnato.getDenominazioneFunzionario());
										assegnamentoDTO.setRup(false);
										assegnamentoDTO.setNumAssegnazioni((short) 0);
										this.assegnamentoFascicolo(assegnamentoDTO, TipoOperazione.ASSEGNAZIONE);
									}
							if (StringUtil.isNotEmpty(valoreAssegnato.getUsernameRup()) && 
								valoreAssegnato.getTipoAssegnamento()==TipoAssegnamento.TIPO_PROCEDIMENTO &&
								valoreAssegnato.getIdComuneTipoProcedimento()==tipoProcedimento) 
									{
										AssegnamentoFascicoloOldDTO assegnamentoDTO = new AssegnamentoFascicoloOldDTO();
										assegnamentoDTO.setIdFascicolo(idFascicolo);
										assegnamentoDTO.setIdOrganizzazione(organizzazione);
										assegnamentoDTO.setUsernameUtente(valoreAssegnato.getUsernameRup());
										assegnamentoDTO.setDenominazioneUtente(valoreAssegnato.getDenominazioneRup());
										assegnamentoDTO.setRup(true);
										assegnamentoDTO.setNumAssegnazioni((short) 0);
										this.assegnamentoFascicolo(assegnamentoDTO, TipoOperazione.ASSEGNAZIONE);
									}
						}
				}
			}
		}
	}
	
/*	@Override
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public String getPrefissoCodice() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String codiceEnte = commonService.getCodiceCiviliaByIdOrganizzazione(userUtil.getIntegerId());
		if (StringUtil.isEmpty(codiceEnte)) {
			throw new CustomOperationToAdviceException("Errore. L'ente "+userUtil.getGruppo_Id()+" non possiede un codice generatore!");
		}
		String prefix=props.isPareri()?"PP":"AP";
		return prefix+codiceEnte+"-";
	}	*/
	
/*	@Override
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<String> autocompleteCodice(String codice) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String prefisso = this.getPrefissoCodice();
		List<String> lista = fascicoloService.autocompleteCodiceAssegnamento(codice, gruppiRuoliService.entiForGruppoUtenteLoggato());
		
		List<String> listaFinale = new ArrayList<>();
		if (lista!=null) {
			for (String codiceCompleto : lista) {
				if (codiceCompleto.startsWith(prefisso)) {
					listaFinale.add(codiceCompleto.substring(prefisso.length()));
				}
			}
		}
		return listaFinale;
	}	*/
	
	@Override
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<String> autocompleteCodiceFascicoliAssegnati(String codice) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.autocompleteCodiceFascicoliAssegnati(codice);
	}
	
	@Override
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<String> autocompleteCodiceFascicoliNonAssegnati(String codice) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.autocompleteCodiceFascicoliNonAssegnati(codice, gruppiRuoliService.entiForGruppoUtenteLoggato());
	}
	
}