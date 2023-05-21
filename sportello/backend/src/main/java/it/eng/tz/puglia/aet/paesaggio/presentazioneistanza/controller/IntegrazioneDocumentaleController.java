package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.IntegrazioneDocumentaleExtendedDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.IntegrazioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;

@RestController
@RequestMapping(value = IntegrazioneDocumentaleController.CONTROLLER_PATH)
public class IntegrazioneDocumentaleController extends RoleAwareController
{
	final static String CONTROLLER_PATH = "/IntegrazioneDocumentale";
	private final String SAVE 	= "/save";
	private final String UPDATE = "/update";
	private final String DELETE = "/delete";
	private final String SEARCH = "/search";
	private final String FIND	= "/find";
	private final String FIND_ACTIVE = "/findActive";
	private final String FIND_ATTACHMENTS = "/findAttachments";
	private final String UPDATE_STATUS = "/updateStatus";
	private final String DOWNLOAD = "/download";
	
	@Autowired private IntegrazioneDocumentale service;
	@Autowired private AllegatoService allegatiService;
	@Autowired private FascicoloService fascicoloService;
	
	@Logging
	@PostMapping(value=SAVE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<IntegrazioneDTO>> save(@RequestBody IntegrazioneDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(entity.getPraticaId());
		return okResponse(service.save(entity,true));
	}
	
	@Logging
	@PutMapping(value=UPDATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> update(@RequestBody IntegrazioneDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(entity.getPraticaId());
		int n = service.update(entity);
		return okResponse(n == 1);
	}
	
	@Logging
	@PutMapping(value=UPDATE_STATUS, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> updateStatus(@RequestParam("idIntegrazione")Integer idIntegrazione,
																  @RequestParam("idPratica")UUID idPratica,
																  @RequestParam("stato")StatoIntegrazioneDocumentale stato) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(idPratica);
		return okResponse(service.updateStatus(idIntegrazione, stato));
	}
	
	@Logging
	@DeleteMapping(value=DELETE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestParam("idPratica")UUID idPratica, @RequestParam("idIntegrazione")Integer id) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(idPratica);
		IntegrazioneSearch search = new IntegrazioneSearch();
		search.setId(id);
		search.setPraticaId(idPratica);
		return okResponse(service.delete(search));
	}
	
	@Logging
	@GetMapping(value=SEARCH, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<IntegrazioneDTO>>> search(IntegrazioneSearch filters) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(filters.getPraticaId());
		return okResponse(service.search(filters));
	}
	
	@Logging
	@GetMapping(value=FIND_ACTIVE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<IntegrazioneDTO>> find(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(idPratica);
		return okResponse(service.findActiveIntegration(idPratica));
	}
	
	@Logging
	@GetMapping(value=FIND_ATTACHMENTS, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDto>> getAllegatiIntegrazione(@RequestParam("idPratica")UUID idPratica, 
																				 @RequestParam("idIntegrazione")Integer idIntegrazione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		checkDiMiaCompetenza(idPratica);
		return okResponse(service.getAllegatiIntegrazione(idPratica, idIntegrazione));
	}
	
	@Logging
	@GetMapping(value = FIND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<IntegrazioneDocumentaleExtendedDto>> find(@RequestParam("idIntegrazione")Integer idIntegrazione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.RICHIEDENTI);
		IntegrazioneSearch search = new IntegrazioneSearch();
		search.setId(idIntegrazione);
		List<IntegrazioneDTO> tmp = service.search(search);
		if(tmp == null || tmp.size() != 1)
		{
			logger.error("Errore nella ricerca dell'integrazione con id {}. Trovati {} risultati", idIntegrazione, tmp == null ? "0" : tmp.size());
			throw new Exception("Errore nella ricerca dell'integrazione con id " + idIntegrazione + ". Trovati " + tmp == null ? "0" : tmp.size() + "risultati");
		}
		IntegrazioneDocumentaleExtendedDto dto = new IntegrazioneDocumentaleExtendedDto(tmp.get(0));
		checkDiMiaCompetenza(dto.getPraticaId());
		dto.setAllegati(service.getAllegatiIntegrazione(dto.getPraticaId(), dto.getId()));
		dto.setDocumentoIntegrazione(service.getMetadatiDocumentoIntegrazione(dto.getPraticaId(), dto.getId()));
		return okResponse(dto);
	}
	
	@Logging
	@GetMapping(value=DOWNLOAD, produces=MEDIA_TYPE)
	public void downloadDocumentoIntegrazione(@RequestParam("idIntegrazione")Integer idIntegrazione,
																			 @RequestParam("idPratica")UUID idPratica,
																			 @RequestParam("codicePratica")String codicePratica,
																			 HttpServletResponse response) throws Exception
	{
		final String messaggio="File autogenerato non trovato ... tornare a Compila domanda e riffettuare il genera e stampa !!!";
		try 
		{
			checkAbilitazioneFor(Ruoli.RICHIEDENTE);
			checkDiMiaCompetenza(idPratica);
			PraticaSearch praticaS=new PraticaSearch();
			praticaS.setCodicePraticaAppptr(codicePratica);
			FascicoloDto fascicolo = fascicoloService.find(praticaS);
			List<AllegatiDTO> allegati = allegatiService.getAllegati(fascicolo.getId(), SezioneAllegati.INT_DOC_PREVIEW,idIntegrazione,752);
			if(allegati==null || allegati.size()==0) {
				throw new CustomOperationToAdviceException(messaggio);
			}
			CmsDownloadResponseBean cms =allegatiService.downloadAllegato(allegati.get(0).getId());
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato nel CMS...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} 
		catch (Exception e) 
		{
			logger.error("Error in download", e);
			response.setStatus(500);
		}
	}
																			
}
