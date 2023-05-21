package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Executor per inviare su diogene i documenti
 * 
 * @author Antonio La Gatta
 * @date 3 feb 2022
 */
@Component(SendPlanToDiogeneExecutor.ID)
public class SendPlanToDiogeneExecutor extends SportelloBaseExecutor {

	/**
	 * ID
	 */
	public static final String ID = "SendPlanToDiogeneExecutor";
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(SendPlanToDiogeneExecutor.class);
	/**
	 * service
	 */
	@Autowired
	FascicoloService fascicoloSvc;
	
	/**
	 * 
	 */
	@Override
	public UUID internalExecute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("internalExecute");
		LOGGER.info("Start internalExecute");
		try {
			final FascicoloStatoBean fascicoloStatoBean =JsonUtil.toBean(parameters, FascicoloStatoBean.class); 
			this.fascicoloSvc.allineaDiogene(fascicoloStatoBean);
			return fascicoloStatoBean.getPratica().getId();
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public String getId() {
		return ID;
	}

}
