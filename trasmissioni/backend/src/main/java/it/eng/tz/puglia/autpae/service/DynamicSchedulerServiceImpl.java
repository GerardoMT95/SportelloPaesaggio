package it.eng.tz.puglia.autpae.service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.servizi_esterni.logging.Loggable;

@Service
public class DynamicSchedulerServiceImpl implements SchedulingConfigurer {

	private static final Logger log = LoggerFactory.getLogger(DynamicSchedulerServiceImpl.class);

	@Value("${abilita.scheduling}")
	private String abilitaScheduling;

	private ScheduledTaskRegistrar scheduledTaskRegistrar;

	@Autowired
	private CampionamentoService campionamentoService;
	
	private List<ScheduledFuture<?>> taskSchedulati=new LinkedList<>();

	@Loggable
	public void activateFuture() {
		this.configureTasks(scheduledTaskRegistrar);
	}

	private boolean isAttivoScheduling() {
		return abilitaScheduling.compareToIgnoreCase("S") == 0;
	}

	@Bean(name = "taskScheduler") // ci sono altri bean simili in common starter....
	private TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		scheduler.setPoolSize(2);
		scheduler.initialize();
		return scheduler;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Loggable
	// @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor=Exception.class)
	public synchronized void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		this.initScheduler(taskRegistrar);
		// in caso non deve essere attivato....
		if (!this.isAttivoScheduling()) {
			log.info("Scheduling DISABILITATO !!!!");
			return;
		} else {
			log.info("Scheduling ABILITATO !!!!");
		}
		//stoppo tutti i vecchi processi pendenti....
		List<ScheduledFuture<?>> toRemove = new LinkedList<>();
		taskSchedulati.forEach(task->
		{
		 if(task!=null && (!task.isCancelled() || !task.isDone()))
			if(task.cancel(false)) {
				toRemove.add(task);
			};
		});
		if(toRemove.size()>0) {
			toRemove.stream().forEach(taskSchedulati::remove);
		}
		log.info("task schedulati residui:"+taskSchedulati.size());
		log.info("task schedulati registrati:"+scheduledTaskRegistrar.getScheduledTasks().size());
		CampionamentoDTO campionamento = null;
		try {
			campionamento = campionamentoService.getActiveSampling();
			if (campionamento == null) {
				throw new CustomOperationException();
			}
			final CampionamentoDTO Campionamento = campionamento;
			Date today = new Date();

			Assert.isTrue(Campionamento.getIntervalloCamp() > 0, "Number of days for sampling must be greater than 0.");
			Assert.isTrue(Campionamento.getIntervalloVer() > 0,
					"Number of days for verification must be greater than 0.");
			// Assert.isTrue ( isToday (Campionamento.getDataInizio()), "DataInizio must be
			// today.");
			Assert.isTrue(today.compareTo(Campionamento.getDataInizio()) >= 0,
					"DataInizio must be equal or lower than today.");
			Assert.isTrue(Campionamento.getDataCampionatura().after(Campionamento.getDataInizio()),
					"DataCampionatura must be later than DataInizio.");

			CampionamentoService.stringToShortArray(Campionamento.getNotificaCamp()).stream()
					.forEach(day -> Assert.isTrue(day < Campionamento.getIntervalloCamp(),
							"Number of days for e-mail sampling notification must be equal or less than number of days for sampling."));

			CampionamentoService.stringToShortArray(Campionamento.getNotificaVer()).stream()
					.forEach(day -> Assert.isTrue(day < Campionamento.getIntervalloVer(),
							"Number of days for e-mail verification notification must be equal or less than number of days for verification period."));
			this.scheduleTasks(taskRegistrar, Campionamento); // se c'è un campionamento attivo
		} catch (DataAccessException e1) {
			log.warn("Warning! Probabilmente non c'è nessun Campionamento attivo! [" + e1.toString() + "]");
			// e1.printStackTrace(); // FIXME: eventualmente decommentare se riesco a non
			// farlo scrivere in rosso nella console log
			this.scheduleSamplingForInactiveSamplings(taskRegistrar); // se NON c'è un campionamento attivo
		} catch (CustomOperationException e2) {
			log.error("Errore nella lettura del Campionamento attivo!",e2);
		}
		log.info("end schedule task");
	}

	private void initScheduler(ScheduledTaskRegistrar taskRegistrar) {
		if (scheduledTaskRegistrar == null) {
			scheduledTaskRegistrar = taskRegistrar;
		}
		if (taskRegistrar.getScheduler() == null) {
			taskRegistrar.setScheduler(poolScheduler());
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void scheduleTasks(ScheduledTaskRegistrar taskRegistar, CampionamentoDTO entity) { // se c'è un
																								// campionamento attivo
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		//this.initScheduler(taskRegistar);
		Assert.notNull(taskRegistar, "Argument should not be null.");
		ScheduledFuture<?> campionamento = taskRegistar.getScheduler().schedule(() -> {
			try {
				while (LocalDate.now().isBefore(new java.util.Date(entity.getDataCampionatura().getTime()).toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDate())) {
					try {
						TimeUnit.SECONDS.sleep(10);
					} catch (InterruptedException e) {
						log.warn("", e);
					}
				}
				campionamentoService.effettuaCampionamentoEInviaNotifica(entity);// effettuo il campionamento (più
																				// notifica ecc. ecc.) ///////// 1
				// verifico se devo già attivare il nuovo campionamento
				ConfigurazioneCampionamentoDTO confCamp = campionamentoService.getConfigurazioneCampionamento();
				if (confCamp != null && confCamp.isCampionamentoAttivo()
						&& confCamp.getTipoCampionamentoSuccessivo() == CampionamentoSuccessivo.PREDEFINITO) {
					campionamentoService.createSamplingProcess();
					this.activateFuture();
				}
			} catch (Exception e) {
				log.error("Sampling failed.", e);
			}
		}, t -> {
			Calendar nextExecutionTime = new GregorianCalendar();
			Date lastActualExecutionTime = t.lastActualExecutionTime();
			nextExecutionTime.setTime(entity.getDataCampionatura());
			nextExecutionTime.add(Calendar.SECOND, -1);
			Date ret = lastActualExecutionTime == null ? nextExecutionTime.getTime() : null;
			if (ret == null) {
				log.info(
						"data calcolata per il prossimo campionamentoService.effettuaCampionamentoEInviaNotifica= null");
			} else {
				log.info("data calcolata per il prossimo campionamentoService.effettuaCampionamentoEInviaNotifica="
						+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
								.format(nextExecutionTime.getTime()));
			}
			return ret;
		});
		if(campionamento!=null) {
			this.taskSchedulati.add(campionamento);	
		}
		CampionamentoService.stringToShortArray(entity.getNotificaCamp()).stream()
				.forEach(el ->{ 
				ScheduledFuture<?> preSampling = taskRegistar.getScheduler().schedule(() -> {
					try {
						// this.schedulePresamplingNotification(entity.getId()/*entity.getDataCampionatura()*/);
						// // notifico l'imminente data effettiva di campionamento ///////// 2
						campionamentoService.inviaPresamplingNotification(entity.getId());
					} catch (Exception e) {
						log.error("Email not sent!", e);
						throw new RuntimeException("Email not sent!");
					}
				}, t -> {
					Calendar nextExecutionTime = new GregorianCalendar();
					nextExecutionTime.setTime(entity.getDataCampionatura());
					nextExecutionTime.add(Calendar.DATE, -el);
					log.info("data calcolata per il prossimo campionamentoService.inviaPresamplingNotification="
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
									.format(nextExecutionTime.getTime()));
					if (t.lastActualExecutionTime() != null || new Date().after(nextExecutionTime.getTime())) {
						log.info("nessuna schedulazione per campionamentoService.inviaPresamplingNotification, gg=" + el
								+ " dataCampionatura="
								+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
										.format(entity.getDataCampionatura()));
						return null;
					}
					log.info("schedulazione al "
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
									.format(nextExecutionTime.getTime())
							+ " per campionamentoService.inviaPresamplingNotification, gg=" + el + " dataCampionatura="
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
									.format(entity.getDataCampionatura()));
					return nextExecutionTime.getTime();
				});
				if(preSampling!=null) {
					this.taskSchedulati.add(preSampling);	
				}
				});

		CampionamentoService.stringToShortArray(entity.getNotificaVer()).stream()
				.forEach(el -> 
				{
					ScheduledFuture<?> notificaVerifica = taskRegistar.getScheduler().schedule(() -> {
					try {
						// this.scheduleVerificationNotification(entity.getId()/*entity.getDataCampionatura()*/);
						// // notifico l'imminente scadenza del periodo di verifica ///////// 3
						campionamentoService.inviaNotificaVerifica(entity.getId()/* entity.getDataCampionatura() */); // notifico
																														// l'imminente
																														// scadenza
																														// del
																														// periodo
																														// di
																														// verifica
																														// /////////
																														// 3
					} catch (Exception e) {
						log.error("Email not sent!", e);
						throw new RuntimeException("Email not sent!");
					}
				}, t -> {
					Calendar nextExecutionTime = new GregorianCalendar();
					nextExecutionTime.setTime(entity.getDataCampionatura());
					nextExecutionTime.add(Calendar.DATE, entity.getIntervalloVer());
					nextExecutionTime.add(Calendar.DATE, -el);
					log.info("data calcolata per il prossimo campionamentoService.inviaNotificaVerifica="
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
									.format(nextExecutionTime.getTime()));
					if (t.lastActualExecutionTime() != null || new Date().after(nextExecutionTime.getTime())) {

						log.info("nessuna schedulazione per campionamentoService.inviaNotificaVerifica, gg=" + el
								+ " dataCampionatura="
								+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
										.format(entity.getDataCampionatura()));
						return null;
					}
					log.info("schedulazione al "
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
									.format(nextExecutionTime.getTime())
							+ " per campionamentoService.inviaNotificaVerifica, gg=" + el + " dataCampionatura="
							+ DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
									.format(entity.getDataCampionatura()));
					return nextExecutionTime.getTime();
				});
				if(notificaVerifica!=null) {
					this.taskSchedulati.add(notificaVerifica);	
				}	
				});
	}

	private void scheduleSamplingForInactiveSamplings(ScheduledTaskRegistrar taskRegistrar) { // se NON c'è un
																								// campionamento attivo
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		// Cerco tutti i Campionamenti collegati a Fascicoli che hanno:
		// EsitoVerifica=CHECK_IN_PROGRESS && StatoFascicolo=TRANSMITTED
		List<CampionamentoDTO> samplings = null;
		try {
			samplings = campionamentoService.getSamplingsNotVerified();
		} catch (DataAccessException e) {
			samplings = null;
		}
		if (samplings != null && !samplings.isEmpty()) {
			//this.initScheduler(taskRegistrar);
			Assert.notNull(taskRegistrar, "Argument should not be null.");
			samplings.stream().forEach(sampling -> CampionamentoService.stringToShortArray(sampling.getNotificaVer())
					.stream().forEach(el -> 
					{
						ScheduledFuture<?> inactiveSampling = taskRegistrar.getScheduler().schedule(() -> {
						try {
							// this.scheduleVerificationNotification(sampling.getId()/*samp.getDataCampionat()*/);
							// // notifico l'imminente scadenza del periodo di verifica ///////// 3
							campionamentoService.inviaNotificaVerifica(sampling.getId()/* samp.getDataCampionat() */); // notifico
																														// l'imminente
																														// scadenza
																														// del
																														// periodo
																														// di
																														// verifica
																														// /////////
																														// 3
						} catch (Exception e) {
							log.error("Email not sent!", e);
							throw new RuntimeException("Email not sent!");
						}
					}, t -> {
						Calendar nextExecutionTime = new GregorianCalendar();
						nextExecutionTime.setTime(sampling.getDataCampionatura());
						nextExecutionTime.add(Calendar.DATE, sampling.getIntervalloVer());
						nextExecutionTime.add(Calendar.DATE, -el);

						if (t.lastActualExecutionTime() != null || new Date().after(nextExecutionTime.getTime())) {
							return null;
						}
						return nextExecutionTime.getTime();
					});
					if(inactiveSampling!=null) {
						this.taskSchedulati.add(inactiveSampling);	
					}	
					}));
			
		}	
	}

	
}