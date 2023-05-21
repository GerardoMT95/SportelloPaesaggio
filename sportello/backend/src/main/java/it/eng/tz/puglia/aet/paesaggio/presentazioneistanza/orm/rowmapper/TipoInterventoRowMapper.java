package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.tipo_intervento
 */
public class TipoInterventoRowMapper implements RowMapper<TipoInterventoDTO>{

    public TipoInterventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TipoInterventoDTO result = new TipoInterventoDTO();
        result.setCodice(rs.getString("codice"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        if (rs.getObject("artt") != null)
            result.setArtt(rs.getString("artt"));
        if (rs.getObject("data_approvazione") != null)
            result.setDataApprovazione(rs.getDate("data_approvazione"));
        if (rs.getObject("con") != null)
            result.setCon(rs.getString("con"));
        return result;
    }
}
