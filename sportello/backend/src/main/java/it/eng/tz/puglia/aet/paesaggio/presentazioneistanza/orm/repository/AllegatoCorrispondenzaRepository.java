package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AllegatoCorrispondenzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.allegato_corrispondenza
 */
@Repository
public class AllegatoCorrispondenzaRepository
		extends GenericCrudDao<AllegatoCorrispondenzaDTO, AllegatoCorrispondenzaSearch>
{

	private final AllegatoCorrispondenzaRowMapper rowMapper = new AllegatoCorrispondenzaRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id_allegato\"").append(",\"id_corrispondenza\"")
			.append(" from \"presentazione_istanza\".\"allegato_corrispondenza\"").toString();

	@Override
	public List<AllegatoCorrispondenzaDTO> select()
	{
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count()
	{
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"allegato_corrispondenza\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public AllegatoCorrispondenzaDTO find(AllegatoCorrispondenzaDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id_allegato\" = ?")
				.append(" and \"id_corrispondenza\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getIdAllegato());
		parameters.add(pk.getIdCorrispondenza());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<AllegatoCorrispondenzaDTO> search(AllegatoCorrispondenzaSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getIdAllegato()))
		{
			sql.append(sep).append("lower(\"id_allegato\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdAllegato()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdCorrispondenza()))
		{
			sql.append(sep).append("lower(\"id_corrispondenza\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdCorrispondenza()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "idAllegato":
				sql.append(" order by \"id_allegato\" ").append(sortType);
				break;
			case "idCorrispondenza":
				sql.append(" order by \"id_corrispondenza\" ").append(sortType);
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
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public long insert(AllegatoCorrispondenzaDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"allegato_corrispondenza\"")
				.append("(\"id_allegato\"").append(",\"id_corrispondenza\"").append(") values ").append("(?")
				.append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdAllegato());
		parameters.add(entity.getIdCorrispondenza());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(AllegatoCorrispondenzaDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"allegato_corrispondenza\"")
				.append(" where \"id_allegato\" = ?").append(" and \"id_corrispondenza\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdAllegato());
		parameters.add(entity.getIdCorrispondenza());
		return super.update(sql, parameters);
	}
	
	public List<UUID> findIds(Long idCorrispondenza) throws Exception
	{
		final String sql = "select \"id\" from \"presentazione_istanza\".\"allegato_corrispondenza\" where id_corrispondenza = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idCorrispondenza);
		return queryForList(sql, UUID.class, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(AllegatoCorrispondenzaDTO entity)
	{
		String sql = "";
		final StringBuilder sqlBuilder = new StringBuilder(
				"delete from \"presentazione_istanza\".\"allegato_corrispondenza\"");
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		if (entity.getIdAllegato() != null)
		{
			sqlBuilder.append(sep).append("\"id_allegato\" = ?");
			parameters.add(entity.getIdAllegato());
			sep = " and ";
		}
		if (entity.getIdCorrispondenza() != null)
		{
			sqlBuilder.append(sep).append("\"id_corrispondenza\" = ?");
			parameters.add(entity.getIdCorrispondenza());
			sep = " and ";
		}
		sql = sqlBuilder.toString();
		return super.update(sql, parameters);
	}

}
