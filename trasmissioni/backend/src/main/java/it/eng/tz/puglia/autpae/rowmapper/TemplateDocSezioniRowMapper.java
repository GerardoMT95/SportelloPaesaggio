package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.TemplateDocSezioni;
import it.eng.tz.puglia.autpae.entity.TemplateDocSezioneDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoSezioneDoc;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class TemplateDocSezioniRowMapper implements RowMapper<TemplateDocSezioneDTO> {

	public TemplateDocSezioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TemplateDocSezioneDTO result = new TemplateDocSezioneDTO();
		
		result.setNome(rs.getObject(TemplateDocSezioni.nome.columnName()) != null ? rs.getString(TemplateDocSezioni.nome.columnName()) : null);
		result.setTemplateDocnome(rs.getObject(TemplateDocSezioni.template_doc_nome.columnName()) != null ? rs.getString(TemplateDocSezioni.template_doc_nome.columnName()) : null);
		result.setIdAllegato(rs.getObject(TemplateDocSezioni.id_allegato.columnName()) != null ? rs.getLong(TemplateDocSezioni.id_allegato.columnName()) : null);
		result.setValore(rs.getObject(TemplateDocSezioni.valore.columnName()) != null ? rs.getString(TemplateDocSezioni.valore.columnName()) : null);		
		result.setTipoSezione(rs.getObject(TemplateDocSezioni.tipo_sezione.columnName()) != null ? TipoSezioneDoc.valueOf(rs.getString(TemplateDocSezioni.tipo_sezione.columnName())) : null);
		return result;
	}
   
}
