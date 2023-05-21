/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.TkeDocAttr;

/**
 * @author Adriano Colaianni
 * @date 28 apr 2021
 */
public class TkeDocAttrRowMapper implements RowMapper<TkeDocAttr>{

	@Override
	public TkeDocAttr mapRow(ResultSet rs, int rowNum) throws SQLException {
		TkeDocAttr result = new TkeDocAttr();
		if (rs.getObject("NAME") != null) result.setName(rs.getString("NAME"));
		if (rs.getObject("VALUE") != null) result.setValue(rs.getString("VALUE"));
		if (rs.getObject("PROPRIETARIO") != null) result.setProprietario(rs.getString("PROPRIETARIO"));
		if (rs.getObject("T_KE_DOC_ID") != null) result.settKeDocId(rs.getString("T_KE_DOC_ID"));
		return result;
	}
	

}
