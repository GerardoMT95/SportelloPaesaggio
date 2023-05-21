package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler.executor;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.ReportConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReportDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportOutputBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.ReportParameterBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.generate.IReportGenerateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.utils.ReportTipoEnum;
import it.eng.tz.puglia.batch.scheduler.executor.IBatchSchedulerExecutor;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.SendMailRequestBean;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
/**
 * Executor to clean report
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
@Component(ReportExecutor.ID_SPRING)
public class ReportExecutor implements IBatchSchedulerExecutor{

	/**
	 * Spring id
	 */
	public static final String ID_SPRING = "ReportExecutor";
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ReportExecutor.class);
	
	@Autowired
	private IReportService service;

	@Autowired 
	private IHttpClientService mailService;
	
	@Autowired
	private IConfigurazioneService configurazioneService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired 
	private CommonRepository commonDao;
	
	@Autowired
	private ApplicationProperties props;

	
	@Value("${url.mail.notifica}")
	private String url;
	
	@Value("${mail.key}")
	private String keyMail;

	/**
	 * @author Antonio La Gatta
	 * @date 2 mag 2022
	 * @see it.eng.tz.puglia.batch.scheduler.executor.IBatchSchedulerExecutor#execute()
	 */
	@Override
	public void execute() throws Exception {
		final StopWatch sw = LogUtil.startLog("execute");
		LOGGER.info("Start execute");
		try {
			final String id = this.service.getNextId();
			if(StringUtil.isNotBlank(id)) {
				LOGGER.info("execute {}", id);
				final ReportDTO dto = this.service.detail(id);
				LOGGER.info("Recuperato Detail");
				final ReportParameterBean input = JsonUtil.toBean(CryptUtil.decrypt(dto.getParametri()), ReportParameterBean.class);
				LOGGER.info("Recuperati parametri");
				try {
					final ReportOutputBean result = this.applicationContext.getBean(dto.getTipo(), IReportGenerateService.class).generateReport(input, dto.getUsername());
					LOGGER.info("Eseguito report");
					this.service.concludiReport(id, result);
					final ReportConfigBean conf = this.configurazioneService.findConfigurazioneCorrente(new Date(), ReportConfigBean.class);
					final SendMailRequestBean mail = new SendMailRequestBean();
					mail.setTo(new ArrayList<>());
					mail.getTo().add(dto.getMail());
					mail.setSubject(conf.getReportMailOggetto());
					mail.setText(this.replace(conf.getReportMailTesto(), conf.getReportMaxTime(), dto.getTipo(), id));
					mail.setKey(this.keyMail);
					this.mailService.sendMail(this.url, mail, null, true);
					LOGGER.info("Inviata mail");
				}catch(final Exception e) {
					LOGGER.error("Error in execute report", e);
					this.service.erroreReport(id);
				}
			}
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	private String replace(final String testo, final String ore, final String tipo, final String idDownload) throws Exception {
		return testo.replace("{NUMERO ORE}"  , ore + " ore"   )
   				    .replace("{URL DOWNLOAD}", this.buildLink(idDownload))
   				    .replace("{TIPO}"        , ReportTipoEnum.getTitolo(tipo))
   				    ; 
	}
	
	private String buildLink(final String idDownload) throws Exception {
		final String context = this.commonDao.getConfigurationValue(IReportService.KEY_URL_DOWNLOAD_PREFIX, props.getCodiceApplicazione());
		final StringBuilder sb =  new StringBuilder("<a href=\"")
				                            .append(context)
				                            .append("/public/" + IReportService.BASE_PUBLIC_DOWNLOAD)
				                            .append("/download-report")
				                            .append("/").append(idDownload)
				                            ;
		return sb.append("\">Link</a>").toString();
	}
}
