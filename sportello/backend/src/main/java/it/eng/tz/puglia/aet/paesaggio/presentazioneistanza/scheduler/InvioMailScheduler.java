package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor.InvioMailExecutor;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

@Component(InvioMailScheduler.ID)
public class InvioMailScheduler extends BaseSportelloScheduler{

	public static final String ID = "InvioMailScheduler";

	public InvioMailScheduler(
			@Autowired @Qualifier(InvioMailExecutor.ID) final IBatchQueueExecutor executor) {
		super(ID, executor);
	}

}