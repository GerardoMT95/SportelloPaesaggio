package it.eng.tz.puglia.autpae.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.config.VerifyGroupFilter;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.ValidationBeanDto;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
public class AzioniController extends CheckGroupAbstractController//extends _RestController 
{
	private static final String AZIONI_PATH="azioni";
	private static final String API_AZIONI_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+AZIONI_PATH;

	@Autowired private AzioniService azioniService;
	

	
	@LoggingAet
	@PutMapping(value = {AZIONI_PATH +"/salva",API_AZIONI_PATH+"/salva"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Salvataggio dati Fascicolo", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<InformazioniDTO>> salva(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Oggetto contenente tutti i dati del fascicolo", required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schema=@io.swagger.v3.oas.annotations.media.Schema(implementation = InformazioniDTO.class)))
			@RequestBody(required=true) final InformazioniDTO body
			,@RequestHeader(name = VerifyGroupFilter.GROUP_KEY,required = false) final String gruppo
			) throws Exception {
		String actualGroup = null;
		if(StringUtil.isNotEmpty(gruppo)) {
			actualGroup=new String(Base64.decodeBase64(gruppo.getBytes()));
		}
		final boolean multiComune = this.gruppiRuoliService.isMultiComune(actualGroup);
		body.setMultiComune(multiComune);
		this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
		List<Long> ids = null;
		if(body != null && body.getLocalizzazione() != null && body.getLocalizzazione().getLocalizzazioneComuni() != null)
			ids = body.getLocalizzazione().getLocalizzazioneComuni().stream().map(m -> m.getComuneId()).collect(Collectors.toList());
		super.check(body.getId(), ids);
		this.checkPermission(body.getId());
		return super.okResponse(this.azioniService.salva(body));
	}
	
	@LoggingAet
	@PutMapping(value = AZIONI_PATH +"/salvaLocalizzazione/{idFascicolo}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<LocalizzazioneTabDTO>> salvaLocalizzazione(@RequestBody (required=true) final LocalizzazioneTabDTO body,
																					  @PathVariable(required=true) final long idFascicolo) throws Exception {
		this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
		List<Long> ids = null;
		if(body != null && body.getLocalizzazioneComuni() != null)
			ids = body.getLocalizzazioneComuni().stream().map(m -> m.getComuneId()).collect(Collectors.toList());
		super.check(idFascicolo, ids);
		this.checkPermission(idFascicolo);
		return super.okResponse(this.azioniService.salvaLocalizzazione(body, idFascicolo));
	}
	
	
	@Operation(summary = "Validazione completa del fascicolo preliminare alla trasmissione finale", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = {AZIONI_PATH +"/valida",API_AZIONI_PATH + "/valida"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<ValidationBeanDto>> valida(@RequestParam(required=true) final Long idFascicolo) throws Exception {
	   this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
	   super.check();
	   this.checkPermission(idFascicolo);
	   return super.okResponse(this.azioniService.valida(idFascicolo));
	}
	

	@Operation(summary = "Prelievo degli indirizzi email/PEC dei destinatari automatici della PEC di notifica trasmissione", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value =  {AZIONI_PATH +"/getDestinatariTrasmissione",API_AZIONI_PATH +"/getDestinatariTrasmissione" },produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipologicaDestinatarioDTO>>> getDestinatariTrasmissione(@RequestParam(name="idFascicolo", required=true) final long idFascicolo) throws Exception {
	   this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
	   this.checkPermission(idFascicolo);
	   return super.okResponse(this.azioniService.getDestinatariTrasmissione(idFascicolo, null, false));
	}
	
	@Operation(summary = "Azione irreversibile di trasmissione finale con invio PEC di notifica a tutti i destinatari", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = {AZIONI_PATH + "/trasmetti",API_AZIONI_PATH +"/trasmetti"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> trasmetti(@RequestParam(name="idFascicolo", required=true) final long idFascicolo,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Array di eventuali altri destinatari della trasmissione", required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    array=@io.swagger.v3.oas.annotations.media.ArraySchema(schema=@io.swagger.v3.oas.annotations.media.Schema(implementation = TipologicaDestinatarioDTO.class)
                    )))
			@RequestBody(required=true) final List<TipologicaDestinatarioDTO> ulterioriDestinatari) throws Exception {
	   this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
	   super.check();
	   this.checkPermission(idFascicolo);
	   return super.okResponse(this.azioniService.trasmetti(idFascicolo, ulterioriDestinatari));
	}
	
	
	@GetMapping(value = AZIONI_PATH + "/lineaTemporale",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<LineaTemporaleDTO>> lineaTemporale(@RequestParam(name="idFascicolo", required=true) final long idFascicolo) throws Exception {
	   this.checkPermission(idFascicolo);
	   return super.okResponse(this.azioniService.lineaTemporale(idFascicolo));
	}
	
}