package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrApprovato;

public class PptrApprovatoCiviliaRowMapper implements RowMapper<PptrApprovato>{

	@Override
	public PptrApprovato mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrApprovato result=new PptrApprovato();
		//PPTR_APPROVATO_ID,T_PRATICA_APPTR,CODICE_PRATICA_APPTR,AMBITO_PAESAGGISTICO,FIGURE_AMBITO_PAESAGGISTICO,
		//RICADE_TERR_ART_1_03_CO_5_6,RICADE_TERR_ART_142_CO_2,PROG
		if (rs.getObject("AMBITO_PAESAGGISTICO") != null) result.setAmbitoPaesaggistico(rs.getString("AMBITO_PAESAGGISTICO"));
		if (rs.getObject("FIGURE_AMBITO_PAESAGGISTICO") != null) result.setFigureAmbitoPaesaggistico(rs.getString("FIGURE_AMBITO_PAESAGGISTICO"));
		if (rs.getObject("RICADE_TERR_ART_1_03_CO_5_6") != null) result.setRicadeTerrArt103Co56(rs.getString("RICADE_TERR_ART_1_03_CO_5_6"));
		if (rs.getObject("RICADE_TERR_ART_142_CO_2") != null) result.setRicadeTerrArt142Co2(rs.getString("RICADE_TERR_ART_142_CO_2"));
		return result;
	}
	

}
