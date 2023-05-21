package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiDoc;

public class PptrReferentiDocRowMapper implements RowMapper<PptrReferentiDoc>{

	@Override
	public PptrReferentiDoc mapRow(ResultSet rs, int rowNum) throws SQLException {
		//NOME_FILE, FORMATO_FILE, CONTENUTO_FILE, DATA_CARICAMENTO_FILE, TIPO_DOC_IDENT_ID, NUM_DOC_IDENT, DATA_DOC_IDENT,
		//ENTE_RIL_DOC_IDENT
		PptrReferentiDoc result=new PptrReferentiDoc();
		if (rs.getObject("NOME_FILE") != null) result.setNomeFile(rs.getString("NOME_FILE"));
		if (rs.getObject("FORMATO_FILE") != null) result.setFormatoFile(rs.getString("FORMATO_FILE"));
		if (rs.getObject("CONTENUTO_FILE") != null) result.setContenutoFile(rs.getBlob("CONTENUTO_FILE"));
		if (rs.getObject("DATA_CARICAMENTO_FILE") != null) result.setDataCaricamentoFile(rs.getDate("DATA_CARICAMENTO_FILE"));
		if (rs.getObject("TIPO_DOC_IDENT_ID") != null) result.setTipoDocIdentId(rs.getLong("TIPO_DOC_IDENT_ID"));
		if (rs.getObject("NUM_DOC_IDENT") != null) result.setNumDocIdent(rs.getString("NUM_DOC_IDENT"));
		if (rs.getObject("DATA_DOC_IDENT") != null) result.setDataDocIdent(rs.getDate("DATA_DOC_IDENT"));
		if (rs.getObject("ENTE_RIL_DOC_IDENT") != null) result.setEnteRilDocIdent(rs.getString("ENTE_RIL_DOC_IDENT"));
		return result;
	}

}
