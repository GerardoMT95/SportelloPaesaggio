package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;

/**
 * Row Mapper for table presentazione_istanza.template_email
 */
public class TemplateEmailRowMapper implements RowMapper<TemplateEmailDTO>{

    public TemplateEmailDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TemplateEmailDTO result = new TemplateEmailDTO();
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setCodice(rs.getString("codice"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        result.setDescrizione(rs.getString("descrizione"));
        result.setOggetto(rs.getString("oggetto"));
        result.setTesto(rs.getString("testo"));
        if (rs.getObject("readonly_oggetto") != null)
            result.setReadonlyOggetto(rs.getString("readonly_oggetto"));
        if (rs.getObject("readonly_testo") != null)
            result.setReadonlyTesto(rs.getString("readonly_testo"));
        result.setVisibilita(rs.getString("visibilita"));
        if (rs.getObject("sezione") != null)
            result.setSezione(rs.getString("sezione"));
        if (rs.getObject("tipi_procedimento_ammessi") != null)
            result.setTipiProcedimentoAmmessi(rs.getString("tipi_procedimento_ammessi"));
        if (rs.getObject("protocollazione") != null)
            result.setProtocollazione(rs.getString("protocollazione"));
        if (rs.getObject("placeholders") != null)
            result.setPlaceholders(rs.getString("placeholders"));
        result.setCancellabile(rs.getBoolean("cancellabile"));
        result.setRiservata(rs.getBoolean("riservata"));
        if (rs.getObject("sotto_sezione") != null)
            result.setSottoSezione(rs.getString("sotto_sezione"));
        return result;
    }
}
