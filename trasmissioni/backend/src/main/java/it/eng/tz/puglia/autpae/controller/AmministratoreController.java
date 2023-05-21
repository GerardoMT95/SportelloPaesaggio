package it.eng.tz.puglia.autpae.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.diogene.bean.DiogeneApplicationConfigBean;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.ConfigurazionePECBean;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PaesaggioEmailBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PannelloAmministratoreDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioniSearchBean;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneAttributiDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.VPaesaggioTipoCaricaDocumentoDTO;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.search.PesaggioEmailSearchBean;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AmministratoreService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePECService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneSportelloAmbienteService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.PaesaggioOrganizzazioneService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IDiogeneClientConfigService;
import it.eng.tz.puglia.service.http.IHttpProtocolloService;
import it.eng.tz.puglia.service.http.bean.ProtocolloConfigurationRequestBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloConfigurationResponseBean;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
@RequestMapping(value = "amministratore", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AmministratoreController extends _RestController {
	
	private static final Logger log = LoggerFactory.getLogger(AmministratoreController.class);
	
	private static final String SAVE_CONFIGURATION_PEC  = "savePecConfiguration";
	private static final String RESET_CONFIGURATION_PEC = "resetPecConfiguration";
	private static final String GET_CONFIGURATION_PEC   = "getPecConfiguration";
	
	private static final String DROPDOWN_ENTI 		= "dropdownEntiOrganizzazioneTerritori";
	private static final String DROPDOWN_ENTI_TYPE 		= "dropdownEntiOrganizzazioneAssociazione";
	private static final String SEARCH_ORGAN  		= "searchOrganizzazioni";
	private static final String FIND_ORGAN	  		= "findOrganizzazione";
	private static final String INSERT_ORGAN  		= "insertOrganizzazione";
	private static final String UPDATE_ORGAN  		= "updateOrganizzazione";
	private static final String ENABLE_ORGAN  		= "attivaOrganizzazione";
	private static final String DISABLE_ORGAN 		= "disattivaOrganizzazione";
	private static final String MOD_EN_ORG	  		= "modificaTermineAbilitazione";
	
	private static final String SEARCH_ORG    = "searchOrganizzazioniByTipi";
	private static final String ENTI_ORGANIZ  = "getAllEntiByOrganizzazione";
	private static final String MAIL_ENTE_ORG = "getMailEnteOrganizzazione";
	private static final String INSERT_EMAIL  = "saveEmailOrganizzazione";
	private static final String UPDATE_EMAIL  = "updateEmailOrganizzazione";
	private static final String DELETE_EMAIL  = "deleteEmailOrganizzazione";
	private static final String CONFIGURATION_DIOGENE   = "diogeneConfiguration";
	
	private static final String TIPI_DOCUMENTO_SPORTELLO   = "tipiDocSportello";
	private static final String OPTIONS_TIPI_DOCUMENTO_SPORTELLO   = "optionsTipiDocSportello";
		
	@Autowired private AmministratoreService amministratoreService;
	@Autowired private FascicoloService 	 fascicoloService;
	@Autowired private ConfigurazionePECService confPecService;
	@Autowired private IHttpProtocolloService protoService;
	@Autowired private IDiogeneClientConfigService diogeneClientService;
	@Autowired private CommonService commonService;
	@Autowired private PaesaggioOrganizzazioneService orgService;
	@Autowired private ApplicationProperties props;
	@Autowired private CommonService commonSvc;
	@Autowired private AllegatoService allegatoService;
	@Autowired private ConfigurazioneSportelloAmbienteService confSportelloSvc;
	
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazione;
	
	@Value("${search.default.limit}")
	private Integer defaultLimit;
	
	
	@GetMapping(value = "/autocompleteCodice")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<String>>> autocompleteCodice(
			@RequestParam(value="codice", required=false) String codice,
			@RequestParam(value="limit", required=false) Integer limit,
			@RequestParam(value="isAnnullabile", required=false) Boolean isAnnullabile,
			@RequestParam(value="isModificabile", required=false) Boolean isModificabile,
			@RequestParam(value="isInModifica", required=false) Boolean isInModifica) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		if(limit == null) limit = defaultLimit;
		if(isAnnullabile!=null || isModificabile!=null || isInModifica!=null) {
			return super.okResponse(fascicoloService.autocompleteCodiceAnnMod(codice, limit, isAnnullabile, isModificabile,isInModifica));
		}else {
			return super.okResponse(fascicoloService.autocompleteCodice(codice, limit));	
		}
		
	}
	
	
	@LoggingAet
	@GetMapping(value = "/infoPannello")
	public ResponseEntity<BaseRestResponse<PannelloAmministratoreDTO>> infoPannello() throws Exception {
		final String jwt=SecurityUtil.getAuthorizationHeader();
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(amministratoreService.infoPannello(jwt));
	}
	
	@LoggingAet
	@GetMapping(value = "/listaGruppi")
	public ResponseEntity<BaseRestResponse<List<TipologicaDTO>>> listaGruppi() throws Exception {
		final String jwt=SecurityUtil.getAuthorizationHeader();
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(amministratoreService.listaGruppi(jwt));
	}
	
	@LoggingAet
	@PostMapping(value = "/savePannello")
	public ResponseEntity<BaseRestResponse<Boolean>> savePannello(@RequestBody PannelloAmministratoreDTO pannello) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(amministratoreService.savePannello(pannello));
	}
	
	@LoggingAet
	@GetMapping(value = "/getFascicoloDaAnnullare")
	public ResponseEntity<BaseRestResponse<FascicoloDTO>> getFascicoloDaAnnullare(@RequestParam(value="codice", required=true) String codice) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		if (StringUtil.isEmpty(codice) || codice.equalsIgnoreCase("null")) {	// TODO: capire perchè qua se "codice è null" arriva come stringa 'null'
			log.error("Attenzione! Inserire il codice parziale del fascicolo.");
			throw new CustomOperationToAdviceException("Inserire il codice parziale del fascicolo!");
		}
		return super.okResponse(amministratoreService.getFascicoloDaAnnullare(codice));
	}
	
	@LoggingAet
	@GetMapping(value = "/getFascicoloDaModificare")
	public ResponseEntity<BaseRestResponse<FascicoloDTO>> getFascicoloDaModificare(@RequestParam(value="codice", required=true) String codice) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		if (StringUtil.isEmpty(codice) || codice.equalsIgnoreCase("null")) {	// TODO: capire perchè qua se "codice è null" arriva come stringa 'null'
			log.error("Attenzione! Inserire il codice parziale del fascicolo.");
			throw new CustomOperationToAdviceException("Inserire il codice parziale del fascicolo!");
		}
		return super.okResponse(amministratoreService.getFascicoloDaModificare(codice));
	}
	
	@LoggingAet
	@GetMapping(value = "/getFascicoliInModifica")
	public ResponseEntity<BaseRestResponse<PaginatedList<FascicoloDTO>>> getFascicoliInModifica(FascicoloSearch filter) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		filter.setStato(StatoFascicolo.ON_MODIFY);
		fascicoloService.prepareForSearch(filter);
		return super.okResponse(fascicoloService.searchFascicolo(filter));
	}
	
	@LoggingAet
	@PostMapping(value = "/revocaModifica")
	public ResponseEntity<BaseRestResponse<Boolean>> revocaModifica(@RequestParam(value="id", required=true) long id) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(amministratoreService.revocaModifica(id));
	}
	
	@LoggingAet
	@PostMapping(value = "/annullaFascicolo")
	public ResponseEntity<BaseRestResponse<Boolean>> annullaFascicolo(@RequestParam(value="id", required=true) long id) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		if (id<=0) {
			log.error("Errore - Id fascicolo");
			throw new Exception("Errore - Id fascicolo");
		}
		return super.okResponse(amministratoreService.annullaFascicolo(id));
	}
	
	@LoggingAet
	@PostMapping(value = "/modificaFascicolo")
	public ResponseEntity<BaseRestResponse<Boolean>> modificaFascicolo(@RequestParam(value="id"    , required=true) long id,
																	   @RequestParam(value="giorni", required=true) int  giorni) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		if (id<=0 || giorni<=0) {
			log.error("Errore nei dati in input!");
			throw new Exception("Errore nei dati in input!");
		}
		return super.okResponse(amministratoreService.modificaFascicolo(id, giorni));
	}
	
	/**
	 * @author acolaianni
	 * @date 1 set 2020
	 * @param key
	 * @return Configurazioni protocollo
	 * @throws Exception
	 */
	@LoggingAet
	@RequestMapping(value = "/getProtocolloConf", method = { RequestMethod.GET, RequestMethod.OPTIONS })
	public ResponseEntity<BaseRestResponse<ProtocolloConfigurationResponseBean>> getProtocolloConf() throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(this.protoService.getConfigurazione(this.codiceApplicazione.toUpperCase()));
	}

	/**
	 * Save Configurazioni protocollo
	 * 
	 * @author acolaianni
	 * @date 1 set 2020
	 * @param inputBean
	 * @return {@link ResponseBean} instance
	 * @throws Exception
	 */
	@LoggingAet
	@RequestMapping(value = "/updateProtocolloConf", method = { RequestMethod.POST, RequestMethod.OPTIONS })
	public ResponseEntity<BaseRestResponse<String>> updateProtocolloConf(
			@RequestBody ProtocolloConfigurationRequestBean inputBean) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		this.protoService.updateConfigurazione(inputBean);
		return super.okResponse("");
	}
	
	@LoggingAet
	@PostMapping(SAVE_CONFIGURATION_PEC)
	public ResponseEntity<BaseRestResponse<Boolean>> saveConfigurationPec(@RequestBody ConfigurazionePECBean bean) throws Exception
	{
		log.info("Chiamata a saveOrUpdate configurazione PEC");
		checkAbilitazioneFor(Gruppi.ADMIN);
		confPecService.saveOrUpdate(bean);
		return okResponse(true);
	}
	
	@LoggingAet
	@PostMapping(RESET_CONFIGURATION_PEC)
	public ResponseEntity<BaseRestResponse<ConfigurazionePECBean>> resetConfigurationPec() throws Exception
	{
		log.info("Chiamata a resetDefault configurazione PEC");
		checkAbilitazioneFor(Gruppi.ADMIN);
		ConfigurazionePECBean conf = confPecService.resetDefault();
		return okResponse(conf);
	}
	
	@LoggingAet
	@GetMapping(GET_CONFIGURATION_PEC)
	public ResponseEntity<BaseRestResponse<ConfigurazionePECBean>> getConfigurationPec() throws Exception
	{
		log.info("Chiamata a getConfiguration configurazione PEC");
		checkAbilitazioneFor(Gruppi.ADMIN);
		ConfigurazionePECBean resp = confPecService.getConfiguration();
		return okResponse(resp);
	}
	
	@LoggingAet
	@GetMapping(DROPDOWN_ENTI)
	public ResponseEntity<BaseRestResponse<List<EnteBean>>> dropdownEnteOrganizzazione(@RequestParam(required=true, value="tipo") String tipo) throws Exception
	{
		log.info("Chiamata per ottenere dropdown enti associabili ad una organizzazione");
		checkAbilitazioneFor(Gruppi.ADMIN);
		TipoPaesaggioOrganizzazione t = TipoPaesaggioOrganizzazione.valueOf(tipo);
		if(t == null)
			throw new Exception("Tipo non valido per la ricerca degli enti associabili");
		List<EnteBean> enti = commonService.findAllEntiBeTipoOrganizzazione(t);
		return okResponse(enti);
	}
	
	@LoggingAet
	@GetMapping(DROPDOWN_ENTI_TYPE)
	public ResponseEntity<BaseRestResponse<List<EnteBean>>> dropdownEnteTipo(@RequestParam(required=false, value="tipo") String tipo) throws Exception
	{
		log.info("Chiamata per ottenere dropdown enti associabili ad una organizzazione");
		checkAbilitazioneFor(Gruppi.ADMIN);
		List<TipoEnte> tipi = Arrays.asList(TipoEnte.ente_parco,//valido solo per ETI
											TipoEnte.soprintendenza, TipoEnte.comune, 
											TipoEnte.associazione_di_comuni, 
											TipoEnte.comune, TipoEnte.provincia, TipoEnte.unione_di_comuni);			
		if(tipo != null)
		{
			TipoPaesaggioOrganizzazione t = TipoPaesaggioOrganizzazione.valueOf(tipo);
			if(t == null)
				throw new Exception("Tipo non valido per la ricerca degli enti associabili");
			switch(t)
			{
			case ADMIN: 
			case CL:
			case EPA:
			case REG:
				throw new Exception("Tipo non ammessi");
			case ED:
				tipi= Arrays.asList(TipoEnte.comune,  
					 TipoEnte.comune,
					 TipoEnte.provincia, 
					 TipoEnte.unione_di_comuni);
				break;
			case ETI:
				tipi = Arrays.asList(
						TipoEnte.ente_parco, TipoEnte.comune, TipoEnte.associazione_di_comuni, 
						 TipoEnte.comune, TipoEnte.provincia, 
						 TipoEnte.unione_di_comuni);
				break;
			case SBAP:
				tipi = Arrays.asList(TipoEnte.soprintendenza);
				break;
			}
		}
		
		
		List<EnteBean> enti = commonService.findAllEntiBeTipo(tipi);
		return okResponse(enti);
	}
	
	@LoggingAet
	@GetMapping(SEARCH_ORGAN)
	public ResponseEntity<BaseRestResponse<PaginatedList<PaesaggioOrganizzazioneBean>>> searchOrganizzazioni(PaesaggioOrganizzazioniSearchBean search) throws Exception
	{
		log.info("Chiamata per ottenere organizzazioni");
		checkAbilitazioneFor(Gruppi.ADMIN);
		search.setTipiCercati(Arrays.asList(TipoPaesaggioOrganizzazione.ED, TipoPaesaggioOrganizzazione.ETI, TipoPaesaggioOrganizzazione.SBAP));
		return okResponse(orgService.searchPaesaggioOrganizzazione(search));
	}
	
	@LoggingAet
	@GetMapping(FIND_ORGAN)
	public ResponseEntity<BaseRestResponse<PaesaggioOrganizzazioneBean>> findOrganizzazione(@RequestParam(required=true, value="idOrganizzazione") Long idOrganizzazione) throws Exception
	{
		log.info("Chiamata per ottenere dettaglio organizzazione");
		checkAbilitazioneFor(Gruppi.ADMIN);
		return okResponse(orgService.findPaesaggioOrganizzazione(idOrganizzazione));
	}

	@LoggingAet
	@PostMapping(INSERT_ORGAN)
	public ResponseEntity<BaseRestResponse<PaesaggioOrganizzazioneBean>> insertOrganizzazione(@RequestBody PaesaggioOrganizzazioneBean bean) throws Exception
	{
		log.info("Chiamata per inserimento nuova organizzazione");
		checkAbilitazioneFor(Gruppi.ADMIN);
		validateOrganizzazione(bean);		
		return okResponse(orgService.insert(bean));
	}
	
	@LoggingAet
	@PutMapping(UPDATE_ORGAN)
	public ResponseEntity<BaseRestResponse<PaesaggioOrganizzazioneBean>> updateOrganizzazione(@RequestBody PaesaggioOrganizzazioneBean bean) throws Exception
	{
		log.info("Chiamata per aggiornamento organizzazione");
		checkAbilitazioneFor(Gruppi.ADMIN);
		validateOrganizzazione(bean);		
		return okResponse(orgService.update(bean));
	}
	
	@LoggingAet
	@PostMapping(ENABLE_ORGAN)
	public ResponseEntity<BaseRestResponse<Boolean>> enableOrganizzazione(@RequestBody PaesaggioOrganizzazioneAttributiDTO bean) throws Exception
	{
		log.info("Chiamata per attivare organizzazione all'applicazione corrente");
		checkAbilitazioneFor(Gruppi.ADMIN);
		validateGestioneOrganizzazioneApp(bean.getPaesaggioOrganizzazioneId());
		orgService.attivaOrganizzazione(bean);
		return okResponse(true);
	}
	
	@LoggingAet
	@GetMapping(DISABLE_ORGAN)
	public ResponseEntity<BaseRestResponse<Boolean>> disableOrganizzazione(@RequestParam(required=true, value="idOrganizzazione") Long idOrganizzazione) throws Exception
	{
		log.info("Chiamata per disattivare organizzazione dall'applicazione corrente");
		checkAbilitazioneFor(Gruppi.ADMIN);
		validateGestioneOrganizzazioneApp(idOrganizzazione.intValue());
		orgService.disattivaOrganizzazione(idOrganizzazione);
		return okResponse(true);
	}
	
	@LoggingAet
	@PutMapping(MOD_EN_ORG)
	public ResponseEntity<BaseRestResponse<Boolean>> aggEnableOrganizzazione(@RequestBody PaesaggioOrganizzazioneAttributiDTO bean) throws Exception
	{
		log.info("Chiamata per modificare data termine abilitazione organizzazione dall'applicazione corrente");
		checkAbilitazioneFor(Gruppi.ADMIN);
		validateGestioneOrganizzazioneApp(bean.getPaesaggioOrganizzazioneId());
		orgService.aggiornaTermineAbilitazione(bean);
		return okResponse(true);
	}
	
	@GetMapping(SEARCH_ORG)
	public ResponseEntity<BaseRestResponse<List<PaesaggioOrganizzazioneDTO>>> searchOrganizzazioniByTipiSpec(@RequestParam(required=true, value="tipi") List<String> tipi) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		log.info("Chiamata per ottenere tutte le organizzazione di tipo specificato");
		if(tipi == null || tipi.isEmpty())
		{
			logger.error("Errore: deve essere presente almeno un tipo per effettuare la ricerca");
			throw new Exception("Errore: deve essere presente almeno un tipo per effettuare la ricerca");
		}
		List<TipoOrganizzazioneSpecifica> types = tipi.stream().map(TipoOrganizzazioneSpecifica::fromValue).filter(o -> o != null).collect(Collectors.toList());
		if(types == null || types.isEmpty())
		{
			logger.error("Errore: deve essere presente almeno un tipo per effettuare la ricerca");
			throw new Exception("Errore: deve essere presente almeno un tipo per effettuare la ricerca");
		}
		PaesaggioOrganizzazioniSearchBean bean = new PaesaggioOrganizzazioniSearchBean();
		bean.setTipiSpecCercati(types);
		List<PaesaggioOrganizzazioneDTO> list = orgService.searchPaesaggioOrganizzazioneNoPag(bean); 
		return okResponse(list);
	}
	
	@GetMapping(ENTI_ORGANIZ)
	public ResponseEntity<BaseRestResponse<List<EnteBean>>> searchEnteByOrganizzazione(@RequestParam(required=true , value="idOrganizzazione") Long idOrganizzazione,
																					   @RequestParam(required=false, value="isSoprintendenza") boolean isSbap) throws Exception
	{
		log.info("Chiamata per ottenere tutti gli enti in cui una specifica organizzazione ha competenza");
		checkAbilitazioneFor(Gruppi.ADMIN);
		List<EnteBean> list = orgService.listEntiOrganizzazione(idOrganizzazione, isSbap);
		return okResponse(list);
	}
	
	@PostMapping(MAIL_ENTE_ORG)
	public ResponseEntity<BaseRestResponse<PaginatedList<PaesaggioEmailBean>>> getEmailEnteOrganizzazione(@RequestBody PesaggioEmailSearchBean search) throws Exception
	{
		log.info("Chiamata per ottenere tutte le email attualmente censite legato ad una specifica organizzazione");
		checkAbilitazioneFor(Gruppi.ADMIN);
		search.setRicercaDestinatari(true);
		PaginatedList<PaesaggioEmailBean> list = orgService.listPaesaggioEmail(search);
		return okResponse(list);
	}
	
	@PostMapping(INSERT_EMAIL)
	public ResponseEntity<BaseRestResponse<PaesaggioEmailBean>> insertEmail(@RequestBody PaesaggioEmailDTO bean) throws Exception
	{
		log.info("Chiamata per inserimento nuova mail in paesaggio-email");
		checkAbilitazioneFor(Gruppi.ADMIN);
		PaesaggioEmailBean response = orgService.savePaesaggioEmail(bean);
		return okResponse(response);
	}
	
	@PutMapping(UPDATE_EMAIL)
	public ResponseEntity<BaseRestResponse<PaesaggioEmailBean>> updateEmail(@RequestBody PaesaggioEmailDTO bean) throws Exception
	{
		log.info("Chiamata per aggiornamento mail in paesaggio-email");
		checkAbilitazioneFor(Gruppi.ADMIN);
		PaesaggioEmailBean response = orgService.updatePaesaggioEmail(bean);
		return okResponse(response);
	}
	
	@DeleteMapping(DELETE_EMAIL)
	public ResponseEntity<BaseRestResponse<Boolean>> updateEmail(@RequestParam(required=true, value="idPaesaggioEmail") Long idPaesaggioEmail) throws Exception
	{
		log.info("Chiamata per cancellazione mail da paesaggio-email");
		checkAbilitazioneFor(Gruppi.ADMIN);
		orgService.deletePaesaggioEmail(idPaesaggioEmail);
		return okResponse(true);
	}
	
	private void validateOrganizzazione(PaesaggioOrganizzazioneBean bean) throws Exception
	{
		EnteDTO reg = commonService.findRegione();
		if(bean.getEnteId().equals(reg.getId()))
		{
			logger.error("Non è possibile censire/aggiornare una nuova organizzazione 'Regione'");
			throw new Exception("Non è possibile censire una nuova organizzazione 'Regione'");
		}
		if(bean.getEnti() != null && bean.getEnti().stream().anyMatch(e -> e.getIdEnte().equals(reg.getId().longValue())))
		{
			logger.error("Non è possibile associare 'Regione' come competenza territoriale");
			throw new Exception("Non è possibile associare 'Regione' come competenza territoriale");
		}
	}
	
	private void validateGestioneOrganizzazioneApp(Integer idOrganizzazione) throws Exception
	{
		PaesaggioOrganizzazioneBean bean = orgService.findPaesaggioOrganizzazione(idOrganizzazione.longValue());
		EnteDTO reg = commonService.findRegione();
		if(reg.getId().equals(bean.getEnteId()))
		{
			logger.error("Non è possibile attivare/disattivare organizzazione 'Regione'");
			throw new Exception("Non è possibile attivare/disattivare organizzazione 'Regione'");
		}
	}

	@GetMapping(value = CONFIGURATION_DIOGENE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<DiogeneApplicationConfigBean>> getConfigurazioneDiogene() throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		DiogeneApplicationConfigBean ret = diogeneClientService.getConfigDiogene(this.codiceApplicazione.toLowerCase());
		return super.okResponse(ret);
	}
	
	@PostMapping(value = CONFIGURATION_DIOGENE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Integer>> upsertConfigurazioneDiogene(@RequestBody DiogeneApplicationConfigBean request) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		request.setCodiceApplicazione(codiceApplicazione.toLowerCase());
		Integer ret = diogeneClientService.upsertConfigDiogene(request);
		return super.okResponse(ret);
	}
	
	
	
	@GetMapping(value ="downloadManuale")
	public void downloadManuale(final HttpServletResponse response) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		StopWatch sw = LogUtil.startLog("downloadManuale admin");
		try {
			try {
				String idAlfresco=this.commonSvc.getConfigurationValue("MANUALE-CMIS-ID-ADMIN", props.getCodiceApplicazione());
				//get from common.sit_puglia_configuration where key='MANUALE-CMIS-ID-ADMIN' and application_name=autpae .. o pareri
				File f = allegatoService.downloadAlfresco(idAlfresco);
				String nome = f.getName();
				copyToResponse(response, f, nome);
			} catch (Exception e) {
				log.error("Errore nel download del file!",e);
				response.setStatus(500);
			}
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * save lista tipi documento da utilizzare per import provvedimento da sportello ambiente
	 * @autor Adriano Colaianni
	 * @date 23 lug 2022
	 * @param uuids
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = TIPI_DOCUMENTO_SPORTELLO)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Integer>> upsertTipiDocSportello(@RequestBody List<UUID> uuids) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		int ret = confSportelloSvc.setSelected(uuids, SecurityUtil.getUsername());
		return super.okResponse(ret);
	}
	
	/**
	 * get valori settati
	 * @autor Adriano Colaianni
	 * @date 23 lug 2022
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = TIPI_DOCUMENTO_SPORTELLO)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<UUID>>> getTipiDocSportello() throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		List<UUID> selected = confSportelloSvc.getSelected();
		return super.okResponse(selected);
	}
	
	/**
	 * get options
	 * @autor Adriano Colaianni
	 * @date 23 lug 2022
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = OPTIONS_TIPI_DOCUMENTO_SPORTELLO)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<VPaesaggioTipoCaricaDocumentoDTO>>> optionsTipiDocSportello() throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		List<VPaesaggioTipoCaricaDocumentoDTO> options = confSportelloSvc.getElencoTipoDocumentoSportello();
		return super.okResponse(options);
	}
	
}