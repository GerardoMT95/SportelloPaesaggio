package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoCorrispondenza;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoCorrispondenzaPK;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoCorrispondenzaRowMapper;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table allegato_corrispondenza
 */
@Repository
public class AllegatoCorrispondenzaBaseRepository  extends GenericCrudDao<AllegatoCorrispondenzaDTO, AllegatoCorrispondenzaSearch, AllegatoCorrispondenzaPK>
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoCorrispondenzaBaseRepository.class);
    private final AllegatoCorrispondenzaRowMapper rowMapper = new AllegatoCorrispondenzaRowMapper();
   
	@Override
	public List<AllegatoCorrispondenzaDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(AllegatoCorrispondenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", AllegatoCorrispondenza.getTableName()));

				   filter.getSqlWhereClause(sql);
				   
	        log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	        return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public AllegatoCorrispondenzaDTO find(AllegatoCorrispondenzaPK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoCorrispondenza.getTableName())
		   .append(" where ")
		   .append(AllegatoCorrispondenza.id_corrispondenza.getCompleteName())
		   .append(" = :")
		   .append(AllegatoCorrispondenza.id_corrispondenza.columnName())
		   .append(" and ")
		   .append(AllegatoCorrispondenza.id_allegato.getCompleteName())
		   .append(" = :")
		   .append(AllegatoCorrispondenza.id_allegato.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoCorrispondenza.id_corrispondenza.columnName(), pk.getIdCorrispondenza());
		parameters.put(AllegatoCorrispondenza.id_allegato.columnName(), pk.getIdAllegato());
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public AllegatoCorrispondenzaPK insert(AllegatoCorrispondenzaDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql1 = StringUtil.concateneString("insert into "+AllegatoCorrispondenza.getTableName()+" ( "
							                	, AllegatoCorrispondenza.id_corrispondenza.columnName()
							                	," , "
							                	, AllegatoCorrispondenza.id_allegato.columnName()
							                	," , "
							                	, AllegatoCorrispondenza.is_url.columnName()
							                	," )");
		String sql2 = StringUtil.concateneString("values ("
								                ," :"+AllegatoCorrispondenza.id_corrispondenza.columnName()
							                	," , "
								                ," :"+AllegatoCorrispondenza.id_allegato.columnName()
								                ," , "
								                ," :"+AllegatoCorrispondenza.is_url.columnName()
								                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AllegatoCorrispondenza.id_corrispondenza.columnName(), entity.getIdCorrispondenza())
				.addValue(AllegatoCorrispondenza.id_allegato.columnName(), entity.getIdAllegato())
				.addValue(AllegatoCorrispondenza.is_url.columnName(), entity.getIsUrl());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql.toString(), parameters);
		return new AllegatoCorrispondenzaPK(entity.getIdAllegato(), entity.getIdCorrispondenza());
	}

	@Override
	public int update(AllegatoCorrispondenzaDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(AllegatoCorrispondenzaSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", AllegatoCorrispondenza.getTableName()));

			   search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<AllegatoCorrispondenzaDTO> search(AllegatoCorrispondenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoCorrispondenza.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}