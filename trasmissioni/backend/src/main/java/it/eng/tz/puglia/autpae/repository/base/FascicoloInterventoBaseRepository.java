package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloIntervento;
import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloInterventoPK;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloInterventoRowMapper;
import it.eng.tz.puglia.autpae.search.FascicoloInterventoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table fascicolo_intervento
 */
public class FascicoloInterventoBaseRepository extends GenericCrudDao<FascicoloInterventoDTO, FascicoloInterventoSearch, FascicoloInterventoPK> {
	
	private static final Logger log = LoggerFactory.getLogger(FascicoloInterventoBaseRepository.class);
	private final FascicoloInterventoRowMapper rowMapper = new FascicoloInterventoRowMapper();

	@Override
	public List<FascicoloInterventoDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(FascicoloInterventoSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
												," from ", FascicoloIntervento.getTableName()
												);
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql, filter.getSqlParameters(), Long.class);
	}

	@Override
	public FascicoloInterventoDTO find(FascicoloInterventoPK pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from " , FascicoloIntervento.getTableName()
												, " where ", FascicoloIntervento.id_tipi_qualificazioni.columnName(), " = ?"
												, " and "  , FascicoloIntervento.id_fascicolo.columnName(), " = ?"
												);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getIdTipiQualificazioni());
		parameters.add(pk.getIdFascicolo());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, pk);
		return super.queryForObject(sql, rowMapper, parameters);
	}

	@Override
	public FascicoloInterventoPK insert(FascicoloInterventoDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" insert into ", FascicoloIntervento.getTableName()
												," ( "
												, FascicoloIntervento.id_tipi_qualificazioni.columnName(), ", "
												, FascicoloIntervento.id_fascicolo.columnName()
												, " ) "
												, " values "
												, " ( "
												, " :", FascicoloIntervento.id_tipi_qualificazioni.columnName()
												, ", :", FascicoloIntervento.id_fascicolo.columnName()
												, " ) "
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(FascicoloIntervento.id_tipi_qualificazioni.columnName(), entity.getIdTipiQualificazioni());
		parameters.put(FascicoloIntervento.id_fascicolo.columnName(), entity.getIdFascicolo());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		int ret = namedJdbcTemplate.update(sql, parameters);
		if(ret>=1) {
			return new FascicoloInterventoPK(entity.getIdFascicolo(), entity.getIdTipiQualificazioni()+"");
		}
		return null;
	}

	@Override
	public int update(FascicoloInterventoDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(FascicoloInterventoSearch entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" delete from ", FascicoloIntervento.getTableName());
		sql = entity.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, entity.getSqlParameters());
		return namedJdbcTemplate.update(sql, entity.getSqlParameters());
	}

	@Override
	public PaginatedList<FascicoloInterventoDTO> search(FascicoloInterventoSearch bean) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from ", FascicoloIntervento.getTableName());
		
		sql = bean.getSqlWhereClause(new StringBuilder(sql)).toString();
		sql = bean.getSqlOrderByClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, bean.getSqlParameters());
		return super.paginatedList(sql, bean.getSqlParameters(), rowMapper, bean.getPage(), bean.getLimit());
	}

}