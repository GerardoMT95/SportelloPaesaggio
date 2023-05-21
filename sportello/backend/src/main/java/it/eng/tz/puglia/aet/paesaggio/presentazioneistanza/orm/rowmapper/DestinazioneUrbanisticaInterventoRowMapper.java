package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.destinazione_urbanistica_intervento
 */
public class DestinazioneUrbanisticaInterventoRowMapper implements RowMapper<DestinazioneUrbanisticaInterventoDTO>{

    public DestinazioneUrbanisticaInterventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DestinazioneUrbanisticaInterventoDTO result = new DestinazioneUrbanisticaInterventoDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setComuneId(rs.getLong("comune_id"));
        result.setId(rs.getInt("id"));
        if (rs.getObject("strum_urb_approvato") != null)
            result.setStrumUrbApprovato(rs.getString("strum_urb_approvato"));
        if (rs.getObject("strum_urb_approvato_data") != null)
            result.setStrumUrbApprovatoData(rs.getDate("strum_urb_approvato_data"));
        if (rs.getObject("strum_urb_approvato_atto") != null)
            result.setStrumUrbApprovatoAtto(rs.getString("strum_urb_approvato_atto"));
        if (rs.getObject("destin_area_str_vig") != null)
            result.setDestinAreaStrVig(rs.getString("destin_area_str_vig"));
        if (rs.getObject("strum_approv_ult_tutele") != null)
            result.setStrumApprovUltTutele(rs.getString("strum_approv_ult_tutele"));
        if (rs.getObject("strum_urb_adottato") != null)
            result.setStrumUrbAdottato(rs.getString("strum_urb_adottato"));
        if (rs.getObject("strum_urb_adottato_data") != null)
            result.setStrumUrbAdottatoData(rs.getDate("strum_urb_adottato_data"));
        if (rs.getObject("strum_urb_adottato_atto") != null)
            result.setStrumUrbAdottatoAtto(rs.getString("strum_urb_adottato_atto"));
        if (rs.getObject("destin_area_str_adott") != null)
            result.setDestinAreaStrAdott(rs.getString("destin_area_str_adott"));
        if (rs.getObject("strum_adott_ult_tutele") != null)
            result.setStrumAdottUltTutele(rs.getString("strum_adott_ult_tutele"));
        if (rs.getObject("conforme_discipl_urb_vigente") != null)
            result.setConformeDisciplUrbVigente(rs.getString("conforme_discipl_urb_vigente"));
        if (rs.getObject("check_presa_visione") != null)
            result.setCheckPresaVisione(rs.getString("check_presa_visione"));
        if (rs.getObject("id_strum_urban_art38") != null)
            result.setIdStrumUrbanArt38(rs.getLong("id_strum_urban_art38"));
        if (rs.getObject("id_strum_urban_art100") != null)
            result.setIdStrumUrbanArt100(rs.getLong("id_strum_urban_art100"));
        return result;
    }
}
