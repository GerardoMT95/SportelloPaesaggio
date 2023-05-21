package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStatoEffMitigaz;

public class PptrStatoEffMitigazRowMapper implements RowMapper<PptrStatoEffMitigaz>{

	@Override
	public PptrStatoEffMitigaz mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrStatoEffMitigaz result=new PptrStatoEffMitigaz();
		//STATO_EFF_MITIGAZ_ID,T_PRATICA_APPTR_ID,CODICE_PRATICA_APPPTR,DESCR_STATO_ATTUALE,EFFETTI_REALIZ_OPERA,MITIGAZIONE_IMP_INTERV,
		//PROG,INDICAZ_CONTENUTI_PERCETTIVI
		if (rs.getObject("DESCR_STATO_ATTUALE") != null) result.setDescrStatoAttuale(rs.getString("DESCR_STATO_ATTUALE"));
		if (rs.getObject("EFFETTI_REALIZ_OPERA") != null) result.setEffettiRealizOpera(rs.getString("EFFETTI_REALIZ_OPERA"));
		if (rs.getObject("MITIGAZIONE_IMP_INTERV") != null) result.setMitigazioneImpInterv(rs.getString("MITIGAZIONE_IMP_INTERV"));
		if (rs.getObject("INDICAZ_CONTENUTI_PERCETTIVI") != null) result.setIndicazContenutiPercettivi(rs.getString("INDICAZ_CONTENUTI_PERCETTIVI"));
		return result;
	}
	

}
