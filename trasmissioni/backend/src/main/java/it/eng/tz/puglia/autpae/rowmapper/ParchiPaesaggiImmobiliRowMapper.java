package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;

/**
 * Row Mapper for table autpae.parchi_paesaggi_immobili
 */
public class ParchiPaesaggiImmobiliRowMapper implements RowMapper<ParchiPaesaggiImmobiliDTO>{

    public ParchiPaesaggiImmobiliDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ParchiPaesaggiImmobiliDTO result = new ParchiPaesaggiImmobiliDTO();
        result.setPraticaId(rs.getLong("pratica_id"));
        result.setComuneId (rs.getLong( "comune_id"));
        result.setTipoVincolo(rs.getString("tipo_vincolo"));
        result.setCodice(rs.getString("codice"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("selezionato") != null)
            result.setSelezionato(rs.getString("selezionato"));
        if (rs.getObject("info") != null)
            result.setInfo(rs.getString("info"));
        if (rs.getObject("data_inserimento") != null)
            result.setDataInserimento(rs.getDate("data_inserimento"));
        return result;
    }
}