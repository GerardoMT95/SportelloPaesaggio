package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.rowmapper;

import java.sql.*;
import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.ApplicazioneDTO;

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
