package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DocumentazioneCdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDocumentazioneCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaDocumentazioneCdsRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaDocumentazioneCdsSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaDocumentazioneCdsService;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomUnauthorizedException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.log.LogUtil;

@Service
public class PraticaDocumentazioneCdsServiceImpl extends 
GenericCrudService<PraticaDocumentazioneCdsDTO, PraticaDocumentazioneCdsSearch, PraticaDocumentazioneCdsRepository> implements IPraticaDocumentazioneCdsService {
	
private static final Logger LOGGER = LoggerFactory.getLogger(PraticaDocumentazioneCdsServiceImpl.class);
	
	@Value("${cms.url.download}")
	private String urlDownload;
	@Value("${spring.application.name}")
	private String nomeApplicazione;

	
	@Autowired
	private PraticaDocumentazioneCdsRepository dao;
	@Autowired
	private PraticaRepository praticaDao;
	@Autowired
	private TipoContenutoRepository tipoRepository;
	@Autowired
	private IHttpClientService clientAlfresco;
	@Autowired
	private IQueueService queueService;
	
	/**
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @see it.eng.tz.aet.Pratica.service.IDocumentazioneCdsService#list(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<DocumentazioneCdsDto> list(final String idPratica)  throws Exception{
		final StopWatch sw = LogUtil.startLog("list ", idPratica);
		LOGGER.info("Start list {}", idPratica);
		try {
			this.canRead(idPratica);
			return this.dao.list(idPratica, this.isRegione(idPratica));
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}


	/**
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @see it.eng.tz.aet.Pratica.service.IDocumentazioneCdsService#save(String, List)
	 */
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public void save(final String idPratica, final List<PraticaDocumentazioneCdsDTO> list)  throws Exception{
		final StopWatch sw = LogUtil.startLog("save ", idPratica);
		LOGGER.info("Start save {} {}", idPratica);
		try {
			this.canWrite(idPratica);
			final String codiceTrasmissione = this.praticaDao.findCodiceById(idPratica);
			final TipoContenutoDTO tipoDocumento = this.tipoRepository.findById(list.get(0).getIdTipo());
			for (final Iterator<PraticaDocumentazioneCdsDTO> iterator = list.iterator(); iterator.hasNext();) {
				final Path path = Files.createTempFile("", "");
				try {
					final PraticaDocumentazioneCdsDTO dto = iterator.next();
					DocumentazioneCdsDto dtoDb = this.dao.find(idPratica, Long.valueOf(dto.getId()), this.isRegione(idPratica));
					if(dtoDb.getIdTipoDocumento() != null) {
						throw new CustomOperationException("Documento già inserito");
					}
					dto.setIdTipo(tipoDocumento.getId());
					this.dao.insert(dto);
//					if(tipoDocumento.getCdpFolder() != null) {
//						LOGGER.info("Add scheduler per documentazione");
//						dtoDb = this.dao.find(idPratica, Long.valueOf(dto.getId()), this.isRegione(idPratica));
//						this.clientAlfresco.downloadFromCmsStream(dtoDb.getCmisId()
//								                                 ,this.urlDownload
//								                                 ,this.alfrescoUsername
//								                                 ,path.toFile().getAbsolutePath()
//								                                 );
//						
//						
//						final SimpleApiDocumentoBean bean = new SimpleApiDocumentoBean();
//						bean.setCmisId(dtoDb.getCmisId());
//						bean.setDataCaricamento(dtoDb.getDataCreazione());
//						bean.setDimensione(path.toFile().length());
//						bean.setIdDocumentazione(UUID.fromString(tipoCaricaDocumento.getCdpFolder()));
//						bean.setIdPratica(codiceTrasmissione);
//						bean.setNome(dtoDb.getNome());
//						this.queueService.insertNewQueue(AetConstants.CDP_GENERIC_DOC_ID, 3600, bean);
//					}else {
//						LOGGER.warn("Folder Cdp non valorizzata");
//					}
				}finally {
					Files.deleteIfExists(path);
				}
			}
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Check if user can read
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @param idPratica
	 * @throws CustomUnauthorizedException
	 */
	private void canRead(final String idPratica) throws CustomUnauthorizedException {
//		this.isruttoriaOrmService.userCanShowPratica(idPratica);
	}
	/**
	 * Check if user can write
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @param idPratica
	 * @throws CustomUnauthorizedException
	 * @throws CustomOperationException 
	 */
	private void canWrite(final String idPratica) throws CustomUnauthorizedException, CustomOperationException {
//		if(this.isruttoriaOrmService.userIsRdp(idPratica) == false) {
//			throw new CustomUnauthorizedException("Utente non RDP");
//		}
//		final PraticaStatoEnum stato = this.isruttoriaOrmService.getStato(idPratica);
//		if(stato.equals(PraticaStatoEnum.ARCHIVIATA)
//		|| stato.equals(PraticaStatoEnum.TRASMESSA )
//		)
//			throw new CustomOperationException("Pratica conclusa o archiviata");
	}


	/**
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @see it.eng.tz.aet.Pratica.service.IDocumentazioneCdsService#download(java.lang.String, java.lang.Long, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public void download(final String idPratica, final Long id, final HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("download ", idPratica, " ", id);
		LOGGER.info("Start download {} {}", idPratica, id);
		try {
//			this.canRead(idPratica);
			this.clientAlfresco.downloadFromCmsStream(this.dao.find(idPratica, id, this.isRegione(idPratica)).getCmisId()
					                                 ,this.urlDownload
					                                 ,"AUT-AMB"
					                                 ,response
					                                 );
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param idPratica 
	 * @return is regione
	 */
	private boolean isRegione(final String idPratica) {
//		return this.isruttoriaOrmService.userIsRdp(idPratica)
//			|| this.isruttoriaOrmService.userIsIstruttore(idPratica)
//			|| this.isruttoriaOrmService.userIsDirigente(idPratica)
//			|| this.isruttoriaOrmService.userIsRdpAssegnatario(idPratica)
//			;
		return true;
	}
	
	@Override
	protected PraticaDocumentazioneCdsRepository getDao() {
		// TODO
		return null;
	}

	@Override
	protected void validateInsertDTO(PraticaDocumentazioneCdsDTO entity) throws CustomValidationException {
		// TODO
		
	}

	@Override
	protected void validateUpdateDTO(PraticaDocumentazioneCdsDTO entity) throws CustomValidationException {
		// TODO
		
	}

}
