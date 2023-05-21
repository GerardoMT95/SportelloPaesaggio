package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.template_doc_sezioni
 */
public class TemplateDocSezioniRowMapper implements RowMapper<TemplateDocSezioniDTO>{

    public TemplateDocSezioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TemplateDocSezioniDTO result = new TemplateDocSezioniDTO();
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setNome(rs.getString("nome"));
        result.setTemplateDocNome(rs.getString("template_doc_nome"));
        if (rs.getObject("valore") != null)
            result.setValore(rs.getString("valore"));
        if (rs.getObject("id_allegato") != null)
            result.setIdAllegato( (java.util.UUID) rs.getObject("id_allegato"));
        if (rs.getObject("placeholders") != null)
            result.setPlaceholders(rs.getString("placeholders"));
        result.setTipoSezione(rs.getString("tipo_sezione"));
        return result;
    }
}
