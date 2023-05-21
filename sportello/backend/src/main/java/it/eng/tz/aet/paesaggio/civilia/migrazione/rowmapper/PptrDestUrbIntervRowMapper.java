package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDestUrbInterv;

public class PptrDestUrbIntervRowMapper implements RowMapper<PptrDestUrbInterv>{

	@Override
	public PptrDestUrbInterv mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrDestUrbInterv result = new PptrDestUrbInterv();
		//TNO_PPTR_DEST_URB_INTERV_ID,T_PRATICA_APPTR_ID,STRUM_URB_APPROVATO,
		//STRUM_URB_APPROVATO_DATA,STRUM_URB_APPROVATO_ATTO,DESTIN_AREA_STR_VIG,
		//STRUM_APPROV_ULT_TUTELE,STRUM_URB_ADOTTATO,STRUM_URB_ADOTTATO_DATA,STRUM_URB_ADOTTATO_ATTO,
		//DESTIN_AREA_STR_ADOTT,STRUM_ADOTT_ULT_TUTELE,CONFORME_DISCIPL_URB_VIG,CODICE_PRATICA_APPPTR,PROG,
		//LOCALIZ_INTERV_ID,ID_STRUM_URBAN_ART100,CHECK_PRESA_VISIONE,ID_STRUM_URBAN_ART38 
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaApptrId(rs.getLong("T_PRATICA_APPTR_ID"));				
		if (rs.getObject("STRUM_URB_APPROVATO") != null) result.setStrumUrbApprovato(rs.getString("STRUM_URB_APPROVATO"));
		if (rs.getObject("STRUM_URB_APPROVATO_DATA") != null) result.setStrumUrbApprovatoData(rs.getDate("STRUM_URB_APPROVATO_DATA"));
		if (rs.getObject("STRUM_URB_APPROVATO_ATTO") != null) result.setStrumUrbApprovatoAtto(rs.getString("STRUM_URB_APPROVATO_ATTO"));
		if (rs.getObject("DESTIN_AREA_STR_VIG") != null) result.setDestinAreaStrVig(rs.getString("DESTIN_AREA_STR_VIG"));
		if (rs.getObject("STRUM_APPROV_ULT_TUTELE") != null) result.setStrumApprovUltTutele(rs.getString("STRUM_APPROV_ULT_TUTELE"));
		if (rs.getObject("STRUM_URB_ADOTTATO") != null) result.setStrumUrbAdottato(rs.getString("STRUM_URB_ADOTTATO"));
		if (rs.getObject("STRUM_URB_ADOTTATO_DATA") != null) result.setStrumUrbAdottatoData(rs.getDate("STRUM_URB_ADOTTATO_DATA"));
		if (rs.getObject("STRUM_URB_ADOTTATO_ATTO") != null) result.setStrumUrbAdottatoAtto(rs.getString("STRUM_URB_ADOTTATO_ATTO"));
		if (rs.getObject("DESTIN_AREA_STR_ADOTT") != null) result.setDestinAreaStrAdott(rs.getString("DESTIN_AREA_STR_ADOTT"));
		if (rs.getObject("STRUM_ADOTT_ULT_TUTELE") != null) result.setStrumAdottUltTutele(rs.getString("STRUM_ADOTT_ULT_TUTELE"));
		if (rs.getObject("CONFORME_DISCIPL_URB_VIG") != null) result.setConformeDisciplUrbVig(rs.getString("CONFORME_DISCIPL_URB_VIG"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("LOCALIZ_INTERV_ID") != null) result.setIdLocalizzaInterv(rs.getLong("LOCALIZ_INTERV_ID"));
		if (rs.getObject("ID_STRUM_URBAN_ART100") != null) result.setIdStrumUrbanArt100(rs.getLong("ID_STRUM_URBAN_ART100"));
		if (rs.getObject("CHECK_PRESA_VISIONE") != null) result.setCheckPresaVisione(rs.getString("CHECK_PRESA_VISIONE"));
		if (rs.getObject("ID_STRUM_URBAN_ART38") != null) result.setIdStrumUrbanArt38(rs.getLong("ID_STRUM_URBAN_ART38"));
		return result;
	}
	
	

}
