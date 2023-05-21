package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LogSchedulerRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.LogSchedulerService;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Service per log scheduler
 * 
 * @author Antonio La Gatta
 * @date 4 feb 2022
 */
@Service
public class LogSchedulerServiceImpl implements LogSchedulerService {

	private final static Logger LOGGER = LoggerFactory.getLogger(LogSchedulerServiceImpl.class);

	@Autowired
	private LogSchedulerRepository repository;

	
	/**
	 * 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false,transactionManager = "txWrite")
	public void logScheduler(final UUID praticaId, final String executorId) throws Exception {
		final StopWatch sw = LogUtil.startLog("logScheduler ", praticaId, " ", executorId);
		LOGGER.info("Start logScheduler {} {}", praticaId, executorId);
		try {
			this.repository.logScheduler(praticaId, executorId);
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}

	}

}
