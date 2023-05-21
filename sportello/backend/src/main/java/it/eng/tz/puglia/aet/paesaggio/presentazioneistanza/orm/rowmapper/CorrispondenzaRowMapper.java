package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.corrispondenza
 */
public class CorrispondenzaRowMapper implements RowMapper<CorrispondenzaDTO>{

    public CorrispondenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final CorrispondenzaDTO result = new CorrispondenzaDTO();
        result.setId(rs.getLong("id"));
        if (rs.getObject("id_eml_cmis") != null)
            result.setIdEmlCmis(rs.getString("id_eml_cmis"));
        if (rs.getObject("message_id") != null)
            result.setMessageId(rs.getString("message_id"));
        result.setMittenteEmail(rs.getString("mittente_email"));
        result.setMittenteDenominazione(rs.getString("mittente_denominazione"));
        result.setMittenteUsername(rs.getString("mittente_username"));
        result.setMittenteEnte(rs.getString("mittente_ente"));
        result.setDataInvio(rs.getTimestamp("data_invio"));
        result.setOggetto(rs.getString("oggetto"));
        result.setStato(rs.getBoolean("stato"));
        result.setComunicazione(rs.getBoolean("comunicazione"));
        result.setBozza(rs.getBoolean("bozza"));
        if (rs.getObject("text") != null)
            result.setTesto(rs.getString("text"));
        if (rs.getObject("codice_template") != null)
            result.setCodiceTemplate(rs.getString("codice_template"));
        if (rs.getObject("id_organizzazione_owner") != null)
            result.setIdOrganizzazioneOwner(rs.getInt("id_organizzazione_owner"));
        if (rs.getObject("tipo_organizzazione_owner") != null)
            result.setTipoOrganizzazioneOwner(rs.getString("tipo_organizzazione_owner"));
        if (rs.getObject("visibilita") != null)
            result.setVisibilita(rs.getString("visibilita"));
        result.setRiservata(rs.getBoolean("riservata"));
        if (rs.getObject("protocollo") != null)
            result.setProtocollo(rs.getString("protocollo"));
        if (rs.getObject("data_protocollo") != null)
            result.setDataProtocollo(rs.getDate("data_protocollo"));
        if (rs.getObject("t_mailinviate_id") != null)
            result.settMailInviateId(rs.getLong("t_mailinviate_id"));
        if (rs.getObject("t_pptr_mailinviate_id") != null)
            result.settPptrMailInviateId(rs.getLong("t_pptr_mailinviate_id"));
        return result;
    }
}
