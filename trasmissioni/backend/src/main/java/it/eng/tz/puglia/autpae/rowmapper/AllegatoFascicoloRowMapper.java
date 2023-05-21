package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoFascicolo;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class AllegatoFascicoloRowMapper implements RowMapper<AllegatoFascicoloDTO> {

	public AllegatoFascicoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AllegatoFascicoloDTO result = new AllegatoFascicoloDTO();
		
		result.setIdAllegato(rs.getObject(AllegatoFascicolo.id_allegato.columnName()) != null ? rs.getLong(AllegatoFascicolo.id_allegato.columnName()) : null);
		result.setType(rs.getObject(AllegatoFascicolo.type.columnName()) != null ? TipoAllegato.valueOf(rs.getString(AllegatoFascicolo.type.columnName())) : null);
		result.setIdFascicolo(rs.getObject(AllegatoFascicolo.id_fascicolo.columnName()) != null ? rs.getLong(AllegatoFascicolo.id_fascicolo.columnName()) : null);
				
		return result;
	}
   
}
