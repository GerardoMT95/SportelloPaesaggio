package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table common.applicazione
 */
public class ApplicazioneRowMapper implements RowMapper<ApplicazioneDTO>{

    public ApplicazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ApplicazioneDTO result = new ApplicazioneDTO();
        result.setId(rs.getInt("id"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("codice") != null)
            result.setCodice(rs.getString("codice"));
        return result;
    }
}
