package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Parchi;

public class TipologicaAnagraficaRowMapper implements RowMapper<TipologicaDTO> {

	@Override
	public TipologicaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipologicaDTO dto = new TipologicaDTO();
		
		if (rs.getObject(Anagrafica_Parchi.codice.     columnName()) != null) 	dto.setCodice(rs.getString(Anagrafica_Parchi.codice.     columnName()));
		if (rs.getObject(Anagrafica_Parchi.descrizione.columnName()) != null) 	dto.setLabel (rs.getString(Anagrafica_Parchi.descrizione.columnName()));
		
		return dto;
	}

}
