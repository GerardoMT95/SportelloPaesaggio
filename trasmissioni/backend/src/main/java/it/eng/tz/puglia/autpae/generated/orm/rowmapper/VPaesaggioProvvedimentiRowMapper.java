package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table procedimenti_ambientali.v_paesaggio_provvedimenti
 */
public class VPaesaggioProvvedimentiRowMapper implements RowMapper<VPaesaggioProvvedimentiDTO>{

    public VPaesaggioProvvedimentiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final VPaesaggioProvvedimentiDTO result = new VPaesaggioProvvedimentiDTO();
        if (rs.getObject("id_doc") != null)
            result.setIdDoc(rs.getString("id_doc"));
        if (rs.getObject("id_pratica") != null)
            result.setIdPratica(rs.getString("id_pratica"));
        
        if (rs.getObject("nome_procedimento") != null)
            result.setNomeProcedimento(rs.getString("nome_procedimento"));
        if (rs.getObject("nome_famiglia") != null)
            result.setNomeFamiglia(rs.getString("nome_famiglia"));
        if (rs.getObject("nome_autorita_procedente") != null)
            result.setNomeAutoritaProcedente(rs.getString("nome_autorita_procedente"));
        if (rs.getObject("nome_soggetto_coinvolto") != null)
            result.setNomeSoggettoCoinvolto(rs.getString("nome_soggetto_coinvolto"));
        if (rs.getObject("id_tipo_doc") != null)
            result.setIdTipoDoc(rs.getString("id_tipo_doc"));
        if (rs.getObject("descrizione_tipo_doc") != null)
            result.setDescrizioneTipoDoc(rs.getString("descrizione_tipo_doc"));
        if (rs.getObject("codice_trasmissione") != null)
            result.setCodiceTrasmissione(rs.getString("codice_trasmissione"));
        if (rs.getObject("oggetto") != null)
            result.setOggetto(rs.getString("oggetto"));
        if (rs.getObject("id_tipo_carica_documento") != null)
            result.setIdTipoCaricaDocumento( (java.util.UUID) rs.getObject("id_tipo_carica_documento"));
        if (rs.getObject("numero_protocollo_esterno") != null)
            result.setNumeroProtocolloEsterno(rs.getString("numero_protocollo_esterno"));
        if (rs.getObject("data_protocollo_esterno") != null)
            result.setDataProtocolloEsterno(rs.getTimestamp("data_protocollo_esterno"));
        if (rs.getObject("cmis_id") != null)
            result.setCmisId(rs.getString("cmis_id"));
        if (rs.getObject("file_name") != null)
            result.setFileName(rs.getString("file_name"));
        if (rs.getObject("hash_file") != null)
            result.setHashFile(rs.getString("hash_file"));
        if (rs.getObject("codice_fiscale_ente") != null)
            result.setCodiceFiscaleEnte(rs.getString("codice_fiscale_ente"));
        if (rs.getObject("ente_id") != null)
            result.setEnteId(rs.getInt("ente_id"));
        if (rs.getObject("tipo") != null)
            result.setTipo(rs.getString("tipo"));
        if (rs.getObject("id") != null)
            result.setId(rs.getString("id"));
        if (rs.getObject("indice") != null)
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
        if (rs.getObject("telefono") != null)
            result.setTelefono(rs.getString("telefono"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        if (rs.getObject("mail") != null)
            result.setMail(rs.getString("mail"));
        if (rs.getObject("ditta") != null)
            result.setDitta(rs.getBoolean("ditta"));
        if (rs.getObject("id_ruolo_azienda") != null)
            result.setIdRuoloAzienda(rs.getString("id_ruolo_azienda"));
        if (rs.getObject("nome_ruolo_azienda") != null)
            result.setNomeRuoloAzienda(rs.getString("nome_ruolo_azienda"));
        if (rs.getObject("altro_ruolo_azienda") != null)
            result.setAltroRuoloAzienda(rs.getString("altro_ruolo_azienda"));
        if (rs.getObject("piva_azienda") != null)
            result.setPivaAzienda(rs.getString("piva_azienda"));
        if (rs.getObject("codice_fiscale_azienda") != null)
            result.setCodiceFiscaleAzienda(rs.getString("codice_fiscale_azienda"));
        if (rs.getObject("id_tipo_documento") != null)
            result.setIdTipoDocumento(rs.getString("id_tipo_documento"));
        if (rs.getObject("numero_documento") != null)
            result.setNumeroDocumento(rs.getString("numero_documento"));
        if (rs.getObject("data_rilascio_documento") != null)
            result.setDataRilascioDocumento(rs.getDate("data_rilascio_documento"));
        if (rs.getObject("data_scadenza_documento") != null)
            result.setDataScadenzaDocumento(rs.getDate("data_scadenza_documento"));
        if (rs.getObject("cmis_documento") != null)
            result.setCmisDocumento(rs.getString("cmis_documento"));
        if (rs.getObject("nome_documento") != null)
            result.setNomeDocumento(rs.getString("nome_documento"));
        if (rs.getObject("titolarita") != null)
            result.setTitolarita(rs.getString("titolarita"));
        if (rs.getObject("titolarita_altro") != null)
            result.setTitolaritaAltro(rs.getString("titolarita_altro"));
        if (rs.getObject("hash_documento") != null)
            result.setHashDocumento(rs.getString("hash_documento"));
        if (rs.getObject("azienda") != null)
            result.setAzienda(rs.getString("azienda"));
        if (rs.getObject("data_documento_identita") != null)
            result.setDataDocumentoIdentita(rs.getTimestamp("data_documento_identita"));
        if (rs.getObject("ente_rilascio_documento") != null)
            result.setEnteRilascioDocumento(rs.getString("ente_rilascio_documento"));
        if (rs.getObject("date_create") != null)
            result.setDateCreate(rs.getTimestamp("date_create"));
        if (rs.getObject("id_tipo_azienda") != null)
            result.setIdTipoAzienda(rs.getString("id_tipo_azienda"));
        if (rs.getObject("tipo_azienda") != null)
            result.setTipoAzienda(rs.getString("tipo_azienda"));
        if (rs.getObject("c_ipa") != null)
            result.setCIpa(rs.getString("c_ipa"));
        if (rs.getObject("c_uo") != null)
            result.setCUo(rs.getString("c_uo"));
        return result;
    }
}
