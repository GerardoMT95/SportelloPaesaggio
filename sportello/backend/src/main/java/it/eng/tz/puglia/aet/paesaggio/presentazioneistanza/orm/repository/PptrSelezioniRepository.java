package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PptrSelezioniRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PptrSelezioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pptr_selezioni
 */
@Repository
public class PptrSelezioniRepository extends GenericCrudDao<PptrSelezioniDTO, PptrSelezioniSearch>
{

	private final PptrSelezioniRowMapper rowMapper = new PptrSelezioniRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id_pratica\"").append(",\"codice\"")
			.append(",\"specifica\"").append(",\"sezione\"").append(" from \"presentazione_istanza\".\"pptr_selezioni\"").toString();

	@Override
	public List<PptrSelezioniDTO> select()
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
				.append(" from \"presentazione_istanza\".\"pptr_selezioni\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public PptrSelezioniDTO find(PptrSelezioniDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id_pratica\" = ?")
				.append(" and \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getIdPratica());
		parameters.add(pk.getCodice());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<PptrSelezioniDTO> search(PptrSelezioniSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		if (search.getIdPratica() != null)
		{
			sql.append(sep).append("\"id_pratica\" = ?");
			parameters.add(search.getIdPratica());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodice()))
		{
			sql.append(sep).append("\"codice\" = ?");
			parameters.add(search.getCodice());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSpecifica()))
		{
			sql.append(sep).append("lower(\"specifica\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getSpecifica().toLowerCase()));
			sep = " and ";
		}
		if(StringUtil.isNotEmpty(search.getSezione()))
		{
			sql.append(sep).append("lower(\"sezione\") = ?");
			parameters.add(search.getSezione().toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "idPratica":
				sql.append(" sort by \"id_pratica\" ").append(sortType);
			case "codice":
				sql.append(" sort by \"codice\" ").append(sortType);
			case "specifica":
				sql.append(" sort by \"specifica\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	public List<PptrSelezioniDTO> searchByIdPratica(UUID idPratica)
	{
		final List<Object> parameters = new ArrayList<Object>();
		final StringBuilder sql = new StringBuilder(selectAll).append(" where \"id_pratica\" = ?");
		parameters.add(idPratica);
		return queryForList(sql.toString(), this.rowMapper, parameters);
	}
	
	public List<PptrSelezioniDTO> searchByIdPraticaSezione(UUID idPratica,String sezione,boolean txWrite)
	{
		final List<Object> parameters = new ArrayList<Object>();
		final StringBuilder sql = new StringBuilder(selectAll).append(" where \"id_pratica\" = ? and lower(\"sezione\")= ?");
		parameters.add(idPratica);
		parameters.add(sezione);
		if(txWrite) {
			return queryForList(sql.toString(), this.rowMapper, parameters);
		}else {
			return queryForListTxRead(sql.toString(), this.rowMapper, parameters);	
		}
		
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(PptrSelezioniDTO entity)
	{
		final Map<String, Object> parameters = new HashMap<>();
		final String subquery = "(select \"sezione\" from \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\" where \"codice\" = :codice)";
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"pptr_selezioni\"")
				.append("(\"id_pratica\"").append(",\"codice\"").append(",\"specifica\"").append(",\"sezione\"")
				.append(") values ")
				.append("(:id_pratica").append(",:codice").append(",:specifica").append(",").append(subquery)
				.append(")").toString();
		parameters.put("id_pratica", entity.getIdPratica());
		parameters.put("codice", entity.getCodice());
		parameters.put("specifica", entity.getSpecifica());
		return namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public long insert(List<PptrSelezioniDTO> entity) throws Exception
	{
		long counter = 0;
		for(PptrSelezioniDTO e: entity)
			counter += insert(e);
		return counter;
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(PptrSelezioniDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"pptr_selezioni\"")
				.append(" set \"specifica\" = ?").append(" where \"id_pratica\" = ?").append(" and \"codice\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getSpecifica());
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(PptrSelezioniDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pptr_selezioni\"")
				.append(" where \"id_pratica\" = ?").append(" and \"codice\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getCodice());
		return super.update(sql, parameters);
	}
	
	public int delete(UUID idPratica)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"pptr_selezioni\"")
				.append(" where \"id_pratica\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		return super.update(sql, parameters);
	}

	/**
	 * rimuove dalle selezioni i codici non più ammessi nel nuovo tipoProcedimento
	 * utilizzato quando si cambia tipoProcedimento sulla pratica in editing
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param tipoProcedimento
	 * @return
	 */
	public int deleteNonAmmessi(UUID idPratica,int tipoProcedimento) {
	final StringBuilder sql=new StringBuilder();	
	sql.append(StringUtil.concateneString(
			"delete from \"presentazione_istanza\".\"pptr_selezioni\" ps where ", 
			" ps.id_pratica = ? and ", 
			" ps.codice not in  ",
			" (select utp.codice_ucp as codiciAmmessi ", 
			" FROM ",
			" \"presentazione_istanza\".\"ucp_tipo_procedimento\" utp left join ",
			" \"presentazione_istanza\".\"ulteriori_contesti_paesaggistici\" uc on ",
			" utp.codice_ucp = uc.codice  ",
			" where ",
			" utp.id_tipo_procedimento = ?",
			" and data_inizio_val <= now() and  ",
			" (data_fine_val is null or data_fine_val >=now())) "));
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idPratica);
		parameters.add(tipoProcedimento);
		return super.update(sql.toString(), parameters);
	}
}
