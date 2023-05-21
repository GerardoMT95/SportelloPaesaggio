package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.rowmapper;

import java.sql.*;
import java.math.*;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;


/**
 * Row Mapper for table common.utente
 */
public class UtenteRowMapper implements RowMapper<UtenteDTO>{

    public UtenteDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final UtenteDTO result = new UtenteDTO();
        result.setId(rs.getInt("id"));
        result.setUsername(rs.getString("username"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("cognome") != null)
            result.setCognome(rs.getString("cognome"));
        if (rs.getObject("cf") != null)
            result.setCf(rs.getString("cf"));
        if (rs.getObject("data_richiesta_registrazione") != null)
            result.setDataRichiestaRegistrazione(rs.getTimestamp("data_richiesta_registrazione"));
        if (rs.getObject("data_conferma_registrazione") != null)
            result.setDataConfermaRegistrazione(rs.getTimestamp("data_conferma_registrazione"));
        if (rs.getObject("id_stato_registrazione") != null)
            result.setIdStatoRegistrazione(rs.getInt("id_stato_registrazione"));
        if (rs.getObject("note") != null)
            result.setNote(rs.getString("note"));
        return result;
    }
}
