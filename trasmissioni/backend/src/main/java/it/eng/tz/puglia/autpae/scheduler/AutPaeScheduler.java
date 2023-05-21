package it.eng.tz.puglia.autpae.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
import it.eng.tz.puglia.batch.queue.runnable.GenericBatchQueueRunnable;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.SendMailRequestBean;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Scheduler generico
 * @author Antonio La Gatta
 * @date 4 ago 2021
 */
public abstract class AutPaeScheduler extends GenericBatchQueueRunnable {

	/**
	 * application name
	 */
	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${url.mail.notifica:}")
	private String urlMailNotifica;
	
	@Value("#{'${url.mail.notifica.destinatari:}'.split(',')}") 
    private List<String> destinatariMailNotifica;
	
	@Value("${cron.expression}")
	private String cronExpression;

	@Autowired
	private IHttpClientService mailService;
	
	public AutPaeScheduler(final String id, final IBatchQueueExecutor executor) {
		super(id, executor);
	}

	@PostConstruct
	public void init() {
		this.logger.info("Post Constructor");
		this.setApplicationName(this.applicationName.toUpperCase());
	}
	
	@Override
	public String getCron() {
		return this.cronExpression;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 6 ott 2021
	 * @see it.eng.tz.puglia.batch.queue.runnable.GenericBatchQueueRunnable#notifyEnd(long, boolean)
	 */
	@Override
	public void notifyEnd(final long id, final boolean success) {
		super.notifyEnd(id, success);
		if(!success) {
			this.sendMailNotificaErrore(id);
		}
	}
	/**
	 * Metodo per invio mail di notifica su errori scheduler
	 * @author Antonio La Gatta
	 * @date 6 ott 2021
	 * @param id
	 */
	private void sendMailNotificaErrore(final long id) {
		if(StringUtil.isNotBlank(this.urlMailNotifica) && destinatariMailNotifica != null && !destinatariMailNotifica.isEmpty()) {
			this.logger.info("Start sendMailNotificaErrore {}", id);
			try {
					final SendMailRequestBean bean = new SendMailRequestBean();
					bean.setSubject("SIT Puglia - Autorizzazioni Paesaggistiche - Notifica Errore Batch");
					bean.setText(StringUtil.concateneString("Notifica Errore Batch con id ", id));
					bean.setTo(new ArrayList<>());
					bean.setKey(this.applicationName);
					this.destinatariMailNotifica.forEach(mail ->{
	                    if(StringUtil.isNotBlank(mail))
	                        bean.getTo().add(mail);
	                });
					this.mailService.sendMail(this.urlMailNotifica, bean, null,true);
			}catch(final Exception e) {
				this.logger.error("Errore in invio mail notifica", e);
			}
		}
	}
}
