package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.NumeroPraticaDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.numero_pratica
 */
public class NumeroPraticaRowMapper implements RowMapper<NumeroPraticaDTO>{

    public NumeroPraticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final NumeroPraticaDTO result = new NumeroPraticaDTO();
        result.setId(rs.getInt("id"));
        if (rs.getObject("anno") != null)
            result.setAnno(rs.getLong("anno"));
        if (rs.getObject("numero") != null)
            result.setNumero(rs.getLong("numero"));
        if (rs.getObject("next_numero") != null)
            result.setNextNumero(rs.getLong("next_numero"));
        if (rs.getObject("numero_pratica") != null)
            result.setNumeroPratica(rs.getString("numero_pratica"));
        return result;
    }
}
