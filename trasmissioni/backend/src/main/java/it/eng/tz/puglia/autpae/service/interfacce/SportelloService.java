/**
 * 
 */
package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.autpae.generated.orm.dto.VPaesaggioProvvedimentiDTO;
import it.eng.tz.puglia.autpae.generated.orm.search.VPaesaggioProvvedimentiSearch;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * @author Adriano Colaianni
 * @date 25 lug 2022
 */
public interface SportelloService {

	/**
	 * fetch dei documenti su sportello ambiente potenzialmente importabili in un fascicolo
	 * @autor Adriano Colaianni
	 * @date 25 lug 2022
	 * @param idEnte id common.ente dell'ente delegato corrente a cui appartengo come utente 
	 * @param codiceFiscaleEnte  codiceFiscale del mio common.ente delegato a cui appartengo
	 * @param codiceTrasmissione codice trasmissione su sportello ambiente della pratica
	 * @return
	 * @throws Exception
	 */
	List<VPaesaggioProvvedimentiDTO> getProvvedimentiPotenziali(Long idEnte, String codiceFiscaleEnte,
			String codiceTrasmissione) throws Exception;

	
	/**
	 * 
	 * effettua transazione con cui preleva i dati del provvedimento 
	 * selezionato dallo sportello ambiente e popola la bozza all'idFascicolo.
	 * @autor Adriano Colaianni
	 * @date 25 lug 2022
	 * @param idEnte
	 * @param codiceFiscaleEnte
	 * @param codiceTrasmissione
	 * @param idProvvedimento
	 * @param idFascicolo
	 * @param username
	 * @throws Exception
	 * @return messaggio eventuale da dare all'utente
	 */
	String importProvvedimento(Long idEnte, String codiceFiscaleEnte, String codiceTrasmissione, UUID idProvvedimento,
			Long idFascicolo, String username) throws Exception;


	/**
	 * rimuove i mark dell'utilizzo dell'idProvvedimento, a seguito di rimozione fascicolo
	 * in modo da poterlo riutilizzare
	 * @autor Adriano Colaianni
	 * @date 25 lug 2022
	 * @param idProvvedimento nullable
	 * @param username
	 * @param idFascicolo
	 * @throws Exception
	 */
	void setLibero(UUID idProvvedimento, String username, Long idFascicolo) throws Exception;
	
	/**
	 * true se è stato già fatto import su questo fascicolo
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param idFascicolo
	 * @throws Exception
	 */
	boolean hasImport(Long idFascicolo) throws Exception;


	/**
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param search
	 * @return
	 * @throws Exception
	 */
	PaginatedList<VPaesaggioProvvedimentiDTO> search(VPaesaggioProvvedimentiSearch search) throws Exception;

}
