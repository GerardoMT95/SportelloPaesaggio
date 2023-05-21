package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table common.paesaggio_organizzazione_rubrica
 */
public class PaesaggioOrganizzazioneRubricaRowMapper implements RowMapper<PaesaggioOrganizzazioneRubricaDTO>{

    public PaesaggioOrganizzazioneRubricaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PaesaggioOrganizzazioneRubricaDTO result = new PaesaggioOrganizzazioneRubricaDTO();
        result.setId(rs.getLong("id"));
        result.setPaesaggioOrganizzazioneId(rs.getInt("paesaggio_organizzazione_id"));
        result.setCodiceApplicazione(rs.getString("codice_applicazione"));
        result.setNome(rs.getString("nome"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        if (rs.getObject("mail") != null)
            result.setMail(rs.getString("mail"));
        result.setDataCreazione(rs.getTimestamp("data_creazione"));
        if (rs.getObject("data_modifica") != null)
            result.setDataModifica(rs.getTimestamp("data_modifica"));
        result.setUsernameCreazione(rs.getString("username_creazione"));
        if (rs.getObject("username_modifica") != null)
            result.setUsernameModifica(rs.getString("username_modifica"));
        return result;
    }
}
