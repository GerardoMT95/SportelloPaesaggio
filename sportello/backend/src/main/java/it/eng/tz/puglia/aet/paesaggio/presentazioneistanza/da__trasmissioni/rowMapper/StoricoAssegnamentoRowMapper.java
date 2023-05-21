package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.StoricoAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoOperazione;

/**
 * Row Mapper for table ...
 */
public class StoricoAssegnamentoRowMapper implements RowMapper<StoricoAssegnamentoOldDTO> {

	public StoricoAssegnamentoOldDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StoricoAssegnamentoOldDTO result = new StoricoAssegnamentoOldDTO();
		
		result.setId(rs.getObject(StoricoAssegnamento.id.columnName()) != null ? rs.getLong(StoricoAssegnamento.id.columnName()) : null);
		result.setIdFascicolo(rs.getObject(StoricoAssegnamento.id_fascicolo.columnName()) != null ? UUID.fromString(rs.getString(StoricoAssegnamento.id_fascicolo.columnName())) : null);
		result.setIdOrganizzazione(rs.getObject(StoricoAssegnamento.id_organizzazione.columnName()) != null ? rs.getInt(StoricoAssegnamento.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(StoricoAssegnamento.fase.columnName()) != null ? rs.getString(StoricoAssegnamento.fase.columnName()) : null);
		result.setRup(rs.getObject(StoricoAssegnamento.rup.columnName()) != null ? rs.getBoolean(StoricoAssegnamento.rup.columnName()) : null);
		result.setUsernameUtente(rs.getObject(StoricoAssegnamento.username_utente.columnName()) != null ? rs.getString(StoricoAssegnamento.username_utente.columnName()) : null);
		result.setDenominazioneUtente(rs.getObject(StoricoAssegnamento.denominazione_utente.columnName()) != null ? rs.getString(StoricoAssegnamento.denominazione_utente.columnName()) : null);
		result.setTipoOperazione(rs.getObject(StoricoAssegnamento.tipo_operazione.columnName()) != null ? TipoOperazione.valueOf(rs.getString(StoricoAssegnamento.tipo_operazione.columnName())) : null);
		result.setDataOperazione(rs.getObject(StoricoAssegnamento.data_operazione.columnName()) != null ? Date.from((rs.getObject(StoricoAssegnamento.data_operazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		return result;
	}
   
}