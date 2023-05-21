package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;

public class EnteRowMapper implements RemoteRowMapper<EnteDTO>
{

	@Override
	public EnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		EnteDTO dto = null;
		if(rs != null)
		{
			dto = new EnteDTO();
			dto.setId(rs.getInt("id"));
			dto.setParentId(rs.getInt("parent_id"));
			dto.setNome(rs.getString("nome"));
			dto.setDescrizione(rs.getString("descrizione"));
			dto.setCodice(rs.getString("codice"));
			dto.setPec(rs.getString("pec"));
		}
		return dto;
	}

}
