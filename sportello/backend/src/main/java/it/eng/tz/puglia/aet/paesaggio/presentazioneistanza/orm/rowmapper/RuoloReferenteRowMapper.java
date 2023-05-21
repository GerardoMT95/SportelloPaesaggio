package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.ruolo_referente
 */
public class RuoloReferenteRowMapper implements RowMapper<RuoloReferenteDTO>{

    public RuoloReferenteDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final RuoloReferenteDTO result = new RuoloReferenteDTO();
        result.setId(rs.getInt("id"));
        result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("titolarita") != null)
            result.setTitolarita(rs.getBoolean("titolarita"));
        if (rs.getObject("altra_titolarita") != null)
            result.setAltraTitolarita(rs.getBoolean("altra_titolarita"));
        if (rs.getObject("attiva_specifica") != null)
            result.setAttivaSpecifica(rs.getBoolean("attiva_specifica"));
        return result;
    }
}
