package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.SportelloConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PrivacyDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipiEQualificazioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PrivacyRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioriContestiPaesaggisticiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IRuoloPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.RuoloReferenteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.TipoDocumentoIdentitaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.TipoRuoloDittaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.IpaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DominioService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.SchedaTecnicaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CustomPlainValueLabelComparator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.error.exception.CustomUnauthorizedException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.log.LogUtil;

@Controller
@RequestMapping("dominio")
public class DominioController extends RoleAwareController {

	private final String QUALIFICAZIONI = "/qualificazioni";
	private final String ULTERIORI_CONT_PAES = "/ulterioriContestiPaesag";
	private final String COMUNI = "/comuniOrganizzazione";
	private final String ENTI_ETI = "/entiDelegati";
	private final String POPULATE = "/populate";
	private final String SEZIONI_CATASTALI = "/sezioniCatastali";
	private final String RUOLO_PRATICA = "/ruolo_pratica";
	private final String SPORTELLO_CONFIG ="/sportello_config";
	private final String AUTOCOMPLETE_ENTE = "/autocompleteEnte";
	
	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DominioController.class);
	/**
	 * service
	 */
	@Autowired private RemoteSchemaService remoteSvc;
	@Autowired private DominioService dominioSvc;	
	@Autowired private SchedaTecnicaService schedaTecnicaService;
	@Autowired private TipoDocumentoIdentitaService tipoDocIdentSvc;
	@Autowired private TipoRuoloDittaService tipoRuoloDittaSvc;
	@Autowired private RuoloReferenteService ruoloReferenteSvc;
	@Autowired private DisclaimerRepository disclaimerDao;
	@Autowired private PrivacyRepository privacyDao;
	@Autowired private CommonRepository commonRepo;
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	@Autowired private AuthClient profileManager;
	@Autowired private IHttpClientService client;	
	@Autowired private IRuoloPraticaService ruoloPraticaSvc;
	
	
	
	@Logging
	@GetMapping(value=SEZIONI_CATASTALI, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<SelectOptionDto>>> getSezioniCatastali() throws Exception
	{
		List<SelectOptionDto> result = remoteSvc.getSezioniCatastali();
		return okResponse(result);
	}
	
	@Logging
	@GetMapping(value=COMUNI, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getComuni() throws Exception
	{
		List<PlainNumberValueLabel> result = remoteSvc.getEntiCOMUNIdiCompetenzaForOrganizzazioneDetail(userUtil.getIntegerId());
		return okResponse(result);
	}
	
	@Logging
	@GetMapping(value=ENTI_ETI, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getEntiEti() throws Exception
	{
		List<Integer> enti = remoteSvc.getEntiCOMUNIdiCompetenzaForOrganizzazione(userUtil.getIntegerId());
		List<PaesaggioOrganizzazioneDTO> paesaggio = null;
		List<PlainNumberValueLabel> result = null;
		if(enti != null && !enti.isEmpty())
		{
			final Long id = userUtil.getLongId();
			paesaggio = remoteSvc.getOrganizzazioniCompetentiOnEntiDetails(enti, props.getCodiceApplicazione());
			result = paesaggio.parallelStream()
							  .filter(p -> !p.getId().equals(id) && !p.getEnteId().equals(0L))
							  .map(PlainNumberValueLabel::new)
							  .collect(Collectors.toList());
		}
		return okResponse(result);
	}
	
	@Logging
	@GetMapping(value=POPULATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Map<String, Object>>> populateSearch() throws Exception
	{
		final Map<String, Object> container = new HashMap<>();
		List<PlainNumberValueLabel> result = null;
		if(!userUtil.getGruppo().equals(Gruppi.ADMIN))
		{
			//cerco comuni
			result = remoteSvc.getEntiCOMUNIdiCompetenzaForOrganizzazioneDetail(userUtil.getIntegerId());
			result.sort(new CustomPlainValueLabelComparator<PlainNumberValueLabel>());
			container.put("comuni", result);
			//cerco enti delegati se l'utente loggato è SBAP
			if(userUtil.getGruppo().equals(Gruppi.SBAP_))
			{
				List<Integer> enti = remoteSvc.getEntiCOMUNIdiCompetenzaForOrganizzazione(userUtil.getIntegerId());
				List<PaesaggioOrganizzazioneDTO> paesaggio = null;
				if(enti != null && !enti.isEmpty())
				{
					final Long id = userUtil.getLongId();
					paesaggio = remoteSvc.getOrganizzazioniCompetentiOnEntiDetails(enti, props.getCodiceApplicazione());
					result = paesaggio.parallelStream()
									  .filter(p -> !p.getId().equals(id) && !p.getEnteId().equals(0L))
									  .map(PlainNumberValueLabel::new)
									  .collect(Collectors.toList());
					result.sort(new CustomPlainValueLabelComparator<PlainNumberValueLabel>());
				}
				container.put("enti", result);
			}
		}
		else
		{
			List<PaesaggioOrganizzazioneDTO> tmp1 = remoteSvc.getAllEntiDelegati();
			if(tmp1 != null)
				result = tmp1.parallelStream().map(PlainNumberValueLabel::new).collect(Collectors.toList());
			container.put("enti", result);
			List<EnteDTO> tmp2 = remoteSvc.getAllComuniPuglia();
			if(tmp2 != null)
				result = tmp2.parallelStream().map(PlainNumberValueLabel::new).collect(Collectors.toList());
			container.put("comuni", result);
		}
		//populate funzionari
		List<PlainStringValueLabel> pmresult = null;
//		List<UtenteGruppo> users = null;
		List<AssUtenteGruppo> users = null;
		String jwt = client.getBatchUser().getAuthorization();
		if(!userUtil.getGruppo().equals(Gruppi.ADMIN) &&
		   !userUtil.getGruppo().equals(Gruppi.REG_) &&
		   !userUtil.getRuolo().equals(Ruoli.FUNZIONARIO))
		{
			//users = Arrays.asList(profileManager.getUtentiGruppo(jwt, props.getCodiceApplicazione(), userUtil.getCodice_GruppoIdRuolo()).getPayload());
			users = Arrays.asList(profileManager.utentiInGruppi(jwt, props.getCodiceApplicazione(), Arrays.asList(userUtil.getCodice_GruppoIdRuolo())).getPayload());			 
			if(users != null)
			{
				pmresult = users.stream().map(PlainStringValueLabel::new).collect(Collectors.toList());
				pmresult.sort(new CustomPlainValueLabelComparator<PlainStringValueLabel>());
			}
			container.put("funzionari", pmresult);
		}
		//populate richiedenti
//		users = Arrays.asList(profileManager.getUtentiGruppo(jwt, props.getCodiceApplicazione(), Gruppi.RICHIEDENTI.name()).getPayload());
		users = Arrays.asList(profileManager.utentiInGruppi(jwt, props.getCodiceApplicazione(), Arrays.asList(Gruppi.RICHIEDENTI.name())).getPayload());
		if(users != null)
		{
			pmresult = users.stream().map(PlainStringValueLabel::new).collect(Collectors.toList());
			pmresult.sort(new CustomPlainValueLabelComparator<PlainStringValueLabel>());
		}
		container.put("richiedenti", pmresult);
		return okResponse(container);
	}

	/**
	 * select all
	 * 
	 * @throws CustomUnauthorizedException
	 */
	@GetMapping(value = "/enti_riceventi.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getEntiRiceventi()
			throws CustomUnauthorizedException {
		final String TRACE = "get_enti_riceventi";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(this.remoteSvc.getEntiRiceventi());
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	
	@GetMapping(value = "/enti_riceventi_all.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getAllEntiRiceventi()
			throws CustomUnauthorizedException {
		final String TRACE = "get_all_enti_riceventi";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(this.remoteSvc.getAllEntiRiceventi());
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * tipi_procedimento
	 */
	@GetMapping(value = "/tipi_procedimento.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getTipiProcedimento(
			@RequestParam(required=false) Boolean ancheInattivi) {
		final String TRACE = "get_tipi_procedimento";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(this.dominioSvc.getTipiProcedimento(ancheInattivi));
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * tipo_documento_identita
	 */
	@GetMapping(value = "/tipi_documento_identita.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getTipiDocumentoIdentita() {
		final String TRACE = "get_tipi_documento_identita";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			return super.okResponse(this.tipoDocIdentSvc.select()
					.stream()
					.sorted((a,b)->{ return a.getId()-b.getId();})
					.map(el -> {
				return new PlainNumberValueLabel((long) el.getId(), el.getNome());
			}).collect(Collectors.toList()));
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * tipo_ruolo_ditta
	 */
	@GetMapping(value = "/tipi_ruolo_ditta.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getTipiRuoloDitta() {
		final String TRACE = "get_tipi_ruolo_ditta";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(this.tipoRuoloDittaSvc.select().stream().map(el -> {
				return new PlainNumberValueLabel((long) el.getId(), el.getNome());
			}).collect(Collectors.toList()));
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * titolarita_in_qualita_di
	 */
	@GetMapping(value = "/titolarita_in_qualita_di.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getTitolaritaInQualitaDi() {
		final String TRACE = "titolarita_in_qualita_di";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			List<PlainNumberValueLabel> tqd = this.ruoloReferenteSvc.select().stream().filter(el -> el.getTitolarita()).map(el -> {
				if (el.getAttivaSpecifica() && el.getId() == 5) {
					return new PlainNumberValueLabel((long) el.getId(), el.getDescrizione(),"diAvereTitoloRappSpec");
				} else if (el.getAttivaSpecifica()) {
					return new PlainNumberValueLabel((long) el.getId(), el.getDescrizione(),"diAvereTitoloAltroSpec");
				} else {
					return new PlainNumberValueLabel((long) el.getId(), el.getDescrizione());
				}
			}).collect(Collectors.toList());
			return super.okResponse(tqd);
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * titolarita_in_qualita_di
	 */
	@GetMapping(value = "/titolarita_in_qualita_di_altro_titolare.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getTitolaritaInQualitaDiAltroTit() {
		final String TRACE = "titolarita_in_qualita_di_altro_titolare";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(
					this.ruoloReferenteSvc.select().stream().filter(el -> el.getAltraTitolarita()).map(el -> {
						return el.getAttivaSpecifica()
								? new PlainNumberValueLabel((long) el.getId(), el.getDescrizione(), "specificare")
								: new PlainNumberValueLabel((long) el.getId(), el.getDescrizione());
					}).collect(Collectors.toList()));
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * disclaimer_pratica
	 */
	@GetMapping(value = "/disclaimer_pratica.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> getAlDisclaimer(@RequestParam String codicePratica) {
		final String TRACE = "disclaimer_pratica " +codicePratica;
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			List<PlainNumberValueLabel> disclaimer = this.disclaimerDao.selectByCodicePratica(TipoReferente.SD.getValue(),codicePratica)
																	   .stream()
																	   .map(PlainNumberValueLabel::new)
																	   .collect(Collectors.toList());
			return super.okResponse(disclaimer);
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * disclaimer_pratica
	 */
	@GetMapping(value = "/testo_privacy.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<String>> getPrivacyHtml(@RequestParam Integer id) {
		final String TRACE = "testo_privacy";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			PrivacyDTO privacy=new PrivacyDTO();
			privacy.setId(id);
			privacyDao.find(privacy);
			return super.okResponse(privacy.getTesto());
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * bp_parchi_e_riserve
	 */
	@GetMapping(value = "/bp_parchi_e_riserve.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getParchieRiserve(@RequestParam String codIstat) {
		final String TRACE = "bp_parchi_e_riserve";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			List<PlainStringValueLabel> ret = remoteSvc.findBpParchi(new HashSet<>(Arrays.asList(codIstat)));
			return super.okResponse(ret);
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * ucp_paesaggi
	 */
	@GetMapping(value = "/ucp_paesaggi.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getUcpPaesaggi(@RequestParam String codIstat) {
		final String TRACE = "ucp_paesaggi";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			List<PlainStringValueLabel> ret = remoteSvc.findUcpPaesaggi(new HashSet<>(Arrays.asList(codIstat)));
			return super.okResponse(ret);
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * bp_immobili
	 */
	@GetMapping(value = "/bp_immobili.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getBpImmobili(@RequestParam String codIstat) {
		final String TRACE = "bp_immobili";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			List<PlainStringValueLabel> ret = remoteSvc.findBpImmobili(new HashSet<>(Arrays.asList(codIstat)));
			return super.okResponse(ret);
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * tipi_contenuto per allegati
	 */
	@Logging
	@GetMapping(value = "/tipi_contenuto.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<TipoContenutoDTO>>> getTipiContenuto(@RequestParam Integer tipoProcedimento) {
		try	{
			logger.info("Effettuo la chiamata per ottenere i tipi_contenuto("+tipoProcedimento+")");
			checkAbilitazioneFor(Gruppi.values());
			return okResponse(dominioSvc.getTipiContenuto(tipoProcedimento));
		}
		catch(Exception e)
		{
			logger.error("Errore nel retrive dei record di tipi_contenuto"+tipoProcedimento+")", e.getMessage(), e);
			return koResponse(e, null);
		}
	}
	
	
	@Logging
	@GetMapping(QUALIFICAZIONI)
	public ResponseEntity<BaseRestResponse<List<TipiEQualificazioniDTO>>> getTipiEQualificazioni(@RequestParam("tipoProcedimento")Integer tipoProcedimento)
	{
		try	{
			checkAbilitazioneFor(Gruppi.values());
			logger.info("Effettuo la chiamata per ottenere i record dalla tabella tipi e qualificazioni");
			return okResponse(schedaTecnicaService.findTipiEQualificazioni(tipoProcedimento));
		}
		catch(Exception e)
		{
			logger.error("Errore nel retrive dei record di tipo e qualificazione ", e.getMessage(), e);
			return koResponse(e, null);
		}
	}
	
	@Logging
	@GetMapping(ULTERIORI_CONT_PAES)
	public ResponseEntity<BaseRestResponse<List<UlterioriContestiPaesaggisticiDTO>>> getUlterioriContestiPaesaggistici(@RequestParam(name="idTipoProcedimento")Integer idTipoProcedimento,
																													   @RequestParam(name="dataPratica", required=false)Date data,
																													   @RequestParam(name="sezione", required=false)String sezione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.values());
		try	{
			UlterioriContestiPaesaggisticiSearch filters = new UlterioriContestiPaesaggisticiSearch();
			filters.setSezione(sezione);
			filters.setIdTipoProcedimento(idTipoProcedimento);
			if(data != null) {
				filters.setDataFineVal(data);
				filters.setDataInizioVal(data);
			}
			else {
				filters.setIsnowValid(true);
			}
			return okResponse(schedaTecnicaService.findUlterioreContestiPaesaggistici(filters));
		}
		catch(Exception e)
		{
			logger.error("Errore nel retrive dei record di tipo e qualificazione ", e.getMessage(), e);
			return koResponse(e, null);
		}
	}

	
	@Logging
	@GetMapping(RUOLO_PRATICA)
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getRuoloPratica() throws Exception
	{
		return super.okResponse(ruoloPraticaSvc.select());
	}

	@Autowired
	IConfigurazioneService confSvc;
	@Autowired
	IPraticaService praticaSvc;
	
	/**
	 * configurazione dello sportello alla data di creazione della pratica
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @return
	 * @throws Exception
	 */
	@Logging
	@GetMapping(SPORTELLO_CONFIG)
	public ResponseEntity<BaseRestResponse<SportelloConfigBean>> getSportelloConfigBean(
			@RequestParam("id") String idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.values());
		PraticaDTO pk=new PraticaDTO();
		pk.setId(UUID.fromString(idPratica));
		pk=this.praticaSvc.find(pk);
		return super.okResponse(confSvc.findConfigurazioneCorrente(pk.getDataCreazione(), SportelloConfigBean.class));
	}
	
	@Logging
	@GetMapping(AUTOCOMPLETE_ENTE)
	public ResponseEntity<BaseRestResponse<List<IpaEnteDTO>>> autocompleteIpaEnte(@RequestParam("param")String param) throws Exception
	{
	    return okResponse(dominioSvc.autocompleteIpaEnte(param));	
	}
	
	@GetMapping(value = "/tipo_org_ente_delegato", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<String>> getTipoOrgEnteDelegato(@RequestParam Integer id) {
		final String TRACE = "tipo_org_ente_delegato";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(commonRepo.getTipoOrganizzazione(id));
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@GetMapping(value = "/indirizziMailDefault", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<RubricaSearchDTO>>> getIndirizziMailDefault(@RequestParam Integer id) {
		final String TRACE = "tipo_org_ente_delegato";
		LOGGER.info("Start " + TRACE);
		final StopWatch sw = LogUtil.startLog("start " + TRACE);
		try {
			checkAbilitazioneFor(Gruppi.values());
			
			return super.okResponse(this.remoteSvc.getIndirizziMailDefaultEnte(id));
		} catch (Exception e) {
			LOGGER.error("Error in " + TRACE, e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
}