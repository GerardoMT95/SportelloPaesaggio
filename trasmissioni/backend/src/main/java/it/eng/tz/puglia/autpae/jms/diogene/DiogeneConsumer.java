/**
 * 
 */
package it.eng.tz.puglia.autpae.jms.diogene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.jms.diogene.service.IDiogeneClientService;
import it.eng.tz.puglia.jms.executor.IJmsExecutor;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * executer avviato da listener su coda jms
 * @author Adriano Colaianni
 * @date 18 ott 2021
 */
@Component
public class DiogeneConsumer implements IJmsExecutor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DiogeneConsumer.class);
	
	@Autowired
	IDiogeneClientService diogeneSvc;

	/**
	 * letture notifiche diogene sulla mia coda segnata in jms.consumer.queue.name
	 */
	@Override
	public void execute(String message) throws Exception {
		final StopWatch sw = LogUtil.startLog("execute");
		LOGGER.info("Start execute");
		try {
			diogeneSvc.elaboraNotifica(message);
			LOGGER.info("execute message {}",message);
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

}
