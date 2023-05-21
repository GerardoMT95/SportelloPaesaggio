package it.eng.tz.puglia.autpae.repository.base;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ProcedimentoQualificazioni;
import it.eng.tz.puglia.autpae.entity.ProcedimentoQualificazioniDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ProcedimentoQualificazioniPK;
import it.eng.tz.puglia.autpae.rowmapper.ProcedimentoQualificazioniRowMapper;
import it.eng.tz.puglia.autpae.search.ProcedimentoQualificazioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table procedimento_qualificazioni
 */
@Repository
public class ProcedimentoQualificazioniBaseRepository  extends GenericCrudDao<ProcedimentoQualificazioniDTO, ProcedimentoQualificazioniSearch, ProcedimentoQualificazioniPK>
{
	private static final Logger log = LoggerFactory.getLogger(ProcedimentoQualificazioniBaseRepository.class);
    private final ProcedimentoQualificazioniRowMapper rowMapper = new ProcedimentoQualificazioniRowMapper();
   
	@Override
	public List<ProcedimentoQualificazioniDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ProcedimentoQualificazioniSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", ProcedimentoQualificazioni.getTableName()));

				   filter.getSqlWhereClause(sql);
				   
	        log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	        return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public ProcedimentoQualificazioniDTO find(ProcedimentoQualificazioniPK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

//		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
//		sql.append(ProcedimentoQualificazioni.getTableName())
//		   .append(" where ")
//		   .append(ProcedimentoQualificazioni.id_tipi_qualificazioni.getCompleteName())
//		   .append(" = :")
//		   .append(ProcedimentoQualificazioni.id_tipi_qualificazioni.columnName())
//		   .append(" and ")
//		   .append(ProcedimentoQualificazioni.codice_tipo_procedimento.getCompleteName())
//		   .append(" = :")
//		   .append(ProcedimentoQualificazioni.codice_tipo_procedimento.columnName());
//		
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put(ProcedimentoQualificazioni.id_tipi_qualificazioni.columnName(), pk.getIdTipiQualificazioni());
//		parameters.put(ProcedimentoQualificazioni.codice_tipo_procedimento.columnName(), pk.getCodiceTipoProcedimento());
//		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
//		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ProcedimentoQualificazioniPK insert(ProcedimentoQualificazioniDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(ProcedimentoQualificazioniDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(ProcedimentoQualificazioniSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<ProcedimentoQualificazioniDTO> search(ProcedimentoQualificazioniSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(ProcedimentoQualificazioni.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}