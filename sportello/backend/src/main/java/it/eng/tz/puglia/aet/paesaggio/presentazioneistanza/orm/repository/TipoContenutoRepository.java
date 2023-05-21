package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipoContenutoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoContenutoSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.tipo_contenuto
 */
@Repository
public class TipoContenutoRepository extends GenericCrudDao<TipoContenutoDTO, TipoContenutoSearch>
{

	private final TipoContenutoRowMapper rowMapper = new TipoContenutoRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select")
			.append(" \"id\"")
			.append(",\"descrizione\"")
			.append(",\"descrizione_estesa\"")
			.append(",\"sezione\"")
			.append(",\"multiple\"")
			.append(",\"check_pagamento\"")
			.append(" from \"presentazione_istanza\".\"tipo_contenuto\" ").toString();

	@Override
	public List<TipoContenutoDTO> select()
	{
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}

	protected String genereteWhereClause(String baseSql, Map<String, Object> parameters, TipoContenutoSearch search)
	{
		String sep = " where ";
		StringBuilder sql = new StringBuilder(baseSql);
		if (StringUtil.isNotEmpty(search.getId()))
		{
			sql.append(sep).append("\"id\" = :id");
			parameters.put("id", search.getId());
			sep = " and ";
		}
		if(search.getIds() != null && !search.getIds().isEmpty())
		{
			sql.append(sep).append("\"id\" in (:ids)");
			parameters.put("ids", search.getIds());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDescrizione()))
		{
			sql.append(sep).append("\"descrizione\" like :desc");
			parameters.put("desc", search.getDescrizione());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDescrizioneEstesa()))
		{
			sql.append(sep).append("lower(\"descrizione_estesa\"::text) like :desc_xt");
			parameters.put("desc_xt", StringUtil.convertLike(search.getDescrizioneEstesa().toLowerCase()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSezione()))
		{
			sql.append(sep).append("\"sezione\" = :sezione");
			parameters.put("sezione", search.getSezione());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "id":
				sql.append(" sort by \"id\" ").append(sortType);
				break;
			case "descrizione":
				sql.append(" sort by \"descrizione\" ").append(sortType);
				break;
			case "descrizioneEstesa":
				sql.append(" sort by \"descrizione_estesa\" ").append(sortType);
				break;
			case "sezione":
				sql.append(" sort by \"sezione\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

	/**
	 * count all method
	 */
	@Override
	public long count()
	{
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"tipo_contenuto\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}
	
	public Long count(TipoContenutoSearch search) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = genereteWhereClause("select count(*) from \"presentazione_istanza\".\"tipo_contenuto\"", parameters, search);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public TipoContenutoDTO find(TipoContenutoDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public TipoContenutoDTO findById(int id)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}


	/**
	 * search method
	 */
	@Override
	public PaginatedList<TipoContenutoDTO> search(TipoContenutoSearch search)
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = genereteWhereClause(selectAll, parameters, search);		
		return super.paginatedList(sql, parameters, rowMapper, search.getPage(), search.getLimit());
	}

	public List<TipoContenutoDTO> findTypeByPraticaId(UUID idPratica, Collection<String> sezioni) throws Exception
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idPratica", idPratica);
		parameters.put("sezioni", sezioni);
		StringBuilder sqlBuilder = new StringBuilder(selectAll).append(" where \"sezioni\" in (:sezioni) ")
				.append("and (select count(*) ").append("from \"presentazione_istanza\".\"allegati_tipo_contenuto\" ")
				.append("\"allegati_id\" = :idPratica) > 0");
		return queryForList(sqlBuilder.toString(), this.rowMapper, parameters);
	}

	public List<Integer> findTipiAllegato(UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("select tc.\"id\" from ",
				"\"presentazione_istanza\".\"tipo_contenuto\" as tc ",
				"join \"presentazione_istanza\".\"allegati_tipo_contenuto\" as atc ",
				"on tc.\"id\" = atc.\"tipo_contenuto_id\" ", "where atc.\"allegati_id\" = :id_allegato");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		return namedJdbcTemplateRead.queryForList(sql, parameters, Integer.class);
	}
	
	public List<String> findDescrEstesaTipiAllegato(UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("select tc.\"descrizione_estesa\" from ",
				"\"presentazione_istanza\".\"tipo_contenuto\" as tc ",
				"join \"presentazione_istanza\".\"allegati_tipo_contenuto\" as atc ",
				"on tc.\"id\" = atc.\"tipo_contenuto_id\" ", "where atc.\"allegati_id\" = :id_allegato");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		return namedJdbcTemplateRead.queryForList(sql, parameters, String.class);
	}
	
	public List<String> findDescrTipiAllegato(UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("select tc.\"descrizione\" from ",
				"\"presentazione_istanza\".\"tipo_contenuto\" as tc ",
				"join \"presentazione_istanza\".\"allegati_tipo_contenuto\" as atc ",
				"on tc.\"id\" = atc.\"tipo_contenuto_id\" ", "where atc.\"allegati_id\" = :id_allegato");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		return namedJdbcTemplateRead.queryForList(sql, parameters, String.class);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(TipoContenutoDTO entity)
	{
		throw new RuntimeException("Inserimento non supportato !!");
//		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"tipo_contenuto\"")
//				.append("(\"id\"").append(",\"descrizione\"").append(",\"descrizione_estesa\"").append(",\"sezione\"")
//				.append(") values ").append("(?").append(",?").append(",?").append(",?").append(")").toString();
//		final List<Object> parameters = new ArrayList<Object>();
//		parameters.add(entity.getId());
//		parameters.add(entity.getDescrizione());
//		parameters.add(entity.getDescrizioneEstesa());
//		parameters.add(entity.getSezione());
//		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(TipoContenutoDTO entity)
	{
		throw new RuntimeException("Aggiornamento non supportato !!");
//		final String sql = new StringBuilder("update \"presentazione_istanza\".\"tipo_contenuto\"")
//				.append(" set \"descrizione\" = ?").append(", \"descrizione_estesa\" = ?").append(", \"sezione\" = ?")
//				.append(" where \"id\" = ?").toString();
//		final List<Object> parameters = new ArrayList<Object>();
//		parameters.add(entity.getDescrizione());
//		parameters.add(entity.getDescrizioneEstesa());
//		parameters.add(entity.getSezione());
//		parameters.add(entity.getId());
//		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(TipoContenutoDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"tipo_contenuto\"")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	public List<TipoContenutoDTO> selectByTipoProc(Integer tipoProcedimento)
	{
		final String selectAllByTipoProc = new 
				StringBuilder(selectAll)
				.append(" tc ")
				.append(",  \"presentazione_istanza\".\"procedimento_contenuto\" pc ")
				.append(" where pc.tipo_contenuto_id=tc.id and pc.tipo_procedimento = ? order by tc.id").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(tipoProcedimento);
		return super.queryForListTxRead(selectAllByTipoProc, this.rowMapper, parameters);
	}

	public TipoContenutoDTO findContenutoByDesc(String descrizone)
	{
		final String selectByDesc = new StringBuilder(selectAll)
				.append(" tc ").append(" WHERE tc.descrizione = ? ")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(descrizone);
		return super.queryForObjectTxRead(selectByDesc, this.rowMapper, parameters);
	}

}
