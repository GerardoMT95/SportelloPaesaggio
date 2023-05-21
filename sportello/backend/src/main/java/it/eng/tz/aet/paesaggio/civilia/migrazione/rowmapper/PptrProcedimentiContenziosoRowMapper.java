package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProcedimentiContenzioso;

public class PptrProcedimentiContenziosoRowMapper implements RowMapper<PptrProcedimentiContenzioso>{

	@Override
	public PptrProcedimentiContenzioso mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrProcedimentiContenzioso result=new PptrProcedimentiContenzioso();
		//TNO_PPTR_PROCED_CONTENZIOSO_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,FLAG_CONTENZIOSO_IN_ATTO,DESCRIZIONE,PROG
		if (rs.getObject("FLAG_CONTENZIOSO_IN_ATTO") != null) result.setFlagContenziosoInAtto(rs.getString("FLAG_CONTENZIOSO_IN_ATTO"));				
		if (rs.getObject("DESCRIZIONE") != null) result.setDescrizione(rs.getString("DESCRIZIONE"));
		return result;
	}

}
