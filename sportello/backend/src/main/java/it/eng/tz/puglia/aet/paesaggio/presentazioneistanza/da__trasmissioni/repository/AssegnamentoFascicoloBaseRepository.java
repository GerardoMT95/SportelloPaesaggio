package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.AssegnamentoFascicoloRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table assegnamento_fascicolo
 */
public class AssegnamentoFascicoloBaseRepository extends Trasmissioni_GenericCrudDao<AssegnamentoFascicoloOldDTO, AssegnamentoFascicoloSearch, Integer>
{
	private static final Logger log = LoggerFactory.getLogger(AssegnamentoFascicoloBaseRepository.class);
    private final AssegnamentoFascicoloRowMapper rowMapper = new AssegnamentoFascicoloRowMapper();
   
    
	@Override
	public List<AssegnamentoFascicoloOldDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public AssegnamentoFascicoloOldDTO find(Integer pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public long count(AssegnamentoFascicoloSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", AssegnamentoFascicolo.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTrasmissioniTemplateRead.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
	@Override
	public Integer insert(AssegnamentoFascicoloOldDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+AssegnamentoFascicolo.getTableName()+" ( "
                	, AssegnamentoFascicolo.id_fascicolo.columnName()
                ,",", AssegnamentoFascicolo.id_organizzazione.columnName()
                ,",", AssegnamentoFascicolo.fase.columnName()
                ,",", AssegnamentoFascicolo.rup.columnName()
                ,",", AssegnamentoFascicolo.username_utente.columnName()
                ,",", AssegnamentoFascicolo.denominazione_utente.columnName()
                ,",", AssegnamentoFascicolo.num_assegnazioni.columnName()
                ,",", AssegnamentoFascicolo.data_operazione.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + AssegnamentoFascicolo.id_fascicolo.columnName()
                ,",:" + AssegnamentoFascicolo.id_organizzazione.columnName()
                ,",:" + AssegnamentoFascicolo.fase.columnName()
                ,",:" + AssegnamentoFascicolo.rup.columnName()
                ,",:" + AssegnamentoFascicolo.username_utente.columnName()
                ,",:" + AssegnamentoFascicolo.denominazione_utente.columnName()
                ,",:" + AssegnamentoFascicolo.num_assegnazioni.columnName()
                ,",:" + AssegnamentoFascicolo.data_operazione.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AssegnamentoFascicolo.id_fascicolo.columnName(), entity.getIdFascicolo())
				.addValue(AssegnamentoFascicolo.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(AssegnamentoFascicolo.fase.columnName(), entity.getFase())
				.addValue(AssegnamentoFascicolo.rup.columnName(), entity.isRup())
				.addValue(AssegnamentoFascicolo.username_utente.columnName(), entity.getUsernameUtente())
				.addValue(AssegnamentoFascicolo.denominazione_utente.columnName(), entity.getDenominazioneUtente())
				.addValue(AssegnamentoFascicolo.num_assegnazioni.columnName(), entity.getNumAssegnazioni())
				.addValue(AssegnamentoFascicolo.data_operazione.columnName(), new Date());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.update(sql.toString(), parameters);
	}

	@Override
	public int update(AssegnamentoFascicoloOldDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("UPDATE "+AssegnamentoFascicolo.getTableName()+" SET "
	            	, AssegnamentoFascicolo.username_utente	 	.columnName(), "=:", AssegnamentoFascicolo.username_utente	 	.columnName()
	            ,",", AssegnamentoFascicolo.denominazione_utente.columnName(), "=:", AssegnamentoFascicolo.denominazione_utente .columnName()
	            ,",", AssegnamentoFascicolo.num_assegnazioni	.columnName(), "=:", AssegnamentoFascicolo.num_assegnazioni		.columnName()
	            ,",", AssegnamentoFascicolo.data_operazione		.columnName(), "=:", AssegnamentoFascicolo.data_operazione		.columnName());

		sql = StringUtil.concateneString( sql
									    , " WHERE "
										, AssegnamentoFascicolo.id_fascicolo	 .columnName()," = :", AssegnamentoFascicolo.id_fascicolo	  .columnName()
										, " and "
										, AssegnamentoFascicolo.id_organizzazione.columnName()," = :", AssegnamentoFascicolo.id_organizzazione.columnName() 
										, " and "
										, AssegnamentoFascicolo.fase			 .columnName()," = :", AssegnamentoFascicolo.fase			  .columnName()
										, " and "
										, AssegnamentoFascicolo.rup				 .columnName()," = :", AssegnamentoFascicolo.rup			  .columnName());
										
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(AssegnamentoFascicolo.id_fascicolo.columnName(), 			entity.getIdFascicolo())
				.addValue(AssegnamentoFascicolo.id_organizzazione.columnName(), 	entity.getIdOrganizzazione())
				.addValue(AssegnamentoFascicolo.fase.columnName(), 					entity.getFase())
				.addValue(AssegnamentoFascicolo.rup.columnName(), 					entity.isRup())
				.addValue(AssegnamentoFascicolo.username_utente.columnName(), 		entity.getUsernameUtente())
				.addValue(AssegnamentoFascicolo.denominazione_utente.columnName(), 	entity.getDenominazioneUtente())
				.addValue(AssegnamentoFascicolo.num_assegnazioni.columnName(), 		entity.getNumAssegnazioni())
				.addValue(AssegnamentoFascicolo.data_operazione.columnName(), 		new Date());
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.update(sql, parameters);
	}

	@Override
	public int delete(AssegnamentoFascicoloSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<AssegnamentoFascicoloOldDTO> search(AssegnamentoFascicoloSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from "));
		sql.append(AssegnamentoFascicolo.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
	public List<String> findRupEFunzionrio(UUID idPratica, Long idOrganizzazione) throws Exception
	{
		String sql = StringUtil.concateneString("select \"username_utente\" from",
												"\"presentazione_istanza\".\"assegnamento_fascicolo\" ",
												"where \"id_fascicolo\" = :id_fascicolo ",
												"and \"id_organizzazione\" = :id_organizzazione");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_fascicolo", idPratica);
		parameters.put("id_organizzazione", idOrganizzazione);
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateRead.queryForList(sql, parameters, String.class);
	}
	
}