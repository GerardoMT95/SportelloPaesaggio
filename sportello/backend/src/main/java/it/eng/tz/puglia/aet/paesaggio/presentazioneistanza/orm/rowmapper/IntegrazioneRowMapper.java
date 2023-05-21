package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;

/**
 * Row Mapper for table presentazione_istanza.integrazione
 */
public class IntegrazioneRowMapper implements RowMapper<IntegrazioneDTO>{

    public IntegrazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final IntegrazioneDTO result = new IntegrazioneDTO();
        result.setId(rs.getInt("id"));
        if (rs.getObject("data_creazione") != null)
            result.setDataCreazione(rs.getTimestamp("data_creazione"));
        if (rs.getObject("data_caricamento") != null)
            result.setDataCaricamento(rs.getTimestamp("data_caricamento"));
        result.setStato(StatoIntegrazioneDocumentale.valueOf(rs.getString("stato")));
        if (rs.getObject("richiesta_integrazione") != null)
            result.setRichiestaIntegrazione(rs.getBoolean("richiesta_integrazione"));
        if (rs.getObject("username_utente_creazione") != null)
            result.setUsernameUtenteCreazione(rs.getString("username_utente_creazione"));
        result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("note") != null)
            result.setNote(rs.getString("note"));
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        return result;
    }
}
