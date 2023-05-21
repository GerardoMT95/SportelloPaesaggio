package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazionePermessi;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.rowmapper.ConfigurazionePermessiRowMapper;
import it.eng.tz.puglia.autpae.search.ConfigurazionePermessiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;

/**
 * Repository for table configurazione_permessi
 */
public class ConfigurazionePermessiBaseRepository extends GenericCrudDao<ConfigurazionePermessiDTO, ConfigurazionePermessiSearch, String>
{
	private static final Logger log = LoggerFactory.getLogger(ConfigurazionePermessiBaseRepository.class);
	private ConfigurazionePermessiRowMapper rowMapper = new ConfigurazionePermessiRowMapper();
	
	@Override
	public List<ConfigurazionePermessiDTO> select() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		StringBuilder sql = new StringBuilder("select * from ").append(ConfigurazionePermessi.getTableName());
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), null);
		return super.queryForList(sql.toString(), rowMapper);
	}

	@Override
	public long count(ConfigurazionePermessiSearch filter) throws Exception	{
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder("select count(*) from ").append(ConfigurazionePermessi.getTableName());
		filter.getSqlWhereClause(sql);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filter);
		return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public ConfigurazionePermessiDTO find(String pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("select * from ")
									.append(ConfigurazionePermessi.getTableName())
									.append(" where ")
									.append(ConfigurazionePermessi.codice_ente.getCompleteName())
									.append(" = :")
									.append(ConfigurazionePermessi.codice_ente.columnName());
		parameters.put(ConfigurazionePermessi.codice_ente.columnName(), pk);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), pk);
		try {
			return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
		} catch (DataAccessException e) {
			log.info("Configurazione Permessi non trovata per l'organizzazione "+pk);
			return null;
		}
	}

	@Override
	public String insert(ConfigurazionePermessiDTO entity) throws Exception	{
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
//		StringBuilder sql = new StringBuilder("insert into ")
//								.append(ConfigurazionePermessi.getTableName())
//								.append(StringUtil.concateneString("(", ConfigurazionePermessi.codice_ente.columnName(), ","))
//								.append(StringUtil.concateneString(ConfigurazionePermessi.permesso_comunicazione.columnName(), ","))
//								.append(StringUtil.concateneString(ConfigurazionePermessi.permesso_documentazione.columnName(), ","))
//								.append(StringUtil.concateneString(ConfigurazionePermessi.permesso_osservazione.columnName(), ")"))
//								.append(" values(")
//								.append(StringUtil.concateneString(":", ConfigurazionePermessi.codice_ente.columnName(), ","))
//								.append(StringUtil.concateneString(":", ConfigurazionePermessi.permesso_comunicazione.columnName(), ","))
//								.append(StringUtil.concateneString(":", ConfigurazionePermessi.permesso_documentazione.columnName(), ","))
//								.append(StringUtil.concateneString(":", ConfigurazionePermessi.permesso_osservazione.columnName(), ")"));
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put(ConfigurazionePermessi.codice_ente.columnName(), entity.getCodiceEnte());
//		parameters.put(ConfigurazionePermessi.permesso_comunicazione.columnName(), entity.isPermessoComunicazione());
//		parameters.put(ConfigurazionePermessi.permesso_documentazione.columnName(), entity.isPermessoDocumentazione());
//		parameters.put(ConfigurazionePermessi.permesso_osservazione.columnName(), entity.isPermessoOsservazione());
//		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
//		namedJdbcTemplate.update(sql.toString(), parameters);
//		return entity.getCodiceEnte();
	}

	@Override
	public int update(ConfigurazionePermessiDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
//		StringBuilder sql = new StringBuilder("update ")
//								.append(ConfigurazionePermessi.getTableName())
//								.append(" set ")
//								.append(StringUtil.concateneString(ConfigurazionePermessi.codice_ente.columnName(), " = :", ConfigurazionePermessi.codice_ente.columnName(), ","))
//								.append(StringUtil.concateneString(ConfigurazionePermessi.permesso_comunicazione.columnName(), " = :", ConfigurazionePermessi.permesso_comunicazione.columnName(), ","))
//								.append(StringUtil.concateneString(ConfigurazionePermessi.permesso_documentazione.columnName(), " = :", ConfigurazionePermessi.permesso_documentazione.columnName(), ","))
//								.append(StringUtil.concateneString(ConfigurazionePermessi.permesso_osservazione.columnName(), " = :", ConfigurazionePermessi.permesso_osservazione.columnName()))
//								.append(" where ")
//								.append(ConfigurazionePermessi.codice_ente.getCompleteName())
//								.append(" = :")
//								.append(ConfigurazionePermessi.codice_ente.columnName());
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put(ConfigurazionePermessi.codice_ente.columnName(), entity.getCodiceEnte());
//		parameters.put(ConfigurazionePermessi.permesso_comunicazione.columnName(), entity.isPermessoComunicazione());
//		parameters.put(ConfigurazionePermessi.permesso_documentazione.columnName(), entity.isPermessoDocumentazione());
//		parameters.put(ConfigurazionePermessi.permesso_osservazione.columnName(), entity.isPermessoOsservazione());
//		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
//		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int delete(ConfigurazionePermessiSearch filters) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
//		StringBuilder sql = new StringBuilder("delete from ")
//									.append(ConfigurazionePermessi.getTableName());
//		filters.getSqlWhereClause(sql);
//		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filters);
//		return namedJdbcTemplate.update(sql.toString(), filters.getSqlParameters());
	}

	@Override
	public PaginatedList<ConfigurazionePermessiDTO> search(ConfigurazionePermessiSearch filters) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder("select * from ").append(ConfigurazionePermessi.getTableName());
		filters.getSqlWhereClause(sql);
		filters.getSqlOrderByClause(sql);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filters);
		return super.paginatedList(sql.toString(), filters.getSqlParameters(), rowMapper, filters.getPage(), filters.getLimit());
	}

}