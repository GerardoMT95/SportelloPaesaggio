package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ProvvedimentoFinaleRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ProvvedimentoFinaleSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.provvedimento_finale
 */
@Repository
public class ProvvedimentoFinaleRepository extends GenericCrudDao<ProvvedimentoFinaleDTO, ProvvedimentoFinaleSearch>
{

	private final ProvvedimentoFinaleRowMapper rowMapper = new ProvvedimentoFinaleRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"id_pratica\"")
			.append(",\"numero_provvedimento\"").append(",\"data_rilascio_autorizzazione\"")
			.append(",\"esito_provvedimento\"").append(",\"rup\"").append(",\"draft\"").append(",\"id_corrispondenza\"")
			.append(" from \"presentazione_istanza\".\"provvedimento_finale\"").toString();
	
	private String generateWhereClause(String baseSql, Map<String, Object> parameters, ProvvedimentoFinaleSearch search)
	{
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(baseSql);
		if (search.getId() != null)
		{
			sql.append(sep).append("\"id\" = :id");
			parameters.put("id", search.getId());
			sep = " and ";
		}
		if (search.getIdPratica() != null)
		{
			sql.append(sep).append("\"id_pratica\" = :id_pratica");
			parameters.put("id_pratica", search.getIdPratica());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNumeroProvvedimento()))
		{
			sql.append(sep).append("lower(\"numero_provvedimento\"::text) like :numero_provvedimento");
			parameters.put("numero_provvedimento", StringUtil.convertLike(search.getNumeroProvvedimento()).toLowerCase());
			sep = " and ";
		}
		if (search.getDataRilascioAutorizzazione() != null)
		{
			sql.append(sep).append("\"data_rilascio_autorizzazione\" = :data_rilascio_aut");
			parameters.put("data_rilascio_aut", search.getDataRilascioAutorizzazione());
			sep = " and ";
		}
		if (search.getEsitoProvvedimento() != null)
		{
			sql.append(sep).append("\"esito_provvedimento\" = :esito");
			parameters.put("esito", search.getEsitoProvvedimento().name());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getRup()))
		{
			sql.append(sep).append("\"rup\" = :rup");
			parameters.put("rup", search.getRup());
			sep = " and ";
		}
		if (search.getDraft())
		{
			sql.append(sep).append("\"draft\" = :draft");
			parameters.put("draft", search.getDraft());
			sep = " and ";
		}
		if (search.getIdCorrispondenza() != null)
		{
			sql.append(sep).append("\"id_corrispondenza\" = :id_corrispondenza");
			parameters.put("id_corrispondenza", search.getIdCorrispondenza());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "id":
				sql.append(" order by \"id\" ").append(sortType);
				break;
			case "idPratica":
				sql.append(" order by \"id_pratica\" ").append(sortType);
				break;
			case "numeroProvvedimento":
				sql.append(" order by \"numero_provvedimento\" ").append(sortType);
				break;
			case "dataRilascioAutorizzazione":
				sql.append(" order by \"data_rilascio_autorizzazione\" ").append(sortType);
				break;
			case "esitoProvvedimento":
				sql.append(" order by \"esito_provvedimento\" ").append(sortType);
				break;
			case "rup":
				sql.append(" order by \"rup\" ").append(sortType);
				break;
			case "draft":
				sql.append(" order by \"draft\" ").append(sortType);
				break;
			case "idCorrispondenza":
				sql.append(" order by \"id_corrispondenza\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

	@Override
	public List<ProvvedimentoFinaleDTO> select()
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
				.append(" from \"presentazione_istanza\".\"provvedimento_finale\"").toString();
		logger.info("Eseguo la query {}", sql);
		return super.queryForObjectTxRead(sql, Long.class);
	}
	
	public long count(ProvvedimentoFinaleSearch search)
	{
		String sql = StringUtil.concateneString("select count(*) ",
												"from \"presentazione_istanza\".\"provvedimento_finale\"");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		sql = generateWhereClause(sql, parameters, search);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public ProvvedimentoFinaleDTO find(ProvvedimentoFinaleDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = :id").toString();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", pk.getId());
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, this.rowMapper);
	}
	
	public ProvvedimentoFinaleDTO find(UUID idPratca) throws Exception
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id_pratica\" = :id_pratica").toString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", idPratca);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, rowMapper);
	}
	
	public ProvvedimentoFinaleDTO find(Long id) throws Exception
	{
		ProvvedimentoFinaleDTO pk = new ProvvedimentoFinaleDTO();
		pk.setId(id);
		return find(id);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<ProvvedimentoFinaleDTO> search(ProvvedimentoFinaleSearch search)
	{
		final Map<String, Object> parameters = new HashMap<>();
		final String sql = generateWhereClause(selectAll, parameters, search);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(ProvvedimentoFinaleDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"provvedimento_finale\"")
				.append("(\"id_pratica\"").append(",\"numero_provvedimento\"").append(",\"rup\"")
				.append(",\"data_rilascio_autorizzazione\"").append(",\"esito_provvedimento\"")
				.append(",\"draft\"").append(",\"id_corrispondenza\"").append(") values ").append("(:id_pratica")
				.append(",:numero_provvedimento").append(",:rup").append(",:data_ra").append(",:esito")
				.append(",:draft").append(",:id_corrispondenza").append(") returning \"id\"").toString();
		String esito = entity.getEsitoProvvedimento() != null ? entity.getEsitoProvvedimento().name() : null;
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id_pratica", entity.getIdPratica());
		parameters.put("numero_provvedimento", entity.getNumeroProvvedimento());
		parameters.put("data_ra", entity.getDataRilascioAutorizzazione());
		parameters.put("esito", esito);
		parameters.put("rup", entity.getRup());
		parameters.put("draft", entity.getDraft());
		parameters.put("id_corrispondenza", entity.getIdCorrispondenza());
		return namedJdbcTemplateWrite.queryForObject(sql, parameters, Long.class);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(ProvvedimentoFinaleDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"provvedimento_finale\" ")
				.append("set \"numero_provvedimento\" = :numero_provvedimento")
				.append(", \"data_rilascio_autorizzazione\" = :data_ra").append(", \"rup\" = :rup")
				.append(", \"esito_provvedimento\" = :esito").append(", \"draft\" = :draft")
				.append(", \"id_corrispondenza\" = :id_corrispondenza").append(" where \"id\" = :id").toString();
		String esito = entity.getEsitoProvvedimento() != null ? entity.getEsitoProvvedimento().name() : null;
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", entity.getId());
		parameters.put("numero_provvedimento", entity.getNumeroProvvedimento());
		parameters.put("data_ra", entity.getDataRilascioAutorizzazione());
		parameters.put("esito", esito);
		parameters.put("rup", entity.getRup());
		parameters.put("draft", entity.getDraft());
		parameters.put("id_corrispondenza", entity.getIdCorrispondenza());
		return namedUpdate(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(ProvvedimentoFinaleDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"provvedimento_finale\"")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

}
