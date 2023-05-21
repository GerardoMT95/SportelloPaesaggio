package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoEnte;
import it.eng.tz.puglia.autpae.entity.AllegatoEnteDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class AllegatoEnteRowMapper implements RowMapper<AllegatoEnteDTO> {

	public AllegatoEnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AllegatoEnteDTO result = new AllegatoEnteDTO();
		
		result.setIdAllegato(rs.getObject(AllegatoEnte.id_allegato.columnName()) != null ? rs.getLong(AllegatoEnte.id_allegato.columnName()) : null);
		result.setCodice(rs.getObject(AllegatoEnte.codice.columnName()) != null ? rs.getString(AllegatoEnte.codice.columnName()) : null);
		result.setDescrizione(rs.getObject(AllegatoEnte.descrizione.columnName()) != null ? rs.getString(AllegatoEnte.descrizione.columnName()) : null);
		return result;
	}
   
}
