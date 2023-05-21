package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittTitoli;

public class PptrLegittTitoliRowMapper implements RowMapper<PptrLegittTitoli>{

	@Override
	public PptrLegittTitoli mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrLegittTitoli result = new PptrLegittTitoli();
		//LEGITT_TITOLI_ID,LEGITTIMITA_ID,LEG_TIT_DENOMINAZIONE,LEG_TIT_RILASCIATO_DA,LEG_TIT_NUM_PROTOCOLLO,
		//LEG_TIT_DATA_RILASCIO,LEG_TIT_INTESTATARIO,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("LEGITT_TITOLI_ID") != null) result.setLegittTitoliId(rs.getLong("LEGITT_TITOLI_ID"));
		if (rs.getObject("LEGITTIMITA_ID") != null) result.setLegittimitaId(rs.getLong("LEGITTIMITA_ID"));
		if (rs.getObject("LEG_TIT_DENOMINAZIONE") != null) result.setLegTitDenominazione(rs.getString("LEG_TIT_DENOMINAZIONE"));
		if (rs.getObject("LEG_TIT_RILASCIATO_DA") != null) result.setLegTitRilasciatoDa(rs.getString("LEG_TIT_RILASCIATO_DA"));
		if (rs.getObject("LEG_TIT_NUM_PROTOCOLLO") != null) result.setLegTitNumProtocollo(rs.getString("LEG_TIT_NUM_PROTOCOLLO"));
		if (rs.getObject("LEG_TIT_DATA_RILASCIO") != null) result.setLegTitDataRilascio(rs.getDate("LEG_TIT_DATA_RILASCIO"));
		if (rs.getObject("LEG_TIT_INTESTATARIO") != null) result.setLegTitIntestatario(rs.getString("LEG_TIT_INTESTATARIO"));
		return result;
	}
	

}
