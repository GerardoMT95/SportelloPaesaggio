package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoCorrispondenza;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class AllegatoCorrispondenzaRowMapper implements RowMapper<AllegatoCorrispondenzaDTO> {

	public AllegatoCorrispondenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AllegatoCorrispondenzaDTO result = new AllegatoCorrispondenzaDTO();
		
		result.setIdAllegato(rs.getObject(AllegatoCorrispondenza.id_allegato.columnName()) != null ? rs.getLong(AllegatoCorrispondenza.id_allegato.columnName()) : null);
		result.setIdCorrispondenza(rs.getObject(AllegatoCorrispondenza.id_corrispondenza.columnName()) != null ? rs.getLong(AllegatoCorrispondenza.id_corrispondenza.columnName()) : null);
		result.setIsUrl(rs.getObject(AllegatoCorrispondenza.is_url.columnName()) != null ? rs.getBoolean(AllegatoCorrispondenza.is_url.columnName()) : false);
				
		return result;
	}
   
}
