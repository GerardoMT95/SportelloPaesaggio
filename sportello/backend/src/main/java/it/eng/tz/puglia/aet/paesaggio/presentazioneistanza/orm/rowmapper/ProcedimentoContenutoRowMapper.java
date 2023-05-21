package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.procedimento_contenuto
 */
public class ProcedimentoContenutoRowMapper implements RowMapper<ProcedimentoContenutoDTO>{

    public ProcedimentoContenutoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ProcedimentoContenutoDTO result = new ProcedimentoContenutoDTO();
        result.setTipoProcedimento(rs.getInt("tipo_procedimento"));
        result.setTipoContenutoId(rs.getInt("tipo_contenuto_id"));
        return result;
    }
}
