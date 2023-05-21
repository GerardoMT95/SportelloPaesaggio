package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutIdrogeomorf;

public class PptrStrutIdrogeomorfRowMapper implements RowMapper<PptrStrutIdrogeomorf>{

	@Override
	public PptrStrutIdrogeomorf mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrStrutIdrogeomorf result=new PptrStrutIdrogeomorf();
		//STRUT_IDROGEOMORF_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,GEO_UCP_VERSANTI,GEO_UCP_LAME_GRAVINE,
		//GEO_UCP_DOLINE,GEO_UCP_GROTTE,GEO_UCP_GEOSITI,GEO_UCP_INGHIOTTITOI,GEO_UCP_CORDONI_DUNARI,IDRO_BP_TERRIT_COSTIERI,
		//IDRO_BP_TERRIT_CONTERM_LAGHI,IDRO_BP_CORSI_ACQUA,IDRO_BP_CORSI_ACQUA_SPECIF,IDRO_UCP_RETICOLO_IDROGRAFICO,
		//IDRO_UCP_SORGENTI,IDRO_UCP_AREE_SOGG_VINC,PROG
		if (rs.getObject("GEO_UCP_VERSANTI") != null) result.setGeoUcpVersanti(rs.getString("GEO_UCP_VERSANTI"));
		if (rs.getObject("GEO_UCP_LAME_GRAVINE") != null) result.setGeoUcpLameGravine(rs.getString("GEO_UCP_LAME_GRAVINE"));
		if (rs.getObject("GEO_UCP_DOLINE") != null) result.setGeoUcpDoline(rs.getString("GEO_UCP_DOLINE"));
		if (rs.getObject("GEO_UCP_GROTTE") != null) result.setGeoUcpGrotte(rs.getString("GEO_UCP_GROTTE"));
		if (rs.getObject("GEO_UCP_GEOSITI") != null) result.setGeoUcpGeositi(rs.getString("GEO_UCP_GEOSITI"));
		if (rs.getObject("GEO_UCP_INGHIOTTITOI") != null) result.setGeoUcpInghiottitoi(rs.getString("GEO_UCP_INGHIOTTITOI"));
		if (rs.getObject("GEO_UCP_CORDONI_DUNARI") != null) result.setGeoUcpCordoniDunari(rs.getString("GEO_UCP_CORDONI_DUNARI"));
		if (rs.getObject("IDRO_BP_TERRIT_COSTIERI") != null) result.setIdroBpTerritCostieri(rs.getString("IDRO_BP_TERRIT_COSTIERI"));
		if (rs.getObject("IDRO_BP_TERRIT_CONTERM_LAGHI") != null) result.setIdroBpTerritContermLaghi(rs.getString("IDRO_BP_TERRIT_CONTERM_LAGHI"));
		if (rs.getObject("IDRO_BP_CORSI_ACQUA") != null) result.setIdroBpCorsiAcqua(rs.getString("IDRO_BP_CORSI_ACQUA"));
		if (rs.getObject("IDRO_BP_CORSI_ACQUA_SPECIF") != null) result.setIdroBpCorsiAcquaSpecif(rs.getString("IDRO_BP_CORSI_ACQUA_SPECIF"));
		if (rs.getObject("IDRO_UCP_RETICOLO_IDROGRAFICO") != null) result.setIdroUcpReticoloIdrografico(rs.getString("IDRO_UCP_RETICOLO_IDROGRAFICO"));
		if (rs.getObject("IDRO_UCP_SORGENTI") != null) result.setIdroUcpSorgenti(rs.getString("IDRO_UCP_SORGENTI"));
		if (rs.getObject("IDRO_UCP_AREE_SOGG_VINC") != null) result.setIdroUcpAreeSoggVinc(rs.getString("IDRO_UCP_AREE_SOGG_VINC"));
		return result;
	}

}
