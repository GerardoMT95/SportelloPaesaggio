package it.eng.tz.puglia.autpae.repository.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.dbMapping.Campionamento;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.rowmapper.CampionamentoRowMapper;
import it.eng.tz.puglia.autpae.search.CampionamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table campionamento
 */
public class CampionamentoBaseRepository extends GenericCrudDao<CampionamentoDTO, CampionamentoSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(CampionamentoBaseRepository.class);
    private final CampionamentoRowMapper rowMapper = new CampionamentoRowMapper();
   
	@Override
	public List<CampionamentoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(CampionamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", Campionamento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public CampionamentoDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Campionamento.getTableName())
		   .append(" where ")
		   .append(Campionamento.id.getCompleteName())
		   .append(" = :")
		   .append(Campionamento.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Campionamento.id.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public Long insert(CampionamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql1 = StringUtil.concateneString("insert into "+Campionamento.getTableName()+" ( "
                	, Campionamento.attivo.columnName()
                ,",", Campionamento.intervallo_camp.columnName()
                ,",", Campionamento.tipo_successivo.columnName()
                ,",", Campionamento.percentuale.columnName()
                ,",", Campionamento.notifica_camp.columnName()
                ,",", Campionamento.notifica_ver.columnName()
                ,",", Campionamento.intervallo_ver.columnName()
                ,",", Campionamento.esito_pubb.columnName()
                ,",", Campionamento.customized.columnName()
                ,",", Campionamento.data_inizio.columnName()
                ,",", Campionamento.data_campionatura.columnName()
                ," )");
		String sql2 = StringUtil.concateneString("values ("
                ," :" + Campionamento.attivo.columnName()
                ,",:" + Campionamento.intervallo_camp.columnName()
                ,",:" + Campionamento.tipo_successivo.columnName()
                ,",:" + Campionamento.percentuale.columnName()
                ,",:" + Campionamento.notifica_camp.columnName()
                ,",:" + Campionamento.notifica_ver.columnName()
                ,",:" + Campionamento.intervallo_ver.columnName()
                ,",:" + Campionamento.esito_pubb.columnName()
                ,",:" + Campionamento.customized.columnName()
                ,",:" + Campionamento.data_inizio.columnName()
                ,",:" + Campionamento.data_campionatura.columnName()
                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Campionamento.attivo.columnName(), 			entity.isAttivo())
				.addValue(Campionamento.intervallo_camp.columnName(), 	entity.getIntervalloCamp())
				.addValue(Campionamento.tipo_successivo.columnName(), 	entity.getTipoSuccessivo().name())
				.addValue(Campionamento.percentuale.columnName(), 		entity.getPercentuale())
				.addValue(Campionamento.notifica_camp.columnName(), 	entity.getNotificaCamp())
				.addValue(Campionamento.notifica_ver.columnName(), 		entity.getNotificaVer())
				.addValue(Campionamento.intervallo_ver.columnName(), 	entity.getIntervalloVer())
				.addValue(Campionamento.esito_pubb.columnName(), 		entity.isEsitoPubb())
				.addValue(Campionamento.customized.columnName(), 		entity.isCustomized())
				.addValue(Campionamento.data_inizio.columnName(),	 	entity.getDataInizio())
				.addValue(Campionamento.data_campionatura.columnName(), entity.getDataCampionatura());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", Campionamento.id.columnName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(CampionamentoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("update "+Campionamento.getTableName()+" set "
	            	, Campionamento.attivo			 .columnName(), "=:", Campionamento.attivo			 .columnName()
	            ,",", Campionamento.intervallo_camp	 .columnName(), "=:", Campionamento.intervallo_camp	 .columnName()
	            ,",", Campionamento.tipo_successivo	 .columnName(), "=:", Campionamento.tipo_successivo	 .columnName()
	            ,",", Campionamento.percentuale		 .columnName(), "=:", Campionamento.percentuale		 .columnName()
	            ,",", Campionamento.notifica_camp	 .columnName(), "=:", Campionamento.notifica_camp	 .columnName()
	            ,",", Campionamento.notifica_ver	 .columnName(), "=:", Campionamento.notifica_ver	 .columnName()
	            ,",", Campionamento.intervallo_ver	 .columnName(), "=:", Campionamento.intervallo_ver	 .columnName()
	            ,",", Campionamento.esito_pubb		 .columnName(), "=:", Campionamento.esito_pubb		 .columnName()
	            ,",", Campionamento.customized		 .columnName(), "=:", Campionamento.customized		 .columnName()
	            ,",", Campionamento.data_inizio      .columnName(), "=:", Campionamento.data_inizio		 .columnName()
	            ,",", Campionamento.data_campionatura.columnName(), "=:", Campionamento.data_campionatura.columnName()
				);

		StringUtil.concateneString(sql," where ",Campionamento.getTableName(),".",Campionamento.id.columnName()," = :",
																				  Campionamento.id.columnName());  
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Campionamento.id				 .columnName(), entity.getId())
				.addValue(Campionamento.attivo			 .columnName(), entity.isAttivo())
				.addValue(Campionamento.intervallo_camp	 .columnName(), entity.getIntervalloCamp())
				.addValue(Campionamento.tipo_successivo	 .columnName(), entity.getTipoSuccessivo().name())
				.addValue(Campionamento.percentuale		 .columnName(), entity.getPercentuale())
				.addValue(Campionamento.notifica_camp	 .columnName(), entity.getNotificaCamp())
				.addValue(Campionamento.notifica_ver	 .columnName(), entity.getNotificaVer())
				.addValue(Campionamento.intervallo_ver	 .columnName(), entity.getIntervalloVer())
				.addValue(Campionamento.esito_pubb		 .columnName(), entity.isEsitoPubb())
				.addValue(Campionamento.customized		 .columnName(), entity.isCustomized())
				.addValue(Campionamento.data_inizio		 .columnName(), entity.getDataInizio())
				.addValue(Campionamento.data_campionatura.columnName(), entity.getDataCampionatura());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

	@Override
	public int delete(CampionamentoSearch search) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
			   StringUtil.concateneString("delete from ", Campionamento.getTableName()));

		search.getSqlWhereClause(sql);
			   
        log.trace("Sql -> {} Parameters -> {}", sql, search.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), search.getSqlParameters());
	}

	@Override
	public PaginatedList<CampionamentoDTO> search(CampionamentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Campionamento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}