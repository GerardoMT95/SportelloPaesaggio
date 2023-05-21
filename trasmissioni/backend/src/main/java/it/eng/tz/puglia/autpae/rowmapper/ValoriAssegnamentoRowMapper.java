package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ValoriAssegnamento;
import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class ValoriAssegnamentoRowMapper implements RowMapper<ValoriAssegnamentoDTO> {

	public ValoriAssegnamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ValoriAssegnamentoDTO result = new ValoriAssegnamentoDTO();
		
		result.setIdOrganizzazione(rs.getObject(ValoriAssegnamento.id_organizzazione.columnName()) != null ? rs.getInt(ValoriAssegnamento.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(ValoriAssegnamento.fase.columnName()) != null ? rs.getString(ValoriAssegnamento.fase.columnName()) : null);
		result.setIdComuneTipoProcedimento(rs.getObject(ValoriAssegnamento.id_comune_tipo_procedimento.columnName()) != null ? rs.getString(ValoriAssegnamento.id_comune_tipo_procedimento.columnName()) : null);
		result.setUsernameFunzionario(rs.getObject(ValoriAssegnamento.username_funzionario.columnName()) != null ? rs.getString(ValoriAssegnamento.username_funzionario.columnName()) : null);
		result.setDenominazioneFunzionario(rs.getObject(ValoriAssegnamento.denominazione_funzionario.columnName()) != null ? rs.getString(ValoriAssegnamento.denominazione_funzionario.columnName()) : null);
		return result;
	}
   
}