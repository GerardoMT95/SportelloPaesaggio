/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProvvedimento;

/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class PptrProvvedimentoRowMapper implements RowMapper<PptrProvvedimento>{

	@Override
	public PptrProvvedimento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrProvvedimento result=new PptrProvvedimento();
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaApPptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("DATA_PROVVEDIMENTO") != null) result.setDataProvvedimento(rs.getDate("DATA_PROVVEDIMENTO"));
		if (rs.getObject("FLAG_ESITO") != null) result.setFlagEsito(rs.getString("FLAG_ESITO"));
		if (rs.getObject("NUMERO_PROVVEDIMENTO") != null) result.setNumeroProvvedimento(rs.getString("NUMERO_PROVVEDIMENTO"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("RUP") != null) result.setRup(rs.getString("RUP"));
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaApptrId(rs.getString("T_PRATICA_APPTR_ID"));
		return result;
	}

}
