package it.eng.tz.puglia.autpae.rowmapper.custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dto.TabelleAssegnamentoFascicoliDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class TabelleAssegnamentoFascicoliRowMapper implements RowMapper<TabelleAssegnamentoFascicoliDTO> {

	public TabelleAssegnamentoFascicoliDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TabelleAssegnamentoFascicoliDTO result = new TabelleAssegnamentoFascicoliDTO();
		
		result.setId(rs.getObject(Fascicolo.id.columnName()) != null ? rs.getLong(Fascicolo.id.columnName()) : null);
		result.setStato(rs.getObject(Fascicolo.stato.columnName()) != null ? StatoFascicolo.valueOf(rs.getString(Fascicolo.stato.columnName())) : null);
		result.setCodice(rs.getObject(Fascicolo.codice.columnName()) != null ? rs.getString(Fascicolo.codice.columnName()) : null);
		result.setTipoProcedimento(rs.getObject(Fascicolo.tipo_procedimento.columnName()) != null ? TipoProcedimento.valueOf(rs.getString(Fascicolo.tipo_procedimento.columnName())) : null);
		result.setOggettoIntervento(rs.getObject(Fascicolo.oggetto_intervento.columnName()) != null ? rs.getString(Fascicolo.oggetto_intervento.columnName()) : null);
		result.setUsernameFunzionario(rs.getObject(AssegnamentoFascicolo.username_funzionario.columnName()) != null ? rs.getString(AssegnamentoFascicolo.username_funzionario.columnName()) : null);
		result.setDenominazioneFunzionario(rs.getObject(AssegnamentoFascicolo.denominazione_funzionario.columnName()) != null ? rs.getString(AssegnamentoFascicolo.denominazione_funzionario.columnName()) : null);
		result.setRiassegnazioni(rs.getObject(AssegnamentoFascicolo.num_assegnazioni.columnName()) != null ? rs.getShort(AssegnamentoFascicolo.num_assegnazioni.columnName()) : 0);
		result.setUltimaOperazione(rs.getObject(AssegnamentoFascicolo.data_operazione.columnName()) != null ? Date.from((rs.getObject(AssegnamentoFascicolo.data_operazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
	//	result.setUltimaAssegnazione(rs.getObject(AssegnamentoFascicolo.data_assegnazione.columnName()) != null ? Date.from((rs.getObject(AssegnamentoFascicolo.data_assegnazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		
		return result;
	}
   
}