package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table autpae.fascicolo_tipi_procedimento
 */
public class FascicoloTipiProcedimentoRowMapper implements RowMapper<FascicoloTipiProcedimentoDTO>{

    public FascicoloTipiProcedimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final FascicoloTipiProcedimentoDTO result = new FascicoloTipiProcedimentoDTO();
        result.setId(rs.getLong("id"));
        result.setIdFascicolo(rs.getLong("id_fascicolo"));
        result.setCodiceTipoProcedimento(rs.getString("codice_tipo_procedimento"));
        result.setInizioValidita(rs.getDate("inizio_validita"));
        result.setFineValidita(rs.getDate("fine_validita"));
        return result;
    }
}
