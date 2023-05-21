package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ParereSoprintendenzaDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;

public interface ParereSoprintendenzaService
{
	/**
	 * Metodo che permette di ottenere i dati del parere soprintendenza associata ad una pratica
	 * @param idPratica
	 * @return Istanza di {@link ParereSoprintendenzaDetailsDTO}
	 * @throws Exception
	 */
	ParereSoprintendenzaDetailsDTO find(UUID idPratica) throws Exception;
	/**
	 * Inserimento completo di tutti i dati del parere, questo metodo allega anche la seduta alla pratica
	 * @param file
	 * @param entity
	 * @return Istanza di {@link ParereSoprintendenzaDetailsDTO}
	 * @throws Exception
	 */
	//ParereSoprintendenzaDetailsDTO insertAll(MultipartFile file, ParereSoprintendenzaDTO entity) throws Exception;
	/**
	 * Inserisce i dati del parere senza preoccuparsi degli allegati
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	ParereSoprintendenzaDTO insert(ParereSoprintendenzaDTO entity) throws Exception;
	/**
	 * Aggiorna i dati del parere senza preoccuparsi degli allegati
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	ParereSoprintendenzaDTO update(ParereSoprintendenzaDTO entity) throws Exception;
	/**
	 * Elimina un parere della soprintendenza se questa non è stata allegata ad una pratica.
	 * Il metodo si occua anche della cancellazione degli allegati associati
	 * @param idParere
	 * @return 
	 * @throws Exception
	 */
	Integer delete(Long idParere) throws Exception;
	/**
	 * Metodo che allega un file di tipo "Parere_MIBAC" al parere della soprintendenza
	 * @param file
	 * @param idParere
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadAllegato(MultipartFile file, Long idParere) throws Exception;
	/**
	 * Metodo che allega un file con i tipi associati al parere della soprintendenza
	 * @param file
	 * @param tipiContenuto
	 * @param idParere
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadAllegato(MultipartFile file, List<Integer> tipiContenuto,  Long idParere) throws Exception;
	/**
	 * Metodo che elimina uno specifico allegato del parere
	 * @param idAllegato
	 * @param parere
	 * @throws Exception
	 */
	void deleteAllegato(UUID idAllegato, Long parere) throws Exception;
	/**
	 * Crea una comunicazione associata al parere della soprintendenza
	 * @param idParere
	 * @return
	 */
	DettaglioCorrispondenzaDTO creaComunicazione(Long idParere) throws Exception;
	/**
	 * Metodo utile a completare l'iter allegando definitivamente il parere alla pratica
	 * @param idParere
	 * @throws Exception
	 */
	ParereSoprintendenzaDTO allegaParere(ParereSoprintendenzaDTO entity) throws Exception;
}
