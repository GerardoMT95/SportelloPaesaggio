package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrumentiUrbanistici;

public class PptrStrumentiUrbanisticiRowMapper implements RowMapper<PptrStrumentiUrbanistici>{

	@Override
	public PptrStrumentiUrbanistici mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrStrumentiUrbanistici result=new PptrStrumentiUrbanistici();
		//ID,ISTAT_6_PROV,TIPO_STRUMENTO,STATO,ATTO,DATA_ATTO,UTENTE_MODIFICA,DATA_MODIFICA
		if (rs.getObject("ISTAT_6_PROV") != null) result.setCodIstat(rs.getString("ISTAT_6_PROV"));
		if (rs.getObject("TIPO_STRUMENTO") != null) result.setTipoStrumento(rs.getLong("TIPO_STRUMENTO"));
		if (rs.getObject("STATO") != null) result.setStato(rs.getString("STATO"));
		if (rs.getObject("ATTO") != null) result.setAtto(rs.getString("ATTO"));
		if (rs.getObject("DATA_ATTO") != null) result.setDataAtto(rs.getDate("DATA_ATTO"));
		return result;
	}
	

}
