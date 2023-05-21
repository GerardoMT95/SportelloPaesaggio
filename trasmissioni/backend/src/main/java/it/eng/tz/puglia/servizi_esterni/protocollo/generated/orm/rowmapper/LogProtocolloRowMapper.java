package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.dto.LogProtocolloDTO;

/**
 * Row Mapper for table putt.log_protocollo
 */
public class LogProtocolloRowMapper implements RowMapper<LogProtocolloDTO>{

    public LogProtocolloDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final LogProtocolloDTO result = new LogProtocolloDTO();
        result.setId(rs.getLong("id"));
        if (rs.getObject("request") != null)
            result.setRequest(rs.getString("request"));
        if (rs.getObject("response") != null)
            result.setResponse(rs.getString("response"));
        if (rs.getObject("protocollo") != null)
            result.setProtocollo(rs.getString("protocollo"));
        if (rs.getObject("verso") != null)
            result.setVerso(rs.getString("verso"));
        if (rs.getObject("data_protocollo") != null)
            result.setDataProtocollo(rs.getTimestamp("data_protocollo"));
        if (rs.getObject("data_esecuzione") != null)
            result.setDataEsecuzione(rs.getTimestamp("data_esecuzione"));
        if (rs.getObject("id_allegato") != null)
            result.setIdAllegato(rs.getLong("id_allegato"));
        return result;
    }
}
