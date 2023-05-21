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
import it.eng.tz.puglia.autpae.dto.RichiestePostTrasmissioneDetailDto;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.StatoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.enumeratori.TipoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.generated.orm.dto.RichiestePostTrasmissioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.search.RichiestePostTrasmissioneSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiestePostTrasmissioneService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "richiestePostTrasmissione", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RichiestePostTrasmissioneController extends _RestController {
	private static final Logger log = LoggerFactory.getLogger(RichiestePostTrasmissioneController.class);

	@Autowired
	private RichiestePostTrasmissioneService richPostTrasmissioneService;
	@Autowired
	private AllegatoService allegatoService;

	@GetMapping(value = "/search")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<RichiestePostTrasmissioneDTO>>> search(
			RichiestePostTrasmissioneSearch search) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.ADMIN);
		long idFascicolo = Long.parseLong(search.getIdFascicolo());
		if (idFascicolo<=0) {
			throw new CustomOperationToAdviceException("Impossibile effettuare ricerca senza id fascicolo");
		}
		super.checkPermission(idFascicolo);
		return super.okResponse(richPostTrasmissioneService.search(search));

	}

	/**
	 * crea nuova richiesta
	 * 
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/crea")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<RichiestePostTrasmissioneDTO>> creaRichiesta(
			@RequestParam(required = true) Long idFascicolo,
			@RequestParam(required = true) TipoRichiestaPostTrasmissione tipo) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_);
		super.checkPermission(idFascicolo);
		return super.okResponse(richPostTrasmissioneService.create(idFascicolo, tipo));
	}

	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param idCorrispondenza
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/caricaAllegato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaAllegato(
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "idRichiesta", required = true) long idRichiesta,
			@RequestPart(name = "file", required = true) MultipartFile file) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_);
		if (file == null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			throw new Exception("Allegato vuoto o non trovato!");
		}
		if (idRichiesta <= 0 || idFascicolo <= 0) {
			log.error("Errore. Dati in ingresso incompleti!");
			throw new Exception("Errore. Dati in ingresso incompleti!");
		}
		RichiestePostTrasmissioneDetailDto detail = this.richPostTrasmissioneService.getDetail(idFascicolo, idRichiesta);
		if(detail==null) {
			throw new CustomOperationToAdviceException("Errore. Nessuna richiesta accessibile con questi parametri !") ;
		}
		if (detail.getRichiestaBase().getStato()==null || 
			!detail.getRichiestaBase().getStato().equals(StatoRichiestaPostTrasmissione.Bozza.name()))
		{
			throw new CustomOperationToAdviceException("Errore. Stato richiesta non ammesso per caricare l'allegato!") ;
		}
		return super.okResponse(this.richPostTrasmissioneService.caricaAllegato(Collections.singletonList(idFascicolo),
					idRichiesta, file));	
	}

	@DeleteMapping(value = "/eliminaAllegato")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> eliminaAllegato(
			@RequestParam(name = "idAllegato", required = true) long idAllegato,
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "idRichiesta", required = true) long idRichiesta) throws Exception {
		if (idAllegato < 1)
			throw new Exception("Id allegato errato!");
		// checkPermission(idFascicolo); //se elimino un allegato in bozza per da parte
		// di ente territorialmente interessato, non ha competenza sul fascicolo
		try {
			RichiestePostTrasmissioneDetailDto detail = this.richPostTrasmissioneService.getDetail(idFascicolo, idRichiesta);
			if (detail!=null && 
				detail.getRichiestaBase().getStato()!=null && 
				detail.getRichiestaBase().getStato().equals(StatoRichiestaPostTrasmissione.Bozza.name())) {
				//è tra gli allegati???
				//controllo che l'allegato sia effettivamente della richiesta i-esima
				boolean isAllegato = detail.getAllegati().stream().filter(allegato->allegato.getIdRichiestaPostTrasmissione()!=null && allegato.getIdRichiestaPostTrasmissione().equals(idRichiesta)).findAny().isPresent();
				if(!isAllegato) {
					new CustomOperationToAdviceException("Allegato non appartenente alla richiesta corrente, impossibile eliminare!!!");
				}
				allegatoService.eliminaAllegato(idAllegato);
			}
		} catch (EmptyResultDataAccessException e) {
			return super.okResponse(false);
		}
		return super.okResponse(true);
	}
	
	/**
	 * Restituisce i dettagli di una richiesta in base al suo id. L'id del fascicolo è necessario per effettuare controlli di sicurezza.
	 * 
	 * @author Raffaele Del Basso
	 * @date 01 set 2021
	 * @param idRichiesta
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/dettaglio")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<RichiestePostTrasmissioneDetailDto>> getDettaglio(
			@RequestParam(required = true) Long idFascicolo,
			@RequestParam(required = true) Long idRichiesta) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_);
		if (idRichiesta == null || idFascicolo == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		return super.okResponse(richPostTrasmissioneService.getDetail(idFascicolo, idRichiesta));
	}
	
	/**
	 * Modifica i dati di una richiesta.
	 * 
	 * @author Raffaele Del Basso
	 * @date 01 set 2021
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/modifica")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<RichiestePostTrasmissioneDTO>> update(
			@RequestBody RichiestePostTrasmissioneDTO bean) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_);
		if (bean == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		return super.okResponse(richPostTrasmissioneService.update(bean));
	}
	
	/**
	 * Elimina una richiesta in base al suo id. L'id del fascicolo è necessario per effettuare controlli di sicurezza.
	 * 
	 * @author Raffaele Del Basso
	 * @date 01 set 2021
	 * @param idFascicolo
	 * @param idRichiesta
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "/elimina")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> delete(
			@RequestParam(required = true) Long idFascicolo,
			@RequestParam(required = true) Long idRichiesta) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_);
		if (idRichiesta == null || idFascicolo == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		try {
			return super.okResponse(richPostTrasmissioneService.delete(idFascicolo, idRichiesta));	
		} catch (CustomOperationToAdviceException e) {
			return super.okResponse(false);
		}
		
	}

	
	/**
	 * trasmette una richiesta a stato bozza con conseguente generazione ed invio della maile passaggio a stato Trasmessa
	 * 
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/trasmetti")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<RichiestePostTrasmissioneDTO>> trasmettiRichiesta(
			@RequestParam(required = true) Long idFascicolo,
			@RequestParam(required = true) Long idRichiestaPostTrasmissione) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_);
		if (idRichiestaPostTrasmissione == null || idFascicolo == null) {
			throw new CustomOperationToAdviceException("Parametri mancanti o non corretti.");
		}
		return super.okResponse(richPostTrasmissioneService.trasmettiRichiesta(idFascicolo, idRichiestaPostTrasmissione));
	}

}