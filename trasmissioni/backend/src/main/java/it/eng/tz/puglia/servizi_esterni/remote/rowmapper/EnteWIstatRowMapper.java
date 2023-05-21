package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteWIstatDTO;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;

public class EnteWIstatRowMapper implements CommonRowMapper<EnteWIstatDTO>
{

	@Override
	public EnteWIstatDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		EnteWIstatDTO dto = null;
		if(rs != null)
		{
			dto = new EnteWIstatDTO();
			dto.setId(rs.getInt("id"));
			dto.setParentId(rs.getInt("parent_id"));
			dto.setNome(rs.getString("nome"));
			dto.setDescrizione(rs.getString("descrizione"));
			dto.setCodice(rs.getString("codice"));
			dto.setPec(rs.getString("pec"));
			dto.setTipo(TipoEnte.fromCodice(rs.getString("tipo")));
			dto.setIstat(rs.getString("cod_istat"));
		}
		return dto;
	}
}
