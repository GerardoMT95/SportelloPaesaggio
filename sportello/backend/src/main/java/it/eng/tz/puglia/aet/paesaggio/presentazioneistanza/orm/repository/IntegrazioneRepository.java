package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.IntegrazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.IntegrazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.integrazione
 */
@Repository
public class IntegrazioneRepository extends GenericCrudDao<IntegrazioneDTO, IntegrazioneSearch>
{

	private final IntegrazioneRowMapper rowMapper = new IntegrazioneRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"data_creazione\"")
			.append(",\"data_caricamento\"").append(",\"stato\"").append(",\"richiesta_integrazione\"")
			.append(",\"username_utente_creazione\"").append(",\"descrizione\"").append(",\"note\"")
			.append(",\"pratica_id\"").append(" from \"presentazione_istanza\".\"integrazione\"").toString();

	@Override
	public List<IntegrazioneDTO> select()
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
				.append(" from \"presentazione_istanza\".\"integrazione\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	public long count(IntegrazioneSearch search) throws Exception
	{
		final List<Object> parameters = new ArrayList<Object>();
		final String sql = new StringBuilder("select count(*) from \"presentazione_istanza\".\"integrazione\"")
				.append(buildWhere(parameters, search)).toString();
		return super.queryForObjectTxRead(sql, Long.class, parameters);
	}

	/**
	 * find by pk method
	 */
	@Override
	public IntegrazioneDTO find(IntegrazioneDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	private String buildWhere(List<Object> parameters, IntegrazioneSearch search)
	{
		String sep = " where ";
		final StringBuilder sql = new StringBuilder();
		if (search.getId() != null)
		{
			sql.append(sep).append("\"id\" = ?");
			parameters.add(search.getId());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataCreazione()))
		{
			sql.append(sep).append("lower(\"data_creazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataCreazione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataCaricamento()))
		{
			sql.append(sep).append("lower(\"data_caricamento\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataCaricamento()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getStato()))
		{
			sql.append(sep).append("lower(\"stato\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getStato()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getRichiestaIntegrazione()))
		{
			sql.append(sep).append("lower(\"richiesta_integrazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getRichiestaIntegrazione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUsernameUtenteCreazione()))
		{
			sql.append(sep).append("lower(\"username_utente_creazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getUsernameUtenteCreazione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDescrizione()))
		{
			sql.append(sep).append("lower(\"descrizione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDescrizione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNote()))
		{
			sql.append(sep).append("lower(\"note\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getNote()));
			sep = " and ";
		}
		if (search.getPraticaId() != null)
		{
			sql.append(sep).append("\"pratica_id\" = ?");
			parameters.add(search.getPraticaId());
			sep = " and ";
		}
		if (search.getAttiva() != null && search.getAttiva() == true)
		{
			sql.append(sep).append("\"data_caricamento\" is null");
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
			case "dataCreazione":
				sql.append(" order by \"data_creazione\" ").append(sortType);
				break;
			case "dataCaricamento":
				sql.append(" order by \"data_caricamento\" ").append(sortType);
				break;
			case "stato":
				sql.append(" order by \"stato\" ").append(sortType);
				break;
			case "richiestaIntegrazione":
				sql.append(" order by \"richiesta_integrazione\" ").append(sortType);
				break;
			case "usernameUtenteCreazione":
				sql.append(" order by \"username_utente_creazione\" ").append(sortType);
				break;
			case "descrizione":
				sql.append(" order by \"descrizione\" ").append(sortType);
				break;
			case "note":
				sql.append(" order by \"note\" ").append(sortType);
				break;
			case "praticaId":
				sql.append(" order by \"pratica_id\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<IntegrazioneDTO> search(IntegrazioneSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sql = new StringBuilder(selectAll).append(buildWhere(parameters, search)).toString();
		return super.paginatedList(sql, parameters, this.rowMapper, search.getPage(), search.getLimit());
	}

	public List<IntegrazioneDTO> searchForList(IntegrazioneSearch search) throws Exception
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sql = new StringBuilder(selectAll).append(buildWhere(parameters, search)).toString();
		return queryForList(sql, rowMapper, parameters);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(IntegrazioneDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"integrazione\"")
				.append("(\"stato\"").append(",\"richiesta_integrazione\"").append(",\"username_utente_creazione\"")
				.append(",\"descrizione\"").append(",\"note\"").append(",\"pratica_id\"").append(") values ")
				.append("(?").append(",?").append(",?").append(",?").append(",?").append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getStato().name());
		parameters.add(entity.getRichiestaIntegrazione());
		parameters.add(entity.getUsernameUtenteCreazione());
		parameters.add(entity.getDescrizione());
		parameters.add(entity.getNote());
		parameters.add(entity.getPraticaId());
		return super.insertAndGetAutoincrementValue(sql, parameters, "id");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(IntegrazioneDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"integrazione\"")
				.append(" set \"data_caricamento\" = ?").append(", \"stato\" = ?")
				.append(", \"richiesta_integrazione\" = ?").append(", \"username_utente_creazione\" = ?")
				.append(", \"descrizione\" = ?").append(", \"note\" = ?").append(", \"pratica_id\" = ?")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getDataCaricamento());
		parameters.add(entity.getStato());
		parameters.add(entity.getRichiestaIntegrazione());
		parameters.add(entity.getUsernameUtenteCreazione());
		parameters.add(entity.getDescrizione());
		parameters.add(entity.getNote());
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(IntegrazioneDTO entity)
	{
		final StringBuilder sql = new StringBuilder("delete from \"presentazione_istanza\".\"integrazione\"");
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		if (entity.getId() != null)
		{
			sql.append(sep).append("\"id\"=?");
			parameters.add(entity.getId());
			sep = " and ";
		}
		if (entity.getPraticaId() != null)
		{
			sql.append(sep).append("\"pratica_id\"=?");
			parameters.add(entity.getPraticaId());
			sep = " and ";
		}
		return super.update(sql.toString(), parameters);
	}
	
	public Integer updateStatus(Integer idIntegrazione, StatoIntegrazioneDocumentale nuovoStato,Date dataCaricamento) throws Exception
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sqlBuilder = new StringBuilder("update \"presentazione_istanza\".\"integrazione\" set ")
										.append("\"stato\" = :stato ");
		StringBuilder whereClause = new StringBuilder("where \"id\" = :id");
		switch (nuovoStato)
		{
		case IN_BOZZA://da valutare se si può far "regredire" lo stato del fascicolo... si da IN_ATTESA
			//throw new Exception("Stato non valido per l'aggiornamento dell'integrazione");
			whereClause.append(" and \"stato\" = 'IN_ATTESA'");
			break;
		case IN_ATTESA:
			whereClause.append(" and \"stato\" = 'IN_BOZZA'");
			break;
		case COMPLETATA:
			whereClause.append(" and \"stato\" = 'IN_ATTESA'");
			sqlBuilder.append(", \"data_caricamento\" = :data ");
			parameters.put("data", dataCaricamento);
			break;
		case ANNULLATA:
			whereClause.append(" and \"stato\" != 'COMPLETATA'");
			break;
		default:
			throw new Exception("Stato non valido per l'aggiornamento dell'integrazione");
		}
		parameters.put("id", idIntegrazione);
		parameters.put("stato", nuovoStato.name());
		sqlBuilder.append(whereClause);
		return super.namedJdbcTemplateWrite.update(sqlBuilder.toString(), parameters);
	}

}
