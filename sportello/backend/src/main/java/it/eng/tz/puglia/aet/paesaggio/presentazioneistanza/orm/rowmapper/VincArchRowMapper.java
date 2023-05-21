package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.vinc_arch
 */
public class VincArchRowMapper implements RowMapper<VincArchDTO>{

    public VincArchDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final VincArchDTO result = new VincArchDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("vinc_arc_no_tutela") != null)
            result.setVincArcNoTutela(rs.getString("vinc_arc_no_tutela"));
        if (rs.getObject("vinc_arc_monum_diretto") != null)
            result.setVincArcMonumDiretto(rs.getString("vinc_arc_monum_diretto"));
        if (rs.getObject("vinc_arc_monum_indiretto") != null)
            result.setVincArcMonumIndiretto(rs.getString("vinc_arc_monum_indiretto"));
        if (rs.getObject("vinc_arc_archeo_diretto") != null)
            result.setVincArcArcheoDiretto(rs.getString("vinc_arc_archeo_diretto"));
        if (rs.getObject("vinc_arc_archeo_indiretto") != null)
            result.setVincArcArcheoIndiretto(rs.getString("vinc_arc_archeo_indiretto"));
        if (rs.getObject("altri_vincoli") != null)
            result.setAltriVincoli(rs.getString("altri_vincoli"));
        return result;
    }
}
