package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParticelleCatastaliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ParticelleCatastaliRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.particelle_catastali
 */
@Repository
public class ParticelleCatastaliRepository extends GenericCrudDao<ParticelleCatastaliDTO, ParticelleCatastaliSearch> {

	private final ParticelleCatastaliRowMapper rowMapper = new ParticelleCatastaliRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"pratica_id\"").append(",\"comune_id\"")
			.append(",\"id\"").append(",\"livello\"").append(",\"sezione\"").append(",\"foglio\"")
			.append(",\"particella\"").append(",\"sub\"")
			.append(",\"cod_cat\"")
			.append(",\"oid\"")
			.append(",\"note\"")
			.append(",\"descr_sezione\"")
			.append(" from \"presentazione_istanza\".\"particelle_catastali\"").toString();

	@Override
	public List<ParticelleCatastaliDTO> select() {
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}
	
	
	public List<ParticelleCatastaliDTO> select(UUID praticaId,Long comuneId) {
		return this.select(praticaId, comuneId, false);
	}
	
	public List<ParticelleCatastaliDTO> select(UUID praticaId,Long comuneId,boolean txWrite) {
		final String sql = new StringBuilder(selectAll).append(" where \"pratica_id\" = ?")
				.append(" and \"comune_id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		parameters.add(comuneId);
		if(txWrite) {
			return super.queryForList(sql, this.rowMapper,parameters);
		}else {
			return super.queryForListTxRead(sql, this.rowMapper,parameters);	
		}
	}

	/**
	 * count all method
	 */
	@Override
	public long count() {
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"particelle_catastali\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public ParticelleCatastaliDTO find(ParticelleCatastaliDTO pk) {
		final String sql = new StringBuilder(selectAll).append(" where \"pratica_id\" = ?")
				.append(" and \"comune_id\" = ?").append(" and \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getPraticaId());
		parameters.add(pk.getComuneId());
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<ParticelleCatastaliDTO> search(ParticelleCatastaliSearch search) {
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getPraticaId())) {
			sql.append(sep).append("lower(\"pratica_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPraticaId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComuneId())) {
			sql.append(sep).append("lower(\"comune_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComuneId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getId())) {
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getLivello())) {
			sql.append(sep).append("lower(\"livello\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getLivello()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSezione())) {
			sql.append(sep).append("lower(\"sezione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSezione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getFoglio())) {
			sql.append(sep).append("lower(\"foglio\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getFoglio()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getParticella())) {
			sql.append(sep).append("lower(\"particella\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getParticella()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSub())) {
			sql.append(sep).append("lower(\"sub\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSub()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodCat())) {
			sql.append(sep).append("lower(\"cod_cat\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodCat()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy())) {
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy()) {
			case "praticaId":
				sql.append(" sort by \"pratica_id\" ").append(sortType);
			case "comuneId":
				sql.append(" sort by \"comune_id\" ").append(sortType);
			case "id":
				sql.append(" sort by \"id\" ").append(sortType);
			case "livello":
				sql.append(" sort by \"livello\" ").append(sortType);
			case "sezione":
				sql.append(" sort by \"sezione\" ").append(sortType);
			case "foglio":
				sql.append(" sort by \"foglio\" ").append(sortType);
			case "particella":
				sql.append(" sort by \"particella\" ").append(sortType);
			case "sub":
				sql.append(" sort by \"sub\" ").append(sortType);
			case "codCat":
				sql.append(" sort by \"cod_cat\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(ParticelleCatastaliDTO entity) {
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"particelle_catastali\"")
				.append("(\"pratica_id\"").append(",\"comune_id\"").append(",\"id\"").append(",\"livello\"")
				.append(",\"sezione\"").append(",\"foglio\"").append(",\"particella\"").append(",\"sub\"")
				.append(",\"cod_cat\"")
				.append(",\"oid\"")
				.append(",\"note\"")
				.append(",\"descr_sezione\"")
				.append(") values ").append("(?").append(",?").append(",?").append(",?")
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
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(ParticelleCatastaliDTO entity) {
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"particelle_catastali\"")
				.append(" set \"livello\" = ?").append(", \"sezione\" = ?").append(", \"foglio\" = ?")
				.append(", \"particella\" = ?").append(", \"sub\" = ?").append(", \"cod_cat\" = ?")
				.append(", \"oid\" = ?")
				.append(", \"note\" = ?")
				.append(", \"descr_sezione\" = ?")
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").append(" and \"id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getLivello());
		parameters.add(entity.getSezione());
		parameters.add(entity.getFoglio());
		parameters.add(entity.getParticella());
		parameters.add(entity.getSub());
		parameters.add(entity.getCodCat());
		parameters.add(entity.getOid());
		parameters.add(entity.getNote());
		parameters.add(entity.getDescrSezione());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(ParticelleCatastaliDTO entity) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"particelle_catastali\"")
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").append(" and \"id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	public int deleteByKeyLoc(UUID id, long comuneId) {
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"particelle_catastali\"")
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		parameters.add(comuneId);
		return super.update(sql, parameters);
	}

}
