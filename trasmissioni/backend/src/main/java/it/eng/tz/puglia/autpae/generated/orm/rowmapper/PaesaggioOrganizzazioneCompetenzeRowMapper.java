package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table common.paesaggio_organizzazione_competenze
 */
public class PaesaggioOrganizzazioneCompetenzeRowMapper implements RowMapper<PaesaggioOrganizzazioneCompetenzeDTO>{

    public PaesaggioOrganizzazioneCompetenzeDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PaesaggioOrganizzazioneCompetenzeDTO result = new PaesaggioOrganizzazioneCompetenzeDTO();
        result.setId(rs.getInt("id"));
        result.setPaesaggioOrganizzazioneId(rs.getInt("paesaggio_organizzazione_id"));
        result.setEnteId(rs.getInt("ente_id"));
        result.setDataInizioDelega(rs.getDate("data_inizio_delega"));
        result.setDataFineDelega(rs.getDate("data_fine_delega"));
        if (rs.getObject("codice_civilia") != null)
            result.setCodiceCivilia(rs.getString("codice_civilia"));
        return result;
    }
}
