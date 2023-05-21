package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.allegato_corrispondenza
 */
public class AllegatoCorrispondenzaRowMapper implements RowMapper<AllegatoCorrispondenzaDTO>{

    public AllegatoCorrispondenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AllegatoCorrispondenzaDTO result = new AllegatoCorrispondenzaDTO();
        result.setIdAllegato( (java.util.UUID) rs.getObject("id_allegato"));
        result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
        return result;
    }
}
