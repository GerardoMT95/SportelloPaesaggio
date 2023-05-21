package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.procedimento_qualificazioni
 */
public class ProcedimentoQualificazioniRowMapper implements RowMapper<ProcedimentoQualificazioniDTO>{

    public ProcedimentoQualificazioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ProcedimentoQualificazioniDTO result = new ProcedimentoQualificazioniDTO();
        result.setIdTipoProcedimento(rs.getInt("id_tipo_procedimento"));
        result.setCodiceTipiEQualificazioni(rs.getString("codice_tipi_e_qualificazioni"));
        return result;
    }
}
