package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pratica_cds
 */
public class PraticaCdsRowMapper implements RowMapper<PraticaCdsDTO>{

    public PraticaCdsDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaCdsDTO result = new PraticaCdsDTO();
        result.setId(rs.getString("id"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        if (rs.getObject("tipo") != null)
            result.setTipo(rs.getString("tipo"));
        if (rs.getObject("attivita") != null)
            result.setAttivita(rs.getString("attivita"));
        if (rs.getObject("azione") != null)
            result.setAzione(rs.getString("azione"));
        if (rs.getObject("termine_richiesta_integrazione") != null)
            result.setTermineRichiestaIntegrazione(rs.getDate("termine_richiesta_integrazione"));
        if (rs.getObject("termine_pareri") != null)
            result.setTerminePareri(rs.getDate("termine_pareri"));
        if (rs.getObject("prima_sessione") != null)
            result.setPrimaSessione(rs.getDate("prima_sessione"));
        if (rs.getObject("data_termine") != null)
            result.setDataTermine(rs.getDate("data_termine"));
        if (rs.getObject("comune_pertinenza") != null)
            result.setComunePertinenza(rs.getString("comune_pertinenza"));
        if (rs.getObject("provincia_pertinenza") != null)
            result.setProvinciaPertinenza(rs.getString("provincia_pertinenza"));
        if (rs.getObject("indirizzo_pertinenza") != null)
            result.setIndirizzoPertinenza(rs.getString("indirizzo_pertinenza"));
        if (rs.getObject("modalita_incontro") != null)
            result.setModalitaIncontro(rs.getString("modalita_incontro"));
        if (rs.getObject("link") != null)
            result.setLink(rs.getString("link"));
        if (rs.getObject("comune") != null)
            result.setComune(rs.getString("comune"));
        if (rs.getObject("provincia") != null)
            result.setProvincia(rs.getString("provincia"));
        if (rs.getObject("cap") != null)
            result.setCap(rs.getString("cap"));
        if (rs.getObject("indirizzo") != null)
            result.setIndirizzo(rs.getString("indirizzo"));
        if (rs.getObject("orario") != null)
            result.setOrario(rs.getString("orario"));
        result.setDateCreate(rs.getTimestamp("date_create"));
        result.setUserCreate(rs.getString("user_create"));
        if (rs.getObject("date_update") != null)
            result.setDateUpdate(rs.getTimestamp("date_update"));
        if (rs.getObject("user_update") != null)
            result.setUserUpdate(rs.getString("user_update"));
        result.setCompleted(rs.getBoolean("completed"));
        if (rs.getObject("id_cds") != null)
            result.setIdCds(rs.getLong("id_cds"));
        if (rs.getObject("comitato") != null)
            result.setComitato(rs.getBoolean("comitato"));
        if (rs.getObject("username_creatore") != null)
            result.setUsernameCreatore(rs.getString("username_creatore"));
        if (rs.getObject("nome_creatore") != null)
            result.setNomeCreatore(rs.getString("nome_creatore"));
        if (rs.getObject("cognome_creatore") != null)
            result.setCognomeCreatore(rs.getString("cognome_creatore"));
        if (rs.getObject("mail_creatore") != null)
            result.setMailCreatore(rs.getString("mail_creatore"));
        if (rs.getObject("telefono_creatore") != null)
            result.setTelefonoCreatore(rs.getString("telefono_creatore"));
        if (rs.getObject("codice_fiscale_creatore") != null)
            result.setCodiceFiscaleCreatore(rs.getString("codice_fiscale_creatore"));
        if (rs.getObject("username_responsabile") != null)
            result.setUsernameResponsabile(rs.getString("username_responsabile"));
        if (rs.getObject("nome_responsabile") != null)
            result.setNomeResponsabile(rs.getString("nome_responsabile"));
        if (rs.getObject("cognome_responsabile") != null)
            result.setCognomeResponsabile(rs.getString("cognome_responsabile"));
        if (rs.getObject("codice_fiscale_responsabile") != null)
            result.setCodiceFiscaleResponsabile(rs.getString("codice_fiscale_responsabile"));
        if (rs.getObject("mail_responsabile") != null)
            result.setMailResponsabile(rs.getString("mail_responsabile"));
        if (rs.getObject("telefono_responsabile") != null)
            result.setTelefonoResponsabile(rs.getString("telefono_responsabile"));
        return result;
    }
}
