/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCodiceInterno;

/**
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class PptrCodiceInternoRowMapper implements RowMapper<PptrCodiceInterno>{

	@Override
	public PptrCodiceInterno mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrCodiceInterno result=new PptrCodiceInterno();
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaApptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("RIF_DATA") != null) result.setRifData(rs.getDate("RIF_DATA"));
		if (rs.getObject("RIF_INT_ALFAN") != null) result.setRifIntAlfanumerico(rs.getString("RIF_INT_ALFAN"));
		if (rs.getObject("RIF_NUMERO") != null) result.setRifNumero(rs.getString("RIF_NUMERO"));
		if (rs.getObject("RIF_PROTOCOLLO") != null) result.setRifProtocollo(rs.getString("RIF_PROTOCOLLO"));
		return result;
	}
	

}
