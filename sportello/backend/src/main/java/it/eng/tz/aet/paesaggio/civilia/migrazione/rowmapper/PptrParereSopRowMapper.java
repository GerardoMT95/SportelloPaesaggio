package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParereSop;

public class PptrParereSopRowMapper implements RowMapper<PptrParereSop>{

	@Override
	public PptrParereSop mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrParereSop result=new PptrParereSop();
		//TNO_PPTR_PARERE_SOP_ID,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG,
		//NUMERO_PROTOCOLLO_SOP,FLAG_ESITO,DETTAGLIO_PRESCRIZIONI,NOTE_PARERE_SOP,NOME_ISTRUTTORE_SOP,FLAG_PROVENIENZA,
		//ED_PARERE_SOTTOMESSO
		if (rs.getObject("TNO_PPTR_PARERE_SOP_ID") != null) result.setId(rs.getLong("TNO_PPTR_PARERE_SOP_ID"));
		if (rs.getObject("NUMERO_PROTOCOLLO_SOP") != null) result.setNumeroProtocolloSop(rs.getString("NUMERO_PROTOCOLLO_SOP"));
		if (rs.getObject("FLAG_ESITO") != null) result.setFlagEsito(rs.getString("FLAG_ESITO"));
		if (rs.getObject("DETTAGLIO_PRESCRIZIONI") != null) result.setDettaglioPrescrizioni(rs.getString("DETTAGLIO_PRESCRIZIONI"));
		if (rs.getObject("NOTE_PARERE_SOP") != null) result.setNoteParereSop(rs.getString("NOTE_PARERE_SOP"));
		if (rs.getObject("FLAG_PROVENIENZA") != null) result.setFlag_provenienza(rs.getLong("FLAG_PROVENIENZA"));
		if (rs.getObject("ED_PARERE_SOTTOMESSO") != null) result.seteD_ParereSottomesso(rs.getString("ED_PARERE_SOTTOMESSO"));
		
		return result;
	}
	

}
