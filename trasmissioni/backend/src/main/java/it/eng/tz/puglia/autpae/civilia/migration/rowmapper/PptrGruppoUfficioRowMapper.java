/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;

/**
 * @author Adriano Colaianni
 * @date 29 apr 2021
 */
public class PptrGruppoUfficioRowMapper implements RowMapper<PptrGruppoUfficio> {

	@Override
	public PptrGruppoUfficio mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrGruppoUfficio result=new PptrGruppoUfficio();
		if (rs.getObject("codiceGruppo") != null) result.setCodiceGruppo(rs.getString("codiceGruppo"));
		if (rs.getObject("codiceUfficio") != null) result.setCodiceUfficio(rs.getString("codiceUfficio"));
		if (rs.getObject("descrizioneGruppo") != null) result.setDescrizioneGruppo(rs.getString("descrizioneGruppo"));
		if (rs.getObject("gruppoId") != null) result.setGruppoId(rs.getLong("gruppoId"));
		if (rs.getObject("tUfficioId") != null) result.settUfficioId(rs.getLong("tUfficioId"));
		if (rs.getObject("ufficioDescrizione") != null) result.setUfficioDescrizione(rs.getString("ufficioDescrizione"));
		return result;
	}

}
