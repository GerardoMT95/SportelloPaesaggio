package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.ConfigurazioneAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.ConfigurazioneAssegnamentoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ConfigurazioneAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table configurazione_assegnamento
 */
@Repository
public class ConfigurazioneAssegnamentoBaseRepository extends Trasmissioni_GenericCrudDao<ConfigurazioneAssegnamentoDTO, ConfigurazioneAssegnamentoSearch, Integer>
{
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneAssegnamentoBaseRepository.class);
    private final ConfigurazioneAssegnamentoRowMapper rowMapper = new ConfigurazioneAssegnamentoRowMapper();
   
    
	@Override
	public List<ConfigurazioneAssegnamentoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ConfigurazioneAssegnamentoDTO find(Integer pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(ConfigurazioneAssegnamentoSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", ConfigurazioneAssegnamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTrasmissioniTemplateRead.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
	@Override
	public Integer insert(ConfigurazioneAssegnamentoDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+ConfigurazioneAssegnamento.getTableName()+" ( "
                	, ConfigurazioneAssegnamento.id_organizzazione.columnName()
                ,",", ConfigurazioneAssegnamento.fase.columnName()
                ,",", ConfigurazioneAssegnamento.rup.columnName()
                ,",", ConfigurazioneAssegnamento.criterio_assegnamento.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + ConfigurazioneAssegnamento.id_organizzazione.columnName()
                ,",:" + ConfigurazioneAssegnamento.fase.columnName()
                ,",:" + ConfigurazioneAssegnamento.rup.columnName()
                ,",:" + ConfigurazioneAssegnamento.criterio_assegnamento.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ConfigurazioneAssegnamento.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(ConfigurazioneAssegnamento.fase.columnName(), entity.getFase())
				.addValue(ConfigurazioneAssegnamento.rup.columnName(), entity.isRup())
				.addValue(ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), entity.getCriterioAssegnamento().name());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.update(sql.toString(), parameters);
	}

	@Override
	public int update(ConfigurazioneAssegnamentoDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("UPDATE "+ConfigurazioneAssegnamento.getTableName()+" SET "
	            		   , ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), "=:", ConfigurazioneAssegnamento.criterio_assegnamento.columnName());

		sql = StringUtil.concateneString( sql
										, " WHERE "
										, ConfigurazioneAssegnamento.id_organizzazione.columnName()," = :", ConfigurazioneAssegnamento.id_organizzazione.columnName() 
										, " and "
										, ConfigurazioneAssegnamento.fase			  .columnName()," = :", ConfigurazioneAssegnamento.fase			    .columnName()
										, " and "
										, ConfigurazioneAssegnamento.rup			  .columnName()," = :", ConfigurazioneAssegnamento.rup			    .columnName());
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ConfigurazioneAssegnamento.id_organizzazione	  .columnName(), entity.getIdOrganizzazione())
				.addValue(ConfigurazioneAssegnamento.fase			      .columnName(), entity.getFase())
				.addValue(ConfigurazioneAssegnamento.rup				  .columnName(), entity.isRup())
				.addValue(ConfigurazioneAssegnamento.criterio_assegnamento.columnName(), entity.getCriterioAssegnamento().name());
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.update(sql, parameters);
	}

	@Override
	public int delete(ConfigurazioneAssegnamentoSearch search) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("DELETE from ", ConfigurazioneAssegnamento.getTableName()));

		search.getSqlWhereClause(sql);
			   
		log.info("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTrasmissioniTemplateWrite.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<ConfigurazioneAssegnamentoDTO> search(ConfigurazioneAssegnamentoSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from ", ConfigurazioneAssegnamento.getTableName()));
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}