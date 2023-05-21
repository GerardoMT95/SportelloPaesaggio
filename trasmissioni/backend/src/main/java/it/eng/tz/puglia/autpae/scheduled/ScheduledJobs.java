package it.eng.tz.puglia.autpae.scheduled;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.eng.tz.puglia.autpae.service.DynamicSchedulerServiceImpl;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.batch.scheduler.runnable.GenericBatchSchedulerRunnable;

@Configuration
public class ScheduledJobs {

	@Value("${abilita.scheduling}")
	private String abilitaScheduling;

	private static final Logger log = LoggerFactory.getLogger(ScheduledJobs.class);
	
	
	@Autowired private FascicoloService fascicoloService;
	@Autowired private DynamicSchedulerServiceImpl scheduler;
	
	@Bean("operazioniNotturne")
	public GenericBatchSchedulerRunnable getJobToRun() {

		return new BatchCroned("operazioniNotturne", 
				this::run , 
				"0 40 1,23 * * *"); //ogni 01.40 e 23.20 di ogni giorno....
		// secondo minuto ora ....
	}

	void run(){
		if (!this.isAttivoScheduling()) {
			log.info("Started operazioni notturne .. disabilitate...");
			return;
		}
		log.info("Started operazioni notturne "+new Date());
		try {
			fascicoloService.bloccaModificheScadute();
			scheduler.activateFuture();//riattiva eventuali job riguardando i dati del DB
		} catch (Exception e) {
			log.error("Errore nelle operazioni notturne:",e);
		}
	}
	
	
	private boolean isAttivoScheduling() {
		return abilitaScheduling.compareToIgnoreCase("S") == 0;
	}
	
}
