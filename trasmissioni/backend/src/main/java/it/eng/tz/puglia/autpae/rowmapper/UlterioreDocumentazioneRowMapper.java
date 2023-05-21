package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.dto.UlterioreDocumentazioneDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class UlterioreDocumentazioneRowMapper implements RowMapper<UlterioreDocumentazioneDTO> {

	public UlterioreDocumentazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UlterioreDocumentazioneDTO result = new UlterioreDocumentazioneDTO();
		
		result.setId(rs.getObject(Allegato.id.columnName()) != null ? rs.getLong(Allegato.id.columnName()) : null);
		result.setNome(rs.getObject(Allegato.nome.columnName()) != null ? rs.getString(Allegato.nome.columnName()) : null);
		result.setTitolo(rs.getObject(Allegato.titolo.columnName()) != null ? rs.getString(Allegato.titolo.columnName()) : null);
		result.setDescrizione(rs.getObject(Allegato.descrizione.columnName()) != null ? rs.getString(Allegato.descrizione.columnName()) : null);
		result.setUtenteInserimento(rs.getObject(Allegato.utente_inserimento.columnName()) != null ? rs.getString(Allegato.utente_inserimento.columnName()) : null);
		result.setDataCaricamento(rs.getObject(Allegato.data_caricamento.columnName()) != null ? rs.getDate(Allegato.data_caricamento.columnName()) : null);
		result.setMimeType(rs.getObject(Allegato.mime_type.columnName()) != null ? rs.getString(Allegato.mime_type.columnName()) : null);
		result.setDimensione(rs.getObject(Allegato.dimensione.columnName()) != null ? rs.getInt(Allegato.dimensione.columnName()) : null);
		
		return result;
	}
   
}
