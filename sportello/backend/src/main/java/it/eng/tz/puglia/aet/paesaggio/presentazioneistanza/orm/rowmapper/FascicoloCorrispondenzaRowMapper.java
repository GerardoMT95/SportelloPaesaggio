package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.fascicolo_corrispondenza
 */
public class FascicoloCorrispondenzaRowMapper implements RowMapper<FascicoloCorrispondenzaDTO>{

    public FascicoloCorrispondenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final FascicoloCorrispondenzaDTO result = new FascicoloCorrispondenzaDTO();
        result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        return result;
    }
}
