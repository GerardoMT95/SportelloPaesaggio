package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.StoricoAssegnamento;
import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoOperazione;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class StoricoAssegnamentoRowMapper implements RowMapper<StoricoAssegnamentoDTO> {

	public StoricoAssegnamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoAssegnamentoDTO result = new StoricoAssegnamentoDTO();
		
		result.setId(rs.getObject(StoricoAssegnamento.id.columnName()) != null ? rs.getLong(StoricoAssegnamento.id.columnName()) : null);
		result.setIdFascicolo(rs.getObject(StoricoAssegnamento.id_fascicolo.columnName()) != null ? rs.getLong(StoricoAssegnamento.id_fascicolo.columnName()) : null);
		result.setIdOrganizzazione(rs.getObject(StoricoAssegnamento.id_organizzazione.columnName()) != null ? rs.getInt(StoricoAssegnamento.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(StoricoAssegnamento.fase.columnName()) != null ? rs.getString(StoricoAssegnamento.fase.columnName()) : null);
		result.setUsernameFunzionario(rs.getObject(StoricoAssegnamento.username_funzionario.columnName()) != null ? rs.getString(StoricoAssegnamento.username_funzionario.columnName()) : null);
		result.setDenominazioneFunzionario(rs.getObject(StoricoAssegnamento.denominazione_funzionario.columnName()) != null ? rs.getString(StoricoAssegnamento.denominazione_funzionario.columnName()) : null);
		result.setTipoOperazione(rs.getObject(StoricoAssegnamento.tipo_operazione.columnName()) != null ? TipoOperazione.valueOf(rs.getString(StoricoAssegnamento.tipo_operazione.columnName())) : null);
		result.setDataOperazione(rs.getObject(StoricoAssegnamento.data_operazione.columnName()) != null ? Date.from((rs.getObject(StoricoAssegnamento.data_operazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		return result;
	}
   
}