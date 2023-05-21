package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoObbligatorio;
import it.eng.tz.puglia.autpae.entity.AllegatoObbligatorioDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoObbligatorioPK;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoObbligatorioRowMapper;
import it.eng.tz.puglia.autpae.search.AllegatoObbligatorioSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table allegato_obbligatorio
 */
@Repository
public class AllegatoObbligatorioBaseRepository  extends GenericCrudDao<AllegatoObbligatorioDTO, AllegatoObbligatorioSearch, AllegatoObbligatorioPK>
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoObbligatorioBaseRepository.class);
    private final AllegatoObbligatorioRowMapper rowMapper = new AllegatoObbligatorioRowMapper();
   
	@Override
	public List<AllegatoObbligatorioDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(AllegatoObbligatorioSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", AllegatoObbligatorio.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public AllegatoObbligatorioDTO find(AllegatoObbligatorioPK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoObbligatorio.getTableName())
		   .append(" where ")
		   .append(AllegatoObbligatorio.type.getCompleteName())
		   .append(" = :")
		   .append(AllegatoObbligatorio.type.columnName())
		   .append(" and ")
		   .append(AllegatoObbligatorio.codice_tipo_procedimento.getCompleteName())
		   .append(" = :")
		   .append(AllegatoObbligatorio.codice_tipo_procedimento.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoObbligatorio.type.columnName(), pk.getType().name());
		parameters.put(AllegatoObbligatorio.codice_tipo_procedimento.columnName(), pk.getCodiceTipoProcedimento());
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public AllegatoObbligatorioPK insert(AllegatoObbligatorioDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(AllegatoObbligatorioDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(AllegatoObbligatorioSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<AllegatoObbligatorioDTO> search(AllegatoObbligatorioSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoObbligatorio.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}