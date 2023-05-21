package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.Corrispondenza;
import it.eng.tz.puglia.autpae.repository.base.CorrispondenzaBaseRepository;

/**
 * Full Repository for table corrispondenza
 */
@Repository
public class CorrispondenzaFullRepository  extends CorrispondenzaBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(CorrispondenzaFullRepository.class);
	
	
	public int updateMessageId(Long idCorrispondenza, String messageId) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("update ")
		   .append(Corrispondenza.getTableName())
		   .append(" set "  )
		   .append(Corrispondenza.message_id.columnName())
		   .append(" = :")
		   .append(Corrispondenza.message_id.columnName())
		   .append(" where ")
		   .append(Corrispondenza.id.columnName())
		   .append(" = :")
		   .append(Corrispondenza.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Corrispondenza.id.columnName(), idCorrispondenza);
		parameters.put(Corrispondenza.message_id.columnName(), messageId);
		
		log.trace("Eseguo questa query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int updateBozza(Long idCorrispondenza, Boolean bozza) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("update ")
		   .append(Corrispondenza.getTableName())
		   .append(" set "  )
		   .append(Corrispondenza.bozza.columnName())
		   .append(" = :")
		   .append(Corrispondenza.bozza.columnName())
		   .append(" where ")
		   .append(Corrispondenza.id.columnName())
		   .append(" = :")
		   .append(Corrispondenza.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Corrispondenza.id.columnName(), idCorrispondenza);
		parameters.put(Corrispondenza.bozza.columnName(), bozza);
		
		log.trace("Eseguo questa query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	/**
	 * @autor Adriano Colaianni
	 * @date 27 apr 2022
	 * @param idCorrispondenza
	 * @param mittenteUsername
	 * @param mittenteDenominazione
	 * @return
	 */
	public int updateMittenteUsernameAndMittenteDenominazione(Long idCorrispondenza, String mittenteUsername,
			String mittenteDenominazione,String mittenteEnte) {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Corrispondenza.getTableName())
		   .append(" set "  )
		   .append(Corrispondenza.mittente_username.columnName())
		   .append(" = :")
		   .append(Corrispondenza.mittente_username.columnName())
		   .append(" , ")
		   .append(Corrispondenza.mittente_denominazione.columnName())
		   .append(" = :")
		   .append(Corrispondenza.mittente_denominazione.columnName())
		   .append(" , ")
		   .append(Corrispondenza.mittente_ente.columnName())
		   .append(" = :")
		   .append(Corrispondenza.mittente_ente.columnName())
		   .append(" where ")
		   .append(Corrispondenza.id.columnName())
		   .append(" = :")
		   .append(Corrispondenza.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Corrispondenza.id.columnName(), idCorrispondenza);
		parameters.put(Corrispondenza.mittente_denominazione.columnName(), mittenteDenominazione);
		parameters.put(Corrispondenza.mittente_username.columnName(), mittenteUsername);
		parameters.put(Corrispondenza.mittente_ente.columnName(), mittenteEnte);
		
		log.trace("Eseguo questa query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
}