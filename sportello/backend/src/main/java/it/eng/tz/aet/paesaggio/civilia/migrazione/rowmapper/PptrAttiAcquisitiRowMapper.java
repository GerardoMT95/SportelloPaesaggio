package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAttiAcquisiti;

public class PptrAttiAcquisitiRowMapper implements RowMapper<PptrAttiAcquisiti> {

	@Override
	public PptrAttiAcquisiti mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrAttiAcquisiti result=new PptrAttiAcquisiti();
		//ATTO_ACQUISITO_ID,TIPOLOGIA_ATTO,AUTORITA_RILASCIO,PROTOCOLLO,
		//DATA_RILASCIO,CODICE_PRATICA_APPPTR,FLAG_ATTO_PARERE,INTESTATARIO_ATTO,PROG
		if (rs.getObject("TIPOLOGIA_ATTO") != null) result.setTipologiaAtto(rs.getString("TIPOLOGIA_ATTO"));
		if (rs.getObject("AUTORITA_RILASCIO") != null) result.setAutoritaRilascio(rs.getString("AUTORITA_RILASCIO"));
		if (rs.getObject("PROTOCOLLO") != null) result.setProtocollo(rs.getString("PROTOCOLLO"));
		if (rs.getObject("DATA_RILASCIO") != null) result.setDataRilascio(rs.getDate("DATA_RILASCIO"));
		if (rs.getObject("FLAG_ATTO_PARERE") != null) result.setFlagAttoParere(rs.getString("FLAG_ATTO_PARERE"));
		if (rs.getObject("INTESTATARIO_ATTO") != null) result.setIntestatario_atto(rs.getString("INTESTATARIO_ATTO"));
		return result;
	}

}
