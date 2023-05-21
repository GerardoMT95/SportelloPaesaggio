package it.eng.tz.puglia.autpae.service.interfacce;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaNumeriDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.autpae.utility.FileWrapper;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;


public interface AllegatoService {
	
	final String PLACEHOLDERTEMPIDCMS="PLACEHOLDERTEMPIDCMS";

	// BaseRepository
//	public List<AllegatoDTO> select() throws Exception;
	public long count(AllegatoSearch filter) throws Exception;
	public AllegatoDTO find(Long pk) throws Exception;
	public Long insert(AllegatoDTO entity) throws Exception;
	public int update(AllegatoDTO entity) throws Exception;
	public int delete(AllegatoSearch search) throws Exception;
	public PaginatedList<AllegatoDTO> search(AllegatoSearch filter) throws Exception;
	// FullRepository
//	public Long findIdByIdAlfresco(String idAlfresco) throws Exception;
	public String findIdAlfrescoById(Long id) throws Exception;
	public List<AllegatoDTO> datiAllegato(Long idFascicolo, List<TipoAllegato> listaTipi) throws Exception;
//	public int aggiorna(long idAllegato, String descrizione, String titolo) throws Exception;
	public int protocolla(long idAllegato, String protocolloIn, String protocolloOut) throws Exception;
//	public List<TipologicaNumeriDTO> nomeAllegatiCorrispondenza(Long idCorrispondenza) throws Exception;
	public List<AllegatoCustomDTO> infoAllegatiCorrispondenza(Long idCorrispondenza) throws Exception;
	public TipologicaNumeriDTO cercaRecente(AllegatoSearch filter, List<Long> listaId) throws Exception;
//	public PaginatedList<UlterioreDocumentazioneDTO> searchUlterioreDocumentazione(UlterioreDocumentazioneSearch filter) throws Exception;
	public PaginatedList<Long> searchIdDocumentiUD(UlterioreDocumentazioneSearch filter) throws Exception;
	public ArrayList<AllegatoCustomDTO> tabAllegati(TipoProcedimento tipoProcedimento) throws Exception;

	public ArrayList<AllegatoCustomDTO> datiAllegati(long idFascicolo) throws Exception;

	public AllegatoCustomDTO inserisciAllegato(List<Long> idFascicoli, 		TipoAllegato  tipoAllegato, MultipartFile file, AllegatoDTO informazioni) throws Exception;
	public AllegatoCustomDTO inserisciAllegato(List<Long> idFascicoli, List<TipoAllegato> tipiAllegato, MultipartFile file, AllegatoDTO informazioni) throws Exception;
	/**
	 * 
	 * @param idFascicoli
	 * @param tipiAllegato
	 * @param file
	 * @param informazioni
	 * @param pathCms da concatenare al base path dell'applicazione... 
	 * es. configurazione/template_doc/LETTERA_TRASMISSIONE   =>  
	 *   diventa /autpae/configurazione/template_doc/LETTERA_TRASMISSIONE/filename.txt
	 *                  ^--aggiunto                                      ^--aggiunto
	 * @return
	 * @throws Exception
	 */
	public AllegatoCustomDTO inserisciAllegato(List<Long> idFascicoli, List<TipoAllegato> tipiAllegato, MultipartFile file,	AllegatoDTO informazioni, String pathCms) throws Exception;

	public void eliminaAllegato(long idAllegato) throws Exception;
	/**
	 * non effettua alcun controllo sulle relazioni che ha con fascicolo / ente / corrispondenza / etc...
	 * @param idAllegato
	 * @throws Exception
	 */
	public void eliminaAllegatoWithoutCheck(long idAllegato) throws Exception;
	
	public void eliminaAllegati(List<Long> listaAllegati) throws Exception;
	
	//public String alfrescoUpload(MultipartFile multipartFile, String codice) throws Exception;

	public void alfrescoDelete(long idAllegato) throws Exception;

	public AllegatoCustomDTO infoAllegato(long idAllegato) throws Exception;
	
	public List<SelectOptionDto> validazione(long idFascicolo, TipoProcedimento tipoProcedimento) throws Exception;

	public TipologicaNumeriDTO findRicevutaTrasmissione(long id) throws Exception;
	public File downloadAlfresco(String idAlfresco) throws Exception;
	
	/**
	 * 
	 * @param file
	 * @param informazioni
	 * @param tipo
	 * @param bean nullable
	 * @return
	 * @throws Exception
	 */
	public String protocolla(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,ProtocolloRequestBean bean) throws Exception;
	/**
	 * per salvare il pdf pre timbratura... viene salvato con un tipo predefinito e su un path condiviso...
	 * @param idFascicoli
	 * @param file
	 * @param informazioni
	 * @return
	 * @throws Exception
	 */
	public AllegatoCustomDTO inserisciPdfPreTimbratura(List<Long> idFascicoli, MultipartFile file, AllegatoDTO informazioni)
			throws Exception;
	/**
	 * @autor Adriano Colaianni
	 * @date 13 apr 2021
	 * @param file
	 * @param informazioni
	 * @param tipo
	 * @param beanProto
	 * @return
	 * @throws Exception
	 */
	SegnaturaProtocollo protocollaEgetSegnatura(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,
			ProtocolloRequestBean beanProto) throws Exception;
	
	/**
	 * A partire dal Blob viene scritto localmente in localBasePath/migrazioneSubPath/CODICE_PRATICA/.....(come solito calcolo path a partire da codice pratica)
	 * NON viene inviato su cms e al posto del idCms ci sarà placeholder
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param fascicolo
	 * @param tipiAllegato
	 * @param file
	 * @param nomeFile
	 * @param nomeDocumento
	 * @param contentType
	 * @param migrazioneSubPath
	 * @param localBasePath
	 * @param protocolloOut nullable
	 * @param dataProtocolloOut nullable
	 * @param dataArrivo
	 * @param subPathCategoriaAllegato nullable oppure es. 56789  utilizzato in allegati mail o
	 * @param noAllegati se a true simula anche il prelievo dal blob, utilizzato per i soli metadati della procedura. 
	 * allegati ricevute, viene messo id dell'entita per creare path univoco in caso di + mail con un allegato che ha stesso nomefile.
	 * @return
	 * @throws Exception
	 */
	AllegatoDTO inserisciAllegatoDaMigrazione(FascicoloDTO fascicolo, List<TipoAllegato> tipiAllegato, Blob file,
			String nomeFile, String nomeDocumento, String contentType, String migrazioneSubPath, String localBasePath,
			String protocolloOut, Date dataProtocolloOut, Date dataArrivo,String subPathCategoriaAllegato,boolean noAllegati) throws Exception;
	
	
	/**
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param idFascicolo
	 * @param idRichiestaPostTrasmissione
	 * @return
	 * @throws Exception
	 */
	List<AllegatoDTO> infoAllegatiRichiestaPostTrasmissione(Long idFascicolo, Long idRichiestaPostTrasmissione)
			throws Exception;
	
	/**
	 * Ricerca gli allegati di una annotazione interna
	 * 
	 * @autor Raffaele Del Basso, Marta Zecchillo
	 * @date 07 set 2021
	 * @param idFascicolo
	 * @param idAnnotazioneInterna
	 * @return
	 * @throws Exception
	 */
	public List<AllegatoDTO> infoAllegatiAnnotazioniInterne(Long idFascicolo, Long idAnnotazioneInterna) throws Exception;
	/**
	 * come il find ma senza eccezioni... 
	 * @autor Adriano Colaianni
	 * @date 15 set 2021
	 * @param idFascicolo
	 * @return se non la trova  null
	 */
	TipologicaNumeriDTO getRicevutaTrasmissione(long idFascicolo);
	
	/**
	 * @autor Adriano Colaianni
	 * @date 17 ott 2021
	 * @param tipoAllegato
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	FileWrapper downloadAllegatoByTipoEidFascicolo(TipoAllegato tipoAllegato, Long idFascicolo) throws Exception;
	
	/**
	 * @autor Adriano Colaianni
	 * @date 22 ott 2021
	 * @param tipoAllegato
	 * @param idRicevuta
	 * @return
	 * @throws Exception
	 */
	File downloadRicevutaMail(TipoAllegato tipoAllegato, Long idRicevuta) throws Exception;
	
	/**
	 * @autor Adriano Colaianni
	 * @date 22 ott 2021
	 * @param tipoAllegato
	 * @param idAllegato
	 * @param idFascicolo
	 * @param idCorrispondenza
	 * @return
	 * @throws Exception
	 */
	File downloadFile(TipoAllegato tipoAllegato, Long idAllegato, Long idFascicolo, Long idCorrispondenza) throws Exception;
	
	/**
	 * download del contenuto senza alcun controllo di accesso
	 * @autor Adriano Colaianni
	 * @date 27 lug 2022
	 * @param idCmis
	 * @param response
	 * @throws Exception
	 */
	void downloadFile(String idCmis,HttpServletResponse response) throws Exception;
	
	
	

}