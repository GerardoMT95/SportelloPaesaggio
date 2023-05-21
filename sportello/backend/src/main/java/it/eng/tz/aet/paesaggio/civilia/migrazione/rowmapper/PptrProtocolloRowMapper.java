package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocollo;

public class PptrProtocolloRowMapper implements RowMapper<PptrProtocollo>{

	@Override
	public PptrProtocollo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrProtocollo result =new PptrProtocollo();
		//TNO_PPTR_PROTOCOLLO_ID,VERSO,TNO_PPTR_TIPOPROTOCOLLO_ID,CODICE_TIPOPROTOCOLLO,CODICE_PRATICA_APPPTR,
		//CODICE_PRATICA_ENTEDELEGATO,T_PRATICA_ID_APPPTR,T_PRATICA_ID_ENTEDELEGATO,BIN,BIN_TIMBRATO,T_KE_DOC_ST_ID,
		//ESITO_PROTOCOLLAZIONE,MOTIVO_ERRATA_PROTOCOLLAZIONE,NUMERO_PROTOCOLLO,DATA_PROTOCOLLO,TITOLARIO_PROTOCOLLO,
		//DOC_SEGNATO,ALGORITMO,CODIFICA,IMPRONTA,NOTE,SEGNATURA_BLOB,BIN_PDF_CONTENT,BIN_PDFPROT_CONTENT,PROG
		if (rs.getObject("ESITO_PROTOCOLLAZIONE") != null) result.setEsitoProtocollazione(rs.getString("ESITO_PROTOCOLLAZIONE"));
		if (rs.getObject("NUMERO_PROTOCOLLO") != null) result.setNumeroProtocollo(rs.getString("NUMERO_PROTOCOLLO"));
		if (rs.getObject("DATA_PROTOCOLLO") != null) result.setDataprotocollo(rs.getDate("DATA_PROTOCOLLO"));
		if (rs.getObject("TITOLARIO_PROTOCOLLO") != null) result.setTitolarioProtocollo(rs.getString("TITOLARIO_PROTOCOLLO"));
		if (rs.getObject("BIN_PDF_CONTENT") != null) result.setBinPdfContent(rs.getBlob("BIN_PDF_CONTENT"));
		if (rs.getObject("BIN_PDFPROT_CONTENT") != null) result.setBinPdfProtContent(rs.getBlob("BIN_PDFPROT_CONTENT"));
		return result;
	}

	
}
