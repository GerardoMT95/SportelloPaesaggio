package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;

/**
 * Row Mapper for table autpae
 */
public class RubricaIstituzionaleRowMapper implements RowMapper<RubricaIstituzionaleDTO> {

	public RubricaIstituzionaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RubricaIstituzionaleDTO result = new RubricaIstituzionaleDTO();
		
	//	result.setEnteId		(rs.getInt	 (Common_Ente_Attribute.ente_id		   .columnName()));
	//	result.setApplicazioneId(rs.getInt   (Common_Ente_Attribute.applicazione_id.columnName()));
		result.setNome			(rs.getObject(Common_Ente		   .descrizione	   .columnName()) != null ? rs.getString(Common_Ente.   descrizione.columnName()) : null);

		boolean isPec = rs.getBoolean("is_pec");
		
		if (isPec==true)
			result.setPec (rs.getString("pec_mail"));
		else
			result.setMail(rs.getString("pec_mail"));
		
		return result;
	}
   
}
