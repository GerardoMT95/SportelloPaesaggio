package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaTitoliDTO;

/**
 * Row Mapper for table presentazione_istanza.leggittimita_titoli
 */
public class LeggittimitaTitoliRowMapper implements RowMapper<LeggittimitaTitoliDTO>{

    public LeggittimitaTitoliDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final LeggittimitaTitoliDTO result = new LeggittimitaTitoliDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setId(rs.getLong("id"));
        if (rs.getObject("leg_tit_denominazione") != null)
            result.setLegTitDenominazione(rs.getString("leg_tit_denominazione"));
        if (rs.getObject("leg_tit_rilasciato_da") != null)
            result.setLegTitRilasciatoDa(rs.getString("leg_tit_rilasciato_da"));
        if (rs.getObject("leg_tit_num_protocollo") != null)
            result.setLegTitNumProtocollo(rs.getString("leg_tit_num_protocollo"));
        if (rs.getObject("leg_tit_data_rilascio") != null)
            result.setLegTitDataRilascio(rs.getDate("leg_tit_data_rilascio"));
        if (rs.getObject("leg_tit_intestatario") != null)
            result.setLegTitIntestatario(rs.getString("leg_tit_intestatario"));
        return result;
    }
}
