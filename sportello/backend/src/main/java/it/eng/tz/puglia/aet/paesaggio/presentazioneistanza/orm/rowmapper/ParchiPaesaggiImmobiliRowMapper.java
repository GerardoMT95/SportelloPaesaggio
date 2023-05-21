package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParchiPaesaggiImmobiliDTO;

/**
 * Row Mapper for table presentazione_istanza.parchi_paesaggi_immobili
 */
public class ParchiPaesaggiImmobiliRowMapper implements RowMapper<ParchiPaesaggiImmobiliDTO>{

    public ParchiPaesaggiImmobiliDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ParchiPaesaggiImmobiliDTO result = new ParchiPaesaggiImmobiliDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setComuneId(rs.getLong("comune_id"));
        result.setTipoVincolo(rs.getString("tipo_vincolo"));
        result.setCodice(rs.getString("codice"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("selezionato") != null)
            result.setSelezionato(rs.getString("selezionato"));
        if (rs.getObject("info") != null)
            result.setInfo(rs.getString("info"));
        if (rs.getObject("data_inserimento") != null)
            result.setDataInserimento(rs.getDate("data_inserimento"));
        if (rs.getObject("tipo_variazione") != null)
        	result.setTipoVariazione(rs.getString("tipo_variazione").charAt(0));
        result.setNotifica(rs.getBoolean("notifica"));
        return result;
    }
}
