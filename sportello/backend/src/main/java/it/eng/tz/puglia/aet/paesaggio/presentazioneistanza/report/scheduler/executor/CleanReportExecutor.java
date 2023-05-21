package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler.executor;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.batch.scheduler.executor.IBatchSchedulerExecutor;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
/**
 * Executor to clean report
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
@Component(CleanReportExecutor.ID_SPRING)
public class CleanReportExecutor implements IBatchSchedulerExecutor{

	/**
	 * Spring id
	 */
	public static final String ID_SPRING = "CleanReportExecutor";
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(CleanReportExecutor.class);
	
	@Autowired
	private IReportService service;

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
			final List<String> lista = this.service.idTerminati();
			int size = ListUtil.size(lista);
			LOGGER.info("Da controllare {} downloads", size);
			if(size > 0) {
				final int ore = this.service.getMaxOre();
				LOGGER.info("Ore permesse {}", ore);
				for(final Iterator<String> iterator = lista.iterator(); iterator.hasNext(); ) {
					final String idDownload = iterator.next();
					final Timestamp now = DateUtil.currentTimestamp();
					final Timestamp dataCreazione = this.service.dataCreazione(idDownload);
					final Timestamp dataRichiesta = this.service.dataRichiesta(idDownload);
					LOGGER.info("now {}", now);
					LOGGER.info("dataCreazione {} per {}", dataCreazione, idDownload);
					LOGGER.info("dataRichiesta {} per {}", dataRichiesta, idDownload);
					final long diff = (now.getTime() - (dataCreazione != null ? dataCreazione: dataRichiesta).getTime()) / (1000 * 60 * 60);
					LOGGER.info("differenza {}", diff);
					if(diff > ore) {
						this.service.eliminaReport(idDownload);
					}
					LOGGER.info("Restano {} downlaods", --size);
				}
			}
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
		
	}
}
