package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.servizi_esterni.remote.dto.AutoCompleteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.RemoteSchemaService;

@RestController
//@RequestMapping(value = "public/ente","api-gateway/public/ente"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicEntiController extends _RestController 
{
	private final String PUBLIC_ENTE_PATH="public/ente";
	private final String API_PUBLIC_ENTE_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+PUBLIC_ENTE_PATH;
	private final static Logger log = LoggerFactory.getLogger(PublicEntiController.class);
	private final static String FIND_COMUNI_PROVINCE  = "/findComuniEProvince";
	private final static String AUTOCOMPLETE_NAZIONI  = "/autocompleteNazioni";
	private final static String AUTOCOMPLETE_PROVINCE = "/autocompleteProvince";
	private final static String AUTOCOMPLETE_COMUNI   = "/autocompleteComuni";
	
	@Autowired private RemoteSchemaService remoteService;
	@Autowired private AnagraficaService anagraficaService;
	@Value("${anagrafica.comuni.idPuglia:16}")
	private Integer idRegionePuglia;
	
	
	@GetMapping(value=PUBLIC_ENTE_PATH+FIND_COMUNI_PROVINCE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<EnteDTO>>> findComuniEProvince() throws Exception {
		return okResponse(remoteService.findComuniEProvince());
	}
	
	@GetMapping(value= {PUBLIC_ENTE_PATH+AUTOCOMPLETE_NAZIONI,API_PUBLIC_ENTE_PATH+AUTOCOMPLETE_NAZIONI}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "search nazione by name pattern", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<AutoCompleteDTO>>> autocompleteNazioni(
			@Parameter(description="pattern filtro es. ital")
			@RequestParam("filter") String filter,
			@Parameter(description="limite sui risultati da prelevare")
			@RequestParam(value="limit", required=false) Integer limit) throws Exception	{
		return okResponse(anagraficaService.autocompleteNazioni(filter, limit));
	}
	
	@GetMapping(value= {PUBLIC_ENTE_PATH+AUTOCOMPLETE_PROVINCE,API_PUBLIC_ENTE_PATH+AUTOCOMPLETE_PROVINCE}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "search provincia italiana by name pattern", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<List<AutoCompleteDTO>>> autocompleteProvince(
			@Parameter(description="pattern filtro es. bar")
			@RequestParam("filter") String filter,
			@Parameter(description="limite sui risultati da prelevare")
			@RequestParam(value="limit", required=false) Integer limit) throws Exception {
		return okResponse(anagraficaService.autocompleteProvince(filter, limit));
	}
	
	@GetMapping(value= {PUBLIC_ENTE_PATH+AUTOCOMPLETE_COMUNI,API_PUBLIC_ENTE_PATH+AUTOCOMPLETE_COMUNI}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "search comune italiano by name pattern e altri filtri", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<List<AutoCompleteDTO>>> autocompleteComuni(
			@Parameter(description="pattern filtro es. biton")
			@RequestParam("filter") String filter,
			@Parameter(description="id numerico della provincia")																					  
			@RequestParam(value="idProvincia", required=false) Integer idProvincia,
			@Parameter(description="Denominazione provincia di riferimento")
			@RequestParam(value="denominazioneProvincia", required=false)String denominazioneProv,
			@Parameter(description="id numerico della regione (16 Puglia)")					  
			@RequestParam(value="idRegione", required=false) Integer idRegione,
			@Parameter(description="true, cerca solo nella Regione Puglia")																					 
			@RequestParam(value="soloPuglia", required=false) Boolean soloPuglia, 
			@Parameter(description="limite sui risultati da prelevare")
			@RequestParam(value="limit", required=false) Integer limit) throws Exception {
		Integer idRegioneVar=idRegione;
		if(soloPuglia!=null && soloPuglia) {
			idRegioneVar=this.idRegionePuglia;
		}
		return okResponse(anagraficaService.autocompleteComuni(filter, denominazioneProv, idProvincia, limit,idRegioneVar));
	}
	
}