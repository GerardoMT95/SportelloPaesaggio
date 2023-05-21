package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.TipoAllegato;
import it.eng.tz.puglia.autpae.entity.TipoAllegatoDTO;
import it.eng.tz.puglia.autpae.rowmapper.TipoAllegatoRowMapper;
import it.eng.tz.puglia.autpae.search.TipoAllegatoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table tipo_allegato
 */
@Repository
public class TipoAllegatoBaseRepository  extends GenericCrudDao<TipoAllegatoDTO, TipoAllegatoSearch, it.eng.tz.puglia.autpae.enumeratori.TipoAllegato>
{
	private static final Logger log = LoggerFactory.getLogger(TipoAllegatoBaseRepository.class);
    private final TipoAllegatoRowMapper rowMapper = new TipoAllegatoRowMapper();
   
	@Override
	public List<TipoAllegatoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(TipoAllegatoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", TipoAllegato.getTableName()));

		filter.getSqlWhereClause(sql);
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public TipoAllegatoDTO find(it.eng.tz.puglia.autpae.enumeratori.TipoAllegato pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(TipoAllegato.getTableName())
		   .append(" where ")
		   .append(TipoAllegato.type.getCompleteName())
		   .append(" = :")
		   .append(TipoAllegato.type.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(TipoAllegato.type.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public it.eng.tz.puglia.autpae.enumeratori.TipoAllegato insert(TipoAllegatoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(TipoAllegatoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(TipoAllegatoSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<TipoAllegatoDTO> search(TipoAllegatoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(TipoAllegato.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}