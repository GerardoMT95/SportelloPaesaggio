package it.eng.tz.puglia.servizi_esterni.scheduler;

//import java.util.Date;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
//import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;

// to use after the spring application has been configured (@Configuration) with @EnableScheduling and @EnableAsync
/**
 * non piu' utilizzata... see {@link it.eng.tz.puglia.autpae.scheduled.ScheduledJobs}}
 * @author acolaianni
 *
 */
@Deprecated
public class CustomScheduling {
	
//	private static final Logger log = LoggerFactory.getLogger(CustomScheduling.class);
//	
//	private TaskScheduler scheduler;
//	
//	@Autowired FascicoloService fascicoloService;
//
//	//@Async
//	public ScheduledFuture<?> executeTask(Date dataEsecuzione, Object parameter) {
//		log.info("Il Task '"+new Object(){}.getClass().getEnclosingMethod().getName()+"'è stato programmato per essere eseguito in data: "+dataEsecuzione);
//
//		ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
//		scheduler = new ConcurrentTaskScheduler(localExecutor);
//
//		CustomRunnable eseguiOperazioni = new CustomRunnable(parameter) {
//			@Override
//			public void run() {
//				log.info("Avvio del task programmato: "+new Object(){}.getClass().getEnclosingMethod().getName());
//
//				// operazioni_da_eseguire(parameter);
//			}
//		};
//		
//		return scheduler.schedule(eseguiOperazioni, dataEsecuzione);  // any timestamp in milliseconds to text
//	}
//	
//
//	
//	//@Async
//	public ScheduledFuture<?> bloccaModifica(Date dataEsecuzione, Object parameter) {
//		log.info("Il Task '"+new Object(){}.getClass().getEnclosingMethod().getName()+"'è stato programmato per essere eseguito in data: "+dataEsecuzione);
//		
//		// per questo task NON ho bisogno di prevedere l'annullamento perchè l'effettiva applicazione dei cambiamenti è già gestita nella WHERE della query
//		
//		ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
//		scheduler = new ConcurrentTaskScheduler(localExecutor);
//
//		CustomRunnable eseguiOperazioni = new CustomRunnable(parameter) {
//			@Override
//			public void run() {
//				log.info("Avvio del task programmato: "+new Object(){}.getClass().getEnclosingMethod().getName());
//
//				try {
//					fascicoloService.bloccaModifica((Long) parameter);
//				} catch (Exception e) {
//					log.error("Errore durante il blocco della modifica per il fascicolo con id="+(Long)parameter);
//					e.printStackTrace();
//				}
//			}
//		};
//		return scheduler.schedule(eseguiOperazioni, dataEsecuzione);  // any timestamp in milliseconds to text
//	}

	//  TO PROGRAM THE TASK:  	ScheduledFuture<?> scheduledFuture = executeTask(dataEsecuzione); 		// call it somewhere in your code
	
	//  TO CANCEL  THE TASK:   					   scheduledFuture.cancel(true);						// call it somewhere in your code

}