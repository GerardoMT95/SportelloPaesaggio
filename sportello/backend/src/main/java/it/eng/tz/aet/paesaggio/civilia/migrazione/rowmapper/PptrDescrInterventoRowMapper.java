package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDescrIntervento;

public class PptrDescrInterventoRowMapper implements RowMapper<PptrDescrIntervento>{

	@Override
	public PptrDescrIntervento mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrDescrIntervento result=new PptrDescrIntervento();
		if (rs.getObject("DESCR_INTERVENTO") != null) result.setDescrIntervento(rs.getString("DESCR_INTERVENTO"));
		return result;
	}
	

}
