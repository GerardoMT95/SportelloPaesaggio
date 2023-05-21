package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.AllegatoEnte;
import it.eng.tz.puglia.autpae.entity.AllegatoEnteDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoEntePK;
import it.eng.tz.puglia.autpae.rowmapper.AllegatoEnteRowMapper;
import it.eng.tz.puglia.autpae.search.AllegatoEnteSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table allegato_ente
 */
@Repository
public class AllegatoEnteBaseRepository  extends GenericCrudDao<AllegatoEnteDTO, AllegatoEnteSearch, AllegatoEntePK>
{
	private static final Logger log = LoggerFactory.getLogger(AllegatoEnteBaseRepository.class);
    private final AllegatoEnteRowMapper rowMapper = new AllegatoEnteRowMapper();
   
	@Override
	public List<AllegatoEnteDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(AllegatoEnteSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", AllegatoEnte.getTableName()));

				   filter.getSqlWhereClause(sql);
				   
	        log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	        return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public AllegatoEnteDTO find(AllegatoEntePK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoEnte.getTableName())
		   .append(" where ")
		   .append(AllegatoEnte.codice.getCompleteName())
		   .append(" = :")
		   .append(AllegatoEnte.codice.columnName())
		   .append(" and ")
		   .append(AllegatoEnte.id_allegato.getCompleteName())
		   .append(" = :")
		   .append(AllegatoEnte.id_allegato.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(AllegatoEnte.codice.columnName(), pk.getCodice());
		parameters.put(AllegatoEnte.id_allegato.columnName(), pk.getIdAllegato());
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public AllegatoEntePK insert(AllegatoEnteDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql1 = StringUtil.concateneString("insert into "+AllegatoEnte.getTableName()+" ( "
							                	, AllegatoEnte.codice.columnName()
							                	," , "
							                	, AllegatoEnte.descrizione.columnName()
							                	," , "
							                	, AllegatoEnte.id_allegato.columnName()
							                	," )");
		String sql2 = StringUtil.concateneString("values ("
								                ," :"+AllegatoEnte.codice.columnName()
							                	," , "
								                ," :"+AllegatoEnte.descrizione.columnName()
							                	," , "
								                ," :"+AllegatoEnte.id_allegato.columnName()
								                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AllegatoEnte.codice.columnName(), entity.getCodice())
				.addValue(AllegatoEnte.descrizione.columnName(), entity.getDescrizione())
				.addValue(AllegatoEnte.id_allegato.columnName(), entity.getIdAllegato());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql.toString(), parameters);
		return new AllegatoEntePK(entity.getIdAllegato(), entity.getCodice());
	}
	
	public void insert(List<AllegatoEnteDTO> entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String sql1 = StringUtil.concateneString("insert into "+AllegatoEnte.getTableName()+" ( "
							 , AllegatoEnte.codice.columnName() ," , "
							 , AllegatoEnte.descrizione.columnName()," , "
							 , AllegatoEnte.id_allegato.columnName()," ) values");
		String sql2 = "";
		String sep = "";
		int i = 0;
		for(AllegatoEnteDTO e: entity)
		{
		    sql2 = StringUtil.concateneString(sql2, sep, "("
				   	      	     ," :"+AllegatoEnte.codice.columnName() + i," , "
				   	             ," :"+AllegatoEnte.descrizione.columnName() + i," , "
				   	             ," :"+AllegatoEnte.id_allegato.columnName() + i," )");
		    parameters.addValue(AllegatoEnte.codice.columnName() + i, e.getCodice())
			      .addValue(AllegatoEnte.descrizione.columnName() + i, e.getDescrizione())
			      .addValue(AllegatoEnte.id_allegato.columnName() + i, e.getIdAllegato());
		    i++;
		    sep = ", ";
    		}
		
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int update(AllegatoEnteDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(AllegatoEnteSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", AllegatoEnte.getTableName()));

			   search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<AllegatoEnteDTO> search(AllegatoEnteSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(AllegatoEnte.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}