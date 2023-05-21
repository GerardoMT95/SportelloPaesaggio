package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.procedimenti_contenzioso
 */
public class ProcedimentiContenziosoRowMapper implements RowMapper<ProcedimentiContenziosoDTO>{

    public ProcedimentiContenziosoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ProcedimentiContenziosoDTO result = new ProcedimentiContenziosoDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("flag_contenzioso_in_atto") != null)
            result.setFlagContenziosoInAtto(rs.getString("flag_contenzioso_in_atto"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        return result;
    }
}
