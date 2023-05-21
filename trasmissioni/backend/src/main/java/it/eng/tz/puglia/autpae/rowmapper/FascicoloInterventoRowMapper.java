package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloIntervento;
import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;

public class FascicoloInterventoRowMapper implements RowMapper<FascicoloInterventoDTO> {

	@Override
	public FascicoloInterventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FascicoloInterventoDTO dto = new FascicoloInterventoDTO();
		if (rs.getObject(FascicoloIntervento.id_fascicolo.columnName()) != null) {
			dto.setIdFascicolo(rs.getLong(FascicoloIntervento.id_fascicolo.columnName()));
		}
		if (rs.getObject(FascicoloIntervento.id_tipi_qualificazioni.columnName()) != null) {
			dto.setIdTipiQualificazioni(rs.getLong(FascicoloIntervento.id_tipi_qualificazioni.columnName()));
		}
		return dto;
	}

}
