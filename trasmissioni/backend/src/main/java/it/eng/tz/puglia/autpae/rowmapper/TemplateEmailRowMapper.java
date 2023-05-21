package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.TemplateEmail;
import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class TemplateEmailRowMapper implements RowMapper<TemplateEmailDTO> {

	public TemplateEmailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TemplateEmailDTO result = new TemplateEmailDTO();
		
		result.setCodice(rs.getObject(TemplateEmail.codice.columnName()) != null ? TipoTemplate.valueOf(rs.getString(TemplateEmail.codice.columnName())) : null);
		result.setNome(rs.getObject(TemplateEmail.nome.columnName()) != null ? rs.getString(TemplateEmail.nome.columnName()) : null);
		result.setDescrizione(rs.getObject(TemplateEmail.descrizione.columnName()) != null ? rs.getString(TemplateEmail.descrizione.columnName()) : null);
		result.setOggetto(rs.getObject(TemplateEmail.oggetto.columnName()) != null ? rs.getString(TemplateEmail.oggetto.columnName()) : null);
		result.setTesto(rs.getObject(TemplateEmail.testo.columnName()) != null ? rs.getString(TemplateEmail.testo.columnName()) : null);
				
		return result;
	}
   
}
