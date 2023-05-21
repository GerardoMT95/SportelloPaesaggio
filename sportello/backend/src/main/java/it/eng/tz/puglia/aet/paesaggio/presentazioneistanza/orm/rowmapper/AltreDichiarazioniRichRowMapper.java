package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.altre_dichiarazioni_rich
 */
public class AltreDichiarazioniRichRowMapper implements RowMapper<AltreDichiarazioniRichDTO>{

    public AltreDichiarazioniRichDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AltreDichiarazioniRichDTO result = new AltreDichiarazioniRichDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("check_136") != null)
            result.setCheck136(rs.getString("check_136"));
        if (rs.getObject("check_142") != null)
            result.setCheck142(rs.getString("check_142"));
        if (rs.getObject("check_134") != null)
            result.setCheck134(rs.getString("check_134"));
        if (rs.getObject("check_136a") != null)
            result.setCheck136a(rs.getString("check_136a"));
        if (rs.getObject("check_136b") != null)
            result.setCheck136b(rs.getString("check_136b"));
        if (rs.getObject("check_136c") != null)
            result.setCheck136c(rs.getString("check_136c"));
        if (rs.getObject("check_136d") != null)
            result.setCheck136d(rs.getString("check_136d"));
        if (rs.getObject("ente_rilascio") != null)
            result.setEnteRilascio(rs.getString("ente_rilascio"));
        if (rs.getObject("descr_aut_rilasciata") != null)
            result.setDescrAutRilasciata(rs.getString("descr_aut_rilasciata"));
        if (rs.getObject("data_rilascio") != null)
            result.setDataRilascio(rs.getDate("data_rilascio"));
        if (rs.getObject("is_caso_variante") != null)
            result.setIsCasoVariante(rs.getBoolean("is_caso_variante"));
        if (rs.getObject("check_142_parchi") != null)
            result.setCheck142Parchi(rs.getString("check_142_parchi"));
        return result;
    }
}
