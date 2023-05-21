package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.disclaimer
 */
public class DisclaimerRowMapper implements RowMapper<DisclaimerDTO>{

    public DisclaimerDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DisclaimerDTO result = new DisclaimerDTO();
        result.setId(rs.getInt("id"));
        result.setTesto(rs.getString("testo"));
        result.setTipoProcedimento(rs.getInt("tipo_procedimento"));
        if (rs.getObject("tipo_referente") != null)
            result.setTipoReferente(rs.getString("tipo_referente"));
        result.setDataInizio(rs.getDate("data_inizio"));
        result.setDataFine(rs.getDate("data_fine"));
        result.setUserInserted(rs.getString("user_inserted"));
        result.setUserUpdated(rs.getString("user_updated"));
        result.setOrdine(rs.getInt("ordine"));
        result.setRequired(rs.getBoolean("required"));
        return result;
    }
}
