package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class DestinatarioRowMapper implements RowMapper<DestinatarioDTO> {

	public DestinatarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		DestinatarioDTO result = new DestinatarioDTO();
		
		result.setId(rs.getObject(Destinatario.id.columnName()) != null ? rs.getLong(Destinatario.id.columnName()) : null);
		result.setTipo(rs.getObject(Destinatario.type.columnName()) != null ? TipoDestinatario.valueOf(rs.getString(Destinatario.type.columnName())) : null);
		result.setEmail(rs.getObject(Destinatario.email.columnName()) != null ? rs.getString(Destinatario.email.columnName()) : null);
		result.setStato(rs.getObject(Destinatario.stato.columnName()) != null ? StatoCorrispondenza.valueOf(rs.getString(Destinatario.stato.columnName())) : null);
		result.setIdCorrispondenza(rs.getObject(Destinatario.id_corrispondenza.columnName()) != null ? rs.getLong(Destinatario.id_corrispondenza.columnName()) : null);
		result.setPec(rs.getObject(Destinatario.pec.columnName()) != null ? rs.getBoolean(Destinatario.pec.columnName()) : false);
		result.setNome(rs.getObject(Destinatario.denominazione.columnName()) != null ? rs.getString(Destinatario.denominazione.columnName()) : null);
		result.setDataRicezione(rs.getObject(Destinatario.data_ricezione.columnName()) != null ? Date.from((rs.getObject(Destinatario.data_ricezione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		return result;
	}
   
}
