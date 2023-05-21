package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;


public class PlainNumberValueLabelRowMapper implements RowMapper<PlainNumberValueLabel> {

public PlainNumberValueLabel mapRow(ResultSet rs, int rowNum) throws SQLException{
	PlainNumberValueLabel result = new PlainNumberValueLabel();
   	result.setValue(rs.getLong("value"));
   	result.setDescription(rs.getString("label"));
   	
    return result;
}

}
