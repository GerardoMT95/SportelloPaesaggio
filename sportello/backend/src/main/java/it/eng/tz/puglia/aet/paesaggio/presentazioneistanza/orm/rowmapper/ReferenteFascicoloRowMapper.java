package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferenteFascicoloDTO;

public class ReferenteFascicoloRowMapper implements RowMapper<ReferenteFascicoloDTO>{

	@Override
	public ReferenteFascicoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        final ReferenteFascicoloDTO result = new ReferenteFascicoloDTO();
        result.setId((java.util.UUID) rs.getObject("id"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("cognome") != null)
            result.setCognome(rs.getString("cognome"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        if (rs.getObject("mail") != null)
            result.setMail(rs.getString("mail"));
        if (rs.getObject("codice_fiscale") != null)
            result.setCodiceFiscale(rs.getString("codice_fiscale"));
        if (rs.getObject("tipo_referente") != null)
            result.setTipoReferente(rs.getString("tipo_referente"));
        return result;
	}

}
