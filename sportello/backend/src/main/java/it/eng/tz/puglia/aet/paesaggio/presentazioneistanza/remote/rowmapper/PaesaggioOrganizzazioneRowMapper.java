package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.enumeration.TipoOrganizzazione;

public class PaesaggioOrganizzazioneRowMapper implements RowMapper<PaesaggioOrganizzazioneDTO>
{

	@Override
	public PaesaggioOrganizzazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		PaesaggioOrganizzazioneDTO res = null;
		if(rs != null)
		{
			res = new PaesaggioOrganizzazioneDTO();
			res.setId(rs.getLong("id"));
			res.setEnteId(rs.getLong("ente_id"));
			res.setDenominazione(rs.getString("denominazione"));
			res.setRiferimentoOrganizzazione(rs.getLong("riferimento_organizzazione"));
			res.setTipoOrganizzazione(rs.getObject("tipo_organizzazione") != null ? TipoOrganizzazione.valueOf(rs.getString("tipo_organizzazione")) : null);
			res.setTipoOrganizzazioneSpecifica(rs.getString("tipo_organizzazione_specifica"));
			res.setCodiceCivilia(rs.getString("codice_civilia"));
			res.setDataCreazione(rs.getDate("data_creazione"));
			res.setDataTermine(rs.getDate("data_termine"));
		}
		return res;
	}

}
