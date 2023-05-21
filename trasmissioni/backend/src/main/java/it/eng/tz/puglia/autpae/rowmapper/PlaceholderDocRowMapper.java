package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.PlaceholderDoc;
import it.eng.tz.puglia.autpae.entity.PlaceholderDTO;

/**
 * Row Mapper for table placeholder_doc
 */
public class PlaceholderDocRowMapper implements RowMapper<PlaceholderDTO> {

	public PlaceholderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		PlaceholderDTO result = new PlaceholderDTO();
		
		result.setCodice(rs.getObject(PlaceholderDoc.codice.columnName()) != null ? rs.getString(PlaceholderDoc.codice.columnName()) : null);
		result.setInfo(rs.getObject(PlaceholderDoc.info.columnName()) != null ? rs.getString(PlaceholderDoc.info.columnName()) : null);
				
		return result;
	}
   
}
