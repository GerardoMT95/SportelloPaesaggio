package it.eng.tz.puglia.autpae.repository.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneAssegnamento;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.autpae.rowmapper.ConfigurazioneAssegnamentoRowMapper;
import it.eng.tz.puglia.autpae.search.ConfigurazioneAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table configurazione_assegnamento
 */
@Repository
public class ConfigurazioneAssegnamentoBaseRepository  extends GenericCrudDao<ConfigurazioneAssegnamentoDTO, ConfigurazioneAssegnamentoSearch, Integer>
{
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneAssegnamentoBaseRepository.class);
    private final ConfigurazioneAssegnamentoRowMapper rowMapper = new ConfigurazioneAssegnamentoRowMapper();
   
    
	@Override
	public List<ConfigurazioneAssegnamentoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ConfigurazioneAssegnamentoDTO find(Integer pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(ConfigurazioneAssegnamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", ConfigurazioneAssegnamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
	@Override
	public Integer insert(ConfigurazioneAssegnamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+ConfigurazioneAssegnamento.getTableName()+" ( "
                	, ConfigurazioneAssegnamento.id_organizzazione.columnName()
                ,",", ConfigurazioneAssegnamento.fase.columnName()
                ,",", ConfigurazioneAssegnamento.criterio_assegnamento.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + ConfigurazioneAssegnamento.id_organizzazione.columnName()
                ,",:" + ConfigurazioneAssegnamento.fase.columnName()
                ,",:" + ConfigurazioneAssegnamento.criterio_assegnamento.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ConfigurazioneAssegnamento.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(ConfigurazioneAssegnamento.fase.columnName(), entity.getFase())
				.addValue(ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), entity.getCriterioAssegnamento().name());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int update(ConfigurazioneAssegnamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("UPDATE "+ConfigurazioneAssegnamento.getTableName()+" SET "
	            		   , ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), "=:", ConfigurazioneAssegnamento.criterio_assegnamento.columnName());

		sql = StringUtil.concateneString( sql
										, " WHERE "
										, ConfigurazioneAssegnamento.id_organizzazione.columnName()," = :", ConfigurazioneAssegnamento.id_organizzazione.columnName() 
										, " and "
										, ConfigurazioneAssegnamento.fase			  .columnName()," = :", ConfigurazioneAssegnamento.fase			    .columnName());  
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ConfigurazioneAssegnamento.id_organizzazione	  .columnName(), entity.getIdOrganizzazione())
				.addValue(ConfigurazioneAssegnamento.fase			      .columnName(), entity.getFase())
				.addValue(ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), entity.getCriterioAssegnamento().name());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(ConfigurazioneAssegnamentoSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("DELETE from ", ConfigurazioneAssegnamento.getTableName()));

		search.getSqlWhereClause(sql);
			   
		log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<ConfigurazioneAssegnamentoDTO> search(ConfigurazioneAssegnamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from ", ConfigurazioneAssegnamento.getTableName()));
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}