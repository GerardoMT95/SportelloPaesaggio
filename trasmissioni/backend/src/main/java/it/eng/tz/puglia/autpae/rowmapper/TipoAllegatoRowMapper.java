package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.TipoAllegato;
import it.eng.tz.puglia.autpae.entity.TipoAllegatoDTO;

/**
 * Row Mapper for table autpae.tipo_allegato
 */
public class TipoAllegatoRowMapper implements RowMapper<TipoAllegatoDTO> {

	public TipoAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TipoAllegatoDTO result = new TipoAllegatoDTO();
		
		result.setType       (rs.getObject(TipoAllegato.type       .columnName()) != null ? it.eng.tz.puglia.autpae.enumeratori.TipoAllegato.valueOf(rs.getString(TipoAllegato.type       .columnName())) : null);
		result.setDescrizione(rs.getObject(TipoAllegato.descrizione.columnName()) != null ? 														 rs.getString(TipoAllegato.descrizione.columnName())  : null);
		return result;
	}
   
}
