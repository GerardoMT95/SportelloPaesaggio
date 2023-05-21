package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table autpae.annotazioni_interne
 */
public class AnnotazioniInterneRowMapper implements RowMapper<AnnotazioniInterneDTO>{

    public AnnotazioniInterneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AnnotazioniInterneDTO result = new AnnotazioniInterneDTO();
        result.setId(rs.getLong("id"));
        result.setIdFascicolo(rs.getLong("id_fascicolo"));
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setEsitoControllo(rs.getString("esito_controllo"));
        result.setNote(rs.getString("note"));
        result.setDateCreated(rs.getTimestamp("date_created"));
        result.setUserCreated(rs.getString("user_created"));
        if (rs.getObject("date_updated") != null)
            result.setDateUpdated(rs.getTimestamp("date_updated"));
        if (rs.getObject("user_updated") != null)
            result.setUserUpdated(rs.getString("user_updated"));
        return result;
    }
}
