package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipoInterventoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoInterventoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tipo_intervento
 */
@Repository
public class TipoInterventoRepository extends GenericCrudDao<TipoInterventoDTO, TipoInterventoSearch>
{

	private final TipoInterventoRowMapper rowMapper = new TipoInterventoRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"codice\"").append(",\"id_pratica\"")
			.append(",\"artt\"").append(",\"data_approvazione\"").append(",\"con\"")
			.append(" from \"presentazione_istanza\".\"tipo_intervento\"").toString();

	@Override
	public List<TipoInterventoDTO> select()
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
				.append(" from \"presentazione_istanza\".\"tipo_intervento\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public TipoInterventoDTO find(TipoInterventoDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"codice\" = ?")
				.append(" and \"id_pratica\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getCodice());
		parameters.add(pk.getIdPratica());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public TipoInterventoDTO find(UUID idPratica)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id_pratica\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		List<TipoInterventoDTO> temp = super.queryForList(sql, this.rowMapper, parameters);
		return temp != null && !temp.isEmpty() ? temp.get(0) : null;
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<TipoInterventoDTO> search(TipoInterventoSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getCodice()))
		{
			sql.append(sep).append("lower(\"codice\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodice()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdPratica()))
		{
			sql.append(sep).append("lower(\"id_pratica\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdPratica()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getArtt()))
		{
			sql.append(sep).append("lower(\"artt\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getArtt()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataApprovazione()))
		{
			sql.append(sep).append("lower(\"data_approvazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataApprovazione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCon()))
		{
			sql.append(sep).append("lower(\"con\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCon()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "codice":
				sql.append(" sort by \"codice\" ").append(sortType);
			case "idPratica":
				sql.append(" sort by \"id_pratica\" ").append(sortType);
			case "artt":
				sql.append(" sort by \"artt\" ").append(sortType);
			case "dataApprovazione":
				sql.append(" sort by \"data_approvazione\" ").append(sortType);
			case "con":
				sql.append(" sort by \"con\" ").append(sortType);
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
	public long insert(TipoInterventoDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tipo_intervento\"")
				.append("(\"codice\"").append(",\"id_pratica\"").append(",\"artt\"").append(",\"data_approvazione\"")
				.append(",\"con\"").append(") values ").append("(?").append(",?").append(",?").append(",?").append(",?")
				.append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getArtt());
		parameters.add(entity.getDataApprovazione());
		parameters.add(entity.getCon());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(TipoInterventoDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"tipo_intervento\"")
				.append(" set \"artt\" = ?").append(", \"data_approvazione\" = ?").append(", \"con\" = ?")
				.append(" where \"codice\" = ?").append(" and \"id_pratica\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getArtt());
		parameters.add(entity.getDataApprovazione());
		parameters.add(entity.getCon());
		parameters.add(entity.getCodice());
		parameters.add(entity.getIdPratica());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(TipoInterventoDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"tipo_intervento\"")
				.append(" where \"codice\" = ?").append(" and \"id_pratica\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		parameters.add(entity.getIdPratica());
		return super.update(sql, parameters);
	}

	/**
	 * delete by id pratica
	 */
	public int delete(UUID idPratica)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"tipo_intervento\"")
				.append(" where \"id_pratica\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.update(sql, parameters);
	}

}
