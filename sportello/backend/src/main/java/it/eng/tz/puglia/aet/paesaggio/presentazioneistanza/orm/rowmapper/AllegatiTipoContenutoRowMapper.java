package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.allegati_tipo_contenuto
 */
public class AllegatiTipoContenutoRowMapper implements RowMapper<AllegatiTipoContenutoDTO>{

    public AllegatiTipoContenutoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AllegatiTipoContenutoDTO result = new AllegatiTipoContenutoDTO();
        result.setAllegatiId( (java.util.UUID) rs.getObject("allegati_id"));
        result.setTipoContenutoId(rs.getInt("tipo_contenuto_id"));
        return result;
    }
}
