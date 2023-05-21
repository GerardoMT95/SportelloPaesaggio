/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.Comuni_completo_cod_istat;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class Comuni_completo_cod_istatRowMapper implements RowMapper<Comuni_completo_cod_istat> {

	@Override
	public Comuni_completo_cod_istat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comuni_completo_cod_istat result=new Comuni_completo_cod_istat();
		if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		if (rs.getObject("PROVINCIA") != null) result.setProvincia(rs.getString("PROVINCIA"));
		if (rs.getObject("CODICE_CATASTALE") != null) result.setCodiceCatastale(rs.getString("CODICE_CATASTALE"));
		if (rs.getObject("ISTAT_6_PROV") != null) result.setIstat6province(rs.getString("ISTAT_6_PROV"));
		return result;
	}
	

}
