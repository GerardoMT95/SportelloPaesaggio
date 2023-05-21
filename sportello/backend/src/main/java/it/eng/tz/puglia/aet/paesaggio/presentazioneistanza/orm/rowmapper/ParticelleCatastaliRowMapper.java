package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.particelle_catastali
 */
public class ParticelleCatastaliRowMapper implements RowMapper<ParticelleCatastaliDTO>{

    public ParticelleCatastaliDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ParticelleCatastaliDTO result = new ParticelleCatastaliDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setComuneId(rs.getLong("comune_id"));
        result.setId(rs.getInt("id"));
        if (rs.getObject("livello") != null)
            result.setLivello(rs.getString("livello"));
        if (rs.getObject("sezione") != null)
            result.setSezione(rs.getString("sezione"));
        if (rs.getObject("foglio") != null)
            result.setFoglio(rs.getString("foglio"));
        if (rs.getObject("particella") != null)
            result.setParticella(rs.getString("particella"));
        if (rs.getObject("sub") != null)
            result.setSub(rs.getString("sub"));
        if (rs.getObject("cod_cat") != null)
            result.setCodCat(rs.getString("cod_cat"));
        if (rs.getObject("oid") != null)
            result.setOid(rs.getLong("oid"));
        if (rs.getObject("note") != null)
            result.setNote(rs.getString("note"));
        if (rs.getObject("descr_sezione") != null)
            result.setDescrSezione(rs.getString("descr_sezione"));
        return result;
    }
}
