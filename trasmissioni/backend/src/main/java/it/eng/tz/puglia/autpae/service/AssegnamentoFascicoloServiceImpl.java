package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.TabelleAssegnamentoFascicoliDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AssegnamentoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.FaseProcedimento;
import it.eng.tz.puglia.autpae.enumeratori.TipoAssegnamento;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoOperazione;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.AssegnamentoFascicoloFullRepository;
import it.eng.tz.puglia.autpae.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.autpae.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.StoricoAssegnamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.utility.VarieUtils;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.UtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class AssegnamentoFascicoloServiceImpl implements AssegnamentoFascicoloService {

	private static final Logger log = LoggerFactory.getLogger(AssegnamentoFascicoloServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired private AssegnamentoFascicoloFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<AssegnamentoFascicoloDTO> select() 					  			    throws Exception { return repository.select(); 	  	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		     count (AssegnamentoFascicoloSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   AssegnamentoFascicoloDTO  find  (AssegnamentoFascicoloPK pk) 		throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Integer 	 			  				 insert(AssegnamentoFascicoloDTO entity) 	throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     update(AssegnamentoFascicoloDTO entity)	throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		     delete(AssegnamentoFascicoloSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<AssegnamentoFascicoloDTO> search(AssegnamentoFascicoloSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired private LocalizzazioneInterventoService localizzazioneInterventoService;
	@Autowired private CommonService commonService;
	@Autowired private StoricoAssegnamentoService storicoAssegnamentoService;
	@Autowired private FascicoloService fascicoloService;
	@Autowired private ConfigurazioneAssegnamentoService configurazioneAssegnamentoService;
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	@Autowired private IHttpClientService clientSvc;
	@Autowired private CorrispondenzaService corrispondenzaService;
	@Autowired private IProfileManager profileManager;
	@Autowired private TemplateDestinatarioService templateDestinatarioService;
	@Autowired private GruppiRuoliService gruppiRuoliService;
	
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<TabelleAssegnamentoFascicoliDTO> tabelleAssegnamentoFascicoliSearch(TabelleAssegnamentoFascicoliSearch filter) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		PaginatedList<TabelleAssegnamentoFascicoliDTO> listaPaginata = null;
		
		if (filter.isGiaAssegnato()==true)
			listaPaginata = repository.getFascicoliAssegnati   (filter);
		else 
			listaPaginata = repository.getFascicoliNonAssegnati(filter, gruppiRuoliService.entiForGruppoUtenteLoggato());
		
		if (listaPaginata!=null && listaPaginata.getList()!=null) {
			for (TabelleAssegnamentoFascicoliDTO fascicolo : listaPaginata.getList()) {
				
				if (fascicolo.getComuni()==null)
					fascicolo.setComuni(new ArrayList<String>());				
				
				Set<Long> idComuni = new HashSet<>();
				
				List<LocalizzazioneInterventoDTO> locInt = localizzazioneInterventoService.select(fascicolo.getId());
				if (locInt!=null) {
					locInt.forEach(elem-> idComuni.add(elem.getComuneId()));
				}
				
				for (Long elem : idComuni) {
					fascicolo.getComuni().add(commonService.findEnteById(elem.intValue()).getNome());
				};
				
				fascicolo.setStorico(storicoAssegnamentoService.getStoricoAssegnamento(fascicolo.getId()));
			}
		}
		return listaPaginata;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AssegnamentoFascicoloDTO assegnamentoFascicolo(AssegnamentoFascicoloDTO entity, TipoOperazione tipoOperazione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Date dataOperazione = new Date();
		String username = entity.getUsernameFunzionario();
		
		if (entity.getIdOrganizzazione()==0) {
			entity.setIdOrganizzazione(userUtil.getIntegerId());
		}
		entity.setFase(FaseProcedimento.REVISIONE.name());
		entity.setDataOperazione(dataOperazione);
		if (tipoOperazione==TipoOperazione.ASSEGNAZIONE || tipoOperazione==TipoOperazione.RIASSEGNAZIONE) {
			entity.setNumAssegnazioni(Integer.valueOf(entity.getNumAssegnazioni()+1).shortValue());
		}
		else {	// DISASSEGNAZIONE
			entity.setUsernameFunzionario(null);
			entity.setDenominazioneFunzionario(null);
		}
		
		AssegnamentoFascicoloSearch searchAF = new AssegnamentoFascicoloSearch();
		searchAF.setIdFascicolo(entity.getIdFascicolo());
		searchAF.setIdOrganizzazione(entity.getIdOrganizzazione());
		
		if (tipoOperazione==TipoOperazione.RIASSEGNAZIONE) {
			AssegnamentoFascicoloDTO assegnOLD = this.search(searchAF).getList().get(0);
			if (assegnOLD.getUsernameFunzionario().equals(entity.getUsernameFunzionario())) {
				throw new CustomOperationToAdviceException("Impossibile riassegnare il fascicolo allo stesso funzionario di prima!");
			}
			else {
				this.inviaEmail(entity.getIdFascicolo(), assegnOLD.getUsernameFunzionario(), TipoOperazione.DISASSEGNAZIONE);
			}
		}
		
		if (this.count(searchAF)==0) {
			this.insert(entity);
		} else {
			this.update(entity);
		}
		
		StoricoAssegnamentoDTO storico = new StoricoAssegnamentoDTO();
		storico.setIdFascicolo(entity.getIdFascicolo());
		storico.setIdOrganizzazione(entity.getIdOrganizzazione());
		storico.setFase(FaseProcedimento.REVISIONE.name());
		storico.setDataOperazione(dataOperazione);
		storico.setUsernameFunzionario(entity.getUsernameFunzionario());
		storico.setDenominazioneFunzionario(entity.getDenominazioneFunzionario());
		storico.setTipoOperazione(tipoOperazione);
		
		storicoAssegnamentoService.insert(storico);
		
		this.inviaEmail(entity.getIdFascicolo(), username, tipoOperazione);
		
		return entity;
	}
	
	private void inviaEmail(long idFascicolo, String usernameFunzionario, TipoOperazione tipoOperazione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();
		
		corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
		corrispondenza.setBozza(false);
		corrispondenza.setTipoTemplate(tipoOperazione==TipoOperazione.DISASSEGNAZIONE ? TipoTemplate.DISASSEGNAMENTO_FASCICOLO : TipoTemplate.ASSEGNAMENTO_FASCICOLO);
		
		// 1) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(corrispondenza.getTipoTemplate());
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}

		// 2) cerco il destinatario diretto dell'assegnamento\disassegnamento
		String emailDestinatario = null;
		String denominazioneDestinatario = null;
		
		String authorization=clientSvc.getBatchUser().getAuthorization();
		String oldAuth=LogUtil.getAuthorization();
		UtenteGruppo[] infoUtenti=null;
		try {
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
			infoUtenti = profileManager.getUtentiGruppo(authorization, props.getCodiceApplicazioneProfile(), null /*userUtil.getGruppo_Id()*/, usernameFunzionario, true).getPayload();
		} catch(Throwable e) {
			throw e;
		} finally {
			LogUtil.addAuthorization(oldAuth);
		}
		if (infoUtenti!=null) {
			for (UtenteGruppo infoUtente : infoUtenti) {
				emailDestinatario = infoUtente.getEmail();
				denominazioneDestinatario = infoUtente.getNomeUtente()+" "+infoUtente.getCognomeUtente();
			}
		}
		TipologicaDestinatarioDTO destinatario = new TipologicaDestinatarioDTO(emailDestinatario, denominazioneDestinatario, TipoDestinatario.TO);
		
		// 3) unisco le 2 liste eliminando i doppioni delle email
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault, Collections.singletonList(destinatario)));
		
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);
		corrispondenza.getInfoPlaceHolders().setInfoAssegnatario(denominazioneDestinatario);
		
		// invio la corrispondenza
		corrispondenzaService.inviaCorrispondenza(corrispondenza);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void assegnamentoAutomaticoInTrasmissione(long idFascicolo, boolean ritrasmissione) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<Long> enti = localizzazioneInterventoService.selectEffettivo(idFascicolo);
		Set<Integer> idEnti = new HashSet<Integer>();
		enti.forEach(elem->{
			idEnti.add(Math.toIntExact(elem));
		});
		List<Integer> orgCompetenti = commonService.getOrganizzazioniCompetentiOnEnti(idEnti, props.getCodiceApplicazione(), null, null);
		
		for (int organizzazione : orgCompetenti) {
			if (ritrasmissione==true) {
				AssegnamentoFascicoloSearch searchAF = new AssegnamentoFascicoloSearch();
				searchAF.setIdFascicolo(idFascicolo);
				searchAF.setIdOrganizzazione(organizzazione);
				searchAF.setFase(FaseProcedimento.REVISIONE.name());
				if (this.count(searchAF)!=0) {	// se ho già assegnato\disassegnato\riassegnato almeno una volta, rimane tutto com'è
					continue;
				}
			}
			ConfigurazioneAssegnamentoDTO configAssegnamento = configurazioneAssegnamentoService.getConfigurazioneForOrganizzazione(organizzazione);
			if (configAssegnamento!=null && configAssegnamento.getCriterioAssegnamento()!=null && configAssegnamento.getValoriAssegnati()!=null) {
					 if (configAssegnamento.getCriterioAssegnamento()==TipoAssegnamento.LOCALIZZAZIONE   ) {
						Set<String> idComuni = new HashSet<>();
						List<LocalizzazioneInterventoDTO> locInt = localizzazioneInterventoService.select(idFascicolo);
						locInt.forEach(elem-> idComuni.add(Long.valueOf(elem.getComuneId()).toString()));
						Set<String> usernameFunzionari = new HashSet<>();
						for (ValoriAssegnamentoDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
							if (StringUtil.isNotEmpty(valoreAssegnato.getUsernameFunzionario()) && 
								valoreAssegnato.getTipoAssegnamento()==TipoAssegnamento.LOCALIZZAZIONE &&
								idComuni.contains(valoreAssegnato.getIdComuneTipoProcedimento())) 
							{
								usernameFunzionari.add(valoreAssegnato.getUsernameFunzionario());
							}
						}
						if (!usernameFunzionari.isEmpty()) {
							// scelgo il funzionario casualmente tra quelli possibili
							List<String> listaUsernameFunzionari = new ArrayList<>(usernameFunzionari);
						    String usernameFunzionario = listaUsernameFunzionari.get((new Random()).nextInt(listaUsernameFunzionari.size()));
							String denominazioneFunzionario = null;
						    for (ValoriAssegnamentoDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
								if (usernameFunzionario.equals(valoreAssegnato.getUsernameFunzionario())) {
									denominazioneFunzionario = valoreAssegnato.getDenominazioneFunzionario();
									break;
								}
							}
							AssegnamentoFascicoloDTO assegnamentoDTO = new AssegnamentoFascicoloDTO();
							assegnamentoDTO.setIdFascicolo(idFascicolo);
							assegnamentoDTO.setIdOrganizzazione(organizzazione);
							assegnamentoDTO.setUsernameFunzionario(usernameFunzionario);
							assegnamentoDTO.setDenominazioneFunzionario(denominazioneFunzionario);
							assegnamentoDTO.setNumAssegnazioni((short) 0);
							this.assegnamentoFascicolo(assegnamentoDTO, TipoOperazione.ASSEGNAZIONE);
						}
				}
				else if (configAssegnamento.getCriterioAssegnamento()==TipoAssegnamento.TIPO_PROCEDIMENTO) {
					TipoProcedimento tipoProcedimento = fascicoloService.find(idFascicolo).getTipoProcedimento();
						for (ValoriAssegnamentoDTO valoreAssegnato : configAssegnamento.getValoriAssegnati()) {
							if (StringUtil.isNotEmpty(valoreAssegnato.getUsernameFunzionario()) && 
								valoreAssegnato.getTipoAssegnamento()==TipoAssegnamento.TIPO_PROCEDIMENTO &&
								valoreAssegnato.getIdComuneTipoProcedimento().equalsIgnoreCase(tipoProcedimento.name())) {
									AssegnamentoFascicoloDTO assegnamentoDTO = new AssegnamentoFascicoloDTO();
									assegnamentoDTO.setIdFascicolo(idFascicolo);
									assegnamentoDTO.setIdOrganizzazione(organizzazione);
									assegnamentoDTO.setUsernameFunzionario(valoreAssegnato.getUsernameFunzionario());
									assegnamentoDTO.setDenominazioneFunzionario(valoreAssegnato.getDenominazioneFunzionario());
									assegnamentoDTO.setNumAssegnazioni((short) 0);
									this.assegnamentoFascicolo(assegnamentoDTO, TipoOperazione.ASSEGNAZIONE);
							}
						}
				}
			}
		}
	}	
	
/*	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public String getPrefissoCodice() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String codiceEnte = commonService.getCodiceCiviliaByIdOrganizzazione(userUtil.getIntegerId());
		if (StringUtil.isEmpty(codiceEnte)) {
			throw new CustomOperationToAdviceException("Errore. L'ente "+userUtil.getGruppo_Id()+" non possiede un codice generatore!");
		}
		String prefix=props.isPareri()?"PP":"AP";
		return prefix+codiceEnte+"-";
	}	*/
	
/*	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<String> autocompleteCodice(String codice) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
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
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<String> autocompleteCodiceFascicoliAssegnati(String codice) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.autocompleteCodiceFascicoliAssegnati(codice);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, rollbackFor=Exception.class, timeout=60000)
	public List<String> autocompleteCodiceFascicoliNonAssegnati(String codice) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return repository.autocompleteCodiceFascicoliNonAssegnati(codice, gruppiRuoliService.entiForGruppoUtenteLoggato());
	}
	
}