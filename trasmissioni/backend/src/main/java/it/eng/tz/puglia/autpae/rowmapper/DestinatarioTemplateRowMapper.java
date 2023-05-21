package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;

public class DestinatarioTemplateRowMapper implements RowMapper<DestinatarioTemplateDTO>
{
	@Override
	public DestinatarioTemplateDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		DestinatarioTemplateDTO res = null;
		if(rs != null)
		{
			res = new DestinatarioTemplateDTO();
			res.setCodiceTemplate(TipoTemplate.valueOf(rs.getString("codice_template")));
			res.setId(rs.getLong("id"));
			res.setEmail(rs.getString("email"));
			res.setPec(rs.getBoolean("pec"));
			res.setNome(rs.getString("denominazione"));
			res.setTipo(TipoDestinatario.valueOf(rs.getString("tipo")));
		}
		return res;
	}
}
