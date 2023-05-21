package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloCorrispondenza;
import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloCorrispondenzaRowMapper;
import it.eng.tz.puglia.autpae.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table fascicolo_corrispondenza
 */
@Repository
public class FascicoloCorrispondenzaBaseRepository  extends GenericCrudDao<FascicoloCorrispondenzaDTO, FascicoloCorrispondenzaSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(FascicoloCorrispondenzaBaseRepository.class);
    private final FascicoloCorrispondenzaRowMapper rowMapper = new FascicoloCorrispondenzaRowMapper();
   
	@Override
	public List<FascicoloCorrispondenzaDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(FascicoloCorrispondenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", FascicoloCorrispondenza.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public FascicoloCorrispondenzaDTO find(Long id) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(FascicoloCorrispondenza.getTableName())
		   .append(" where ")
		   .append(FascicoloCorrispondenza.id.getCompleteName())
		   .append(" = :")
		   .append(FascicoloCorrispondenza.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(FascicoloCorrispondenza.id.columnName(), id);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public Long insert(FascicoloCorrispondenzaDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql1 = StringUtil.concateneString("insert into "+FascicoloCorrispondenza.getTableName()+" ( "
								                	, FascicoloCorrispondenza.id_corrispondenza.columnName()
								                ,",", FascicoloCorrispondenza.id_fascicolo.columnName()
								                ," )");
		String sql2 = StringUtil.concateneString("values ("
								                ," :" + FascicoloCorrispondenza.id_corrispondenza.columnName()
								                ,",:" + FascicoloCorrispondenza.id_fascicolo.columnName()
								                ," )");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(FascicoloCorrispondenza.id_corrispondenza.columnName(), entity.getIdCorrispondenza());
		parameters.put(FascicoloCorrispondenza.id_fascicolo.columnName(), entity.getIdFascicolo());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", FascicoloCorrispondenza.id.columnName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(FascicoloCorrispondenzaDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(FascicoloCorrispondenzaSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", FascicoloCorrispondenza.getTableName()));

		search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<FascicoloCorrispondenzaDTO> search(FascicoloCorrispondenzaSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(FascicoloCorrispondenza.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}