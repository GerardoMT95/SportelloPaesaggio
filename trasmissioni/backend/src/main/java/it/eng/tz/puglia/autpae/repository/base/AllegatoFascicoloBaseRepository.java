package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoFascicolo;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoFascicoloPK;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoFascicoloRowMapper;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Base Repository for table allegato_fascicolo
 */
public class AllegatoFascicoloBaseRepository  extends GenericCrudDao<AllegatoFascicoloDTO, AllegatoFascicoloSearch, AllegatoFascicoloPK>
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoFascicoloBaseRepository.class);
    private final AllegatoFascicoloRowMapper rowMapper = new AllegatoFascicoloRowMapper();
   
	@Override
	public List<AllegatoFascicoloDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(AllegatoFascicoloSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", AllegatoFascicolo.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public AllegatoFascicoloDTO find(AllegatoFascicoloPK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoFascicolo.getTableName())
		   .append(" where ")
		   .append(AllegatoFascicolo.id_allegato.getCompleteName())
		   .append(" = :")
		   .append(AllegatoFascicolo.id_allegato.columnName())
		   .append(" and ")
		   .append(AllegatoFascicolo.type.getCompleteName())
		   .append(" = :")
		   .append(AllegatoFascicolo.type.columnName())
		   .append(" and ")
		   .append(AllegatoFascicolo.id_fascicolo.getCompleteName())
		   .append(" = :")
		   .append(AllegatoFascicolo.id_fascicolo.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoFascicolo.id_allegato.columnName(), pk.getIdAllegato());
		parameters.put(AllegatoFascicolo.type.columnName(), pk.getType());
		parameters.put(AllegatoFascicolo.id_fascicolo.columnName(), pk.getIdAllegato());
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public AllegatoFascicoloPK insert(AllegatoFascicoloDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+AllegatoFascicolo.getTableName()+" ( "
                	, AllegatoFascicolo.type.columnName()
                ,",", AllegatoFascicolo.id_fascicolo.columnName()
                ,",", AllegatoFascicolo.id_allegato.columnName()
                ," )");
		String sql2 = StringUtil.concateneString("values ("
                ," :" + AllegatoFascicolo.type.columnName()
                ,",:" + AllegatoFascicolo.id_fascicolo.columnName()
                ,",:" + AllegatoFascicolo.id_allegato.columnName()
                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AllegatoFascicolo.type.columnName(), entity.getType().name())
				.addValue(AllegatoFascicolo.id_fascicolo.columnName(), entity.getIdFascicolo())
				.addValue(AllegatoFascicolo.id_allegato.columnName(), entity.getIdAllegato());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql.toString(), parameters);
		return new AllegatoFascicoloPK(entity.getIdAllegato(), entity.getType());
	}
	
	@Override
	public int update(AllegatoFascicoloDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(AllegatoFascicoloSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", AllegatoFascicolo.getTableName()));

		search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<AllegatoFascicoloDTO> search(AllegatoFascicoloSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoFascicolo.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}