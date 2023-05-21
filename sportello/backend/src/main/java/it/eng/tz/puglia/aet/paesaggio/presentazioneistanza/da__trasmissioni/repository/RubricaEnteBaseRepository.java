package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.RubricaEnteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_GenericCrudDao;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Organizzazione_Rubrica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table "common".Paesaggio_Organizzazione_Rubrica
 */
@Repository
public class RubricaEnteBaseRepository  extends Trasmissioni_GenericCrudDao<RubricaEnteDTO, RubricaEnteSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(RubricaEnteBaseRepository.class);
    private final RubricaEnteRowMapper rowMapper = new RubricaEnteRowMapper();
   
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	@Override
	public List<RubricaEnteDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(RubricaEnteSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	/*	log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) FROM ( ", Common_Paesaggio_Organizzazione_Rubrica.getTableName()));

		sql .append("select ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.pec .getCompleteName()).append(" AS pec_mail ").append(", ")
			.append("true AS is_pec")
		    .append(" from ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.getTableName());
		
		filter.setPec (filter.getPecORmail());
		filter.setMail(null);
		filter.getSqlWhereClause(sql);
		
		sql .append(" UNION ");
		
		sql .append("select ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.mail.getCompleteName()).append(" AS pec_mail ").append(", ")
			.append("false AS is_pec")
		    .append(" from ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.getTableName());
		
		filter.setPec (null);
		filter.setMail(filter.getPecORmail());
		filter.getSqlWhereClause(sql);
		
		sql.append(" ) AS risultato WHERE pec_mail IS NOT null");
		
	    log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);	*/
	}

	@Override
	public RubricaEnteDTO find(Long pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public Long insert(RubricaEnteDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(RubricaEnteDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(RubricaEnteSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

/*	@Override 
//	public PaginatedList<RubricaEnteDTO> search(RubricaEnteSearch filter) throws Exception {
//		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//
//		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * FROM ( select distinct ", Common_Paesaggio_Organizzazione_Rubrica.getTableName()));
//		
//		filter.getSqlWhereClause(sql);
//		sql.append(" ) ");
//		filter.getSqlOrderByClause(sql);
//		
//		log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
//		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
//	}	*/
	
	@Override
	public PaginatedList<RubricaEnteDTO> search(RubricaEnteSearch filter) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * FROM ( ")); 
		
		sql .append("select ")
		 	.append(Common_Paesaggio_Organizzazione_Rubrica.id	.getCompleteName())						   .append(", ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.pec .getCompleteName()).append(" AS pec_mail ").append(", ")
			.append("true AS is_pec")																	   .append(", ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.nome.getCompleteName())
		    .append(" from ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.getTableName());
		
		filter.setPec (filter.getPecORmail());
		filter.setMail(null);
		filter.getSqlWhereClause(sql);
		
		sql .append(" UNION ");
		
		sql .append("select ")
		 	.append(Common_Paesaggio_Organizzazione_Rubrica.id	.getCompleteName())						   .append(", ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.mail.getCompleteName()).append(" AS pec_mail ").append(", ")
			.append("false AS is_pec")																   	   .append(", ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.nome.getCompleteName())
		    .append(" from ")
			.append(Common_Paesaggio_Organizzazione_Rubrica.getTableName());
		
		filter.setPec (null);
		filter.setMail(filter.getPecORmail());
		filter.getSqlWhereClause(sql);
		
		sql.append(" ) AS risultato WHERE pec_mail IS NOT null");
		sql.append(" ORDER BY risultato.")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.nome.columnName())
		   .append(filter.getDirezione()!=null ? (" "+filter.getDirezione().name()) : "");
		
	//	filter.setColonna(Common_Paesaggio_Organizzazione_Rubrica.nome);
	//	filter.getSqlOrderByClause(sql);
		
		log.info("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return this.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
	@Override
	public <T>PaginatedList<T> paginatedList(String sql, Map<String, Object> paramMap, RowMapper<T> rowMapper, int page, int limit) {
		StopWatch sw = this.startWatch("paginatedList");
		try {
			String  countSql     = StringUtil.concateneString("select count(*) FROM (", sql , ") t");
			String  paginatedSql = super.getPaginatedSql(sql, page, limit);
			int     count        = namedJdbcTemplate.queryForObject(countSql, paramMap, Integer.class);
			List<T> list         = namedJdbcTemplate.query(paginatedSql, paramMap, rowMapper);
			return new PaginatedList<T>(list, count);
		}finally {
			this.stopWatch(sw);
		}
	}
	
}