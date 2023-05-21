/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAllegatiPptr;


/**
 * @author Adriano Colaianni
 * @date 22 apr 2021
 */
public class VtnoAllegatiPptrRowMapper implements RowMapper<VtnoAllegatiPptr>{

	@Override
	public VtnoAllegatiPptr mapRow(ResultSet rs, int rowNum) throws SQLException {
		VtnoAllegatiPptr result=new VtnoAllegatiPptr();
		if (rs.getObject("CODICE") != null) result.setCodice(rs.getString("CODICE"));
		if (rs.getObject("DATAARRIVO") != null) result.setDataArrivo(rs.getDate("DATAARRIVO"));
		if (rs.getObject("DATAPROTOCOLLO") != null) result.setDataProtocollo(rs.getDate("DATAPROTOCOLLO"));
		if (rs.getObject("DESCRIZIONE") != null) result.setDescrizione(rs.getString("DESCRIZIONE"));
		if (rs.getObject("FASE_ID") != null) result.setFaseId(rs.getString("FASE_ID"));
		if (rs.getObject("ID_ALFRESCO") != null) result.setIdAlfresco(rs.getString("ID_ALFRESCO"));
		if (rs.getObject("NAME") != null) result.setName(rs.getString("NAME"));
		if (rs.getObject("NOME") != null) result.setNome(rs.getString("NOME"));
		if (rs.getObject("NOMEALLEGATO") != null) result.setNomeAllegato(rs.getString("NOMEALLEGATO"));
		if (rs.getObject("NOMEFILE") != null) result.setNomeFile(rs.getString("NOMEFILE"));
		if (rs.getObject("N_T_KE_DOC_ST_ID") != null) result.setnTKeDocStId(rs.getString("N_T_KE_DOC_ST_ID"));
		if (rs.getObject("NUMEROPROTOCOLLO") != null) result.setNumeroProtocollo(rs.getString("NUMEROPROTOCOLLO"));
		if (rs.getObject("PPTR_TIPOALLEGATO_CODICE") != null) result.setPptrTipoAllegatoCodice(rs.getString("PPTR_TIPOALLEGATO_CODICE"));
		if (rs.getObject("PPTR_TIPOALLEGATO_DESCRIZIONE") != null) result.setPptrTipoAllegatoDescrizione(rs.getString("PPTR_TIPOALLEGATO_DESCRIZIONE"));
		if (rs.getObject("PPTR_TIPOALLEGATO_ORDINE") != null) result.setPptrTipoAllegatoOrdine(rs.getString("PPTR_TIPOALLEGATO_ORDINE"));
		if (rs.getObject("PROG") != null) result.setProg(rs.getLong("PROG"));
		if (rs.getObject("PROVV") != null) result.setProvv(rs.getString("PROVV"));
		if (rs.getObject("TIPO") != null) result.setTipo(rs.getString("TIPO"));
		if (rs.getObject("T_KE_DOC_ATTR_NAME") != null) result.settKeDocAttrName(rs.getString("T_KE_DOC_ATTR_NAME"));
		if (rs.getObject("T_KE_DOC_ATTR_VALUE") != null) result.settKeDocAttrValue(rs.getString("T_KE_DOC_ATTR_VALUE"));
		if (rs.getObject("T_PRATICA_ID") != null) result.settPraticaId(rs.getLong("T_PRATICA_ID"));
		if (rs.getObject("T_TIPODOC_CODICE") != null) result.settTipoCodice(rs.getString("T_TIPODOC_CODICE"));
		if (rs.getObject("T_TIPODOC_DESCRIZIONE") != null) result.settTipodocDescrizione(rs.getString("T_TIPODOC_DESCRIZIONE"));
		if (rs.getObject("T_TIPODOC_ID") != null) result.settTipoDocId(rs.getLong("T_TIPODOC_ID"));
		if (rs.getObject("T_TIPODOC_MODCOMPILAZIONE") != null) result.settTipodocModcompilazione(rs.getString("T_TIPODOC_MODCOMPILAZIONE"));
		if (rs.getObject("VERSION_NOTES") != null) result.setVersionNotes(rs.getString("VERSION_NOTES"));
		if (rs.getObject("BIN_CONTENT") != null) result.setBinContent(rs.getBlob("BIN_CONTENT"));
		return result;
	}
	

}
