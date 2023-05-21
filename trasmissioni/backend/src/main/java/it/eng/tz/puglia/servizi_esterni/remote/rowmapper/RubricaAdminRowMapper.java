package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaAdminSearchDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente_Attribute;

/**
 * Row Mapper for table autpae
 */
public class RubricaAdminRowMapper implements RowMapper<RubricaAdminSearchDTO> {

	public RubricaAdminSearchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RubricaAdminSearchDTO result = new RubricaAdminSearchDTO();
		
		result.setId	  (rs.getInt   (Common_Ente_Attribute.ente_id	 .columnName()));
		result.setNome	  (rs.getObject(Common_Ente		     .descrizione.columnName()) != null ? rs.getString(Common_Ente.			 descrizione.columnName()) : null);
		result.setTipoEnte(rs.getObject(Common_Ente		     .tipo	     .columnName()) != null ? rs.getString(Common_Ente.			 tipo		.columnName()) : null);
		result.setEmail   (rs.getObject(Common_Ente_Attribute.mail	     .columnName()) != null ? rs.getString(Common_Ente_Attribute.mail		.columnName()) : null);
		result.setPec	  (rs.getObject(Common_Ente_Attribute.pec		 .columnName()) != null ? rs.getString(Common_Ente_Attribute.pec		.columnName()) : null);
		
		return result;
	}
   
}
