package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrVincoliArchArch;

public class PptrVincoliArchArchRowMapper implements RowMapper<PptrVincoliArchArch>{

	@Override
	public PptrVincoliArchArch mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrVincoliArchArch result=new PptrVincoliArchArch();
		//VINC_ARCH_ARCH_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,VINC_ARC_NO_TUTELA,VINC_ARC_MONUM_DIRETTO,
		//VINC_ARC_MONUM_INDIRETTO,VINC_ARC_ARCHEO_DIRETTO,VINC_ARC_ARCHEO_INDIRETTO,ALTRI_VINCOLI,PROG
		if (rs.getObject("VINC_ARC_NO_TUTELA") != null) result.setVincArcNoTutuela(rs.getString("VINC_ARC_NO_TUTELA"));
		if (rs.getObject("VINC_ARC_MONUM_DIRETTO") != null) result.setVincArcMonumDiretto(rs.getString("VINC_ARC_MONUM_DIRETTO"));
		if (rs.getObject("VINC_ARC_MONUM_INDIRETTO") != null) result.setVincArcMonumIndirett(rs.getString("VINC_ARC_MONUM_INDIRETTO"));
		if (rs.getObject("VINC_ARC_ARCHEO_DIRETTO") != null) result.setVincArcArcheoDiretto(rs.getString("VINC_ARC_ARCHEO_DIRETTO"));
		if (rs.getObject("VINC_ARC_ARCHEO_INDIRETTO") != null) result.setVincArcArcheoIndirett(rs.getString("VINC_ARC_ARCHEO_INDIRETTO"));
		if (rs.getObject("ALTRI_VINCOLI") != null) result.setAltriVincoli(rs.getString("ALTRI_VINCOLI"));
		return result;
	}

}
