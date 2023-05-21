package it.eng.tz.puglia.autpae.repository.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloCampionamento;
import it.eng.tz.puglia.autpae.entity.FascicoloCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloCampionamentoPK;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloCampionamentoRowMapper;
import it.eng.tz.puglia.autpae.search.FascicoloCampionamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table fascicolo_campionamento
 */
@Repository
public class FascicoloCampionamentoBaseRepository extends GenericCrudDao<FascicoloCampionamentoDTO, FascicoloCampionamentoSearch, FascicoloCampionamentoPK>
{
	private static final Logger log = LoggerFactory.getLogger(FascicoloCampionamentoBaseRepository.class);
	private FascicoloCampionamentoRowMapper rowMapper = new FascicoloCampionamentoRowMapper();
	
	@Override
	public List<FascicoloCampionamentoDTO> select() throws Exception	{
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(FascicoloCampionamentoSearch filter) throws Exception	{
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select count(*) from ", FascicoloCampionamento.getTableName()));
		filter.getSqlWhereClause(sql);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public FascicoloCampionamentoDTO find(FascicoloCampionamentoPK pk) throws Exception	{
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public FascicoloCampionamentoPK insert(FascicoloCampionamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+FascicoloCampionamento.getTableName()+" ( "
	            	, FascicoloCampionamento.id_campionamento.columnName()
	            ,",", FascicoloCampionamento.id_fascicolo.columnName()
	            ," )");
		String sql2 = StringUtil.concateneString("values ("
	            ," :" + FascicoloCampionamento.id_campionamento.columnName()
	            ,",:" + FascicoloCampionamento.id_fascicolo.columnName()
	            ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(FascicoloCampionamento.id_campionamento.columnName(), entity.getIdCampionamento())
				.addValue(FascicoloCampionamento.id_fascicolo.columnName(), entity.getIdFascicolo());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql.toString(), parameters);
		return new FascicoloCampionamentoPK(entity.getIdFascicolo(), entity.getIdCampionamento());
	}

	@Override
	public int update(FascicoloCampionamentoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(FascicoloCampionamentoSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("delete from ", FascicoloCampionamento.getTableName()));

		search.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
	    return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<FascicoloCampionamentoDTO> search(FascicoloCampionamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(FascicoloCampionamento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}
