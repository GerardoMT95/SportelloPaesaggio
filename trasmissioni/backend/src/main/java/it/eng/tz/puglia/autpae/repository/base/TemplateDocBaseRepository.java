package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.TemplateDoc;
import it.eng.tz.puglia.autpae.entity.TemplateDocDTO;
import it.eng.tz.puglia.autpae.rowmapper.TemplateDocRowMapper;
import it.eng.tz.puglia.autpae.search.TemplateDocSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table template_email
 */
@Repository
public class TemplateDocBaseRepository extends GenericCrudDao<TemplateDocDTO, TemplateDocSearch, String> {
	
	private static final Logger log = LoggerFactory.getLogger(TemplateDocBaseRepository.class);
	private final TemplateDocRowMapper rowMapper = new TemplateDocRowMapper();

	@Override
	public List<TemplateDocDTO> select() throws Exception {
		String sql = StringUtil.concateneString(" SELECT * "                                           
				," FROM ", TemplateDoc.getTableName(), " ORDER BY ",TemplateDoc.nome.columnName());
			log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, null);
			return super.queryForList(sql, rowMapper, new ArrayList<Object>());
	}
	
	
//	@Override
//	public List<TemplateDocDTO> selectPaginated(BaseSearchRequestBean filters) throws Exception{
//		String sql = StringUtil.concateneString(" select * ",
//				" from ", TemplateDoc.getTableName());
//		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filters);
//		return super.queryForList(sql, rowMapper, filters);
//
//	}

	@Override
	public long count(TemplateDocSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
												," from ", TemplateDoc.getTableName());
		
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql, filter.getSqlParameters(), Long.class);
	}

	@Override
	public TemplateDocDTO find(String pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString( " select * "
												, " from ",  TemplateDoc.getTableName()
												, " where ", TemplateDoc.nome.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return super.queryForObject(sql, rowMapper, parameters);
	}

	@Override
	public String insert(TemplateDocDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(TemplateDocDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" update ", TemplateDoc.getTableName()
												, " set ", TemplateDoc.nome.columnName(), " = ?, "
												, TemplateDoc.descrizione.columnName(), " = ?, "
												, " where ", TemplateDoc.nome.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getNome());
		parameters.add(entity.getDescrizione());
		parameters.add(entity.getNome());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return super.update(sql, parameters);
	}

	@Override
	public int delete(TemplateDocSearch entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<TemplateDocDTO> search(TemplateDocSearch bean) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT * FROM  ", TemplateDoc.getTableName())); 
//		sql.append(" ORDER BY ")
//		.append(bean.getColonna().columnName())
//		.append(bean.getDirezione()!=null ? (" "+bean.getDirezione().name()) : "");
		sql.append(" ");
		bean.getSqlWhereClause(sql);
		bean.getSqlOrderByClause(sql);

		log.trace("Sql -> {} Parameters -> {}", sql, bean.getSqlParameters());
		return this.paginatedList(sql.toString(), bean.getSqlParameters(), rowMapper,  bean.getPage(), bean.getLimit());

	}

}