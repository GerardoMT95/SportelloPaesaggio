package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.template_destinatario
 */
public class TemplateDestinatarioRowMapper implements RowMapper<TemplateDestinatarioDTO>{

    public TemplateDestinatarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TemplateDestinatarioDTO result = new TemplateDestinatarioDTO();
        result.setId(rs.getLong("id"));
        if (rs.getObject("id_organizzazione") != null)
            result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setCodiceTemplate(rs.getString("codice_template"));
        if (rs.getObject("email") != null)
            result.setEmail(rs.getString("email"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        result.setDenominazione(rs.getString("denominazione"));
        result.setTipo(rs.getString("tipo"));
        return result;
    }
}
