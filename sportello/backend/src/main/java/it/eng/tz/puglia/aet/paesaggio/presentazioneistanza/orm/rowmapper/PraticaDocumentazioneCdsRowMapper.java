package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pratica_documentazione_cds
 */
public class PraticaDocumentazioneCdsRowMapper implements RowMapper<PraticaDocumentazioneCdsDTO>{

    public PraticaDocumentazioneCdsDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaDocumentazioneCdsDTO result = new PraticaDocumentazioneCdsDTO();
        result.setId(rs.getString("id"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        result.setIdTipo(rs.getInt("id_tipo"));
        result.setIdDocumentoCds(rs.getLong("id_documento_cds"));
        result.setUserCreate(rs.getString("user_create"));
        result.setDateCreate(rs.getTimestamp("date_create"));
        return result;
    }
}
