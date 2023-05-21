package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDisclaimerXPratica;

public class PptrDisclaimerXPraticaRowMapper implements RowMapper<PptrDisclaimerXPratica>{

	@Override
	public PptrDisclaimerXPratica mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrDisclaimerXPratica result = new PptrDisclaimerXPratica();
		if (rs.getObject("FLG_DISCL_X_PRATICA") != null) result.setFlgDisclXPratica(rs.getString("FLG_DISCL_X_PRATICA"));
		if (rs.getObject("TNO_PPTR_DISCLAIMER_ID") != null) result.setPptrDisclaimerId(rs.getLong("TNO_PPTR_DISCLAIMER_ID"));
		
		return result;
	}
	//TNO_PPTR_DISCL_X_PRATICA_ID,T_PRATICA_APPTR_ID,FLG_DISCL_X_PRATICA,CODICE_PRATICA_APPPTR,REFERENTE_PROGETTO_ID,TNO_PPTR_DISCLAIMER_ID,PROG
	
}
