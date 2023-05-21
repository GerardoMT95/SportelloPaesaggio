package it.eng.tz.puglia.autpae.repository.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ValoriAssegnamento;
import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;
import it.eng.tz.puglia.autpae.rowmapper.ValoriAssegnamentoRowMapper;
import it.eng.tz.puglia.autpae.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table valori_assegnamento
 */
@Repository
public class ValoriAssegnamentoBaseRepository  extends GenericCrudDao<ValoriAssegnamentoDTO, ValoriAssegnamentoSearch, Integer>
{
	private static final Logger log = LoggerFactory.getLogger(ValoriAssegnamentoBaseRepository.class);
    private final ValoriAssegnamentoRowMapper rowMapper = new ValoriAssegnamentoRowMapper();
    
    
	@Override
	public List<ValoriAssegnamentoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ValoriAssegnamentoDTO find(Integer pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ValoriAssegnamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", ValoriAssegnamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public Integer insert(ValoriAssegnamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+ValoriAssegnamento.getTableName()+" ( "
                	, ValoriAssegnamento.id_organizzazione.columnName()
                ,",", ValoriAssegnamento.fase.columnName()
                ,",", ValoriAssegnamento.username_funzionario.columnName()
                ,",", ValoriAssegnamento.denominazione_funzionario.columnName()
                ,",", ValoriAssegnamento.id_comune_tipo_procedimento.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + ValoriAssegnamento.id_organizzazione.columnName()
                ,",:" + ValoriAssegnamento.fase.columnName()
                ,",:" + ValoriAssegnamento.username_funzionario.columnName()
                ,",:" + ValoriAssegnamento.denominazione_funzionario.columnName()
                ,",:" + ValoriAssegnamento.id_comune_tipo_procedimento.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ValoriAssegnamento.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(ValoriAssegnamento.fase.columnName(), entity.getFase())
				.addValue(ValoriAssegnamento.username_funzionario.columnName(), entity.getUsernameFunzionario())
				.addValue(ValoriAssegnamento.denominazione_funzionario.columnName(), entity.getDenominazioneFunzionario())
				.addValue(ValoriAssegnamento.id_comune_tipo_procedimento.columnName(), entity.getIdComuneTipoProcedimento());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int update(ValoriAssegnamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("UPDATE "+ValoriAssegnamento.getTableName()+" SET "
	            	, ValoriAssegnamento.username_funzionario	  .columnName(), "=:", ValoriAssegnamento.username_funzionario	   .columnName()
	            ,",", ValoriAssegnamento.denominazione_funzionario.columnName(), "=:", ValoriAssegnamento.denominazione_funzionario.columnName());

		sql = StringUtil.concateneString( sql
										, " WHERE "
										, ValoriAssegnamento.id_organizzazione		    .columnName()," = :", ValoriAssegnamento.id_organizzazione		  	.columnName() 
										, " and "
										, ValoriAssegnamento.fase			   			.columnName()," = :", ValoriAssegnamento.fase						.columnName()
										, " and "
										, ValoriAssegnamento.id_comune_tipo_procedimento.columnName()," = :", ValoriAssegnamento.id_comune_tipo_procedimento.columnName());
		  
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ValoriAssegnamento.id_organizzazione.columnName(), 			entity.getIdOrganizzazione())
				.addValue(ValoriAssegnamento.fase.columnName(), 						entity.getFase())
				.addValue(ValoriAssegnamento.id_comune_tipo_procedimento.columnName(), 	entity.getIdComuneTipoProcedimento())
				.addValue(ValoriAssegnamento.username_funzionario.columnName(), 		entity.getUsernameFunzionario())
				.addValue(ValoriAssegnamento.denominazione_funzionario.columnName(),    entity.getDenominazioneFunzionario());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(ValoriAssegnamentoSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("DELETE from ", ValoriAssegnamento.getTableName()));

		search.getSqlWhereClause(sql);
			   
		log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<ValoriAssegnamentoDTO> search(ValoriAssegnamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from "));
		sql.append(ValoriAssegnamento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}