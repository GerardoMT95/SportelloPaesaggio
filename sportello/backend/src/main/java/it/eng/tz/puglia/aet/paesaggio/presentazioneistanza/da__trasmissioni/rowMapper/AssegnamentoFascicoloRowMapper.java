package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.AssegnamentoFascicolo;

/**
 * Row Mapper for table ...
 */
public class AssegnamentoFascicoloRowMapper implements RowMapper<AssegnamentoFascicoloOldDTO> {

	public AssegnamentoFascicoloOldDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AssegnamentoFascicoloOldDTO result = new AssegnamentoFascicoloOldDTO();
		
		result.setIdFascicolo(rs.getObject(AssegnamentoFascicolo.id_fascicolo.columnName()) != null ? UUID.fromString(rs.getString(AssegnamentoFascicolo.id_fascicolo.columnName())) : null);
		result.setIdOrganizzazione(rs.getObject(AssegnamentoFascicolo.id_organizzazione.columnName()) != null ? rs.getInt(AssegnamentoFascicolo.id_organizzazione.columnName()) : null);
		result.setFase(rs.getObject(AssegnamentoFascicolo.fase.columnName()) != null ? rs.getString(AssegnamentoFascicolo.fase.columnName()) : null);
		result.setRup(rs.getObject(AssegnamentoFascicolo.rup.columnName()) != null ? rs.getBoolean(AssegnamentoFascicolo.rup.columnName()) : null);
		result.setUsernameUtente(rs.getObject(AssegnamentoFascicolo.username_utente.columnName()) != null ? rs.getString(AssegnamentoFascicolo.username_utente.columnName()) : null);
		result.setDenominazioneUtente(rs.getObject(AssegnamentoFascicolo.denominazione_utente.columnName()) != null ? rs.getString(AssegnamentoFascicolo.denominazione_utente.columnName()) : null);
		result.setNumAssegnazioni(rs.getObject(AssegnamentoFascicolo.num_assegnazioni.columnName()) != null ? rs.getShort(AssegnamentoFascicolo.num_assegnazioni.columnName()) : null);
		result.setDataOperazione(rs.getObject(AssegnamentoFascicolo.data_operazione.columnName()) != null ? Date.from((rs.getObject(AssegnamentoFascicolo.data_operazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		return result;
	}
   
}