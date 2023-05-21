package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAltreDichiarRich;

public class PptrAltreDichiarRichRowMapper implements RowMapper<PptrAltreDichiarRich>{

	@Override
	public PptrAltreDichiarRich mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrAltreDichiarRich result=new PptrAltreDichiarRich();
		if (rs.getObject("CHECK_136") != null) result.setCheck136(rs.getString("CHECK_136"));
		if (rs.getObject("CHECK_136A") != null) result.setCheck136A(rs.getString("CHECK_136A"));
		if (rs.getObject("CHECK_136B") != null) result.setCheck136B(rs.getString("CHECK_136B"));
		if (rs.getObject("CHECK_136C") != null) result.setCheck136C(rs.getString("CHECK_136C"));
		if (rs.getObject("CHECK_136D") != null) result.setCheck136C(rs.getString("CHECK_136D"));
		if (rs.getObject("CHECK_142") != null) result.setCheck142(rs.getString("CHECK_142"));
		if (rs.getObject("CHECK_142_PARCHI") != null) result.setCheck142Parchi(rs.getString("CHECK_142_PARCHI"));
		if (rs.getObject("CHECK_134") != null) result.setCheck134(rs.getString("CHECK_134"));
		if (rs.getObject("ENTE_RILASCIO") != null) result.setEnteRilascio(rs.getString("ENTE_RILASCIO"));
		if (rs.getObject("DESCR_AUT_RILASCIATA") != null) result.setDescrAutRilasciata(rs.getString("DESCR_AUT_RILASCIATA"));
		if (rs.getObject("DATA_RILASCIO") != null) result.setDataRilascio(rs.getDate("DATA_RILASCIO"));
		return result;
	}
	//CHECK_136,CHECK_142,CHECK_142_PARCHI,CHECK_134,ENTE_RILASCIO,DESCR_AUT_RILASCIATA,
	//DATA_RILASCIO,CHECK_136A,CHECK_136B,CHECK_136C,CHECK_136D

}
