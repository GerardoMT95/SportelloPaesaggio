package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTitolarita;

public class PptrTitolaritaRowMapper implements RowMapper<PptrTitolarita>{

	@Override
	public PptrTitolarita mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrTitolarita result=new PptrTitolarita();
		//TNO_PPTR_TITOLARITA_ID, TIT_RUOLO_ID, TIT_RUOLO_ALTRO, TIT_ESCLUSIVO, FILE_DICH_ASSENSO, REFERENTE_PROGETTO_ID, 
		//CODICE_PRATICA_APPPTR, NOME_FILE, DATA_CARICAMENTO_FILE, FORMATO_FILE, PROG 
		if (rs.getObject("TIT_RUOLO_ID") != null) result.setTitRuoloId(rs.getLong("TIT_RUOLO_ID"));
		if (rs.getObject("TIT_RUOLO_ALTRO") != null) result.setTitRuoloAltro(rs.getString("TIT_RUOLO_ALTRO"));
		if (rs.getObject("TIT_ESCLUSIVO") != null) result.setTitEsclusivo(rs.getString("TIT_ESCLUSIVO"));
		if (rs.getObject("FILE_DICH_ASSENSO") != null) result.setFileDichAssenso(rs.getBlob("FILE_DICH_ASSENSO"));
		if (rs.getObject("REFERENTE_PROGETTO_ID") != null) result.setReferenteProgettoId(rs.getLong("REFERENTE_PROGETTO_ID"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaAppptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("NOME_FILE") != null) result.setNomeFile(rs.getString("NOME_FILE"));
		if (rs.getObject("DATA_CARICAMENTO_FILE") != null) result.setDataCaricamentoFile(rs.getDate("DATA_CARICAMENTO_FILE"));
		if (rs.getObject("FORMATO_FILE") != null) result.setFormatoFile(rs.getString("FORMATO_FILE"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		return result;
	}

}
