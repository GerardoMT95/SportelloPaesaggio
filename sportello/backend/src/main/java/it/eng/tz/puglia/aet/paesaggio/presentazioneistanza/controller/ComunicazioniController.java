package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipologicaAllegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.CorrispondenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.SezioneIstruttoria;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.util.log.LogUtil;

@RequestMapping("comunicazioni")
@RestController
public class ComunicazioniController extends RoleAwareController
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String SEARCH = "/search";
	private final String CREATE = "/create";
	private final String UPDATE = "/update/{idPratica}";
	private final String DELETE = "/delete";
	private final String UPLOAD = "/upload";
	private final String UPLOAD_AND_GET_DETAIL="/uploadAndGetDetail";
	private final String REMOVE = "/remove";
	private final String DOWNLOAD_ALLEGATO = "/download";
	private final String DOWNLOAD_RICEVUTA = "/download_ricevuta";
	private final String SEND	= "/send";
	private final String FIND	= "/find";
	private final String ANTEPRIMA = "/anteprima";
	
	@Autowired private ComunicazioniService service;
	@Autowired private UserUtil userUtil;
	@Autowired private IPraticaService praticaSvc;
	@Autowired private IstrPraticaService istrPraticaSvc;
	@Autowired private AllegatoService allegatiService;
	
	
	
	private PraticaDTO checkPermessi(UUID idPratica,CorrispondenzaSearch search) throws Exception {
		super.checkAbilitazioneFor(Gruppi.values());
		Gruppi gruppo = userUtil.getGruppo();
		int idOrganizzazione=-1;
		try{
			idOrganizzazione=userUtil.getIntegerId();
		}catch(Exception e) {}
		PraticaDTO praticaDTO=new PraticaDTO();
		praticaDTO.setId(idPratica);
		praticaDTO=praticaSvc.find(praticaDTO);
		if (search.isSolaLettura()) { //Con flag di rigera isSolaLettura a true...
			//...Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
			this.checkDiMiaCompetenzaInLettura(praticaDTO);
		}else {
			if(gruppo.equals(Gruppi.RICHIEDENTI) && !praticaDTO.diMiaCompetenza()) {
				throw new CustomOperationToAdviceException("Pratica non di competenza!!");
			}
		}
		//escludo le bozze non della mia organizzazione...
		if(idOrganizzazione>0) {
			search.setEscludiBozzeOrganizzazione(idOrganizzazione+"");
		}
		search.setEscludiBozzeTipoOrganizzazione(gruppo.name());
		if(gruppo.equals(Gruppi.RICHIEDENTI)) {
			search.setRiservata("false");//solo le comunicazioni non riservate
			//fine controlli richiedente....
			return praticaDTO;
		}
		//controlli lato istruttoria
		PraticaSearch pSearch=new PraticaSearch();
		pSearch.setId(idPratica);
		PaginatedList<FascicoloDto> ret = istrPraticaSvc.search_istr(pSearch);
		if (ret.getCount()==0) {
			//non mi spetta....
			throw new CustomOperationToAdviceException("Pratica non di competenza!!");
		}
		//escludo le bozze non della mia organizzazione...
		if(idOrganizzazione>0) {
			search.setEscludiBozzeOrganizzazione(idOrganizzazione+"");
		}
		search.setEscludiBozzeTipoOrganizzazione(gruppo.name());
		return praticaDTO;
	}
	
	
	
	@Logging
	@GetMapping(value=SEARCH, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PaginatedList<DettaglioCorrispondenzaDTO>>> search(CorrispondenzaSearch search) throws Exception
	{
		if(search.getIdPratica()==null) {
			throw new CustomOperationToAdviceException("Pratica non impostata.");
		}
		//Set del flag per sola lettura (caso di altri referenti che possono solo visualizzare)
		search.setSolaLettura(true);
		checkPermessi(search.getIdPratica(), search);
		return okResponse(service.search(search));
	}
	
	@Logging
	@GetMapping(value=FIND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> find(@RequestParam("idPratica")UUID idPratica,
								@RequestParam("idComunicazione")Long idComunicazione) throws Exception
	{
		checkPermessi(idPratica, new CorrispondenzaSearch());
		return okResponse(service.find(idComunicazione));
	}
	
	@Logging
	@PostMapping(value=CREATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> create(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		Gruppi gruppo = userUtil.getGruppo();
		Integer idOrganizzazione=null;
		try {idOrganizzazione=userUtil.getIntegerId();}catch(Exception e) {}
		PraticaDTO praticaDTO=checkPermessi(idPratica, new CorrispondenzaSearch());
		if(gruppo==Gruppi.RICHIEDENTI) {
			idOrganizzazione=Integer.parseInt(praticaDTO.getEnteDelegato());	
		}
		return okResponse(service.create(idPratica,idOrganizzazione,gruppo,null,true));
	}
	
	@Logging
	@PutMapping(value=UPDATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> update(@PathVariable(name = "idPratica") UUID idPratica, 
																				@RequestBody DettaglioCorrispondenzaDTO body) throws Exception
	{
		checkPermessi(idPratica, new CorrispondenzaSearch());
		return okResponse(service.saveComunication(body, idPratica));
	}

	@Logging
	@DeleteMapping(value=DELETE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> delete(@RequestParam("idPratica")UUID idPratica, 
															@RequestParam("idCorrispondenza")Long idCorrispondenza) throws Exception
	{
		checkPermessi(idPratica, new CorrispondenzaSearch());
		return okResponse(service.eraseComunication(idCorrispondenza) != 0);
	}
	
	@Logging
	@PostMapping(value=UPLOAD, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<TipologicaAllegatoDTO>> upload(@RequestParam("idPratica")UUID idPratica,
																	  @RequestParam("idCorrispondenza")Long idCorrispondenza,
																	  @RequestPart("file")MultipartFile attachment) throws Exception
	{
		checkPermessi(idPratica, new CorrispondenzaSearch());
			return okResponse(service.addAttachment(idPratica, idCorrispondenza, attachment));	
	}
	
	@Logging
	@PostMapping(value=UPLOAD_AND_GET_DETAIL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> uploadAndGetDetail(@RequestParam("idPratica")UUID idPratica,
																	  @RequestParam("idCorrispondenza")Long idCorrispondenza,
																	  @RequestPart("file")MultipartFile attachment) throws Exception
	{
		checkPermessi(idPratica, new CorrispondenzaSearch());
			return okResponse(service.addAttachmentAndGetDetail(idPratica, idCorrispondenza, attachment));	
	}
	
	@Logging
	@DeleteMapping(value=REMOVE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> removeAttachment(@RequestParam("idAllegato")UUID idAllegato,
																	  @RequestParam("idPratica")UUID idPratica) throws Exception
	{
		checkPermessi(idPratica, new CorrispondenzaSearch());
		return okResponse(service.removeAttachment(idPratica, idAllegato) != 0);
	}
	
	@Logging
	@PostMapping(value=SEND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> sendData(@RequestParam("idCorrispondenza")Long idCorrispondenza,
															  @RequestParam("idPratica")UUID idPratica,
															  @RequestParam("withProto")boolean withProto) throws Exception
	{
		PraticaDTO praticaDto = checkPermessi(idPratica, new CorrispondenzaSearch());
		DettaglioCorrispondenzaDTO corr=this.service.find(idCorrispondenza);
		if(corr.getCorrispondenza().getCodiceTemplate()!=null && 
				corr.getCorrispondenza().getCodiceTemplate().equals(SezioneIstruttoria.RICH_INTEGRAZIONE.name()))
		{
			//controllo che sia compatibile con lo stato pratica.
			if(!AttivitaDaEspletare.getStatiIntegrazioneAmmessa(true).contains(praticaDto.getStato())) {
				throw new CustomOperationToAdviceException("Integrazione non ammessa, stato pratica avanzato...");
			}
		}
		service.sendComunication(idCorrispondenza,idPratica,withProto);
		return okResponse(true);
	}	
	
	
	@GetMapping(value = DOWNLOAD_ALLEGATO, produces = MEDIA_TYPE)
	public void downloadAttachment(@RequestParam String idAllegato,@RequestParam UUID idPratica,
			HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("download");
		checkPermessi(idPratica, new CorrispondenzaSearch());
//		InputStreamResource resource;
		try {
			CmsDownloadResponseBean cms = allegatiService.downloadAllegato(UUID.fromString(idAllegato));
			if (cms == null || cms.getFileName() == null)
				throw new CustomOperationToAdviceException("Nessun file trovato...");
			File f = new File(cms.getFileName());
			this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
//			InputStream is = new FileInputStream(f);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentDisposition(ContentDisposition.builder("inline").build());
//			headers.setContentLength(f.length());
//			try {
//				headers.setContentType(MediaType.parseMediaType(cms.getContentType()));
//			} catch (Exception e) {
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//			}
//			resource = new InputStreamResource(is);
//			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
		} catch (Exception e) {
			logger.error("Errore in downoad file " ,e);
			response.setStatus(500);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@GetMapping(value = DOWNLOAD_RICEVUTA, produces = MEDIA_TYPE)
	public void downloadEmlRicevuta(@RequestParam(name="idCms") String idCms,
									@RequestParam(name="idPratica") UUID idPratica,
			HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("downloadRicevuta");
		checkPermessi(idPratica, new CorrispondenzaSearch());
		try {
			allegatiService.doDownloadFromCms(idCms, response);
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@GetMapping(value = ANTEPRIMA, produces = MEDIA_TYPE)
	public void anteprima(@RequestParam Long id,@RequestParam UUID idPratica,
			HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("download");
		PraticaDTO praticaDto=checkPermessi(idPratica, new CorrispondenzaSearch());
		DettaglioCorrispondenzaDTO corr = service.find(id);
		File f=null;
		try {
			f= this.service.generaPDFinformazioniEmail(corr, corr.getAllegatiInfo(),new Date() , "",praticaDto);
			this.copyToResponse(response, f, f.getName(),MediaType.APPLICATION_PDF.toString());
		} catch (Exception e) {
			logger.error("Errore nella generazione dell'anteprima ",e);
			response.setStatus(500);
		} finally {
			logger.info(LogUtil.stopLog(sw));
			if(f!=null && f.exists()) {
				try{f.delete();}catch(Exception e) {logger.info("Impossibile rimuovere il file temporaneo:"+f.getName());}
			}
		}
	}
	
}

