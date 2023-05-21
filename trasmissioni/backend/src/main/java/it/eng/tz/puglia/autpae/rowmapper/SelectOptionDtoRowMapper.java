package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dto.SelectOptionDto;

public class SelectOptionDtoRowMapper implements RowMapper<SelectOptionDto> {
	
	@Override
	public SelectOptionDto mapRow(ResultSet rs, int rowNum) throws SQLException{
		SelectOptionDto result = new SelectOptionDto();
	   	result.setValue(rs.getString("value"));
	   	result.setDescription(rs.getString("description"));
	   	result.setLinked(rs.getString("linked"));
	    return result;
	}
}
