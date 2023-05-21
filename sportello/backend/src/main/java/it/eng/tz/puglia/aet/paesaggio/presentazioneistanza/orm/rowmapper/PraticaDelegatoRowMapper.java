package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;

/**
 * Row Mapper for table presentazione_istanza.pratica_delegato
 */
public class PraticaDelegatoRowMapper implements RowMapper<PraticaDelegatoDTO>{

    public PraticaDelegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaDelegatoDTO result = new PraticaDelegatoDTO();
        result.setId( (java.util.UUID) rs.getObject("id"));
        result.setIndice(rs.getShort("indice"));
        if (rs.getObject("cognome") != null)
            result.setCognome(rs.getString("cognome"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("codice_fiscale") != null)
            result.setCodiceFiscale(rs.getString("codice_fiscale"));
        if (rs.getObject("sesso") != null)
            result.setSesso(rs.getString("sesso"));
        if (rs.getObject("data_nascita") != null)
            result.setDataNascita(rs.getTimestamp("data_nascita"));
        if (rs.getObject("id_nazione_nascita") != null)
            result.setIdNazioneNascita(rs.getInt("id_nazione_nascita"));
        if (rs.getObject("nazione_nascita") != null)
            result.setNazioneNascita(rs.getString("nazione_nascita"));
        if (rs.getObject("id_comune_nascita") != null)
            result.setIdComuneNascita(rs.getInt("id_comune_nascita"));
        if (rs.getObject("comune_nascita") != null)
            result.setComuneNascita(rs.getString("comune_nascita"));
        if (rs.getObject("comune_nascita_estero") != null)
            result.setComuneNascitaEstero(rs.getString("comune_nascita_estero"));
        if (rs.getObject("id_nazione_residenza") != null)
            result.setIdNazioneResidenza(rs.getInt("id_nazione_residenza"));
        if (rs.getObject("nazione_residenza") != null)
            result.setNazioneResidenza(rs.getString("nazione_residenza"));
        if (rs.getObject("id_comune_residenza") != null)
            result.setIdComuneResidenza(rs.getInt("id_comune_residenza"));
        if (rs.getObject("comune_residenza") != null)
            result.setComuneResidenza(rs.getString("comune_residenza"));
        if (rs.getObject("comune_residenza_estero") != null)
            result.setComuneResidenzaEstero(rs.getString("comune_residenza_estero"));
        if (rs.getObject("indirizzo_residenza") != null)
            result.setIndirizzoResidenza(rs.getString("indirizzo_residenza"));
        if (rs.getObject("civico_residenza") != null)
            result.setCivicoResidenza(rs.getString("civico_residenza"));
        if (rs.getObject("cap_residenza") != null)
            result.setCapResidenza(rs.getString("cap_residenza"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        if (rs.getObject("mail") != null)
            result.setMail(rs.getString("mail"));
        result.setDateCreate(rs.getTimestamp("date_create"));
        if (rs.getObject("delgato_corrente") != null)
            result.setDelgatoCorrente(rs.getBoolean("delgato_corrente"));
        if(rs.getObject("id_provincia_residenza") != null)
            result.setIdProvinciaResidenza(rs.getInt("id_provincia_residenza"));
        if(rs.getObject("provincia_residenza") != null)
            result.setProvinciaResidenza(rs.getString("provincia_residenza"));
        if(rs.getObject("id_provincia_nascita") != null)
            result.setIdProvinciaNascita(rs.getInt("id_provincia_nascita"));
        if(rs.getObject("provincia_nascita") != null)
            result.setProvinciaNascita(rs.getString("provincia_nascita"));
        return result;
    }
}
