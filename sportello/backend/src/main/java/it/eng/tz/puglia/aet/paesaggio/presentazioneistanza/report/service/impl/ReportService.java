package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.ReportConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReportDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReportRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReportSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.DownloadReportBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.InsertReportBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportLocalizzazioneBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOneriIstruttoriDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOutputBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.SelectReportTypeDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler.ReportScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.utils.ReportTipoEnum;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomUnauthorizedException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.util.uuid.UuidUtil;

/**
 * Service for report
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
@Service
public class ReportService extends GenericCrudService<ReportDTO, ReportSearch, ReportRepository> implements IReportService {

	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private RemoteSchemaService remoteSchemaSvc;

	@Autowired
	private IConfigurazioneService configurazioneService;
	
	@Autowired 
	private IQueueService queueService;
	
    @Autowired
    private ReportRepository dao;
    
	@Override
	protected ReportRepository getDao() {
		return this.dao;
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#idTerminati()
	 */
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public List<String> idTerminati() {
		final StopWatch sw = LogUtil.startLog("idTerminati");
		LOGGER.info("Start idTerminati");
		try {
			return this.dao.idTerminati();
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#eliminaReport(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE, rollbackFor = Exception.class)
	public void eliminaReport(final String id) throws Exception{
		final StopWatch sw = LogUtil.startLog("eliminaReport ", id);
		LOGGER.info("Start eliminaReport {}", id);
		try {
			this.dao.eliminaReport(id);
			final String pathFile = this.dao.pathFile(id);
			if(StringUtil.isNotBlank(pathFile))
				Files.deleteIfExists(Paths.get(pathFile));
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#dataCreazione(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public Timestamp dataCreazione(final String id) {
		final StopWatch sw = LogUtil.startLog("dataCreazione ", id);
		LOGGER.info("Start dataCreazione {}", id);
		try {
			return this.dao.dataCreazione(id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#getMaxOre()
	 */
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public int getMaxOre() throws Exception {
		final StopWatch sw = LogUtil.startLog("getMaxOre");
		LOGGER.info("Start getMaxOre");
		try {
			final ReportConfigBean conf = this.configurazioneService.findConfigurazioneCorrente(new Date(), ReportConfigBean.class);
			if(conf != null) {
				return Integer.parseInt(conf.getReportMaxTime());
			}
			return 0;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#getNextId()
	 */
	@Override
	@Transactional(readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE, rollbackFor = Exception.class)
	public String getNextId() {
		final StopWatch sw = LogUtil.startLog("getNextId");
		LOGGER.info("Start getNextId");
		try {
			final String id = this.dao.getNextId();
			if(StringUtil.isNotBlank(id))
				this.dao.avviaReport(id);
			return id;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#concludiReport(java.lang.String, it.eng.tz.aet.report.bean.ReportOutputBean)
	 */
	@Override
	@Transactional(readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE, rollbackFor = Exception.class)
	public void concludiReport(final String id, final ReportOutputBean bean) throws Exception {
		final StopWatch sw = LogUtil.startLog("concludiReport ", id);
		LOGGER.info("Start concludiReport {}", id);
		try {
			this.dao.concludiReport(id, bean);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#erroreReport(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE, rollbackFor = Exception.class)
	public void erroreReport(final String id) {
		final StopWatch sw = LogUtil.startLog("erroreReport ", id);
		LOGGER.info("Start erroreReport {}", id);
		try {
			this.dao.erroreReport(id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#detail(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public ReportDTO detail(final String id) {
		final StopWatch sw = LogUtil.startLog("detail ", id);
		LOGGER.info("Start detail {}", id);
		try {
			final ReportDTO pk = new ReportDTO();
			pk.setId(id);
			return this.dao.find(pk);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#getMail(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public String getMail(final String id) {
		final StopWatch sw = LogUtil.startLog("getMail ", id);
		LOGGER.info("Start getMail {}", id);
		try {
			final String mail = this.dao.getMail(id);
			LOGGER.info("mail {}", mail);
			return mail;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 * @see it.eng.tz.aet.report.service.IReportService#dataRichiesta(java.lang.String)
	 */
	@Override
	public Timestamp dataRichiesta(final String id) {
		final StopWatch sw = LogUtil.startLog("dataRichiesta ", id);
		LOGGER.info("Start dataRichiesta {}", id);
		try {
			return this.dao.dataRichiesta(id);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	protected void validateInsertDTO(ReportDTO entity) throws CustomValidationException {
		// TODO
		
	}

	@Override
	protected void validateUpdateDTO(ReportDTO entity) throws CustomValidationException {
		// TODO
		
	}
	
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
    public PaginatedList<ReportDTO> search(final ReportSearch search) throws Exception{
		Ruoli ruolo = userUtil.getRuolo(userUtil.getCodice_GruppoIdRuolo());
		if(ruolo != Ruoli.DIRIGENTE) {
			throw new Exception("Devi essere dirigente per poter visualizzare i report");
		}
		search.setEnteDelegato(String.valueOf(userUtil.getIntegerId()));
		return this.dao.search(search);
	}
	
	@Override
	@Transactional(readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE)
    public void insert(final InsertReportBean bean) throws Exception {
		try {
			Ruoli ruolo = userUtil.getRuolo(userUtil.getCodice_GruppoIdRuolo());
			if(ruolo != Ruoli.DIRIGENTE) {
				throw new Exception("Devi essere dirigente per poter inserire un nuovo report");
			}
			final ReportParameterBean parametri = new ReportParameterBean();
			parametri.setDateFrom(bean.getDataFrom());
			parametri.setDateTo(bean.getDataTo());
			parametri.setDirigente(true);
			parametri.setIdProcedimento(bean.getTipoProcedimento());
			parametri.setEstensione(bean.getFormato());
	    	final ReportDTO report = new ReportDTO();
			report.setEnteDelegato(userUtil.getIntegerId());
			final PaesaggioOrganizzazioneDTO ente = this.remoteSchemaSvc.findPaesaggioById(report.getEnteDelegato().longValue());
			parametri.setEnteDelegato(report.getEnteDelegato());
			parametri.setDescrizioneEnte(ente.getDenominazione());
			report.setDescrizioneEnte(ente.getDenominazione());
	    	report.setTipo(ReportTipoEnum.getCodice(bean.getTipo()));
			report.setParametri(CryptUtil.encrypt(JsonUtil.toJson(parametri)));
			report.setId(UuidUtil.newValue());
			report.setUsername(userUtil.getMyProfile().getUsername());
			report.setStato("I");
			report.setDataRichiesta(new Timestamp(System.currentTimeMillis()));
			report.setMail(userUtil.getMyProfile().getEmail());
			final String fileName = new StringBuilder(ReportTipoEnum.getTitolo(report.getTipo()))
					.append(" - ")
					.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
					.append(".")
					.append(bean.getFormato())
					.toString();
			report.setFileName(fileName);
			this.dao.insert(report);
			this.queueService.insertNewQueue(ReportScheduler.ID_SPRING, 60);
		} catch (Exception e) {
			throw new Exception(e);
		}
    }



	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public List<ReportOneriIstruttoriDto> listaOneriIstruttori(final ReportParameterBean parameters, final String username)
			throws Exception {
		return this.dao.listaOneriIstruttori(parameters, username);
	}
	
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public ReportLocalizzazioneBean listaLocalizzazione(final ReportParameterBean parameters, final String username) {
		final StopWatch sw = LogUtil.startLog("lista");
		LOGGER.info("Start lista");
		try {
			final ReportLocalizzazioneBean result = new ReportLocalizzazioneBean();
			result.setComuneList(this.dao.listaComune(parameters, username));
			result.setComuneProcedimentoList(this.dao.listaComuneProcedimento(parameters, username));
			result.setProvinciaList(this.dao.listaProvincia(parameters, username));
			result.setProvinciaProcedimentoList(this.dao.listaProvinciaProcedimento(parameters, username));
			return result;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	public List<SelectReportTypeDto> selectTipologie(){
		final List<SelectReportTypeDto> result = new ArrayList<>();
		for(final ReportTipoEnum tipo : ReportTipoEnum.values()) {
			final PlainStringValueLabel labelValue = new PlainStringValueLabel();
			labelValue.setDescription(tipo.getTitolo());
			labelValue.setValue(tipo.name());
			final Map<String, List<String>> formati = new HashMap<>();
			formati.put(tipo.name(), tipo.getEstensioni());
			final SelectReportTypeDto item = new SelectReportTypeDto();
			item.setLabelValue(labelValue);
			item.setFormati(formati);
			result.add(item);
		}
		return result;
	}
	
	@Override
	@Transactional(readOnly = true, transactionManager = DatabaseConfiguration.TX_READ)
	public void downloadReport(final String idDownload, final String username, final HttpServletResponse response)
	throws Exception {
		final StopWatch sw = LogUtil.startLog("downloadReport ", idDownload, " ", username);
		LOGGER.info("downloadReport {} {} {}", idDownload, username);
		try {
			final DownloadReportBean bean = this.dao.downloadReport(idDownload, username);
			if(bean != null
			&& Files.exists(Paths.get(bean.getPathFile()))
			) {
				response.setContentType(new Tika().detect(bean.getPathFile()));
				response.setHeader("Content-disposition", StringUtil.concateneString("attachment; filename=\"", bean.getNomeFile(), "\""));
				try(InputStream is = new FileInputStream(bean.getPathFile());
					OutputStream os = response.getOutputStream();
				){
					IOUtils.copy(is, os);
				}
			}else {
				throw new CustomUnauthorizedException("Download non trovato");
			}
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
		
	}

}
