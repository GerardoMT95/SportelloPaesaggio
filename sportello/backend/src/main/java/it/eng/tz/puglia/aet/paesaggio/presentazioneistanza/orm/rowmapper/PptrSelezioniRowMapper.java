package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;

/**
 * Row Mapper for table presentazione_istanza.pptr_selezioni
 */
public class PptrSelezioniRowMapper implements RowMapper<PptrSelezioniDTO>
{

	public PptrSelezioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final PptrSelezioniDTO result = new PptrSelezioniDTO();
		result.setIdPratica((java.util.UUID) rs.getObject("id_pratica"));
		result.setCodice(rs.getString("codice"));
		if (rs.getObject("specifica") != null)
			result.setSpecifica(rs.getString("specifica"));
		if (rs.getObject("sezione") != null)
			result.setSezione(rs.getString("sezione"));
		return result;
	}
}
