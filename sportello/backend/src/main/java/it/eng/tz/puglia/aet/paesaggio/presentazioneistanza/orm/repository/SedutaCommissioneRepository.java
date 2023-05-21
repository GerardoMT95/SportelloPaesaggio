package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SimplePraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.AllegatiCommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSedutaCommissione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoAllegatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SedutaCommissioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.SedutaCommissioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SedutaCommissioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.seduta_commissione
 */
@Repository
public class SedutaCommissioneRepository extends GenericCrudDao<SedutaCommissioneDTO, SedutaCommissioneSearch>
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final SedutaCommissioneRowMapper rowMapper = new SedutaCommissioneRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(",\"id_organizzazione\"")
			.append(", \"id_ente_delegato\"").append(",\"data_seduta\"").append(",\"username_utente_creazione\"")
			.append(",\"data_creazione\"").append(",\"nome_seduta\"").append(",\"stato\"").append(",\"pubblica\"")
			.append(" from \"presentazione_istanza\".\"seduta_commissione\"").toString();

	private String generateWhereClause(String baseSql, SedutaCommissioneSearch search, Map<String, Object> parameters)
	{
		String sep = " where ";
		StringBuilder sql = new StringBuilder(baseSql);
		if (search.getId() != null)
		{
			sql.append(sep).append("\"id\" = :id");
			parameters.put("id", search.getId());
			sep = " and ";
		}
		if(StringUtil.isNotEmpty(search.getNomeSeduta()))
		{
			sql.append(sep).append("\"nome_seduta\" like :nome_seduta");
			parameters.put("nome_seduta", StringUtil.convertRightLike(search.getNomeSeduta()));
			sep = " and ";
		}
		if (search.getIdOrganizzazione() != null)
		{
			sql.append(sep).append("\"id_organizzazione\" = :id_organizzazione");
			parameters.put("id_organizzazione", search.getIdOrganizzazione());
			sep = " and ";
		}
		if (search.getIdEnteDelegato() != null)
		{
			sql.append(sep).append("\"id_ente_delegato\" = :id_ente_delegato");
			parameters.put("id_ente_delegato", search.getIdEnteDelegato());
			sep = " and ";
		}
		if (search.getDataSedutaDa() != null)
		{
			sql.append(sep).append("\"data_seduta\" >= :data_seduta_da");
			parameters.put("data_seduta_da", search.getDataSedutaDa());
			sep = " and ";
		}
		if (search.getDataSedutaA() != null)
		{
			sql.append(sep).append("(\"data_seduta\"::date) <= :data_seduta_a");
			parameters.put("data_seduta_a", search.getDataSedutaA());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getUsernameUtenteCreazione()))
		{
			sql.append(sep).append("\"username_utente_creazione\" like :username");
			parameters.put("username", search.getUsernameUtenteCreazione());
			sep = " and ";
		}
		if (search.getDataCreazione() != null)
		{
			sql.append(sep).append("\"data_creazione\" = :data_creazione");
			parameters.put("data_creazione", search.getDataCreazione());
			sep = " and ";
		}
		if(search.getPubblica() != null)
		{
			sql.append(sep).append("\"pubblica\" is :pubblica");
			parameters.put("pubblica", search.getPubblica());
			sep = " and ";
		}
		if(search.getStato() != null)
		{
			sql.append(sep).append("\"stato\" = :stato");
			parameters.put("stato", search.getStato().name());
			sep = " and ";
		}
		if(StringUtil.isNotEmpty(search.getCodicePratica()))
		{
			sql.append(sep)
			   .append("(select count(*) from ")
			   .append("\"presentazione_istanza\".\"seduta_pratica\" ")
			   .append("join \"presentazione_istanza\".\"pratica\" ")
			   .append("on \"codice_pratica_appptr\" = :codice_pratica ")
			   .append("where \"id_seduta\" = \"seduta_commissione\".\"id\" ")
			   .append("and \"id_pratica\" = \"pratica\".\"id\") > 0");
			parameters.put("codice_pratica", search.getCodicePratica());
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
			case "idOrganizzazione":
				sql.append(" order by \"id_organizzazione\" ").append(sortType);
				break;
			case "dataSeduta":
				sql.append(" order by \"data_seduta\" ").append(sortType);
				break;
			case "usernameUtenteCreazione":
				sql.append(" order by \"username_utente_creazione\" ").append(sortType);
				break;
			case "dataCreazione":
				sql.append(" order by \"data_creazione\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return sql.toString();
	}
	
	@Override
	public List<SedutaCommissioneDTO> select()
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
				.append(" from \"presentazione_istanza\".\"seduta_commissione\"").toString();
		logger.info("Eseguo la query {}", sql);
		return super.queryForObjectTxRead(sql, Long.class);
	}
	
	public long count(SedutaCommissioneSearch search)
	{
		String sql = StringUtil.concateneString("select count(*)",
												" from \"presentazione_istanza\".\"seduta_commissione\"");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		sql = generateWhereClause(sql, search, parameters);
		logger.info("Eseguo la query {}", sql, parameters);
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public SedutaCommissioneDTO find(SedutaCommissioneDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	/**
	 * find by id method
	 */
	public SedutaCommissioneDTO find(Long id)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = :id").toString();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, rowMapper);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<SedutaCommissioneDTO> search(SedutaCommissioneSearch search)
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = generateWhereClause(selectAll, search, parameters);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return paginatedList(sql, parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	public PaginatedList<SedutaCommissioneDto> searchWitCount(SedutaCommissioneSearch search) throws Exception
	{
		String baseSql = new StringBuilder("select").append(" \"id\"").append(",\"id_organizzazione\"")
				.append(", \"id_ente_delegato\"").append(",\"data_seduta\"").append(",\"username_utente_creazione\"")
				.append(",\"data_creazione\"").append(",\"nome_seduta\"").append(",\"stato\"").append(",\"pubblica\"")
				.append(", (select count(\"id_pratica\") from \"presentazione_istanza\".\"seduta_pratica\"")
				.append(" where \"id_seduta\" = \"id\") as \"n_da_esaminare\"")
				.append(", (select count(distinct \"id_pratica\") from \"presentazione_istanza\".\"seduta_pratica_allegati\"")
				.append(" where \"id_seduta\" = \"id\") as \"n_esaminate\"")
				.append(" from \"presentazione_istanza\".\"seduta_commissione\"").toString();
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = generateWhereClause(baseSql, search, parameters);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return paginatedList(sql, parameters, new RowMapper<SedutaCommissioneDto>()
		{
			@Override
			public SedutaCommissioneDto mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				SedutaCommissioneDto dto = null;
				if(rs != null)
				{
					dto = new SedutaCommissioneDto(rowMapper.mapRow(rs, rowNum));
					dto.setnFascicoli(rs.getInt("n_da_esaminare"));
					dto.setnFascicoliEsaminati(rs.getInt("n_esaminate"));
				}
				return dto;
			}
		}, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(SedutaCommissioneDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"seduta_commissione\"")
				.append("(\"id_organizzazione\"").append(", \"id_ente_delegato\"").append(",\"data_seduta\"")
				.append(",\"username_utente_creazione\"").append(", \"nome_seduta\"").append(", \"pubblica\"").append(", \"stato\"")
				.append(") values ").append("(:id_organizzazione").append(", :id_ente_delegato").append(", :data_seduta")
				.append(", :username").append(", :nome_seduta").append(", :pubblica").append(", :stato")
				.append(") returning \"id\"").toString();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id_organizzazione", entity.getIdOrganizzazione());
		parameters.put("id_ente_delegato", entity.getIdEnteDelegato());
		parameters.put("data_seduta", entity.getDataSeduta());
		parameters.put("username", entity.getUsernameUtenteCreazione());
		parameters.put("nome_seduta", entity.getNomeSeduta());
		parameters.put("pubblica", entity.getPubblica());
		parameters.put("stato", entity.getStato().name());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.queryForObject(sql, parameters, Long.class);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(SedutaCommissioneDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"seduta_commissione\"")
				.append(" set \"data_seduta\" = :data_seduta").append(", \"nome_seduta\" = :nome_seduta")
				.append(", \"pubblica\" = :pubblica").append(", \"stato\" = :stato").append(" where \"id\" = :id").toString();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("data_seduta", entity.getDataSeduta());
		parameters.put("nome_seduta", entity.getNomeSeduta());
		parameters.put("pubblica", entity.getPubblica());
		parameters.put("stato", entity.getStato().name());
		parameters.put("id", entity.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.update(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(SedutaCommissioneDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"seduta_commissione\"")
				.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.update(sql, parameters);
	}
	
	public int delete(Long idSeduta) throws Exception
	{
		SedutaCommissioneDTO entity = new SedutaCommissioneDTO();
		entity.setId(idSeduta);
		return delete(entity);
	}
	
	public StatoSedutaCommissione getStatoSeduta(Long idSeduta) throws Exception 
	{
		final String sql = "select \"stato\" from \"presentazione_istanza\".\"seduta_commissione\" where \"id\" = :id";
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, StatoSedutaCommissione.class);
	}
	
	//Table seduta_pratica
	public List<UUID> getPratiche(Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"id_pratica\" from ",
													  "\"presentazione_istanza\".\"seduta_pratica\" ",
													  "where \"id_seduta\" = :id_seduta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
	}
	
	public Integer insertSedutaPratica(List<UUID> pratiche, Long idSeduta) throws Exception
	{
		Integer n = 0;
		if(pratiche != null && !pratiche.isEmpty())
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"seduta_pratica\"",
													"(\"id_pratica\", \"id_seduta\") values ");
			parameters.put("id_seduta", idSeduta);
			sql = generateMultipleInsert(sql, pratiche, parameters, null, ", :id_seduta");
			logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
			n = namedUpdate(sql, parameters);
		}
		return n;
	}
	
	public Integer countPraticheDaEsaminare(Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("select count(\"id_pratica\") from ",
													  "\"presentazione_istanza\".\"seduta_pratica\" ",
													  "where \"id_seduta\" = :id_seduta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Integer.class);
	}
	
	public Integer removeSedutaPratica(List<UUID> pratiche, Long idSeduta) throws Exception
	{
		Integer n = 0;
		if(pratiche != null && !pratiche.isEmpty())
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"seduta_pratica\" ",
													"where \"id_seduta\" = :id_seduta ",
													"and \"id_pratica\" in (:ids)");
			parameters.put("id_seduta", idSeduta);
			parameters.put("ids", pratiche);
			logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
			n = namedUpdate(sql, parameters);
		}
		return n;
	}
	
	public Integer removeSedutaPratica(Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"seduta_pratica\" ",
													  "where \"id_seduta\" = :id_seduta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	//Table seduta_allegati
	public List<UUID> getAllegatiSeduta(Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"id_allegato\" from ",
													  "\"presentazione_istanza\".\"seduta_allegati\" ",
													  "where \"id_seduta\" = :id_seduta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		return namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
	}
	
	public Integer insertSedutaAllegati(UUID idAllegato, Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\"",
													  ".\"seduta_allegati\"(\"id_allegato\", \"id_seduta\") ",
													  "values(:id_allegato, :id_seduta)");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato",  idAllegato);
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	public Integer deleteAllegato(UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"seduta_allegati\" ",
													  "where \"id_allegato\" = :id_allegato");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	//Table seduta_pratica_allegati
	public List<UUID> getCodiciPratica(UUID idAllegato, Long idSeduta) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = StringUtil.concateneString("select \"id_pratica\" from \"presentazione_istanza\".\"seduta_pratica_allegati\" ",
													  "where \"id_allegato\" = :id_allegato ",
													  "and \"id_seduta\" = :id_seduta");
		parameters.put("id_allegato", idAllegato);
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
	}
	
	public List<SimplePraticaDTO> getPraticaAllegati(UUID idAllegato, UUID idPratica) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = StringUtil.concateneString("select \"oggetto\", \"codice_pratica_appptr\" ",
													  "from \"presentazione_istanza\".\"pratica\" ",
													  "join \"presentazione_istanza\".\"seduta_pratica_allegati\" ",
													  "on \"pratica\".\"id\" = \"seduta_pratica_allegati\".\"id_pratica\" ",
													  "where \"id_allegato\" = :id_allegato ");
		if(idPratica != null)
			sql = StringUtil.concateneString(sql, "and \"pratica\".\"id\" = :id_pratica ");
		sql = StringUtil.concateneString(sql, "order by \"codice_pratica_appptr\" desc");
		parameters.put("id_allegato", idAllegato);
		parameters.put("id_pratica", idPratica);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.query(sql, parameters, new RowMapper<SimplePraticaDTO>()
		{
			@Override
			public SimplePraticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				SimplePraticaDTO result = null;
				if(rs != null)
				{
					result = new SimplePraticaDTO();
					result.setOggetto(rs.getString("oggetto"));
					result.setCodicePraticaAppptr(rs.getString("codice_pratica_appptr"));
				}
				return result;
			}
		});
	}
	
	public List<SimplePraticaDTO> getPraticaAllegati(UUID idAllegato) throws Exception
	{
		return getPraticaAllegati(idAllegato, null);
	}

	
	public Integer insertAllegatoSedutaPratica(AllegatiCommissioneLocaleDTO allegato, Long idSeduta) throws Exception
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"seduta_pratica_allegati\"",
												"(\"id_allegato\", \"id_pratica\", \"id_seduta\") values ");
		parameters.put("id_allegato", allegato.getId());
		parameters.put("id_seduta", idSeduta);
		sql = generateMultipleInsert(sql, allegato.getPraticheAssociate(), parameters, ":id_allegato, ", ", :id_seduta");
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql.toString(), parameters);
	}
	
	public Integer countPraticheEsaminate(Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("select count(distinct \"id_pratica\") from ",
													  "\"presentazione_istanza\".\"seduta_pratica_allegati\" ",
													  "where \"id_seduta\" = :id_seduta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Integer.class);
	}
	
	public Integer removeAllegatoSedutaPratica(UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"seduta_pratica_allegati\" ",
													  "where \"id_allegato\" = :id_allegato");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	public Integer countPerTipologia(Long idSeduta, TipoAllegatoSeduta tipologia) throws Exception
	{
		final String sql = StringUtil.concateneString("select count(*) from \"presentazione_istanza\".\"seduta_allegati\" ",
													  "join \"presentazione_istanza\".\"allegati_tipo_contenuto\" ",
													  "on \"seduta_allegati\".\"id_allegato\" = \"allegati_tipo_contenuto\".\"allegati_id\" ",
													  "where \"tipo_contenuto_id\" = :tipo_contenuto ",
													  "and \"id_seduta\" = :id_seduta"); 
		final Integer idTipologia = tipologia.getValue();
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo_contenuto", idTipologia);
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Integer.class);
	}
	
	public Boolean verificaAbilitazioneSeduta(Long idSeduta, Integer organizzazione) throws Exception
	{
		final String sql = StringUtil.concateneString("select count(*) > 0 from \"presentazione_istanza\".\"seduta_commissione\" ",
													  "where \"id\" = :id_seduta and (\"id_organizzazione\" = :organizzazione_loggata or ",
													  "\"id_ente_delegato\" = :organizzazione_loggata)");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id_seduta", idSeduta);
		parameters.put("organizzazione_loggata", organizzazione);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Boolean.class);
	}
	
	public List<Long> getIdCorrispondenze(Long idSeduta) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"id_corrispondenza\" ",
													  "from \"presentazione_istanza\".\"seduta_corrispondenza\" ",
													  "where \"id_seduta\" = :id_seduta");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql, parameters, Long.class);
	}
	
	public Integer insertSedutaCorrispondenza(Long idSeduta, Long idCorrispondenza) throws Exception
	{
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"seduta_corrispondenza\"",
													  "(\"id_seduta\", \"id_corrispondenza\") ",
													  "values(:id_seduta, :id_corrispondenza)");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_seduta", idSeduta);
		parameters.put("id_corrispondenza", idCorrispondenza);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
			
	private String generateMultipleInsert(String baseSql, List<UUID> ids, Map<String, Object> parameters, String startWith, String endWith)
	{
		StringBuilder sql = new StringBuilder(baseSql);
		Integer i = 1;
		String sep = "";
		startWith = startWith == null ? "" : startWith;
		endWith = endWith == null ? "" : endWith;
		for(UUID id: ids)
		{
			String key = "id_pratica_" + i++;
			sql.append(sep).append("(").append(startWith).append(":"+key).append(endWith + ")");
			parameters.put(key, id);
			sep = ", ";
		}
		return sql.toString();
	}
	
	public Boolean praticaEsaminata(UUID idPratica) throws Exception
	{
		final String sql = StringUtil.concateneString("select count(*) > 0 as mostra_seduta ",
													  "from \"presentazione_istanza\".\"seduta_commissione\" as sc ",
													  "join \"presentazione_istanza\".\"seduta_pratica_allegati\" as spa ",
													  "on spa.\"id_seduta\" = sc.\"id\" and spa.\"id_pratica\" = :id_pratica ",
													  "where sc.\"stato\" = 'CONCLUSA'");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", idPratica);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Boolean.class);
	}
	
	public Integer eliminaDaSeduteAttive(UUID idPratica) throws Exception
	{
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"seduta_pratica\" ",
													  "where \"id_pratica\" = :id_pratica and \"id_seduta\" in (",
													  "	select \"id\" from ",
													  " \"presentazione_istanza\".\"seduta_commissione\" ",
													  " join \"presentazione_istanza\".\"seduta_pratica\" ",
													  " on \"id\" = \"id_seduta\" ",
													  " where \"id_pratica\" = :id_pratica ",
													  " and \"stato\" != 'CONCLUSA')");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", idPratica);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.update(sql, parameters);
	}

}
