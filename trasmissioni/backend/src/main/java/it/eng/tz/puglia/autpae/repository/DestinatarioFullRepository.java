package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.repository.base.DestinatarioBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.DestinatarioRowMapper;

/**
 * Full Repository for table destinatario
 */
@Repository
public class DestinatarioFullRepository  extends DestinatarioBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(DestinatarioFullRepository.class);
    private final DestinatarioRowMapper rowMapper = new DestinatarioRowMapper();
    
	
	public List<DestinatarioDTO> searchByIdCorrispondenzaAndEmails(Long idCorrispondenza, List<String> emails) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * ")
		   .append(" from ")
		   .append(Destinatario.getTableName())
		   .append(" where ")
		   .append(Destinatario.id_corrispondenza.columnName())
		   .append(" = :")
		   .append(Destinatario.id_corrispondenza.columnName())
		   .append(" and ")
		   .append(Destinatario.email.columnName())
		   .append(" in ")    	   
		   .append("( :emails )");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Destinatario.id_corrispondenza.columnName(), idCorrispondenza);
		parameters.put("emails", (emails!=null && emails.isEmpty()) ? null : emails);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters.toString());
		return namedJdbcTemplate.query(sql.toString(), parameters, rowMapper);
	}
	
	public int updateFieldPec(List<Long> listaId, Boolean pec) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Destinatario.getTableName())
		   .append(" set ")
		   .append(Destinatario.pec.columnName())
		   .append(" = :")
		   .append(Destinatario.pec.columnName())
		   .append(" where ")
		   .append(Destinatario.id.columnName())
		   .append(" in ")
		   .append("( :listaId )");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Destinatario.pec.columnName(), pec);
		parameters.put("listaId", (listaId!=null && listaId.isEmpty()) ? null : listaId);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters.toString());
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

}