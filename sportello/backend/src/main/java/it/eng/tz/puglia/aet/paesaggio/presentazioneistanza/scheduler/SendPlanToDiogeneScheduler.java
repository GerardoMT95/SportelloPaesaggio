package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor.SendPlanToDiogeneExecutor;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

@Component(SendPlanToDiogeneScheduler.ID)
public class SendPlanToDiogeneScheduler extends BaseSportelloScheduler{

	public static final String ID = "SendPlanToDiogeneScheduler";

	public SendPlanToDiogeneScheduler(
			@Autowired @Qualifier(SendPlanToDiogeneExecutor.ID) final IBatchQueueExecutor executor) {
		super(ID, executor);
	}

}
