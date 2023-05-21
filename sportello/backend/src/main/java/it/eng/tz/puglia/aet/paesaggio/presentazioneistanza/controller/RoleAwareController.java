package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.ValidationFailureException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl.PraticaDataAwareService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.controller.BaseRestController;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

public class RoleAwareController extends BaseRestController {
	
//	protected static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User has inadequate Roles";

	private static final Logger log = LoggerFactory.getLogger(RoleAwareController.class);
	
	@Autowired
	private GruppiRuoliService gruppiRuoliService;
	
	@Autowired
	private PraticaDataAwareService praticaDataAwareSvc;
	
	@Autowired
	UserUtil userUtil; 
	
	
	/**
	 * verifica se l'utente loggato è rup per la pratica sia per diretta assegnazione oppure perchè è REG_
	 * @author acolaianni
	 *
	 * @param rup
	 * @return
	 */
	protected boolean isRupForPratica(String rup) {
		return praticaDataAwareSvc.isRupForPratica(rup);
	};
	
	protected void checkAbilitazioneFor(Gruppi... gruppiAmmessi) throws CustomOperationToAdviceException {
		try {
			gruppiRuoliService.checkAbilitazioneFor(gruppiAmmessi);
		} catch (CustomOperationToAdviceException e) {
			throw e;
		}
	}
	
	protected void checkAbilitazioneFor(Ruoli... ruoliAmmessi) throws CustomOperationToAdviceException {
		try {
			gruppiRuoliService.checkAbilitazioneFor(ruoliAmmessi);
		} catch (CustomOperationToAdviceException e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @return id della mia organizzazione (solo per utenze che appartengono ad una organizzazione)
	 */
	protected Long myEnteId() {
		return this.userUtil.getLongId();
	}
	
	/**
	 * l'eccezione CustomOperationToAdviceException viene imbustata 
	 * in modo che il front-end la riconosca e la presenti nella alert verso l'utente
	 */
	@Override
	protected <T> ResponseEntity<BaseRestResponse<T>> koResponse(Exception e, BaseRestResponse<T> body){
		if (e instanceof CustomOperationToAdviceException || 
		    e instanceof       ValidationFailureException) {
			return super.koResponse("CUSTOM-OPERATION-TO-ADVICE:"+e.getMessage(), body);	
		} else {
			return super.koResponse(e, body);
		}
	}
	
	
	private String getContentType(File f) {
		String contentType=null;
		try (InputStream tikaIs = new FileInputStream(f)) {
			String type = AllowedMimeType.detectMimeType(tikaIs);
			MediaType mt1 = MediaType.parse(type);
			contentType=mt1.toString();
		} catch (Exception e) {
			contentType=MediaType.OCTET_STREAM.toString();
		}
		return contentType;
	}
	
	protected void copyToResponse(HttpServletResponse response, File f,String filename) throws IOException, FileNotFoundException {
		this.copyToResponse(response, f, filename, null);
	}
	
	/**
	 * rimuove 
	 * @param response
	 * @param f file: viene rimosso dopo averlo copiato nel body della response 
	 * @param filename nullable , se non nullo lo mette nel content disposition
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void copyToResponse(HttpServletResponse response, File f,String filename,String contentType) throws IOException, FileNotFoundException {
		if(filename!=null) {
			String exposed=response.getHeader("Access-Control-Expose-Headers");
			if(StringUtil.isNotEmpty(exposed)) {
				exposed=exposed.concat(" ,content-disposition ");	
			}else {
				exposed=" content-disposition ";
			}
			response.setHeader("Access-Control-Expose-Headers",exposed);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+filename);
		}else {
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"inline");
		}
		response.setContentLength((int)f.length());
		if(contentType==null) {
			contentType=this.getContentType(f);	
		}
		response.setContentType(contentType);
		try(InputStream  is = new FileInputStream(f);
			OutputStream os = response.getOutputStream())
		{
			IOUtils.copy(is, os);
			try
			{
				logger.info("Il file esiste su disco. Lo rimuovo...");
				is.close();
				Path _path = Paths.get(f.getAbsolutePath());
				Files.deleteIfExists(_path);
			} catch (Exception e)
			{
				log.error("Error in delete tmp file", e);
			}
		}
	}
	
	/**
	 * verifica in caso di sportello se ho accesso alla pratica
	 * @author acolaianni
	 *
	 * @param pratica
	 * @throws CustomOperationToAdviceException
	 */
	protected void checkDiMiaCompetenza(PraticaDTO pratica) throws CustomOperationToAdviceException {
		this.praticaDataAwareSvc.checkDiMiaCompetenza(pratica);
	}
	//Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
	protected void checkDiMiaCompetenzaInLettura(PraticaDTO pratica) throws CustomOperationToAdviceException {
		this.praticaDataAwareSvc.checkDiMiaCompetenzaInLettura(pratica);
	}
	
	protected PraticaDTO checkDiMiaCompetenza(UUID idPratica) throws CustomOperationToAdviceException {
		return this.praticaDataAwareSvc.checkDiMiaCompetenza(idPratica);
	}
	
	protected PraticaDTO checkDiMiaCompetenzaByCodicePraticaAppptr(String codicePraticaAppptr) throws CustomOperationToAdviceException {
		return this.praticaDataAwareSvc.checkDiMiaCompetenzaByCodicePraticaAppptr(codicePraticaAppptr);
	}
	
	//Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
	protected PraticaDTO checkDiMiaCompetenzaInLetturaByCodicePraticaAppptr(String codicePraticaAppptr) throws CustomOperationToAdviceException {
		return this.praticaDataAwareSvc.checkDiMiaCompetenzaInLetturaByCodicePraticaAppptr(codicePraticaAppptr);
	}
	
	
	
}