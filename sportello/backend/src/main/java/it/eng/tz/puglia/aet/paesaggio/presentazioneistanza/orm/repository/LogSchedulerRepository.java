package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class LogSchedulerRepository extends GenericDao{
	
	
	private static final String SQL_INSERT = new StringBuilder("insert into presentazione_istanza.log_scheduler")
			                                           .append("(pratica_id")
			                                           .append(",executor")
			                                           .append(") VALUES")
			                                           .append("(:pratica_id")
			                                           .append(",:executor")
			                                           .append(")")
			                                           .toString()
			                                           ;
	/**
	 * Log scheduler
	 * @author Antonio La Gatta
	 * @date 4 feb 2022
	 * @param pianoId
	 * @param executorId
	 */
	public void logScheduler(final UUID praticaId, final String executorId) {
		final MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("pratica_id", praticaId);
		parameters.addValue("executor", executorId);
		this.namedJdbcTemplateWrite.update(SQL_INSERT, parameters);
	}

}
