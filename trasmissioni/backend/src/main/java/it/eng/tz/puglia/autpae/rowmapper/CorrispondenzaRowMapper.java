package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Corrispondenza;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class CorrispondenzaRowMapper implements RowMapper<CorrispondenzaDTO> {

	public CorrispondenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CorrispondenzaDTO result = new CorrispondenzaDTO();
		
		result.setId(rs.getObject(Corrispondenza.id.columnName()) != null ? rs.getLong(Corrispondenza.id.columnName()) : null);
		result.setMessageId(rs.getObject(Corrispondenza.message_id.columnName()) != null ? rs.getString(Corrispondenza.message_id.columnName()) : null);
//		result.setIdEmlCmis(rs.getObject(Corrispondenza.id_eml_cmis.columnName()) != null ? rs.getString(Corrispondenza.id_eml_cmis.columnName()) : null);
		result.setMittenteEmail(rs.getObject(Corrispondenza.mittente_email.columnName()) != null ? rs.getString(Corrispondenza.mittente_email.columnName()) : null);
		result.setMittenteDenominazione(rs.getObject(Corrispondenza.mittente_denominazione.columnName()) != null ? rs.getString(Corrispondenza.mittente_denominazione.columnName()) : null);
		result.setMittenteEnte(rs.getObject(Corrispondenza.mittente_ente.columnName()) != null ? rs.getString(Corrispondenza.mittente_ente.columnName()) : null);
		result.setMittenteUsername(rs.getObject(Corrispondenza.mittente_username.columnName()) != null ? rs.getString(Corrispondenza.mittente_username.columnName()) : null);
		result.setDataInvio(rs.getObject(Corrispondenza.data_invio.columnName()) != null ? Date.from((rs.getObject(Corrispondenza.data_invio.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setTesto(rs.getObject(Corrispondenza.text.columnName()) != null ? rs.getString(Corrispondenza.text.columnName()) : null);
		result.setOggetto(rs.getObject(Corrispondenza.oggetto.columnName()) != null ? rs.getString(Corrispondenza.oggetto.columnName()) : null);
		result.setStato(rs.getBoolean(Corrispondenza.stato.columnName()));
		result.setComunicazione(rs.getBoolean(Corrispondenza.comunicazione.columnName()));
		result.setBozza(rs.getBoolean(Corrispondenza.bozza.columnName()));
		return result;
	}
   
}
