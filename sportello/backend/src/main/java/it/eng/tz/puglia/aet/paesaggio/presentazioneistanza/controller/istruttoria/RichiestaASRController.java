package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RichiestaAsrDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SospensioneArchiviazioneAttivazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.CorrispondenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SospensioneArchiviazioneAttivazioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.RichiestaASRService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@RequestMapping("/istruttoria/richiestaASR")
public class RichiestaASRController extends RoleAwareController
{
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String SEARCH 	= "/search";
	private final String UPDATE 	= "/update";
	private final String DELETE     = "/delete";
	private final String COUNT		= "/count";
	private final String FIND_DRAFT = "/findDraftOrCreate";
	
	private final String UPLOAD		= "/upload";
	private final String REMOVE		= "/remove";
	
	@Autowired private RichiestaASRService service;
	@Autowired private ComunicazioniService comunicazioneSrvice;
	
	@Qualifier("allegatiIstruttoria")
	@Autowired 
	private AllegatoService allegatiService;
	
	@Autowired
	private IstrPraticaService istrPraticaSvc;
	
	
	private boolean isRup(UUID idPratica) throws Exception {
		PraticaSearch searchP=new PraticaSearch();
		searchP.setId(idPratica);
		PaginatedList<FascicoloDto> pratiche = istrPraticaSvc.search_istr(searchP);
		if(pratiche.getCount()==0) {
			//non ha accesso alla pratica
			throw new CustomOperationToAdviceException("Pratica non trovata!");
		}
		return this.isRupForPratica(pratiche.getList().get(0).getUsernameRup());
	}
	
	@Logging
	@GetMapping(value=FIND_DRAFT, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<RichiestaAsrDTO>> findDraft(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		RichiestaAsrDTO result;
		this.isRup(idPratica);
		SospensioneArchiviazioneAttivazioneSearch search = new SospensioneArchiviazioneAttivazioneSearch();
		search.setIdPratica(idPratica);
		search.setDraft(true);
		Long nInDraft = service.count(search);
		if(nInDraft == 0)
		{
			logger.info("Non esiste alcuna richiesta in bozza per questa pratica, ne creo una");
			result = new RichiestaAsrDTO(idPratica, LogUtil.getUser());
			result = new RichiestaAsrDTO(service.insert(result));
		}
		else
			result = service.findActiveDraft(idPratica);
		return okResponse(result);
	}
	
	@Logging
	@GetMapping(value=SEARCH, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PaginatedList<SospensioneArchiviazioneAttivazioneDTO>>> search(SospensioneArchiviazioneAttivazioneSearch search) throws Exception
	{
		this.isRup(search.getIdPratica());
		return okResponse(service.search(search));
	}
	
	@Logging
	@GetMapping(value=COUNT, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Long>> count(SospensioneArchiviazioneAttivazioneSearch search) throws Exception
	{
		//TODO inseire filtro per contare solo pratiche per cui l'utente loggato è rup
		return okResponse(service.count(search));
	}
	
	@Logging
	@PutMapping(value=UPDATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<SospensioneArchiviazioneAttivazioneDTO>> update(@RequestBody SospensioneArchiviazioneAttivazioneDTO entity) throws Exception
	{
		this.isRup(entity.getIdPratica());
		return okResponse(service.update(entity));
	}
	
	@Logging
	@DeleteMapping(value=DELETE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestParam("idRichiesta")Long id) throws Exception
	{
		return okResponse(service.delete(id));
	}
	
	@Logging
	@PostMapping(value=UPLOAD, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> upload(@RequestPart("file")MultipartFile file,
																@RequestParam("idRichiesta")Long id,
																@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		service.verifyPermissions(id, idPratica);
		AllegatiDTO result = allegatiService.uploadAllegatoASR(idPratica.toString(), Collections.singletonList(800), file.getOriginalFilename(), file, id);
		return okResponse(result);
	}
	
	@Logging
	@DeleteMapping(value=REMOVE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> removeFile(@RequestParam("idAllegato")UUID idAllegato,
																@RequestParam("idRichiesta")Long idRichiesta,
																@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		service.verifyPermissions(idRichiesta, idPratica);
		allegatiService.deleteAllegato(idPratica.toString(), idAllegato.toString());
		return okResponse(true);
	}
	
	@Logging
	@PostMapping(value = "/request")
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> searchTemplateRichiesta(@RequestParam("richiestaArchiviazione") Boolean richiesta, @RequestParam("idPratica") UUID idPratica) throws Exception 
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_);
		String codice = "REQ_ATT";
		if(richiesta != null && richiesta)
			codice = "REQ_ARC";
		
		CorrispondenzaSearch search = new CorrispondenzaSearch();
		search.setBozza(true);
		search.setIdPratica(idPratica);
		search.setCodiceTemplate(codice);
		DettaglioCorrispondenzaDTO comunicazione = null;
		List<DettaglioCorrispondenzaDTO> payload = comunicazioneSrvice.search(search).getList();
		if(payload != null && !payload.isEmpty())
			comunicazione = payload.get(0);
		else
			comunicazione = service.prepareTemplateRichiesta(idPratica, codice);
		return super.okResponse(comunicazione);
	}
	
}
