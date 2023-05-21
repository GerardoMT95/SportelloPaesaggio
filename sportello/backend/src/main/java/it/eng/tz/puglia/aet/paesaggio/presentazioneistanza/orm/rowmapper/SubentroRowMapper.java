package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SubentroDTO;

/**
 * Row Mapper for table presentazione_istanza.subentro
 */
public class SubentroRowMapper implements RowMapper<SubentroDTO>{

    public SubentroDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final SubentroDTO result = new SubentroDTO();
        result.setId( (java.util.UUID) rs.getObject("id"));
        if (rs.getObject("id_pratica") != null)
            result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        result.setStato(rs.getString("stato"));
        if (rs.getObject("cognome") != null)
            result.setCognome(rs.getString("cognome"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("codice_fiscale") != null)
            result.setCodiceFiscale(rs.getString("codice_fiscale"));
        if (rs.getObject("sesso") != null)
            result.setSesso(rs.getString("sesso"));
        if (rs.getObject("data_nascita") != null)
            result.setDataNascita(rs.getDate("data_nascita"));
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
        if (rs.getObject("date_update") != null)
            result.setDateUpdate(rs.getTimestamp("date_update"));
        if (rs.getObject("cmis_id_modulo") != null)
            result.setCmisIdModulo(rs.getString("cmis_id_modulo"));
        if (rs.getObject("file_name_modulo") != null)
            result.setFileNameModulo(rs.getString("file_name_modulo"));
        if (rs.getObject("cmis_id_sollevamento") != null)
            result.setCmisIdSollevamento(rs.getString("cmis_id_sollevamento"));
        if (rs.getObject("file_name_sollevamento") != null)
            result.setFileNameSollevamento(rs.getString("file_name_sollevamento"));
        if (rs.getObject("hash_modulo") != null)
            result.setHashModulo(rs.getString("hash_modulo"));
        if (rs.getObject("hash_sollevamento") != null)
            result.setHashSollevamento(rs.getString("hash_sollevamento"));
        if (rs.getObject("id_provincia_nascita") != null)
            result.setIdProvinciaNascita(rs.getInt("id_provincia_nascita"));
        if (rs.getObject("provincia_nascita") != null)
            result.setProvinciaNascita(rs.getString("provincia_nascita"));
        if (rs.getObject("id_provincia_residenza") != null)
            result.setIdProvinciaResidenza(rs.getInt("id_provincia_residenza"));
        if (rs.getObject("provincia_residenza") != null)
            result.setProvinciaResidenza(rs.getString("provincia_residenza"));
        return result;
    }
}
