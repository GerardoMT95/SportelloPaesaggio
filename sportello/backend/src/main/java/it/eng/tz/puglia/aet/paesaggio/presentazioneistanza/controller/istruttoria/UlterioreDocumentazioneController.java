package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.UlterioreDocumentazioneMultiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioreDocSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.UlterioriDocService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "documentazione", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UlterioreDocumentazioneController extends RoleAwareController 
{
	private static final Logger log = LoggerFactory.getLogger(UlterioreDocumentazioneController.class);
	
	//@Autowired private UlterioreDocRepository ulterioreDocRepo;
	@Autowired private UserUtil userUtil;
	@Autowired private UlterioriDocService ulterioriDocService;
//	@Autowired private ISitPugliaConfiguration configuration;
	@Autowired ApplicationProperties props;
	@Autowired CommonRepository commonDao;

	@Value("${allegatoemail.url}")
	private String downloadUrl;
	
	
	@PostMapping(value = "/search")
	public ResponseEntity<BaseRestResponse<PaginatedList<AllegatiDTO>>> searchUlterioreDocumentazione(@RequestBody UlterioreDocSearch search) throws Exception {
		log.info("Chiamato il controller: 'ulterioreDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN, Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_,Gruppi.CL_);

		String visibileA = null;
		if(!userUtil.getGruppo().equals(Gruppi.ADMIN))
			visibileA = userUtil.getGruppo().toString();
		if(search.getIdFascicolo() != null) {
			this.checkDiMiaCompetenza(search.getIdFascicolo());
			if(search.getVisibileA() != null) {
				visibileA+=","+search.getVisibileA()+"_";
			}
			search.setVisibileA(visibileA);
			return super.okResponse(ulterioriDocService.search(search));
		}else {
			return super.okResponse(new PaginatedList<AllegatiDTO>());
		}
	}
	
	@PostMapping(value = "/invia-documentazione", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseRestResponse<String>> insertUlterioreDocumentazione(@RequestPart(name="data", required=true) UlterioreDocumentazioneDTO data,
										      @RequestPart(name="file", required=true) MultipartFile[] file,
										      HttpServletRequest request) throws Exception {
		log.info("Chiamato il controller: 'ulterioreDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

//		String prefixUrl = configuration.getString(ComunicazioniService.KEY_URL_DOWNLOAD_PREFIX);
		String prefixUrl = this.commonDao.getConfigurationValue(ComunicazioniService.KEY_URL_DOWNLOAD_PREFIX,props.getCodiceApplicazione());
		String pathFileDownload =
				prefixUrl+
				//request.getRequestURL().toString().split("documentazione")[0]+
				"/public/"+ComunicazioniService.PUBLIC_DOWNLOAD_ULT_DOC;	
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_,Gruppi.CL_);
		this.checkDiMiaCompetenza(data.getIdFascicolo());

		ulterioriDocService.insertUlterioreDoc(data, file, pathFileDownload); 
		
		return super.okResponse("ok");
	}
	
	@PostMapping(value = "/invia-documentazione-multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseRestResponse<String>> insertUlterioreDocumentazione(@RequestPart(name="data", required=true) UlterioreDocumentazioneMultiDTO data,
										      @RequestPart(name="file", required=true) MultipartFile[] file,
										      HttpServletRequest request) throws Exception 
	{
	    log.info("Chiamato il controller: 'ulterioreDoc'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	    String prefixUrl = this.commonDao.getConfigurationValue(ComunicazioniService.KEY_URL_DOWNLOAD_PREFIX,props.getCodiceApplicazione());
	    String pathFileDownload =
		    prefixUrl+
		    //request.getRequestURL().toString().split("documentazione")[0]+
		    "/public/"+ComunicazioniService.PUBLIC_DOWNLOAD_ULT_DOC;	
	    checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_,Gruppi.CL_);
	    this.checkDiMiaCompetenza(data.getIdFascicolo());
	    
	    ulterioriDocService.insertUlterioreDoc(data, file, pathFileDownload); 
	    
	    return super.okResponse("ok");
	}
	
	
}