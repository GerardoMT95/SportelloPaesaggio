package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloCorrispondenza;
import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class FascicoloCorrispondenzaRowMapper implements RowMapper<FascicoloCorrispondenzaDTO> {

	public FascicoloCorrispondenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FascicoloCorrispondenzaDTO result = new FascicoloCorrispondenzaDTO();
		
		result.setId(rs.getObject(FascicoloCorrispondenza.id.columnName()) != null ? rs.getLong(FascicoloCorrispondenza.id.columnName()) : null);
		result.setIdCorrispondenza(rs.getObject(FascicoloCorrispondenza.id_corrispondenza.columnName()) != null ? rs.getLong(FascicoloCorrispondenza.id_corrispondenza.columnName()) : null);
		result.setIdFascicolo(rs.getObject(FascicoloCorrispondenza.id_fascicolo.columnName()) != null ? rs.getLong(FascicoloCorrispondenza.id_fascicolo.columnName()) : null);
		return result;
	}
   
}
