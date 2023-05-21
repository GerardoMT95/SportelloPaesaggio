package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CambioOwnershipRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CambioOwnershipResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SubentroDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AlfrescoPaths;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaOwnerHistoryDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SubentroDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiTipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaOwnerHistoryRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.SubentroRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.AnagraficaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ICambioOwnershipService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CheckSumUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.FiscalCodeValidation;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class CambioOwnershipService implements ICambioOwnershipService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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

	@Value("${cms.path.developer:}")
	private String subpathDeveloper;
	
	@Autowired
	SubentroRepository subDao;
	@Autowired
	PraticaDelegatoRepository prDelDao;
	@Autowired
	PraticaRepository prDao;
	@Autowired
	ReferentiRepository repDao;
	@Autowired
	CommonRepository commonDao;
	@Autowired
	AnagraficaRepository anagraficaDao;
	@Autowired
	PraticaOwnerHistoryRepository historyDao;
	@Autowired
	AllegatiRepository allegatiDao;
	@Autowired
	AllegatiTipoContenutoRepository tipoDao;
	@Autowired
	IHttpClientService cmsService;
	@Autowired
	AllegatoService allegatoService;
	@Autowired
	ComunicazioniService comunicazioniService;
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public CambioOwnershipResponseBean cercaOwnership(final CambioOwnershipRequestBean request) throws Exception {		
		String usernameCorrente = SecurityUtil.getUsername();
		CambioOwnershipResponseBean response=new CambioOwnershipResponseBean();
		String owner = prDelDao.hasAccessCambioOwnership(request.getCodicePratica(), request.getCodiceSegreto(), request.getCodiceFiscaleProponente());
		if(StringUtil.isEmpty(owner)) {
			response.setEsito(false);
			return response;
		}
		//ok ha agganciato la pratica
		if(owner.equals(usernameCorrente)) {
			throw new CustomOperationToAdviceException("Sei già l'owner della pratica...");
		}
		response.setEsito(true);
		//test se ho bisogno di sollevamento incarico
		response.setSollevamentoIncarico(this.needSollevamentoIncarico(request.getCodicePratica()));
		PraticaDTO pratica = this.prDao.findByCodice(request.getCodicePratica());
		//cerco se esiste già per utente corrente un subentro in bozza, se no ne creo uno con mio cf nome e cogmome
		SubentroDTO subentro=null;
		try {
			subentro=subDao.findByCfUserAndPraticaAndStato(SecurityUtil.getUserDetail().getFiscalCode(), pratica.getId(), ICambioOwnershipService.STATO_BOZZA);
		}catch(EmptyResultDataAccessException e){
			//lo inserisco ex novo
			subentro=new SubentroDTO();
			subentro.setId(UUID.randomUUID());
			subentro.setIdPratica(pratica.getId());
			subentro.setCognome(SecurityUtil.getUserDetail().getLastName());
			subentro.setNome(SecurityUtil.getUserDetail().getFirstName());
			subentro.setCodiceFiscale(SecurityUtil.getUserDetail().getFiscalCode());
			subentro.setDataNascita(SecurityUtil.getUserDetail().getBirthDate());
			subentro.setStato(ICambioOwnershipService.STATO_BOZZA);
			subDao.insert(subentro);
		}
		response.setSubentro(new SubentroDto(subentro));
		return response;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void saveCambioOwnership(final String codicePratica, final SubentroDto subentro) throws Exception {
		//cerco per codice pratica cfUtente corrente e stato BOZZA e faccio update
		PraticaDTO pratica = this.prDao.findByCodice(codicePratica);
		SubentroDTO subentroRetrieved =null;
		try {
		 	subentroRetrieved=subDao.findByCfUserAndPraticaAndStato(SecurityUtil.getUserDetail().getFiscalCode(), pratica.getId(), ICambioOwnershipService.STATO_BOZZA);
		}catch(EmptyResultDataAccessException e) {
			throw new CustomOperationToAdviceException("Subentro non trovato, impossibile procedere!!");
		}
		//prendo solo i campi di interesse
		subentroRetrieved.setSesso(subentro.getSesso());
		subentroRetrieved.setDataNascita(subentro.getDataNascita());
		subentroRetrieved.setIdNazioneNascita(subentro.getIdNazioneNascita());
		subentroRetrieved.setNazioneNascita(subentro.getNazioneNascita());
		subentroRetrieved.setIdComuneNascita(subentro.getIdComuneNascita());
		subentroRetrieved.setComuneNascita(subentro.getComuneNascita());
		subentroRetrieved.setComuneNascitaEstero(subentro.getComuneNascitaEstero());
		subentroRetrieved.setIdNazioneResidenza(subentro.getIdNazioneResidenza());
		subentroRetrieved.setNazioneResidenza(subentro.getNazioneResidenza());
		subentroRetrieved.setIdProvinciaNascita(subentro.getIdProvinciaNascita());
		subentroRetrieved.setProvinciaNascita(subentro.getProvinciaNascita());
		subentroRetrieved.setIdProvinciaResidenza(subentro.getIdProvinciaResidenza());
		subentroRetrieved.setProvinciaResidenza(subentro.getProvinciaResidenza());
		subentroRetrieved.setIdComuneResidenza(subentro.getIdComuneResidenza());
		subentroRetrieved.setComuneResidenza(subentro.getComuneResidenza());
		subentroRetrieved.setComuneResidenzaEstero(subentro.getComuneResidenzaEstero());
		subentroRetrieved.setIndirizzoResidenza(subentro.getIndirizzoResidenza());
		subentroRetrieved.setCivicoResidenza(subentro.getCivicoResidenza());
		subentroRetrieved.setCapResidenza(subentro.getCapResidenza());
		subentroRetrieved.setPec(subentro.getPec());
		subentroRetrieved.setMail(subentro.getMail());
		subentroRetrieved.setDateUpdate(DateUtil.currentTimestamp());
		subDao.update(subentroRetrieved);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public String addModulo(final String codicePratica, final MultipartFile delega) throws Exception {
		final StopWatch sw = LogUtil.startLog("addModulo ", codicePratica);
		logger.info("Start addModulo {}", codicePratica);
		if(!FilenameUtils.getExtension(delega.getOriginalFilename()).equals("pdf")) {
			throw new CustomOperationToAdviceException("Il file deve essere un PDF");
		}
		String cmisId = null;
		final Path tmpPath = Files.createTempFile("", "");
		try(InputStream is = delega.getInputStream();
				FileOutputStream fos = new FileOutputStream(tmpPath.toFile())
				){
			IOUtils.copy(is, fos);
		}
		
		try {
			StringBuilder pathCms = new StringBuilder();
			pathCms.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue()).append(rootPathCms.replace("/", ""))
					.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
					.append(StringUtil.isNotEmpty(subpathDeveloper)
							? subpathDeveloper.replace("/", "") + AlfrescoPaths.PATH_SEPARATOR.getTextValue()
							: "");
//					.append(tmpPath.toString().replace("//", "/"));
			if (pathCms.charAt(pathCms.length() - 1) == '/') {
				pathCms.deleteCharAt(pathCms.length() - 1);
			}
			final String idPratica = this.prDao.findIdByCodice(codicePratica);
			final String hash = CheckSumUtil.getCheckSum(tmpPath.toFile());
					logger.info("idPratica {}", idPratica);
			
			cmisId = cmsService.uploadCms(tmpPath.toFile(), uploadUrl, pathCms.toString(), nomeApplicazione,
					StringUtil.isEmpty(LogUtil.getAuthorization()));
			this.subDao.addModulo(UUID.fromString(idPratica), cmisId, delega.getOriginalFilename(), hash);
			return hash;
		}catch(final Exception e) {
			logger.error("Error in addModulo", e);
			if(StringUtil.isNotEmpty(cmisId))
				cmsService.deleteCmisIdFromCms(deleteUrl, cmisId, nomeApplicazione);
			throw e;
		}finally {
			try {
				Files.deleteIfExists(tmpPath);
			}catch(final Exception e) {
				logger.error("Error in addModulo", e);
			}
			LogUtil.stopLog(sw);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteModulo(final String codicePratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("deleteModulo ", codicePratica);
		logger.info("Start deleteModulo {}", codicePratica);
		try {
			final String idPratica = this.prDao.findIdByCodice(codicePratica);
			logger.info("idPratica {}", idPratica);
			final SubentroDTO subentro = this.subDao.getCurrentSubentro(UUID.fromString(idPratica));
			this.subDao.deleteModulo(UUID.fromString(idPratica));
			cmsService.deleteCmisIdFromCms(deleteUrl, subentro.getCmisIdModulo(), nomeApplicazione);
		}finally {
			LogUtil.stopLog(sw);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public String addSollevamento(final String codicePratica, final MultipartFile sollevamento) throws Exception {
		final StopWatch sw = LogUtil.startLog("addSollevamento ", codicePratica);
		logger.info("Start addSollevamento {}", codicePratica);
		if(!FilenameUtils.getExtension(sollevamento.getOriginalFilename()).equals("pdf")) {
			throw new CustomOperationToAdviceException("Il file deve essere un PDF");
		}
		String cmisId = null;
		final Path tmpPath = Files.createTempFile("", "");
		try(InputStream is = sollevamento.getInputStream();
				FileOutputStream fos = new FileOutputStream(tmpPath.toFile())
				){
			IOUtils.copy(is, fos);
		}
		
		try {
			StringBuilder pathCms = new StringBuilder();
			pathCms.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue()).append(rootPathCms.replace("/", ""))
					.append(AlfrescoPaths.PATH_SEPARATOR.getTextValue())
					.append(StringUtil.isNotEmpty(subpathDeveloper)
							? subpathDeveloper.replace("/", "") + AlfrescoPaths.PATH_SEPARATOR.getTextValue()
							: "");
//					.append(tmpPath.toString().replace("//", "/"));
			if (pathCms.charAt(pathCms.length() - 1) == '/') {
				pathCms.deleteCharAt(pathCms.length() - 1);
			}
			final String idPratica = this.prDao.findIdByCodice(codicePratica);
			final String hash = CheckSumUtil.getCheckSum(tmpPath.toFile());
					logger.info("idPratica {}", idPratica);
			
			cmisId = cmsService.uploadCms(tmpPath.toFile(), uploadUrl, pathCms.toString(), nomeApplicazione,
					StringUtil.isEmpty(LogUtil.getAuthorization()));
			this.subDao.addSollevamento(UUID.fromString(idPratica), cmisId, sollevamento.getOriginalFilename(), hash);
			return hash;
		}catch(final Exception e) {
			logger.error("Error in addSollevamento", e);
			if(StringUtil.isNotEmpty(cmisId))
				cmsService.deleteCmisIdFromCms(deleteUrl, cmisId, nomeApplicazione);
			throw e;
		}finally {
			try {
				Files.deleteIfExists(tmpPath);
			}catch(final Exception e) {
				logger.error("Error in addSollevamento", e);
			}
			LogUtil.stopLog(sw);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void deleteSollevamento(final String codicePratica) throws Exception {
		final StopWatch sw = LogUtil.startLog("deleteModulo ", codicePratica);
		logger.info("Start deleteModulo {}", codicePratica);
		try {
			final String idPratica = this.prDao.findIdByCodice(codicePratica);
			logger.info("idPratica {}", idPratica);
			final SubentroDTO subentro = this.subDao.getCurrentSubentro(UUID.fromString(idPratica));
			this.subDao.deleteSollevamento(UUID.fromString(idPratica));
			cmsService.deleteCmisIdFromCms(deleteUrl, subentro.getCmisIdSollevamento(), nomeApplicazione);
		}finally {
			LogUtil.stopLog(sw);
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public String cambiaOwnership(final CambioOwnershipRequestBean request) throws Exception {
		final String codicePratica = request.getCodicePratica();
		final boolean exists = this.exists(codicePratica, request.getCodiceFiscaleProponente(), request.getCodiceSegreto());
		if(!exists)
			throw new CustomValidationException("Dati inviati non validi");
		final String idPratica = this.prDao.findIdByCodice(codicePratica);
		logger.info("idPratica {}", idPratica);
		logger.info("Start validazione");
		final SubentroDTO subentro = this.subDao.getCurrentSubentro(UUID.fromString(idPratica));

		if(this.needSollevamentoIncarico(codicePratica) && StringUtil.isBlank(subentro.getCmisIdSollevamento()))
			throw new CustomValidationException("Occorre inviare anche il sollevamento incarico");		
		
		final String codIstat=this.anagraficaDao.getCodiceCatastale(subentro.getIdNazioneNascita(), subentro.getIdComuneNascita());
		final boolean resultFc = FiscalCodeValidation.validate(subentro.getCodiceFiscale())
	               && FiscalCodeValidation.validateValue(subentro.getCodiceFiscale()
	                                                    ,subentro.getNome()
	                                                    ,subentro.getCognome()
	                                                    ,subentro.getSesso()
	                                                    ,subentro.getDataNascita()
	                                                    ,codIstat
	                                                    );

		if(!resultFc) {
			throw new CustomOperationToAdviceException("Errore durante la validazione del codice fiscale");
//			throw new CustomValidationException(StringUtil.concateneString("<ul><li>", this.bundle.getMessage("generic.codiceFiscale", null, Locale.getDefault()),"</li></ul>"));
		}
		logger.info("End validazione");
		logger.info("insert history");
		
		final PraticaOwnerHistoryDTO ownerDto = new PraticaOwnerHistoryDTO();
		ownerDto.setId(UUID.fromString(idPratica));
		ownerDto.setCmisIdDelega(subentro.getCmisIdModulo());
		ownerDto.setFileNameDelega(subentro.getFileNameModulo());
		ownerDto.setCmisIdSollevamentoIncarico(subentro.getCmisIdSollevamento());
		ownerDto.setFileNameSollevamentoIncarico(subentro.getFileNameSollevamento());
		ownerDto.setCodiceSegretoUtilizzato(request.getCodiceSegreto());
		ownerDto.setUsername(SecurityUtil.getUsername());

		this.historyDao.insert(ownerDto);

		logger.info("Add false corrente");

		this.prDelDao.setUserCorrenteToFalse(UUID.fromString(idPratica));

		logger.info("Nuovo codice segreto");

		final String codiceSegreto = this.prDao.updateCodiceSegreto(codicePratica);
		logger.info("Change owner");
		this.prDao.changeOwner(UUID.fromString(idPratica));

		if(this.prDao.getStato(UUID.fromString(idPratica)).equals(AttivitaDaEspletare.COMPILA_DOMANDA)) {
			logger.info("Annulla validazione");
			this.prDao.annullaValidazione(UUID.fromString(idPratica));
		}

		logger.info("Insert delegato");
		final short index = this.prDelDao.insertFromSubentro(subentro, UUID.fromString(idPratica));
		logger.info("End subentro");
		this.subDao.endSubentro(UUID.fromString(idPratica));
		this.subDao.cancellaSubentriNonConclusi(UUID.fromString(idPratica));
		logger.info("Insert allegato");
		this.insertDocumentazioneAllegato(subentro, idPratica, index);
		logger.info("update comunicazione");
		logger.info("Send comunicazioni");
	    comunicazioniService.sendNuovoCodiceSegreto(UUID.fromString(idPratica), codiceSegreto);
		return codicePratica;
	}
	
	private void insertDocumentazioneAllegato(final SubentroDTO subentro ,final String idPratica, final short index) throws Exception {
		this.insertDocumentazioneAllegato(subentro.getCmisIdModulo(), idPratica, subentro.getFileNameModulo(), index, false);
		if(StringUtil.isNotBlank(subentro.getCmisIdSollevamento())) {
			this.insertDocumentazioneAllegato(subentro.getCmisIdSollevamento(), idPratica, subentro.getFileNameSollevamento(), index, true);
		}
	}
	
	private void insertDocumentazioneAllegato(final String cmisIdOld
			,final String idPratica
			,final String fileName
			,final Short index
			,final boolean sollevamento
			) throws Exception{
		logger.info("Start insertDocumentazioneAllegato");
		String cmisId = null;
		final Path file = Files.createTempFile("", "");
		try {
			cmsService.downloadFromCmsStream(cmisIdOld, downloadUrl, nomeApplicazione, file.toFile().getAbsolutePath());
			final PraticaDTO pratica = this.prDao.find(UUID.fromString(idPratica));
			final MockMultipartFile mockFile = new MockMultipartFile(fileName, file.toFile());
			mockFile.setContentType("application/pdf");
			if(!sollevamento) {
				this.allegatoService.uploadDelegaAllegato(pratica, index, fileName, mockFile, true);
			} else {
				this.allegatoService.uploadAllegatoSollevamentoIncarico(pratica, index, fileName, mockFile, true);
			}
		}catch(final Exception e) {
			logger.error("Error in insertDocumentazioneAllegato", e);
			if(StringUtil.isNotBlank(cmisId))
				cmsService.deleteCmisIdFromCms(deleteUrl, cmisId, nomeApplicazione);
		}finally {
			Files.deleteIfExists(file);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public void downloadDelega(final String codicePratica, final HttpServletResponse response) throws Exception {
		final String idPratica = this.prDao.findIdByCodice(codicePratica);
		final String cmisId = this.subDao.getCmisModulo(UUID.fromString(idPratica));
		cmsService.downloadFromCmsStream(cmisId, downloadUrl, nomeApplicazione, response);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public void downloadSollevamento(final String codicePratica, final HttpServletResponse response) throws Exception {
		final String idPratica = this.prDao.findIdByCodice(codicePratica);
		final String cmisId = this.subDao.getCmisSollevamento(UUID.fromString(idPratica));
		cmsService.downloadFromCmsStream(cmisId, downloadUrl, nomeApplicazione, response);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void sollevaIncarico(final String idPratica) throws Exception {
		if(!this.repDao.getCfRichiedente(idPratica).equals(SecurityUtil.getUserDetail().getFiscalCode())){
			throw new CustomOperationException("Utente non è richiedente");
		}
		final String codicePratica = this.prDao.findCodiceById(idPratica);
		if(!this.needSollevamentoIncarico(codicePratica)) {
			throw new CustomOperationException("Utente è owner della pratica");
		}
		logger.info("Modifica delegato");
		this.prDelDao.setUserCorrenteToFalse(UUID.fromString(idPratica));
		logger.info("Change owner");
		this.prDao.changeOwner(UUID.fromString(idPratica));
	}


	/**
	 * true se l'owner pratica è proprio il richiedente
	 * @author acolaianni
	 *
	 * @param codicePratica
	 * @return
	 */
	private boolean needSollevamentoIncarico(final String codicePratica) throws Exception {
		final String idPratica = this.prDao.findIdByCodice(codicePratica);
		final String codiceFiscaleRichiedente = this.repDao.getCfRichiedente(idPratica);
		final String codiceFiscaleOwner = this.commonDao.getCfUtenteByUsername(this.prDao.getOwnerById(idPratica));
		return codiceFiscaleRichiedente.equals(codiceFiscaleOwner) == false;
	}
	
	private boolean exists(final String codicePratica, final String codiceFiscaleProponente, final String codiceSegreto) throws Exception{
		final String idPratica = this.subDao.subentroExists(codicePratica, codiceFiscaleProponente, codiceSegreto);
		return idPratica != null ? !idPratica.isBlank() : false;
	}
	
}
