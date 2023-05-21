package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.dbMapping.ModificheTab;
import it.eng.tz.puglia.autpae.entity.ModificheTabDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ModificheTabPK;
import it.eng.tz.puglia.autpae.rowmapper.ModificheTabRowMapper;
import it.eng.tz.puglia.autpae.search.ModificheTabSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table modifiche_tab
 */
public class ModificheTabBaseRepository  extends GenericCrudDao<ModificheTabDTO, ModificheTabSearch, ModificheTabPK>
{
	private static final Logger log = LoggerFactory.getLogger(ModificheTabBaseRepository.class);
    private final ModificheTabRowMapper rowMapper = new ModificheTabRowMapper();
   
	@Override
	public List<ModificheTabDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ModificheTabSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", ModificheTab.getTableName()));

				   filter.getSqlWhereClause(sql);
				   
	        log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	        return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public ModificheTabDTO find(ModificheTabPK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(ModificheTab.getTableName())
		   .append(" where ")
		   .append(ModificheTab.hashcode.getCompleteName())
		   .append(" = :")
		   .append(ModificheTab.hashcode.columnName())
		   .append(" and ")
		   .append(ModificheTab.tab.getCompleteName())
		   .append(" = :")
		   .append(ModificheTab.tab.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ModificheTab.hashcode.columnName(), pk.getHashcode());
		parameters.put(ModificheTab.tab.columnName(), pk.getTab().name());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public ModificheTabPK insert(ModificheTabDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(ModificheTabDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(ModificheTabSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<ModificheTabDTO> search(ModificheTabSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(ModificheTab.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}