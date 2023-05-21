/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PraticaLavorazione;

/**
 * @author Adriano Colaianni
 * @date 9 set 2021
 */
public class PraticaLavorazioneRowMapper implements RowMapper<PraticaLavorazione> {

	@Override
	public PraticaLavorazione mapRow(ResultSet rs, int rowNum) throws SQLException {
		PraticaLavorazione result=new PraticaLavorazione();
		if (rs.getObject("CODICE_PRATICA_ENTEDELEGATO") != null) result.setCodicePraticaEnteDelegato(rs.getString("CODICE_PRATICA_ENTEDELEGATO"));
		if (rs.getObject("T_PRATICA_DESCRIZIONE") != null) result.settPraticaDescrizione(rs.getString("T_PRATICA_DESCRIZIONE"));
		if (rs.getObject("UFFICIO") != null) result.setUfficio(rs.getString("UFFICIO"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("DATAATTIVAZIONE") != null) result.setDataAttivazione(rs.getDate("DATAATTIVAZIONE"));
		return result;
	}

}
