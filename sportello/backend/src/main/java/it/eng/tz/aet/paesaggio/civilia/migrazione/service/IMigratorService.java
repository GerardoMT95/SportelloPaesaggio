/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.service;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RicevutaDTO;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public interface IMigratorService {
	
	public static final Marker LOG_MIGRAZIONE_MARKER = MarkerFactory.getMarker("INFO_MIGRAZIONE");
	/**
	 * a partire da infoPratica effettua retrieve di tutti i dati e genera i record su DB presentazione istanza
	 * vengono trattate solo le pratiche con ENTEDELEGATO=00016!REGIONE PUGLIA (che dovrebbe essere l'unico attivo in produzione)
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param infoPratica
	 * @throws BaseMigrationException
	 * @throws Exception 
	 */
	public void migraPratica(VtnoAttivitaPptr infoPratica) throws Exception;

	/**
	 * setta idCmis e update
	 * @author acolaianni
	 *
	 * @param allDTO
	 * @throws Exception
	 */
	void aggiornaAlfrescoId(AllegatiDTO allDTO) throws Exception;

	void aggiornaAlfrescoId(RicevutaDTO ricevutaDTO) throws Exception;

	void aggiornaAlfrescoId(CorrispondenzaDTO corr) throws Exception;

	/**
	 * aggiornamento qualifiaczione intervento solo per pratiche già migrate
	 * @author acolaianni
	 *
	 * @param infoPratica
	 * @throws Exception
	 */
	void aggiornaQualificazioneInterventoPratica(VtnoAttivitaPptr infoPratica) throws Exception;

	
}
