package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.servizi_esterni.remote.dto.AutoCompleteDTO;

public class AutocompleteRowMapper implements RowMapper<AutoCompleteDTO>
{
	private String columnLabel;
	private String columnValue;
	
	public AutocompleteRowMapper(String columnLabel, String columnValue)
	{
		this.columnLabel = columnLabel;
		this.columnValue = columnValue;
	}

	public String getColumnLabel()
	{
		return columnLabel;
	}
	public void setColumnLabel(String columnLabel)
	{
		this.columnLabel = columnLabel;
	}

	public String getColumnValue()
	{
		return columnValue;
	}
	public void setColumnValue(String columnValue)
	{
		this.columnValue = columnValue;
	}

	@Override
	public AutoCompleteDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		AutoCompleteDTO dto = new AutoCompleteDTO();
		if(rs != null)
		{
			dto.setLabel(rs.getObject(columnLabel) != null ? rs.getString(columnLabel) : null);
			dto.setValue(rs.getObject(columnValue) != null ? rs.getString(columnValue) : null);
		}
		return dto;
	}

}
