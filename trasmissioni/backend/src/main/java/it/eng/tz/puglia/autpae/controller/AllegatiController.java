package it.eng.tz.puglia.autpae.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.InfoMultitipoDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.utility.CheckSumUtil;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
//@RequestMapping(value = "allegati", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AllegatiController extends CheckGroupAbstractController//extends _RestController 
{

	private static final Logger log = LoggerFactory.getLogger(AllegatiController.class);
	private final String ALLEGATI_PATH="allegati";
	private final String API_ALLEGATI_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+ALLEGATI_PATH;
	@Autowired private AllegatoService allegatoService;
	@Autowired private CreaReportService creaReportService;
	@Autowired private ApplicationProperties props;
	@Autowired private CommonService commonSvc;
	@Autowired AllegatoFascicoloService allFascicoloSvc;


/*	@PostMapping(value = "/test_JasperJSON")
	public ResponseEntity<BaseRestResponse<List<JasperDTO>>> testJasperJson(@RequestParam(name="idFascicolo", required=false) Long idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<JasperDTO> json = new ArrayList<>();
		try  {
			json = creaReportService.test_JasperJSON(idFascicolo==null ? 1L : idFascicolo);
		} catch (Exception e) {
			throw e;
		}
		return super.okResponse(json);
	}	*/

	@Operation(summary = "Download dell'anteprima ricevuta di trasmissione", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = {ALLEGATI_PATH+"/download_AnteprimaTrasmissione",
			API_ALLEGATI_PATH+"/download_AnteprimaTrasmissione"}, produces = "application/pdf")
	public void downloadAnteprimaTrasmissione(final HttpServletResponse response, 
			@RequestParam(name = "idFascicolo", required = true) final long idFascicolo,
			@ApiParam("Eventuale lista di indirizzi mail/PEC di ulteriori destinatari della trasmissione")												 				
			@RequestBody(required = false) final List<TipologicaDestinatarioDTO> ulterioriDestinatari) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("download_AnteprimaTrasmissione");
		try {
		//checkAbilitazioneFor(Gruppi.ED_);
		checkAbilitazioneForTrasmissione();
		checkPermission(idFascicolo);
		JasperPrint jasper = null;
		try {
			// apro output stream dalla response
			OutputStream out = response.getOutputStream();

			// creo il pdf del jasper report
			jasper = creaReportService.creaPdf_RicevutaDiTrasmissione(idFascicolo, null, ulterioriDestinatari, null, false); // abbiamo tutto internamente

			// setto alcune informazioni per l'header
			response.setContentType("application/pdf");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "(Anteprima)_Lettera_di_Trasmissione.pdf" + "\"");
			response.setHeader("file-name", "ricevuta_di_trasmissione");

			// esporto il il pdf e lo incapsulo nell'header
			JasperExportManager.exportReportToPdfStream(jasper, out);

			// chiudo lo stream
			out.flush();
			out.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		}finally {
			log.info(LogUtil.stopLog(sw));
		}
	}

	@GetMapping(value = ALLEGATI_PATH+"/getProtocollo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE) // recupera il numero di protocollo dell'allegato "Ricevuta di Trasmissione"
	public ResponseEntity<BaseRestResponse<String>> getProtocollo(@RequestParam(name = "idFascicolo", required = true) final long idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("getProtocollo");
		try {
			checkPermission(idFascicolo);
			return super.okResponse(allegatoService.findRicevutaTrasmissione(idFascicolo).getLabel());	
		}finally{
			log.info(LogUtil.stopLog(sw));
		}
		
	}

	@GetMapping(value = ALLEGATI_PATH+"/info",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> infoAllegato(@RequestParam(name = "idAllegato", required = true) final long idAllegato) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("info");
		try {
			super.checkAbilitazioneFor(Gruppi.values());
			AllegatoCustomDTO ret = allegatoService.infoAllegato(idAllegato);
			return super.okResponse(ret);	
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@GetMapping(value = {ALLEGATI_PATH+"/get",API_ALLEGATI_PATH+"/get"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Elenco tipologie di allegati ammesse per il tipoProcedimento in input, come da pannello Allegati", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<List<AllegatoCustomDTO>>> tabAllegati(@RequestParam(name = "tipoProcedimento", required = true) final TipoProcedimento tipoProcedimento) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("get");
		try {
			super.checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(allegatoService.tabAllegati(tipoProcedimento));	
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@GetMapping(value = {ALLEGATI_PATH+"/dati",API_ALLEGATI_PATH+"/dati"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Elenco degli allegati che risultano caricati sul fascicolo", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<List<AllegatoCustomDTO>>> datiAllegati(@RequestParam(name = "idFascicolo", required = true) final long idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("dati");
		try {
			checkPermission(idFascicolo);
			return super.okResponse(allegatoService.datiAllegati(idFascicolo));	
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@PostMapping(value = { ALLEGATI_PATH + "/caricaAllegatoETipi/{idFascicolo}", API_ALLEGATI_PATH
			+ "/caricaAllegatoETipi/{idFascicolo}" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Caricamento di un allegato non obbligatorio del corrispondente pannello Allegati", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaAllegatoETipi(
			@ApiParam("Array delle tipologie attribuite all'allegato") 
			@RequestParam(required = true) final TipoAllegato[] tipiAllegato,
			@ApiParam("Allegato da caricare, dimensione massima ammessa 50MB") 
			@RequestPart(required = true) final MultipartFile file,
			@ApiParam("IdFascicolo")
			@PathVariable final Long idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("caricaAllegatoETipi");
		try {
			InfoMultitipoDTO info = new InfoMultitipoDTO();
			info.setIdFascicolo(idFascicolo);
			info.setTipiAllegato(List.of(tipiAllegato));
			return caricaMultitipo(info, file);
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@PostMapping(value = {ALLEGATI_PATH+"/caricaMultitipo",API_ALLEGATI_PATH+"/caricaMultitipo"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Caricamento di un allegato non obbligatorio del corrispondente pannello Allegati", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaAllegatoMultitipo(
			@ApiParam("Descrittore delle tipologie contenute nell'allegato e dell'idFascicolo a cui fa riferimento")
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Descrittore delle tipologie contenute nell'allegato e dell'idFascicolo a cui fa riferimento", required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schema=@io.swagger.v3.oas.annotations.media.Schema(implementation = InfoMultitipoDTO.class
                    )))
			@RequestPart(required = true) final InfoMultitipoDTO infoAllegato,
			@ApiParam("Allegato da caricare, dimensione massima ammessa 50MB")
			@RequestPart(required = true) final MultipartFile file) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("caricaMultitipo");
		try {
			return caricaMultitipo(infoAllegato, file);		
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}

	private ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaMultitipo(final InfoMultitipoDTO infoAllegato,
			final MultipartFile file) throws CustomOperationToAdviceException, Exception {
		checkAbilitazioneForTrasmissione();
		if(infoAllegato.getIdFascicolo()==null)
			 throw new CustomOperationToAdviceException("Id fascicolo null, impossibile caricare");
		super.check();
		checkPermission(infoAllegato.getIdFascicolo(),StatoFascicolo.WORKING);
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		if (infoAllegato.getTipiAllegato() == null || infoAllegato.getTipiAllegato().size() <= 0) {
			log.error("La tipologia di allegato non è specificata!");
			throw new Exception("La tipologia di allegato non è specificata!");
		}
		boolean tipiKo = infoAllegato.getTipiAllegato().stream().filter(el -> 
		{
			List<TipoAllegato> tipiAmmessi = null;
			if (props.isPareri()) {
				tipiAmmessi = new ArrayList<>( List.of(TipoAllegato.ISTANZA, 
													   TipoAllegato.ELABORATI,
													   TipoAllegato.INTEGRAZIONI) );
			}
			else if (props.isAutPae()) {
				tipiAmmessi = new ArrayList<>( List.of(TipoAllegato.ISTANZA, 
													   TipoAllegato.VERBALE, 
													   TipoAllegato.PARERE,
													   TipoAllegato.PREAVVISO, 
													   TipoAllegato.ALTRI_PARERI, 
													   TipoAllegato.ELABORATI,
													   TipoAllegato.PARERI_SCHEDA, 
													   TipoAllegato.SCHEDA_PROGETTO) );
			}
			else if(props.isPutt()) {
				tipiAmmessi = new ArrayList<>( List.of(TipoAllegato.ATTESTAZIONE_CONFORMITA, 
													   TipoAllegato.PREAVVISO, 
													   TipoAllegato.RICHIESTA_SOPRINTENDENZA,
													   TipoAllegato.PROPOSTA_PROVVEDIMENTO, 
													   TipoAllegato.DOCUMENTAZIONE_FOTOGRAFICA, 
													   TipoAllegato.PARERE,
													   TipoAllegato.RETTIFICA_AUTORIZZAZIONE,
													   TipoAllegato.VERBALE, 
													   TipoAllegato.ISTANZA, 
													   TipoAllegato.ALTRI_PARERI, 
													   TipoAllegato.ELABORATI, 
													   TipoAllegato.ALTRO) );
			}
			return !tipiAmmessi.contains(el);
		}).findFirst().isPresent();
		if (!tipiKo) {
			return super.okResponse(
					allegatoService.inserisciAllegato(Collections.singletonList(infoAllegato.getIdFascicolo()),	infoAllegato.getTipiAllegato(), file, null));
		} else {
			log.error("Tipologia di allegato non riconosciuta o non consentita!");
			throw new CustomOperationToAdviceException("Tipologia di allegato non riconosciuta o non consentita!");
		}
	}


	@DeleteMapping(value = {ALLEGATI_PATH+"/elimina",API_ALLEGATI_PATH+"/elimina"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Cancellazione irreversibile dell'allegato, se lo stato fasciololo è compatibile.", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegato(
			@RequestParam(name = "idAllegato", required = true) final long idAllegato,
			@RequestParam(name = "idFascicolo", required = true) final long idFascicolo
			) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("elimina");
		try {
			if (idAllegato < 1)
				throw new Exception("Id allegato errato!");
			checkPermission(idFascicolo, StatoFascicolo.WORKING);
			allegatoService.eliminaAllegato(idAllegato);
			return super.okResponse(true);	
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@DeleteMapping(value = ALLEGATI_PATH+"/eliminaSelezionati",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Cancellazione di tutti gli allegati non obbligatori in caso di fascicolo a stato WORKING", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegati(
			@RequestParam(name = "listaAllegati", required = true) final List<Long> listaAllegati,
			@RequestParam(name = "idFascicolo", required = true) final long idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("eliminaSelezionati");
		try {
			if(ListUtil.isEmpty(listaAllegati)) {
				return super.okResponse(false);
			}
			//controllo che ci siano i permessi per farlo
			checkPermission(idFascicolo, StatoFascicolo.WORKING);
			allegatoService.eliminaAllegati(listaAllegati);
			return super.okResponse(true);	
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	
	@GetMapping(value = API_ALLEGATI_PATH+"/downloadAllegatoFascicolo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Download degli allegati al fascicolo, per la ricevuta trasmissione tipoAllegato=RICEVUTA_TRASMISSIONE e idAllegato a null ", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public void downloadAllegatoFascicolo(
			@RequestParam(name = "tipoAllegato", 	 required = false) final TipoAllegato tipoAllegato,
			@Parameter(description = "Per la RICEVUTA_TRASMISSIONE settare a null")
			@RequestParam(name = "idAllegato", 		 required = false) final Long idAllegato,
			@RequestParam(name = "idFascicolo", 	 required = true) final Long idFascicolo,
														final HttpServletResponse response) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("download");
		try {
			checkPermission(idFascicolo);
			try {
				File f = allegatoService.downloadFile(tipoAllegato, idAllegato, idFascicolo, null);
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

	@GetMapping(value = ALLEGATI_PATH + "/downloadRicevutaMail")
	public void downloadRicevutaMail(@RequestParam(name = "tipoAllegato", required = false) final TipoAllegato tipoAllegato,
			@RequestParam(name = "idFascicolo", required = true) final Long idFascicolo,
			@RequestParam(name = "idRicevuta", required = false) final Long idRicevuta, final HttpServletResponse response)
			throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("downloadRicevutaMail");
		try {
			checkPermission(idFascicolo);
			if (idRicevuta == null && tipoAllegato == null) {
				log.error("Dati insufficienti!");
				throw new Exception("Dati insufficienti!");
			}
			try {
				File f = allegatoService.downloadRicevutaMail(tipoAllegato,idRicevuta);
				String nome = f.getName();
				copyToResponse(response, f, nome);
			} catch (Exception e) {
				log.error("Errore nel download del file!", e);
				response.setStatus(500);
			}
		} catch (Exception e) {
			log.error("Errore nel reperire l'id cmis !", e);
			response.setStatus(500);
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	
	@GetMapping(value = ALLEGATI_PATH+"/download")
	public void download(@RequestParam(name = "tipoAllegato", 	 required = false) final TipoAllegato tipoAllegato,
														@RequestParam(name = "idAllegato", 		 required = false) final Long idAllegato,
														@RequestParam(name = "idFascicolo", 	 required = true) final Long idFascicolo,
														@RequestParam(name = "idCorrispondenza", required = false) final Long idCorrispondenza,
														final HttpServletResponse response) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("download");
		try {
			checkPermission(idFascicolo);
			if (idAllegato == null && tipoAllegato == null) {
				log.error("Dati insufficienti!");
				throw new Exception("Dati insufficienti!");
			}
			if (tipoAllegato != null && (idFascicolo == null && idCorrispondenza == null)) {
				log.error("Dati insufficienti!");
				throw new Exception("Dati insufficienti!");
			}
			//if (idAllegato != null && (tipoAllegato != null || idFascicolo != null || idCorrispondenza != null || idRicevuta != null)) {
			if (idAllegato != null && (tipoAllegato != null || idCorrispondenza != null )) {
				log.error("Troppi dati inseriti!");
				throw new Exception("Troppi dati inseriti!");
			}
			try {
				File f = allegatoService.downloadFile(tipoAllegato, idAllegato, idFascicolo, idCorrispondenza);
				String nome = f.getName();
				copyToResponse(response, f, nome);
			} catch (Exception e) {
				log.error("Errore nel download del file!",e);
				response.setStatus(500);
			}
		}catch(Exception e) {
			log.error("Errore nel reperire l'id cmis !",e);
			response.setStatus(500);
		}
		finally {
			logger.info(LogUtil.stopLog(sw));
		}
		
	}
	
	
	/**
	 * url per effettuare il retrieve del manuale da alfresco
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = ALLEGATI_PATH + "/downloadManuale.pjson")
	public void downloadManuale(final HttpServletResponse response) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("downloadManuale");
		try {
			try {
				String idAlfresco=this.commonSvc.getConfigurationValue("MANUALE-CMIS-ID", props.getCodiceApplicazione());
				//get from common.sit_puglia_configuration where key='MANUALE-CMIS-ID' and application_name=autpae .. o pareri
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

	
	
	@PostMapping(value = {ALLEGATI_PATH+"/calcola-hash"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Calcolo impronta hash files", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<String>> calcolaHash(
			@ApiParam("Allegato da caricare, dimensione massima ammessa 50MB")
			@RequestPart(required = true) final MultipartFile file) throws Exception {
		log.info("Chiamato il controller: 'allegati'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("calcolaHash");
		try {
			checkAbilitazioneFor(Gruppi.values());
			if (file == null || file.isEmpty()) {
				log.error("Allegato vuoto !");
				throw new Exception("Allegato vuoto!");
			}
			String checksum = CheckSumUtil.getCheckSum(file);
			return super.okResponse(checksum);
		}catch(final Exception e) {
			logger.error("Error in calcolaHash", e);
			return super.koResponse(e, new BaseRestResponse<>());	
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
}