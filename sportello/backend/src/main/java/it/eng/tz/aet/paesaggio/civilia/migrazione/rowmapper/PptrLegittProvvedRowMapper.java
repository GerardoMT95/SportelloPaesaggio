package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittProvved;

public class PptrLegittProvvedRowMapper implements RowMapper<PptrLegittProvved>{

	@Override
	public PptrLegittProvved mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrLegittProvved result = new PptrLegittProvved();
		//LEGITT_PROVVED_ID,LEGITTIMITA_ID,LEG_PROVV_DENOMINAZIONE,LEG_PROVV_RILASCIATO_DA,LEG_PROVV_NUM_PROTOCOLLO,
		//LEG_PROVV_DATA_RILASCIO,LEG_PROVV_INTESTATARIO,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("LEGITT_PROVVED_ID") != null) result.setLegittProvvedId(rs.getLong("LEGITT_PROVVED_ID"));
		if (rs.getObject("LEGITTIMITA_ID") != null) result.setLegittimitaId(rs.getLong("LEGITTIMITA_ID"));
		if (rs.getObject("LEG_PROVV_DENOMINAZIONE") != null) result.setLegProvvDenominazione(rs.getString("LEG_PROVV_DENOMINAZIONE"));
		if (rs.getObject("LEG_PROVV_RILASCIATO_DA") != null) result.setLegProvvRilasciatoDa(rs.getString("LEG_PROVV_RILASCIATO_DA"));
		if (rs.getObject("LEG_PROVV_NUM_PROTOCOLLO") != null) result.setLegProvvNumProtocollo(rs.getString("LEG_PROVV_NUM_PROTOCOLLO"));
		if (rs.getObject("LEG_PROVV_DATA_RILASCIO") != null) result.setLegProvvDataRilascio(rs.getDate("LEG_PROVV_DATA_RILASCIO"));
		if (rs.getObject("LEG_PROVV_INTESTATARIO") != null) result.setLegProvvIntestatario(rs.getString("LEG_PROVV_INTESTATARIO"));
		return result;
	}

}
