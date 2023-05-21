package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.rowmapper.DestinatarioRowMapper;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table destinatario
 */
public class DestinatarioBaseRepository  extends GenericCrudDao<DestinatarioDTO, DestinatarioSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(DestinatarioBaseRepository.class);
    private final DestinatarioRowMapper rowMapper = new DestinatarioRowMapper();
   
	@Override
	public List<DestinatarioDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(DestinatarioSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", Destinatario.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public DestinatarioDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Destinatario.getTableName())
		   .append(" where ")
		   .append(Destinatario.id.getCompleteName())
		   .append(" = :")
		   .append(Destinatario.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Destinatario.id.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public Long insert(DestinatarioDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql1 = StringUtil.concateneString("insert into "+Destinatario.getTableName()+" ( "
                	, Destinatario.type.columnName()
                ,",", Destinatario.id_corrispondenza.columnName()
                ,",", Destinatario.email.columnName()
                ,",", Destinatario.stato.columnName()
                ,",", Destinatario.data_ricezione.columnName()
                ,",", Destinatario.denominazione.columnName()
                ," )");
		String sql2 = StringUtil.concateneString("values ("
                ," :" + Destinatario.type.columnName()
                ,",:" + Destinatario.id_corrispondenza.columnName()
                ,",:" + Destinatario.email.columnName()
                ,",:" + Destinatario.stato.columnName()
                ,",:" + Destinatario.data_ricezione.columnName()
                ,",:" + Destinatario.denominazione.columnName()
                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Destinatario.type.columnName(), entity.getTipo()==null ? TipoDestinatario.TO.name() : entity.getTipo().name())  // TODO: mettere il default nel DB
				.addValue(Destinatario.id_corrispondenza.columnName(), entity.getIdCorrispondenza())
				.addValue(Destinatario.email.columnName(), entity.getEmail())
				.addValue(Destinatario.stato.columnName(), entity.getStato().name())
				.addValue(Destinatario.data_ricezione.columnName(), entity.getDataRicezione())
				.addValue(Destinatario.denominazione.columnName(), entity.getNome());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", Destinatario.id.columnName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(DestinatarioDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("update "+Destinatario.getTableName()+" set "
	            	, Destinatario.type.columnName(), "=:", Destinatario.type.columnName()
	            ,",", Destinatario.id_corrispondenza.columnName(), "=:", Destinatario.id_corrispondenza.columnName()
	            ,",", Destinatario.email.columnName(), "=:", Destinatario.email.columnName()
	            ,",", Destinatario.stato.columnName(), "=:", Destinatario.stato.columnName()
	            ,",", Destinatario.data_ricezione.columnName(), "=:", Destinatario.data_ricezione.columnName()
	            ,",", Destinatario.denominazione.columnName(), "=:", Destinatario.denominazione.columnName()
	            ," where ",Destinatario.getTableName(),".",Destinatario.id.columnName()," = :",
																	 Destinatario.id.columnName());  
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Destinatario.id.columnName(), entity.getId())
				.addValue(Destinatario.type.columnName(), entity.getTipo().name())
				.addValue(Destinatario.id_corrispondenza.columnName(), entity.getIdCorrispondenza())
				.addValue(Destinatario.email.columnName(), entity.getEmail())
				.addValue(Destinatario.stato.columnName(), entity.getStato().name())
				.addValue(Destinatario.data_ricezione.columnName(), entity.getDataRicezione())
				.addValue(Destinatario.denominazione.columnName(), entity.getNome());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(DestinatarioSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", Destinatario.getTableName()));

		search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<DestinatarioDTO> search(DestinatarioSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Destinatario.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}