package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Organizzazione_Rubrica;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;

/**
 * Row Mapper for table autpae
 */
public class RubricaEnteRowMapper implements RowMapper<RubricaEnteDTO> {

	public RubricaEnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RubricaEnteDTO result = new RubricaEnteDTO();
		
		result.setId					   (rs.getLong  (Common_Paesaggio_Organizzazione_Rubrica.id.columnName()));
	//	result.setPaesaggioOrganizzazioneId(rs.getInt   (Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName()));
	//	result.setCodiceApplicazione	   (rs.getString(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.columnName()));
		result.setNome(rs.getObject(Common_Paesaggio_Organizzazione_Rubrica.nome.columnName()) != null ? rs.getString(Common_Paesaggio_Organizzazione_Rubrica.nome.columnName()) : null);
		
		boolean isPec = rs.getBoolean("is_pec");
		
		if (isPec==true)
			result.setPec (rs.getString("pec_mail"));
		else
			result.setMail(rs.getString("pec_mail"));
		
		return result;
	}
   
}
