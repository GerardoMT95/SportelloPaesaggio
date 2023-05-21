package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.TemplateDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.base.TemplateDestinatarioBaseRepository;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for table template_destinatario
 */
@Repository
public class TemplateDestinatarioFullRepository  extends TemplateDestinatarioBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(TemplateDestinatarioFullRepository.class);
	
	
	public int deleteAll(TipoTemplate codice) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", TemplateDestinatario.getTableName()));

		sql.append(" where ")
			.append(TemplateDestinatario.codice_template.columnName())
			.append(" = :")
			.append(TemplateDestinatario.codice_template.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(TemplateDestinatario.codice_template.columnName(), codice.name());
		
        log.trace("Sql -> {} Parameters -> {}", sql, parameters);
        return namedJdbcTemplate.update(sql.toString(), parameters);
	}

}