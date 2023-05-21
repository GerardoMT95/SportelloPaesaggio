package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ProvvedimentoFinaleDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ProvvedimentoFinaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;

@RestController
@RequestMapping("/istruttoria/provvedimento")
public class ProvvedimentoFinaleController extends RoleAwareController
{	
	private final String FIND 	   	= "/find";
	private final String INSERT	   	= "/insert";
	private final String UPDATE 	= "/update";
	private final String UPLOAD	   	= "/upload";
	private final String REMOVE	   	= "/remove";
	private final String SAVE_DEST  = "/saveDestinatari";
	private final String FIND_DEST  = "/findDestinatariFissi";
	private final String TRASMETTI  = "/trasmettiProvvedimento";
	private final String GENERA_PDF	= "/generaPdf";
	private final String DOWNLOAD_RICEVUTA_TRASMISSIONE ="/getRicevutaTrasmissione";
	
	@Autowired private ProvvedimentoFinaleService service;
//	@Autowired private ISitPugliaConfiguration configuration;
	@Autowired ApplicationProperties props;
	@Autowired CommonRepository commonDao;
	@Autowired private UserUtil userUtil;
	@Autowired private AllegatoService allService;
	
	
	@Logging
	@GetMapping(value=FIND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ProvvedimentoFinaleDetailsDTO>> find(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		return okResponse(service.find(idPratica));
	}
	
	@Logging
	@PostMapping(value=INSERT, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ProvvedimentoFinaleDTO>> insert(@RequestBody ProvvedimentoFinaleDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		return okResponse(service.saveProvvedimento(entity));
	}
	
	@Logging
	@PutMapping(value=UPDATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ProvvedimentoFinaleDTO>> update(@RequestBody ProvvedimentoFinaleDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		return okResponse(service.updateProvvedimento(entity));
	}
	
	@Logging
	@PostMapping(value=UPLOAD, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> upload(@RequestPart("file")MultipartFile file, @RequestParam("type")Integer type, @RequestParam("idPratica")UUID idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		return okResponse(service.uploadAllegatoProvvedimento(file, Collections.singletonList(type), idPratica));
	}
	
	@Logging
	@DeleteMapping(value=REMOVE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> remove(@RequestParam("idAllegato")UUID idAllegato) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		service.deleteAllegatoProvvedimento(idAllegato);
		return okResponse(true);
	}
	
	@Logging
	@PostMapping(value=SAVE_DEST, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<DestinatarioDTO>>> creaComunicazione(@RequestBody List<DestinatarioDTO> ulterioriDestinatari, @RequestParam("idPratica")UUID idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		return okResponse(service.insertUlterioriDestinatari(ulterioriDestinatari, idPratica));
	}
	
	@Logging
	@GetMapping(value=FIND_DEST, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<DestinatarioDTO>>> findDestinatariFissi(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		return okResponse(service.findDestinatariFissi(idPratica, userUtil.getIntegerId()));
	}
	
	@Logging
	@PutMapping(value=TRASMETTI, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ProvvedimentoFinaleDetailsDTO>> trasmetti(@RequestBody(required=false) List<DestinatarioDTO> ulterioriDestinatari, 
																					 @RequestParam("idPratica")UUID idPratica,
																					 HttpServletRequest request) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		String prefixUrl = this.commonDao.getConfigurationValue(ComunicazioniService.KEY_URL_DOWNLOAD_PREFIX,props.getCodiceApplicazione());
		return okResponse(service.trasmettiProvvedimento(ulterioriDestinatari, idPratica, prefixUrl));
	}
	
	@Logging
	@GetMapping(value=GENERA_PDF, produces=MEDIA_TYPE)
	public void generaPdf(@RequestParam("idPratica")UUID idPratica,
		  				  HttpServletResponse response) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_);
		try
		{
			//File f = reportService.creaPdf_DocumentoTrasmissione(idPratica, null, null);
			File f=service.generaAnteprimaPDFRicevutaTrasmissione(idPratica, null);
		 	copyToResponse(response, f, "ricevutaTrasmissione.pdf", "application/pdf");
		}
		catch(Exception e)
		{
			logger.error("Errore nella generazione del documento di trasmissione");
			response.setStatus(500);
		}
	}
	
	@Logging
	@GetMapping(value=DOWNLOAD_RICEVUTA_TRASMISSIONE, produces=MEDIA_TYPE)
	public void getRicevutaTrasmnissione(@RequestParam("idPratica")UUID idPratica,
		  				  HttpServletResponse response) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_,Gruppi.ETI_,Gruppi.USER_CL);
		try
		{
			List<AllegatiDTO> all = service.findDocumentiTrasmissione(idPratica);
			logger.info("{} allegati trovati:  {}", all.size(), all);
			AllegatiDTO allegato = all.stream().filter(p -> p.getType().equals("1101") || p.getTipiContenuto().contains("1101")).findFirst().get();
			CmsDownloadResponseBean cms = this.allService.downloadAllegatoGenerico(allegato.getId());
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		}
		catch(Exception e)
		{
			logger.error("Errore nel fetch della ricevuta di trasmissione");
			response.setStatus(500);
		}
	}
}
