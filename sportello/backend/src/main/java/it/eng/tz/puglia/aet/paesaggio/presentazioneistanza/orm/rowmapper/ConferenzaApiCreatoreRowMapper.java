package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.cds.bean.ConferenzaApiCreatoreDto;

public class ConferenzaApiCreatoreRowMapper implements RowMapper<ConferenzaApiCreatoreDto>{

	@Override
	public ConferenzaApiCreatoreDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ConferenzaApiCreatoreDto result = new ConferenzaApiCreatoreDto();
        result.setNome(rs.getString("nome"));
        result.setCognome(rs.getString("cognome"));
        result.setCodiceFiscale(rs.getString("codice_fiscale"));
        result.setMail(rs.getString("mail"));
        result.setTelefono(rs.getString("telefono"));
        return result;
	}

}
