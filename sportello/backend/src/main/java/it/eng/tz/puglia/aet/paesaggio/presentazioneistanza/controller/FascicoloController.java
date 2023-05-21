package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.awt.print.Book;
import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.IpaEnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PresentaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ValidInfoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.EsoneroPagamentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.AltroTitolareDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PagamentiMypayDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PagamentiMypaySearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.PraticaDelegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.SchedaTecnicaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.privacy.Privacy;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.service.registroimprese.bean.RegistroImpreseBean;
import it.eng.tz.puglia.util.log.LogUtil;

@Controller
@RequestMapping("Fascicolo")
@Privacy
public class FascicoloController extends RoleAwareController
{
	private static final String FIND = "/find.pjson";
	private static final String DELETE = "/delete.pjson";
	private static final String CREA_TITOLARE = "/crea_titolare.pjson";
	private static final String ELIMINA_TITOLARE = "/elimina_titolare.pjson";
	private final static String VALIDA = "/validaIstanza";
	private final static String VALIDA_ALL = "/valida";
	private final static String VALIDA_ALLEGATI = "/validaAllegati";
	private final static String DOWNLOAD_DOMANDA_ISTANZA ="/downloadDomandaIstanza";
	private final static String DOWNLOAD_DOMANDA_DICHIARAZIONE_TECNICA ="/downloadDomandaDichiarazioneTecnica";
	private final static String GET_DICHIARAZIONI_UPLOADED = "/getDichiarazioni";
	private final static String UPDATE = "/update.pjson";
	private final static String CAMBIO_ENTE = "/cambioEnte.pjson";
	private final static String CAMBIO_TIPO_PROCEDIMENTO = "/cambioTipoProcedimento.pjson";
	private final static String GENERA_STAMPA_DOMANDA = "/generaStampaDomanda";
	private final static String ALLEGA_DOCUMENTI_GENERATI = "/allegaDocumentiGenerati";
	private final static String PRESENTA_ISTANZA = "/presentaIstanza";
	private final static String TORNA_COMPILA_DOMANDA = "/compilaDomanda";
	private final static String PRESA_VISIONE_CAMBIAMENTI_VINCOLI = "/presaVisione";
	private final static String EFFETTUA_PAGAMENTO="/effettuaPagamento";
	private final static String SEARCH_PAGAMENTI="/searchPagamenti";
	private final static String TOT_PAGAMENTI_PER_STATO="/totPagamentiPerStato";
	private final static String GET_LAST_DELEGATO = "/lastDelegato";
	private final static String VALIDA_RICHIEDENTE = "/validaRichiedente";
	private final static String UPDATE_RICHIEDENTE = "/updateRichiedente";
	
	
	@Autowired
	Validator validator;
	
	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FascicoloController.class);
	/**
	 * service
	 */
	@Autowired private FascicoloService fascicoloService;
	@Autowired private SchedaTecnicaService schedaTecnicaService;
	@Autowired private AllegatoService allegatiService;
	@Autowired private IConfigurazioniEnteService configurazioniEnteService;
	@Autowired private PraticaService praticaSvc;
	@Autowired private PraticaDelegatoService pdService;
	

	/**
	 * find by pk
	 */
	@PostMapping(value = FIND, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<FascicoloDto>> find(@RequestBody final FascicoloDto pk)
	{
		LOGGER.info("Start find");
		final StopWatch sw = LogUtil.startLog("find");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final FascicoloDto ret = this.fascicoloService.find(pk);
			ret.setSchedaTecnica(this.schedaTecnicaService.find(ret.getId()));
			//se possono essere presenti i file Istanza e SchedaTecnica uploadati, li aggiungo al fascicolo
			ret.setDocumentiSottoscritti(this.allegatiService.getAllegati(ret.getId(),SezioneAllegati.GENERA_STAMPA));
			//controllo se alcuni campi devono essere visibili
			final Boolean statoAvanzamentoVisibile = true;
			final Boolean statoAvanzamentoConfig = this.configurazioniEnteService.visibilitaStatoAvanzamento(new HashSet<Integer>(Collections.singletonList(Integer.valueOf(ret.getEnteDelegato())))).get(0).getBooleano();
			if (!statoAvanzamentoVisibile.equals(statoAvanzamentoConfig)) {
				ret.setDataTrasmissioneParere(null);
				ret.setDataTrasmissioneRelazione(null);
				ret.setDataTrasmissioneVerbale(null);
				ret.setStatoSedutaCommissione(null);
				ret.setStatoParereSoprintendenza(null);
				ret.setStatoRelazioneEnte(null);
			}
			return super.okResponse(ret);
		} catch (final Exception e)
		{
			LOGGER.error("Error in find", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@PostMapping(value = UPDATE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<FascicoloDto>> update(@RequestBody final FascicoloDto pk)
	{
		LOGGER.info("Start update");
		final StopWatch sw = LogUtil.startLog("update");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final FascicoloDto ret = this.fascicoloService.update(pk);
			this.updateFlagValidazione(pk, ret);
			this.fascicoloService.cambiaStato(pk.getId(), AttivitaDaEspletare.COMPILA_DOMANDA);
			return super.okResponse(fascicoloService.find(pk));
		} catch (final Exception e)
		{
			LOGGER.error("Error in update", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	
	@PostMapping(value = CAMBIO_ENTE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<FascicoloDto>> cambioEnte(@RequestBody final FascicoloDto pk)
	{
		LOGGER.info("Start cambioEnte");
		final StopWatch sw = LogUtil.startLog("cambioEnte");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			FascicoloDto ret = this.fascicoloService.cambioEnteDelegatoPratica(pk);
			ret=this.fascicoloService.find(ret);//rileggo tutto....
			this.updateFlagValidazione(pk, ret);
			this.fascicoloService.cambiaStato(pk.getId(), AttivitaDaEspletare.COMPILA_DOMANDA);
			return super.okResponse(ret);
		} catch (final Exception e)
		{
			LOGGER.error("Error in cambioEnte", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	@PostMapping(value = CAMBIO_TIPO_PROCEDIMENTO, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<FascicoloDto>> cambioTipoProcedimento(@RequestBody final FascicoloDto pk)
	{
		LOGGER.info("Start cambioTipoProcedimento");
		final StopWatch sw = LogUtil.startLog("cambioTipoProcedimento");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			FascicoloDto ret = this.fascicoloService.cambioTipoProcedimentoPratica(pk);
			ret=this.fascicoloService.find(ret);//rileggo tutto....
			this.updateFlagValidazione(pk, ret);
			this.fascicoloService.cambiaStato(pk.getId(), AttivitaDaEspletare.COMPILA_DOMANDA);
			return super.okResponse(ret);
		} catch (final Exception e)
		{
			LOGGER.error("Error in cambioTipoProcedimento", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * aggiorna i flag di validazione nel caso fossero a true in quanto l'update di istanza potrebbe invalidare la sezione scheda tecnica.
	 * @param pk
	 * @param ret
	 * @throws CustomOperationToAdviceException
	 * @throws Exception
	 */
	private void updateFlagValidazione(final FascicoloDto pk, final FascicoloDto ret)
			throws CustomOperationToAdviceException, Exception {
		Boolean validaIstanza=false;
		Boolean validaSchedaTecnica=false;
		if(ret.getValidatoIstnza()!=null && ret.getValidatoIstnza()) {
			try {
				validaIstanza=this.fascicoloService.validaIstanza(pk.getCodicePraticaAppptr());	
			}catch(final Exception e ) {}
			ret.setValidatoIstnza(validaIstanza);	
		}
		if(ret.getValidatoSchedaTecnica()!=null && ret.getValidatoSchedaTecnica()) {
			try {
			validaSchedaTecnica=this.schedaTecnicaService.valida(pk.getId());
			}catch(final Exception e) {}
			ret.setValidatoSchedaTecnica(validaSchedaTecnica);	
		}
		this.fascicoloService.updateValidation(pk.getId(), validaIstanza, validaSchedaTecnica, null);
	}

	/**
	 * in FascicoloDto mi arriva solo codicePraticaAppptr
	 * 
	 * @param pk
	 * @return
	 */
	@PostMapping(value = DELETE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestBody final FascicoloDto pk)
	{
		LOGGER.info("Start delete");
		final StopWatch sw = LogUtil.startLog("delete");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final int ret = this.fascicoloService.delete(pk);
			return super.okResponse(ret);
		} catch (final Exception e)
		{
			LOGGER.error("Error in delete", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * in FascicoloDto mi basta solo l'id della pratica viene creato un nuovo record
	 * titolare in referenti
	 * 
	 * @param pk
	 * @return
	 */
	@PostMapping(value = CREA_TITOLARE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AltroTitolareDto>> creaTitolare(@RequestBody final FascicoloDto pk)
	{
		LOGGER.info("Start crea_titolare");
		final StopWatch sw = LogUtil.startLog("crea_titolare");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final AltroTitolareDto ret = this.fascicoloService.creaTitolare(pk);
			return super.okResponse(ret);
		} catch (final Exception e)
		{
			LOGGER.error("Error in crea_titolare", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * 
	 * @param req id del altro_titolare da rimuovere e in description il
	 *            codicePraticaApptr della pratica.
	 * @return
	 */
	@PostMapping(value = ELIMINA_TITOLARE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> eliminaTitolare(@RequestBody final PlainNumberValueLabel req)
	{
		LOGGER.info("Start elimina_titolare");
		final StopWatch sw = LogUtil.startLog("elimina_titolare");
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final int ret = this.fascicoloService.eliminaTitolare(req.getValue());
			return super.okResponse(ret);
		} catch (final Exception e)
		{
			LOGGER.error("Error in elimina_titolare", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally
		{
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * Endoint per effettuare la validazione in BE della pratica
	 * @param idPratica da validare
	 * @return
	 * @throws Exception
	 */
	@Logging
	@GetMapping(value=VALIDA, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> validaIstanza(@RequestParam("codicePratica") final String codicePratica, @RequestParam("idPratica") final UUID idPratica) throws Exception
	{
		try 
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final Boolean valido = this.fascicoloService.validaIstanza(codicePratica);
			final ResponseEntity<BaseRestResponse<Boolean>> response = super.okResponse(valido);
			this.fascicoloService.updateValidation(idPratica, valido, null, null);
			return response;
		} catch (final Exception e) 
		{
			this.logger.error("Errore nella validazione della sezione istanza ", e.getMessage(),e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@Logging
	@GetMapping(value=VALIDA_ALLEGATI, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> validaAllegati(
			@RequestParam("codicePratica") final String codicePratica,
			@RequestParam("idPratica") final UUID idPratica) throws Exception
	{
		try 
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			PraticaDTO praticaDTO = this.checkDiMiaCompetenza(idPratica);
			final Boolean valido = this.fascicoloService.validaAllegati(praticaDTO);
			final ResponseEntity<BaseRestResponse<Boolean>> response = super.okResponse(valido);
			this.fascicoloService.updateValidation(idPratica, null, null, valido);
			return response;
		} catch (final Exception e) 
		{
			this.logger.error("Errore nella validazione della sezione istanza ", e.getMessage(),e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@Logging
	@GetMapping(value=VALIDA_ALL, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ValidInfoDto>> valida(@RequestParam("codicePratica") final String codicePratica, 
																 @RequestParam("idPratica") final UUID idPratica) throws Exception
	{
		try {
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			PraticaDTO praticaDTO = this.checkDiMiaCompetenza(idPratica);
			final ValidInfoDto v = validaSezioni(codicePratica, idPratica, praticaDTO);
			if (v.getIstanza() && v.getSchedaTecnica() && v.getAllegati()) {
				this.fascicoloService.controllaVincoli(idPratica, v, false);
			}
			final ResponseEntity<BaseRestResponse<ValidInfoDto>> response = super.okResponse(v);
			this.fascicoloService.updateValidation(idPratica, v.getIstanza(), v.getSchedaTecnica(), v.getAllegati());
			return response;
		} catch (final Exception e) 
		{
			this.logger.error("Errore nella validazione di istanza e/o della scheda tecnica ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}

	private ValidInfoDto validaSezioni(final String codicePratica, final UUID idPratica, PraticaDTO praticaDTO) {
		final ValidInfoDto v = new ValidInfoDto();
		Boolean validaIstanza=false;
		try {
			validaIstanza=this.fascicoloService.validaIstanza(codicePratica);
		} catch(final Exception e) { 
			LOGGER.error("Errore in validaAll ",e); 
			}
		Boolean validaSchedaTecnica=false;
		try {
			validaSchedaTecnica=this.schedaTecnicaService.valida(idPratica);
		} catch(final Exception e) 
		{ 
			LOGGER.error("Errore in validaAll ",e); 
		}
		Boolean validaAllegati=false;
		try {
			validaAllegati=this.fascicoloService.validaAllegati(praticaDTO);
		} catch(final Exception e) 
		{ 
			LOGGER.error("Errore in validaAll ",e); 
		}
		v.setIstanza(validaIstanza);
		v.setSchedaTecnica(validaSchedaTecnica);
		v.setAllegati(validaAllegati);
		return v;
	}
	
	@Logging
	@GetMapping(value=PRESA_VISIONE_CAMBIAMENTI_VINCOLI, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> presaVisioneCambiamentiVincoli(@RequestParam("idPratica") final UUID idPratica) throws Exception
	{
		try {
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			this.checkDiMiaCompetenza(idPratica);
			this.fascicoloService.controllaVincoli(idPratica, new ValidInfoDto(), true);
			return super.okResponse(true);
		} catch (final Exception e)
		{
			this.logger.error("Errore nell'accettazione della presa visione dei cambiamenti relativi ai vincoli paesaggistici/PPTR", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	
	@Logging 
	@GetMapping(value = DOWNLOAD_DOMANDA_ISTANZA, produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam("codiceFascicolo") final String codiceFascicolo, final HttpServletResponse response) 
	{
		LOGGER.info("Start download");
		try 
		{
			final AllegatiDTO optAll75x = this.get75x(codiceFascicolo, 750);
			final CmsDownloadResponseBean cms =this.allegatiService.downloadAllegato(optAll75x.getId());
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato nel CMS...");
			final File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} 
		catch (final Exception e) 
		{
			LOGGER.error("Error in download", e);
			response.setStatus(500);
		}
	}

	/**
	 * effettua check di competenza
	 * @author acolaianni
	 *
	 * @param codiceFascicolo
	 * @param idTipoContenuto
	 * @return
	 * @throws CustomOperationToAdviceException
	 * @throws Exception
	 */
	private AllegatiDTO get75x(final String codiceFascicolo, final Integer idTipoContenuto)
			throws CustomOperationToAdviceException, Exception {
		final String messaggio="File autogenerato non trovato ... tornare a Compila domanda e riffettuare il genera e stampa !!!";
		this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
		final PraticaSearch praticaS=new PraticaSearch();
		praticaS.setCodicePraticaAppptr(codiceFascicolo);
		final FascicoloDto fascicolo = this.fascicoloService.find(praticaS);
		final List<AllegatiDTO> allegati = this.allegatiService.getAllegati(fascicolo.getId(), SezioneAllegati.GENERA_STAMPA_PREVIEW,null,idTipoContenuto);
		if(allegati==null || allegati.size()<=0) {
			throw new CustomOperationToAdviceException(messaggio);
		}
		return allegati.get(0);
	}
	
	@Logging
	@GetMapping(value = DOWNLOAD_DOMANDA_DICHIARAZIONE_TECNICA, produces = MEDIA_TYPE)
	public void downloadAttachmentDichiarazioneTecnica(@RequestParam("codiceFascicolo") final String codiceFascicolo, final HttpServletResponse response) 
	{
		LOGGER.info("Start download");
		try 
		{
			final AllegatiDTO optAll75x = this.get75x(codiceFascicolo, 751);
			final CmsDownloadResponseBean cms =this.allegatiService.downloadAllegato(optAll75x.getId());
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato nel CMS...");
			final File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} 
		catch (final Exception e) 
		{
			LOGGER.error("Error in download", e);
			response.setStatus(500);
		}
	}
	
	@Logging
	@GetMapping(value=GET_DICHIARAZIONI_UPLOADED, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<DocumentoDto>>> getDichiarazioni(@RequestParam("idPratica") final UUID idPratica)
	{
		try 
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			this.checkDiMiaCompetenza(idPratica);
			final ResponseEntity<BaseRestResponse<List<DocumentoDto>>> response = super.okResponse(this.fascicoloService.getDichiarazioni(idPratica));
			return response;
		} catch (final Exception e) 
		{
			this.logger.error("Errore nel reperimento dei metadati riguardando le dichiarazioni uploadate ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@Logging
	@GetMapping(value=GENERA_STAMPA_DOMANDA, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> generaStampaDomanda(@RequestParam("idPratica") final UUID idPratica)
	{
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			PraticaDTO praticaDTO =this.checkDiMiaCompetenza(idPratica);
			final AttivitaDaEspletare stato = this.fascicoloService.getStato(idPratica);
			//rieffettuo la validazione
			final ValidInfoDto v = validaSezioni(praticaDTO.getCodicePraticaAppptr(), idPratica, praticaDTO);
			if(!v.isValidAll()) {
				throw new CustomOperationToAdviceException("Alcune sezioni risultano invalide, rieffettuare la validazione !!!");
			}
			if(stato.equals(AttivitaDaEspletare.COMPILA_DOMANDA))
			{
				this.fascicoloService.cambiaStato(idPratica, AttivitaDaEspletare.GENERA_STAMPA_DOMANDA);
			}
			return super.okResponse(true);
		} catch (final Exception e) 
		{
			this.logger.error("Errore mentre cambiavo lo stato dell'istanza in \"GENERA_STAMPA_DOMANDA\": ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@Logging
	@GetMapping(value=ALLEGA_DOCUMENTI_GENERATI, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> allegaDocumentiGenerati(@RequestParam("idPratica") final UUID idPratica)
	{
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			this.checkDiMiaCompetenza(idPratica);
			final AttivitaDaEspletare stato = this.fascicoloService.getStato(idPratica);
			if(stato.equals(AttivitaDaEspletare.GENERA_STAMPA_DOMANDA))
				this.fascicoloService.cambiaStato(idPratica, AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI);
			return super.okResponse(true);
		} catch (final Exception e) 
		{
			this.logger.error("Errore mentre cambiavo lo stato dell'iistanza in \"ALLEGA_DOCUMENTI_SOTTOSCRITTI\": ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	
	
	@Logging
	@GetMapping(value=PRESENTA_ISTANZA, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PresentaIstanzaDto>> presentaIstanza(
			@RequestParam("idPratica") final UUID idPratica, 
			@RequestParam("codicePratica") final String codicePratica) throws Exception
	{
		final StopWatch sw = LogUtil.startLog("presentaIstanza");
		try{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			final PresentaIstanzaDto presenteIstanza = this.fascicoloService.presentaIstanza(idPratica, codicePratica);
			return this.okResponse(presenteIstanza);
		}catch(final Exception e){
			this.logger.error("Errore in presentaIstanza", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
		
	}
	
	@Logging
	@PutMapping(value=TORNA_COMPILA_DOMANDA, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> tornaCompilazioneIstanza(@RequestParam("idPratica") final UUID idPratica)
	{
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			this.checkDiMiaCompetenza(idPratica);
			final AttivitaDaEspletare stato = this.fascicoloService.getStato(idPratica);
			if(stato.equals(AttivitaDaEspletare.GENERA_STAMPA_DOMANDA) ||
			   stato.equals(AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI))
			{
				final int n = this.allegatiService.deleteAllOfType(idPratica, SezioneAllegati.GENERA_STAMPA);
				if(n > 0)
					this.logger.info("Ho eliminato {} allegati per domanda dichiarazione tecnica e dichiarazione istanza", n);
				this.fascicoloService.cambiaStato(idPratica, AttivitaDaEspletare.COMPILA_DOMANDA);
				return this.okResponse(true);
			}
			else
			{
				this.logger.error("Stato non valido per il passaggio a 'compila istanza'");
				return this.okResponse(false);
			}
		} catch(final Exception e)
		{
			this.logger.error("Errore durante il passaggio di stato");
			return this.koResponse(e, new BaseRestResponse<Boolean>());
		}
	}
	
	
	
	/**
	 * il ritorno puo' essere nullo se la call al service interno restituisce KO
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param importo
	 * @param causale
	 * @param retUrl
	 * @return
	 * @throws Exception
	 */
	@Logging
	@GetMapping(value=EFFETTUA_PAGAMENTO, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PagamentiMypayDTO>> effettuaPagamento(
			@RequestParam("idPratica") final UUID idPratica,
			@RequestParam final String importo, //arriva con XXXXX.DD
			@RequestParam final String causale,
			@RequestParam final String retUrl) throws Exception{
		LOGGER.info("Start effettuaPagamento");
		final StopWatch sw = LogUtil.startLog("effettuaPagamento");
		try {
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			PraticaDTO pratica=new PraticaDTO();
			pratica.setId(idPratica);
			pratica=this.praticaSvc.find(pratica);
			this.fascicoloService.checkDiMiaCompetenza(pratica);
			final Double importoDbl=Double.parseDouble(importo);
			if(importoDbl<=0)
				throw new CustomOperationToAdviceException("Importo nullo!!");
			final PagamentiMypayDTO ret = this.fascicoloService.effettuaPagamento(pratica, importoDbl, causale, retUrl);
			if(ret==null) {
				throw new CustomOperationToAdviceException("Errore durante la chiamata la servizio di pagamento ");
			}
			return super.okResponse(ret);
		}catch(final Exception e) {
			LOGGER.error("Error in inviaDovuti", e);
			throw e;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	
	@Logging
	@GetMapping(value=SEARCH_PAGAMENTI, produces=MEDIA_TYPE)
	@Deprecated
	public ResponseEntity<BaseRestResponse<PaginatedList<PagamentiMypayDTO>>> searchPagamenti(
			final PagamentiMypaySearch search) throws Exception{
		LOGGER.info("Start searchPagamenti");
		final StopWatch sw = LogUtil.startLog("searchPagamenti");
		try {
			this.checkAbilitazioneFor(Gruppi.values());
			if(search==null || search.getPraticaId()==null) {
				throw new CustomOperationToAdviceException("Riferimento pratica errato");
			}
			PraticaDTO pratica=new PraticaDTO();
			pratica.setId(UUID.fromString(search.getPraticaId()));
			pratica=this.praticaSvc.find(pratica);
			this.fascicoloService.checkDiMiaCompetenza(pratica);
			//se ci sono pagamenti da aggiornare, chiamo il service per aggiornare lo stato...
			this.fascicoloService.aggiornaEventualiStatiPagamento(search);
			return super.okResponse(this.fascicoloService.searchPagamenti(search));
		}catch(final Exception e) {
			LOGGER.error("Error in searchPagamenti", e);
			throw e;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	
	/**
	 * 
	 * @author acolaianni
	 *	almeno uno dei due deve essere presente
	 * @param praticaId
	 * @param codicePratica
	 * @return
	 * @throws Exception
	 */
	@Logging
	@GetMapping(value=TOT_PAGAMENTI_PER_STATO, produces=MEDIA_TYPE)
	@Deprecated
	public ResponseEntity<BaseRestResponse<Map<String, BigDecimal>>> getPagamentiPerStato(
			@RequestParam(required = false) final UUID praticaId,
			@RequestParam(required = false) final String codicePratica) throws Exception{
		LOGGER.info("Start getPagamentiPerStato");
		final StopWatch sw = LogUtil.startLog("getPagamentiPerStato");
		try {
			this.checkAbilitazioneFor(Gruppi.values());
			if(praticaId==null && codicePratica==null) {
				throw new CustomOperationToAdviceException("Riferimento pratica errato");
			}
			PraticaDTO pratica=new PraticaDTO();
			if(codicePratica!=null) {
				pratica=this.praticaSvc.findByCodice(codicePratica);
			}else {
				pratica.setId(praticaId);
				pratica=this.praticaSvc.find(pratica);	
			}
			this.fascicoloService.checkDiMiaCompetenza(pratica);
			return super.okResponse(this.fascicoloService.totPagato(pratica));
		}catch(final Exception e) {
			LOGGER.error("Error in getPagamentiPerStato", e);
			throw e;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Recupera impresa da visura camerale
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @param id
	 * @param codiceFiscale
	 * @return response payload
	 */
	@Logging
	@Operation(summary = "Recupera Impresa", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	
	@RequestMapping(value="/recupera-impresa/{idPratica}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<RegistroImpreseBean>> recuperaImpresa(
			 @PathVariable final String idPratica
			,@RequestParam final String codiceFiscale
			,@RequestParam final Long id
	){
		final StopWatch sw = LogUtil.startLog("recuperaImpresa {}", codiceFiscale);
		try{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			return super.okResponse(this.fascicoloService.validaAzienda(idPratica, id, codiceFiscale));
		}catch(final Exception e){
			this.logger.error("Errore in recuperaImpresa", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	@Operation(summary = "Valida Ente", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	/**
	 * @author Antonio La Gatta
	 * @param id
	 * @param codiceFiscale
	 * @return recupera ente
	 */
	@Logging
	@RequestMapping(value="/recupera-ente/{idPratica}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<IpaEnteDto>> recuperaEnte(
			@PathVariable final String idPratica
			 ,@RequestParam final String codiceFiscale
			 ,@RequestParam final Long id
	){
		final StopWatch sw = LogUtil.startLog("recuperaEnte {}", codiceFiscale);
		try{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			return super.okResponse(this.fascicoloService.validaEnte(idPratica, id, codiceFiscale));
		}catch(final Exception e){
			this.logger.error("Errore in recuperaEnte", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * salvataggio checkbox esonero pagamenti
	 * viene invalidata la sezione allegati
	 * @author acolaianni
	 *
	 * @param pk
	 * @return
	 * @throws CustomOperationToAdviceException 
	 */
	@Logging
	@PutMapping(value = "/esoneri/{idPratica}")
	public ResponseEntity<BaseRestResponse<Boolean>> saveEsoneri(@PathVariable String idPratica,
			@RequestBody EsoneroPagamentoDto esonero
			) throws Exception
	{
		this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
		PraticaDTO praticaDTO = checkDiMiaCompetenza(UUID.fromString(idPratica));
		this.fascicoloService.updateEsoneri(praticaDTO,esonero.getEsoneroOneri(),esonero.getEsoneroBollo(),SecurityUtil.getUsername());
		return super.okResponse(true);		
	}
	
	@Logging
	@GetMapping(GET_LAST_DELEGATO)
	public ResponseEntity<BaseRestResponse<PraticaDelegatoDTO>> getLastDelegato() throws Exception
	{
		final StopWatch sw = LogUtil.startLog(GET_LAST_DELEGATO);
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			return okResponse(pdService.getLastDelegato());
		}
		finally
		{
		    logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Logging
	@GetMapping(VALIDA_RICHIEDENTE)
	public ResponseEntity<BaseRestResponse<Boolean>> validaRichiedente(@RequestParam("codicePratica") String codicePratica) throws Exception
	{
		final StopWatch sw = LogUtil.startLog(VALIDA_RICHIEDENTE);
		try
		{
			this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			fascicoloService.validaRichiedente(codicePratica);
			return okResponse(true);
		}
		finally
		{
		    logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Logging
	@PostMapping(UPDATE_RICHIEDENTE)
	public ResponseEntity<BaseRestResponse<FascicoloDto>> updateRichiedente(@RequestBody FascicoloDto fascicolo) throws Exception
	{
	    LOGGER.info("Start update");
	    final StopWatch sw = LogUtil.startLog("update richiedente");
	    try
	    {
		this.checkAbilitazioneFor(Ruoli.RICHIEDENTE);
		fascicoloService.updateRichiedente(fascicolo);
		return super.okResponse(fascicoloService.find(fascicolo));
	    } catch (final Exception e)
	    {
		LOGGER.error("Error in update richiedente", e);
		return super.koResponse(e, new BaseRestResponse<>());
	    } finally
	    {
		LOGGER.info(LogUtil.stopLog(sw));
	    }
	}
	
}