package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table common.paesaggio_organizzazione
 */
public class PaesaggioOrganizzazioneRowMapper implements RowMapper<PaesaggioOrganizzazioneDTO>{

    public PaesaggioOrganizzazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PaesaggioOrganizzazioneDTO result = new PaesaggioOrganizzazioneDTO();
        result.setId(rs.getInt("id"));
        if (rs.getObject("ente_id") != null)
            result.setEnteId(rs.getInt("ente_id"));
        result.setTipoOrganizzazione(rs.getString("tipo_organizzazione"));
        if (rs.getObject("tipo_organizzazione_specifica") != null)
            result.setTipoOrganizzazioneSpecifica(rs.getString("tipo_organizzazione_specifica"));
        if (rs.getObject("codice_civilia") != null)
            result.setCodiceCivilia(rs.getString("codice_civilia"));
        if (rs.getObject("riferimento_organizzazione") != null)
            result.setRiferimentoOrganizzazione(rs.getInt("riferimento_organizzazione"));
        result.setDataCreazione(rs.getDate("data_creazione"));
        result.setDataTermine(rs.getDate("data_termine"));
        if (rs.getObject("denominazione") != null)
            result.setDenominazione(rs.getString("denominazione"));
        return result;
    }
}
