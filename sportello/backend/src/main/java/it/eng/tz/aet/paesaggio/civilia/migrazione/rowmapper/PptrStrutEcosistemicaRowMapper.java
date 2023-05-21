package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutEcosistemica;

public class PptrStrutEcosistemicaRowMapper  implements RowMapper<PptrStrutEcosistemica>{

	@Override
	public PptrStrutEcosistemica mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrStrutEcosistemica result=new PptrStrutEcosistemica();
		//STRUT_ECOSISTEMICA_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,BOT_BP_BOSCHI,BOT_BP_ZONE_UMIDE_RAMSAR,
		//BOT_BP_Z_UMIDE_RAMSAR_SPECIF,BOT_UCP_AREE_UMIDE,BOT_UCP_AREE_UMIDE_SPECIF,BOT_UCP_PRATI_PASCOLI,
		//BOT_UCP_FORM_ARBUSTIVE,BOT_UCP_AREE_RISP_BOSCHI,NAT_UCP_AREA_RISP_PARCHI,NAT_UCP_SITI_RIL_NATURAL,
		//NAT_UCP_SITI_RIL_NATUR_SPECIF,PROG
		if (rs.getObject("BOT_BP_BOSCHI") != null) result.setBotBpBoschi(rs.getString("BOT_BP_BOSCHI"));
		if (rs.getObject("BOT_BP_ZONE_UMIDE_RAMSAR") != null) result.setBotBpZoneUmideRamsar(rs.getString("BOT_BP_ZONE_UMIDE_RAMSAR"));
		if (rs.getObject("BOT_BP_Z_UMIDE_RAMSAR_SPECIF") != null) result.setBotBpZUmideRamsarSpecif(rs.getString("BOT_BP_Z_UMIDE_RAMSAR_SPECIF"));
		if (rs.getObject("BOT_UCP_AREE_UMIDE") != null) result.setBotUcpAreeUmide(rs.getString("BOT_UCP_AREE_UMIDE"));
		if (rs.getObject("BOT_UCP_AREE_UMIDE_SPECIF") != null) result.setBotUcpAreeUmideSpecif(rs.getString("BOT_UCP_AREE_UMIDE_SPECIF"));
		if (rs.getObject("BOT_UCP_PRATI_PASCOLI") != null) result.setBotUcpPratiPascoli(rs.getString("BOT_UCP_PRATI_PASCOLI"));
		if (rs.getObject("BOT_UCP_FORM_ARBUSTIVE") != null) result.setBotUcpFormArbustive(rs.getString("BOT_UCP_FORM_ARBUSTIVE"));
		if (rs.getObject("BOT_UCP_AREE_RISP_BOSCHI") != null) result.setBotUcpAreeRispBoschi(rs.getString("BOT_UCP_AREE_RISP_BOSCHI"));
		if (rs.getObject("NAT_UCP_AREA_RISP_PARCHI") != null) result.setNatUcpAreaRispParchi(rs.getString("NAT_UCP_AREA_RISP_PARCHI"));
		if (rs.getObject("NAT_UCP_SITI_RIL_NATURAL") != null) result.setNatUcpSitiRilNatural(rs.getString("NAT_UCP_SITI_RIL_NATURAL"));
		if (rs.getObject("NAT_UCP_SITI_RIL_NATUR_SPECIF") != null) result.setNatUcpSitiRilNaturSpecif(rs.getString("NAT_UCP_SITI_RIL_NATUR_SPECIF"));
		return result;
	}
	

}
