package it.eng.tz.puglia.autpae.repository.base;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.StoricoAssegnamento;
import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.rowmapper.StoricoAssegnamentoRowMapper;
import it.eng.tz.puglia.autpae.search.StoricoAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table storico_assegnamento
 */
@Repository
public class StoricoAssegnamentoBaseRepository  extends GenericCrudDao<StoricoAssegnamentoDTO, StoricoAssegnamentoSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(StoricoAssegnamentoBaseRepository.class);
    private final StoricoAssegnamentoRowMapper rowMapper = new StoricoAssegnamentoRowMapper();
   
    
	@Override
	public List<StoricoAssegnamentoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public StoricoAssegnamentoDTO find(Long pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(StoricoAssegnamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", StoricoAssegnamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
	@Override
	public Long insert(StoricoAssegnamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+StoricoAssegnamento.getTableName()+" ( "
                	, StoricoAssegnamento.id_fascicolo.columnName()
                ,",", StoricoAssegnamento.id_organizzazione.columnName()
                ,",", StoricoAssegnamento.fase.columnName()
                ,",", StoricoAssegnamento.username_funzionario.columnName()
                ,",", StoricoAssegnamento.denominazione_funzionario.columnName()
                ,",", StoricoAssegnamento.tipo_operazione.columnName()
                ,",", StoricoAssegnamento.data_operazione.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + StoricoAssegnamento.id_fascicolo.columnName()
                ,",:" + StoricoAssegnamento.id_organizzazione.columnName()
                ,",:" + StoricoAssegnamento.fase.columnName()
                ,",:" + StoricoAssegnamento.username_funzionario.columnName()
                ,",:" + StoricoAssegnamento.denominazione_funzionario.columnName()
                ,",:" + StoricoAssegnamento.tipo_operazione.columnName()
                ,",:" + StoricoAssegnamento.data_operazione.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(StoricoAssegnamento.id_fascicolo.columnName(), entity.getIdFascicolo())
				.addValue(StoricoAssegnamento.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(StoricoAssegnamento.fase.columnName(), entity.getFase())
				.addValue(StoricoAssegnamento.username_funzionario.columnName(), entity.getUsernameFunzionario())
				.addValue(StoricoAssegnamento.denominazione_funzionario.columnName(), entity.getDenominazioneFunzionario())
				.addValue(StoricoAssegnamento.tipo_operazione.columnName(), entity.getTipoOperazione().name())
				.addValue(StoricoAssegnamento.data_operazione.columnName(), new Date());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", StoricoAssegnamento.id.columnName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(StoricoAssegnamentoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(StoricoAssegnamentoSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<StoricoAssegnamentoDTO> search(StoricoAssegnamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from "));
		sql.append(StoricoAssegnamento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}