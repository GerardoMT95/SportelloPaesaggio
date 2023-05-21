package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.TemplateEmail;
import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.rowmapper.TemplateEmailRowMapper;
import it.eng.tz.puglia.autpae.search.TemplateEmailSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table template_email_default
 */
@Repository
public class TemplateEmailDefaultBaseRepository extends GenericCrudDao<TemplateEmailDTO, TemplateEmailSearch, TipoTemplate> {
	
	private static final Logger log = LoggerFactory.getLogger(TemplateEmailDefaultBaseRepository.class);
	private final TemplateEmailRowMapper rowMapper = new TemplateEmailRowMapper();

	@Override
	public List<TemplateEmailDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(TemplateEmailSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public TemplateEmailDTO find(TipoTemplate pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString( " select * "
											   , " from ",  TemplateEmail.getTableName(), "_default"
											   , " where ", TemplateEmail.codice.columnName(), " = ?"
											   );
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.name());
		log.error("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return super.queryForObject(sql, rowMapper, parameters);
	}

	@Override
	public TipoTemplate insert(TemplateEmailDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(TemplateEmailDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(TemplateEmailSearch entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch bean) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

}