package it.eng.tz.puglia.autpae.controller;

import java.util.Collections;

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
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;

@RestController
@RequestMapping(value = "richiedente", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RichiedenteController extends _RestController {

	private static final Logger log = LoggerFactory.getLogger(RichiedenteController.class);
	
	@Autowired private ApplicationProperties props;
	
	@Autowired private RichiedenteService richiedenteService;
	
	@Autowired private AllegatoService allegatoService;

		
	@GetMapping(value = "/dati")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<RichiedenteDTO>> datiRichiedente(@RequestParam(name="id", required=true) long id) throws Exception {
		if (!props.isPareri()) {
			throw new Exception("La controller non è abilitata per questo l'applicativo!");
		}
		super.checkPermission(id);
		return super.okResponse(richiedenteService.datiRichiedente(id));
	}
	
	@PostMapping(value = "/carica", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaDocumentoRiconoscimento(@RequestParam(name = "idFascicolo",  required = true) long idFascicolo,
																		 	  				 @RequestBody (required = true) MultipartFile file) throws Exception {
		//checkAbilitazioneFor(Gruppi.ED_);
		checkAbilitazioneForTrasmissione();
		if (!props.isPareri()) {
			throw new Exception("La controller non è abilitata per questo l'applicativo!");
		}
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		else {
			super.checkPermission(idFascicolo);
			return super.okResponse(allegatoService.inserisciAllegato(Collections.singletonList(idFascicolo), TipoAllegato.DOCUMENTO_RICONOSCIMENTO, file, null));
		}
	}
}