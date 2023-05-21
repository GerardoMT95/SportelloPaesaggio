package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneCasellaPostale;
import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.autpae.rowmapper.ConfigurazioneCasellaPostaleRowMapper;
import it.eng.tz.puglia.autpae.search.ConfigurazioneCasellaPostaleSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.regione.puglia.webmail.be.dto.ConfigurazioneCasellaPostaleDto;

/**
 * Repository for table configurazione_casella_postale
 */
@Repository
public class ConfigurazioneCasellaPostaleBaseRepository extends GenericCrudDao<ConfigurazioneCasellaPostaleDTO, ConfigurazioneCasellaPostaleSearch, String> {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneCasellaPostaleBaseRepository.class);
	private ConfigurazioneCasellaPostaleRowMapper rowMapper = new ConfigurazioneCasellaPostaleRowMapper();
	
	@Autowired
	@Qualifier("casellaMittenteApplicazione")
	private ConfigurazioneCasellaPostaleDto ccpd;

	@Override
	public List<ConfigurazioneCasellaPostaleDTO> select() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" select * ")
		   .append(" from ").append(ConfigurazioneCasellaPostale.getTableName());
		log.trace("Eseguo questa query: {}", sql.toString());
		return super.queryForList(sql.toString(), rowMapper);
	}

	@Override
	public long count(ConfigurazioneCasellaPostaleSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) ")
		   .append(" from ").append(ConfigurazioneCasellaPostale.getTableName());
		filter.getSqlWhereClause(sql);
		log.trace("Eseguo questa query: {}", sql.toString());
		log.info("con questi parametri: {}", filter.toString());
		return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public ConfigurazioneCasellaPostaleDTO find(String pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" select * ")
		   .append(" from ").append(ConfigurazioneCasellaPostale.getTableName())
		   .append(" where ").append(ConfigurazioneCasellaPostale.email.columnName()).append(" = :").append(ConfigurazioneCasellaPostale.email.columnName());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ConfigurazioneCasellaPostale.email.columnName(), pk);
		log.trace("Eseguo questa query: {}", sql.toString());
		log.info("con questi parametri: {}", pk);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public String insert(ConfigurazioneCasellaPostaleDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into ").append(ConfigurazioneCasellaPostale.getTableName())
		   .append(" ( ")
		   .append(ConfigurazioneCasellaPostale.email.columnName()).append(", ")
		   .append(ConfigurazioneCasellaPostale.configurazione.columnName())
		   .append(" ) ")
		   .append(" values ")
		   .append(" ( ")
		   .append(" :").append(ConfigurazioneCasellaPostale.email.columnName())
		   .append(", :").append(ConfigurazioneCasellaPostale.configurazione.columnName())
		   .append(" ) ")
		   .append(" returning ")
		   .append(ConfigurazioneCasellaPostale.email.columnName());
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ConfigurazioneCasellaPostale.email.columnName(), entity.getEmail());
		parameters.put(ConfigurazioneCasellaPostale.configurazione.columnName(), entity.getConfigurazione());
		log.trace("Eseguo questa query: {}", sql.toString());
		log.info("con questi parametri: {}", parameters.toString());
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
	}

	@Override
	public int update(ConfigurazioneCasellaPostaleDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" update ").append(ConfigurazioneCasellaPostale.getTableName())
		   .append(" set ").append(ConfigurazioneCasellaPostale.configurazione.columnName()).append(" = :").append(ConfigurazioneCasellaPostale.configurazione.columnName())
		   .append(" where ").append(ConfigurazioneCasellaPostale.email.columnName()).append(" = :").append(ConfigurazioneCasellaPostale.email.columnName());
		Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put(ConfigurazioneCasellaPostale.email.columnName(), entity.getEmail());
	    parameters.put(ConfigurazioneCasellaPostale.configurazione.columnName(), entity.getConfigurazione());
	    log.trace("Eseguo questa query: {}", sql.toString());
		log.info("con questi parametri: {}", parameters.toString());
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int delete(ConfigurazioneCasellaPostaleSearch entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" delete from ").append(ConfigurazioneCasellaPostale.getTableName());
		entity.getSqlWhereClause(sql);
		log.trace("Eseguo questa query: {}", sql.toString());
		log.info("con questi parametri: {}", entity.toString());
		return namedJdbcTemplate.update(sql.toString(), entity.getSqlParameters());
	}

	@Override
	public PaginatedList<ConfigurazioneCasellaPostaleDTO> search(ConfigurazioneCasellaPostaleSearch bean) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		sql.append(" select * ")
		   .append(" from ").append(ConfigurazioneCasellaPostale.getTableName());
		bean.getSqlWhereClause(sql);
		bean.getSqlOrderByClause(sql);
		log.trace("Eseguo questa query: {}", sql.toString());
		log.info("con questi parametri: {}", bean.toString());
		return super.paginatedList(sql.toString(), bean.getSqlParameters(), rowMapper, bean.getPage(), bean.getLimit());
	}
	
	public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaDisattivare(){
        final String sql = new StringBuilder()
                                     .append("select * " + 
                                     		"from  configurazione_casella_postale ccp " + 
                                     		"where ccp.email not in( " + 
                                     		"	select ce.pec_indirizzo " + 
                                     		"	from configurazione_pec ce " + 
                                     		"	where ce.pec_indirizzo is not null " + 
                                     		") AND ccp.email not like ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(ccpd.getCasellaPostale().getIndirizzoMail());
        return super.queryForList(sql, this.rowMapper,parameters);
    }
	
	public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaAttivare() {
		 final String sql = new StringBuilder()
                .append("select * " + 
                		"from  configurazione_casella_postale ccp " + 
                		"where ccp.email in( " + 
                		"	select ce.pec_indirizzo " + 
                		"	from configurazione_pec ce " + 
                		"	where ce.pec_indirizzo is not null " + 
                		") OR ccp.email like ?")
                .toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(ccpd.getCasellaPostale().getIndirizzoMail());
		return super.queryForList(sql, this.rowMapper,parameters);
	}

}