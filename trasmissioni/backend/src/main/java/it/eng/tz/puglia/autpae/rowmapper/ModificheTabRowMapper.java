package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ModificheTab;
import it.eng.tz.puglia.autpae.entity.ModificheTabDTO;
import it.eng.tz.puglia.autpae.enumeratori.NomiTab;

/**
 * Row Mapper for table autpae.modifiche_tab
 */
public class ModificheTabRowMapper implements RowMapper<ModificheTabDTO> {

	public ModificheTabDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ModificheTabDTO result = new ModificheTabDTO();
		
		result.setHashcode(rs.getInt(ModificheTab.hashcode.columnName()));
		result.setTab(NomiTab.valueOf(rs.getString(ModificheTab.tab.columnName())));
		result.setInfo(rs.getString(ModificheTab.info.columnName()));
		
		return result;
	}
   
}