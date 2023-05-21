package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pratica_cds_partecipanti
 */
public class PraticaCdsPartecipantiRowMapper implements RowMapper<PraticaCdsPartecipantiDTO>{

    public PraticaCdsPartecipantiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaCdsPartecipantiDTO result = new PraticaCdsPartecipantiDTO();
        result.setId(rs.getString("id"));
        result.setUsername(rs.getString("username"));
        result.setDenominazione(rs.getString("denominazione"));
        return result;
    }
}
