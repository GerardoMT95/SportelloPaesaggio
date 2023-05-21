/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProtocolloUscita;

/**
 * @author Adriano Colaianni
 * @date 22 apr 2021
 */
public class PptrProtocolloUscitaRowMapper implements RowMapper<PptrProtocolloUscita>{

	@Override
	public PptrProtocolloUscita mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrProtocolloUscita result=new PptrProtocolloUscita();
		if (rs.getObject("ALGORITMO") != null) result.setAlgoritmo(rs.getString("ALGORITMO"));
		if (rs.getObject("BIN_PDF_CONTENT") != null) result.setBinPdfContent(rs.getBlob("BIN_PDF_CONTENT"));
		if (rs.getObject("BIN_PDFPROT_CONTENT") != null) result.setBinPdfProtContent(rs.getBlob("BIN_PDFPROT_CONTENT"));
		if (rs.getObject("CODICE_PRATICA_APPPTR") != null) result.setCodicePraticaApptr(rs.getString("CODICE_PRATICA_APPPTR"));
		if (rs.getObject("CODIFICA") != null) result.setCodifica(rs.getString("CODIFICA"));
		if (rs.getObject("DATA_PROTOCOLLO") != null) result.setDataprotocollo(rs.getDate("DATA_PROTOCOLLO"));
		if (rs.getObject("DOC_SEGNATO") != null) result.setDocSegnato(rs.getString("DOC_SEGNATO"));
		if (rs.getObject("ESITO_PROTOCOLLAZIONE") != null) result.setEsitoProtocollazione(rs.getString("ESITO_PROTOCOLLAZIONE"));
		if (rs.getObject("TNO_PPTR_PROTOCOLLO_USCITA_ID") != null) result.setId(rs.getLong("TNO_PPTR_PROTOCOLLO_USCITA_ID"));
		if (rs.getObject("IMPRONTA") != null) result.setImpronta(rs.getString("IMPRONTA"));
		if (rs.getObject("MOTIVO_ERRATA_PROTOCOLLAZIONE") != null) result.setMotivoErrataProtocollazione(rs.getString("MOTIVO_ERRATA_PROTOCOLLAZIONE"));
		if (rs.getObject("NOTE") != null) result.setNote(rs.getString("NOTE"));
		if (rs.getObject("NUMERO_PROTOCOLLO") != null) result.setNumeroProtocollo(rs.getString("NUMERO_PROTOCOLLO"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("SEGNATURA_BLOB") != null) result.setSegnaturaBlob(rs.getBlob("SEGNATURA_BLOB"));
		if (rs.getObject("TITOLARIO_PROTOCOLLO") != null) result.setTitolarioProtocollo(rs.getString("TITOLARIO_PROTOCOLLO"));
		if (rs.getObject("TNO_PPTR_TRASMISSIONE_ID") != null) result.setTnoPptrTrasmissioneId(rs.getLong("TNO_PPTR_TRASMISSIONE_ID"));
		if (rs.getObject("T_PRATICA_APPTR_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_APPTR_ID"));
		return result;
	}
	
	

}
