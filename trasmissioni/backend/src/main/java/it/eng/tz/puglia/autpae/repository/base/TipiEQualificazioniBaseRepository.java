package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.dbMapping.TipiEQualificazioni;
import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
import it.eng.tz.puglia.autpae.rowmapper.TipiEQualificazioniRowMapper;
import it.eng.tz.puglia.autpae.search.TipiEQualificazioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table tipi_e_qualificazioni
 */
public class TipiEQualificazioniBaseRepository  extends GenericCrudDao<TipiEQualificazioniDTO, TipiEQualificazioniSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(TipiEQualificazioniBaseRepository.class);
    private final TipiEQualificazioniRowMapper rowMapper = new TipiEQualificazioniRowMapper();
    
	@Override
	public long count(TipiEQualificazioniSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString(" select count(*) "                                           
								               ," from ", TipiEQualificazioni.getTableName());
		
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql, filter.getSqlParameters(), Long.class);
	}
    
    @Override
    public TipiEQualificazioniDTO find(Long pk) throws Exception {
    	log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	
    	String sql = StringUtil.concateneString( " select * "
    										   , " from " , TipiEQualificazioni.getTableName()
    										   , " where ", TipiEQualificazioni.id.columnName(), " = ?"
    										   );
    	List<Object> parameters = new ArrayList<Object>();
    	parameters.add(pk);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, pk);
    	return super.queryForObject(sql, rowMapper, parameters);
    }

	@Override
	public List<TipiEQualificazioniDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public Long insert(TipiEQualificazioniDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(TipiEQualificazioniDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(TipiEQualificazioniSearch search) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public PaginatedList<TipiEQualificazioniDTO> search(TipiEQualificazioniSearch bean) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString(" select * "
											   ," from ", TipiEQualificazioni.getTableName());
		
		sql = bean.getSqlWhereClause(new StringBuilder(sql)).toString();
		sql = bean.getSqlOrderByClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, bean.getSqlParameters());
		return super.paginatedList(sql, bean.getSqlParameters(), rowMapper, bean.getPage(), bean.getLimit());
	}

}