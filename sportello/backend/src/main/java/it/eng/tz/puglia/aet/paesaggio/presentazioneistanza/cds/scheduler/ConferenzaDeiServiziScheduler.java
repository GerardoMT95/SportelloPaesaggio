package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler.executor.ConferenzaDeiServiziExecutor;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.BaseSportelloScheduler;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;
/**
 * Scheduler per avviare conferenza dei servizi
 * @author Antonio La Gatta
 * @date 30 nov 2021
 */
@Component(ConferenzaDeiServiziScheduler.SPRING_ID)
public class ConferenzaDeiServiziScheduler extends BaseSportelloScheduler {
	
	/**
	 * ID SPRING
	 */
	public static final String SPRING_ID = "CONFERENZA_DEI_SERVIZI";

	public ConferenzaDeiServiziScheduler(
			@Autowired @Qualifier(ConferenzaDeiServiziExecutor.SPRING_ID) final IBatchQueueExecutor executor) {
		super(SPRING_ID, executor);
	}

}
