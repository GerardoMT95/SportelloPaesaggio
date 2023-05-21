package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.leggittimita_provvedimenti
 */
public class LeggittimitaProvvedimentiRowMapper implements RowMapper<LeggittimitaProvvedimentiDTO>{

    public LeggittimitaProvvedimentiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final LeggittimitaProvvedimentiDTO result = new LeggittimitaProvvedimentiDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setId(rs.getLong("id"));
        if (rs.getObject("leg_provv_denominazione") != null)
            result.setLegProvvDenominazione(rs.getString("leg_provv_denominazione"));
        if (rs.getObject("leg_provv_rilasciato_da") != null)
            result.setLegProvvRilasciatoDa(rs.getString("leg_provv_rilasciato_da"));
        if (rs.getObject("leg_provv_num_protocollo") != null)
            result.setLegProvvNumProtocollo(rs.getString("leg_provv_num_protocollo"));
        if (rs.getObject("leg_provv_data_rilascio") != null)
            result.setLegProvvDataRilascio(rs.getDate("leg_provv_data_rilascio"));
        if (rs.getObject("leg_provv_intestatario") != null)
            result.setLegProvvIntestatario(rs.getString("leg_provv_intestatario"));
        return result;
    }
}
