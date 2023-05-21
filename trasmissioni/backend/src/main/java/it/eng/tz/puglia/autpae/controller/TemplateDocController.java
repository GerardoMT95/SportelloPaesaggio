package it.eng.tz.puglia.autpae.controller;

import java.io.File;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.TipologicaNumeriDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;
import it.eng.tz.puglia.autpae.entity.TemplateDocSezioneDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.search.TemplateDocSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDocService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.util.log.LogUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping(value = "templateDoc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TemplateDocController extends _RestController {
	private static final Logger log = LoggerFactory.getLogger(TemplateDocController.class);

	@Autowired
	private TemplateDocService templateDocService;
	@Autowired
	private GruppiRuoliService gruppiRuoliService;
	@Autowired
	private CreaReportService creaReportService;
	@Autowired
	private AllegatoService allegatoService;

	@GetMapping(value = "/info")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<TemplateDocDTO>> info(String nome) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateDocService.info(nome));
	}

	@GetMapping(value = "/getAll")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TemplateDocDTO>>> getAll() throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateDocService.getAll());
	}

	@GetMapping(value = "/getAllPaginated")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<TemplateDocDTO>>> getAllPaginated(TemplateDocSearch filtri)
			throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateDocService.getAllPaginated(filtri));
	}

	@PostMapping(value = "/salva")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> salva(@RequestBody(required = true) TemplateDocDTO body)
			throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateDocService.salva(body));
	}

	@PostMapping(value = "/reset") // Get se autoSave=false o Post se autoSave=true
	@LoggingAet
	public ResponseEntity<BaseRestResponse<TemplateDocDTO>> resetEmail(
			@RequestParam(name = "nome", required = true) String nome) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateDocService.reset(nome));
	}

	@PostMapping(value = "/carica", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AllegatoDTO>> caricaImage(
			@RequestPart(name = "sezione", required = true) TemplateDocSezioneDTO sezione,
			@RequestPart(required = true) MultipartFile file) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new CustomOperationToAdviceException("Allegato vuoto o non trovato!");
		} else {
			return super.okResponse(templateDocService.caricaImage(sezione, file));
		}
	}

	@DeleteMapping(value = "/eliminaImage")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegato(
			@RequestParam(name = "idAllegato", required = true) long idAllegato) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		if (idAllegato < 1)
			throw new Exception("Id allegato errato!");
		templateDocService.deleteRifAllegato(idAllegato);
		return super.okResponse(true);
	}

	@PostMapping(value = "/download_Anteprima", produces = "application/pdf")
	@LoggingAet
	public void downloadJasper(HttpServletResponse response, @RequestParam(name = "nome", required = true) String nome)
			throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		JasperPrint jasper = null;
		try {
			// apro output stream dalla response
			OutputStream out = response.getOutputStream();

			// creo il pdf del jasper report
			jasper = creaReportService.creaPdf_DemoTemplate(nome); // abbiamo tutto internamente

			// setto alcune informazioni per l'header
			response.setContentType("application/pdf");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + "(Anteprima)_" + nome + ".pdf" + "\"");
			response.setHeader("file-name", nome);

			// esporto il il pdf e lo incapsulo nell'header
			JasperExportManager.exportReportToPdfStream(jasper, out);

			// chiudo lo stream
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/downloadImage")
	public void download(@RequestParam(name = "idAllegato", required = true) Long idAllegato,
			HttpServletResponse response) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		StopWatch sw = LogUtil.startLog("downloadImage");
		try {
			try {
				File f = allegatoService.downloadFile(null, idAllegato, null, null);
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

}