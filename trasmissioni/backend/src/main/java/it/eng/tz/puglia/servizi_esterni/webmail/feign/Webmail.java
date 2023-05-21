package it.eng.tz.puglia.servizi_esterni.webmail.feign;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tz.puglia.autpae.config.FeignConfiguration;
import it.eng.tz.regione.puglia.webmail.be.dto.AttivaDisattivaCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.BaseResponse;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleResponseDto;

@FeignClient(contextId ="webmailContextId", name="Webmail", url="${microservice.webmail.url}", configuration=FeignConfiguration.class)
public interface Webmail {
	
	@PostMapping(value = {"/casella-postale/configura-casella"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleResponseDto>> configuraCasellaPostale( @RequestBody @Valid ConfigurazioneCasellaPostaleDto requestBody );
	
	@GetMapping(value = {"/casella-postale/get-configurazione-casella/{indirizzoMail}"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BaseResponse<ConfigurazioneCasellaPostaleDto>> getConfigurazioneCasellaPostale( @PathVariable(value="indirizzoMail") String indirizzoMail );
	
	@GetMapping(value = {"/mail/retrieve-mail-eml/{mailId}"})
	public ResponseEntity<Resource> retrieveEml( @PathVariable(name="mailId", required=true) String mailId,
															@RequestParam(name="docId" , required=true) String emlId );
	
	@GetMapping(value = {"/ricevuta/retrieve-ricevuta-eml/{pkRicevuta}"})
	public ResponseEntity<Resource> retrieveEml( @PathVariable(name="pkRicevuta", required=true) String pkRicevuta );
	
	@GetMapping(value = {"/ricevuta/retrieve-mail-ricevuta-eml/{pkRicevuta}"})
	public ResponseEntity<Resource> retrieveMailRicevutaEml( @PathVariable(name="pkRicevuta", required=true) String pkRicevuta );
	
	@PutMapping(value = {"/casella-postale/attiva-disattiva-casella"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BaseResponse<Boolean>> attivaDisattivaCasellaPosta( @RequestBody AttivaDisattivaCasellaPostaleDto request );
	
}
