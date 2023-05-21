package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;

/**
 * Row Mapper for table presentazione_istanza.parere_soprintendenza
 */
public class ParereSoprintendenzaRowMapper implements RowMapper<ParereSoprintendenzaDTO>
{

	public ParereSoprintendenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final ParereSoprintendenzaDTO result = new ParereSoprintendenzaDTO();
		result.setId(rs.getLong("id"));
		result.setIdPratica((java.util.UUID) rs.getObject("id_pratica"));
		if (rs.getObject("numero_protocollo") != null)
			result.setNumeroProtocollo(rs.getString("numero_protocollo"));
		if (rs.getObject("nominativo_istruttore") != null)
			result.setNominativoIstruttore(rs.getString("nominativo_istruttore"));
		if(rs.getObject("esito_parere") != null)
			result.setEsitoParere(EsitoParere.valueOf(rs.getString("esito_parere")));
		if (rs.getObject("note") != null)
			result.setNote(rs.getString("note"));
		if (rs.getObject("username_utente_creazione") != null)
			result.setUsernameUtenteCreazione(rs.getString("username_utente_creazione"));
		if (rs.getObject("dettaglio_prescrizione") != null)
			result.setDettaglioPrescrizione(rs.getString("dettaglio_prescrizione"));
		if (rs.getObject("data_inserimento") != null)
			result.setDataInserimento(rs.getTimestamp("data_inserimento"));
		result.setOrganizzazioneCreazione(rs.getLong("organizzazione_creazione"));
		result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
		return result;
	}
}
