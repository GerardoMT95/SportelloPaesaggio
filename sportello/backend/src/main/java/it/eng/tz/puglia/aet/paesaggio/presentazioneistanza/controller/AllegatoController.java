package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RequestAllegato;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CheckSumUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.service.http.IHttpPadesClientService;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.util.log.LogUtil;

@Controller
@RequestMapping(value = "allegati", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
/**
 * Controller dedicato alla gestione di allegati nella fase di presentazione istanza 
 * @author acolaianni
 *
 */
@Api(value ="AllegatoController", description = "Controller dedicato alla gestione di allegati nella fase di presentazione istanza")
public class AllegatoController extends RoleAwareController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AllegatoController.class);

	@Value("${url.pades.checkOriginalAndSigned}")
	private String pathCheckPades;

	
	@Autowired AllegatoService allegatiService;
	//@Autowired FascicoloService fascicoloService;
	@Autowired IntegrazioneDocumentale integrazioneService;
	@Autowired ApplicationProperties props;
	@Autowired IHttpPadesClientService padesService;
	/**
	 * 
	 * @param praticaId
	 * @param referenteId
	 * @param file        documento del referente in questione (richiedente tecnico o altro titolare)
	 * @return
	 */
	@PostMapping(value = "/upload_documento_referente.pjson", consumes = {
			"multipart/form-data" }, produces = MEDIA_TYPE)
	@ApiOperation(value = "caricamento del documento di identità di un referente presente nel fascicolo dell'istanza")
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> uploadDocumentoReferente(@RequestPart("req") RequestAllegato req,
			@RequestPart("file") MultipartFile file) {
		LOGGER.info("Start upload");
		final StopWatch sw = LogUtil.startLog("upload");
		try {
		//	exceptionIfNoUserRoleMatch(PptrRole.RICHIEDENTE);
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			AllegatiDTO ret = allegatiService.uploadAllegatoReferente(req.getPraticaId(), req.getReferenteId(), file);
			return super.okResponse(ret);
		} catch (Exception e) {
			LOGGER.error("Error in upload", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@PostMapping(value = "/delete_documento_referente.pjson",  produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> deleteDocumentoReferente(@RequestBody RequestAllegato req) {
		LOGGER.info("Start delete_documento_referente");
		final StopWatch sw = LogUtil.startLog("delete_documento_referente");
		try {
		//	exceptionIfNoUserRoleMatch(PptrRole.RICHIEDENTE);
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			allegatiService.deleteDocumentoReferente(req.getPraticaId(), req.getReferenteId());
			return super.okResponse(0);
		} catch (Exception e) {
			LOGGER.error("Error in delete_documento_referente", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@PostMapping(value = "/get_documento_referente.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> getDocumentoReferente(@RequestBody RequestAllegato req) {
		LOGGER.info("Start get_documento");
		final StopWatch sw = LogUtil.startLog("get_documento");
		try {
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			AllegatiDTO ret = allegatiService.getDocumentoReferente(req.getPraticaId(), req.getReferenteId());
			return super.okResponse(ret);
		} catch (Exception e) {
			LOGGER.error("Error in get_documento", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@GetMapping(value = "/download.pjson", produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam String idAllegato,
			@RequestParam UUID idPratica,
			HttpServletResponse response) {
		LOGGER.info("Start download");
		final StopWatch sw = LogUtil.startLog("download");
		try {
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			super.checkDiMiaCompetenza(idPratica);
			CmsDownloadResponseBean cms = allegatiService.downloadAllegato(UUID.fromString(idAllegato));
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} catch (Exception e) {
			LOGGER.error("Error in download", e);
			response.setStatus(500);
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@GetMapping(value = "/downloadDocumentoIntegrazione.pjson", produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam("idPratica") UUID idPratica, 
			@RequestParam("idIntegrazione") Integer idIntegrazione, HttpServletResponse response) 
	{
		LOGGER.info("Start download");
		final StopWatch sw = LogUtil.startLog("download");
		try {
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			super.checkDiMiaCompetenza(idPratica);
			AllegatiSearch search = new AllegatiSearch();
			search.setIntegrazioneId(idIntegrazione);
			List<AllegatiDTO> tmp = allegatiService.getAllegati(idPratica, SezioneAllegati.INT_DOC, idIntegrazione);
			if(tmp == null || tmp.size() != 1)
				throw new Exception("Errore nel download. Mi aspettavo di trovare un documento e ne ho trovati " + tmp.size());
			CmsDownloadResponseBean cms = allegatiService.downloadAllegato(tmp.get(0).getId());
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} catch (Exception e) {
			LOGGER.error("Error in download", e);
			response.setStatus(500);
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	private void checkFirma(Integer idTIpoAllegato,UUID idPratica,MultipartFile file,Integer idIntegrazione) throws Exception {
		
		List<AllegatiDTO> domandaPreviews = 
				this.allegatiService.getAllegati(idPratica, 
						idTIpoAllegato==702?SezioneAllegati.INT_DOC_PREVIEW:SezioneAllegati.GENERA_STAMPA_PREVIEW, 
								idIntegrazione, idTIpoAllegato+50);
		if(domandaPreviews==null || domandaPreviews.size()!=1) {
			logger.error("Il file di Istanza autogenerato non è unico !!!");
			throw new CustomOperationToAdviceException("Errore interno!!!");
		}
		if(AllowedMimeType.isMimePdf(file.getContentType()))
		{
			//effettuo un check sulla firma
			//scarico l'originale...
			CmsDownloadResponseBean cmsBeanOriginale = allegatiService.downloadAllegato(domandaPreviews.get(0).getId());
			File fOrig=new File(cmsBeanOriginale.getFileName());
			Path fFirmato=Files.createTempFile(idTIpoAllegato.toString().concat("_").concat(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())),".PDF");
			try(InputStream  is = file.getInputStream();
				OutputStream os = new FileOutputStream(fFirmato.toFile()))
				{
					IOUtils.copy(is, os);
			}
		
			String error ="";
			try {
				String esitoCheck = padesService.checkOriginalAndSigned(pathCheckPades, fOrig, fFirmato.toFile());
				
				switch(esitoCheck) {
				case "OK":
					break;
				case "SIGNED_KO":
					throw new CustomOperationToAdviceException(error="Errore. La firma nel file firmato non è presente");
				case "NOT_SAMEFILE":
					throw new CustomOperationToAdviceException(error="Errore. Il file originale e il file firmato hanno contenuto diverso");
				default:
					throw new CustomOperationToAdviceException("Errore nella comparazione del file originale e firmato");
				}
			} catch (CustomOperationToAdviceException e) {
				LOGGER.error("Errore nella comparazione dei file in checkOriginalAndSigned", e);
				throw new CustomOperationToAdviceException(error);
			}
			catch (Exception e) {
				LOGGER.error("Error in checkOriginalAndSigned", e);
				throw new CustomOperationToAdviceException("La firma sul file non risulta valida!!!");
			}
		}
		else {
			//la scansione caricata non puo' avere dimensione al di sotto del PDF autogenerato 
			if(file.getSize()<domandaPreviews.get(0).getSize())
				throw new CustomOperationToAdviceException("Dimensione del file incompatibile !!");
		}
	}
	
	/**
	 * 
	 * @param file   documento da allegare alla pratica con relativi tipi contenuto nella req
	 * @return
	 */
	@Logging
	@PostMapping(value = "/upload_allegato.pjson", consumes = {"multipart/form-data" }, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> uploadDocumento(@RequestPart("req") RequestAllegato req, 
																		@RequestPart("file") MultipartFile file)
	{
		try {
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			checkDiMiaCompetenza(UUID.fromString(req.getPraticaId()));
			if(req.getTipiContenuto()!=null && 
				req.getTipiContenuto().size()>0 &&
				req.getIsSigned()!=null && req.getIsSigned()==true) {
				if(req.getTipiContenuto().contains(700))
				{
				//controlli su istanza 
				this.checkFirma(700, UUID.fromString(req.getPraticaId()),file,null);
				}
				if(req.getTipiContenuto().contains(701))
				{
				//controlli su scheda tecnica 
				this.checkFirma(701, UUID.fromString(req.getPraticaId()),file,null);
				}
				if(req.getTipiContenuto().contains(702))
				{
				//controlli su riepilogo integrazione
				this.checkFirma(702, UUID.fromString(req.getPraticaId()),file,req.getIntegrazioneId());
				}
			}
			AllegatiDTO ret = allegatiService.uploadAllegato(req.getPraticaId(),req.getTipiContenuto(),req.getNomeContenuto(), req.getIntegrazioneId(), file, req.getIsSigned());
			return super.okResponse(ret);
		} catch (Exception e) 
		{
			LOGGER.error("Errore nell'upload del del documento", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	@Autowired
	private FascicoloService fascicoloSvc;
	
	@Logging
	@PostMapping(value = "/delete_allegato.pjson",  produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> deleteAllegato(@RequestBody RequestAllegato req) {
		try {
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			PraticaDTO praticaDTO = checkDiMiaCompetenza(UUID.fromString(req.getPraticaId()));
			allegatiService.deleteAllegato(req.getPraticaId(), req.getAllegatoId(),req.getIntegrazioneId());
			if(req.getInvalidaSezione()!=null && req.getInvalidaSezione()==true) {
				this.fascicoloSvc.updateValidation(praticaDTO.getId(), null, null, false);	
			}
			return super.okResponse(0);
		} catch (Exception e) {
			LOGGER.error("Error in delete_allegato", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = {"/calcola-hash"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Calcolo impronta hash files", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<String>> calcolaHash(
			@ApiParam("Allegato da caricare, dimensione massima ammessa 50MB")
			@RequestPart(required = true) MultipartFile file) throws Exception {
		LOGGER.info("Chiamato il controller: 'allegato'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StopWatch sw = LogUtil.startLog("calcolaHash");
		try {
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			if (file == null || file.isEmpty()) {
				LOGGER.error("Allegato vuoto !");
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