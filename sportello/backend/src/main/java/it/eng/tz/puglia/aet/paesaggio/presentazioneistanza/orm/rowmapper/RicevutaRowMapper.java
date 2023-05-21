package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoErrore;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoRicevuta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.ricevuta
 */
public class RicevutaRowMapper implements RowMapper<RicevutaDTO>{

    public RicevutaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final RicevutaDTO result = new RicevutaDTO();
        result.setId(rs.getLong("id"));
        if (rs.getObject("id_corrispondenza") != null)
            result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
        if (rs.getObject("id_destinatario") != null)
            result.setIdDestinatario(rs.getLong("id_destinatario"));
        if (rs.getObject("tipo_ricevura") != null)
        	result.setTipoRicevuta(TipoRicevuta.valueOf(rs.getString("tipo_ricevura")));
        if (rs.getObject("errore") != null)
            result.setErrore(TipoErrore.valueOf(rs.getString("errore")));
        if (rs.getObject("descrizione_errore") != null)
            result.setDescrizioneErrore(rs.getString("descrizione_errore"));
        if (rs.getObject("id_cms_eml") != null)
            result.setIdCmsEml(rs.getString("id_cms_eml"));
        if (rs.getObject("id_cms_dati_cert") != null)
            result.setIdCmsDatiCert(rs.getString("id_cms_dati_cert"));
        if (rs.getObject("id_cms_smime") != null)
            result.setIdCmsSmime(rs.getString("id_cms_smime"));
        if (rs.getObject("data") != null)
            result.setData(rs.getTimestamp("data"));
        return result;
    }
}
