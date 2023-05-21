package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.ruolo_pratica
 */
public class RuoloPraticaRowMapper implements RowMapper<RuoloPraticaDTO>{

    public RuoloPraticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final RuoloPraticaDTO result = new RuoloPraticaDTO();
        result.setId(rs.getString("id"));
        result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("delegato") != null)
            result.setDelegato(rs.getBoolean("delegato"));
        return result;
    }
}
