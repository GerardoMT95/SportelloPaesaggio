package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StoricoRichiesta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SospensioneArchiviazioneAttivazioneDTO;

/**
 * Row Mapper for table
 * presentazione_istanza.sospensione_archiviazione_attivazione
 */
public class SospensioneArchiviazioneAttivazioneRowMapper implements RowMapper<SospensioneArchiviazioneAttivazioneDTO>
{

	public SospensioneArchiviazioneAttivazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final SospensioneArchiviazioneAttivazioneDTO result = new SospensioneArchiviazioneAttivazioneDTO();
		result.setId(rs.getLong("id"));
		result.setIdPratica( UUID.fromString(rs.getString("id_pratica")));
		result.setType(rs.getObject("type") != null ? StoricoRichiesta.valueOf(rs.getString("type")) : null);
		result.setDraft(rs.getBoolean("draft"));
		if (rs.getObject("note") != null) result.setNote(rs.getString("note"));
		if (rs.getObject("data") != null) result.setData(rs.getDate("data"));
		result.setUsernameUtenteCreazione(rs.getString("username_utente_creazione"));
		if (rs.getObject("stato_pratica") != null) result.setStatoPratica(AttivitaDaEspletare.valueOf(rs.getString("stato_pratica")));
		result.setCodicePratica(rs.getString("codice_pratica"));
		return result;
	}
}
