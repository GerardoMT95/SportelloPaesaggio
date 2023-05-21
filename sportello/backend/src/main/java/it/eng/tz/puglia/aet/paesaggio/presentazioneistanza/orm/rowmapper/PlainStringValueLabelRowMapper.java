package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;


public class PlainStringValueLabelRowMapper implements RowMapper<PlainStringValueLabel> {

	public PlainStringValueLabel mapRow(ResultSet rs, int rowNum) throws SQLException{

		PlainStringValueLabel result = new PlainStringValueLabel();

		result.setValue(rs.getString("value"));
		result.setDescription(rs.getString("label"));
		if(this.isColumnPresent(rs, "linked")) {
			result.setLinked(rs.getString("linked"));
		}
		return result;
	}
	
	public boolean isColumnPresent(ResultSet rs, String column) {
    	try {
    		rs.getObject(column);
    	} catch (Exception e) {
    		return false;
    	}
    	return true;
    }

}
