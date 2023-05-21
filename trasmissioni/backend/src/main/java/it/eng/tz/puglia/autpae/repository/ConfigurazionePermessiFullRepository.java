package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazionePermessi;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazionePermessiBaseRepository;

/**
 * Full Repository for Configurazione_permessi
 */
@Repository
public class ConfigurazionePermessiFullRepository extends ConfigurazionePermessiBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(ConfigurazionePermessiFullRepository.class);
	
	
	public void insertAll(List<ConfigurazionePermessiDTO> listaPermessi) throws Exception	{
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("insert into ")
		   .append(ConfigurazionePermessi.getTableName())
		   .append(" ( ")
		   .append(ConfigurazionePermessi.codice_ente            	  .columnName()) .append(", ")
		   .append(ConfigurazionePermessi.permesso_comunicazione 	  .columnName()) .append(", ")
		   .append(ConfigurazionePermessi.permesso_documentazione	  .columnName()) .append(", ")
		   .append(ConfigurazionePermessi.permesso_osservazione		  .columnName()) .append(", ")
		   .append(ConfigurazionePermessi.stato_registrazione_pubblico.columnName()) .append(", ")
		   .append(ConfigurazionePermessi.esito_verifica_pubblico	  .columnName())
		   .append(" ) ")
		   .append(" values ");
		for (int i = 0; i < listaPermessi.size(); i++) {
			ConfigurazionePermessiDTO configurazionePermessi = listaPermessi.get(i);
			sql.append(" ( ")
			   .append(":").append(ConfigurazionePermessi.codice_ente            	  .columnName() + i) .append(", ")
			   .append(":").append(ConfigurazionePermessi.permesso_comunicazione 	  .columnName() + i) .append(", ")
			   .append(":").append(ConfigurazionePermessi.permesso_documentazione	  .columnName() + i) .append(", ")
			   .append(":").append(ConfigurazionePermessi.permesso_osservazione		  .columnName() + i) .append(", ")
			   .append(":").append(ConfigurazionePermessi.stato_registrazione_pubblico.columnName() + i) .append(", ")
			   .append(":").append(ConfigurazionePermessi.esito_verifica_pubblico	  .columnName() + i)
			   .append(" ) ");
			if ((i + 1) < listaPermessi.size()) {
				sql.append(", ");
			}
			parameters.put(ConfigurazionePermessi.codice_ente			 	  .columnName() + i, configurazionePermessi.getCodiceEnte());
			parameters.put(ConfigurazionePermessi.permesso_comunicazione 	  .columnName() + i, configurazionePermessi.isPermessoComunicazione());
			parameters.put(ConfigurazionePermessi.permesso_documentazione	  .columnName() + i, configurazionePermessi.isPermessoDocumentazione());
			parameters.put(ConfigurazionePermessi.permesso_osservazione  	  .columnName() + i, configurazionePermessi.isPermessoOsservazione());
			parameters.put(ConfigurazionePermessi.stato_registrazione_pubblico.columnName() + i, configurazionePermessi.getStatoRegistrazionePubblico());
			parameters.put(ConfigurazionePermessi.esito_verifica_pubblico	  .columnName() + i, configurazionePermessi.getEsitoVerificaPubblico());
		}
		log.trace("Sql -> {} Parameters -> {}", sql, parameters.toString());
		namedJdbcTemplate.update(sql.toString(), parameters);
	}

	public int deleteAll() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder("TRUNCATE TABLE ").append(ConfigurazionePermessi.getTableName());

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), null);
		return super.update(sql.toString());
	}

}