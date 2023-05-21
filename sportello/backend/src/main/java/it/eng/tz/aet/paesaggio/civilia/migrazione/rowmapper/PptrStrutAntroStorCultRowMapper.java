package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutAntroStorCult;

public class PptrStrutAntroStorCultRowMapper implements RowMapper<PptrStrutAntroStorCult>{

	@Override
	public PptrStrutAntroStorCult mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrStrutAntroStorCult result=new PptrStrutAntroStorCult();
		//TNO_PPTR_STR_ANTRO_STOCULT_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,
		//CULT_BP_INTERESSE_PUBB,CULT_BP_INTERESSE_PUBB_SPECIF,
		//CULT_BP_USI_CIVICI,CULT_BP_INTERESSE_ARCHEO,
		//CULT_UCP_CITTA_CONSOLIDATA,CULT_UCP_STRAT_INSED_ARCHEO,CULT_UCP_STRAT_INSED_ARC_SPEC,
		//CULT_UCP_STRAT_RETE_TRATTURI,CULT_UCP_STRAT_TRATTURI_SPECIF,
		//CULT_UCP_STRAT_INSED_RISK_ARC,CULT_UCP_STR_INS_RISK_ARC_SPEC,
		//CULT_UCP_RISP_COMPON_INSEDIAT,CULT_UCP__PAESAG_RURALI,
		//PERC_UCP_STRADE_VALENZA_PAESAG,CULT_UCP_STRADE_PANORAMICHE,CULT_UCP_PUNTI_PANORAMICI,CULT_UCP_CONI_VISUALI,PROG
		if (rs.getObject("CULT_BP_INTERESSE_PUBB") != null) result.setCultBpInteressePubb(rs.getString("CULT_BP_INTERESSE_PUBB"));
		if (rs.getObject("CULT_BP_INTERESSE_PUBB_SPECIF") != null) result.setCultBpInteressePubbSpecif(rs.getString("CULT_BP_INTERESSE_PUBB_SPECIF"));
		if (rs.getObject("CULT_BP_USI_CIVICI") != null) result.setCultBpUsiCivici(rs.getString("CULT_BP_USI_CIVICI"));
		if (rs.getObject("CULT_BP_INTERESSE_ARCHEO") != null) result.setCultBpInteresseArcheo(rs.getString("CULT_BP_INTERESSE_ARCHEO"));
		if (rs.getObject("CULT_UCP_CITTA_CONSOLIDATA") != null) result.setCultUcpCittaConsolidata(rs.getString("CULT_UCP_CITTA_CONSOLIDATA"));
		if (rs.getObject("CULT_UCP_STRAT_INSED_ARCHEO") != null) result.setCultUcpStratInsedArcheo(rs.getString("CULT_UCP_STRAT_INSED_ARCHEO"));
		if (rs.getObject("CULT_UCP_STRAT_INSED_ARC_SPEC") != null) result.setCultUcpStratInsedArcSpec(rs.getString("CULT_UCP_STRAT_INSED_ARC_SPEC"));
		if (rs.getObject("CULT_UCP_STRAT_RETE_TRATTURI") != null) result.setCultUcpStratReteTratturi(rs.getString("CULT_UCP_STRAT_RETE_TRATTURI"));
		if (rs.getObject("CULT_UCP_STRAT_TRATTURI_SPECIF") != null) result.setCultUcpStratTratturiSpecif(rs.getString("CULT_UCP_STRAT_TRATTURI_SPECIF"));
		if (rs.getObject("CULT_UCP_STRAT_INSED_RISK_ARC") != null) result.setCultUcpStratInsedRiskArc(rs.getString("CULT_UCP_STRAT_INSED_RISK_ARC"));
		if (rs.getObject("CULT_UCP_STR_INS_RISK_ARC_SPEC") != null) result.setCultUcpStrInsRiskArcSpec(rs.getString("CULT_UCP_STR_INS_RISK_ARC_SPEC"));
		if (rs.getObject("CULT_UCP_RISP_COMPON_INSEDIAT") != null) result.setCultUcpRispComponInsediat(rs.getString("CULT_UCP_RISP_COMPON_INSEDIAT"));
		if (rs.getObject("CULT_UCP__PAESAG_RURALI") != null) result.setCultUcpPaesagRurali(rs.getString("CULT_UCP__PAESAG_RURALI"));
		if (rs.getObject("PERC_UCP_STRADE_VALENZA_PAESAG") != null) result.setPercUcpStradeValenzaPaesag(rs.getString("PERC_UCP_STRADE_VALENZA_PAESAG"));
		if (rs.getObject("CULT_UCP_STRADE_PANORAMICHE") != null) result.setCultUcpStradePanoramiche(rs.getString("CULT_UCP_STRADE_PANORAMICHE"));
		if (rs.getObject("CULT_UCP_PUNTI_PANORAMICI") != null) result.setCultUcpPuntiPanoramici(rs.getString("CULT_UCP_PUNTI_PANORAMICI"));
		if (rs.getObject("CULT_UCP_CONI_VISUALI") != null) result.setCultUcpPuntiPanoramici(rs.getString("CULT_UCP_CONI_VISUALI"));
		return result;
	}
	

}
