package it.eng.tz.puglia.autpae.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.repository.base.AllegatoFascicoloBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.custom.AllegatoDiogeneInfoRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for AllegatoFascicolo
 */
@Repository
public class AllegatoFascicoloFullRepository extends AllegatoFascicoloBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoFascicoloFullRepository.class);
	
	
	public int cambiaTipo(long idFascicolo, long idAllegato, TipoAllegato tipoPrecedente, TipoAllegato nuovoTipo) {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "update "
												+ AllegatoFascicolo.getTableName()
												+ " set "
												+ AllegatoFascicolo.type.columnName()
												+ " = :nuovoTipo"
												+ " where "
												+  AllegatoFascicolo.id_allegato.columnName()
												+ " = :"
												+  AllegatoFascicolo.id_allegato.columnName()
												+ " and "
												+  AllegatoFascicolo.id_fascicolo.columnName()
												+ " = :"
												+  AllegatoFascicolo.id_fascicolo.columnName()
												+ " and "
												+ AllegatoFascicolo.type.columnName()
												+ " = :vecchioTipo"
												);
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AllegatoFascicolo.id_allegato.columnName(), idAllegato)
				.addValue(AllegatoFascicolo.id_fascicolo.columnName(), idFascicolo)
				.addValue("nuovoTipo", nuovoTipo.name())
				.addValue("vecchioTipo", tipoPrecedente.name());

		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}
	
}