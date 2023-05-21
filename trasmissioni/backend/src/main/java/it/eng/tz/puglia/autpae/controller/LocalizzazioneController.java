package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.UlterioriInformazioniDto;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

@RestController
//@RequestMapping(value = {"localizzazione","api-gateway/localizzazione"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LocalizzazioneController extends _RestController {

	private static final Logger log = LoggerFactory.getLogger(LocalizzazioneController.class);
	private final String LOCALIZZAZIONE_PATH="localizzazione";
	private final String API_LOCALIZZAZIONE_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+LOCALIZZAZIONE_PATH;
	@Autowired	private LocalizzazioneService localizzazioneService;
	@Autowired	private CommonService commonService;
	

	@PostMapping(value = {LOCALIZZAZIONE_PATH+"/liste",API_LOCALIZZAZIONE_PATH+"/liste"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Vincolistica presente nella lista dei codici catastali dei comuni regionali passati in input", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<UlterioriInformazioniDto>> getListe(
			@Parameter(description="Lista dei codici catastali dei comuni su cui si chiedono i vincoli paesaggistici")
			@RequestBody List<String> comuni
			) throws Exception {
			super.checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(localizzazioneService.getListe(comuni));
	}
	
	@GetMapping(value = LOCALIZZAZIONE_PATH+"/particelleComuniForGruppo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipologicaDTO>>> comuniForGruppo() throws Exception {
			super.checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(localizzazioneService.particelleComuniForGruppo());
	}
	
	
	@GetMapping(value = {LOCALIZZAZIONE_PATH+"/sezioniCatastali",API_LOCALIZZAZIONE_PATH+"/sezioniCatastali"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Elenco delle sezioni catastali con codifica e descrizione previste nei comuni della regione", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<List<SelectOptionDto>>> sezioniCatastali() throws Exception {
			super.checkAbilitazioneFor(Gruppi.values());
			return super.okResponse(this.commonService.getSezioniCatastali());
	}

}