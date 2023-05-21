package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pratica_owner_history
 */
public class PraticaOwnerHistoryRowMapper implements RowMapper<PraticaOwnerHistoryDTO>{

    public PraticaOwnerHistoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaOwnerHistoryDTO result = new PraticaOwnerHistoryDTO();
        result.setId( (java.util.UUID) rs.getObject("id"));
        result.setUsername(rs.getString("username"));
        result.setCodiceSegretoUtilizzato(rs.getString("codice_segreto_utilizzato"));
        result.setCreateDate(rs.getTimestamp("create_date"));
        result.setCmisIdDelega(rs.getString("cmis_id_delega"));
        result.setFileNameDelega(rs.getString("file_name_delega"));
        if (rs.getObject("cmis_id_sollevamento_incarico") != null)
            result.setCmisIdSollevamentoIncarico(rs.getString("cmis_id_sollevamento_incarico"));
        if (rs.getObject("file_name_sollevamento_incarico") != null)
            result.setFileNameSollevamentoIncarico(rs.getString("file_name_sollevamento_incarico"));
        return result;
    }
}
