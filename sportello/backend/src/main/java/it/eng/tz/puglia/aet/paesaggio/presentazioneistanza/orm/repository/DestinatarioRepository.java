package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoCorrispondenza;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DestinatarioRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.DestinatarioSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.destinatario
 */
@Repository
public class DestinatarioRepository extends GenericCrudDao<DestinatarioDTO, DestinatarioSearch>
{

	private final DestinatarioRowMapper rowMapper = new DestinatarioRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"type\"").append(",\"email\"")
			.append(",\"stato\"").append(",\"pec\"").append(",\"denominazione\"").append(",\"data_ricezione\"")
			.append(",\"id_corrispondenza\"").append(" from \"presentazione_istanza\".\"destinatario\"").toString();
	
	private String generateClause(String openingSql, DestinatarioSearch search, List<Object> parameters)
	{
		StringBuilder sql = new StringBuilder(openingSql);
		String sep = " where ";
		if (StringUtil.isNotEmpty(search.getId()))
		{
			sql.append(sep).append("lower(\"id\"::text) = ?");
			parameters.add(search.getId());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getType()))
		{
			sql.append(sep).append("lower(\"type\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getType()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getEmail()))
		{
			sql.append(sep).append("lower(\"email\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getEmail()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getStato()))
		{
			sql.append(sep).append("lower(\"stato\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getStato()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getPec()))
		{
			sql.append(sep).append("lower(\"pec\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getPec()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDenominazione()))
		{
			sql.append(sep).append("lower(\"denominazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDenominazione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataRicezione()))
		{
			sql.append(sep).append("lower(\"data_ricezione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataRicezione()));
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
			case "id":
				sql.append(" order by \"id\" ").append(sortType);
				break;
			case "type":
				sql.append(" order by \"type\" ").append(sortType);
				break;
			case "email":
				sql.append(" order by \"email\" ").append(sortType);
				break;
			case "stato":
				sql.append(" order by \"stato\" ").append(sortType);
				break;
			case "pec":
				sql.append(" order by \"pec\" ").append(sortType);
				break;
			case "denominazione":
				sql.append(" order by \"denominazione\" ").append(sortType);
				break;
			case "dataRicezione":
				sql.append(" order by \"data_ricezione\" ").append(sortType);
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
	public List<DestinatarioDTO> select()
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
				.append(" from \"presentazione_istanza\".\"destinatario\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public DestinatarioDTO find(DestinatarioDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public DestinatarioDTO findTxWrite(DestinatarioDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObject(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<DestinatarioDTO> search(DestinatarioSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
//		final StringBuilder sql = new StringBuilder(selectAll);
		final String sql = generateClause(selectAll, search, parameters);
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	public List<DestinatarioDTO> searchWithoutPaging(DestinatarioSearch search) throws Exception
	{
		final List<Object> parameters = new ArrayList<Object>();
		final String sql = generateClause(selectAll, search, parameters);
		return super.queryForList(sql, rowMapper, parameters);
	}
	
	public List<DestinatarioDTO> searchWithoutPaging(Long idCorrispondenza) throws Exception
	{
		DestinatarioSearch search = new DestinatarioSearch();
		search.setIdCorrispondenza(idCorrispondenza.toString());
		return searchWithoutPaging(search);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(DestinatarioDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"destinatario\"")
				.append("(\"type\"").append(",\"email\"").append(",\"stato\"").append(",\"pec\"")
				.append(",\"denominazione\"").append(",\"data_ricezione\"").append(",\"id_corrispondenza\"")
				.append(") values ").append("(?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getTipo().name());
		parameters.add(entity.getEmail());
		parameters.add(entity.getStato()==null?null:entity.getStato().name());
		parameters.add(entity.getPec());
		parameters.add(entity.getNome());
		parameters.add(entity.getDataRicezione());
		parameters.add(entity.getIdCorrispondenza());
		return super.insertAndGetAutoincrementValue(sql, parameters, "id");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(DestinatarioDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"destinatario\"")
				.append(" set \"type\" = ?").append(", \"email\" = ?").append(", \"stato\" = ?").append(", \"pec\" = ?")
				.append(", \"denominazione\" = ?").append(", \"data_ricezione\" = ?")
				.append(", \"id_corrispondenza\" = ?").append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getTipo());
		parameters.add(entity.getEmail());
		parameters.add(entity.getStato()==null?null:entity.getStato().name());
		parameters.add(entity.getPec());
		parameters.add(entity.getNome());
		parameters.add(entity.getDataRicezione());
		parameters.add(entity.getIdCorrispondenza());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(DestinatarioDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"destinatario\"")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	public int delete(Long idCorrispondenza) throws Exception
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"destinatario\"")
				.append(" where \"id_corrispondenza\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idCorrispondenza);
		return super.update(sql, parameters);
	}
	
	public List<DestinatarioDTO> getDestFromIdComunicazioni(long idCorrispondenza) {
		final String sql = new StringBuilder(selectAll).append(" where \"id_corrispondenza\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idCorrispondenza);
		return super.queryForList(sql, this.rowMapper, parameters);

	}
	
	public int updateFieldPec(List<Long> listaDestinatari,boolean pec) {
		String inSql = String.join(",", Collections.nCopies(listaDestinatari.size(), "?"));
		final String sql = "UPDATE presentazione_istanza.destinatario set pec = ? where id IN ("+inSql+")";
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pec);
		for (Long destinatario : listaDestinatari) {
			parameters.add(destinatario);
		}
		return super.update(sql, parameters);
		
	}

	public int updateStato(Long idCorrispondenza, StatoCorrispondenza inviata) {
		final String sql = "UPDATE presentazione_istanza.destinatario set stato = ? where  \"id_corrispondenza\" = ? ";
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(inviata.name());
		parameters.add(idCorrispondenza);
		return super.update(sql, parameters);
	}

}
