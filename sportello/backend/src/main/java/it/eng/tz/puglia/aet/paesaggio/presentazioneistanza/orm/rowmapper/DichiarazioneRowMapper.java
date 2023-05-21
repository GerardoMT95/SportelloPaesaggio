package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.dichiarazione
 */
public class DichiarazioneRowMapper implements RowMapper<DichiarazioneDTO>{

    public DichiarazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DichiarazioneDTO result = new DichiarazioneDTO();
        result.setId(rs.getLong("id"));
        result.setTesto(rs.getString("testo"));
        result.setLabelCb(rs.getString("label_cb"));
        result.setDataInizio(rs.getDate("data_inizio"));
        result.setDataFine(rs.getDate("data_fine"));
        result.setIdProcedimento(rs.getInt("id_procedimento"));
        return result;
    }
}
