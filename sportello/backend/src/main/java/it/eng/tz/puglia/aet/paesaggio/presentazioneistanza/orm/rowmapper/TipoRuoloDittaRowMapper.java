package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.tipo_ruolo_ditta
 */
public class TipoRuoloDittaRowMapper implements RowMapper<TipoRuoloDittaDTO>{

    public TipoRuoloDittaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TipoRuoloDittaDTO result = new TipoRuoloDittaDTO();
        result.setId(rs.getInt("id"));
        result.setNome(rs.getString("nome"));
        result.setOrder(rs.getShort("order"));
        return result;
    }
}
