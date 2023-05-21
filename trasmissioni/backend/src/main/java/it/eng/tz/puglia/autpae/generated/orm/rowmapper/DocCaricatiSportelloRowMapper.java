package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table pareri.doc_caricati_sportello
 */
public class DocCaricatiSportelloRowMapper implements RowMapper<DocCaricatiSportelloDTO>{

    public DocCaricatiSportelloDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DocCaricatiSportelloDTO result = new DocCaricatiSportelloDTO();
        result.setId(rs.getLong("id"));
        result.setIdDocCaricato( (java.util.UUID) rs.getObject("id_doc_caricato"));
        result.setDateInsert(rs.getTimestamp("date_insert"));
        if (rs.getObject("user_insert") != null)
            result.setUserInsert(rs.getString("user_insert"));
        if (rs.getObject("deleted") != null)
            result.setDeleted(rs.getBoolean("deleted"));
        result.setIdFascicolo(rs.getLong("id_fascicolo"));
        if (rs.getObject("date_updated") != null)
            result.setDateUpdated(rs.getTimestamp("date_updated"));
        if (rs.getObject("user_updated") != null)
            result.setUserUpdated(rs.getString("user_updated"));
        return result;
    }
}
