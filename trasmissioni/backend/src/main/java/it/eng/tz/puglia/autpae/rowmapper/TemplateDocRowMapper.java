package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.TemplateEmail;
import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class TemplateDocRowMapper implements RowMapper<TemplateDocDTO> {

	public TemplateDocDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		TemplateDocDTO result = new TemplateDocDTO();
		
		result.setNome(rs.getObject(TemplateEmail.nome.columnName()) != null ? rs.getString(TemplateEmail.nome.columnName()) : null);
		result.setDescrizione(rs.getObject(TemplateEmail.descrizione.columnName()) != null ? rs.getString(TemplateEmail.descrizione.columnName()) : null);
				
		return result;
	}
   
}
