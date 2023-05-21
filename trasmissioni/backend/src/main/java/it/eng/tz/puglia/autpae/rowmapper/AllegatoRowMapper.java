package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

/**
 * Row Mapper for table allegato
 */
public class AllegatoRowMapper implements RowMapper<AllegatoDTO> {

	public AllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AllegatoDTO result = new AllegatoDTO();
		
		result.setId(rs.getObject(Allegato.id.columnName()) != null ? rs.getLong(Allegato.id.columnName()) : null);
		result.setNome(rs.getObject(Allegato.nome.columnName()) != null ? rs.getString(Allegato.nome.columnName()) : null);
		result.setTitolo(rs.getObject(Allegato.titolo.columnName()) != null ? rs.getString(Allegato.titolo.columnName()) : null);
		result.setDescrizione(rs.getObject(Allegato.descrizione.columnName()) != null ? rs.getString(Allegato.descrizione.columnName()) : null);
		result.setMimeType(rs.getObject(Allegato.mime_type.columnName()) != null ? rs.getString(Allegato.mime_type.columnName()) : null);
		result.setDataCaricamento(Date.from((rs.getObject(Allegato.data_caricamento.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()));
		result.setContenuto(rs.getObject(Allegato.contenuto.columnName()) != null ? rs.getString(Allegato.contenuto.columnName()) : null);
		result.setChecksum(rs.getObject(Allegato.checksum.columnName()) != null ? rs.getString(Allegato.checksum.columnName()) : null);
		result.setDeleted(rs.getObject(Allegato.deleted.columnName()) != null ? rs.getBoolean(Allegato.deleted.columnName()) : null);
		result.setDimensione(rs.getObject(Allegato.dimensione.columnName()) != null ? rs.getInt(Allegato.dimensione.columnName()) : null);
		result.setNumeroProtocolloIn(rs.getObject(Allegato.numero_protocollo_in.columnName()) != null ? rs.getString(Allegato.numero_protocollo_in.columnName()) : null);
		result.setNumeroProtocolloOut(rs.getObject(Allegato.numero_protocollo_out.columnName()) != null ? rs.getString(Allegato.numero_protocollo_out.columnName()) : null);
		result.setDataProtocolloIn(rs.getObject(Allegato.data_protocollo_in.columnName()) != null ? Date.from((rs.getObject(Allegato.data_protocollo_in.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setDataProtocolloOut(rs.getObject(Allegato.data_protocollo_out.columnName()) != null ? Date.from((rs.getObject(Allegato.data_protocollo_out.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setUtenteInserimento(rs.getObject(Allegato.utente_inserimento.columnName()) != null ? rs.getString(Allegato.utente_inserimento.columnName()) : null);
		result.setUsernameInserimento(rs.getObject(Allegato.username_inserimento.columnName()) != null ? rs.getString(Allegato.username_inserimento.columnName()) : null);
		result.setPathCms(rs.getObject(Allegato.path_cms.columnName()) != null ? rs.getString(Allegato.path_cms.columnName()) : null);
//		result.setTipo(rs.getObject(AllegatoFascicolo.type.columnName()) != null ? rs.getString(AllegatoFascicolo.type.columnName()) : null);
		result.setIdRichiestaPostTrasmissione(rs.getObject(Allegato.id_richiesta_post_trasmissione.columnName()) != null ? rs.getLong(Allegato.id_richiesta_post_trasmissione.columnName()) : null);
		result.setDeletable(rs.getObject(Allegato.deletable.columnName()) != null ? rs.getBoolean(Allegato.deletable.columnName()) : null);
		return result;
	}
   
}
