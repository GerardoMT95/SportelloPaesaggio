package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor.ProtocollazioneExecutor;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

@Component(ProtocollazioneScheduler.ID)
public class ProtocollazioneScheduler extends BaseSportelloScheduler{

	public static final String ID = "ProtocollazioneScheduler";

	public ProtocollazioneScheduler(
			@Autowired @Qualifier(ProtocollazioneExecutor.ID) final IBatchQueueExecutor executor) {
		super(ID, executor);
	}

}
