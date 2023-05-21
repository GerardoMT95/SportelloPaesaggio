package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.inquadramento
 */
public class InquadramentoRowMapper implements RowMapper<InquadramentoDTO>{

    public InquadramentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final InquadramentoDTO result = new InquadramentoDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("inq_dest_uso_interv") != null)
            result.setInqDestUsoInterv(rs.getString("inq_dest_uso_interv"));
        if (rs.getObject("inq_dest_uso_interv_altro") != null)
            result.setInqDestUsoIntervAltro(rs.getString("inq_dest_uso_interv_altro"));
        if (rs.getObject("inq_dest_uso_suolo") != null)
            result.setInqDestUsoSuolo(rs.getString("inq_dest_uso_suolo"));
        if (rs.getObject("inq_dest_uso_suolo_altro") != null)
            result.setInqDestUsoSuoloAltro(rs.getString("inq_dest_uso_suolo_altro"));
        if (rs.getObject("inq_contesto_paesag") != null)
            result.setInqContestoPaesag(rs.getString("inq_contesto_paesag"));
        if (rs.getObject("inq_contesto_paesag_altro") != null)
            result.setInqContestoPaesagAltro(rs.getString("inq_contesto_paesag_altro"));
        if (rs.getObject("inq_morfologia_paesag") != null)
            result.setInqMorfologiaPaesag(rs.getString("inq_morfologia_paesag"));
        if (rs.getObject("inq_morfologia_paesag_altro") != null)
            result.setInqMorfologiaPaesagAltro(rs.getString("inq_morfologia_paesag_altro"));
        return result;
    }
}
