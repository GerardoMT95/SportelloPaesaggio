package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CLEntiCommissioniDTO;

public class EntiCommissioniRowMapper implements RowMapper<CLEntiCommissioniDTO>
{

	@Override
	public CLEntiCommissioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		CLEntiCommissioniDTO result = null;
		if(rs != null)
		{
			result = new CLEntiCommissioniDTO();
			result.setIdEnte(rs.getLong("id_ente"));
			result.setIdCommissione(rs.getLong("id_commissione"));
			result.setNomeEnte(rs.getString("nome_ente"));
			result.setNomeCommissione(rs.getString("nome_commissione"));
			result.setDataInizioVal(rs.getDate("data_inizio_val"));
			result.setDataTermineVal(rs.getDate("data_termine_val"));
		}
		return result;
	}

}
