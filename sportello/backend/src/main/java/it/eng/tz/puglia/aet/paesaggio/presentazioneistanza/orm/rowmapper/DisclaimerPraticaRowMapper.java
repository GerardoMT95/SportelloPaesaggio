package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.disclaimer_pratica
 */
public class DisclaimerPraticaRowMapper implements RowMapper<DisclaimerPraticaDTO>{

    public DisclaimerPraticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DisclaimerPraticaDTO result = new DisclaimerPraticaDTO();
        result.setDisclaimerId(rs.getInt("disclaimer_id"));
        if (rs.getObject("flag") != null)
            result.setFlag(rs.getString("flag"));
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        return result;
    }
}
