package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.autpae.dto.UlterioreDocumentazioneMultiDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.autpae.service.interfacce.UlterioreDocumentazioneService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "ulterioreDoc", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UlterioreDocumentazioneController extends _RestController
{
	private static final Logger log = LoggerFactory.getLogger(UlterioreDocumentazioneController.class);
	
	@Autowired private UlterioreDocumentazioneService ulterioreDocumentazioneService;
	
	
	
	@PostMapping(value = "/caricaAllegato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<UlterioreDocumentazioneDTO>> caricaUlterioreDocumentazione(@RequestPart(name="data", required=true) UlterioreDocumentazioneDTO data,
																									  @RequestPart(name="file", required=true) MultipartFile file) throws Exception	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		else if (data.getIdFascicolo()<=0    ||
				 data.getDescrizione()==null || data.getDescrizione().trim().isEmpty() ||
				 data.getTitolo()==null      || data.getTitolo().trim().isEmpty()      ||
				 data.getNotifica()==null    || data.getNotifica().isEmpty()            ) {
			log.error("Dati documentazione incompleti!");
			throw new Exception("Dati documentazione incompleti!");
		}
		else {
			checkPermission(data.getIdFascicolo());
			return super.okResponse(ulterioreDocumentazioneService.inserisciDocumentazione(data.getIdFascicolo(), data.getTitolo(), data.getDescrizione(), data.getNotifica(), data.getEnti(), file));
		}
			
	}
	
	@LoggingAet
	@PostMapping(value = "caricaAllegatoMulti", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseRestResponse<UlterioreDocumentazioneDTO>> caricaUlterioreDocumentazioneMulti(@RequestPart(name="data", required=true) UlterioreDocumentazioneMultiDTO data, @RequestPart(name="file", required=true) MultipartFile[] file) throws Exception
	{
	    checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
	    if (file == null || file.length == 0) {
		log.error("Allegato vuoto o non trovato!");
		throw new Exception("Allegato vuoto o non trovato!");
	    }
//		else if (data.getIdFascicolo()<=0    ||
//				 data.getDescrizione()==null || data.getDescrizione().trim().isEmpty() ||
//				 data.getTitolo()==null      || data.getTitolo().trim().isEmpty()      ||
//				 data.getNotifica()==null    || data.getNotifica().isEmpty()            ) {
//			log.error("Dati documentazione incompleti!");
//			throw new Exception("Dati documentazione incompleti!");
//		}
	    else {
		checkPermission(data.getIdFascicolo());
		return super.okResponse(ulterioreDocumentazioneService.inserisciDocumentazione(data.getIdFascicolo(), data.getNotifica(), data.getEnti(), data.getAllegati(), file));
	    }
	}
	
	
	
	@GetMapping(value = "/search")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<UlterioreDocumentazioneDTO>>> searchUlterioreDocumentazione(UlterioreDocumentazioneSearch search) throws Exception {
		checkPermission(search.getIdFascicolo());
		return super.okResponse(ulterioreDocumentazioneService.searchDocumentazione(search));
	}
	
	
	@GetMapping(value = "/destinatariDefault")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipologicaDestinatarioDTO>>> getDestinatariDefault() throws Exception {
		checkAbilitazioneFor(Gruppi.values());
		return super.okResponse(ulterioreDocumentazioneService.getDestinatariDefault());
	}
	
	
	
}