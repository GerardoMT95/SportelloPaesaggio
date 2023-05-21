package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloCampionamento;
import it.eng.tz.puglia.autpae.entity.FascicoloCampionamentoDTO;

public class FascicoloCampionamentoRowMapper implements RowMapper<FascicoloCampionamentoDTO>
{

	@Override
	public FascicoloCampionamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		FascicoloCampionamentoDTO dto = new FascicoloCampionamentoDTO();
		if(rs != null)
		{
			dto.setIdCampionamento(rs.getObject(FascicoloCampionamento.id_campionamento.columnName()) != null ? rs.getLong(FascicoloCampionamento.id_campionamento.columnName()) : null);
			dto.setIdFascicolo(rs.getObject(FascicoloCampionamento.id_fascicolo.columnName()) != null ? rs.getLong(FascicoloCampionamento.id_fascicolo.columnName()) : null);

		}
		return dto;
	}
	
}
