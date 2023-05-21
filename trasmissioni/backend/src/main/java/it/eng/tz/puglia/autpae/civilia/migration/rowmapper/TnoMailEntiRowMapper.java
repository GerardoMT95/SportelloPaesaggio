/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoMailEnti;

/**
 * @author Adriano Colaianni
 * @date 10 set 2021
 */
public class TnoMailEntiRowMapper implements RowMapper<TnoMailEnti>{

	@Override
	public TnoMailEnti mapRow(ResultSet rs, int rowNum) throws SQLException {
		TnoMailEnti result = new TnoMailEnti();
		//COD_ISTAT,ENTE_RIFERIMENTO,PEC_ENTE_RIFERIMENTO,TIPO_TERRITORIO
		if (rs.getObject("COD_ISTAT") != null) result.setCodIstat(rs.getLong("COD_ISTAT"));
		if (rs.getObject("ENTE_RIFERIMENTO") != null) result.setEnteRiferimento(rs.getString("ENTE_RIFERIMENTO"));
		if (rs.getObject("PEC_ENTE_RIFERIMENTO") != null) result.setPecEnteRiferimento(rs.getString("PEC_ENTE_RIFERIMENTO"));
		if (rs.getObject("TIPO_TERRITORIO") != null) result.setTipoTerritorio(rs.getString("TIPO_TERRITORIO"));
		return result;
	}

}
