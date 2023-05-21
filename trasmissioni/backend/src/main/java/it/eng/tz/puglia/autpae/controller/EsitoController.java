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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.service.interfacce.EsitoService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;

@RestController
@RequestMapping(value = "esito", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EsitoController extends CheckGroupAbstractController//extends _RestController 
{

	private static final Logger log = LoggerFactory.getLogger(EsitoController.class);
	
	@Autowired private EsitoService esitoService;
	
	
	@GetMapping(value = "/dati")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<AllegatoCustomDTO>>> datiEsito(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
		checkAbilitazioneFor(Gruppi.REG_);
		return super.okResponse(esitoService.datiEsito(idFascicolo));
	}
	
	
	@PostMapping(value = "/caricaLettera", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MEDIA_TYPE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Long>> caricaLettera(@RequestParam("idFascicolo") long idFascicolo,
																@RequestBody MultipartFile file) throws Exception {
		checkAbilitazioneFor(Gruppi.REG_);
		super.check();
		if (file==null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		return super.okResponse(esitoService.caricaOresettaLettera(idFascicolo, file));
	}
	
	@PostMapping(value = "/prosegui")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<AllegatoCustomDTO>>> proseguiEsito(@RequestPart InformazioniDTO body,
																				   @RequestPart MultipartFile   file) throws Exception {
		checkAbilitazioneFor(Gruppi.REG_);
		super.check();
		if (body==null) {
			log.error("Errore. Dati in input mancanti!");
			throw new Exception("Errore. Dati in input mancanti!");
		}
		if (file==null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		return super.okResponse(esitoService.proseguiEsito(body, file));
	}
	
	@GetMapping(value = "/resetLettera")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Long>> resetLettera(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
		checkAbilitazioneFor(Gruppi.REG_);
		super.check();
		return super.okResponse(esitoService.caricaOresettaLettera(idFascicolo, null));
	}
	
	@GetMapping(value = "/invia")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> inviaEsito(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
		checkAbilitazioneFor(Gruppi.REG_);
		super.check();
		return super.okResponse(esitoService.inviaEsito(idFascicolo));
	}
	
}