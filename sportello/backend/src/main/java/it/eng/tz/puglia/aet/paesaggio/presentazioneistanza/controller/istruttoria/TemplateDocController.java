package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.TemplateDocSezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search.TemplateDocSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.service.ITemplateDocService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping(value = "istruttoria/templateDoc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TemplateDocController extends RoleAwareController {

	private static final Logger log = LoggerFactory.getLogger(TemplateDocController.class);

	@Autowired private UserUtil userUtil;
	@Autowired private ITemplateDocService templateDocService;
	@Autowired private CreaReportService creaReportService;
	@Autowired private AllegatoService allegatiService;
	
	
	@GetMapping(value = "/info")
	public ResponseEntity<BaseRestResponse<TemplateDocDTO>> info(String nome) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		return super.okResponse(templateDocService.info(idOrganizzazione,nome,false));
	}
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<BaseRestResponse<List<TemplateDocDTO>>> getAll() throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		TemplateDocSearch src=new TemplateDocSearch();
		src.setIdOrganizzazione(idOrganizzazione+"");
		return super.okResponse(templateDocService.getAll(src));
	}	
	
	@GetMapping(value = "/getAllPaginated")
	public ResponseEntity<BaseRestResponse<PaginatedList<TemplateDocDTO>>> getAllPaginated(TemplateDocSearch filtri) throws Exception{
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		filtri.setIdOrganizzazione(idOrganizzazione+"");
		templateDocService.sincronizza(filtri);
		return super.okResponse(templateDocService.getAllPaginated(filtri));
	}
	
	
	@Logging
	@GetMapping(value = "/downloadLogo", produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam String idAllegato,
			HttpServletResponse response) throws CustomOperationToAdviceException
	{
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		try
		{
//			InputStreamResource resource;
			CmsDownloadResponseBean cms = allegatiService.downloadAllegatoGenerico(UUID.fromString(idAllegato));
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
//			InputStream is = new FileInputStream(f);
//			HttpHeaders headers = handleResouces(cms, f.length());
//			resource = new InputStreamResource(is);
//			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
		} catch (Exception e)
		{
			log.error("Error in download", e);
			response.setStatus(500);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	@PostMapping(value = "/salva")
	public ResponseEntity<BaseRestResponse<Boolean>> salva(@RequestBody(required=true) TemplateDocDTO body) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		if(body.getIdOrganizzazione()!=idOrganizzazione)
			throw new CustomOperationToAdviceException("Organizzazione errata per il template da aggiornare!");
		return super.okResponse(templateDocService.salva(body));
	}
	
	@PostMapping(value = "/reset")			// Get se autoSave=false o Post se autoSave=true
	public ResponseEntity<BaseRestResponse<TemplateDocDTO>> resetTemplate(@RequestParam(name="nome", required=true) String nome) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		TemplateDocDTO toReset=new TemplateDocDTO();
		toReset.setIdOrganizzazione(idOrganizzazione);
		toReset.setNome(nome);
		toReset=templateDocService.find(toReset);
		return super.okResponse(templateDocService.reset(toReset));
	}

	@PostMapping(value = "/carica", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> caricaImage(@RequestPart(name = "sezione",  required = true) TemplateDocSezioniDTO sezione,
																     @RequestPart(required = true) MultipartFile file) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		if(sezione.getIdOrganizzazione()!=idOrganizzazione)
			throw new CustomOperationToAdviceException("Organizzazione errata per la sezione da aggiornare!");
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new CustomOperationToAdviceException("Allegato vuoto o non trovato!");
		}
		else {
			return super.okResponse(templateDocService.caricaImage(sezione,file));
		}
	}
	
	@DeleteMapping(value = "/eliminaImage")
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegato(@RequestParam(name = "idAllegato", required = true) UUID idAllegato) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		templateDocService.deleteRifAllegato(idOrganizzazione,idAllegato);
		return super.okResponse(true);
	}
	
	@PostMapping(value = "/downloadAnteprima", produces = "application/pdf")
	public void downloadJasper(HttpServletResponse response, @RequestParam(name = "nome", required = true) String nome) throws Exception {
		log.info("Chiamato il controller: 'templateDoc'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		JasperPrint jasper = null;
		try {
			// apro output stream dalla response
			OutputStream out = response.getOutputStream();

			// creo il pdf del jasper report
			//da completare quando riportiamo la generazione del report.... (acolaianni 07.10.2020)
			jasper = creaReportService.creaPdf_DemoTemplate(idOrganizzazione,nome); // abbiamo tutto internamente  

			// setto alcune informazioni per l'header
			response.setContentType("application/pdf");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "(Anteprima)_"+nome+".pdf" + "\"");
			response.setHeader("file-name", nome);

			// esporto il il pdf e lo incapsulo nell'header
			JasperExportManager.exportReportToPdfStream(jasper, out);

			// chiudo lo stream
			out.flush();
			out.close();
		} 
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
//	private HttpHeaders handleResouces(CmsDownloadResponseBean cms, Long length)
//	{
//		HttpHeaders headers = new HttpHeaders();
//		Path filePath = Paths.get(cms.getFilePathName());
//		int nameCount=filePath.getNameCount();
//		Path fileName = filePath.getName(nameCount-1);
//		headers.setContentDisposition(ContentDisposition.builder("inline").filename(fileName.toString()).build());
//		headers.setContentLength(length);
//		try
//		{
//			headers.setContentType(MediaType.parseMediaType(cms.getContentType()));
//		} catch (Exception e)
//		{
//			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		}
//		return headers;
//	}
	
}