package it.eng.tz.puglia.autpae.scheduled;

import it.eng.tz.puglia.batch.scheduler.executor.IBatchSchedulerExecutor;
import it.eng.tz.puglia.batch.scheduler.runnable.GenericBatchSchedulerRunnable;
// import it.eng.tz.puglia.batch.scheduler.runnable.IBatchSechedulerRunnable;

/**
 * classe che estende la {@link GenericBatchSchedulerRunnable}
 * per dare la possibilità di modificare la stringa di schedulazione che per default è pari a 0 * * * * *
 * 
 * @author acolaianni
 *
 */
public class BatchCroned extends GenericBatchSchedulerRunnable {

	String cron;
	
	public BatchCroned(String idBatchScheduler, IBatchSchedulerExecutor executor,String cron) {
		super(idBatchScheduler, executor);
		this.cron=cron;
	}

	@Override
	public String getCron() {
		return this.cron;
	}
	
}