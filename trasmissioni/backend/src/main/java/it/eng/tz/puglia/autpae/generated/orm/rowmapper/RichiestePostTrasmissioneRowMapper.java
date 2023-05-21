package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table autpae.richieste_post_trasmissione
 */
public class RichiestePostTrasmissioneRowMapper implements RowMapper<RichiestePostTrasmissioneDTO>{

    public RichiestePostTrasmissioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final RichiestePostTrasmissioneDTO result = new RichiestePostTrasmissioneDTO();
        result.setId(rs.getLong("id"));
        result.setIdFascicolo(rs.getLong("id_fascicolo"));
        result.setMotivazione(rs.getString("motivazione"));
        result.setStato(rs.getString("stato"));
        result.setTipo(rs.getString("tipo"));
        result.setDateCreated(rs.getTimestamp("date_created"));
        result.setUserCreated(rs.getString("user_created"));
        if (rs.getObject("date_updated") != null)
            result.setDateUpdated(rs.getTimestamp("date_updated"));
        if (rs.getObject("user_updated") != null)
            result.setUserUpdated(rs.getString("user_updated"));
        if (rs.getObject("id_corrispondenza") != null)
            result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
        return result;
    }
}
