package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Email;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioEmailDTO;

/**
 * Row Mapper for table common.paesaggio_email
 */
public class PaesaggioEmailRowMapper implements RowMapper<PaesaggioEmailDTO> {

	public PaesaggioEmailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		PaesaggioEmailDTO result = new PaesaggioEmailDTO();
		
		result.setId			  (rs.getObject(Common_Paesaggio_Email.id				.columnName()) != null ? rs.getLong   (Common_Paesaggio_Email.id			   .columnName()) : null);
		result.setEmail			  (rs.getObject(Common_Paesaggio_Email.email		  	.columnName()) != null ? rs.getString (Common_Paesaggio_Email.email			   .columnName()) : null);
		result.setDenominazione	  (rs.getObject(Common_Paesaggio_Email.denominazione	.columnName()) != null ? rs.getString (Common_Paesaggio_Email.denominazione	   .columnName()) : null);
		result.setPec			  (rs.getObject(Common_Paesaggio_Email.pec	  		  	.columnName()) != null ? rs.getBoolean(Common_Paesaggio_Email.pec			   .columnName()) : null);
		result.setOrganizzazioneId(rs.getObject(Common_Paesaggio_Email.organizzazione_id.columnName()) != null ? rs.getInt    (Common_Paesaggio_Email.organizzazione_id.columnName()) : null);
		result.setEnteId		  (rs.getObject(Common_Paesaggio_Email.ente_id		  	.columnName()) != null ? rs.getInt    (Common_Paesaggio_Email.ente_id		   .columnName()) : null);
				
		return result;
	}
   
}
