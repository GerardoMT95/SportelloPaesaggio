package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.util.UUID;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.referenti_documento
 */
public class ReferentiDocumentoRowMapper implements RowMapper<ReferentiDocumentoDTO>{

    public ReferentiDocumentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ReferentiDocumentoDTO result = new ReferentiDocumentoDTO();
        result.setReferenteId(rs.getLong("referente_id"));
        if	(rs.getObject("id_tipo") != null)
        	result.setIdTipo(rs.getInt("id_tipo"));
        if (rs.getObject("numero") != null)
            result.setNumero(rs.getString("numero"));
        if (rs.getObject("data_rilascio") != null)
            result.setDataRilascio(rs.getDate("data_rilascio"));
        if (rs.getObject("ente_rilascio") != null)
            result.setEnteRilascio(rs.getString("ente_rilascio"));
        if (rs.getObject("data_scadenza") != null)
            result.setDataScadenza(rs.getDate("data_scadenza"));
        if (rs.getObject("allegato_id") != null)
            result.setIdAllegato((UUID)rs.getObject("allegato_id"));
        
        return result;
    }
}
