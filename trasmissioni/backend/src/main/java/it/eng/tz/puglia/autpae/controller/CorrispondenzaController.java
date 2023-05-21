package it.eng.tz.puglia.autpae.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AllegatoInfoDTO;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaAggDTO;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;


@RestController
@RequestMapping(value = "corrispondenza", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CorrispondenzaController extends _RestController {

	private static final Logger log = LoggerFactory.getLogger(CorrispondenzaController.class);
	
	@Autowired private CorrispondenzaService corrispondenzaService;
	@Autowired private AllegatoService allegatoService;
	
	
	
	@LoggingAet
	@GetMapping(value = "/dettaglio")
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaAggDTO>> dettaglioCorrispondenza(@RequestParam(name="idCorrispondenza", required=true) long idCorrispondenza) throws Exception {
			checkAbilitazioneFor(Gruppi.values());
			DettaglioCorrispondenzaDTO dettaglio = corrispondenzaService.dettaglioCorrispondenza(idCorrispondenza, true);
			return super.okResponse(new DettaglioCorrispondenzaAggDTO(dettaglio));
	}
	
	@GetMapping(value = "/search")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<DettaglioCorrispondenzaDTO>>> searchCorrispondenza(CorrispondenzaSearch filters) throws Exception {
		if (filters.getIdFascicolo()==null || filters.getIdFascicolo()<=0) {
			log.error("ERRORE. Specificare un idFascicolo valido!");
			throw new Exception("ERRORE. Specificare un idFascicolo valido!");
		}
		checkPermission(filters.getIdFascicolo());
		filters.setUsernameForBozza(SecurityUtil.getUsername());
		return super.okResponse(corrispondenzaService.searchCorrispondenza(filters));
	}

	private void checkPermissionBozza(Long idFascicolo,Long idComunicazione) throws Exception{
		CorrispondenzaSearch filters=new CorrispondenzaSearch();
		filters.setIdFascicolo(idFascicolo);
		filters.setId(idComunicazione);
		filters.setBozza(true);
		filters.setUsernameForBozza(SecurityUtil.getUsername());
		PaginatedList<DettaglioCorrispondenzaDTO> list = corrispondenzaService.searchCorrispondenza(filters);
		if(list.getCount()<=0) {
			throw new CustomOperationToAdviceException("Impossibile operare sulla bozza selezionata !!!");
		}
	}
		
	
	@GetMapping(value = "/getTemplate")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<NuovaComunicazioneDTO>> getTemplate(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
			checkPermission(idFascicolo);
			return super.okResponse(corrispondenzaService.getTemplate(idFascicolo));
	}
	
	
	@PostMapping(value = "/invia/{idFascicolo}")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> inviaComunicazione(
			@PathVariable(name="idFascicolo", required=true) Long idFascicolo,
			@RequestPart(name="comunicazione", required=true ) NuovaComunicazioneDTO comunicazione,
			@RequestPart(name="allegati"     , required=false) MultipartFile[] allegati) throws Exception {
		checkAbilitazioneFor(Gruppi.values());
		checkPermission(idFascicolo);
		if(comunicazione.getId()!=null) {
			//update .. esiste già bozza
			checkPermissionBozza(idFascicolo, comunicazione.getId());	
		}
		if (allegati!=null) {
			List<AllegatoInfoDTO> listaAllegati = new ArrayList<AllegatoInfoDTO>();
			for (int i=0; i<allegati.length; i++) {
				AllegatoInfoDTO all = new AllegatoInfoDTO(new AllegatoDTO(), allegati[i]);
				all.setIsUrl(true);
				listaAllegati.add(all);
			}
			comunicazione.setAllegati(listaAllegati);
		}
		return super.okResponse(corrispondenzaService.inviaOsalvaComunicazione(comunicazione));
	}
	
	@PostMapping(value = "/caricaAllegato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaAllegato(@RequestParam(name="idFascicolo",      required=true) long idFascicolo,
																			  @RequestParam(name="idCorrispondenza", required=true) long idCorrispondenza,
																			  @RequestPart (name="file",             required=true) MultipartFile file) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		checkPermission(idFascicolo);
		checkPermissionBozza(idFascicolo, idCorrispondenza);
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		if (idCorrispondenza<=0 || idFascicolo<=0) {
			log.error("Errore. Dati in ingresso incompleti!");
			throw new Exception("Errore. Dati in ingresso incompleti!");
		}
		return super.okResponse(corrispondenzaService.caricaAllegato(Collections.singletonList(idFascicolo), idCorrispondenza, file,true));
	}
	
	@DeleteMapping(value = "/eliminaAllegato")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegato(
			@RequestParam(name = "idAllegato", required = true) long idAllegato,
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "idCorrispondenza", required = true) long idCorrispondenza
			) throws Exception {
		if (idAllegato < 1)
			throw new Exception("Id allegato errato!");
		checkAbilitazioneFor(Gruppi.values());
		checkPermission(idFascicolo);
		checkPermissionBozza(idFascicolo, idCorrispondenza);
		try {
			CorrispondenzaDTO corrDto = corrispondenzaService.find(idCorrispondenza);
			if(corrDto.isBozza()) {
				allegatoService.eliminaAllegato(idAllegato);	
			}
		}catch(EmptyResultDataAccessException e) {
			return super.okResponse(false);
		}
		return super.okResponse(true);
	}
	
	@DeleteMapping(value = "/cancellaBozza")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> cancellaBozza(
			@RequestParam(name="idCorrispondenza", required=true) long idCorrispondenza,
			@RequestParam(name="idFascicolo", required=true) long idFascicolo
			) throws Exception {
		checkAbilitazioneFor(Gruppi.values());
		checkPermission(idFascicolo);
		checkPermissionBozza(idFascicolo, idCorrispondenza);
		return super.okResponse(corrispondenzaService.cancellaBozza(idCorrispondenza));
	}
	
}