package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.InvioMailScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.ProtocollazioneBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Executor per inviare su diogene i documenti
 * 
 * @author Antonio La Gatta
 * @date 3 feb 2022
 */
@Component(ProtocollazioneExecutor.ID)
public class ProtocollazioneExecutor extends SportelloBaseExecutor {

	/**
	 * ID
	 */
	public static final String ID = "ProtocollazioneExecutor";
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ProtocollazioneExecutor.class);
	/**
	 * service
	 */
	@Autowired
	FascicoloService fascicoloSvc;
	@Autowired
	private IQueueService queueService;
	
	/**
	 * 
	 */
	@Override
	public UUID internalExecute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("internalExecute");
		LOGGER.info("Start internalExecute");
		try {
			final ProtocollazioneBean bean = JsonUtil.toBean(parameters, ProtocollazioneBean.class);
			this.fascicoloSvc.protocollaFascicolo(UUID.fromString(bean.getIdPratica()), true);
			this.queueService.insertNewQueue(InvioMailScheduler.ID, bean);
			return UUID.fromString(bean.getIdPratica());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public String getId() {
		return ID;
	}

}
