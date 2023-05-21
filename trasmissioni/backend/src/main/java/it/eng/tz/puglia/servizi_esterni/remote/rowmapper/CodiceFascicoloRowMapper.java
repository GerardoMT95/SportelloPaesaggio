package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_CodiceFascicolo;
import it.eng.tz.puglia.servizi_esterni.remote.dto.CodiceFascicoloDTO;

/**
 * Row Mapper for table codice_fascicolo
 */
public class CodiceFascicoloRowMapper implements RowMapper<CodiceFascicoloDTO> {

	public CodiceFascicoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CodiceFascicoloDTO result = new CodiceFascicoloDTO();
		
		result.setCodiceEnte(rs.getObject(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName()) != null ? rs.getString(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName()) : null);
		result.setAnno		(rs.getObject(Common_Paesaggio_CodiceFascicolo.anno		  .columnName()) != null ? rs.getInt   (Common_Paesaggio_CodiceFascicolo.anno		.columnName()) : null);
		result.setPrefisso  (rs.getObject(Common_Paesaggio_CodiceFascicolo.prefisso	  .columnName()) != null ? rs.getString(Common_Paesaggio_CodiceFascicolo.prefisso	.columnName()) : null);
		result.setSeriale	(rs.getObject(Common_Paesaggio_CodiceFascicolo.seriale	  .columnName()) != null ? rs.getInt   (Common_Paesaggio_CodiceFascicolo.seriale	.columnName()) : null);
				
		return result;
	}
   
}
