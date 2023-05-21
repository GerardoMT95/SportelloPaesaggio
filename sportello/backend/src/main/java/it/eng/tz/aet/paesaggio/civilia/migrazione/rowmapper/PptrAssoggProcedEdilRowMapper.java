package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAssoggProcedEdil;

public class PptrAssoggProcedEdilRowMapper implements RowMapper<PptrAssoggProcedEdil>{

	@Override
	public PptrAssoggProcedEdil mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrAssoggProcedEdil result=new PptrAssoggProcedEdil();
		//TNO_PPTR_ASSOGG_PROCED_EDIL_ID,FLAG_ASSOGGETTATO,NO_ASSOGG_SPECIFICARE,ASSOGG_FLAG_PRATICA_IN_CORSO,
		//ASSOGG_ENTE_PRATICA_IN_CORSO,ASSOGG_DATAPR_PRATICA_IN_CORSO,ASSOGG_FLAG_PARERE_URB_ESPR,ASSOGG_DATA_APPROV_PRATICA,
		//CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG
		if (rs.getObject("FLAG_ASSOGGETTATO") != null) result.setFlagAssoggettato(rs.getString("FLAG_ASSOGGETTATO"));
		if (rs.getObject("NO_ASSOGG_SPECIFICARE") != null) result.setNoAssoggSpecificare(rs.getString("NO_ASSOGG_SPECIFICARE"));
		if (rs.getObject("ASSOGG_FLAG_PRATICA_IN_CORSO") != null) result.setAssoggFlagPraticaInCorso(rs.getString("ASSOGG_FLAG_PRATICA_IN_CORSO"));
		if (rs.getObject("ASSOGG_ENTE_PRATICA_IN_CORSO") != null) result.setAssoggEntePraticaInCorso(rs.getString("ASSOGG_ENTE_PRATICA_IN_CORSO"));
		if (rs.getObject("ASSOGG_DATAPR_PRATICA_IN_CORSO") != null) result.setAssoggDataprPraticaInCorso(rs.getDate("ASSOGG_DATAPR_PRATICA_IN_CORSO"));
		if (rs.getObject("ASSOGG_FLAG_PARERE_URB_ESPR") != null) result.setAssoggFlagParereUrbEspr(rs.getString("ASSOGG_FLAG_PARERE_URB_ESPR"));
		if (rs.getObject("ASSOGG_DATA_APPROV_PRATICA") != null) result.setAssoggDataApprovPratica(rs.getDate("ASSOGG_DATA_APPROV_PRATICA"));
		return result;
	}
	

}
