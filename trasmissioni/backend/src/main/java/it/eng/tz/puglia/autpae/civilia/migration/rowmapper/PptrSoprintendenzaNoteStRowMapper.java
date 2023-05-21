/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class PptrSoprintendenzaNoteStRowMapper implements RowMapper<PptrSoprintendenzaNoteSt>{

	@Override
	public PptrSoprintendenzaNoteSt mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrSoprintendenzaNoteSt result=new PptrSoprintendenzaNoteSt();
		if (rs.getObject("SPUNTA") != null) result.setSpunta(rs.getLong("SPUNTA"));
		if (rs.getObject("NOTE") != null) result.setNote(rs.getString("NOTE"));
		return result;
	}

	
}
