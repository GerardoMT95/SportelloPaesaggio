package it.eng.tz.aet.paesaggio.civilia.migrazione.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrRelazioneEnte;

public class PptrRelazioneEnteRowMapper implements RowMapper<PptrRelazioneEnte>{

	@Override
	public PptrRelazioneEnte mapRow(ResultSet rs, int rowNum) throws SQLException {
		PptrRelazioneEnte result=new PptrRelazioneEnte();
		//TNO_PPTR_RELAZIONE_ENTE_ID,CODICE_PRATICA_APPPTR,T_PRATICA_APPTR_ID,PROG,NUMERO_PROTOCOLLO_ENTE,FLAG_ESITO,
		//DETTAGLIO_RELAZIONE,NOTE_ENTE,NOME_ISTRUTTORE_ENTE
		if (rs.getObject("NUMERO_PROTOCOLLO_ENTE") != null) result.setNumeroProtocolloEnte(rs.getString("NUMERO_PROTOCOLLO_ENTE"));
		if (rs.getObject("FLAG_ESITO") != null) result.setFlagEsito(rs.getString("FLAG_ESITO"));
		if (rs.getObject("DETTAGLIO_RELAZIONE") != null) result.setDettaglioRelazione(rs.getString("DETTAGLIO_RELAZIONE"));
		if (rs.getObject("NOTE_ENTE") != null) result.setNoteEnte(rs.getString("NOTE_ENTE"));
		if (rs.getObject("NOME_ISTRUTTORE_ENTE") != null) result.setNomeIstruttoreEnte(rs.getString("NOME_ISTRUTTORE_ENTE"));
		return result;
	}

}
