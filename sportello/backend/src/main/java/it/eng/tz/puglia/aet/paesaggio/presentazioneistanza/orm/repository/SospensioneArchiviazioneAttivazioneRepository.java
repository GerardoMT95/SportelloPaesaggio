package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SospensioneArchiviazioneAttivazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.SospensioneArchiviazioneAttivazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SospensioneArchiviazioneAttivazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table
 * presentazione_istanza.sospensione_archiviazione_attivazione
 */
@Repository
public class SospensioneArchiviazioneAttivazioneRepository
		extends GenericCrudDao<SospensioneArchiviazioneAttivazioneDTO, SospensioneArchiviazioneAttivazioneSearch>
{

	private final SospensioneArchiviazioneAttivazioneRowMapper rowMapper = new SospensioneArchiviazioneAttivazioneRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"sospensione_archiviazione_attivazione\".\"id\"")
			.append(",\"id_pratica\"").append(",\"type\"").append(",\"draft\"").append(",\"note\"")
			.append(",\"data\"").append(",\"username_utente_creazione\"").append(", \"stato_pratica\"")
			.append(",\"pratica\".\"codice_pratica_appptr\" as \"codice_pratica\"")
			.append(" from \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"")
			.append(" join \"presentazione_istanza\".\"pratica\"").append(" on \"id_pratica\" = \"pratica\".\"id\"")
			.toString();

	private String getWhereClause(String baseSql, Map<String, Object> parameters, SospensioneArchiviazioneAttivazioneSearch search)
	{
		final StringBuilder sql = new StringBuilder(baseSql);
		String sep = " where ";
		if (search.getId() != null)
		{
			sql.append(sep).append("\"sospensione_archiviazione_attivazione\".\"id\" = :id");
			parameters.put(":id", search.getId());
			sep = " and ";
		}
		if (search.getIdPratica() != null)
		{
			sql.append(sep).append("\"id_pratica\" = :id_pratica");
			parameters.put("id_pratica", search.getIdPratica());
			sep = " and ";
		}
		if (search.getType() != null)
		{
			sql.append(sep).append("\"type\" = :type");
			parameters.put("type", search.getType().name());
			sep = " and ";
		}
		if (search.getDraft() != null)
		{
			sql.append(sep).append("\"draft\" = :draft");
			parameters.put("draft", search.getDraft());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNote()))
		{
			sql.append(sep).append("\"note\" like :note");
			parameters.put("note", StringUtil.convertLike(search.getNote()));
			sep = " and ";
		}
		if(search.getDataDa() != null)
		{
			sql.append(sep).append("\"data\" >= :data_da");
			parameters.put("data_da", search.getDataDa());
			sep = " and ";
		}
		if(search.getDataA() != null)
		{
			sql.append(sep).append("\"data\" <= :data_a");
			parameters.put("data_a", search.getDataA());
			sep = " and ";

		}
		if (StringUtil.isNotEmpty(search.getUsernameUtenteCreazione()))
		{
			sql.append(sep).append("\"username_utente_creazione\" like :username");
			parameters.put("username", StringUtil.convertRightLike(search.getUsernameUtenteCreazione()));
			sep = " and ";
		}
		if(StringUtil.isNotEmpty(search.getCodicePratica()))
		{
			sql.append(sep).append("\"codice_pratica_appptr\" like :codice");
			parameters.put("codice", StringUtil.convertRightLike(search.getCodicePratica()));
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
			case "type":
				sql.append(" order by \"type\" ").append(sortType);
				break;
			case "draft":
				sql.append(" order by \"draft\" ").append(sortType);
				break;
			case "note":
				sql.append(" order by \"note\" ").append(sortType);
				break;
			case "data":
				sql.append(" order by \"data\" ").append(sortType);
				break;
			case "usernameUtenteCreazione":
				sql.append(" order by \"username_utente_creazione\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}

	@Override
	public List<SospensioneArchiviazioneAttivazioneDTO> select()
	{
		return super.queryForListTxRead(selectAll, rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count()
	{
		final String sql = new StringBuilder("select count(*)")
				.append(" from \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"").toString();
		logger.info("Eseguo la query {}", sql);
		return super.queryForObjectTxRead(sql, Long.class);
	}
	
	public long count(SospensioneArchiviazioneAttivazioneSearch search) throws Exception
	{
		String sql = "select count(*) from \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		sql = getWhereClause(sql, parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public SospensioneArchiviazioneAttivazioneDTO find(SospensioneArchiviazioneAttivazioneDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"sospensione_archiviazione_attivazione\".\"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public SospensioneArchiviazioneAttivazioneDTO find(Long id) throws Exception
	{
		final String sql = new StringBuilder(selectAll).append(" where \"sospensione_archiviazione_attivazione\".\"id\" = :id").toString();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, rowMapper);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<SospensioneArchiviazioneAttivazioneDTO> search(SospensioneArchiviazioneAttivazioneSearch search)
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = getWhereClause(selectAll, parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	public SospensioneArchiviazioneAttivazioneDTO findDraft(UUID idPratica) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		SospensioneArchiviazioneAttivazioneSearch search = new SospensioneArchiviazioneAttivazioneSearch();
		search.setIdPratica(idPratica);
		search.setDraft(true);
		String sql = getWhereClause(selectAll, parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, rowMapper);
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(SospensioneArchiviazioneAttivazioneDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"")
						.append("(\"id_pratica\"").append(",\"type\"").append(",\"draft\"").append(",\"note\"")
						.append(",\"data\"").append(",\"username_utente_creazione\"").append(",\"stato_pratica\"")
						.append(") values ").append("(?").append(",?").append(",?").append(",?").append(",?").append(",?")
						.append(",?").append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getType() != null ? entity.getType().name() : null);
		parameters.add(entity.getDraft());
		parameters.add(entity.getNote());
		parameters.add(entity.getData());
		parameters.add(entity.getUsernameUtenteCreazione());
		parameters.add(entity.getStatoPratica());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.insertAndGetAutoincrementValue(sql, parameters, "id");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(SospensioneArchiviazioneAttivazioneDTO entity)
	{
		final String sql = new StringBuilder(
				"update \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"")
						.append(" set \"id_pratica\" = ?").append(", \"type\" = ?").append(", \"draft\" = ?")
						.append(", \"note\" = ?").append(", \"data\" = ?").append(", \"username_utente_creazione\" = ?")
						.append(", \"stato_pratica\" = ?").append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdPratica());
		parameters.add(entity.getType() != null ? entity.getType().name() : null);
		parameters.add(entity.getDraft());
		parameters.add(entity.getNote());
		parameters.add(entity.getData());
		parameters.add(entity.getUsernameUtenteCreazione());
		parameters.add(entity.getStatoPratica() != null ? entity.getStatoPratica().name() : null);
		parameters.add(entity.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(SospensioneArchiviazioneAttivazioneDTO entity)
	{
		final String sql = new StringBuilder(
				"delete from \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"")
						.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.update(sql, parameters);
	}
	
	public int delete(SospensioneArchiviazioneAttivazioneSearch search) throws Exception
	{
		String sql = "delete from \"presentazione_istanza\".\"sospensione_archiviazione_attivazione\"";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		sql = getWhereClause(sql, parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public Integer insertRichiestaAllegato(UUID idAllegato, Long idRichiesta) throws Exception
	{
		String sql = StringUtil.concateneString("insert into \"presentazione_istanza\"",
												".\"allegato_sosp_arc_att\"(\"id_allegato\",",
												"\"id_sosp_arc_att\") values(:id_allegato, :id_sosp_arc_att)");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		parameters.put("id_sosp_arc_att", idRichiesta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public List<UUID> findAllegatiRichiesta(Long idRichiesta) throws Exception
	{
		String sql = StringUtil.concateneString("select \"id_allegato\" ",
												"from \"presentazione_istanza\".\"allegato_sosp_arc_att\" ",
												"where \"id_sosp_arc_att\" = :id_richiesta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_richiesta", idRichiesta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
	}
	
	public List<SospensioneArchiviazioneAttivazioneDTO> getStorico(UUID idPratica) throws Exception
	{
		String sql = StringUtil.concateneString(selectAll,
											    " where \"id_pratica\" = :id_pratica",
											    " and \"draft\" = false",
											    " order by \"data\" desc");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", idPratica);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.query(sql, parameters, rowMapper);
	}

}
