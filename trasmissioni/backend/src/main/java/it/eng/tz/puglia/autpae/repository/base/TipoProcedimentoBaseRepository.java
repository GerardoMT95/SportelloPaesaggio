package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.rowmapper.TipoProcedimentoRowMapper;
import it.eng.tz.puglia.autpae.search.TipoProcedimentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table tipo_procedimento
 */
@Repository
public class TipoProcedimentoBaseRepository  extends GenericCrudDao<TipoProcedimentoDTO, TipoProcedimentoSearch, String>
{
	private static final Logger log = LoggerFactory.getLogger(TipoProcedimentoBaseRepository.class);
    private final TipoProcedimentoRowMapper rowMapper = new TipoProcedimentoRowMapper();
   
	@Override
	public List<TipoProcedimentoDTO> select() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * from "
										, TipoProcedimento.getTableName()
										, " order by ",TipoProcedimento.applicativo.columnName()
										,", ",TipoProcedimento.descrizione.columnName());
		log.trace("Sql -> {} Parameters -> null", sql);
		return super.queryForList(sql, rowMapper);
	}
	
	
	public List<TipoProcedimentoDTO> selectAllByApplication(Applicativo app) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString(" select * from "
				, TipoProcedimento.getTableName()
				," where applicativo=?"
				, " order by ",TipoProcedimento.applicativo.columnName()
				,", ",TipoProcedimento.descrizione.columnName());

		List<Object> parameters = new ArrayList<>();
		parameters.add(app.name());
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForList(sql, rowMapper, parameters);
	}
	
	/**
	 * Metodo che restituisce la lista di tipi procedimento (validi in base a data inizio e fine rispetto ad oggi)
	 * relativi all'applicativo ricevuto in input 
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @param app
	 * @return
	 * @throws Exception
	 */
	public List<TipoProcedimentoDTO> selectByApplication(Applicativo app) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString(" select * from "
												, TipoProcedimento.getTableName()
												," where applicativo=?"
												," and inizio_validita<=current_date"
												," and fine_validita>=current_date");
		List<Object> parameters = new ArrayList<>();
		parameters.add(app.name());
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForList(sql, rowMapper, parameters);
	}

	@Override
	public long count(TipoProcedimentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", TipoProcedimento.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public TipoProcedimentoDTO find(String pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(TipoProcedimento.getTableName())
		   .append(" where ")
		   .append(TipoProcedimento.codice.getCompleteName())
		   .append(" = :")
		   .append(TipoProcedimento.codice.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(TipoProcedimento.codice.columnName(), pk);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public String insert(TipoProcedimentoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(TipoProcedimentoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	public int updateValidita(TipoProcedimentoDTO dto) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		String sql = StringUtil.concateneString(
				"update ",TipoProcedimento.getTableName()
		   		," set " ,TipoProcedimento.inizio_validita.columnName()
		   		," = ? "
		   		,", "    ,TipoProcedimento.fine_validita.columnName()
		   		," = ? "
		   		," where ",TipoProcedimento.codice.columnName()
		   		," = ?");
		List<Object> parameters= new ArrayList<Object>();
		parameters.add(dto.getInizioValidita());
		parameters.add(dto.getFineValidita());
		parameters.add(dto.getCodice());
		return super.update(sql, parameters);
	}

	@Override
	public int delete(TipoProcedimentoSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<TipoProcedimentoDTO> search(TipoProcedimentoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(TipoProcedimento.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}