package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.effetti_mitigazione
 */
public class EffettiMitigazioneRowMapper implements RowMapper<EffettiMitigazioneDTO>{

    public EffettiMitigazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final EffettiMitigazioneDTO result = new EffettiMitigazioneDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("descr_stato_attuale") != null)
            result.setDescrStatoAttuale(rs.getString("descr_stato_attuale"));
        if (rs.getObject("effetti_realiz_opera") != null)
            result.setEffettiRealizOpera(rs.getString("effetti_realiz_opera"));
        if (rs.getObject("mitigazione_imp_interv") != null)
            result.setMitigazioneImpInterv(rs.getString("mitigazione_imp_interv"));
        if (rs.getObject("indicaz_contenuti_percettivi") != null)
            result.setIndicazContenutiPercettivi(rs.getString("indicaz_contenuti_percettivi"));
        return result;
    }
}
