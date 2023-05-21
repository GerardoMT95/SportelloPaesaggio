package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;

public class VtnoAttivitaPptrRowMapper implements RowMapper<VtnoAttivitaPptr> {

	@Override
	public VtnoAttivitaPptr mapRow(ResultSet rs, int rowNum) throws SQLException {
		final VtnoAttivitaPptr result=new VtnoAttivitaPptr();
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("T_PRATICA_CODICE") != null) result.settPraticaCodice(rs.getString("T_PRATICA_CODICE"));
		if (rs.getObject("T_PRATICA_DESCRIZIONE") != null) result.settPraticaDescrizione(rs.getString("T_PRATICA_DESCRIZIONE"));
		if (rs.getObject("ACTIVITIES_ACT_DEFINITIONID") != null) result.setActivitiesActDefinitionid(rs.getString("ACTIVITIES_ACT_DEFINITIONID"));
		if (rs.getObject("ACTIVITIES_NAME") != null) result.setActivitiesName(rs.getString("ACTIVITIES_NAME"));
		if (rs.getObject("ACTIVITIES_PROCESS") != null) result.setActivitiesProcess(rs.getString("ACTIVITIES_PROCESS"));
		if (rs.getObject("JBP_UNAME") != null) result.setJbpUname(rs.getString("JBP_UNAME"));
		if (rs.getObject("PPTR_FASE_ISTANZA_CODICE") != null) result.setPptrFaseIstanzaCodice(rs.getString("PPTR_FASE_ISTANZA_CODICE"));
		if (rs.getObject("SOLOTRASMISSIONE") != null) result.setSoloTrasmissione(rs.getLong("SOLOTRASMISSIONE"));
		if (rs.getObject("CODICE_PRATICA_ENTEDELEGATO") != null) result.setCodicePraticaEnteDelegato(rs.getString("CODICE_PRATICA_ENTEDELEGATO"));
		if (rs.getObject("ENTEDELEGATO") != null) result.setEntedelegato(rs.getString("ENTEDELEGATO"));
		if (rs.getObject("TNO_PPTR_ABILITAZ_ENTEDELEG_ID") != null) result.setIdEntedelegato(rs.getLong("TNO_PPTR_ABILITAZ_ENTEDELEG_ID"));
		if (rs.getObject("DESCPROCEDIMENTO") != null) result.setDescprocedimento(rs.getString("DESCPROCEDIMENTO"));
		if (rs.getObject("TIPOPROCEDIMENTO") != null) result.setTipoProcedimento(rs.getLong("TIPOPROCEDIMENTO"));
		if (rs.getObject("OBJECTIDPUBBLICAZIONE") != null) result.setObjectIdPubblicazione(rs.getLong("OBJECTIDPUBBLICAZIONE"));
		return result;
	}
	
}




