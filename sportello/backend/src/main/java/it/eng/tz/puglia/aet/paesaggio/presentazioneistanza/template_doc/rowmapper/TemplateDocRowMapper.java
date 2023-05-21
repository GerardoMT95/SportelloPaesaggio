package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.template_doc
 */
public class TemplateDocRowMapper implements RowMapper<TemplateDocDTO>{

    public TemplateDocDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TemplateDocDTO result = new TemplateDocDTO();
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setNome(rs.getString("nome"));
        result.setDescrizione(rs.getString("descrizione"));
        return result;
    }
}
