package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.UlterioriContestiPaesaggisticiRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioriContestiPaesaggisticiSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.ulteriori_contesti_paesaggistici
 */
@Repository
public class UlterioriContestiPaesaggisticiRepository
		extends GenericCrudDao<UlterioriContestiPaesaggisticiDTO, UlterioriContestiPaesaggisticiSearch>
{

	private final UlterioriContestiPaesaggisticiRowMapper rowMapper = new UlterioriContestiPaesaggisticiRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select distinct ").append(" \"codice\"").append(",\"label\"").append(",\"art1\"")
			.append(",\"definizione\"").append(",\"disposizioni\"").append(",\"art2\"").append(",\"type\"")
			.append(",\"hasText\"").append(",\"data_inizio_val\"").append(",\"data_fine_val\"")
			.append(",\"gruppo\"")
			.append(",\"sezione\"")
			.append(",\"order\"")
			.append(" from \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\"")
			.append(" join \"presentazione_istanza\".\"ucp_tipo_procedimento\"")
			.append(" on \"codice\"=\"codice_ucp\"").toString();

	@Override
	public List<UlterioriContestiPaesaggisticiDTO> select()
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
				.append(" from \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public UlterioriContestiPaesaggisticiDTO find(UlterioriContestiPaesaggisticiDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getCodice());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<UlterioriContestiPaesaggisticiDTO> search(UlterioriContestiPaesaggisticiSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sql = generateQuery(search, parameters);
		return super.paginatedList(sql, parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	/**
	 * search method
	 */
	public List<UlterioriContestiPaesaggisticiDTO> searchForList(UlterioriContestiPaesaggisticiSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sql = generateQuery(search, parameters);
		return super.queryForList(sql, this.rowMapper, parameters);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(UlterioriContestiPaesaggisticiDTO entity)
	{
		final String sql = new StringBuilder(
				"insert into \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\"").append("(\"codice\"")
						.append(",\"label\"").append(",\"art1\"").append(",\"definizione\"").append(",\"disposizioni\"")
						.append(",\"art2\"").append(",\"type\"").append(",\"hasText\"").append(",\"gruppo\"").append(") values ")
						.append("(?").append(",?").append(",?").append(",?").append(",?").append(",?")
						.append(",?").append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		parameters.add(entity.getLabel());
		parameters.add(entity.getArt1());
		parameters.add(entity.getDefinizione());
		parameters.add(entity.getDisposizioni());
		parameters.add(entity.getArt2());
		parameters.add(entity.getType());
		parameters.add(entity.getHastext());
		parameters.add(entity.getDataInizioVal());
		parameters.add(entity.getDataFineVal());
		parameters.add(entity.getGruppo());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(UlterioriContestiPaesaggisticiDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\"")
				.append(" set \"label\" = ?").append(", \"art1\" = ?").append(", \"definizione\" = ?")
				.append(", \"disposizioni\" = ?").append(", \"art2\" = ?").append(", \"type\" = ?")
				.append(", \"hasText\" = ?").append(", \"data_inizio_val\" = ?").append(", \"data_fine_val\" = ?")
				.append(", \"gruppo\" = ?").append(" where \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getLabel());
		parameters.add(entity.getArt1());
		parameters.add(entity.getDefinizione());
		parameters.add(entity.getDisposizioni());
		parameters.add(entity.getArt2());
		parameters.add(entity.getType());
		parameters.add(entity.getHastext());
		parameters.add(entity.getDataInizioVal());
		parameters.add(entity.getDataFineVal());
		parameters.add(entity.getGruppo());
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(UlterioriContestiPaesaggisticiDTO entity)
	{
		final String sql = new StringBuilder(
				"delete from \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\"")
						.append(" where \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}

	private String generateQuery(UlterioriContestiPaesaggisticiSearch search, List<Object> parameters)
	{
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if(search.getIdTipoProcedimento() != null)
		{
			sql.append(sep).append("\"id_tipo_procedimento\" = ?");
			parameters.add(search.getIdTipoProcedimento());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodice()))
		{
			sql.append(sep).append("lower(\"codice\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getCodice()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getLabel()))
		{
			sql.append(sep).append("lower(\"label\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getLabel()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getArt1()))
		{
			sql.append(sep).append("lower(\"art1\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getArt1()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDefinizione()))
		{
			sql.append(sep).append("lower(\"definizione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDefinizione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDisposizioni()))
		{
			sql.append(sep).append("lower(\"disposizioni\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDisposizioni()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getArt2()))
		{
			sql.append(sep).append("lower(\"art2\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getArt2()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getType()))
		{
			sql.append(sep).append("lower(\"type\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getType()));
			sep = " and ";
		}
		if (search.getHastext() != null)
		{
			sql.append(sep).append("\"hasText\" = ?");
			parameters.add(search.getHastext());
			sep = " and ";
		}
		if (search.getDataInizioVal() != null)
		{
			sql.append(sep).append("\"data_inizio_val\" >= ?");
			parameters.add(search.getDataInizioVal());
			sep = " and ";
		}
		if (search.getDataFineVal() != null)
		{
			sql.append(sep).append("\"data_fine_val\" <= ?");
			parameters.add(search.getDataFineVal());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getGruppo()))
		{
			sql.append(sep).append("lower(\"gruppo\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getGruppo().toLowerCase()));
			sep = " and ";
		}
		if(StringUtil.isNotEmpty(search.getSezione()))
		{
			sql.append(sep).append("lower(\"sezione\") = ?");
			parameters.add(search.getSezione().toLowerCase());
			sep = " and ";
		}
		if(search.getIsnowValid() != null && search.getDataFineVal() != null)
		{
			sql.append(sep).append("\"data_fine_val\" is not null");
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "codice":
				sql.append(" order by \"codice\" ").append(sortType);
			case "label":
				sql.append(" order by \"label\" ").append(sortType);
			case "art1":
				sql.append(" order by \"art1\" ").append(sortType);
			case "definizione":
				sql.append(" order by \"definizione\" ").append(sortType);
			case "disposizioni":
				sql.append(" order by \"disposizioni\" ").append(sortType);
			case "art2":
				sql.append(" order by \"art2\" ").append(sortType);
			case "type":
				sql.append(" order by \"type\" ").append(sortType);
			case "hastext":
				sql.append(" order by \"hasText\" ").append(sortType);
			case "dataInizioVal":
				sql.append(" order by \"data_inizio_val\" ").append(sortType);
			case "dataFineVal":
				sql.append(" order by \"data_fine_val\" ").append(sortType);
			case "gruppo":
				sql.append(" order by \"gruppo\" ").append(sortType);
				break;
			default:
				break;
			}
		}else {
			sql.append(" order by \"order\" ");
		}
		
		return sql.toString();
	}
	
	public List<UlterioriContestiPaesaggisticiDTO> selectAllWhereCodiceIn(List<String> listaCodici) {
		if(listaCodici==null || listaCodici.isEmpty()) {
			return new ArrayList<>();
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select * ")
		   .append("from \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\" ")
		   .append("where \"codice\" in (:lista)");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("lista", listaCodici);
		return super.namedQueryForList(sql.toString(), this.rowMapper, parameters);
	}
	
}
