package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipiEQualificazioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipiEQualificazioniRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipiEQualificazioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tipi_e_qualificazioni
 */
@Repository
public class TipiEQualificazioniRepository extends GenericCrudDao<TipiEQualificazioniDTO, TipiEQualificazioniSearch>
{
	private final Logger logger = LoggerFactory.getLogger(TipiEQualificazioniRepository.class);
	private final TipiEQualificazioniRowMapper rowMapper = new TipiEQualificazioniRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"codice\"").append(",\"stile\"")
			.append(",\"raggruppamento\"").append(",\"label\"").append(",\"ordine\"").append(",\"key\"")
			.append(",\"dependency\"").append(",\"hasText\"").append(",\"sezione\"")
			.append(" from \"presentazione_istanza\".\"tipi_e_qualificazioni\"").toString();

	@Override
	public List<TipiEQualificazioniDTO> select()
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
				.append(" from \"presentazione_istanza\".\"tipi_e_qualificazioni\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public TipiEQualificazioniDTO find(TipiEQualificazioniDTO pk)
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
	public PaginatedList<TipiEQualificazioniDTO> search(TipiEQualificazioniSearch search)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = generateSqlSearchQuery(search, parameters);
		logger.info("Eseguo la query: {}, con i seguenti parametri: {}", sql.toString(), parameters);
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	public List<TipiEQualificazioniDTO> search(Integer tipoProcedimento) throws Exception
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		TipiEQualificazioniSearch search = new TipiEQualificazioniSearch();
		search.setTipoProcedimento(tipoProcedimento);
		String sql = generateSqlSearchQuery(search, parameters);
		logger.info("Eseguo la query: {}, con i seguenti parametri: {}", sql.toString(), parameters);
		return super.namedQueryForList(sql.toString(), this.rowMapper, parameters);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(TipiEQualificazioniDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tipi_e_qualificazioni\"")
				.append("(\"codice\"").append(",\"stile\"").append(",\"raggruppamento\"").append(",\"label\"")
				.append(",\"ordine\"").append(",\"key\"").append(",\"dependency\"").append(",\"hasText\"")
				.append(") values ").append("(?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		parameters.add(entity.getStile());
		parameters.add(entity.getRaggruppamento());
		parameters.add(entity.getLabel());
		parameters.add(entity.getOrdine());
		parameters.add(entity.getKey());
		parameters.add(entity.getDependency());
		parameters.add(entity.getHastext());
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(TipiEQualificazioniDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"tipi_e_qualificazioni\"")
				.append(" set \"stile\" = ?").append(", \"raggruppamento\" = ?").append(", \"label\" = ?")
				.append(", \"ordine\" = ?").append(", \"key\" = ?").append(", \"dependency\" = ?")
				.append(", \"hasText\" = ?").append(" where \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getStile());
		parameters.add(entity.getRaggruppamento());
		parameters.add(entity.getLabel());
		parameters.add(entity.getOrdine());
		parameters.add(entity.getKey());
		parameters.add(entity.getDependency());
		parameters.add(entity.getHastext());
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(TipiEQualificazioniDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"tipi_e_qualificazioni\"")
				.append(" where \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}
	
	private String generateSqlSearchQuery(TipiEQualificazioniSearch search, Map<String, Object> parameters)
	{
		//final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (StringUtil.isNotEmpty(search.getCodice()))
		{
//					sql.append(sep).append("lower(\"codice\"::text) like ?");
//					parameters.add(StringUtil.convertLike(search.getCodice()));
			sql.append(sep).append("lower(\"codice\"::text) like :codice");
			parameters.put("codice", StringUtil.convertLike(search.getCodice()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getStile()))
		{
//					sql.append(sep).append("lower(\"stile\"::text) like ?");
//					parameters.add(StringUtil.convertLike(search.getStile()));
			sql.append(sep).append("lower(\"stile\"::text) like :stile");
			parameters.put("stile", StringUtil.convertLike(search.getStile()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getRaggruppamento()))
		{
//					sql.append(sep).append("lower(\"raggruppamento\"::text) like ?");
//					parameters.add(StringUtil.convertLike(search.getRaggruppamento()));
			sql.append(sep).append("lower(\"raggruppamento\"::text) like :raggruppamento");
			parameters.put("raggruppamento", StringUtil.convertLike(search.getRaggruppamento()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getLabel()))
		{
//					sql.append(sep).append("lower(\"label\"::text) like ?");
//					parameters.add(StringUtil.convertLike(search.getLabel()));
			sql.append(sep).append("lower(\"label\"::text) like :label");
			parameters.put("label", StringUtil.convertLike(search.getLabel()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSezione()))
		{
//					sql.append(sep).append("lower(\"label\"::text) like ?");
//					parameters.add(StringUtil.convertLike(search.getLabel()));
			sql.append(sep).append("\"sezione\" = :sezione");
			parameters.put("sezione", search.getSezione());
			sep = " and ";
		}
		if(search.getTipoProcedimento() != null)
		{
			sql.append(sep).append("(select count(*) from ")
						   .append("\"presentazione_istanza\".\"procedimento_qualificazioni\" ")
						   .append("where \"id_tipo_procedimento\"=:tipoProcedimento and \"codice_tipi_e_qualificazioni\"=\"codice\") > 0");
			parameters.put("tipoProcedimento", search.getTipoProcedimento());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "codice":
				sql.append(" sort by \"codice\" ").append(sortType);
			case "stile":
				sql.append(" sort by \"stile\" ").append(sortType);
			case "raggruppamento":
				sql.append(" sort by \"raggruppamento\" ").append(sortType);
			case "label":
				sql.append(" sort by \"label\" ").append(sortType);
			case "ordine":
				sql.append(" sort by \"ordine\" ").append(sortType);
			case "key":
				sql.append(" sort by \"key\" ").append(sortType);
			case "dependency":
				sql.append(" sort by \"dependency\" ").append(sortType);
			case "hastext":
				sql.append(" sort by \"hasText\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

}
