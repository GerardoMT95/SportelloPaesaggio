package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.dto.InfoPlaceHoldersDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface CorrispondenzaService {
	
	static String KEY_URL_DOWNLOAD_PREFIX="URL_DOWNLOAD_PREFIX";
	/**
	 * Rotta gestita da shibbolet, redirecta sulla pagina di autenticazione
	 * a livello di portale senza token wso2
	 */
	static String BASE_PUBLIC_DOWNLOAD = "downloadDocumentoDaMail";
	static String PUBLIC_DOWNLOAD_ULT_DOC = BASE_PUBLIC_DOWNLOAD+"/documentoUlterioreDoc";
	static String PUBLIC_DOWNLOAD_DOC_TRASM = BASE_PUBLIC_DOWNLOAD+"/ricevutaTrasmissione";
	static String PUBLIC_DOWNLOAD_ALLEGATO_COMUNICAZIONE = BASE_PUBLIC_DOWNLOAD+"/allegatoComunicazione";
	static String PUBLIC_DOWNLOAD_PROVVEDIMENTO_FINALE=BASE_PUBLIC_DOWNLOAD+"/provvedimentoFinale";
	
	// BaseRepository
//	public List<CorrispondenzaDTO> select() throws Exception;
	public long count(CorrispondenzaSearch filter) throws Exception;
	public CorrispondenzaDTO find(Long pk) throws Exception;
	public Long insert(CorrispondenzaDTO entity) throws Exception;
	public int update(CorrispondenzaDTO entity) throws Exception;
	public int delete(CorrispondenzaSearch search) throws Exception;
	public PaginatedList<CorrispondenzaDTO> search(CorrispondenzaSearch filter) throws Exception;
	// FullRepository
	public int updateMessageId(Long idCorrispondenza, String messageId) throws Exception;
	public int updateBozza(Long idCorrispondenza, Boolean bozza) throws Exception;
	public int updateMittenteUsernameAndMittenteDenominazione(Long idCorrispondenza, String mittenteUsername,String mittenteDenominazione,String mittenteEnte) throws Exception;
	
	
	
//	public List<DettaglioCorrispondenzaDTO> datiCorrispondenza(long idFascicolo, boolean comunicazioni) throws Exception;

	public DettaglioCorrispondenzaDTO  dettaglioCorrispondenza(long idCorrispondenza, boolean full_info) throws Exception;

	public PaginatedList<DettaglioCorrispondenzaDTO> searchCorrispondenza(CorrispondenzaSearch filters) throws Exception;

	public DettaglioCorrispondenzaDTO  inviaOsalvaComunicazione(NuovaComunicazioneDTO comunicazione) throws Exception;

	public boolean cancellaBozza(long idCorrispondenza) throws Exception;

	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 24 ago 2021
	 * @param idFascicoli
	 * @param idCorrispondenza
	 * @param file
	 * @param isUrl true se verrà inviato sotto forma di link
	 * @return
	 * @throws Exception
	 */
	public AllegatoCustomDTO caricaAllegato(List<Long> idFascicoli, long idCorrispondenza, MultipartFile file,boolean isUrl) throws Exception;

	public String checkEmail(String email);

	/**
	 * 
	 * se isBozza==false parte immediatamente l'invia mail...
	 * @param corrispondenza
	 * @return
	 * @throws Exception
	 */
	public Long inviaCorrispondenza(NuovaComunicazioneDTO corrispondenza) throws Exception;
	public Long inviaCorrispondenza(NuovaComunicazioneDTO corrispondenza, boolean aggiungiDestinatariDaTemplate) throws Exception;
	
	/**
	 * 
	 * @author Luca Rosario Mazzola
	 * @date 20 apr 2022
	 * @param corrispondenza
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public Long inviaCorrispondenza(NuovaComunicazioneDTO corrispondenza, InformazioniDTO info) throws Exception;
	
	public NuovaComunicazioneDTO getTemplate(long idFascicolo) throws Exception;
	
	/**
	 * la corrispondenza deve essere già salvata su DB 
	 * @param corrispondenza
	 * @throws Exception
	 */
	void inviaMail(NuovaComunicazioneDTO corrispondenza) throws Exception;
	
	/**
	 * codice : {CODICE}
	 * @param codice
	 * @param infoPlaceHolders
	 * @return
	 * @throws Exception
	 */
	String risolviSingoloPH(String codice, InfoPlaceHoldersDTO infoPlaceHolders) throws Exception;
	
	/** per migrazione
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @param entity
	 * @param forMigration
	 * @return
	 * @throws Exception
	 */
	Long insert(CorrispondenzaDTO entity, boolean forMigration) throws Exception;
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 31 ago 2021
	 * @param corrispondenza deve essere avvalorato infoPlaceholder.idFascicolo per 
	 * far si che carichi tutti i dati necessari per valorizzare i placeholder tipici del fascicolo
	 * @param aggiungiDestinatariDaTemplate
	 * @param allegatiPrecaricati se a true non inserisce in tabella allegati e alfresco, ma solo in allegato_corrispondenza.
	 * @return
	 * @throws Exception
	 */
	Long inviaCorrispondenza(NuovaComunicazioneDTO corrispondenza, boolean aggiungiDestinatariDaTemplate,
			boolean allegatiPrecaricati, InformazioniDTO info) throws Exception;
	/**
	 * aggiunge in fondo al corpo della corrispondenza il paragrafo relativo agli url degli allegati logici (isUrl)
	 * comunicazione.id deve essere valorizzato
	 * comunicazione.getIdFascicoli deve contenere al 1 elemento il fascicolo 
	 * 
	 * @autor Adriano Colaianni
	 * @date 31 ago 2021
	 * @param comunicazione id deve essere valorizzato e idFascicoli deve contenere almeno un elemento  
	 * @param dettCorr deve contenere la lista AllegatiInfo non vuota
	 * @throws SitPugliaConfigurationException
	 * @throws Exception 
	 */
	void aggiungiUrlAllegatiInCorpo(NuovaComunicazioneDTO comunicazione, DettaglioCorrispondenzaDTO dettCorr)
			throws  Exception;
	
	Long inviaCorrispondenza(NuovaComunicazioneDTO corrispondenza, boolean aggiungiDestinatariDaTemplate,
			boolean allegatiPrecaricati) throws Exception;
}