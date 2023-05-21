package it.eng.tz.puglia.autpae.repository.base;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.autpae.entity.AssegnamentoFascicoloDTO;
import it.eng.tz.puglia.autpae.rowmapper.AssegnamentoFascicoloRowMapper;
import it.eng.tz.puglia.autpae.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table assegnamento_fascicolo
 */
public class AssegnamentoFascicoloBaseRepository  extends GenericCrudDao<AssegnamentoFascicoloDTO, AssegnamentoFascicoloSearch, Integer>
{
	private static final Logger log = LoggerFactory.getLogger(AssegnamentoFascicoloBaseRepository.class);
    private final AssegnamentoFascicoloRowMapper rowMapper = new AssegnamentoFascicoloRowMapper();
   
    
	@Override
	public List<AssegnamentoFascicoloDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public AssegnamentoFascicoloDTO find(Integer pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(AssegnamentoFascicoloSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", AssegnamentoFascicolo.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
	@Override
	public Integer insert(AssegnamentoFascicoloDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+AssegnamentoFascicolo.getTableName()+" ( "
                	, AssegnamentoFascicolo.id_fascicolo.columnName()
                ,",", AssegnamentoFascicolo.id_organizzazione.columnName()
                ,",", AssegnamentoFascicolo.fase.columnName()
                ,",", AssegnamentoFascicolo.username_funzionario.columnName()
                ,",", AssegnamentoFascicolo.denominazione_funzionario.columnName()
                ,",", AssegnamentoFascicolo.num_assegnazioni.columnName()
                ,",", AssegnamentoFascicolo.data_operazione.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + AssegnamentoFascicolo.id_fascicolo.columnName()
                ,",:" + AssegnamentoFascicolo.id_organizzazione.columnName()
                ,",:" + AssegnamentoFascicolo.fase.columnName()
                ,",:" + AssegnamentoFascicolo.username_funzionario.columnName()
                ,",:" + AssegnamentoFascicolo.denominazione_funzionario.columnName()
                ,",:" + AssegnamentoFascicolo.num_assegnazioni.columnName()
                ,",:" + AssegnamentoFascicolo.data_operazione.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AssegnamentoFascicolo.id_fascicolo.columnName(), entity.getIdFascicolo())
				.addValue(AssegnamentoFascicolo.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(AssegnamentoFascicolo.fase.columnName(), entity.getFase())
				.addValue(AssegnamentoFascicolo.username_funzionario.columnName(), entity.getUsernameFunzionario())
				.addValue(AssegnamentoFascicolo.denominazione_funzionario.columnName(), entity.getDenominazioneFunzionario())
				.addValue(AssegnamentoFascicolo.num_assegnazioni.columnName(), entity.getNumAssegnazioni())
				.addValue(AssegnamentoFascicolo.data_operazione.columnName(), new Date());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int update(AssegnamentoFascicoloDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("UPDATE "+AssegnamentoFascicolo.getTableName()+" SET "
	            	, AssegnamentoFascicolo.username_funzionario	 .columnName(), "=:", AssegnamentoFascicolo.username_funzionario	 .columnName()
	            ,",", AssegnamentoFascicolo.denominazione_funzionario.columnName(), "=:", AssegnamentoFascicolo.denominazione_funzionario.columnName()
	            ,",", AssegnamentoFascicolo.num_assegnazioni		 .columnName(), "=:", AssegnamentoFascicolo.num_assegnazioni		 .columnName()
	            ,",", AssegnamentoFascicolo.data_operazione			 .columnName(), "=:", AssegnamentoFascicolo.data_operazione			 .columnName());

		sql = StringUtil.concateneString( sql
									    , " WHERE "
										, AssegnamentoFascicolo.id_fascicolo	 .columnName()," = :", AssegnamentoFascicolo.id_fascicolo	  .columnName()
										, " and "
										, AssegnamentoFascicolo.id_organizzazione.columnName()," = :", AssegnamentoFascicolo.id_organizzazione.columnName() 
										, " and "
										, AssegnamentoFascicolo.fase			 .columnName()," = :", AssegnamentoFascicolo.fase			  .columnName());  
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AssegnamentoFascicolo.id_fascicolo.columnName(), 				entity.getIdFascicolo())
				.addValue(AssegnamentoFascicolo.id_organizzazione.columnName(), 		entity.getIdOrganizzazione())
				.addValue(AssegnamentoFascicolo.fase.columnName(), 						entity.getFase())
				.addValue(AssegnamentoFascicolo.username_funzionario.columnName(), 		entity.getUsernameFunzionario())
				.addValue(AssegnamentoFascicolo.denominazione_funzionario.columnName(), entity.getDenominazioneFunzionario())
				.addValue(AssegnamentoFascicolo.num_assegnazioni.columnName(), 			entity.getNumAssegnazioni())
				.addValue(AssegnamentoFascicolo.data_operazione.columnName(), 			new Date());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(AssegnamentoFascicoloSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<AssegnamentoFascicoloDTO> search(AssegnamentoFascicoloSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from "));
		sql.append(AssegnamentoFascicolo.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}