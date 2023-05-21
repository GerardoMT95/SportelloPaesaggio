package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.DocAmmVPratiche;

public class DocAmmVPraticheRowMapper implements RowMapper<DocAmmVPratiche> {

	@Override
	public DocAmmVPratiche mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocAmmVPratiche result = new DocAmmVPratiche();
		//TNO_PPTR_DOC_AMM_BLOB_ID,TNO_PPTR_TIPOPROCEDIMENTO_ID,TNO_PPTR_DOC_AMM_TIPO_ID,TIPOPROCEDIMENTO,GRUPPO,
		//LETTERA_STAMPA_LABEL,TIPO_DOCUMENTO,CARICATO_IN_ISTANZA,NOME_FILE,FORMATO_FILE,T_PRATICA_APPTR_ID,
		//BIN_CONTENT,CODICE_PRATICA_APPPTR,PROG
		if (rs.getObject("TIPO_DOCUMENTO") != null) result.setTipoDocumento(rs.getString("TIPO_DOCUMENTO"));
		if (rs.getObject("NOME_FILE") != null) result.setNomeFile(rs.getString("NOME_FILE"));
		if (rs.getObject("FORMATO_FILE") != null) result.setFormatoFile(rs.getString("FORMATO_FILE"));
		if (rs.getObject("BIN_CONTENT") != null) result.setBinContent(rs.getBlob("BIN_CONTENT"));
		return result;
	}

	
	
}

