package it.eng.tz.puglia.autpae.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.scheduler.executor.InvioMailExecutor;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

@Component(InvioMailScheduler.ID)
public class InvioMailScheduler extends AutPaeScheduler {

	public final static String ID = "InvioMailScheduler";

	public InvioMailScheduler(@Autowired @Qualifier(InvioMailExecutor.ID_SPRING) final IBatchQueueExecutor executor) {
		super(ID, executor);
	}
}