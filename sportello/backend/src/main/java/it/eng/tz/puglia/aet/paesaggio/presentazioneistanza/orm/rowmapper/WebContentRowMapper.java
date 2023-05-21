package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.web_content
 */
public class WebContentRowMapper implements RowMapper<WebContentDTO>{

    public WebContentDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final WebContentDTO result = new WebContentDTO();
        result.setCodiceContenuto(rs.getString("codice_contenuto"));
        if (rs.getObject("tooltip") != null)
            result.setTooltip(rs.getString("tooltip"));
        if (rs.getObject("contenuto") != null)
            result.setContenuto(rs.getString("contenuto"));
        return result;
    }
}
