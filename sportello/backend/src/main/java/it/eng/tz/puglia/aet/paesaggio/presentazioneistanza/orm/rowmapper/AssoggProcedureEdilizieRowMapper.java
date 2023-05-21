package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.assogg_procedure_edilizie
 */
public class AssoggProcedureEdilizieRowMapper implements RowMapper<AssoggProcedureEdilizieDTO>{

    public AssoggProcedureEdilizieDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AssoggProcedureEdilizieDTO result = new AssoggProcedureEdilizieDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setFlagAssoggettato(rs.getString("flag_assoggettato"));
        if (rs.getObject("no_assogg_specificare") != null)
            result.setNoAssoggSpecificare(rs.getString("no_assogg_specificare"));
        if (rs.getObject("assogg_flag_pratica_in_corso") != null)
            result.setAssoggFlagPraticaInCorso(rs.getString("assogg_flag_pratica_in_corso"));
        if (rs.getObject("assogg_ente_pratica_in_corso") != null)
            result.setAssoggEntePraticaInCorso(rs.getString("assogg_ente_pratica_in_corso"));
        if (rs.getObject("assogg_datapr_pratica_in_corso") != null)
            result.setAssoggDataprPraticaInCorso(rs.getDate("assogg_datapr_pratica_in_corso"));
        if (rs.getObject("assogg_flag_parere_urb_espr") != null)
            result.setAssoggFlagParereUrbEspr(rs.getString("assogg_flag_parere_urb_espr"));
        if (rs.getObject("assogg_data_approv_pratica") != null)
            result.setAssoggDataApprovPratica(rs.getDate("assogg_data_approv_pratica"));
        return result;
    }
}
