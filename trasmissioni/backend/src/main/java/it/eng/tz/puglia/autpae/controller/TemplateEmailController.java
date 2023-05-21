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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.autpae.dto.TemplateEmailDestinatariDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateEmailService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;

@RestController
@RequestMapping(value = "templateEmail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TemplateEmailController extends _RestController 
{
	private static final Logger log = LoggerFactory.getLogger(TemplateEmailController.class);

	@Autowired private TemplateEmailService templateEmailService;
	@Autowired private GruppiRuoliService gruppiRuoliService;
	
	
	@GetMapping(value = "/info")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<TemplateEmailDestinatariDTO>> info(@RequestParam(name="codice", required=true) TipoTemplate codice) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateEmailService.info(codice));
	}
	
	@GetMapping(value = "/getAll")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TemplateEmailDestinatariDTO>>> getAll() throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateEmailService.getAll());
	}
	
	@PostMapping(value = "/salva")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> salva(@RequestBody(required=true) TemplateEmailDestinatariDTO body) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateEmailService.salva(body));
	}
	
	@PostMapping(value = "/resetEmail")			// Get se autoSave=false o Post se autoSave=true
	@LoggingAet
	public ResponseEntity<BaseRestResponse<TemplateEmailDestinatariDTO>> resetEmail(@RequestParam(name="codice", required=true) TipoTemplate codice) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateEmailService.resetEmail(codice, true));
	}
	
	@PostMapping(value = "/resetDestinatari")	// Get se autoSave=false o Post se autoSave=true
	@LoggingAet
	public ResponseEntity<BaseRestResponse<TemplateEmailDestinatariDTO>> resetDestinatari(@RequestParam(name="codice", required=true) TipoTemplate codice) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ADMIN);
		return super.okResponse(templateEmailService.resetDestinatari(codice, true));
	}
	
}