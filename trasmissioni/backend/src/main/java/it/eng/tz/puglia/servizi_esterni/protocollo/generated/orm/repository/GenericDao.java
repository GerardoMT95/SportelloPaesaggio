package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.repository;

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
public abstract class GenericDao {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Jdbc template write transaction
	 */
	protected JdbcTemplate jdbcTemplateWrite;
	/**
	 * Jdbc template read transaction
	 */
	protected JdbcTemplate jdbcTemplateRead;
	/**
	 * Named jdbc template write transaction
	 */
	@Autowired
	//@Qualifier(DatabaseConfiguration.NAMED_JDBC_TEMPLATE_WRITE)   prende quello default
	protected NamedParameterJdbcTemplate namedJdbcTemplateWrite;
	/**
	 * Named jdbc template write transaction
	 */
	@Autowired
	//@Qualifier(DatabaseConfiguration.NAMED_JDBC_TEMPLATE_READ)
	protected NamedParameterJdbcTemplate namedJdbcTemplateRead;
	/**
	 * Post constructor
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 */
	@PostConstruct
	private void postConstruct() {
		this.jdbcTemplateWrite = this.namedJdbcTemplateWrite.getJdbcTemplate();
		this.jdbcTemplateRead  = this.namedJdbcTemplateRead .getJdbcTemplate();
	}
	/**
	 * Execute sql
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 */
	public void execute(String sql) {
		this.jdbcTemplateWrite.execute(sql);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param page
	 * @param limit
	 * @return paginated list
	 */
	public PaginatedList<Map<String, Object>> paginatedList(final String sql, final int page, final int limit) {
		final StopWatch sw = this.startWatch("paginatedList");
		try {
			final String                    countSql     = this.getCountSql(sql);
			final String                    paginatedSql = this.getPaginatedSql(sql, page, limit);
			final int                       count        = this.queryForObjectTxRead(countSql, Integer.class);
			final List<Map<String, Object>> list         = this.queryForListTxRead(paginatedSql);
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
	public <T> PaginatedList<T> paginatedList(final String sql, final RowMapper<T> rowMapper, final  int page, final int limit) {
		final StopWatch sw = this.startWatch("paginatedList");
		try {
			final String  countSql     = this.getCountSql(sql);
			final String  paginatedSql = this.getPaginatedSql(sql, page, limit);
			final int     count        = this.queryForObjectTxRead(countSql, Integer.class);
			final List<T> list         = this.queryForListTxRead(paginatedSql, rowMapper);
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
	public <T> PaginatedList<T> paginatedList(final String sql, final Object[] args, final RowMapper<T> rowMapper, final int page, final int limit) {
		final StopWatch sw = this.startWatch("paginatedList");
		try {
			final String  countSql     = this.getCountSql(sql);
			final String  paginatedSql = this.getPaginatedSql(sql, page, limit);
			final int     count        = this.queryForObjectTxRead(countSql, Integer.class, args);
			final List<T> list         = this.queryForListTxRead(paginatedSql, rowMapper, args);
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
	public <T> PaginatedList<T> paginatedList(final String sql, final List<Object> args, final RowMapper<T> rowMapper, final int page, final int limit) {
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
	protected String getPaginatedSql(final String sql, final int page, final int limit) {
		if (page==0 && limit==0) {	// FL
			return sql;
		}
		else {
			final int offset = (page - 1) * limit;
			return StringUtil.concateneString(sql, this.getLimitCondition(limit), " OFFSET ", offset);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param limit
	 * @return Add limit condition to query
	 */
	protected String getLimitCondition(final int limit) {
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
	public <T> T queryForObjectTxRead(final String sql, final Class<T> clazz) { 
		return this.jdbcTemplateRead.queryForObject(sql, clazz);
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
	public <T> T queryForObjectTxRead(final String sql, final Class<T> clazz, final Object...args) { 
		return this.jdbcTemplateRead.queryForObject(sql, clazz, args);
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
	public <T> T queryForObjectTxRead(final String sql, final Class<T> clazz, final List<Object> args) { 
		if(ListUtil.isEmpty(args))
			return this.queryForObjectTxRead(sql, clazz);
		return this.queryForObjectTxRead(sql, clazz, args.toArray());
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
	public <T> T queryForObjectTxRead(final String sql, final RowMapper<T> rowMapper, final List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForObjectTxRead(sql, rowMapper);
		else
			return this.queryForObjectTxRead(sql, rowMapper, args.toArray());
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
	public <T> T queryForObjectTxRead(final String sql, final RowMapper<T> rowMapper, final Object... args) { 
		return this.jdbcTemplateRead.queryForObject(sql, rowMapper, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return single instance
	 */
	public <T> T queryForObjectTxRead(final String sql, final RowMapper<T> rowMapper) { 
		return this.jdbcTemplateRead.queryForObject(sql, rowMapper);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @return list
	 */
	public List<Map<String, Object>> queryForListTxRead(final String sql) { 
		return this.jdbcTemplateRead.queryForList(sql);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return list
	 */
	public <T> List<T> queryForListTxRead(final String sql, final RowMapper<T> rowMapper) {
		return this.jdbcTemplateRead.query(sql, rowMapper);
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
	public <T> List<T> queryForListTxRead(final String sql, final RowMapper<T> rowMapper, final Object... args) {
		return this.jdbcTemplateRead.query(sql, rowMapper, args);
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
	public <T> List<T> queryForListNamedParametersTxRead(final String sql, final ResultSetExtractor<List<T>> rse, final Object... args) {
		return this.jdbcTemplateRead.query(sql, rse, args);
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
	public <T> List<T> queryForListTxRead(final String sql, final RowMapper<T> rowMapper, final List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForListTxRead(sql, rowMapper);
		return this.queryForListTxRead(sql, rowMapper, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return list
	 */
	public List<Map<String, Object>> queryForListTxRead(final String sql, final Object... args) { 
		return this.jdbcTemplateRead.queryForList(sql, args);
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
	public <T> List<T> queryForListTxRead(final String sql, final Class<T> clazz, final Object... args) { 
		return this.jdbcTemplateRead.queryForList(sql, clazz, args);
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
	public <T> List<T> queryForListTxRead(final String sql, final Class<T> clazz, final List<Object> args) { 
		return this.queryForListTxRead(sql, clazz, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return list
	 */
	public List<Map<String, Object>> queryForListTxRead(final String sql, final List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForListTxRead(sql);
		return this.queryForListTxRead(sql, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @return rows affected
	 */
	public int update(final String sql) {
		final StopWatch sw = this.startWatch("update");
		try {
			return this.jdbcTemplateWrite.update(sql);
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
	public int update(final String sql, final Object... args) {
		final StopWatch sw = this.startWatch("update");
		try {
			return this.jdbcTemplateWrite.update(sql, args);
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
	public int update(final String sql, final List<Object> args) {
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
	public <T> PaginatedList<T> paginatedList(final String sql, final Map<String, Object> paramMap, final RowMapper<T> rowMapper, final int page, final int limit) {
		final StopWatch sw = this.startWatch("paginatedList");
		try {
			final String  countSql     = this.getCountSql(sql);
			final String  paginatedSql = this.getPaginatedSql(sql, page, limit);
			final int     count        = this.namedQueryForObject(countSql, Integer.class, paramMap);
			final List<T> list         = this.namedQueryForList(paginatedSql, rowMapper, paramMap);
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
	public PaginatedList<Map<String, Object>> paginatedList(final String sql, final Map<String, Object> paramMap, final int page, final int limit) {
		final StopWatch sw = this.startWatch("paginatedList");
		try {
			final String                    countSql     = this.getCountSql(sql);
			final String                    paginatedSql = this.getPaginatedSql(sql, page, limit);
			final int                       count        = this.namedQueryForObject(countSql, Integer.class, paramMap);
			final List<Map<String, Object>> list         = this.namedQueryForList(paginatedSql, paramMap);
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
	 * @param clazz
	 * @return query for object
	 */
	public <T> T namedQueryForObjectTxRead(final String sql, final Class<T> clazz) { 
		return this.namedQueryForObjectTxRead(sql, clazz, new HashMap<>());
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
	public <T> T namedQueryForObjectTxRead(final String sql, final Class<T> clazz, final Map<String,Object> paramMap) { 
		return this.namedJdbcTemplateRead.queryForObject(sql, paramMap, clazz);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param paramMap
	 * @return list
	 */
	public List<Map<String, Object>> namedQueryForListTxRead(final String sql, final Map<String,Object> paramMap) { 
		return this.namedJdbcTemplateRead.queryForList(sql, paramMap);
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
	public <T> List<T> namedQueryForListTxRead(final String sql, final RowMapper<T> rowMapper, final Map<String,Object> paramMap) {
		return this.namedJdbcTemplateRead.query(sql, paramMap, rowMapper);
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
	public <T> List<T> namedQueryForListNamedParametersTxRead(final String sql, final ResultSetExtractor<List<T>> rse, final Map<String,Object> paramMap) {
		return this.namedJdbcTemplateRead.query(sql, paramMap, rse);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return rows affected
	 */
	public int namedUpdate(final String sql, final Map<String,Object> paramMap) {
		final StopWatch sw = this.startWatch("update");
		try {
			return this.namedJdbcTemplateWrite.update(sql, paramMap);
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
	protected StopWatch startWatch(final String id) {
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
	protected void stopWatch(final StopWatch sw) {
		if(logger.isDebugEnabled())
			logger.debug(LogUtil.stopLog(sw));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 23 ott 2018
	 * @param sql
	 * @return string sql count from sql
	 */
	private String getCountSql(final String sql) {
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
	protected <T> T singleRowTxRead(final String sql, final RowMapper<T> rowMapper, final Object[] args){
    	try{
    		return this.jdbcTemplateRead.queryForObject(sql, args, rowMapper);
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
	protected String addInCondition(final String field, final List<?> parametersToAdd, final List<Object> parameters) {
		final StringBuilder sb = new StringBuilder();
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
	protected String addNotInCondition(final String field, final List<?> parametersToExclude, final List<Object> parameters) {
		final StringBuilder sb = new StringBuilder();
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
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return query for object
	 */
	public <T> T queryForObject(final String sql, final Class<T> clazz) { 
		return this.jdbcTemplateWrite.queryForObject(sql, clazz);
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
	public <T> T queryForObject(final String sql, final Class<T> clazz, final Object...args) { 
		return this.jdbcTemplateWrite.queryForObject(sql, clazz, args);
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
	public <T> T queryForObject(final String sql, final Class<T> clazz, final List<Object> args) { 
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
	public <T> T queryForObject(final String sql, final RowMapper<T> rowMapper, final List<Object> args) {
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
	public <T> T queryForObject(final String sql, final RowMapper<T> rowMapper, final Object... args) { 
		return this.jdbcTemplateWrite.queryForObject(sql, rowMapper, args);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return single instance
	 */
	public <T> T queryForObject(final String sql, final RowMapper<T> rowMapper) { 
		return this.jdbcTemplateWrite.queryForObject(sql, rowMapper);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @return list
	 */
	public List<Map<String, Object>> queryForList(final String sql) { 
		return this.jdbcTemplateWrite.queryForList(sql);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return list
	 */
	public <T> List<T> queryForList(final String sql, final RowMapper<T> rowMapper) {
		return this.jdbcTemplateWrite.query(sql, rowMapper);
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
	public <T> List<T> queryForList(final String sql, final RowMapper<T> rowMapper, final Object... args) {
		return this.jdbcTemplateWrite.query(sql, rowMapper, args);
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
	public <T> List<T> queryForListNamedParameters(final String sql, final ResultSetExtractor<List<T>> rse, final Object... args) {
		return this.jdbcTemplateWrite.query(sql, rse, args);
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
	public <T> List<T> queryForList(final String sql, final RowMapper<T> rowMapper, final List<Object> args) {
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
	public List<Map<String, Object>> queryForList(final String sql, final Object... args) { 
		return this.jdbcTemplateWrite.queryForList(sql, args);
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
	public <T> List<T> queryForList(final String sql, final Class<T> clazz, final Object... args) { 
		return this.jdbcTemplateWrite.queryForList(sql, clazz, args);
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
	public <T> List<T> queryForList(final String sql, final Class<T> clazz, final List<Object> args) { 
		return this.queryForList(sql, clazz, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param args
	 * @return list
	 */
	public List<Map<String, Object>> queryForList(final String sql, final List<Object> args) {
		if(ListUtil.isEmpty(args))
			return this.queryForList(sql);
		return this.queryForList(sql, args.toArray());
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @return query for object
	 */
	public <T> T namedQueryForObject(final String sql, final Class<T> clazz) { 
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
	public <T> T namedQueryForObject(final String sql, final Class<T> clazz, final Map<String,Object> paramMap) { 
		return this.namedJdbcTemplateWrite.queryForObject(sql, paramMap, clazz);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sql
	 * @param paramMap
	 * @return list
	 */
	public List<Map<String, Object>> namedQueryForList(final String sql, final Map<String,Object> paramMap) { 
		return this.namedJdbcTemplateWrite.queryForList(sql, paramMap);
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
	public <T> List<T> namedQueryForList(final String sql, final RowMapper<T> rowMapper, final Map<String,Object> paramMap) {
		return this.namedJdbcTemplateWrite.query(sql, paramMap, rowMapper);
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
	public <T> List<T> namedQueryForListNamedParameters(final String sql, final ResultSetExtractor<List<T>> rse, final Map<String,Object> paramMap) {
		return this.namedJdbcTemplateWrite.query(sql, paramMap, rse);
	}
	/**
	 * @author Antonio Grimaldi
	 * @date 25 ott 2018
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return single obejct
	 */
	protected <T> T singleRow(final String sql, final RowMapper<T> rowMapper, final Object[] args){
    	try{
    		return this.jdbcTemplateWrite.queryForObject(sql, args, rowMapper);
		}catch (EmptyResultDataAccessException e) {
			logger.debug("Error in singleRow. No data found", e);
			return null;
		}
	}
	
}
