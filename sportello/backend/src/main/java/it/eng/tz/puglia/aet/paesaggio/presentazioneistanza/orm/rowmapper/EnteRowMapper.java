package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.EnteDto;

public class EnteRowMapper implements RowMapper<EnteDto>{

	@Override
	public EnteDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final EnteDto result = new EnteDto();
        result.setNome(rs.getString("nome"));
        result.setCodice(rs.getString("codice"));
        result.setCodiceFiscale(rs.getString("codice_fiscale"));
        result.setPec(rs.getString("pec"));
        result.setTipoEnte(rs.getString("tipo"));
        return result;
	}

}
