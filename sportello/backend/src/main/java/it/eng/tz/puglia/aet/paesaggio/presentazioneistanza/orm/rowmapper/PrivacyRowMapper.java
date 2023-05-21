package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.privacy
 */
public class PrivacyRowMapper implements RowMapper<PrivacyDTO>{

    public PrivacyDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PrivacyDTO result = new PrivacyDTO();
        result.setId(rs.getInt("id"));
        result.setTesto(rs.getString("testo"));
        result.setDataInizio(rs.getDate("data_inizio"));
        result.setDataFine(rs.getDate("data_fine"));
        result.setUserInserted(rs.getString("user_inserted"));
        result.setUserUpdated(rs.getString("user_updated"));
        return result;
    }
}
