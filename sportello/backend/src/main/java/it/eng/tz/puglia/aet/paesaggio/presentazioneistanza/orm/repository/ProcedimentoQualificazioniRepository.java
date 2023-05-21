package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProcedimentoQualificazioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ProcedimentoQualificazioniRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ProcedimentoQualificazioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.procedimento_qualificazioni
 */
@Repository
public class ProcedimentoQualificazioniRepository extends GenericCrudDao<ProcedimentoQualificazioniDTO, ProcedimentoQualificazioniSearch>
{

	private final ProcedimentoQualificazioniRowMapper rowMapper = new ProcedimentoQualificazioniRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id_tipo_procedimento\"")
														.append(",\"codice_tipi_e_qualificazioni\"")
														.append(" from \"presentazione_istanza\".\"procedimento_qualificazioni\"").toString();

	@Override
	public List<ProcedimentoQualificazioniDTO> select()
	{
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count()
	{
		final String sql = new StringBuilder("select count(*)").append(" from \"presentazione_istanza\".\"procedimento_qualificazioni\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public ProcedimentoQualificazioniDTO find(ProcedimentoQualificazioniDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id_tipo_procedimento\" = ?")
				.append(" and \"codice_tipi_e_qualificazioni\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getIdTipoProcedimento());
		parameters.add(pk.getCodiceTipiEQualificazioni());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<ProcedimentoQualificazioniDTO> search(ProcedimentoQualificazioniSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getIdTipoProcedimento()))
		{
			sql.append(sep).append("lower(\"id_tipo_procedimento\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdTipoProcedimento()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceTipiEQualificazioni()))
		{
			sql.append(sep).append("lower(\"codice_tipi_e_qualificazioni\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodiceTipiEQualificazioni()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "idTipoProcedimento":
				sql.append(" sort by \"id_tipo_procedimento\" ").append(sortType);
			case "codiceTipiEQualificazioni":
				sql.append(" sort by \"codice_tipi_e_qualificazioni\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(ProcedimentoQualificazioniDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"procedimento_qualificazioni\"")
				.append("(\"id_tipo_procedimento\"").append(",\"codice_tipi_e_qualificazioni\"").append(") values ")
				.append("(?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdTipoProcedimento());
		parameters.add(entity.getCodiceTipiEQualificazioni());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(ProcedimentoQualificazioniDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"procedimento_qualificazioni\"")
				.append(" where \"id_tipo_procedimento\" = ?").append(" and \"codice_tipi_e_qualificazioni\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdTipoProcedimento());
		parameters.add(entity.getCodiceTipiEQualificazioni());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(ProcedimentoQualificazioniDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"procedimento_qualificazioni\"")
				.append(" where \"id_tipo_procedimento\" = ?").append(" and \"codice_tipi_e_qualificazioni\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdTipoProcedimento());
		parameters.add(entity.getCodiceTipiEQualificazioni());
		return super.update(sql, parameters);
	}

}
