package it.eng.tz.puglia.autpae.repository.base;

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

import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaAdminSearchDTO;
import it.eng.tz.puglia.autpae.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente_Attribute;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.RubricaAdminRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.RubricaIstituzionaleRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table "common".Ente_Attribute
 */
@Repository
public class RubricaIstituzionaleBaseRepository  extends GenericCrudDao<RubricaIstituzionaleDTO, RubricaIstituzionaleSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(RubricaIstituzionaleBaseRepository.class);
    private final RubricaIstituzionaleRowMapper rowMapper = new RubricaIstituzionaleRowMapper();
   
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	@Override
	public List<RubricaIstituzionaleDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(RubricaIstituzionaleSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
		
	}

	@Override
	public RubricaIstituzionaleDTO find(Long pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}
	
	@Override
	public Long insert(RubricaIstituzionaleDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(RubricaIstituzionaleDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(RubricaIstituzionaleSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	
	@Override
	public PaginatedList<RubricaIstituzionaleDTO> search(RubricaIstituzionaleSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * FROM ( ")); 
		
		sql .append("select ")
			.append(Common_Ente_Attribute.pec 		 .getCompleteName()).append(" AS pec_mail ").append(", ")
			.append("true AS is_pec")															.append(", ")
			.append(Common_Ente			 .descrizione.getCompleteName())
		    .append(" from ")
			.append(Common_Ente_Attribute.getTableName())
			.append(" left join ")
		    .append(Common_Ente			 .getTableName())
		    .append(" on ")
		    .append(Common_Ente.id.getCompleteName())
		    .append(" = ")
		    .append(Common_Ente_Attribute.ente_id	 .getCompleteName());
		
		filter.setPec (filter.getPecORmail());
		filter.setMail(null);
		filter.getSqlWhereClause(sql);
		
		sql .append(" UNION ");
		
		sql .append("select ")
			.append(Common_Ente_Attribute.mail		 .getCompleteName()).append(" AS pec_mail ").append(", ")
			.append("false AS is_pec")															.append(", ")
			.append(Common_Ente			 .descrizione.getCompleteName())
		    .append(" from ")
			.append(Common_Ente_Attribute.getTableName())
			.append(" left join ")
		    .append(Common_Ente			 .getTableName())
		    .append(" on ")
		    .append(Common_Ente.id.getCompleteName())
		    .append(" = ")
		    .append(Common_Ente_Attribute.ente_id	 .getCompleteName());
		
		filter.setPec (null);
		filter.setMail(filter.getPecORmail());
		filter.getSqlWhereClause(sql);
		
		sql.append(" ) AS risultato WHERE pec_mail IS NOT null");
		sql.append(" ORDER BY risultato.")
		   .append(Common_Ente.descrizione.columnName())
		   .append(filter.getDirezione()!=null ? (" "+filter.getDirezione().name()) : "");
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return this.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}
	
	public PaginatedList<RubricaAdminSearchDTO> adminSearch(RubricaAdminSearchDTO filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT distinct "))
						
						 .append(Common_Ente_Attribute.ente_id	  .getCompleteName())
			.append(", ").append(Common_Ente_Attribute.mail		  .getCompleteName())
			.append(", ").append(Common_Ente_Attribute.pec		  .getCompleteName())
			.append(", ").append(Common_Ente		  .descrizione.getCompleteName())
			.append(", ").append(Common_Ente		  .tipo		  .getCompleteName());
		
		sql .append(" from ")
		    .append(Common_Ente_Attribute.getTableName())
		    .append(" left join ")
		    .append(Common_Ente		   	 .getTableName())
		    .append(" on ")
		    .append(Common_Ente_Attribute.ente_id.getCompleteName())
		    .append(" = ")
		    .append(Common_Ente		   	 .id	 .getCompleteName());
		
		filter.getSqlWhereClause(sql);
		
		sql.append(" ORDER BY "+Common_Ente		     .descrizione.getCompleteName()).append(filter.getDirezione()!=null ? (" "+filter.getDirezione().name()) : "")
		   .append(" , "	   +Common_Ente_Attribute.ente_id	 .getCompleteName());
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return this.paginatedList(sql.toString(), filter.getSqlParameters(), new RubricaAdminRowMapper(), filter.getPage(), filter.getLimit());
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