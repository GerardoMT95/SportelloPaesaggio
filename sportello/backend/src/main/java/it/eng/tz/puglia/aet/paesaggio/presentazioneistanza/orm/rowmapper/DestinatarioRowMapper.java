package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoCorrispondenza;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;

/**
 * Row Mapper for table presentazione_istanza.destinatario
 */
public class DestinatarioRowMapper implements RowMapper<DestinatarioDTO>{

    public DestinatarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DestinatarioDTO result = new DestinatarioDTO();
        result.setId(rs.getLong("id"));
        //result.setTipo(rs.getString("type"));
        result.setTipo(rs.getObject("type") != null ? TipoDestinatario.valueOf(rs.getString("type")) : null);
        result.setEmail(rs.getString("email"));
        //result.setStato(rs.getString("stato"));
        result.setStato(rs.getObject("stato") != null ? StatoCorrispondenza.valueOf(rs.getString("stato")) : null);
        result.setPec(rs.getBoolean("pec"));
        if (rs.getObject("denominazione") != null)
            result.setNome(rs.getString("denominazione"));
        if (rs.getObject("data_ricezione") != null)
            result.setDataRicezione(rs.getTimestamp("data_ricezione"));
        result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
        return result;
    }
}
