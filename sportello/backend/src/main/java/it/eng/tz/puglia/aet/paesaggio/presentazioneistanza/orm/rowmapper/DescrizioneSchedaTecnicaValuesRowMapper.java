package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DescrizioneSchedaTecnicaValuesDTO;

/**
 * Row Mapper for table presentazione_istanza.descrizione_scheda_tecnica_values
 */
public class DescrizioneSchedaTecnicaValuesRowMapper implements RowMapper<DescrizioneSchedaTecnicaValuesDTO>{

    public DescrizioneSchedaTecnicaValuesDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final DescrizioneSchedaTecnicaValuesDTO result = new DescrizioneSchedaTecnicaValuesDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        result.setCodice(rs.getString("codice"));
        if (rs.getObject("text") != null)
            result.setText(rs.getString("text"));
        if (rs.getObject("sezione") != null)
            result.setSezione(TipoQualificazione.valueOf(rs.getString("sezione")));
        return result;
    }
}
