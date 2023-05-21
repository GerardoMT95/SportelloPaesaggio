package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoAziendaDTO;

public class TipoAziendaRowMapper implements RowMapper<TipoAziendaDTO>{

	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public TipoAziendaDTO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final TipoAziendaDTO result = new TipoAziendaDTO();
		result.setId(rs.getString("id"));
		result.setNome(rs.getString("nome"));
		result.setPrivato(rs.getBoolean("privato"));
		return result;
	}

}
