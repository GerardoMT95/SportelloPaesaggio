package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Ricevuta;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoErrore;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class RicevutaRowMapper implements RowMapper<RicevutaDTO> {

	public RicevutaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		RicevutaDTO result = new RicevutaDTO();
		
		result.setId(rs.getObject(Ricevuta.id.columnName()) != null ? rs.getLong(Ricevuta.id.columnName()) : null);
		result.setIdCorrispondenza(rs.getObject(Ricevuta.id_corrispondenza.columnName()) != null ? rs.getLong(Ricevuta.id_corrispondenza.columnName()) : null);
		result.setIdDestinatario(rs.getObject(Ricevuta.id_destinatario.columnName()) != null ? rs.getLong(Ricevuta.id_destinatario.columnName()) : null);
		result.setTipoRicevuta(rs.getObject(Ricevuta.tipo_ricevuta.columnName()) != null ? TipoRicevuta.valueOf(rs.getString(Ricevuta.tipo_ricevuta.columnName())) : null);
		result.setErrore(rs.getObject(Ricevuta.errore.columnName()) != null ? TipoErrore.valueOf(rs.getString(Ricevuta.errore.columnName())) : null);
		result.setDescrizioneErrore(rs.getObject(Ricevuta.descrizione_errore.columnName()) != null ? rs.getString(Ricevuta.descrizione_errore.columnName()) : null);
		result.setIdCmsEml(rs.getObject(Ricevuta.id_cms_eml.columnName()) != null ? rs.getString(Ricevuta.id_cms_eml.columnName()) : null);
		result.setIdCmsDatiCert(rs.getObject(Ricevuta.id_cms_dati_cert.columnName()) != null ? rs.getString(Ricevuta.id_cms_dati_cert.columnName()) : null);
		result.setIdCmsSmime(rs.getObject(Ricevuta.id_cms_smime.columnName()) != null ? rs.getString(Ricevuta.id_cms_smime.columnName()) : null);
		result.setData(rs.getObject(Ricevuta.data.columnName()) != null ? Date.from((rs.getObject(Ricevuta.data.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
		result.setIdAllegatoDatiCert(rs.getObject(Ricevuta.id_allegato_dati_cert.columnName()) != null ? rs.getLong(Ricevuta.id_allegato_dati_cert.columnName()) : null);
				
		return result;
	}
   
}