package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica;

import java.util.UUID;

/**
 * Interfaccia che stabilisce i metodi base da implementare per la realizzazione di
 * un service per un'entità figlia di scheda tecnica
 *
 * @param <T> Classe corrispondente al DTO che rappresentà l'entity
 */
public interface SchedaTecnicaChild<T>
{
	/**
	 * Metodo predisposto al salvataggio di un'entity di tipo {@link T}
	 * @param entity {@link T}
	 * @return entità {@link T} salvata
	 * @throws Exception
	 */
	T saveOrUpdate(T entity) throws Exception;
	
	
	/**
	 * Metodo predisposto alla validazione di un'entity di tipo {@link T}
	 * @param entity {@link T}
	 * @throws Exception che indica la causa della validazione fallita 
	 * in caso di errori di validazione da riportare all'utente su FE lanciare CustomOperationToAdviceException con 
	 * relativo messaggio di validazione codificato nel resource bundle (messages.properties)
	 * per adesso è implementato solo per alcuni validazioni che non vengono effettuate lato FE
	 */
	void valida(T entity) throws Exception;
	
	
	/**
	 * Metodo che effettua la ricerca sulla base di idFascicolo
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	T findByIdFascicolo(UUID idFascicolo) throws Exception;

	
	/**
	 * Metodo di cancellazione degli elementi corrispondenti ad un fascicolo
	 * @param idFascicolo {@link UUID}
	 * @return Numero di elementi eliminati o 0 se nessun elemento è stato toccato
	 * @throws Exception
	 */
	Integer delete(UUID idFascicolo) throws Exception;
	
}