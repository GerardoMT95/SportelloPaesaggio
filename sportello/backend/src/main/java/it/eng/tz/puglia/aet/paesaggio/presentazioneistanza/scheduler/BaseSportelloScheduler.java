package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
import it.eng.tz.puglia.batch.queue.runnable.GenericBatchQueueRunnable;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.SendMailRequestBean;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Base scheduler
 * 
 * @author Antonio La Gatta
 * @date 4 feb 2022
 */
public abstract class BaseSportelloScheduler extends GenericBatchQueueRunnable {

	@Value("${profilemanager.codiceapplicazione}")
	private String applicationName;

	@Value("${cron.expression}")
	private String cronExpression;

	@Value("${url.mail.notifica:}")
	private String urlMailNotifica;

	@Value("#{'${url.mail.notifica.destinatari:}'.split(',')}")
	private List<String> destinatariMailNotifica;

	@Autowired
	private IHttpClientService mailService;

	public BaseSportelloScheduler(final String idBatchScheduler, final IBatchQueueExecutor executor) {
		super(idBatchScheduler, executor);
	}

	@PostConstruct
	public void init() {
		this.setApplicationName(this.applicationName.toUpperCase());
	}

	/**
	 * @author Antonio La Gatta
	 * @date 3 feb 2022
	 * @see it.eng.tz.puglia.batch.queue.runnable.GenericBatchQueueRunnable#getCron()
	 */
	@Override
	public String getCron() {
		return this.cronExpression;
	}

	/**
	 * @author Antonio La Gatta
	 * @date 6 ott 2021
	 * @see it.eng.tz.puglia.batch.queue.runnable.GenericBatchQueueRunnable#notifyEnd(long,
	 *      boolean)
	 */
	@Override
	public void notifyEnd(final long id, final boolean success) {
		super.notifyEnd(id, success);
		if (!success) {
			this.sendMailNotificaErrore(id);
		}
	}

	/**
	 * Metodo per invio mail di notifica su errori scheduler
	 * 
	 * @author Antonio La Gatta
	 * @date 6 ott 2021
	 * @param id
	 */
	private void sendMailNotificaErrore(final long id) {
		if (StringUtil.isNotBlank(this.urlMailNotifica) && ListUtil.isNotEmpty(this.destinatariMailNotifica)) {
			this.logger.info("Start sendMailNotificaErrore {}", id);
			try {
				this.logger.info("Url mail notifica {}", this.urlMailNotifica);
				this.logger.info("destinatari mail notifica {}", this.destinatariMailNotifica);
				final SendMailRequestBean bean = new SendMailRequestBean();
				bean.setSubject("pugliacon - Sportello Paesaggio - Notifica Errore Batch");
				bean.setText(StringUtil.concateneString("Notifica Errore Batch con id ", id));
				bean.setTo(new ArrayList<>());
				bean.setKey(this.applicationName);
				this.destinatariMailNotifica.forEach(mail -> {
					if (StringUtil.isNotBlank(mail))
						bean.getTo().add(mail);
				});
				this.mailService.sendMail(this.urlMailNotifica, bean, null,true);
			} catch (final Exception e) {
				this.logger.error("Errore in invio mail notifica", e);
			}
		}
	}

}
