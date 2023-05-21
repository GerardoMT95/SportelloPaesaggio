package it.eng.tz.puglia.servizi_esterni.orm.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Mother class for all dao
 * @author Antonio La Gatta
 * @date 29 lug 2019
 */
public abstract class GenericDao{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Jdbc template
	 */
	protected JdbcTemplate jdbcTemplate;
	/**
	 * NAmed jdbc template
	 */
	@Autowired
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	/**
	 * Post constructor
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 */
	@PostConstruct
	private void postConstruct() {
		this.jdbcTemplate = this.namedJdbcTemplate.getJdbcTemplate();
	}
	/**
	 * Execute sql
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 */
	public void execute(String sql) {
		this.jdbcTemplate.execute(sql);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param page
	 * @param limit
	 * @return paginated list
	 */
	public PaginatedList<Map<String, Object>> paginatedList(String sql, int page, int limit) {
		StopWatch sw = this.startWatch("paginatedList");
		try {
			String                    countSql     = this.getCountSql(sql);
			String                    paginatedSql = this.getPaginatedSql(sql, page, limit);
			int                       count        = this.queryForObject(countSql, Integer.class);
			List<Map<String, Object>> list         = this.queryForList(paginatedSql);
			return new PaginatedList<Map<String, Object>>(list, count);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param page
	 * @param limit
	 * @return paginated list
	 */
	public <T> PaginatedList<T> paginatedList(String sql, RowMapper<T> rowMapper, int page, int limit) {
		StopWatch sw = this.startWatch("paginatedList");
		try {
			String  countSql     = this.getCountSql(sql);
			String  paginatedSql = this.getPaginatedSql(sql, page, limit);
			int     count        = this.queryForObject(countSql, Integer.class);
			List<T> list         = this.queryForList(paginatedSql, rowMapper);
			return new PaginatedList<T>(list, count);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @param page
	 * @param limit
	 * @return paginated list
	 */
	public <T> PaginatedList<T> paginatedList(String sql, Object[] args, RowMapper<T> rowMapper, int page, int limit) {
		StopWatch sw = this.startWatch("paginatedList");
		try {
			String  countSql     = this.getCountSql(sql);
			String  paginatedSql = this.getPaginatedSql(sql, page, limit);
			int     count        = this.queryForObject(countSql, Integer.class, args);
			List<T> list         = this.queryForList(paginatedSql, rowMapper, args);
			return new PaginatedList<T>(list, count);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @param page
	 * @param limit
	 * @return paginated list
	 */
	public <T> PaginatedList<T> paginatedList(String sql, List<Object> args, RowMapper<T> rowMapper, int page, int limit) {
		if(ListUtil.isEmpty(args))
			return this.paginatedList(sql, rowMapper, page, limit);
		return this.paginatedList(sql, args.toArray(), rowMapper, page, limit);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param page
	 * @param limit
	 * @return sql for paginated
	 */
	protected String getPaginatedSql(String sql, int page, int limit) {
		int offset = (page - 1) * limit;
		return StringUtil.concateneString(sql, this.getLimitCondition(limit), " OFFSET ", offset);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param limit
	 * @return Add limit condition to query
	 */
	protected String getLimitCondition(int limit) {
		return StringUtil.concateneString(" LIMIT ", limit);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return query for object
	 */
	public <T> T queryForObject(String sql, Class<T> clazz) { 
		return this.jdbcTemplate.queryForObject(sql, clazz);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return query for object
	 */
	public <T> T queryForObject(String sql, Class<T> clazz, Object...args) { 
		return this.jdbcTemplate.queryForObject(sql, clazz, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return single instance
	 */
	public <T> T queryForObject(String sql, Class<T> clazz, List<Object> args) { 
		if(ListUtil.isEmpty(args))
			return this.queryForObject(sql, clazz);
		return this.queryForObject(sql, clazz, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return single instance
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForObject(sql, rowMapper);
		else
			return this.queryForObject(sql, rowMapper, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return single istance
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) { 
		return this.jdbcTemplate.queryForObject(sql, rowMapper, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return single instance
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) { 
		return this.jdbcTemplate.queryForObject(sql, rowMapper);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @return list
	 */
	public List<Map<String, Object>> queryForList(String sql) { 
		return this.jdbcTemplate.queryForList(sql);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return list
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) {
		return this.jdbcTemplate.query(sql, rowMapper);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return list
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args) {
		return this.jdbcTemplate.query(sql, rowMapper, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rse
	 * @param args
	 * @return list
	 */
	public <T> List<T> queryForListNamedParameters(String sql, ResultSetExtractor<List<T>> rse, Object... args) {
		return this.jdbcTemplate.query(sql, rse, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return list
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForList(sql, rowMapper);
		return this.queryForList(sql, rowMapper, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return list
	 */
	public List<Map<String, Object>> queryForList(String sql, Object... args) { 
		return this.jdbcTemplate.queryForList(sql, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return list
	 */
	public <T> List<T> queryForList(String sql, Class<T> clazz, Object... args) { 
		return this.jdbcTemplate.queryForList(sql, clazz, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return list
	 */
	public <T> List<T> queryForList(String sql, Class<T> clazz, List<Object> args) { 
		return this.queryForList(sql, clazz, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return list
	 */
	public List<Map<String, Object>> queryForList(String sql, List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForList(sql);
		return this.queryForList(sql, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @return rows affected
	 */
	public int update(String sql) {
		StopWatch sw = this.startWatch("update");
		try {
			return this.jdbcTemplate.update(sql);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return rows affected
	 */
	public int update(String sql, Object... args) {
		StopWatch sw = this.startWatch("update");
		try {
			return this.jdbcTemplate.update(sql, args);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return rows affected
	 */
	public int update(String sql, List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.update(sql);
		return this.update(sql, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @param page
	 * @param limit
	 * @return paginated list
	 */
	public <T> PaginatedList<T> paginatedList(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper, int page, int limit) {
		StopWatch sw = this.startWatch("paginatedList");
		try {
			String  countSql     = this.getCountSql(sql);
			String  paginatedSql = this.getPaginatedSql(sql, page, limit);
			int     count        = this.namedQueryForObject(countSql, Integer.class, paramMap);
			List<T> list         = this.namedQueryForList(paginatedSql, rowMapper, paramMap);
			return new PaginatedList<T>(list, count);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return query for object
	 */
	public <T> T namedQueryForObject(String sql, Class<T> clazz) { 
		return this.namedQueryForObject(sql, clazz, new HashMap<>());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param args
	 * @return query for object
	 */
	public <T> T namedQueryForObject(String sql, Class<T> clazz, Map<String,Object> paramMap) { 
		return this.namedJdbcTemplate.queryForObject(sql, paramMap, clazz);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param paramMap
	 * @return list
	 */
	public List<Map<String, Object>> namedQueryForList(String sql, Map<String,Object> paramMap) { 
		return this.namedJdbcTemplate.queryForList(sql, paramMap);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param paramMap
 	 * @return list
	 */
	public <T> List<T> namedQueryForList(String sql, RowMapper<T> rowMapper, Map<String,Object> paramMap) {
		return this.namedJdbcTemplate.query(sql, paramMap, rowMapper);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rse
	 * @param args
	 * @return list
	 */
	public <T> List<T> namedQueryForListNamedParameters(String sql, ResultSetExtractor<List<T>> rse, Map<String,Object> paramMap) {
		return this.namedJdbcTemplate.query(sql, paramMap, rse);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return rows affected
	 */
	public int namedUpdate(String sql, Map<String,Object> paramMap) {
		StopWatch sw = this.startWatch("update");
		try {
			return this.namedJdbcTemplate.update(sql, paramMap);
		}finally {
			this.stopWatch(sw);
		}
	}
	/**
	 * Start log
	 * @author Antonio La Gatta
	 * @date 23 ott 2018
	 * @param id
	 * @return {@link StopWatch} instance
	 */
	protected StopWatch startWatch(String id) {
		if(logger.isDebugEnabled())
			logger.debug(StringUtil.concateneString("Start ", id));
		return LogUtil.startLog(id);
	}
	/**
	 * Log end watch 
	 * @author Antonio La Gatta
	 * @date 23 ott 2018
	 * @param sw
	 */
	protected void stopWatch(StopWatch sw) {
		if(logger.isDebugEnabled())
			logger.debug(LogUtil.stopLog(sw));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 23 ott 2018
	 * @param sql
	 * @return string sql count from sql
	 */
	private String getCountSql(String sql) {
		return StringUtil.concateneString("select count(*) FROM (", sql , ") t");
	}
	
	/**
	 * @author Antonio Grimaldi
	 * @date 25 ott 2018
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return single obejct
	 */
	protected <T> T singleRow(String sql, RowMapper<T> rowMapper, Object[] args){
    	try{
    		return this.jdbcTemplate.queryForObject(sql, args, rowMapper);
		}catch (EmptyResultDataAccessException e) {
			logger.debug("Error in singleRow. No data found", e);
			return null;
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 15 nov 2018
	 * @param fields
	 * @param parametersToAdd
	 * @param parameters
	 * @return build in condition
	 */
	protected String addInCondition(String field, List<?> parametersToAdd, List<Object> parameters) {
		StringBuilder sb = new StringBuilder();
		if(ListUtil.isNotEmpty(parametersToAdd)) {
			sb.append("(").append(field).append(" in ");
			int idx = 1;
			String sep = "(";
			for(Iterator<?> iterator = parametersToAdd.iterator() ; iterator.hasNext() ; ) {
				sb.append(sep).append("?");
				sep = ",";
				parameters.add(iterator.next());
				if(idx == 100 && iterator.hasNext()) {
					sb.append(") OR (").append(field).append(" in ");
					idx = 1;
					sep = "(";
				}else {
					idx++;
				}
			}
			sb.append("))");
		}
		return sb.toString();
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 11 gen 2019
	 * @param fields
	 * @param parametersToExclude
	 * @param parameters
	 * @return build in condition
	 */
	protected String addNotInCondition(String field, List<?> parametersToExclude, List<Object> parameters) {
		StringBuilder sb = new StringBuilder();
		if(ListUtil.isNotEmpty(parametersToExclude)) {
			sb.append("(").append(field).append(" not in ");
			int idx = 1;
			String sep = "(";
			for(Iterator<?> iterator = parametersToExclude.iterator() ; iterator.hasNext() ; ) {
				sb.append(sep).append("?");
				sep = ",";
				parameters.add(iterator.next());
				if(idx == 100 && iterator.hasNext()) {
					sb.append(") OR (").append(field).append(" in ");
					idx = 1;
					sep = "(";
				}else {
					idx++;
				}
			}
			sb.append("))");
		}
		return sb.toString();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return current date in database
	 */
	public java.sql.Date getCurrentDate(){
		return this.queryForObject("select current_date", java.sql.Date.class);
	}
	/**
	 * 
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return current timestamp in database
	 */
	public java.sql.Timestamp getCurrentTimestamp(){
		return this.queryForObject("select current_timestamp", java.sql.Timestamp.class);
	}
}
