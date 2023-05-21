package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pareri_e_atti_assenso
 */
public class PareriEAttiAssensoRowMapper implements RowMapper<PareriEAttiAssensoDTO>{

    public PareriEAttiAssensoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PareriEAttiAssensoDTO result = new PareriEAttiAssensoDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setId(rs.getLong("id"));
        if (rs.getObject("tipologia_atto") != null)
            result.setTipologiaAtto(rs.getString("tipologia_atto"));
        if (rs.getObject("autorita_rilascio") != null)
            result.setAutoritaRilascio(rs.getString("autorita_rilascio"));
        if (rs.getObject("protocollo") != null)
            result.setProtocollo(rs.getString("protocollo"));
        if (rs.getObject("data_rilascio") != null)
            result.setDataRilascio(rs.getDate("data_rilascio"));
        if (rs.getObject("flag_atto_parere") != null)
            result.setFlagAttoParere(rs.getString("flag_atto_parere"));
        if (rs.getObject("intestatario_atto") != null)
            result.setIntestatarioAtto(rs.getString("intestatario_atto"));
        return result;
    }
}
