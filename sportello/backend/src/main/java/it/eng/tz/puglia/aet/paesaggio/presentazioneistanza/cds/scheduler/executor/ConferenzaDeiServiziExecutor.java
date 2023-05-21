package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service.IConferenzaDeiServiziService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.CdsBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor.SportelloBaseExecutor;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.SezioneIstruttoria;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.IResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.cds.bean.ConferenzaApiCreatoreDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiDto;
import it.eng.tz.puglia.cds.service.ICdsClientService;
import it.eng.tz.puglia.cds.service.impl.CdsClientService;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.CmsUploadRequestBean;
import it.eng.tz.puglia.service.http.bean.SendMailRequestBean;
import it.eng.tz.puglia.service.http.bean.SendMailResponseBean;
import it.eng.tz.puglia.service.http.bean.UploadMailRequestBean;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Executor per avviare cds
 * 
 * @author Antonio La Gatta
 * @date 30 nov 2021
 */
@Component(ConferenzaDeiServiziExecutor.SPRING_ID)
public class ConferenzaDeiServiziExecutor extends SportelloBaseExecutor {
	
	@Value("${profilemanager.codiceapplicazione}")
	private String applicationName;

	@Value("${cron.expression}")
	private String cronExpression;

	@Value("${url.mail.notifica:}")
	private String urlMailNotifica;

	@Value("#{'${url.mail.notifica.destinatari:}'.split(',')}")
	private List<String> destinatariMailNotifica;

	/**
	 * SPRING ID
	 */
	public static final String SPRING_ID = "CONFERENZA_DEI_SERVIZI_EXECUTOR";
	/**
	 * Logger
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ConferenzaDeiServiziExecutor.class);
	/**
	 * cds client
	 */
	@Autowired
	private ICdsClientService clientService;
	/**
	 * db service
	 */
	@Autowired
	private IConferenzaDeiServiziService dbService;
	
	@Autowired
	private ITemplateMailService templateMailSvc;

	@Autowired
	private IHttpClientService mailService;
	
	@Autowired
	private IResolveTemplateService resolveTemplateSvc;

	@Override
	public UUID internalExecute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("internalExecute ", parameters);
		LOGGER.info("Start internalExecute {}", parameters);
		try {
			final CdsBean bean = JsonUtil.toBean(parameters, CdsBean.class);
			final ConferenzaApiDto conferenza = this.dbService.getConferenzaApiDto(bean.getId(), bean.getCodiceBase());
			final int idConferenza = this.clientService.creaConferenza(conferenza);
			LOGGER.info("Id conferenza {}", idConferenza);
			this.dbService.updateIdCds(bean.getId(), idConferenza);
			final SendMailRequestBean mailBean = new SendMailRequestBean();
			final TemplateEmailDTO template = this.templateMailSvc.findAncheSuDefault(0, "NOTIFICA_CDS");
			mailBean.setSubject(this.resolveTemplateSvc.risolviTesto(template.getPlaceholders(), template.getOggetto(), bean.getIdPratica()));
			mailBean.setText(this.resolveTemplateSvc.risolviTesto(template.getPlaceholders(), template.getTesto(), bean.getIdPratica()));
			mailBean.setTo(new ArrayList<>());
			mailBean.setKey(this.applicationName);
			mailBean.getTo().add(conferenza.getCreatore().getMail());
			this.mailService.sendMail(this.urlMailNotifica, mailBean, null, true);
			return bean.getIdPratica();
		}catch(final Exception e) {
			LOGGER.error("Error in execute", e );
			throw e;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public String getId() {
		return SPRING_ID;
	}

}
