package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.ValoriAssegnamento;

/**
 * Row Mapper for table ...
 */
public class ValoriAssegnamentoRowMapper implements RowMapper<ValoriAssegnamentoOldDTO> {

	public ValoriAssegnamentoOldDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ValoriAssegnamentoOldDTO result = new ValoriAssegnamentoOldDTO();
		
		result.setIdOrganizzazione(rs.getObject(ValoriAssegnamento.id_organizzazione.columnName()) != null ? rs.getInt(ValoriAssegnamento.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(ValoriAssegnamento.fase.columnName()) != null ? rs.getString(ValoriAssegnamento.fase.columnName()) : null);
		result.setIdComuneTipoProcedimento(rs.getObject(ValoriAssegnamento.id_comune_tipo_procedimento.columnName()) != null ? rs.getInt(ValoriAssegnamento.id_comune_tipo_procedimento.columnName()) : null);
		result.setRup(rs.getObject(ValoriAssegnamento.rup.columnName()) != null ? rs.getBoolean(ValoriAssegnamento.rup.columnName()) : null);
		result.setUsernameUtente(rs.getObject(ValoriAssegnamento.username_utente.columnName()) != null ? rs.getString(ValoriAssegnamento.username_utente.columnName()) : null);
		result.setDenominazioneUtente(rs.getObject(ValoriAssegnamento.denominazione_utente.columnName()) != null ? rs.getString(ValoriAssegnamento.denominazione_utente.columnName()) : null);
		return result;
	}
   
}