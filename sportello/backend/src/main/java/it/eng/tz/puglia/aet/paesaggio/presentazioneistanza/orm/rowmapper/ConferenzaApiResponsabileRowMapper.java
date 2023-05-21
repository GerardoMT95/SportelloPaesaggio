package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.cds.bean.ConferenzaApiResponsabileDto;

public class ConferenzaApiResponsabileRowMapper implements RowMapper<ConferenzaApiResponsabileDto> {

	@Override
	public ConferenzaApiResponsabileDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ConferenzaApiResponsabileDto result = new ConferenzaApiResponsabileDto();
        result.setNome(rs.getString("nome"));
        result.setCognome(rs.getString("cognome"));
        result.setCodiceFiscale(rs.getString("codice_fiscale"));
        result.setMail(rs.getString("mail"));
        return result;
	}

}
