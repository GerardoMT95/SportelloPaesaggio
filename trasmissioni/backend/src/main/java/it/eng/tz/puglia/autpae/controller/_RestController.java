package it.eng.tz.puglia.autpae.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.AllowedMimeType;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.controller.BaseRestController;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
public class _RestController extends BaseRestController {
	
	@Autowired FascicoloService fascicoloService;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(_RestController.class);

	@Autowired
	private GruppiRuoliService gruppiRuoliService;

	
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
	
	protected void checkAbilitazioneForTrasmissione() throws CustomOperationToAdviceException {
		try {
			gruppiRuoliService.checkAbilitazioneForTrasmissione();
		} catch (CustomOperationToAdviceException e) {
			throw e;
		}
	}
	
	/**
	 * rimuove 
	 * @param response
	 * @param f file: viene rimosso dopo averlo copiato nel body della response 
	 * @param filename nullable , se non nullo lo mette nel content disposition
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void copyToResponse(HttpServletResponse response, File f,String filename) throws IOException, FileNotFoundException {
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
		try (InputStream tikaIs = new FileInputStream(f)) {
			String type = AllowedMimeType.detectMimeType(tikaIs);
			MediaType mt1 = MediaType.parseMediaType(type);
			response.setContentType(mt1.toString());
		} catch (Exception e) {
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
		}
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
	
	protected void checkPermission(Long idFascicolo) throws Exception
	{
		this.fascicoloService.checkPermission(idFascicolo);
	}
	
	protected void checkPermission(Long idFascicolo,StatoFascicolo stato) throws Exception
	{
		this.fascicoloService.checkPermission(idFascicolo, stato);
	}

}