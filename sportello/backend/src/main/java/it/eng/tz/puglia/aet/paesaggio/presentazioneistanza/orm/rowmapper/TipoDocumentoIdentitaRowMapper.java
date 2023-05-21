package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.tipo_documento_identita
 */
public class TipoDocumentoIdentitaRowMapper implements RowMapper<TipoDocumentoIdentitaDTO>{

    public TipoDocumentoIdentitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TipoDocumentoIdentitaDTO result = new TipoDocumentoIdentitaDTO();
        result.setId(rs.getInt("id"));
        result.setNome(rs.getString("nome"));
        result.setOrder(rs.getShort("order"));
        return result;
    }
}
