package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.bean.CdsDocumentSchedulerBean;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
import it.eng.tz.puglia.cds.service.ICdsClientService;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * append dei documenti su cds
 * @author acolaianni
 *
 */
@Component(ConferenzaDeiServiziDocumentiExecutor.SPRING_ID)
public class ConferenzaDeiServiziDocumentiExecutor implements IBatchQueueExecutor{

	/**
	 * Spring id
	 */
	public static final String SPRING_ID = "ConferenzaDeiServiziDocumentiExecutor";
	/**
	 * Logger
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ConferenzaDeiServiziDocumentiExecutor.class);
	/**
	 * cds client
	 */
	@Autowired
	private ICdsClientService clientService;
	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @see it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor#execute(java.lang.String)
	 */
	@Override
	public void execute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("execute");
		LOGGER.trace("Start execute");
		try {
			final CdsDocumentSchedulerBean bean = JsonUtil.toBean(CryptUtil.decrypt(parameters), CdsDocumentSchedulerBean.class);
			LOGGER.trace("Id conferenza {}", bean.getIdConferenza());
			this.clientService.aggiungeDocumentoAConferenza(bean.getDocumento(), (int)bean.getIdConferenza());
		}catch(final Exception e) {
			LOGGER.error("Error in execute", e );
			throw e;
		}finally {
			LOGGER.trace(LogUtil.stopLog(sw));
		}
		
	}
	
}
