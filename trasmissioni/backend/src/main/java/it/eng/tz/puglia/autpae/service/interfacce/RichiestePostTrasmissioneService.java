/**
 * 
 */
package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.RichiestePostTrasmissioneDetailDto;
import it.eng.tz.puglia.autpae.enumeratori.TipoRichiestaPostTrasmissione;
import it.eng.tz.puglia.autpae.generated.orm.dto.RichiestePostTrasmissioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.search.RichiestePostTrasmissioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * @author Adriano Colaianni
 * @date 30 ago 2021
 */
public interface RichiestePostTrasmissioneService {
	
	/**
	 * creazione solo se non esistono altre bozze
	 * Chiamata da FE quando si clicca su uno dei 2 button crea nuova richiesta
	 * //
		//se utente è owner fascicolo 
		 * @see it.eng.tz.puglia.autpae.service.FascicoloServiceImpl.checkPermission(Long)
		 * e @see it.eng.tz.puglia.autpae.service.GruppiRuoliServiceImpl.checkAbilitazioneForTrasmissione() 
		//TODO se fascicolo e' in stato in cui posso creare richiesta (NO WORKING e NO CANCELED)
		//se non ci sono altre richieste in bozza
		 
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param tipo
	 * @return
	 */
	public RichiestePostTrasmissioneDTO create(Long idFascicolo,TipoRichiestaPostTrasmissione tipo) throws Exception;
	
	/**
	 * permessa cancellazione solo per le bozze
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param idRichiestaPostTrasmissione
	 * @return
	 */
	public boolean delete(Long idFascicolo,Long idRichiestaPostTrasmissione) throws Exception;
	
	public RichiestePostTrasmissioneDTO update(RichiestePostTrasmissioneDTO dto) throws Exception;
	
	/**
	 * elenco paginato di richieste
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param dtoSearch
	 * @return
	 */
	public PaginatedList<RichiestePostTrasmissioneDTO> search(RichiestePostTrasmissioneSearch dtoSearch);
	
	/**
	 * passa a stato Trasmessa la bozza con id passato in input
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param idRichiestaPostTrasmissione
	 * @return
	 */
	public RichiestePostTrasmissioneDTO trasmettiRichiesta(Long idFascicolo,Long idRichiestaPostTrasmissione) throws Exception;
	
	/**
	 * restituisce il dettaglio comprensivo degli allegati
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param idRichiestaPostTrasmissione
	 * @return
	 */
	public RichiestePostTrasmissioneDetailDto getDetail(Long idFascicolo,Long idRichiestaPostTrasmissione) throws Exception;

	/**
	 * 
	 * @see 
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param singletonList
	 * @param idRichiesta
	 * @param file
	 * @return
	 */
	public AllegatoCustomDTO caricaAllegato(List<Long> fascicoli, long idRichiesta, MultipartFile file);

}
