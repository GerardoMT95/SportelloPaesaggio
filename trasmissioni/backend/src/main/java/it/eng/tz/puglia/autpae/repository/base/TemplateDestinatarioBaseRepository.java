package it.eng.tz.puglia.autpae.repository.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import it.eng.tz.puglia.autpae.dbMapping.TemplateDestinatario;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.rowmapper.DestinatarioTemplateRowMapper;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table template_destinatario
 */
public class TemplateDestinatarioBaseRepository  extends GenericCrudDao<DestinatarioTemplateDTO, TemplateDestinatarioSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(TemplateDestinatarioBaseRepository.class);
    //private final TemplateDestinatarioRowMapper rowMapper = new TemplateDestinatarioRowMapper();
	private final DestinatarioTemplateRowMapper rowMapper = new DestinatarioTemplateRowMapper();
	
	@Override
	public List<DestinatarioTemplateDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(TemplateDestinatarioSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", TemplateDestinatario.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public DestinatarioTemplateDTO find(Long pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public Long insert(DestinatarioTemplateDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+TemplateDestinatario.getTableName()+" ("
                	, TemplateDestinatario.email.columnName()
                ,",", TemplateDestinatario.pec.columnName()
                ,",", TemplateDestinatario.codice_template.columnName()
                ,",", TemplateDestinatario.denominazione.columnName()
                ,",", TemplateDestinatario.tipo.columnName()
                ,")");
		String sql2 = StringUtil.concateneString(" values ("
                , ":" + TemplateDestinatario.email.columnName()
                ,",:" + TemplateDestinatario.pec.columnName()
                ,",:" + TemplateDestinatario.codice_template.columnName()
                ,",:" + TemplateDestinatario.denominazione.columnName()
                ,",:" + TemplateDestinatario.tipo.columnName()
                ,")");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(TemplateDestinatario.email.columnName(), entity.getEmail())
				.addValue(TemplateDestinatario.pec.columnName(), entity.isPec())
				.addValue(TemplateDestinatario.codice_template.columnName(), entity.getCodiceTemplate().name())
				.addValue(TemplateDestinatario.denominazione.columnName(), entity.getNome())
				.addValue(TemplateDestinatario.tipo.columnName(), entity.getTipo().name());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return Long.valueOf(namedJdbcTemplate.update(sql.toString(), parameters));
	}

	@Override
	public int update(DestinatarioTemplateDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(TemplateDestinatarioSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<DestinatarioTemplateDTO> search(TemplateDestinatarioSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(TemplateDestinatario.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}