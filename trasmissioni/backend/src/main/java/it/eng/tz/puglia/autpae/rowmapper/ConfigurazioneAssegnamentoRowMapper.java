package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneAssegnamento;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAssegnamento;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class ConfigurazioneAssegnamentoRowMapper implements RowMapper<ConfigurazioneAssegnamentoDTO> {

	public ConfigurazioneAssegnamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ConfigurazioneAssegnamentoDTO result = new ConfigurazioneAssegnamentoDTO();
		
		result.setIdOrganizzazione(rs.getObject(ConfigurazioneAssegnamento.id_organizzazione.columnName()) != null ? rs.getInt(ConfigurazioneAssegnamento.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(ConfigurazioneAssegnamento.fase.columnName()) != null ? rs.getString(ConfigurazioneAssegnamento.fase.columnName()) : null);
		result.setCriterioAssegnamento(rs.getObject(ConfigurazioneAssegnamento.criterio_assegnamento.columnName()) != null ? TipoAssegnamento.valueOf(rs.getString(ConfigurazioneAssegnamento.criterio_assegnamento.columnName())) : null);
		return result;
	}
   
}