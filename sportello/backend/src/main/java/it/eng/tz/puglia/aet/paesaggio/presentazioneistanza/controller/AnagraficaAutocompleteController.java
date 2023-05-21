package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.bean.BaseRestResponse;

@Controller
@RequestMapping("public/")
public class AnagraficaAutocompleteController extends RoleAwareController {
	
	@Autowired
	RemoteSchemaService remoteService;
	
	private static final Logger log = LoggerFactory.getLogger(AnagraficaAutocompleteController.class);
	private final static String AUTOCOMPLETE_NAZIONI = "nazione/autocomplete.pjson";
	private final static String AUTOCOMPLETE_PROVINCE = "provincia/autocomplete.pjson";
	private final static String AUTOCOMPLETE_COMUNI = "comuni/autocomplete.pjson";
	
	@PostMapping(value=AUTOCOMPLETE_NAZIONI, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> autocompleteNazioni(@RequestParam("filter")String filter,
																					   @RequestParam(value="limit", required=false)Integer limit) throws Exception
	{
		log.info("Chiamato il controller: 'allegati'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		log.info("Chiamata per autocomplete nazioni");
		return okResponse(remoteService.autocompleteNazioni(filter, limit));
	}
	
	@PostMapping(value=AUTOCOMPLETE_PROVINCE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> autocompleteProvince(@RequestParam("filter")String filter,
																						@RequestParam(value="limit", required=false)Integer limit) throws Exception
	{
		log.info("Chiamato il controller: 'allegati'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		log.info("Chiamata per autocomplete comuni");
		return okResponse(remoteService.autocompleteProvince(filter, limit));
	}
	
	@PostMapping(value=AUTOCOMPLETE_COMUNI, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<List<PlainNumberValueLabel>>> autocompleteComuni(@RequestParam("filter")String filter,
																					  @RequestParam(value="parentId", required=false)Integer idProvincia,
																					  @RequestParam(value="limit", required=false)Integer limit) throws Exception
	{
		log.info("Chiamato il controller: 'allegati'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		log.info("Chiamata per autocomplete comuni");
		return okResponse(remoteService.autocompleteComuni(filter, idProvincia, limit));
	}

}
