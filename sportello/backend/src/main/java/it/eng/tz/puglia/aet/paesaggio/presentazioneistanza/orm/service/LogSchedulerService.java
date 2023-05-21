package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.UUID;

/**
 * Service per log scheduler
 * @author Antonio La Gatta
 * @date 4 feb 2022
 */
public interface LogSchedulerService {

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @param executorId
	 * @throws Exception
	 */
	void logScheduler(UUID praticaId, String executorId) throws Exception;
	
}
