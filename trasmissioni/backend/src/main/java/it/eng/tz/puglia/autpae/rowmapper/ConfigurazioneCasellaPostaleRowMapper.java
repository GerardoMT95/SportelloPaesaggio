package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneCasellaPostale;
import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;

public class ConfigurazioneCasellaPostaleRowMapper implements RowMapper<ConfigurazioneCasellaPostaleDTO> {

	@Override
	public ConfigurazioneCasellaPostaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConfigurazioneCasellaPostaleDTO ccpDTO = new ConfigurazioneCasellaPostaleDTO();
		if (rs.getObject(ConfigurazioneCasellaPostale.email.columnName()) != null) {
			ccpDTO.setEmail(rs.getString(ConfigurazioneCasellaPostale.email.columnName()));
		}
		if (rs.getObject(ConfigurazioneCasellaPostale.configurazione.columnName()) != null) {
			ccpDTO.setConfigurazione(rs.getString(ConfigurazioneCasellaPostale.configurazione.columnName()));
		}
		return ccpDTO;
	}

}
