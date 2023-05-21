package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.StoricoAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.StoricoAssegnamentoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.StoricoAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table storico_assegnamento
 */
@Repository
public class StoricoAssegnamentoBaseRepository extends Trasmissioni_GenericCrudDao<StoricoAssegnamentoOldDTO, StoricoAssegnamentoSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(StoricoAssegnamentoBaseRepository.class);
    private final StoricoAssegnamentoRowMapper rowMapper = new StoricoAssegnamentoRowMapper();
   
    
	@Override
	public List<StoricoAssegnamentoOldDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public StoricoAssegnamentoOldDTO find(Long pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(StoricoAssegnamentoSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", StoricoAssegnamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTrasmissioniTemplateRead.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
	@Override
	public Long insert(StoricoAssegnamentoOldDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+StoricoAssegnamento.getTableName()+" ( "
                	, StoricoAssegnamento.id_fascicolo.columnName()
                ,",", StoricoAssegnamento.id_organizzazione.columnName()
                ,",", StoricoAssegnamento.fase.columnName()
                ,",", StoricoAssegnamento.rup.columnName()
                ,",", StoricoAssegnamento.username_utente.columnName()
                ,",", StoricoAssegnamento.denominazione_utente.columnName()
                ,",", StoricoAssegnamento.tipo_operazione.columnName()
                ,",", StoricoAssegnamento.data_operazione.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + StoricoAssegnamento.id_fascicolo.columnName()
                ,",:" + StoricoAssegnamento.id_organizzazione.columnName()
                ,",:" + StoricoAssegnamento.fase.columnName()
                ,",:" + StoricoAssegnamento.rup.columnName()
                ,",:" + StoricoAssegnamento.username_utente.columnName()
                ,",:" + StoricoAssegnamento.denominazione_utente.columnName()
                ,",:" + StoricoAssegnamento.tipo_operazione.columnName()
                ,",:" + StoricoAssegnamento.data_operazione.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(StoricoAssegnamento.id_fascicolo.columnName(), entity.getIdFascicolo())
				.addValue(StoricoAssegnamento.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(StoricoAssegnamento.fase.columnName(), entity.getFase())
				.addValue(StoricoAssegnamento.rup.columnName(), entity.isRup())
				.addValue(StoricoAssegnamento.username_utente.columnName(), entity.getUsernameUtente())
				.addValue(StoricoAssegnamento.denominazione_utente.columnName(), entity.getDenominazioneUtente())
				.addValue(StoricoAssegnamento.tipo_operazione.columnName(), entity.getTipoOperazione().name())
				.addValue(StoricoAssegnamento.data_operazione.columnName(), new Date());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", StoricoAssegnamento.id.columnName());
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(StoricoAssegnamentoOldDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(StoricoAssegnamentoSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<StoricoAssegnamentoOldDTO> search(StoricoAssegnamentoSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from "));
		sql.append(StoricoAssegnamento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
}