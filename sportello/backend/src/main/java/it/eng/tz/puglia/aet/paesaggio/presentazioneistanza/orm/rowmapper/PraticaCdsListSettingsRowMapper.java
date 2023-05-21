package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PraticaCdsListSettingsDto;

public class PraticaCdsListSettingsRowMapper implements RowMapper<PraticaCdsListSettingsDto> {
    public PraticaCdsListSettingsDto mapRow(ResultSet rs, int rowNum) throws SQLException{
    	final PraticaCdsListSettingsDto result = new PraticaCdsListSettingsDto();
        result.setId(rs.getString("id"));
        result.setIdCds(rs.getObject("id_cds") == null ? null : rs.getLong("id_cds"));
        if (rs.getObject("tipo") != null)
            result.setTipo(rs.getString("tipo"));
        if (rs.getObject("attivita") != null)
            result.setAttivita(rs.getString("attivita"));
        if (rs.getObject("azione") != null)
            result.setAzione(rs.getString("azione"));
        if (rs.getObject("completed") != null)
            result.setCompleted(rs.getBoolean("completed"));
    	return result;
    }

}
