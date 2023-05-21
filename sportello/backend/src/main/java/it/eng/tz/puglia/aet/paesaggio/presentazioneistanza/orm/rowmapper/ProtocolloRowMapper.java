package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.protocollo
 */
public class ProtocolloRowMapper implements RowMapper<ProtocolloDTO>{

    public ProtocolloDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ProtocolloDTO result = new ProtocolloDTO();
        result.setIdProtocollo( (java.util.UUID) rs.getObject("id_protocollo"));
        if (rs.getObject("codiceAmministrazione") != null)
            result.setCodiceamministrazione(rs.getString("codiceAmministrazione"));
        if (rs.getObject("codiceAOO") != null)
            result.setCodiceaoo(rs.getString("codiceAOO"));
        if (rs.getObject("codiceRegistro") != null)
            result.setCodiceregistro(rs.getString("codiceRegistro"));
        if (rs.getObject("dataRegistrazione") != null)
            result.setDataregistrazione(rs.getString("dataRegistrazione"));
        if (rs.getObject("numeroProtocollo") != null)
            result.setNumeroprotocollo(rs.getString("numeroProtocollo"));
        if (rs.getObject("hostProxy") != null)
            result.setHostproxy(rs.getString("hostProxy"));
        if (rs.getObject("portProxy") != null)
            result.setPortproxy(rs.getString("portProxy"));
        if (rs.getObject("usernameProxy") != null)
            result.setUsernameproxy(rs.getString("usernameProxy"));
        if (rs.getObject("passwordProxy") != null)
            result.setPasswordproxy(rs.getString("passwordProxy"));
        if (rs.getObject("urlNotProxy") != null)
            result.setUrlnotproxy(rs.getString("urlNotProxy"));
        if (rs.getObject("clientProtocolloEndpoint") != null)
            result.setClientprotocolloendpoint(rs.getString("clientProtocolloEndpoint"));
        if (rs.getObject("clientProtocolloAdministration") != null)
            result.setClientprotocolloadministration(rs.getString("clientProtocolloAdministration"));
        if (rs.getObject("clientProtocolloAOO") != null)
            result.setClientprotocolloaoo(rs.getString("clientProtocolloAOO"));
        if (rs.getObject("clientProtocolloRegister") != null)
            result.setClientprotocolloregister(rs.getString("clientProtocolloRegister"));
        if (rs.getObject("clientProtocolloUser") != null)
            result.setClientprotocollouser(rs.getString("clientProtocolloUser"));
        if (rs.getObject("clientProtocolloPassword") != null)
            result.setClientprotocollopassword(rs.getString("clientProtocolloPassword"));
        if (rs.getObject("clientProtocolloHashAlgorihtm") != null)
            result.setClientprotocollohashalgorihtm(rs.getString("clientProtocolloHashAlgorihtm"));
        if (rs.getObject("protocolObject") != null)
            result.setProtocolobject(rs.getString("protocolObject"));
        if (rs.getObject("request") != null)
            result.setRequest(rs.getString("request"));
        if (rs.getObject("response") != null)
            result.setResponse(rs.getString("response"));
        if (rs.getObject("protocollo") != null)
            result.setProtocollo(rs.getString("protocollo"));
        if (rs.getObject("verso") != null)
            result.setVerso(rs.getString("verso"));
        if (rs.getObject("data_esecuzione") != null)
            result.setDataEsecuzione(rs.getTimestamp("data_esecuzione"));
        if (rs.getObject("id_allegato") != null)
            result.setIdAllegato((java.util.UUID) rs.getObject("id_allegato"));
        if (rs.getObject("id_pratica") != null)
            result.setIdPratica((java.util.UUID) rs.getObject("id_pratica"));
        return result;
    }
}
