package it.eng.tz.puglia.autpae.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AnnotazioniInterneDetailDto;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.generated.orm.dto.AnnotazioniInterneDTO;
import it.eng.tz.puglia.autpae.generated.orm.search.AnnotazioniInterneSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AnnotazioniInterneService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;

@RestController
@RequestMapping(value = "annotazioniInterne", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AnnotazioniInterneController extends _RestController {
	private static final Logger log = LoggerFactory.getLogger(AnnotazioniInterneController.class);

	@Autowired
	private AnnotazioniInterneService annotazioniInterneService;
	
	@Autowired
	private AllegatoService allegatoService;
	
	@Autowired
	private UserUtil userUtil;

	@GetMapping(value = "/search")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<AnnotazioniInterneDTO>>> search(
			AnnotazioniInterneSearch search) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		if (search.getIdFascicolo() == null) {
			throw new CustomOperationToAdviceException("Impossibile effettuare ricerca senza id fascicolo");
		}
		return super.okResponse(annotazioniInterneService.search(search));

	}
	
	/**
	 * Restituisce true se esiste una annotazione per il fascicolo e l'organizzazione corrente
	 * 
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/isPresent")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AnnotazioniInterneDetailDto>> isPresent(
			@RequestParam(required = true) Long idFascicolo) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		AnnotazioniInterneSearch search = new AnnotazioniInterneSearch();
		search.setIdFascicolo(String.valueOf(idFascicolo));
		search.setIdOrganizzazione(String.valueOf(userUtil.getIntegerId()));
		if (search.getIdFascicolo() == null) {
			throw new CustomOperationToAdviceException("Impossibile effettuare ricerca senza id fascicolo");
		}
		try {
			AnnotazioniInterneDTO dto = annotazioniInterneService.search(search).getList().get(0);
			AnnotazioniInterneDetailDto dtoFinale = new AnnotazioniInterneDetailDto();
			dtoFinale.setAnnotazioneBase(dto);
			dtoFinale.setAllegati(annotazioniInterneService.getDetailSoloAllegati(idFascicolo, dto.getId()));
			return super.okResponse(dtoFinale);
		} catch (Exception e) {
			return super.okResponse(new AnnotazioniInterneDetailDto());
		}

	}

	/**
	 * Crea una nuova annotazione
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/crea")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AnnotazioniInterneDTO>> creaAnnotazione(
			@RequestParam(required = true) Long idFascicolo) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		return super.okResponse(annotazioniInterneService.create(idFascicolo));
	}

	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param idAnnotazioneInterna
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/caricaAllegato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaAllegato(
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "idAnnotazioneInterna", required = true) long idAnnotazioneInterna,
			@RequestPart(name = "file", required = true) MultipartFile file) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		if (idAnnotazioneInterna <= 0 || idFascicolo <= 0) {
			log.error("Errore. Dati in ingresso incompleti!");
			throw new Exception("Errore. Dati in ingresso incompleti!");
		}
		AnnotazioniInterneDetailDto detail = this.annotazioniInterneService.getDetail(idFascicolo, idAnnotazioneInterna);
		if(detail==null) {
			throw new CustomOperationToAdviceException("Errore. Nessuna richiesta accessibile con questi parametri !") ;
		}
		
		if (detail.getAllegati() != null) {
			throw new CustomOperationToAdviceException("Non puoi inserire un altro allegato a questa annotazione interna.") ;
		}

		return super.okResponse(this.annotazioniInterneService.caricaAllegato(Collections.singletonList(idFascicolo),
				idAnnotazioneInterna, file));	
	}

	/**
	 * Elimina un allegato collegato ad una specifica annotazione.
	 * 
	 * @param idAllegato
	 * @param idFascicolo
	 * @param idAnnotazioneInterna
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "/eliminaAllegato")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegato(
			@RequestParam(name = "idAllegato", required = true) long idAllegato,
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "idAnnotazioneInterna", required = true) long idAnnotazioneInterna) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		if (idAllegato < 1)
			throw new Exception("Id allegato errato!");
		try {
			AnnotazioniInterneDetailDto detail = this.annotazioniInterneService.getDetail(idFascicolo, idAnnotazioneInterna);
			if (detail!=null) {
				//è tra gli allegati?
				//controllo che l'allegato appartenga all'annotazione selezionata
				boolean isAllegato = detail.getAllegati().stream().filter(allegato->allegato.getIdAnnotazioneInterna()!=null && allegato.getIdAnnotazioneInterna().equals(idAnnotazioneInterna)).findAny().isPresent();
				if(!isAllegato) {
					new CustomOperationToAdviceException("Allegato non appartenente all'annotazione corrente, impossibile eliminare!!!");
				}
				allegatoService.eliminaAllegato(idAllegato);
			}
		} catch (EmptyResultDataAccessException e) {
			return super.okResponse(false);
		}
		return super.okResponse(true);
	}
	
	/**
	 * Restituisce i dettagli di un'annotazione in base al suo id. L'id del fascicolo è necessario per effettuare controlli di sicurezza.
	 * 
	 * @author Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param idAnnotazioneInterna
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/dettaglio")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AnnotazioniInterneDetailDto>> getDettaglio(
			@RequestParam(required = true) Long idFascicolo,
			@RequestParam(required = true) Long idAnnotazioneInterna) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		if (idAnnotazioneInterna == null || idFascicolo == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		return super.okResponse(annotazioniInterneService.getDetail(idFascicolo, idAnnotazioneInterna));
	}
	
	/**
	 * Modifica i dati di un'annotazione.
	 * 
	 * @author Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/modifica")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AnnotazioniInterneDTO>> update(
			@RequestBody AnnotazioniInterneDTO bean) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		if (bean == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		return super.okResponse(annotazioniInterneService.update(bean));
	}
	
	/**
	 * Elimina un'annotazione in base al suo id. L'id del fascicolo è necessario per effettuare controlli di sicurezza.
	 * 
	 * @author Raffaele Del Basso
	 * @date 01 set 2021
	 * @param idFascicolo
	 * @param idAnnotazioneInterna
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "/elimina")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> delete(
			@RequestParam(required = true) Long idFascicolo,
			@RequestParam(required = true) Long idAnnotazioneInterna) throws Exception {
		checkAbilitazioneFor(Gruppi.ETI_, Gruppi.SBAP_, Gruppi.REG_);
		if (idAnnotazioneInterna == null || idFascicolo == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		try {
			return super.okResponse(annotazioniInterneService.delete(idFascicolo, idAnnotazioneInterna));	
		} catch (CustomOperationToAdviceException e) {
			return super.okResponse(false);
		}
		
	}

}