package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.rup
 */
public class RupRowMapper implements RowMapper<RupDTO>{

    public RupDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final RupDTO result = new RupDTO();
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setUsername(rs.getString("username"));
        result.setDenominazione(rs.getString("denominazione"));
        if (rs.getObject("data_scadenza") != null)
            result.setDataScadenza(rs.getDate("data_scadenza"));
        return result;
    }
}
