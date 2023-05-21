package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.scheduler.executor.CleanReportExecutor;
import it.eng.tz.puglia.batch.scheduler.executor.IBatchSchedulerExecutor;
import it.eng.tz.puglia.batch.scheduler.runnable.GenericBatchSchedulerRunnable;

/**
 * Scheduler per pulire i report scaduti
 * @author Antonio La Gatta
 * @date 2 mag 2022
 */
@Component(CleanReportScheduler.ID_SPRING)
public class CleanReportScheduler extends GenericBatchSchedulerRunnable{

	public static final String ID_SPRING = "CLEAN_REPORT_SCHEDULER";
	
	@Value("${cron.expression}")
	private String cronExpression;

	public CleanReportScheduler(final @Autowired @Qualifier(CleanReportExecutor.ID_SPRING) IBatchSchedulerExecutor executor) {
		super(ID_SPRING, executor);
	}
	/**
	 * @author Antonio La Gatta
	 * @see it.eng.tz.puglia.batch.scheduler.runnable.GenericBatchSchedulerRunnable#getCron()
	 */
	@Override
	public String getCron() {
		return cronExpression;
	}

	
}
