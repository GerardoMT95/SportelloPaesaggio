package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.util;

/**
 * classe per gestione thread locale
 * @author Antonio La Gatta
 * @date 4 feb 2022
 */
public abstract class SchedulerThreadLocal {

	private static final ThreadLocal<String> THREAD_LOCAL_USER_BATCH = new ThreadLocal<>();

	/**
	 * @return the threadLocalUserBatch
	 */
	public static ThreadLocal<String> getThreadLocalUserBatch() {
		return THREAD_LOCAL_USER_BATCH;
	}
	
}
