package it.eng.tz.puglia.autpae.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.scheduler.executor.ProtocollazioneFascicoloExecutor;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

@Component(ProtocollazioneFascicoloScheduler.ID)
public class ProtocollazioneFascicoloScheduler extends AutPaeScheduler {

	public final static String ID = "ProtocollazioneFascicoloScheduler";

	public ProtocollazioneFascicoloScheduler(@Autowired @Qualifier(ProtocollazioneFascicoloExecutor.ID_SPRING) final IBatchQueueExecutor executor) {
		super(ID, executor);
	}
}
