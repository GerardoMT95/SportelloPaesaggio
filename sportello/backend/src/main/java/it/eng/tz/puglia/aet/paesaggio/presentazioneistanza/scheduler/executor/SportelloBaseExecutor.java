package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.LogSchedulerService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.util.SchedulerThreadLocal;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpClientService;

/**
 * Base executor for log internal operation
 * @author Antonio La Gatta
 * @date 4 feb 2022
 */
public abstract class SportelloBaseExecutor implements IBatchQueueExecutor{
	
	/**
	 * LOGGER
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 
	 * @author acolaianni
	 *
	 * @param parameters
	 * @return UUID della pratica a cui si fa riferimento , null altrimenti
	 * @throws Exception
	 */
	public abstract UUID internalExecute(String parameters) throws Exception;

	public abstract String getId();

	@Autowired
	private LogSchedulerService logService;
	@Autowired
	private IHttpClientService clientService;

	/**
	 * @author Antonio La Gatta
	 * @date 4 feb 2022
	 * @see it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor#execute(java.lang.String)
	 */
	@Override
	public void execute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("execute");
		this.logger.info("Start execute");
		try {
			if(SecurityUtil.isAuthenticated() == false) {
				LogUtil.addAuthorization(this.clientService.getBatchUser().getAuthorization());
			}
			final UUID praticaId = this.internalExecute(parameters);
			if(praticaId != null) {
				try {
					this.logService.logScheduler(praticaId, this.getId());
				}catch(final Exception e) {
					this.logger.error("Errore nel log dello scheduler", e);
				}
			}
		}finally {
			LogUtil.removeKeys();
			SchedulerThreadLocal.getThreadLocalUserBatch().remove();
			this.logger.info(LogUtil.stopLog(sw));
		}
		
	}

}
