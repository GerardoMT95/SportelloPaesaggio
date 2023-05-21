package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.generated.orm.dto.ConfigurazioneDTO;

/**
 * Row Mapper for table configurazione
 */
public class ConfigurazioneRowMapper implements RowMapper<ConfigurazioneDTO>{

    public ConfigurazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ConfigurazioneDTO result = new ConfigurazioneDTO();
        result.setId(rs.getLong("id"));
        result.setChiave(rs.getString("chiave"));
        result.setValore(rs.getString("valore"));
        result.setStartDate(rs.getTimestamp("start_date"));
        if (rs.getObject("end_date") != null)
            result.setEndDate(rs.getTimestamp("end_date"));
        if (rs.getObject("user_create") != null)
            result.setUserCreate(rs.getString("user_create"));
        if (rs.getObject("user_update") != null)
            result.setUserUpdate(rs.getString("user_update"));
        return result;
    }
}
