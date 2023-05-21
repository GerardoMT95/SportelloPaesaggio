package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.FaseProcedimento;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoAssegnamento;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazioneAssegnamentoBaseRepository;
import it.eng.tz.puglia.autpae.search.ConfigurazioneAssegnamentoSearch;
import it.eng.tz.puglia.autpae.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.ValoriAssegnamentoService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ConfigurazioneAssegnamentoServiceImpl implements ConfigurazioneAssegnamentoService {

	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneAssegnamentoServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired private ConfigurazioneAssegnamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ConfigurazioneAssegnamentoDTO> select() 					  			    	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		     	  count (ConfigurazioneAssegnamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ConfigurazioneAssegnamentoDTO  find  (ConfigurazioneAssegnamentoPK pk) 		  throws Exception { return repository.find  (pk); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Integer 	 			  				 	  insert(ConfigurazioneAssegnamentoDTO entity) 	  throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     	  update(ConfigurazioneAssegnamentoDTO entity)	  throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     	  delete(ConfigurazioneAssegnamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ConfigurazioneAssegnamentoDTO> search(ConfigurazioneAssegnamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired private ValoriAssegnamentoService valoriAssegnamentoService;
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	@Autowired private GruppiRuoliService gruppiRuoliService;
	@Autowired private CommonService commonService;
	@Autowired private IProfileManager profileManager;
	@Autowired private IHttpClientService clientSvc;
	
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazione(int idOrganizzazione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		ConfigurazioneAssegnamentoDTO config = new ConfigurazioneAssegnamentoDTO();
		if (props.getCodiceApplicazione().equalsIgnoreCase("PARERI")) {
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.PARERE_COMP_96D				  .name(), TipoProcedimento.PARERE_COMP_96D				   .getTextValue()));
		}
		else if (props.getCodiceApplicazione().equalsIgnoreCase("AUTPAE")) {
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.AUT_PAES_ORD					  .name(), TipoProcedimento.AUT_PAES_ORD				   .getTextValue()));
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.AUT_PAES_SEMPL_DPR_139_2010	  .name(), TipoProcedimento.AUT_PAES_SEMPL_DPR_139_2010	   .getTextValue()));
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.AUT_PAES_SEMPL_DPR_31_2017	  .name(), TipoProcedimento.AUT_PAES_SEMPL_DPR_31_2017	   .getTextValue()));
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004.name(), TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004.getTextValue()));
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017 .name(), TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017 .getTextValue()));
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.TIPO_PROCEDIMENTO, TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010.name(), TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010.getTextValue()));
		}
		List<Integer> idEnti = gruppiRuoliService.entiForGruppo("GRUPPO_"+idOrganizzazione);
		if (idEnti!=null) {
			List<EnteDTO> infoEnti = commonService.selectEntiById(idEnti);
			for (EnteDTO ente : infoEnti) {
			config.getValoriAssegnati().add(new ValoriAssegnamentoDTO(TipoAssegnamento.LOCALIZZAZIONE   , ente.getId().toString()								 , ente			   							   	   .getNome()));
			}
		}
		
		ConfigurazioneAssegnamentoSearch searchCA = new ConfigurazioneAssegnamentoSearch();
		searchCA.setIdOrganizzazione(idOrganizzazione);
		long count = this.count(searchCA);
		
		if (count==1) {
			config.setCriterioAssegnamento(this.search(searchCA).getList().get(0).getCriterioAssegnamento());
			
			ValoriAssegnamentoSearch searchVA = new ValoriAssegnamentoSearch();
			searchVA.setIdOrganizzazione(idOrganizzazione);
			List<ValoriAssegnamentoDTO> valoriAttuali = valoriAssegnamentoService.search(searchVA).getList();
			
			if (valoriAttuali!=null) {
				for (ValoriAssegnamentoDTO valoreAttuale : valoriAttuali) {
					for (ValoriAssegnamentoDTO valoreAssegnato : config.getValoriAssegnati()) {
						if (valoreAssegnato.getIdComuneTipoProcedimento().equals(valoreAttuale.getIdComuneTipoProcedimento())) {
							valoreAssegnato.setUsernameFunzionario(valoreAttuale.getUsernameFunzionario());
							valoreAssegnato.setDenominazioneFunzionario(valoreAttuale.getDenominazioneFunzionario());
						}
					}
				}
			}
		}
		else if (count>1) {
			throw new Exception("Errore. Trovate "+count+" configurazioni per l'assegnamento fascicoli relative all'organizzazione con id="+searchCA.getIdOrganizzazione());
		}
		return config;

	}
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazioneUtenteLoggato() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		return this.getConfigurazioneForOrganizzazione(userUtil.getIntegerId());
	}
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean saveConfigurazioneForOrganizzazione(ConfigurazioneAssegnamentoDTO configurazione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		validaConfigurazioneForUpdate(configurazione);
		configurazione.setIdOrganizzazione(userUtil.getIntegerId());
		configurazione.setFase(FaseProcedimento.REVISIONE.name());
		
		ConfigurazioneAssegnamentoSearch searchCA = new ConfigurazioneAssegnamentoSearch();
		searchCA.setIdOrganizzazione(userUtil.getIntegerId());
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
		valoriAssegnamentoService.delete(searchVA);
		
		if (configurazione.getValoriAssegnati()!=null) {
			for (ValoriAssegnamentoDTO valoreAssegnato : configurazione.getValoriAssegnati()) {
				if (valoreAssegnato.getTipoAssegnamento()==configurazione.getCriterioAssegnamento() &&
					StringUtil.isNotEmpty(valoreAssegnato.getUsernameFunzionario())) {
						valoreAssegnato.setIdOrganizzazione(userUtil.getIntegerId());
						valoreAssegnato.setFase(FaseProcedimento.REVISIONE.name());
						valoriAssegnamentoService.insert(valoreAssegnato);
				}
			}
		}
		
		return true;
	}

	/**
	 * controllo di validità sulla configurazione
	 * @autor Adriano Colaianni
	 * @date 9 set 2021
	 * @param configurazione
	 * @throws CustomOperationToAdviceException 
	 */
	private void validaConfigurazioneForUpdate(ConfigurazioneAssegnamentoDTO configurazione) throws CustomOperationToAdviceException {
		if(configurazione.getCriterioAssegnamento()==null) {
			throw new CustomOperationToAdviceException("Nessun tipo di assegnazione valido impossibile procedere!");
		}
//		if((configurazione.getCriterioAssegnamento().equals(TipoAssegnamento.MANUALE)||
//			configurazione.getCriterioAssegnamento().equals(TipoAssegnamento.DISATTIVATA))
//				&& ListUtil.isNotEmpty(configurazione.getValoriAssegnati())) {
//			throw new CustomOperationToAdviceException("Il tipo di assegnazione selezionato, non prevede la specifica dei funzionari e il relativo assegnamento, impossibile procedere!");
//		}
	}
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<TipologicaDTO> getUtentiForOrganizzazione(String jwt) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		//UtenteGruppo[] infoUtenti=null;
		AssUtenteGruppo[] infoUtenti=null;
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			//infoUtenti = profileManager.getUtentiGruppo(authorization, props.getCodiceApplicazioneProfile(), userUtil.getGruppo_Id(), null, true, "cognomeUtente","ASC").getPayload();
			infoUtenti = profileManager.utentiInGruppi(authorization, props.getCodiceApplicazioneProfile(), 
					new ArrayList<String>(Gruppi.getAllMyGruppi(userUtil.getCodice_GruppoIdRuolo()))).getPayload();
		}catch(Throwable e){
			throw e;
		}finally {
			LogUtil.addAuthorization(oldAuth);
		}
		
		List<TipologicaDTO> utenti = new ArrayList<>();
		if (infoUtenti!=null) {
			for (AssUtenteGruppo infoUtente : infoUtenti) {
				//tolgo i duplicati in caso di utenti associati a piu' gruppi
				if(ListUtil.isNotEmpty(utenti) && 
				   utenti
				   .stream()
				   .filter(utente->utente.getCodice().equals(infoUtente.getUsername()))
				   .findAny()
				   .isPresent()){
					continue; //salto se giuà presente username
				}
				utenti.add(new TipologicaDTO(infoUtente.getUsername(), (infoUtente.getNome()+" "+infoUtente.getCognome())));
			}
		}
		return utenti;
	}
	
	@Override
	public List<ValoriAssegnamentoDTO> configurazioneAutomaticaSearch(ConfigurazioneAssegnamentoDTO configurazione,	String idComuneTipoProcedimento, String usernameFunzionario) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<ValoriAssegnamentoDTO> lista = new ArrayList<>();
		
		if (configurazione!=null && configurazione.getValoriAssegnati()!=null) {
			for (ValoriAssegnamentoDTO valore : configurazione.getValoriAssegnati()) {
				
				boolean coerente1 = false;
				if (StringUtil.isNotEmpty(idComuneTipoProcedimento)) {
					if (valore.getIdComuneTipoProcedimento().equals(idComuneTipoProcedimento)) {
						coerente1 = true;
					}
				}
				else {
					coerente1 = true;
				}

				boolean coerente2 = false;
				if (StringUtil.isNotEmpty(usernameFunzionario)) {
					if (valore.getUsernameFunzionario()!=null && valore.getUsernameFunzionario().equals(usernameFunzionario)) {
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
	
}