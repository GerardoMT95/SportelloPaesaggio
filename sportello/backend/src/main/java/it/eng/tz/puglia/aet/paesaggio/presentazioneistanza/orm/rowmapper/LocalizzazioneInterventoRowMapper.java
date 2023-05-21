package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.localizzazione_intervento
 */
public class LocalizzazioneInterventoRowMapper implements RowMapper<LocalizzazioneInterventoDTO>{

    public LocalizzazioneInterventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final LocalizzazioneInterventoDTO result = new LocalizzazioneInterventoDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setComuneId(rs.getLong("comune_id"));
        if (rs.getObject("indirizzo") != null)
            result.setIndirizzo(rs.getString("indirizzo"));
        if (rs.getObject("num_civico") != null)
            result.setNumCivico(rs.getString("num_civico"));
        if (rs.getObject("piano") != null)
            result.setPiano(rs.getString("piano"));
        if (rs.getObject("interno") != null)
            result.setInterno(rs.getString("interno"));
        if (rs.getObject("dest_uso_att") != null)
            result.setDestUsoAtt(rs.getString("dest_uso_att"));
        if (rs.getObject("dest_uso_prog") != null)
            result.setDestUsoProg(rs.getString("dest_uso_prog"));
        if (rs.getObject("comune") != null)
            result.setComune(rs.getString("comune"));
        if (rs.getObject("sigla_provincia") != null)
            result.setSiglaProvincia(rs.getString("sigla_provincia"));
        if (rs.getObject("data_riferimento_catastale") != null)
            result.setDataRiferimentoCatastale(rs.getDate("data_riferimento_catastale"));
        result.setStrade(rs.getBoolean("strade"));
        return result;
    }
}
