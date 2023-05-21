package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.scheduler.executor.ConferenzaDeiServiziDocumentiExecutor;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.BaseSportelloScheduler;
import it.eng.tz.puglia.batch.queue.executor.IBatchQueueExecutor;

/**
 * scheduler per appendere un nuovo documento alla conferenza
 * @author acolaianni
 *
 */
@Component(ConferenzaDeiServiziDocumentiScheduler.SPRING_ID)
public class ConferenzaDeiServiziDocumentiScheduler extends BaseSportelloScheduler {
	
	/**
	 * ID SPRING
	 */
	public static final String SPRING_ID = "ConferenzaDeiServiziDocumentiScheduler";
	
	public ConferenzaDeiServiziDocumentiScheduler(@Autowired @Qualifier(ConferenzaDeiServiziDocumentiExecutor.SPRING_ID) final IBatchQueueExecutor executor) {
		super(SPRING_ID, executor);
	}

}
