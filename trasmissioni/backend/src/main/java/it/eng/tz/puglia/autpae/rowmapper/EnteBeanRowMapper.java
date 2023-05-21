package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;

public class EnteBeanRowMapper implements RowMapper<EnteBean>
{
	@Override
	public EnteBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		EnteBean bean = null;
		if(rs != null)
		{
			bean = new EnteBean();
			bean.setIdEnte(rs.getLong("ente_id"));
			bean.setDenominazione(rs.getString("nomeEnte"));
			bean.setIdOrganizzazioneCompetenze(rs.getLong("id"));
			bean.setTipoEnte(TipoEnte.fromCodice(rs.getString("tipo")));
			bean.setDataTermine(rs.getDate("data_fine_delega"));
		}
		return bean;
	}

}
