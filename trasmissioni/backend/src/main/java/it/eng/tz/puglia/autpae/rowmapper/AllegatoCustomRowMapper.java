package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.dbMapping.AllegatoCorrispondenza;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class AllegatoCustomRowMapper implements RowMapper<AllegatoCustomDTO> {

	public AllegatoCustomDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AllegatoCustomDTO result = new AllegatoCustomDTO();
		
		result.setId(rs.getObject(Allegato.id.columnName()) != null ? rs.getLong(Allegato.id.columnName()) : null);
		result.setNome(rs.getObject(Allegato.nome.columnName()) != null ? rs.getString(Allegato.nome.columnName()) : null);
		result.setDimensione(rs.getObject(Allegato.dimensione.columnName()) != null ? rs.getInt(Allegato.dimensione.columnName()) : null);
		result.setMimeType(rs.getObject(Allegato.mime_type.columnName()) != null ? rs.getString(Allegato.mime_type.columnName()) : null);
		result.setDataCaricamento(Date.from((rs.getObject(Allegato.data_caricamento.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()));
		result.setNrProtocolloIn(rs.getObject(Allegato.numero_protocollo_in.columnName()) != null ? rs.getString(Allegato.numero_protocollo_in.columnName()) : null);
		result.setNrProtocolloOut(rs.getObject(Allegato.numero_protocollo_out.columnName()) != null ? rs.getString(Allegato.numero_protocollo_out.columnName()) : null);
		result.setDataProtocolloIn(rs.getObject(Allegato.data_protocollo_in.columnName()) != null ? Date.from((rs.getObject(Allegato.data_protocollo_in.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setDataProtocolloOut(rs.getObject(Allegato.data_protocollo_out.columnName()) != null ? Date.from((rs.getObject(Allegato.data_protocollo_out.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setChecksum(rs.getObject(Allegato.checksum.columnName()) != null ? rs.getString(Allegato.checksum.columnName()) : null);
		result.setIsUrl(rs.getObject(AllegatoCorrispondenza.is_url.columnName()) != null ? rs.getBoolean(AllegatoCorrispondenza.is_url.columnName()) : null);
		
		return result;
	}
   
}
