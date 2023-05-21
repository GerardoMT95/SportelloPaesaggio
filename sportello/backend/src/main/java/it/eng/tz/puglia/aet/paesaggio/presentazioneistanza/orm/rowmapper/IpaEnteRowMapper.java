package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.IpaEnteDto;

public class IpaEnteRowMapper implements RowMapper<IpaEnteDto>{

	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public IpaEnteDto mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final IpaEnteDto result = new IpaEnteDto();
		result.setCodiceFiscale(rs.getString("codice_fiscale"));
		result.setCodiceIpa(rs.getString("codice_ipa"));
		result.setId(rs.getLong("id"));
		result.setNome(rs.getString("denominazione_ente"));
		return result;
	}

}
