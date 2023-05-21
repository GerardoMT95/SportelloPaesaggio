/**
 * 
 */
package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AnnotazioniInterneDetailDto;
import it.eng.tz.puglia.autpae.generated.orm.dto.AnnotazioniInterneDTO;
import it.eng.tz.puglia.autpae.generated.orm.search.AnnotazioniInterneSearch;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * @author Raffaele Del Basso, Marta Zecchillo
 * @date 07 set 2021
 */
public interface AnnotazioniInterneService {
	
	/**
	 * Inserisce una nuova annotazione interna nel DB
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	public AnnotazioniInterneDTO create(Long idFascicolo) throws Exception;
	
	/**
	 * Elimina una annotazione interna dal DB
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param idFascicolo
	 * @param idAnnotazioneInterna
	 * @return
	 */
	public boolean delete(Long idFascicolo, Long idAnnotazioneInterna) throws Exception;
	
	/**
	 * Aggiorna una annotazione interna nel DB
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public AnnotazioniInterneDTO update(AnnotazioniInterneDTO dto) throws Exception;
	
	/**
	 * Ricerca annotazioni in base ai parametri di ricerca passati
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param dtoSearch
	 * @return
	 */
	public PaginatedList<AnnotazioniInterneDTO> search(AnnotazioniInterneSearch dtoSearch);

	/**
	 * restituisce il dettaglio di una specifica annotazione interna
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param idFascicolo
	 * @param idAnnotazioneInterna
	 * @return
	 */
	public AnnotazioniInterneDetailDto getDetail(Long idFascicolo, Long idAnnotazioneInterna) throws Exception;

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
	public AllegatoCustomDTO caricaAllegato(List<Long> fascicoli, long idAnnotazioneInterna, MultipartFile file);

	/**
	 * Restituisce solo gli allegati di una determinata annotazione.
	 * 
	 * @author Raffaele Del Basso, Marta Zecchillo
	 * @date 08 set 2021
	 * @param idFascicolo
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<AllegatoCustomDTO> getDetailSoloAllegati(Long idFascicolo, long id) throws Exception;

}
