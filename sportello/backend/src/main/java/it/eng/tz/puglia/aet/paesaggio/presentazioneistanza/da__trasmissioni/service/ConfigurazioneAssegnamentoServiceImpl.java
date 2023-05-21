package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.ConfigurazioneAssegnamentoBaseRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ConfigurazioneAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.ValoriAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringUtentiValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.FasiAssegnazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RupDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RupRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RupSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ConfigurazioneAssegnamentoServiceImpl implements ConfigurazioneAssegnamentoService {

	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneAssegnamentoServiceImpl.class);
	
	@Autowired private ConfigurazioneAssegnamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ConfigurazioneAssegnamentoDTO> select() 					  			    	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		     	  count (ConfigurazioneAssegnamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ConfigurazioneAssegnamentoDTO  find  (ConfigurazioneAssegnamentoPK pk) 		  throws Exception { return repository.find  (pk); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Integer 	 			  				 	  insert(ConfigurazioneAssegnamentoDTO entity) 	  throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     	  update(ConfigurazioneAssegnamentoDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     	  delete(ConfigurazioneAssegnamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ConfigurazioneAssegnamentoDTO> search(ConfigurazioneAssegnamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired private ValoriAssegnamentoService valoriAssegnamentoService;
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	@Autowired private RupRepository rupRepository;
	@Autowired private GruppiRuoliService gruppiRuoliService;
	@Autowired private RemoteSchemaService remoteSchemaService;
	@Autowired private AuthClient profileManager;
	@Autowired private IHttpClientService clientSvc;
	
	
	@Override 
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazione(int idOrganizzazione) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		ConfigurazioneAssegnamentoDTO config = new ConfigurazioneAssegnamentoDTO();
		config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, 1, "1"));
		config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, 2, "2"));
		config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, 3, "3"));
		config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, 4, "4"));
		config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, 5, "5"));
		config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, 6, "6"));
		List<Integer> idEnti = gruppiRuoliService.entiForGruppo("GRUPPO_"+idOrganizzazione);
		if (idEnti!=null) {
			List<EnteDTO> infoEnti = remoteSchemaService.selectEntiById(idEnti);
			for (EnteDTO ente : infoEnti) {
			config.getValoriAssegnati().add(new ValoriAssegnamentoNewDTO(TipoAssegnamento.LOCALIZZAZIONE, ente.getId(), ente.getNome()));
			}
		}
		
		ConfigurazioneAssegnamentoSearch searchCA = new ConfigurazioneAssegnamentoSearch();
		searchCA.setIdOrganizzazione(idOrganizzazione);
		long count = this.count(searchCA);
		
		if (count==2) {
			config.setCriterioAssegnamento(this.search(searchCA).getList().get(0).getCriterioAssegnamento());
			
			ValoriAssegnamentoSearch searchVA = new ValoriAssegnamentoSearch();
			searchVA.setIdOrganizzazione(idOrganizzazione);
			searchVA.setFase(FasiAssegnazione.ISTRUTTORIA.name());
			List<ValoriAssegnamentoOldDTO> valoriAttuali = valoriAssegnamentoService.search(searchVA).getList();
			
			if (valoriAttuali!=null) {
				Collections.sort(valoriAttuali, new Comparator<ValoriAssegnamentoOldDTO>() {	// ordino in base al campo "IdComuneTipoProcedimento"
					public int compare(ValoriAssegnamentoOldDTO o1, ValoriAssegnamentoOldDTO o2) {
						return ((Integer) o1.getIdComuneTipoProcedimento()).compareTo((Integer) o2.getIdComuneTipoProcedimento());
					}
				});
				List<ValoriAssegnamentoNewDTO> valoriAttualiNew = valoriAttuali.stream().map(ValoriAssegnamentoNewDTO::new).collect(Collectors.toList());
				if(userUtil.getGruppo().equals(Gruppi.ED_))
				{
					valoriAttualiNew = new ArrayList<ValoriAssegnamentoNewDTO>(valoriAttuali.size()/2);
					for (int i=0; i<valoriAttuali.size(); i++) {
						if(i==0 || i%2==0) {
							valoriAttualiNew.add(new ValoriAssegnamentoNewDTO(valoriAttuali.get(i), valoriAttuali.get(i+1)));
						}
					}
				}
				
				
				for (ValoriAssegnamentoNewDTO valoreAttuale : valoriAttualiNew) {
					for (ValoriAssegnamentoNewDTO valoreAssegnato : config.getValoriAssegnati()) {
						if (valoreAssegnato.getIdComuneTipoProcedimento()==valoreAttuale.getIdComuneTipoProcedimento()) {
							valoreAssegnato.setUsernameFunzionario(valoreAttuale.getUsernameFunzionario());
							valoreAssegnato.setDenominazioneFunzionario(valoreAttuale.getDenominazioneFunzionario());
							valoreAssegnato.setUsernameRup(valoreAttuale.getUsernameRup());
							valoreAssegnato.setDenominazioneRup(valoreAttuale.getDenominazioneRup());
						}
					}
				}
			}
		}
		else if (count==1 || count>2) {
			throw new Exception("Errore. Trovate "+count+" configurazioni per l'assegnamento fascicoli relative all'organizzazione con id="+searchCA.getIdOrganizzazione());
		}
		return config;
	}
	
	@Override @Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazioneUtenteLoggato() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.getConfigurazioneForOrganizzazione(userUtil.getIntegerId());
	}
	
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean saveConfigurazioneForOrganizzazione(ConfigurazioneAssegnamentoDTO configurazione) throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (configurazione.getCriterioAssegnamento()!=TipoAssegnamento.MANUALE && 
		   (configurazione.getValoriAssegnati()==null || configurazione.getValoriAssegnati().isEmpty())) {
			throw new Exception("Errore. Nessun valore assegnato per la configurazione Automatica!");
		}
		configurazione.setIdOrganizzazione(userUtil.getIntegerId());
		configurazione.setFase(FasiAssegnazione.ISTRUTTORIA.name());
		
		ConfigurazioneAssegnamentoSearch searchCA = new ConfigurazioneAssegnamentoSearch();
		searchCA.setIdOrganizzazione(userUtil.getIntegerId());
		searchCA.setRup(configurazione.isRup());
		long count = this.count(searchCA);
		if(count==0) {
			this.insert(configurazione);
		}
		else if (count==1) {
			this.update(configurazione);	
		}
		else {
			throw new Exception("Errore. Trovate "+count+" configurazioni per l'assegnamento fascicoli relative all'organizzazione con id="+searchCA.getIdOrganizzazione());
		}
		
		ValoriAssegnamentoSearch searchVA = new ValoriAssegnamentoSearch();
		searchVA.setIdOrganizzazione(userUtil.getIntegerId());
		searchVA.setRup(configurazione.isRup());
		valoriAssegnamentoService.delete(searchVA);
		
		if (configurazione.getValoriAssegnati()!=null) {
			List<ValoriAssegnamentoOldDTO> valoriAssegnOld = new ArrayList<ValoriAssegnamentoOldDTO>();
			configurazione.getValoriAssegnati().forEach(elem->{
				valoriAssegnOld.add(new ValoriAssegnamentoOldDTO(elem, configurazione.isRup()));
			});
			
			for (ValoriAssegnamentoOldDTO valoreAssegnato : valoriAssegnOld) {
				if (valoreAssegnato.getTipoAssegnamento()==configurazione.getCriterioAssegnamento() &&
					StringUtil.isNotEmpty(valoreAssegnato.getUsernameUtente())) {
						valoreAssegnato.setIdOrganizzazione(userUtil.getIntegerId());
						valoreAssegnato.setFase(FasiAssegnazione.ISTRUTTORIA.name());
						valoriAssegnamentoService.insert(valoreAssegnato);
				}
			}
		}
		return true;
	}
	
	@Override	
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<PlainStringValueLabel> getFunzionariForOrganizzazione() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		AssUtenteGruppo[] infoUtenti=null;
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			//infoUtenti = profileManager.getUtentiGruppoOrdered(authorization, props.getCodiceApplicazione(), userUtil.getGruppo_Id(), null, true, "cognomeUtente","ASC").getPayload();
			//infoUtenti = profileManager.getUtentiGruppo(authorization, props.getCodiceApplicazione(), userUtil.getGruppo_Id()).getPayload();
			infoUtenti = profileManager.utentiInGruppi(authorization, props.getCodiceApplicazione(), 
					new ArrayList<String>(Gruppi.getAllMyGruppi(userUtil.getCodice_GruppoIdRuolo()))).getPayload();
		}catch(Throwable e){
			throw e;
		}finally {
			LogUtil.addAuthorization(oldAuth);
		}
		
		List<PlainStringValueLabel> utenti = new ArrayList<>();

		if (infoUtenti!=null) {
			for (AssUtenteGruppo infoUtente : infoUtenti) {
				//utenti.add(new PlainStringValueLabel(infoUtente.getUsernameUtente(), (infoUtente.getNomeUtente()+" "+infoUtente.getCognomeUtente())));
				//tolgo i duplicati
				if(utenti.stream().filter(el->el.getValue().equals(infoUtente.getUsername())).findAny().isEmpty()) {
					utenti.add(new PlainStringValueLabel(infoUtente.getUsername(), (infoUtente.getNome()+" "+infoUtente.getCognome())));	
				}
			}
		}
		return utenti;
	}
	
	@Transactional(transactionManager="txRead", propagation=Propagation.MANDATORY, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	private List<PlainStringValueLabel> getRupForOrganizzazione() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		RupSearch search = new RupSearch();
		search.setIdOrganizzazione(((Integer)userUtil.getIntegerId()).toString());
		List<RupDTO> listaRup = rupRepository.search(search).getList();
		
		List<PlainStringValueLabel> rup = new ArrayList<>();
		
		if (listaRup!=null && !listaRup.isEmpty()) {
			listaRup.forEach(rup_elem->{
				if (rup_elem.getDataScadenza()==null || rup_elem.getDataScadenza().after(new Date())) {
					rup.add(new PlainStringValueLabel(rup_elem.getUsername(), (rup_elem.getDenominazione())));
				}
			});
		}
		return rup;
	}
	
	@Override
	public List<ValoriAssegnamentoNewDTO> configurazioneAutomaticaSearch(ConfigurazioneAssegnamentoDTO configurazione,	Integer idComuneTipoProcedimento, String usernameUtente) {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<ValoriAssegnamentoNewDTO> lista = new ArrayList<>();
		
		if (configurazione!=null && configurazione.getValoriAssegnati()!=null) {
			for (ValoriAssegnamentoNewDTO valore : configurazione.getValoriAssegnati()) {
				
				boolean coerente1 = false;
				if (idComuneTipoProcedimento!=null) {
					if (valore.getIdComuneTipoProcedimento()==idComuneTipoProcedimento) {
						coerente1 = true;
					}
				}
				else {
					coerente1 = true;
				}

				boolean coerente2 = false;
				if (StringUtil.isNotEmpty(usernameUtente)) {
					if ((valore.getUsernameFunzionario()!=null && valore.getUsernameFunzionario().equals(usernameUtente)) ||
						(valore.getUsernameRup		  ()!=null && valore.getUsernameRup		   ().equals(usernameUtente))  ) {
						coerente2 = true;
					}
				}
				else {
					coerente2 = true;
				}
				
				if (coerente1 && coerente2) {
					lista.add(valore);
				}
			}
		}
		return lista;
	}
	
	@Override 	
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED,	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<PlainStringUtentiValueLabel> getUtentiForOrganizzazione() throws Exception {
		log.info("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		List<PlainStringUtentiValueLabel> utenti = new ArrayList<PlainStringUtentiValueLabel>();
		Gruppi gruppoUtente = userUtil.getGruppo();
		List<PlainStringValueLabel> rup 	   = this.getRupForOrganizzazione();
		if (rup==null || rup.isEmpty() && 
				(gruppoUtente.equals(Gruppi.ED_)|| 
				 gruppoUtente.equals(Gruppi.REG_))) 
			return utenti;					// mando al FE una lista vuota (senza mandare un messaggio di errore), così il FE avvisa, blocca e fa quello che deve fare
		List<PlainStringValueLabel> funzionari = this.getFunzionariForOrganizzazione();
		if (funzionari==null || funzionari.isEmpty()) 
			return utenti;					// mando al FE una lista vuota (senza mandare un messaggio di errore), così il FE avvisa, blocca e fa quello che deve fare
		
		rup.forEach(elem_rup->{
			PlainStringUtentiValueLabel utente = new PlainStringUtentiValueLabel();
			utente.setRup(true);
			utente.setValue(elem_rup.getValue());
			utente.setDescription(elem_rup.getDescription());
			utenti.add(utente);
		});
		funzionari.forEach(funzionario->{
			PlainStringUtentiValueLabel utente = new PlainStringUtentiValueLabel();
			utente.setRup(false);
			utente.setValue(funzionario.getValue());
			utente.setDescription(funzionario.getDescription());
			utenti.add(utente);
		});
		
		return utenti;
	}
	
}