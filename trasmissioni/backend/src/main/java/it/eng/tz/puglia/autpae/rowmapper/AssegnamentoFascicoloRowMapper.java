package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.autpae.entity.AssegnamentoFascicoloDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class AssegnamentoFascicoloRowMapper implements RowMapper<AssegnamentoFascicoloDTO> {

	public AssegnamentoFascicoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AssegnamentoFascicoloDTO result = new AssegnamentoFascicoloDTO();
		
		result.setIdFascicolo(rs.getObject(AssegnamentoFascicolo.id_fascicolo.columnName()) != null ? rs.getLong(AssegnamentoFascicolo.id_fascicolo.columnName()) : null);
		result.setIdOrganizzazione(rs.getObject(AssegnamentoFascicolo.id_organizzazione.columnName()) != null ? rs.getInt(AssegnamentoFascicolo.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(AssegnamentoFascicolo.fase.columnName()) != null ? rs.getString(AssegnamentoFascicolo.fase.columnName()) : null);
		result.setUsernameFunzionario(rs.getObject(AssegnamentoFascicolo.username_funzionario.columnName()) != null ? rs.getString(AssegnamentoFascicolo.username_funzionario.columnName()) : null);
		result.setDenominazioneFunzionario(rs.getObject(AssegnamentoFascicolo.denominazione_funzionario.columnName()) != null ? rs.getString(AssegnamentoFascicolo.denominazione_funzionario.columnName()) : null);
		result.setNumAssegnazioni(rs.getObject(AssegnamentoFascicolo.num_assegnazioni.columnName()) != null ? rs.getShort(AssegnamentoFascicolo.num_assegnazioni.columnName()) : null);
		result.setDataOperazione(rs.getObject(AssegnamentoFascicolo.data_operazione.columnName()) != null ? Date.from((rs.getObject(AssegnamentoFascicolo.data_operazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		return result;
	}
   
}