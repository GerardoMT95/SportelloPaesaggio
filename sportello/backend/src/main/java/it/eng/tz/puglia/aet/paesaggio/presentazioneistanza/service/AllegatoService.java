package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;

public interface AllegatoService {
	
	/**
	 * id temporaneo del cms utilizzato per la migrazione dei vecchi allegati nei BLOB
	 */
	final String PLACEHOLDERTEMPIDCMS="PLACEHOLDERTEMPIDCMS";
	
	/**
	 * caricamento documenti di riconoscimento di un referente (richiedente, altri titolari, tecnico incaricato)
	 * @param praticaId
	 * @param referenteId
	 * @param file
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 */
	AllegatiDTO uploadAllegatoReferente(String praticaId, Long referenteId, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;

	/**
	 * 
	 * @param praticaId
	 * @param referenteId
	 * @return
	 * @throws CustomOperationToAdviceException
	 */
	AllegatiDTO getDocumentoReferente(String praticaId, Long referenteId) throws CustomOperationToAdviceException;
	
	AllegatiDTO getDocumentoReferente(String praticaId, Long referenteId, boolean txWrite) throws CustomOperationToAdviceException;
	
	AllegatiDTO getDocumentoReferente(String praticaId, Long referenteId, boolean txWrite, boolean batch) throws CustomOperationToAdviceException;



	/**
	 * per effettuare il download di qualsiasi allegato
	 * @param idAllegato
	 * @return
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 * @throws CustomCmisException
	 */
	CmsDownloadResponseBean downloadAllegato(UUID idAllegato)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException;
	/**
	 * per effettuare il download di un allegato di seduta di commissione
	 * @param idAllegato
	 * @param idSeduta
	 * @return
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 * @throws CustomCmisException
	 */
	CmsDownloadResponseBean downloadAllegato(UUID idAllegato, Long idSeduta)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException;

	/**
	 * elimina l'allegato del documento di riconoscimento del referente (se lo stato lo ammette)
	 * @param praticaId
	 * @param referenteId
	 * @throws CustomOperationToAdviceException
	 * @throws CustomCmisException
	 */
	void deleteDocumentoReferente(String praticaId, Long referenteId)
			throws CustomOperationToAdviceException, CustomCmisException;

	/**
	 * 
	 * @param praticaId
	 * @throws CustomCmisException
	 * @throws CustomOperationToAdviceException
	 */
	void deleteAllAllegatiPratica(UUID praticaId) throws CustomCmisException, CustomOperationToAdviceException;
	
	/**
	 * caricamento allegati istanza tranne che per i documenti di riconoscimento, sui quali c'è il metodo 
	 *  {@link AllegatoService.uploadAllegatoReferente}
	 * @param praticaId
	 * @param tipiContenuto id dei tipi contenuto a cui fa capo il file (solo la documentazione tecnica puo' averne piu' di 1)
	 * @param nomeContenuto (nome assegnato manualmente dall'utente al contenuto solo in caso di documentazione tecnica)
	 * @param file
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 */
	AllegatiDTO uploadAllegato(String praticaId, List<Integer> tipiContenuto,String nomeContenuto, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;
	
	/**
	 * caricamento allegati istanza tranne che per i documenti di riconoscimento, sui quali c'è il metodo 
	 *  {@link AllegatoService.uploadAllegatoReferente}
	 * @param praticaId
	 * @param tipiContenuto id dei tipi contenuto a cui fa capo il file (solo la documentazione tecnica puo' averne piu' di 1)
	 * @param nomeContenuto (nome assegnato manualmente dall'utente al contenuto solo in caso di documentazione tecnica)
	 * @param integrazioneId id dell'integrazione cui si vuole associare un nuovo file
	 * @param file
	 * @param isSigned indica se il file è firmato digitalmente
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 */
	AllegatiDTO uploadAllegato(String praticaId, List<Integer> tipiContenuto,String nomeContenuto, Integer integrazioneId, MultipartFile file, Boolean isSigned)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;
	
	/**
	 * caricamento allegati istanza tranne che per i documenti di riconoscimento, sui quali c'è il metodo 
	 *  {@link AllegatoService.uploadAllegatoReferente}
	 * @param praticaId
	 * @param tipiContenuto id dei tipi contenuto a cui fa capo il file (solo la documentazione tecnica puo' averne piu' di 1)
	 * @param nomeContenuto (nome assegnato manualmente dall'utente al contenuto solo in caso di documentazione tecnica)
	 * @param integrazioneId id dell'integrazione cui si vuole associare un nuovo file
	 * @param praticaPreProtocollazione
	 * @param isSigned indica se il file è firmato digitalmente
	 * @param file
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 */
	AllegatiDTO uploadAllegato(String praticaId, List<Integer> tipiContenuto, String nomeContenuto, Integer integrazioneId, UUID praticaPreProtocollazione, MultipartFile file, Boolean isSigned)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;

	/**
	 * caricamento allegati commissione locale
	 * @param tipiContenuto id dei tipi contenuto a cui fa capo il file (solo la documentazione tecnica puo' averne piu' di 1)
	 * @param nomeContenuto (nome assegnato manualmente dall'utente al contenuto solo in caso di documentazione tecnica)
	 * @param file
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws IOException
	 * @throws CustomValidationException
	 */
	AllegatiDTO uploadAllegatoSeduta(List<Integer> tipiContenuto, String nomeContenuto,  MultipartFile file, String nomeSeduta)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;
	
	/**
	 * Carica l'allegato preoccupandosi di caricare anche il record nella tabella intermedia
	 * 
	 * @param praticaId
	 * @param tipiContenuto
	 * @param nomeContenuto
	 * @param file
	 * @param idRichiesta
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadAllegatoASR(String praticaId, List<Integer> tipiContenuto,String nomeContenuto, MultipartFile file, Long idRichiesta) throws Exception;

	/**
	 * 
	 * @param praticaId
	 * @param idDelegato
	 * @param file
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadDelegaAllegato(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file) throws Exception;
	
	/**
	 * 
	 * @author Simone Verna
	 * @date 5 lug 2022
	 * @param pratica
	 * @param indiceDelegato
	 * @param nomeContenuto
	 * @param file
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadDelegaAllegato(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file, boolean subentro) throws Exception;
	
	/**
	 * 
	 * @author Simone Verna
	 * @date 4 lug 2022
	 * @param pratica
	 * @param indiceDelegato
	 * @param nomeContenuto
	 * @param file
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadAllegatoSollevamentoIncarico(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file) throws Exception;
	
	/**
	 * 
	 * @author Simone Verna
	 * @date 5 lug 2022
	 * @param pratica
	 * @param indiceDelegato
	 * @param nomeContenuto
	 * @param file
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadAllegatoSollevamentoIncarico(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file, boolean subentro) throws Exception;

	
	/**
	 * delete allegato non collegato a nessuna pratica
	 * @param idOrganizzazione
	 * @param allegatoId
	 * @throws CustomOperationToAdviceException
	 * @throws CustomCmisException
	 */
	void deleteAllegato(String allegatoId) throws CustomOperationToAdviceException, CustomCmisException;
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param allegatoId
	 * @param raiseException se a false raccoglie eccezione su cancellazione su cms e prosegue
	 * @throws CustomOperationToAdviceException
	 * @throws CustomCmisException
	 */
	void deleteAllegato(String allegatoId,boolean raiseException) throws CustomOperationToAdviceException, CustomCmisException;
	
	/**
	 * delete allegato pratica tranne che per i documento di riconoscimento dei referenti. Effettua check sullo stato
	 * @param praticaId
	 * @param allegatoId
	 * @throws CustomOperationToAdviceException
	 * @throws CustomCmisException
	 */
	void deleteAllegato(String praticaId, String allegatoId) throws CustomOperationToAdviceException, CustomCmisException;
	
	/**
	 * delete allegato pratica tranne che per i documento di riconoscimento dei referenti. 
	 * Effettua check sulla base di una cancellazione di allegato di integrazione documentale se integrazioneId non nullo 
	 * @param praticaId
	 * @param allegatoId
	 * @param integrazioneId nullable...
	 * @throws CustomOperationToAdviceException
	 * @throws CustomCmisException
	 */
	void deleteAllegato(String praticaId, String allegatoId,Integer integrazioneId) throws CustomOperationToAdviceException, CustomCmisException;

	/**
	 * fetch degli allegati per sezione
	 * @param idPratica 
	 * @param docAmministrativaD
	 * @return
	 * @throws CustomOperationToAdviceException 
	 */
	List<AllegatiDTO> getAllegati(UUID idPratica, SezioneAllegati sezione) throws CustomOperationToAdviceException;
	
	List<AllegatiDTO> getAllegatiBatch(UUID idPratica, SezioneAllegati sezione) throws CustomOperationToAdviceException;
	
	/**
	 * fetch degli allegati per sezione che corrispondono ad una spceifica integrazione
	 * @param idPratica 
	 * @param docAmministrativaD
	 * @param idIntegrazione
	 * @return
	 * @throws CustomOperationToAdviceException 
	 */
	List<AllegatiDTO> getAllegati(UUID idPratica, SezioneAllegati sezione, Integer idIntegrazione) throws CustomOperationToAdviceException;
	
	/**
	 * fetch degli allegati per sezione ma con dei controlli differenti per la competenza
	 * @param idPratica
	 * @param sezione
	 * @return
	 * @throws Exception
	 */
	List<AllegatiDTO> getAllegatiIstruttoria(UUID idPratica, SezioneAllegati sezione) throws Exception;
	
	/**
	 * fetch degli allegati per sezione ma con dei controlli differenti per la competenza
	 * @param idPratica
	 * @param sezione
	 * @param idIntegrazione
	 * @return
	 * @throws Exception
	 */
	List<AllegatiDTO> getAllegatiIstruttoria(UUID idPratica, SezioneAllegati sezione, Integer idIntegrazione) throws Exception;
	
	/**
	 * Metodo che carica solamente l'allegato, senza preoccuparsi della tipologia di riferimento
	 * @param praticaId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	AllegatiDTO uploadAllegatiComunicazione(UUID praticaId, MultipartFile file) throws Exception;
	
	/**
	 * Elimino tutti gli allegati di una pratica appartenenti ad una determinata sezione
	 * @param praticaId
	 * @param sezione
	 * @return
	 * @throws Exception
	 */
	Integer deleteAllOfType(UUID praticaId, SezioneAllegati sezione) throws Exception;

	/*
	 * Salvataggio degli allegati per la relazione
	 * 
	 */
	AllegatiDTO uploadAllegatiRelazione(UUID praticaId, MultipartFile file, String tipo, long idRelazione)
			throws Exception;
	
	boolean deleteAllegatoRelazione(UUID idFile) throws Exception;

	CmsDownloadResponseBean downloadAllegatoGenerico(UUID idAllegato)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException;

	AllegatiDTO uploadLogoTemplate(Integer tipoContenuto, String nomeContenuto, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;
	
	
	/**
	 * utilizzato per i download diretto di un file dal cms.. non effettua alcun check di accesso
	 * utilizzare con cura....
	 * @author acolaianni
	 *
	 * @param idCms
	 * @param response
	 * @throws Exception
	 */
	public void doDownloadFromCms(String idCms,HttpServletResponse response) throws Exception;

	AllegatiDTO uploadAllegatiComunicazione(UUID praticaId, MultipartFile file, String subpath) throws Exception;

	List<AllegatiDTO> getAllegati(UUID idPratica, SezioneAllegati sezione, Integer idIntegrazione,
			Integer idTipoContenuto) throws CustomOperationToAdviceException;

	AllegatiDTO getDocumentoReferenteTxWrite(String praticaId, Long referenteId)
			throws CustomOperationToAdviceException;
	
	AllegatiDTO getDocumentoReferenteTxWrite(String praticaId, Long referenteId, boolean batch)
			throws CustomOperationToAdviceException;

	/**
	 * non effettua check e non aggiorna il record del referente
	 * scrive record allegato e fa upload.
	 * @author acolaianni
	 *
	 * @return
	 */
	AllegatiDTO uploadAllegatoReferenteDaMigrazione(PraticaDTO pratica, ReferentiDTO referente, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException;

	/**
	 * non effettua controlli di alcun tipo, da utilizzare solo per la migrazione
	 * @author acolaianni
	 *
	 * @param file
	 * @param tipiContenuto
	 * @param nomeContenuto
	 * @param pathAlfresco
	 * @param praticaId
	 * @param idIntegrazione
	 * @param allegatoPreProtocollazione
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws IOException
	 */
	AllegatiDTO uploadAllegatoDaMigrazione(MultipartFile file, List<Integer> tipiContenuto, String nomeContenuto,
			String pathAlfresco, UUID praticaId, Integer idIntegrazione, UUID allegatoPreProtocollazione)
			throws CustomCmisException, CustomOperationException, IOException;

	/**
	 * solo per la migrazione, non effettua insert a DB
	 * @author acolaianni
	 *
	 * @param file
	 * @param path
	 * @return
	 */
	AllegatiDTO doUploadPerMigrazione(MultipartFile file, String path)throws CustomCmisException, CustomOperationToAdviceException, IOException ;

	/**
	 * Non effettua alcun check di accesso
	 * @author acolaianni
	 *
	 * @param pratica
	 * @param sezione
	 * @param idIntegrazione nullable
	 * @param idTipoContenuto nullable
	 * @return
	 */
	List<AllegatiDTO> getAllegati(PraticaDTO pratica, SezioneAllegati sezione, Integer idIntegrazione,
			Integer idTipoContenuto);

	/**
	 * restituisce il path alfresco se esiste altrimenti null
	 * @author acolaianni
	 *
	 * @param idCms
	 * @return
	 * @throws Exception
	 */
	String getPathAlfrescoByCmisid(String idCms) throws Exception;

	/**
	 * aggiorna campo descrizione e titolo per il caso di ulteriore documentazione
	 * @author acolaianni
	 *
	 * @param id
	 * @param descrizione
	 * @param titolo
	 * @return
	 * @throws Exception
	 */
	int updateDescrizioneTitolo(UUID id, String descrizione, String titolo) throws Exception;

}
