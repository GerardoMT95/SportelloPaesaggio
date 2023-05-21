/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoSopMatriceTerritorio;

/**
 * @author Adriano Colaianni
 * @date 10 set 2021
 */
public class TnoSopMatriceTerritorioRowMapper implements RowMapper<TnoSopMatriceTerritorio>{

	@Override
	public TnoSopMatriceTerritorio mapRow(ResultSet rs, int rowNum) throws SQLException {
		TnoSopMatriceTerritorio result =new TnoSopMatriceTerritorio();
		//ENTE_RIFERIMENTO,PEC_ENTE_RIFERIMENTO,PROV_RIFERIMENTO,MAIL_AGGIUNTIVA,COD_ISTAT,COMUNE
		if (rs.getObject("COD_ISTAT") != null) result.setCodIstat(rs.getLong("COD_ISTAT"));
		if (rs.getObject("ENTE_RIFERIMENTO") != null) result.setEnteRiferimento(rs.getString("ENTE_RIFERIMENTO"));
		if (rs.getObject("PEC_ENTE_RIFERIMENTO") != null) result.setPecEnteRiferimento(rs.getString("PEC_ENTE_RIFERIMENTO"));
		if (rs.getObject("PROV_RIFERIMENTO") != null) result.setProvRiferimento(rs.getString("PROV_RIFERIMENTO"));
		if (rs.getObject("COMUNE") != null) result.setComune(rs.getString("COMUNE"));
		if (rs.getObject("MAIL_AGGIUNTIVA") != null) result.setMailAggiuntiva(rs.getString("MAIL_AGGIUNTIVA"));
		return result;
	}

}
