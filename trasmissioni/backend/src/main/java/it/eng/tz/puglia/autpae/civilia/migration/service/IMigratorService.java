/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.service;

import java.io.IOException;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.exception.BaseMigrationException;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.error.exception.CustomCmisException;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public interface IMigratorService {
	
	
	/**
	 * questo marker nel log viene scritto in un appender ad hoc su file, 
	 * pertanto tutti gli eventi da registrare durante la migrazione andrebbero scritti con questo marker
	 */
	public static final Marker LOG_MIGRAZIONE_MARKER = MarkerFactory.getMarker("INFO_MIGRAZIONE");
	
	/**
	 * a partire da infoPratica effettua retrieve di tutti i dati e genera i record su DB autpae
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param infoPratica
	 * @throws BaseMigrationException
	 * @throws Exception 
	 */
	public void migraPratica(InfoAutPaesPptrAlfa infoPratica) throws Exception;
	
	/**
	 * a partire da infoPratica effettua retrieve di tutti i dati e genera i record per putt
	 * @autor Michele Santoro
	 * @date 6 lug 2021
	 * @param infoPratica
	 * @throws BaseMigrationException
	 * @throws Exception 
	 */
	void migraPraticaPutt(InfoAutPaesAlfaBean infoPratica) throws Exception;

	/**
	 * Aggiorna il valore dell'idCms utilizzando il path nel cms
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @param allDTO
	 * @throws Exception
	 */
	void aggiornaAlfrescoId(AllegatoDTO allDTO) throws Exception;

	/**
	 * aggiorna gli id cms della ricevuta pec
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @param allDTO
	 * @throws Exception
	 */
	void aggiornaAlfrescoId(RicevutaDTO allDTO) throws Exception;

	
	/**
	 * verifica se per ogni paesaggio_organizzazione per codice applicazione, esiste rispettivo GRUPPO
	 * @throws Exception 
	 * @autor Adriano Colaianni
	 * @date 3 mag 2021
	 */
	void checkGruppiProfileManager() throws Exception;

	/**
	 * migrazione degli enti delegati
	 * se non esiste il relativo ente delegato, creo in paesaggio organizzazione e creo il territorio di competenza.
	 * effettuo select sugli UFFICI
	 * @autor Adriano Colaianni
	 * @date 4 mag 2021
	 * @param listaUfficiGruppi
	 * @throws Exception
	 */
	void migraEnteDelegato(PptrGruppoUfficio pptrGruppoUfficio) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 9 set 2021
	 * @param infoPratica
	 * @param inLavorazione se a true inibisce alcuni controlli di obbligatorietà sui dati
	 * @throws Exception
	 */
	void migraPratica(InfoAutPaesPptrAlfa infoPratica, boolean inLavorazione) throws Exception;
	
	/**
	 * migra gli indirizzi mail degli enti destinatari della trasmissione e le SBAP
	 * @param isProduzione se a false cambia ultima parte con .iot
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @throws Exception
	 */
	void migraMailEntiESbap(boolean isProduzione) throws Exception;

	/**
	 * Caso di allegato caricati a 0 byte per errore su putt, vanno riscaricati dall'alfresco sitpuglia
	 * @autor Adriano Colaianni
	 * @date 28 nov 2021
	 * @param allegato
	 * @throws IOException 
	 * @throws CustomCmisException 
	 * @throws Exception 
	 */
	public void downloadFromAlfresco(AllegatoDTO allegato) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 7 dic 2021
	 * @param infoPratica
	 * @throws Exception
	 */
	void migraMailPraticaMigrataPptr(InfoAutPaesPptrAlfa infoPratica) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 7 dic 2021
	 * @param infoPratica
	 * @throws Exception
	 */
	void migraMailPraticaMigrataPutt(InfoAutPaesAlfaBean infoPratica) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 23 dic 2021
	 * @param allegato
	 * @throws Exception
	 */
	void rielaboraFileVuotoPptr(AllegatoDTO allegato) throws Exception;
	
}
