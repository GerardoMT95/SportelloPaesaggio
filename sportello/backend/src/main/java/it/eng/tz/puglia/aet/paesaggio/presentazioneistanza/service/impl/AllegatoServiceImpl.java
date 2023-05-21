package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AlfrescoPaths;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiTipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatoDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.IntegrazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiDocumentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.SospensioneArchiviazioneAttivazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferentiDocumentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.RelazioneEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CheckSumUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Primary
@Service
public class AllegatoServiceImpl extends PraticaDataAwareService implements AllegatoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AllegatoServiceImpl.class);
	private static final String LOG_UPLOAD_DOCUMENT = "uploadDocument";
	private static final String LOG_DOWNLOAD_DOCUMENT = "downloadDocument";
	private static final String LOG_DELETE_DOCUMENT = "deleteDocument";
	private final static String DESCR_FILE_DOCUMENTO_RICONOSCIMENTO = "ALLEGATO DOCUMENTO RICONOSCIMENTO";
	private final static String DESCR_FILE_ALLEGATO_COMUNICAZIONE = "ALLEGATO COMUNICAZIONE";
	
	

	@Value("${cms.url.upload}")
	private String uploadUrl;

	@Value("${cms.url.download}")
	private String downloadUrl;

	@Value("${cms.url.delete}")
	private String deleteUrl;
	
	@Value("${cms.url.getpath}")
	private String getPathUrl;

	@Value("${spring.application.name}")
	private String nomeApplicazione;

	@Value("${cms.path.base:PaesaggioPresentazioneIstanza}")
	private String rootPathCms;

	/**
	 * serve per non arrivare negli stessi path dato il codicePraticaAppptr
	 * replicabile sui vari env di sviluppo
	 */
	@Value("${cms.path.developer:}")
	private String subpathDeveloper;

	@Value("${path.cms}")
	private String pathTransito;
	
	/**
	 * non esiste nei file di properties, 
	 * va inserita a riga di comando solo in caso di esecuzione in test per la procedura di migrazione
	 */
	@Value("${migration.local.basepath:}")
	private String pathTransitoLocaleMigrazione;
	
	@Autowired
	IHttpClientService cmsService;

	@Autowired
	AllegatiRepository allegatiDao;

	@Autowired
	ReferentiRepository referenteDao;

	@Autowired
	ReferentiDocumentoRepository refDocDao;

	@Autowired
	PraticaRepository praticaDao;

	@Autowired
	TipoContenutoRepository tipiContenutoDao;

	@Autowired
	AllegatiTipoContenutoRepository allegatiTipoDao;
	
	@Autowired 
	SospensioneArchiviazioneAttivazioneRepository asrRepo;
	
	@Autowired
	AllegatoDelegatoRepository adDao;
	
	@Autowired
	RelazioneEnteService relazioneService;
	
	@Autowired
	IntegrazioneRepository integrazioneDao;

	private class PraticaReferenteDoc {
		PraticaDTO pratica;
		ReferentiDTO referente;
		ReferentiDocumentoDTO docRef;

		public PraticaDTO getPratica() {
			return pratica;
		}

		public ReferentiDTO getReferente() {
			return referente;
		}

		public ReferentiDocumentoDTO getDocRef() {
			return docRef;
		}

		/**
		 * effettua tutti i check per costruire
		 * 
		 * @param praticaId
		 * @param referenteId
		 * @throws CustomOperationToAdviceException
		 */
		public PraticaReferenteDoc(String praticaId, Long referenteId) throws CustomOperationToAdviceException {
			cexecPraticaReferenteDoc(praticaId, referenteId, false);
		}
		
		public PraticaReferenteDoc(String praticaId, Long referenteId, boolean batch) throws CustomOperationToAdviceException {
			cexecPraticaReferenteDoc(praticaId, referenteId, batch);
		}

		private void cexecPraticaReferenteDoc(String praticaId, Long referenteId, boolean batch)
				throws CustomOperationToAdviceException {
			pratica = praticaDao.find(UUID.fromString(praticaId));
			Assert.notNull(pratica, "pratica non specificata.");
			if(!batch) {
				//Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
				AllegatoServiceImpl.this.checkDiMiaCompetenzaInLettura(pratica);
			}
			referente = new ReferentiDTO();
			referente.setId(referenteId);
			referente = referenteDao.find(referente);
			if (referente == null || !referente.getPraticaId().equals(UUID.fromString(praticaId))) {
				LOGGER.error("Referente non trovato o appartenente ad altra pratica, impossibile procedere...");
				throw new CustomOperationToAdviceException("Referente non trovato, impossibile procedere...");
			}
			docRef = new ReferentiDocumentoDTO();
			docRef.setReferenteId(referenteId);
			docRef = refDocDao.find(docRef);
		}
	}
	
	/**
	 * solo per migrazione ricevute PEC non inserisce nulla su DB 
	 * @author acolaianni
	 *
	 * @param file
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws CustomOperationToAdviceException 
	 * @throws CustomCmisException 
	 */
	@Override
	public AllegatiDTO doUploadPerMigrazione(MultipartFile file, String path) throws CustomCmisException, CustomOperationToAdviceException, IOException {
		return this.doUpload(file, path,file.getContentType());
	}

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param file
	 * @param path
	 * @param contentType se non nullo usa questo, altrimenti effettua check del content type e verifica che sia ammesso
	 * @return
	 * @throws CustomCmisException
	 * @throws CustomOperationToAdviceException
	 * @throws IOException
	 */
	private AllegatiDTO doUpload(MultipartFile file, String path,String contentType) throws CustomCmisException, CustomOperationToAdviceException, IOException{
		AllegatiDTO retDto = new AllegatiDTO();
		StringBuilder pathCms = new StringBuilder();
		pathCms.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue()).append(rootPathCms.replace("/", ""))
				.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
				.append(StringUtil.isNotEmpty(subpathDeveloper)
						? subpathDeveloper.replace("/", "") + AlfrescoPaths.PATH_SEPARATOR.getTextValue()
						: "")
				.append(path.replace("//", "/"));
		if (pathCms.charAt(pathCms.length() - 1) == '/') {
			pathCms.deleteCharAt(pathCms.length() - 1);
		}
		retDto.setChecksum(CheckSumUtil.getCheckSum(file));
		retDto.setSize(file.getSize());
		retDto.setDataCaricamento(new Date());
		retDto.setNomeFile(file.getOriginalFilename());
		retDto.setPathCms(pathCms.toString());
		if(StringUtil.isNotEmpty(contentType)) {
			retDto.setFormatoFile(contentType);
		}else {
			AllowedMimeType mimeType = AllowedMimeType.getMimeType(file.getContentType());
			if (mimeType == null)
				throw new CustomOperationToAdviceException("Formato file non ammesso: " + file.getContentType());
			retDto.setFormatoFile(mimeType.getMimeType());
		}
		try {
			if(StringUtils.isNotEmpty(pathTransitoLocaleMigrazione)) {
				LOGGER.info("Scrittura su filesystem locale e non su alfresco!!");
				this.scriviFileInlocale(pathTransitoLocaleMigrazione,file,pathCms.toString());
				retDto.setIdCms(AllegatoService.PLACEHOLDERTEMPIDCMS);
			}else {
				String ret = cmsService.uploadCms(file, uploadUrl, pathCms.toString(), nomeApplicazione,
						StringUtil.isEmpty(LogUtil.getAuthorization()));
				retDto.setIdCms(ret);	
			}
			
		} catch (CustomCmisException e) {
			LOGGER.error("errore in cms upload path: " + pathCms.toString());
			throw e;
		}
		return retDto;
	}
	
	private AllegatiDTO doUpload(MultipartFile file, String path)
			throws CustomCmisException, IOException, CustomOperationToAdviceException {
		return this.doUpload(file, path,null);
	}
	
	private void scriviFileInlocale(String pathTransitoLocaleMigrazione, MultipartFile file, String pathCms) throws IOException {
		Path localPath=Paths.get(pathTransitoLocaleMigrazione,pathCms,file.getOriginalFilename());
		if(!localPath.getParent().toFile().exists()) {
			Files.createDirectories(localPath.getParent());
		}
		File outFile=localPath.toFile();
		if(outFile.exists() && outFile.length()==file.getSize()) {
			//già scritto.. evito di riscriverlo
		}else {
			if(outFile.exists()) {
				outFile.delete();
			}
			try(BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(outFile))){
				IOUtils.copy(file.getInputStream(), os);	
			}catch(Exception e) {
				LOGGER.error("Error writing file locally: " +outFile.getName(),e);
			}	
		}
	}

	@Logging
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatoSeduta(List<Integer> tipiContenuto, String nomeContenuto,  MultipartFile file, String nomeSeduta)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException
	{
		Assert.notNull(file, "file inesistente.");
		Assert.notNull(tipiContenuto, "tipo contenuto non specificato.");
		if (tipiContenuto.size() > 1 && StringUtil.isEmpty(nomeContenuto)) 
		{
			throw new CustomOperationToAdviceException(
					"Nome del contenuto non presente... impossibile procedere per un allegato avente più tipologie di contenuti !!!");
		}
		String pathAlfresco = nomeSeduta.replace(" ", "_");
		return genericUpload(file, tipiContenuto, nomeContenuto, pathAlfresco);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegato(String praticaId, List<Integer> tipiContenuto, String nomeContenuto, Integer integrazioneId, UUID allegatoPreProtocollazione, MultipartFile file, Boolean isSigned)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException
	{
		Assert.notNull(file, "file inesistente.");
		Assert.notNull(praticaId, "pratica non specificata.");
		Assert.notNull(tipiContenuto, "tipo contenuto non specificato.");
		if (tipiContenuto.size() > 1 && StringUtil.isEmpty(nomeContenuto)) {
			throw new CustomOperationToAdviceException(
					"Nome del contenuto non presente... impossibile procedere per un allegato avente più tipologie di contenuti !!!");
		}
		if(!allegatiDao.canUpload(UUID.fromString(praticaId), tipiContenuto))
		{
			throw new CustomOperationToAdviceException(
					"Impossibile proseguire con l'upload del documento: una delle tipologie associate al documento non ammette file multipli ed è già presente un documento per quella tipologia");
		}
		PraticaDTO pratica = praticaDao.find(UUID.fromString(praticaId));
		AllegatoServiceImpl.this.checkDiMiaCompetenza(pratica);
		this.checkForUploadInIntegrazione(pratica,integrazioneId); 
		return genericUpload(file, tipiContenuto, nomeContenuto, pratica.getCodicePraticaAppptr(), pratica.getId(), integrazioneId, allegatoPreProtocollazione, isSigned);
				
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegato(String praticaId, List<Integer> tipiContenuto, String nomeContenuto, Integer integrazioneId, MultipartFile file, Boolean isSigned)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException {
		return uploadAllegato(praticaId, tipiContenuto, nomeContenuto, integrazioneId, null, file, isSigned);
	}
	
	private AllegatiDTO genericUpload(MultipartFile file, List<Integer> tipiContenuto, String nomeContenuto, String pathAlfresco) throws CustomCmisException, CustomOperationException, IOException
	{
		return genericUpload(file, tipiContenuto, nomeContenuto, pathAlfresco, null, null, null,null);
	}

	private AllegatiDTO genericUpload(MultipartFile file, List<Integer> tipiContenuto, String nomeContenuto, 
			String pathAlfresco, UUID praticaId, Integer idIntegrazione, 
			UUID allegatoPreProtocollazione,String contentType, Boolean isSigned) throws CustomCmisException, CustomOperationException, IOException{
		LOGGER.info("Start {}", LOG_UPLOAD_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_UPLOAD_DOCUMENT);
		AllegatiDTO retDto = null;
		try {
			TipoContenutoDTO tipoContenuto = new TipoContenutoDTO();
			tipoContenuto.setId(tipiContenuto.get(0));
			tipoContenuto = tipiContenutoDao.find(tipoContenuto);
			StringBuilder pathAlfrescoSb = new StringBuilder();
			pathAlfrescoSb.append(pathAlfresco).append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
					.append(AlfrescoPaths.getPathDocAllegato(SezioneAllegati.valueOf(tipoContenuto.getSezione())));
			// in alfresco in caso di allegato con singola tipologia metto nella path il
			// nume della tipologia "normalizzato"
			if (tipiContenuto.size() == 1) {
				pathAlfrescoSb.append(tipoContenuto.getDescrizione().replaceAll("[^a-zA-Z0-9\\.\\-]", "_"));
			} else {
				pathAlfrescoSb.append(nomeContenuto.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"));
			}
			retDto = doUpload(file, pathAlfrescoSb.toString(),contentType);
			// ad upload effettuato, aggiorno il db
			retDto.setId(UUID.randomUUID());
			retDto.setPraticaId(praticaId);
			retDto.setIdIntegrazione(idIntegrazione);
			retDto.setIdAllegatoPreProtocollazione(allegatoPreProtocollazione);
			retDto.setSigned(isSigned);
			if (!StringUtil.isEmpty(nomeContenuto)) {
				retDto.setDescrizione(nomeContenuto);
			} else {
				retDto.setDescrizione(tipoContenuto.getDescrizione());
			}
			allegatiDao.insert(retDto);
			// inserisco tutti i tipi di contenuto nella tabella allegati_tipo_contenuto
			//e popolo le descrizioni nell'oggetto restituito in tipiContenuto
			retDto.setTipiContenuto(new ArrayList<String>());
			for (Integer idTipo : tipiContenuto) {
				AllegatiTipoContenutoDTO tipoAllegato = new AllegatiTipoContenutoDTO();
				tipoAllegato.setAllegatiId(retDto.getId());
				tipoAllegato.setTipoContenutoId(idTipo);
				allegatiTipoDao.insert(tipoAllegato);
				TipoContenutoDTO tipoCont = new TipoContenutoDTO();
				tipoCont.setId(idTipo);
				tipoCont=tipiContenutoDao.find(tipoCont);
				retDto.getTipiContenuto().add(tipoCont.getDescrizione());
			}
		} catch (CustomCmisException e) {
			LOGGER.error("Errore di comunicazione con il CMS...", e);
			throw new CustomOperationToAdviceException("Errore di comunicazione con il CMS...");
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
		return retDto;
	}
	
	private AllegatiDTO genericUpload(MultipartFile file, List<Integer> tipiContenuto, 
			String nomeContenuto, String pathAlfresco, UUID praticaId, 
			Integer idIntegrazione, UUID allegatoPreProtocollazione,Boolean isSigned) throws CustomCmisException, CustomOperationException, IOException
	{
		return this.genericUpload(file, tipiContenuto, nomeContenuto, pathAlfresco, praticaId,
				idIntegrazione,allegatoPreProtocollazione,null, isSigned);
		
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegato(String praticaId, List<Integer> tipiContenuto, String nomeContenuto, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException 
	{ 
		return uploadAllegato(praticaId, tipiContenuto, nomeContenuto, null, file,null); 
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatoReferente(String praticaId, Long referenteId, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException {
		Assert.notNull(file, "file inesistente.");
		Assert.notNull(referenteId, "id referente null non ammesso.");
		LOGGER.info("Start {}", LOG_UPLOAD_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_UPLOAD_DOCUMENT);
		PraticaReferenteDoc prd = new PraticaReferenteDoc(praticaId, referenteId);
		this.checkStatoForUpdate(prd.getPratica());
		if (prd.getDocRef().getIdAllegato() != null) {
			throw new CustomOperationToAdviceException("Allegato già presente, rimuoverlo e poi caricare.");
		}
		AllegatiDTO retDto = null;
		try {
			LOGGER.info("calling url: " + uploadUrl);
			LOGGER.info("calling path: " + AlfrescoPaths.getPathDocRiconoscimento(TipoReferente.SD));
			retDto = this.doUpload(file,
					prd.getPratica().getCodicePraticaAppptr() + AlfrescoPaths.PATH_SEPARATOR.getTextValue()
							+ AlfrescoPaths.getPathDocRiconoscimento(
									TipoReferente.valueOf(prd.getReferente().getTipoReferente())));
			// ad upload effettuato, aggiorno il db
			retDto.setId(UUID.randomUUID());
			retDto.setDescrizione(DESCR_FILE_DOCUMENTO_RICONOSCIMENTO);
			retDto.setPraticaId(prd.getPratica().getId());
			allegatiDao.insert(retDto);
			prd.getDocRef().setIdAllegato(retDto.getId());
			refDocDao.update(prd.getDocRef());
		} catch (CustomCmisException e) {
			LOGGER.error("Errore di comunicazione con il CMS...", e);
			throw new CustomOperationToAdviceException("Errore di comunicazione con il CMS...");
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
		return retDto;
	}

	@Logging
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDTO uploadAllegatiComunicazione(UUID praticaId, MultipartFile file,String subpath) throws Exception
	{
		AllegatiDTO allegato = null;
		PraticaDTO pratica = new PraticaDTO();
		pratica.setId(praticaId);
		pratica = praticaDao.find(pratica);
		//this.checkStato(pratica);
		StringBuilder path = new StringBuilder(pratica.getCodicePraticaAppptr())
							.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
							.append(AlfrescoPaths.ALLEGATI_COMUNICAZIONE.getTextValue())
							.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue());
		if(StringUtil.isNotEmpty(subpath)) {
			path.append(subpath).append(AlfrescoPaths.PATH_SEPARATOR.getTextValue());
		}
		try
		{
			allegato = this.doUpload(file, path.toString());
			allegato.setId(UUID.randomUUID());
			allegato.setDescrizione(DESCR_FILE_ALLEGATO_COMUNICAZIONE);
			allegato.setPraticaId(praticaId);
			allegatiDao.insert(allegato);
		}
		catch (CustomCmisException e) 
		{
			LOGGER.error("Errore di comunicazione con il CMS...", e);
			throw new CustomOperationToAdviceException("Errore di comunicazione con il CMS...");
		}
		return allegato;
	}
	
	@Logging
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDTO uploadAllegatiComunicazione(UUID praticaId, MultipartFile file) throws Exception
	{
		return this.uploadAllegatiComunicazione(praticaId, file, "");
	}
	
	
	@Logging
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatiRelazione(UUID praticaId, MultipartFile file,String tipo,long idRelazione) throws Exception
	{
		AllegatiDTO allegato = null;
		PraticaDTO pratica = new PraticaDTO();
		pratica.setId(praticaId);
		pratica = praticaDao.find(pratica);
		this.checkStatoForUpdate(pratica);
		String path = new StringBuilder(pratica.getCodicePraticaAppptr())
							.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
							.append(AlfrescoPaths.ALLEGATI_COMUNICAZIONE.getTextValue())
							.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
							.toString();
		try
		{
			allegato = this.doUpload(file, path);
			allegato.setId(UUID.randomUUID());
			allegato.setDescrizione(tipo);
			allegato.setPraticaId(praticaId);
			allegatiDao.insert(allegato);
			relazioneService.insertAllegato(allegato.getId(), idRelazione);
			AllegatiTipoContenutoDTO tipoAllegato = new AllegatiTipoContenutoDTO();
			tipoAllegato.setAllegatiId(allegato.getId());
			tipoAllegato.setTipoContenutoId(tipiContenutoDao.findContenutoByDesc(tipo).getId());
			allegatiTipoDao.insert(tipoAllegato);
			List<String> listTipi = new ArrayList<String>();
			listTipi.add(tipo);
			allegato.setTipiContenuto(listTipi);
		}
		catch (CustomCmisException e) 
		{
			LOGGER.error("Errore di comunicazione con il CMS...", e);
			throw new CustomOperationToAdviceException("Errore di comunicazione con il CMS...");
		}
		return allegato;
	}
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public CmsDownloadResponseBean downloadAllegato(UUID idAllegato)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException {
		CmsDownloadResponseBean cmsBean = null;
		LOGGER.info("Start {}", LOG_DOWNLOAD_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_DOWNLOAD_DOCUMENT);
		try {
			AllegatiDTO pk = new AllegatiDTO();
			pk.setId(idAllegato);
			pk = allegatiDao.find(pk);
			praticaDao.find(pk.getPraticaId());
			//this.checkDiMiaCompetenza(pratica);
			cmsBean = doDownload(pk);
		} catch (Exception e) {
			LOGGER.error("Errore nel downloade dell'allegato ",e);
			throw new CustomOperationException("Copy of downloaded file failed.");
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
		return cmsBean;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public CmsDownloadResponseBean downloadAllegatoGenerico(UUID idAllegato)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException {
		CmsDownloadResponseBean cmsBean = null;
		LOGGER.info("Start {}", LOG_DOWNLOAD_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_DOWNLOAD_DOCUMENT);
		try {
			AllegatiDTO pk = new AllegatiDTO();
			pk.setId(idAllegato);
			pk = allegatiDao.find(pk);
			cmsBean = doDownloadInFolder(pk);
		} catch (Exception e) {
			LOGGER.error("Copy of downloaded file failed.",e);
			throw new CustomOperationException("Copy of downloaded file failed.");
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
		return cmsBean;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public CmsDownloadResponseBean downloadAllegato(UUID idAllegato, Long idSeduta)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException {
		CmsDownloadResponseBean cmsBean = null;
		LOGGER.info("Start {}", LOG_DOWNLOAD_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_DOWNLOAD_DOCUMENT);
		try {
			AllegatiDTO pk = new AllegatiDTO();
			pk.setId(idAllegato);
			pk = allegatiDao.find(pk);
			cmsBean = doDownload(pk);
		} catch (Exception e) {
			throw new CustomOperationException("Copy of downloaded file failed.");
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
		return cmsBean;
	}
		
	private CmsDownloadResponseBean doDownload(AllegatiDTO allegato) throws Exception
	{
		CmsDownloadResponseBean cmsBean = null;
		String pathFileLocale = Path.of(pathTransito, allegato.getId().toString()).toString();
		cmsService.downloadFromCmsStream(allegato.getIdCms(), downloadUrl, nomeApplicazione, pathFileLocale,LogUtil.getAuthorization()==null);
		if (Files.exists(Path.of(pathFileLocale))) 
		{
			cmsBean = new CmsDownloadResponseBean();
			cmsBean.setFileName(pathFileLocale);
			cmsBean.setFilePathName(allegato.getNomeFile());
			cmsBean.setContentType(allegato.getFormatoFile());
		}
		return cmsBean;
	}
	
	private CmsDownloadResponseBean doDownloadInFolder(AllegatiDTO allegato) throws Exception
	{
		CmsDownloadResponseBean cmsBean = null;
		Path pathFileLocale = Files.createTempFile(Path.of(pathTransito),"",allegato.getNomeFile());
		//String pathFileLocale =Path.of(basePath.toString(),allegato.getNomeFile()).toString();
		cmsService.downloadFromCmsStream(allegato.getIdCms(), downloadUrl, nomeApplicazione, pathFileLocale.toString(),
				LogUtil.getAuthorization()==null);
		if (Files.exists(pathFileLocale)) 
		{
			cmsBean = new CmsDownloadResponseBean();
			cmsBean.setFileName(pathFileLocale.toString());
			cmsBean.setFilePathName(allegato.getNomeFile());
			cmsBean.setContentType(allegato.getFormatoFile());
		}
		return cmsBean;
	}

	private void deleteFromCms(String idCms) throws CustomCmisException {
		this.deleteFromCms(idCms, true);
	}
	
	private void deleteFromCms(String idCms,boolean raiseException) throws CustomCmisException {
		LOGGER.info("Start {}", LOG_DELETE_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_DELETE_DOCUMENT);
		try {
			cmsService.deleteCmisIdFromCms(deleteUrl, idCms, nomeApplicazione);
		}catch(Exception e)
		{
			LOGGER.info("Errore durante l'eliminazione del file da alfresco con idCms: {} , {}",idCms,e);
			if(raiseException) {
				throw e;
			}
		}
		finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDTO getDocumentoReferente(String praticaId, Long referenteId)
			throws CustomOperationToAdviceException {
		return this.getDocumentoReferente(praticaId, referenteId, false);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDTO getDocumentoReferente(String praticaId, Long referenteId, boolean txWrite) 
			throws CustomOperationToAdviceException {
		return this.getDocumentoReferente(praticaId, referenteId, false, false);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public AllegatiDTO getDocumentoReferente(String praticaId, Long referenteId, boolean txWrite, boolean batch)
			throws CustomOperationToAdviceException {
		return execGetDocumentoReferente(praticaId, referenteId, txWrite, batch);
	}
	
	private AllegatiDTO execGetDocumentoReferente(String praticaId, Long referenteId, boolean txWrite, boolean batch)
			throws CustomOperationToAdviceException {
		LOGGER.info("Start {}", "get_documento_referente");
		Assert.notNull(referenteId, "id referente null non ammesso.");
		StopWatch sw = LogUtil.startLog("get_documento_referente");
		try {
			PraticaReferenteDoc prd = new PraticaReferenteDoc(praticaId, referenteId, batch);
			AllegatiDTO ret = null;
			UUID idAllegato = prd.getDocRef().getIdAllegato();
			if (idAllegato != null) {// esiste
				ret = new AllegatiDTO();
				ret.setId(idAllegato);
				ret = allegatiDao.find(ret,txWrite);
			}
			return ret;
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO getDocumentoReferenteTxWrite(String praticaId, Long referenteId)
			throws CustomOperationToAdviceException {
		return this.getDocumentoReferente(praticaId, referenteId, true);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO getDocumentoReferenteTxWrite(String praticaId, Long referenteId, boolean batch)
			throws CustomOperationToAdviceException {
		return this.getDocumentoReferente(praticaId, referenteId, true, batch);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteDocumentoReferente(String praticaId, Long referenteId)
			throws CustomOperationToAdviceException, CustomCmisException {
		LOGGER.info("Start {}", "get_documento_referente");
		Assert.notNull(referenteId, "id referente null non ammesso.");
		StopWatch sw = LogUtil.startLog("get_documento_referente");
		try {
			PraticaReferenteDoc prd = new PraticaReferenteDoc(praticaId, referenteId);
			this.checkStatoForUpdate(prd.getPratica());
			AllegatiDTO ret = null;
			UUID idAllegato = prd.getDocRef().getIdAllegato();
			if (idAllegato != null) {// esiste
				ret = new AllegatiDTO();
				ret.setId(idAllegato);
				ret = allegatiDao.find(ret);
				if (ret != null && ret.getIdCms() != null) {
					this.deleteFromCms(ret.getIdCms());
					prd.getDocRef().setIdAllegato(null);
					refDocDao.update(prd.getDocRef());
					allegatiDao.delete(ret);
				}
			}
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
	}

	/**
	 * forse va rivisto... con la cancellazione logica... la transazione potrebbe
	 * rimanere incongruente
	 * 
	 * @param praticaId
	 * @throws CustomCmisException
	 * @throws CustomOperationToAdviceException
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteAllAllegatiPratica(UUID praticaId) throws CustomCmisException, CustomOperationToAdviceException {
		PraticaDTO pratica = praticaDao.find(praticaId);
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		List<AllegatiDTO> lista = allegatiDao.findByPratica(praticaId);
		// se è un allegatoDocumento, devo eliminare il riferimento su referenti_doc
		adDao.deleteAllegatoDelegato(praticaId);
		for (AllegatiDTO allegato : lista) {
			ReferentiDocumentoSearch search = new ReferentiDocumentoSearch();
			search.setIdAllegato(allegato.getId().toString());
			PaginatedList<ReferentiDocumentoDTO> listaAllegatiDoc = refDocDao.search(search);
			if (listaAllegatiDoc.getList() != null) {
				for (ReferentiDocumentoDTO refDoc : listaAllegatiDoc.getList()) {
					refDoc.setIdAllegato(null);
					refDocDao.update(refDoc);
				}
			}
			deleteAllegatoPratica(praticaId, allegato.getId());
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteAllegatoPratica(UUID praticaId, UUID idAllegato) throws CustomCmisException {
		List<AllegatiDTO> lista = allegatiDao.findByPratica(praticaId);
		for (AllegatiDTO allegato : lista) {
			if (allegato.getId().equals(idAllegato)) {
				this.deleteFromCms(allegato.getIdCms());
				allegatiDao.delete(allegato);
			}
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteAllegato(String praticaId, String allegatoId)
			throws CustomOperationToAdviceException, CustomCmisException {
		PraticaDTO pratica = praticaDao.find(UUID.fromString(praticaId));
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		AllegatiDTO allegatoToDelete = new AllegatiDTO();
		allegatoToDelete.setId(UUID.fromString(allegatoId));
		allegatoToDelete = allegatiDao.find(allegatoToDelete);
		if (allegatoToDelete.getDescrizione().equals(DESCR_FILE_DOCUMENTO_RICONOSCIMENTO)) {
			throw new CustomOperationToAdviceException(
					"Allegato documento di riconoscimento, impossibile eliminare da questa funzione, utilizzare la funzione ad hoc !!!");
		}
		deleteAllegatoPratica(UUID.fromString(praticaId), allegatoToDelete.getId());
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteAllegato(String praticaId, String allegatoId,Integer idIntegrazione)
			throws CustomOperationToAdviceException, CustomCmisException {
		PraticaDTO pratica = praticaDao.find(UUID.fromString(praticaId));
		this.checkDiMiaCompetenza(pratica);
		this.checkForUploadInIntegrazione(pratica, idIntegrazione);
		AllegatiDTO allegatoToDelete = new AllegatiDTO();
		allegatoToDelete.setId(UUID.fromString(allegatoId));
		allegatoToDelete = allegatiDao.find(allegatoToDelete);
		if (allegatoToDelete.getDescrizione().equals(DESCR_FILE_DOCUMENTO_RICONOSCIMENTO)) {
			throw new CustomOperationToAdviceException(
					"Allegato documento di riconoscimento, impossibile eliminare da questa funzione, utilizzare la funzione ad hoc !!!");
		}
		deleteAllegatoPratica(UUID.fromString(praticaId), allegatoToDelete.getId());
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteAllegato(String allegatoId)
			throws CustomOperationToAdviceException, CustomCmisException 
	{
		doDeleteAllegato(allegatoId,true);
	}

	private void doDeleteAllegato(String allegatoId,boolean raiseException) throws CustomOperationToAdviceException, CustomCmisException {
		AllegatiDTO allegatoToDelete = new AllegatiDTO();
		allegatoToDelete.setId(UUID.fromString(allegatoId));
		allegatoToDelete = allegatiDao.find(allegatoToDelete);
		if (allegatoToDelete.getDescrizione().equals(DESCR_FILE_DOCUMENTO_RICONOSCIMENTO)) {
			throw new CustomOperationToAdviceException(
					"Allegato documento di riconoscimento, impossibile eliminare da questa funzione, utilizzare la funzione ad hoc !!!");
		}
		deleteFromCms(allegatoToDelete.getIdCms(),raiseException);
		allegatiDao.delete(allegatoToDelete);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteAllegato(String allegatoId,boolean raiseException)
			throws CustomOperationToAdviceException, CustomCmisException 
	{
		doDeleteAllegato(allegatoId,raiseException);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegatiIstruttoria(UUID idPratica, SezioneAllegati sezione, Integer idIntegrazione) throws Exception
	{
		//TODO: inserire controlli per ottenere gli allegati
		PraticaDTO pratica = praticaDao.find(idPratica);
		return this.doGetAllegati(pratica, sezione, idIntegrazione,null);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegatiIstruttoria(UUID idPratica, SezioneAllegati sezione) throws Exception
	{
		return getAllegatiIstruttoria(idPratica, sezione, null);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegati(UUID idPratica, SezioneAllegati sezione, Integer idIntegrazione) 
			throws CustomOperationToAdviceException {
		PraticaDTO pratica = praticaDao.find(idPratica);
		//Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
		this.checkDiMiaCompetenzaInLettura(pratica);
		return doGetAllegati(pratica, sezione, idIntegrazione,null);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegati(UUID idPratica, SezioneAllegati sezione, Integer idIntegrazione, Integer idTipoContenuto) 
			throws CustomOperationToAdviceException {
		PraticaDTO pratica = praticaDao.find(idPratica);
		this.checkDiMiaCompetenzaInLettura(pratica);
		return doGetAllegati(pratica, sezione, idIntegrazione,idTipoContenuto);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegati(UUID idPratica, SezioneAllegati sezione)
			throws CustomOperationToAdviceException 
	{
		return getAllegati(idPratica, sezione, null);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegatiBatch(UUID idPratica, SezioneAllegati sezione)
			throws CustomOperationToAdviceException 
	{
		PraticaDTO pratica = praticaDao.find(idPratica);
		return doGetAllegati(pratica, sezione, null ,null);
	}
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<AllegatiDTO> getAllegati(PraticaDTO pratica, SezioneAllegati sezione, Integer idIntegrazione,Integer idTipoContenuto){
		return this.doGetAllegati(pratica, sezione, idIntegrazione, idTipoContenuto);
	}
	
	private List<AllegatiDTO> doGetAllegati(PraticaDTO pratica, SezioneAllegati sezione, Integer idIntegrazione,Integer idTipoContenuto)
	{
		List<AllegatiDTO> ret = null;
		ret = allegatiDao.findByPraticaAndSezione(pratica.getId(), pratica.getTipoProcedimento(), sezione, idIntegrazione,idTipoContenuto);
		//se l'allegato ha tipologia multipla, allora popolo anche il campo tipiContenuto.
		if(ret!=null && ret.size()>0) {
			ret.forEach(allegato->{
				List<PlainStringValueLabel> tipi = allegatiDao.findTipiAllegato(allegato.getId());
				if(tipi!=null && tipi.size()>0) {
					allegato.setTipiContenuto(tipi.stream().map(el->el.getDescription()).collect(Collectors.toList()));
				}
			});
		}
		return ret;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public Integer deleteAllOfType(UUID praticaId, SezioneAllegati sezione) throws Exception
	{
		PraticaDTO pratica = praticaDao.find(praticaId);
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		List<AllegatiDTO> toDelete = this.getAllegati(praticaId, sezione);
		for(AllegatiDTO allegatoToDelete: toDelete)
		{
			if (allegatoToDelete.getDescrizione().equals(DESCR_FILE_DOCUMENTO_RICONOSCIMENTO)) 
			{
				throw new CustomOperationToAdviceException(
						"Allegato documento di riconoscimento, impossibile eliminare da questa funzione, utilizzare la funzione ad hoc !!!");
			}
			deleteAllegatoPratica(praticaId, allegatoToDelete.getId());
		}
		return toDelete.size();
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatoASR(String praticaId, List<Integer> tipiContenuto, String nomeContenuto, MultipartFile file, Long idRichiesta) throws Exception
	{
		AllegatiDTO result = uploadAllegato(praticaId, tipiContenuto, nomeContenuto, file);
		if(asrRepo.insertRichiestaAllegato(result.getId(), idRichiesta) != 1)
			throw new Exception("Errore in nell'inserimento del record nella tabella intermedia");
		return result;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadDelegaAllegato(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file) throws Exception
	{
	    return execUploadDelegaAllegato(pratica, indiceDelegato, nomeContenuto, file, false);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadDelegaAllegato(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file, boolean subentro) throws Exception
	{
	    return execUploadDelegaAllegato(pratica, indiceDelegato, nomeContenuto, file, subentro);
	}

	private AllegatiDTO execUploadDelegaAllegato(PraticaDTO pratica, int indiceDelegato, String nomeContenuto,
			MultipartFile file, boolean subentro)
			throws CustomOperationToAdviceException, CustomCmisException, CustomOperationException, IOException {
		List<Integer> tipoContenuto = Collections.singletonList(1600);
//	    AllegatiDTO result = uploadAllegato(praticaId, tipoContenuto, nomeContenuto, file);
	    Assert.notNull(file, "file inesistente.");
	    Assert.notNull(pratica.getId(), "pratica non specificata.");
	    Assert.notNull(tipoContenuto, "tipo contenuto non specificato.");
	    if (tipoContenuto.size() > 1 && StringUtil.isEmpty(nomeContenuto)) 
	    {
		throw new CustomOperationToAdviceException(
			"Nome del contenuto non presente... impossibile procedere per un allegato avente più tipologie di contenuti !!!");
	    }
	    if(!allegatiDao.canUpload(pratica.getId(), tipoContenuto))
	    {
		throw new CustomOperationToAdviceException(
			"Impossibile proseguire con l'upload del documento: una delle tipologie associate al documento non ammette file multipli ed è già presente un documento per quella tipologia");
	    }
	    if(!subentro) {
		    AllegatoServiceImpl.this.checkDiMiaCompetenza(pratica);
	    }
	    AllegatiDTO result = subentro ? 
	    		genericUpload(file, tipoContenuto, nomeContenuto, pratica.getCodicePraticaAppptr(), pratica.getId(), null, null, file.getContentType(), null) : 
	    			genericUpload(file, tipoContenuto, nomeContenuto, pratica.getCodicePraticaAppptr(), pratica.getId(), null, null,null);
	    AllegatoDelegatoDTO dto = new AllegatoDelegatoDTO();
	    dto.setIdAllegato(result.getId());
	    dto.setIdPratica(pratica.getId());
	    dto.setIndiceDelegato(indiceDelegato);
	    adDao.insert(dto);
	    return result;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatoSollevamentoIncarico(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file) throws Exception
	{
	    return execUploadAllegatoSollevamentoIncarico(pratica, indiceDelegato,nomeContenuto, file, false);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatoSollevamentoIncarico(PraticaDTO pratica, int indiceDelegato, String nomeContenuto, MultipartFile file, boolean subentro) throws Exception
	{
	    return execUploadAllegatoSollevamentoIncarico(pratica, indiceDelegato, nomeContenuto, file, subentro);
	}

	private AllegatiDTO execUploadAllegatoSollevamentoIncarico(PraticaDTO pratica, int indiceDelegato, String nomeContenuto,
			MultipartFile file, boolean subentro)
			throws CustomOperationToAdviceException, CustomCmisException, CustomOperationException, IOException {
		List<Integer> tipoContenuto = Collections.singletonList(1700);
//	    AllegatiDTO result = uploadAllegato(praticaId, tipoContenuto, nomeContenuto, file);
	    Assert.notNull(file, "file inesistente.");
	    Assert.notNull(pratica.getId(), "pratica non specificata.");
	    Assert.notNull(tipoContenuto, "tipo contenuto non specificato.");
	    if (tipoContenuto.size() > 1 && StringUtil.isEmpty(nomeContenuto)) 
	    {
		throw new CustomOperationToAdviceException(
			"Nome del contenuto non presente... impossibile procedere per un allegato avente più tipologie di contenuti !!!");
	    }
	    if(!allegatiDao.canUpload(pratica.getId(), tipoContenuto))
	    {
		throw new CustomOperationToAdviceException(
			"Impossibile proseguire con l'upload del documento: una delle tipologie associate al documento non ammette file multipli ed è già presente un documento per quella tipologia");
	    }
	    if(!subentro) {
		    AllegatoServiceImpl.this.checkDiMiaCompetenza(pratica);
	    }
	    AllegatiDTO result = subentro ? 
	    		genericUpload(file, tipoContenuto, nomeContenuto, pratica.getCodicePraticaAppptr(), pratica.getId(), null, null, file.getContentType(), null) : 
	    			genericUpload(file, tipoContenuto, nomeContenuto, pratica.getCodicePraticaAppptr(), pratica.getId(), null, null,null);
	    AllegatoDelegatoDTO dto = new AllegatoDelegatoDTO();
	    dto.setIdAllegato(result.getId());
	    dto.setIdPratica(pratica.getId());
	    dto.setIndiceDelegato(indiceDelegato);
	    adDao.insert(dto);
	    return result;
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public boolean deleteAllegatoRelazione(UUID idFile) throws Exception {
		this.allegatiDao.deleteAllegatoRelazione(idFile);
		return false;
	}

	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadLogoTemplate(Integer tipoContenuto, String nomeContenuto, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException {
		Assert.notNull(file, "file inesistente.");
		Assert.notNull(tipoContenuto, "tipo contenuto non specificato.");
		return genericUpload(file, List.of(tipoContenuto), nomeContenuto,"ConfigurazioneTemplateDoc");
	}
	
	@Override
	public void checkStatoForUpdate(PraticaDTO pratica) throws CustomOperationToAdviceException 
	{
		Gruppi gruppo = userUtil.getGruppo();
		if (gruppo==Gruppi.RICHIEDENTI  && !AttivitaDaEspletare.getStatiPresentazioneIstanza().contains(pratica.getStato())) 
		{
			LOGGER.error("Pratica in stato avanzato, impossibile procedere...");
			throw new CustomOperationToAdviceException("Pratica in stato avanzato, impossibile procedere...");
		}		
	}
	
	/**
	 * check in caso di integrazione...
	 * @author acolaianni
	 *
	 * @param pratica
	 * @param integrazioneId
	 * @throws CustomOperationToAdviceException
	 */
	private void checkForUploadInIntegrazione(PraticaDTO pratica,Integer integrazioneId) throws CustomOperationToAdviceException 
	{
		Gruppi gruppo = userUtil.getGruppo();
		if(integrazioneId!=null && gruppo==Gruppi.RICHIEDENTI ) {
			//a sencoda del tipo integrazione (spontanea o su richiesta, lo stato ammesso cambia
			IntegrazioneDTO integrazione=new IntegrazioneDTO();
			integrazione.setId(integrazioneId);
			integrazione.setPraticaId(pratica.getId());
			integrazione=this.integrazioneDao.find(integrazione);
			if(AttivitaDaEspletare.getStatiIntegrazioneAmmessa(integrazione.getRichiestaIntegrazione())
					.contains(pratica.getStato()) && 
					List.of(StatoIntegrazioneDocumentale.IN_BOZZA,
							StatoIntegrazioneDocumentale.IN_ATTESA).contains(integrazione.getStato())){
				//tutto ok	
			}else {
				throw new CustomOperationToAdviceException("Stato pratica non ammesso per l'avanzamento dell'integrazione"+
			(integrazione.getRichiestaIntegrazione()!=null && 
			integrazione.getRichiestaIntegrazione()?" su richiesta ":" spontanea ")+" !!!");
			}
		}else {
			//ex check...
			checkStatoForUpdate(pratica);
		}
	}
	
	@Override
	public void doDownloadFromCms(String idCms,HttpServletResponse response) throws Exception
	{
		cmsService.downloadFromCmsStream(idCms, downloadUrl, nomeApplicazione, response, LogUtil.getAuthorization()==null);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AllegatiDTO uploadAllegatoReferenteDaMigrazione(PraticaDTO pratica,ReferentiDTO referente, MultipartFile file)
			throws CustomCmisException, CustomOperationException, IOException, CustomValidationException {
		Assert.notNull(file, "file inesistente.");
		Assert.notNull(referente.getId(), "id referente null non ammesso.");
		LOGGER.info("Start {}", LOG_UPLOAD_DOCUMENT);
		StopWatch sw = LogUtil.startLog(LOG_UPLOAD_DOCUMENT);
		AllegatiDTO retDto = null;
		try {
			LOGGER.info("calling url: " + uploadUrl);
			LOGGER.info("calling path: " + AlfrescoPaths.getPathDocRiconoscimento(TipoReferente.SD));
			retDto = this.doUpload(file,
					pratica.getCodicePraticaAppptr() + AlfrescoPaths.PATH_SEPARATOR.getTextValue()
							+ AlfrescoPaths.getPathDocRiconoscimento(
									TipoReferente.valueOf(referente.getTipoReferente())),file.getContentType());
			// ad upload effettuato, aggiorno il db
			retDto.setId(UUID.randomUUID());
			retDto.setDescrizione(DESCR_FILE_DOCUMENTO_RICONOSCIMENTO);
			retDto.setPraticaId(pratica.getId());
			allegatiDao.insert(retDto);
		} catch (CustomCmisException e) {
			LOGGER.error("Errore di comunicazione con il CMS...", e);
			throw new CustomOperationToAdviceException("Errore di comunicazione con il CMS...");
		} finally {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(LogUtil.stopLog(sw));
			}
		}
		return retDto;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)	
	public AllegatiDTO uploadAllegatoDaMigrazione(MultipartFile file, List<Integer> tipiContenuto, String nomeContenuto, String pathAlfresco, UUID praticaId, Integer idIntegrazione, UUID allegatoPreProtocollazione) throws CustomCmisException, CustomOperationException, IOException{
		return this.genericUpload(file, tipiContenuto, nomeContenuto, pathAlfresco, praticaId, null, null,file.getContentType(),null);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public String getPathAlfrescoByCmisid(String idCms) throws Exception
	{
		return cmsService.getPathAlfrescoByCmisid(this.getPathUrl, idCms, nomeApplicazione, LogUtil.getAuthorization()==null);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int updateDescrizioneTitolo(UUID id, String descrizione,String titolo) throws Exception{
		return this.allegatiDao.updateDescrizioneTitolo(id, descrizione, titolo);
	}
	
}
