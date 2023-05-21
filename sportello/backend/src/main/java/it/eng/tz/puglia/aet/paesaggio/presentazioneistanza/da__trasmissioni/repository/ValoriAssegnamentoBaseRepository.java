package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.ValoriAssegnamento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.ValoriAssegnamentoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_GenericCrudDao;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table valori_assegnamento
 */
@Repository
public class ValoriAssegnamentoBaseRepository extends Trasmissioni_GenericCrudDao<ValoriAssegnamentoOldDTO, ValoriAssegnamentoSearch, Integer>
{
	private static final Logger log = LoggerFactory.getLogger(ValoriAssegnamentoBaseRepository.class);
    private final ValoriAssegnamentoRowMapper rowMapper = new ValoriAssegnamentoRowMapper();
    
    
	@Override
	public List<ValoriAssegnamentoOldDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ValoriAssegnamentoOldDTO find(Integer pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ValoriAssegnamentoSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", ValoriAssegnamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTrasmissioniTemplateRead.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public Integer insert(ValoriAssegnamentoOldDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+ValoriAssegnamento.getTableName()+" ( "
                	, ValoriAssegnamento.id_organizzazione.columnName()
                ,",", ValoriAssegnamento.fase.columnName()
                ,",", ValoriAssegnamento.rup.columnName()
                ,",", ValoriAssegnamento.username_utente.columnName()
                ,",", ValoriAssegnamento.denominazione_utente.columnName()
                ,",", ValoriAssegnamento.id_comune_tipo_procedimento.columnName()
                ," )");
		String sql2 = StringUtil.concateneString(" values ("
                ," :" + ValoriAssegnamento.id_organizzazione.columnName()
                ,",:" + ValoriAssegnamento.fase.columnName()
                ,",:" + ValoriAssegnamento.rup.columnName()
                ,",:" + ValoriAssegnamento.username_utente.columnName()
                ,",:" + ValoriAssegnamento.denominazione_utente.columnName()
                ,",:" + ValoriAssegnamento.id_comune_tipo_procedimento.columnName()
                ," )");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ValoriAssegnamento.id_organizzazione.columnName(), entity.getIdOrganizzazione())
				.addValue(ValoriAssegnamento.fase.columnName(), entity.getFase())
				.addValue(ValoriAssegnamento.rup.columnName(), entity.isRup())
				.addValue(ValoriAssegnamento.username_utente.columnName(), entity.getUsernameUtente())
				.addValue(ValoriAssegnamento.denominazione_utente.columnName(), entity.getDenominazioneUtente())
				.addValue(ValoriAssegnamento.id_comune_tipo_procedimento.columnName(), entity.getIdComuneTipoProcedimento());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.update(sql.toString(), parameters);
	}

	@Override
	public int update(ValoriAssegnamentoOldDTO entity) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("UPDATE "+ValoriAssegnamento.getTableName()+" SET "
	            	, ValoriAssegnamento.username_utente	 .columnName(), "=:", ValoriAssegnamento.username_utente	 .columnName()
	            ,",", ValoriAssegnamento.denominazione_utente.columnName(), "=:", ValoriAssegnamento.denominazione_utente.columnName());

		sql = StringUtil.concateneString( sql
										, " WHERE "
										, ValoriAssegnamento.id_organizzazione		    .columnName()," = :", ValoriAssegnamento.id_organizzazione		  	.columnName() 
										, " and "
										, ValoriAssegnamento.fase			   			.columnName()," = :", ValoriAssegnamento.fase						.columnName()
										, " and "
										, ValoriAssegnamento.id_comune_tipo_procedimento.columnName()," = :", ValoriAssegnamento.id_comune_tipo_procedimento.columnName()
										, " and "
										, ValoriAssegnamento.rup						.columnName()," = :", ValoriAssegnamento.rup						.columnName());
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(ValoriAssegnamento.id_organizzazione.columnName(), 			entity.getIdOrganizzazione())
				.addValue(ValoriAssegnamento.fase.columnName(), 						entity.getFase())
				.addValue(ValoriAssegnamento.id_comune_tipo_procedimento.columnName(), 	entity.getIdComuneTipoProcedimento())
				.addValue(ValoriAssegnamento.rup.columnName(), 							entity.isRup())
				.addValue(ValoriAssegnamento.username_utente.columnName(), 				entity.getUsernameUtente())
				.addValue(ValoriAssegnamento.denominazione_utente.columnName(),    		entity.getDenominazioneUtente());
		
		log.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTrasmissioniTemplateWrite.update(sql, parameters);
	}

	@Override
	public int delete(ValoriAssegnamentoSearch search) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("DELETE from ", ValoriAssegnamento.getTableName()));

		search.getSqlWhereClause(sql);
			   
		log.info("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTrasmissioniTemplateWrite.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<ValoriAssegnamentoOldDTO> search(ValoriAssegnamentoSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * from "));
		sql.append(ValoriAssegnamento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}