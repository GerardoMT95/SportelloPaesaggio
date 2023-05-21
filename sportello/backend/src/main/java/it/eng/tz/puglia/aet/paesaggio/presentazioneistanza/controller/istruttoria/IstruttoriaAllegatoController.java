package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.RequestAllegato;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CheckSumUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@RequestMapping(value = "/istruttoria/allegati", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IstruttoriaAllegatoController extends RoleAwareController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(IstruttoriaAllegatoController.class);

	@Qualifier("allegatiIstruttoria")
	@Autowired
	private AllegatoService allegatiService;

	@Logging
	@PostMapping(value = "/get_documento_referente.pjson", produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> getDocumentoReferente(@RequestBody RequestAllegato req)
	{
		try
		{
			super.checkDiMiaCompetenza(UUID.fromString(req.getPraticaId()));
			AllegatiDTO ret = allegatiService.getDocumentoReferente(req.getPraticaId(), req.getReferenteId());
			return super.okResponse(ret);
		} catch (Exception e)
		{
			LOGGER.error("Error in get_documento", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}

	@Logging
	@GetMapping(value = "/download.pjson", produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam String idAllegato,
			@RequestParam UUID idPratica,
			HttpServletResponse response)
	{
		LOGGER.info("Start download");
		try
		{
			super.checkDiMiaCompetenza(idPratica);
			CmsDownloadResponseBean cms = allegatiService.downloadAllegato(UUID.fromString(idAllegato));
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} catch (Exception e)
		{
			LOGGER.error("Error in download", e);
			response.setStatus(500);
		}
	}

	@Logging
	@GetMapping(value = "/downloadDocumentoIntegrazione.pjson", produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam("idPratica") UUID idPratica,
			@RequestParam("idIntegrazione") Integer idIntegrazione, HttpServletResponse response)
	{
		LOGGER.info("Start download integrazione documentale");
		try
		{
			super.checkDiMiaCompetenza(idPratica);
			AllegatiSearch search = new AllegatiSearch();
			search.setIntegrazioneId(idIntegrazione);
			List<AllegatiDTO> tmp = allegatiService.getAllegati(idPratica, SezioneAllegati.INT_DOC, idIntegrazione);
			if (tmp == null || tmp.size() != 1)
				throw new Exception("Errore nel download. Mi aspettavo di trovare un documento e ne ho trovati " + tmp.size());
			CmsDownloadResponseBean cms = allegatiService.downloadAllegato(tmp.get(0).getId());
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} catch (Exception e)
		{
			LOGGER.error("Error in download", e);
			response.setStatus(500);
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
			//in teoria non serve alcun ruolo ma solo l'autenticazione...
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
