package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;

/**
 * Row Mapper for table presentazione_istanza.referenti
 */
public class ReferentiRowMapper implements RowMapper<ReferentiDTO>{

    @Override
	public ReferentiDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException{
        final ReferentiDTO result = new ReferentiDTO();
        result.setId(rs.getLong("id"));
        result.setTipoReferente(rs.getString("tipo_referente"));
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("cognome") != null)
            result.setCognome(rs.getString("cognome"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("codice_fiscale") != null)
            result.setCodiceFiscale(rs.getString("codice_fiscale"));
        if (rs.getObject("comune_nascita") != null)
            result.setComuneNascita(rs.getInt("comune_nascita"));
        if (rs.getObject("provincia_nascita") != null)
            result.setProvinciaNascita(rs.getInt("provincia_nascita"));
        if (rs.getObject("nazionalita_nascita") != null)
            result.setNazionalitaNascita(rs.getInt("nazionalita_nascita"));
        if (rs.getObject("comune_nascita_estero") != null)
            result.setComuneNascitaEstero(rs.getString("comune_nascita_estero"));
        if (rs.getObject("provincia_nascita_estero") != null)
            result.setProvinciaNascitaEstero(rs.getString("provincia_nascita_estero"));
        if (rs.getObject("data_nascita") != null)
            result.setDataNascita(rs.getDate("data_nascita"));
        if (rs.getObject("sesso") != null)
            result.setSesso(rs.getString("sesso"));
        if (rs.getObject("indirizzo_residenza") != null)
            result.setIndirizzoResidenza(rs.getString("indirizzo_residenza"));
        if (rs.getObject("civico_residenza") != null)
            result.setCivicoResidenza(rs.getString("civico_residenza"));
        if (rs.getObject("cap_residenza") != null)
            result.setCapResidenza(rs.getString("cap_residenza"));
        if (rs.getObject("comune_residenza") != null)
            result.setComuneResidenza(rs.getInt("comune_residenza"));
        if (rs.getObject("provincia_residenza") != null)
            result.setProvinciaResidenza(rs.getInt("provincia_residenza"));
        if (rs.getObject("nazionalita_residenza") != null)
            result.setNazionalitaResidenza(rs.getInt("nazionalita_residenza"));
        if (rs.getObject("comune_residenza_estero") != null)
            result.setComuneResidenzaEstero(rs.getString("comune_residenza_estero"));
        if (rs.getObject("provincia_residenza_estero") != null)
            result.setProvinciaResidenzaEstero(rs.getString("provincia_residenza_estero"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        if (rs.getObject("mail") != null)
            result.setMail(rs.getString("mail"));
        if (rs.getObject("telefono") != null)
            result.setTelefono(rs.getString("telefono"));
        if (rs.getObject("cellulare") != null)
            result.setCellulare(rs.getString("cellulare"));
        if (rs.getObject("fax") != null)
            result.setFax(rs.getString("fax"));
        if (rs.getObject("ditta") != null)
            result.setDitta(rs.getBoolean("ditta"));
        if (rs.getObject("ditta_ente") != null)
            result.setDittaEnte(rs.getString("ditta_ente"));
        if (rs.getObject("ditta_cf") != null)
            result.setDittaCf(rs.getString("ditta_cf"));
        if (rs.getObject("ditta_partita_iva") != null)
            result.setDittaPartitaIva(rs.getString("ditta_partita_iva"));
        if (rs.getObject("ditta_qualita_di") != null)
            result.setDittaQualitaDi(rs.getInt("ditta_qualita_di"));
        if (rs.getObject("ditta_qualita_altro") != null)
            result.setDittaQualitaAltro(rs.getString("ditta_qualita_altro"));
        if (rs.getObject("tecnico_studio_indirizzo") != null)
            result.setTecnicoStudioIndirizzo(rs.getString("tecnico_studio_indirizzo"));
        if (rs.getObject("tecnico_studio_civico") != null)
            result.setTecnicoStudioCivico(rs.getString("tecnico_studio_civico"));
        if (rs.getObject("tecnico_studio_cap") != null)
            result.setTecnicoStudioCap(rs.getString("tecnico_studio_cap"));
        if (rs.getObject("tecnico_studio_comune") != null)
            result.setTecnicoStudioComune(rs.getInt("tecnico_studio_comune"));
        if (rs.getObject("tecnico_studio_provincia") != null)
            result.setTecnicoStudioProvincia(rs.getInt("tecnico_studio_provincia"));
        if (rs.getObject("tecnico_studio_nazionalita") != null)
            result.setTecnicoStudioNazionalita(rs.getInt("tecnico_studio_nazionalita"));
        if (rs.getObject("tecnico_studio_comune_estero") != null)
            result.setTecnicoStudioComuneEstero(rs.getString("tecnico_studio_comune_estero"));
        if (rs.getObject("tecnico_studio_provincia_estero") != null)
            result.setTecnicoStudioProvinciaEstero(rs.getString("tecnico_studio_provincia_estero"));
        if (rs.getObject("tecnico_ord_collegio") != null)
            result.setTecnicoOrdCollegio(rs.getString("tecnico_ord_collegio"));
        if (rs.getObject("tecnico_collegio_sede") != null)
            result.setTecnicoCollegioSede(rs.getString("tecnico_collegio_sede"));
        if (rs.getObject("tecnico_collegio_n_iscr") != null)
            result.setTecnicoCollegioNIscr(rs.getString("tecnico_collegio_n_iscr"));
        if (rs.getObject("nazionalita_residenza_name") != null)
            result.setNazionalitaResidenzaName(rs.getString("nazionalita_residenza_name"));
        if (rs.getObject("provincia_residenza_name") != null)
            result.setProvinciaResidenzaName(rs.getString("provincia_residenza_name"));
        if (rs.getObject("comune_residenza_name") != null)
            result.setComuneResidenzaName(rs.getString("comune_residenza_name"));
        if (rs.getObject("nazionalita_nascita_name") != null)
            result.setNazionalitaNascitaName(rs.getString("nazionalita_nascita_name"));
        if (rs.getObject("provincia_nascita_name") != null)
            result.setProvinciaNascitaName(rs.getString("provincia_nascita_name"));
        if (rs.getObject("comune_nascita_name") != null)
            result.setComuneNascitaName(rs.getString("comune_nascita_name"));
        if (rs.getObject("tecnico_studio_nazionalita_name") != null)
            result.setTecnicoStudioNazionalitaName(rs.getString("tecnico_studio_nazionalita_name"));
        if (rs.getObject("tecnico_studio_provincia_name") != null)
            result.setTecnicoStudioProvinciaName(rs.getString("tecnico_studio_provincia_name"));
        if (rs.getObject("tecnico_studio_comune_name") != null)
            result.setTecnicoStudioComuneName(rs.getString("tecnico_studio_comune_name"));
        if (rs.getObject("titolarita_id") != null)
            result.setTitolaritaId(rs.getInt("titolarita_id"));
        if (rs.getObject("specifica_titolarita") != null)
            result.setSpecificaTitolarita(rs.getString("specifica_titolarita"));
        if (rs.getObject("titolarita_esclusiva") != null)
            result.setTitolaritaEsclusiva(rs.getString("titolarita_esclusiva"));
        if (rs.getObject("codice_ipa") != null)
        	result.setCodiceIpa(rs.getString("codice_ipa"));
        if (rs.getObject("id_tipo_azienda") != null)
        	result.setIdTipoAzienda(rs.getString("id_tipo_azienda"));
        if (rs.getObject("tipo_azienda") != null)
        	result.setTipoAzienda(rs.getString("tipo_azienda"));
        if (rs.getObject("ditta_codice_uo") != null)
            result.setDittaCodiceUO(rs.getString("ditta_codice_uo"));;
        return result;
    }
}
