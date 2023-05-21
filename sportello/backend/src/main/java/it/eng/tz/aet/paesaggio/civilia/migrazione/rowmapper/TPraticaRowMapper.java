package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.TPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;

public class TPraticaRowMapper implements RowMapper<TPratica> {

	@Override
	public TPratica mapRow(ResultSet rs, int rowNum) throws SQLException {
		final TPratica result=new TPratica();
		if (rs.getObject("CODICE") != null) result.setCodice(rs.getString("CODICE"));
		if (rs.getObject("DATAATTIVAZIONE") != null) result.setDataAttivazione(rs.getDate("DATAATTIVAZIONE"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("T_TIPOPRATICA_ID") != null) result.settTipopraticaId(rs.getLong("T_TIPOPRATICA_ID"));
		return result;
	}
	
}




