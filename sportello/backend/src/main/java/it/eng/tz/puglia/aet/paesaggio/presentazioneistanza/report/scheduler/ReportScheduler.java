package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler.executor.ReportExecutor;
import it.eng.tz.puglia.batch.scheduler.executor.IBatchSchedulerExecutor;
import it.eng.tz.puglia.batch.scheduler.runnable.GenericBatchSchedulerRunnable;

/**
 * Scheduler per eseguire reportistica 
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
@Component(ReportScheduler.ID_SPRING)
public class ReportScheduler extends GenericBatchSchedulerRunnable{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String ID_SPRING = "REPORT_SCHEDULER";
	
	private static final String EVERY_10_SECS="*/10 * * * * *";
	
	@Value("${cron.report.schedule:"+EVERY_10_SECS+"}")
	private String scheduleReport;
	
	@PostConstruct
	public void init() {
		logger.info("Init cron.report.schedule {}",scheduleReport);
	}

	public ReportScheduler(final @Autowired @Qualifier(ReportExecutor.ID_SPRING) IBatchSchedulerExecutor executor) {
		super(ID_SPRING, executor);
	}
	/**
	 * @author Antonio La Gatta
	 * @see it.eng.tz.puglia.batch.scheduler.runnable.GenericBatchSchedulerRunnable#getCron()
	 */
	@Override
	public String getCron() {
		return scheduleReport;
	}

}
