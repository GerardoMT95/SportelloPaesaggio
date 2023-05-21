package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;

public class VCdsAzioneRowMapper implements RowMapper<SelectParentItemDto>{
	
	public SelectParentItemDto mapRow(ResultSet rs, int rowNum) throws SQLException{
        final SelectParentItemDto result = new SelectParentItemDto();
        if (rs.getObject("label") != null)
            result.setLabel(rs.getString("label"));
        if (rs.getObject("value") != null)
            result.setValue(rs.getString("value"));
        if (rs.getObject("attivita") != null)
            result.setParent(rs.getString("attivita"));
        return result;
    }
}