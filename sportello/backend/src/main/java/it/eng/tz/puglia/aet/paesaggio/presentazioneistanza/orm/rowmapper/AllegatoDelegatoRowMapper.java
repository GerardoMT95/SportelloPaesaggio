package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.allegato_delegato
 */
public class AllegatoDelegatoRowMapper implements RowMapper<AllegatoDelegatoDTO>{

    public AllegatoDelegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AllegatoDelegatoDTO result = new AllegatoDelegatoDTO();
        result.setIdAllegato( (java.util.UUID) rs.getObject("id_allegato"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        result.setIndiceDelegato(rs.getInt("indice_delegato"));
        return result;
    }
}
