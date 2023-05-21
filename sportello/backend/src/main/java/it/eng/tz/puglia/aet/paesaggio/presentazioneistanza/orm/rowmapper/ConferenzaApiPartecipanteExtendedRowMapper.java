package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ConferenzaApiPartecipantiExtendedDto;

public class ConferenzaApiPartecipanteExtendedRowMapper implements RowMapper<ConferenzaApiPartecipantiExtendedDto>{

	@Override
	public ConferenzaApiPartecipantiExtendedDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ConferenzaApiPartecipantiExtendedDto result = new ConferenzaApiPartecipantiExtendedDto();
        result.setCfEnte(rs.getString("codice_fiscale"));
        result.setNomeEnte(rs.getString("nome_ente"));
        result.setPecEnte(rs.getString("pec"));
        result.setCodice(rs.getString("codice"));
        result.setCodiceTipo(rs.getString("tipo"));
        return result;
	}

}
