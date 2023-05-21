package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table common.paesaggio_organizzazione_attributi
 */
public class PaesaggioOrganizzazioneAttributiRowMapper implements RowMapper<PaesaggioOrganizzazioneAttributiDTO>{

    public PaesaggioOrganizzazioneAttributiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PaesaggioOrganizzazioneAttributiDTO result = new PaesaggioOrganizzazioneAttributiDTO();
        result.setId(rs.getInt("id"));
        result.setApplicazioneId(rs.getInt("applicazione_id"));
        result.setPaesaggioOrganizzazioneId(rs.getInt("paesaggio_organizzazione_id"));
        result.setDataCreazione(rs.getDate("data_creazione"));
        result.setDataTermine(rs.getDate("data_termine"));
        return result;
    }
}
