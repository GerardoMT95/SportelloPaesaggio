package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;

/**
 * Row Mapper for table presentazione_istanza.provvedimento_finale
 */
public class ProvvedimentoFinaleRowMapper implements RowMapper<ProvvedimentoFinaleDTO>
{

	public ProvvedimentoFinaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final ProvvedimentoFinaleDTO result = new ProvvedimentoFinaleDTO();
		result.setId(rs.getLong("id"));
		result.setIdPratica((java.util.UUID) rs.getObject("id_pratica"));
		if (rs.getObject("numero_provvedimento") != null)
			result.setNumeroProvvedimento(rs.getString("numero_provvedimento"));
		if (rs.getObject("data_rilascio_autorizzazione") != null)
			result.setDataRilascioAutorizzazione(rs.getTimestamp("data_rilascio_autorizzazione"));
		if (rs.getObject("esito_provvedimento") != null)
			result.setEsitoProvvedimento(EsitoParere.valueOf(rs.getString("esito_provvedimento")));
		if (rs.getObject("rup") != null)
			result.setRup(rs.getString("rup"));
		if (rs.getObject("draft") != null)
			result.setDraft(rs.getBoolean("draft"));
		if (rs.getObject("id_corrispondenza") != null)
			result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
		return result;
	}
}
