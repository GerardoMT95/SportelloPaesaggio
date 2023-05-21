package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor.ProvvedimentoFinaleExecutor;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

@Component(ProvvedimentoFinaleScheduler.ID)
public class ProvvedimentoFinaleScheduler extends BaseSportelloScheduler{

	public static final String ID = "ProvvedimentoFinaleScheduler";

	public ProvvedimentoFinaleScheduler(
			@Autowired @Qualifier(ProvvedimentoFinaleExecutor.ID) final IBatchQueueExecutor executor) {
		super(ID, executor);
	}

}