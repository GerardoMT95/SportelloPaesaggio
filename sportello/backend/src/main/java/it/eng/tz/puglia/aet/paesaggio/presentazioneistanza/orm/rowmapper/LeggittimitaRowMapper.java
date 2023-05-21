package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.leggittimita
 */
public class LeggittimitaRowMapper implements RowMapper<LeggittimitaDTO>{

    public LeggittimitaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final LeggittimitaDTO result = new LeggittimitaDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("leg_urb_tit_edilizio") != null)
            result.setLegUrbTitEdilizio(rs.getString("leg_urb_tit_edilizio"));
        if (rs.getObject("leg_urb_privo_specifica") != null)
            result.setLegUrbPrivoSpecifica(rs.getString("leg_urb_privo_specifica"));
        if (rs.getObject("leg_paesag_immobile") != null)
            result.setLegPaesagImmobile(rs.getString("leg_paesag_immobile"));
        if (rs.getObject("leg_pae_tipo_vincolo") != null)
            result.setLegPaeTipoVincolo(rs.getString("leg_pae_tipo_vincolo"));
        if (rs.getObject("leg_pae_data_intervento") != null)
            result.setLegPaeDataIntervento(rs.getDate("leg_pae_data_intervento"));
        if (rs.getObject("leg_pae_data_vincolo") != null)
            result.setLegPaeDataVincolo(rs.getDate("leg_pae_data_vincolo"));
        return result;
    }
}
