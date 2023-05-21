package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.Pratica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;

/**
 * Row Mapper for table ...
 */
public class TabelleAssegnamentoFascicoliRowMapper implements RowMapper<TabelleAssegnamentoFascicoliOldDTO> {

	public TabelleAssegnamentoFascicoliOldDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TabelleAssegnamentoFascicoliOldDTO result = new TabelleAssegnamentoFascicoliOldDTO();
		
		result.setId(rs.getObject(Pratica.id.columnName()) != null ? UUID.fromString(rs.getString(Pratica.id.columnName())) : null);
		result.setStato(rs.getObject(Pratica.stato.columnName()) != null ? AttivitaDaEspletare.valueOf(rs.getString(Pratica.stato.columnName())) : null);
		result.setCodice(rs.getObject(Pratica.codice_pratica_appptr.columnName()) != null ? rs.getString(Pratica.codice_pratica_appptr.columnName()) : null);
		result.setTipoProcedimento(rs.getObject(Pratica.tipo_procedimento.columnName()) != null ? rs.getInt(Pratica.tipo_procedimento.columnName()) : null);
		result.setOggettoIntervento(rs.getObject(Pratica.oggetto.columnName()) != null ? rs.getString(Pratica.oggetto.columnName()) : null);
		result.setRup(rs.getObject(AssegnamentoFascicolo.rup.columnName()) != null ? rs.getBoolean(AssegnamentoFascicolo.rup.columnName()) : false);
		result.setUsernameUtente(rs.getObject(AssegnamentoFascicolo.username_utente.columnName()) != null ? rs.getString(AssegnamentoFascicolo.username_utente.columnName()) : null);
		result.setDenominazioneUtente(rs.getObject(AssegnamentoFascicolo.denominazione_utente.columnName()) != null ? rs.getString(AssegnamentoFascicolo.denominazione_utente.columnName()) : null);
		result.setRiassegnazioni(rs.getObject(AssegnamentoFascicolo.num_assegnazioni.columnName()) != null ? rs.getShort(AssegnamentoFascicolo.num_assegnazioni.columnName()) : 0);
		result.setUltimaOperazione(rs.getObject(AssegnamentoFascicolo.data_operazione.columnName()) != null ? Date.from((rs.getObject(AssegnamentoFascicolo.data_operazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setUsernameRup(rs.getObject("username_rup") != null ? rs.getString("username_rup") : null);
		result.setDenominazioneRup(rs.getObject("denominazione_rup") != null ? rs.getString("denominazione_rup") : null);
	//	result.setUltimaAssegnazione(rs.getObject(AssegnamentoFascicolo.data_assegnazione.columnName()) != null ? Date.from((rs.getObject(AssegnamentoFascicolo.data_assegnazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		
		return result;
	}
   
}