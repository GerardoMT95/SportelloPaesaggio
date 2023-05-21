package it.eng.tz.puglia.autpae.repository.base;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.tz.puglia.autpae.dbMapping.ParticelleCatastali;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ParticelleCatastaliPK;
import it.eng.tz.puglia.autpae.rowmapper.ParticelleCatastaliRowMapper;
import it.eng.tz.puglia.autpae.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Base Repository for table autpae.particelle_catastali
 */

public class ParticelleCatastaliBaseRepository extends GenericCrudDao<ParticelleCatastaliDTO, ParticelleCatastaliSearch, ParticelleCatastaliPK> {

	private static final Logger log = LoggerFactory.getLogger(ParticelleCatastaliBaseRepository.class);
	private final ParticelleCatastaliRowMapper rowMapper = new ParticelleCatastaliRowMapper();
	
	
	/**
	 * insert method
	 */
	@Override
	public ParticelleCatastaliPK insert(ParticelleCatastaliDTO entity) {
		final String sql = new StringBuilder("insert into ")
				.append(ParticelleCatastali.getTableName())
				.append("(\"pratica_id\"").append(",\"comune_id\"").append(",\"id\"").append(",\"livello\"")
				.append(",\"sezione\"").append(",\"foglio\"").append(",\"particella\"").append(",\"sub\"")
				.append(",\"cod_cat\"")
				.append(",")
				.append(ParticelleCatastali.oid.columnName())
				.append(",")
				.append(ParticelleCatastali.note.columnName())
				.append(",")
				.append(ParticelleCatastali.descr_sezione.columnName())
				.append(") values ")
				.append("(?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?")
				.append(",?").append(",?")
				.append(",?").append(",?")
				.append(",?")
				.append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		parameters.add(entity.getId());
		parameters.add(entity.getLivello());
		parameters.add(entity.getSezione());
		parameters.add(entity.getFoglio());
		parameters.add(entity.getParticella());
		parameters.add(entity.getSub());
		parameters.add(entity.getCodCat());
		parameters.add(entity.getOid());
		parameters.add(entity.getNote());
		parameters.add(entity.getDescrSezione());
		super.update(sql, parameters);
		return new ParticelleCatastaliPK(entity.getPraticaId(), entity.getComuneId(), entity.getId());
	}

	@Override
	public List<ParticelleCatastaliDTO> select() throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(ParticelleCatastaliSearch filter) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public ParticelleCatastaliDTO find(ParticelleCatastaliPK pk) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int update(ParticelleCatastaliDTO entity) throws Exception {
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public int delete(ParticelleCatastaliSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" delete from ", ParticelleCatastali.getTableName());
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.update(sql, filter.getSqlParameters());
	}

	@Override
	public PaginatedList<ParticelleCatastaliDTO> search(ParticelleCatastaliSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select * "
												, " from ", ParticelleCatastali.getTableName()
												);
		sql = filter.getSqlWhereClause(new StringBuilder(sql)).toString();
		sql = filter.getSqlOrderByClause(new StringBuilder(sql)).toString();
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, filter.getSqlParameters());
		return super.paginatedList(sql, filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

}