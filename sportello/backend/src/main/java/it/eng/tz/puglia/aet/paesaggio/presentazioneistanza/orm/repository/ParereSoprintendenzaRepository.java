package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ParereSoprintendenzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ParereSoprintendenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.parere_soprintendenza
 */
@Repository
public class ParereSoprintendenzaRepository extends GenericCrudDao<ParereSoprintendenzaDTO, ParereSoprintendenzaSearch>
{

	private final ParereSoprintendenzaRowMapper rowMapper = new ParereSoprintendenzaRowMapper();
	/**
	 * select all method
	 */
	final String selectAll = new StringBuilder("select").append(" \"id\"").append(", \"id_pratica\"")
			.append(", \"numero_protocollo\"").append(", \"nominativo_istruttore\"").append(", \"esito_parere\"")
			.append(", \"note\"").append(", \"username_utente_creazione\"").append(", \"dettaglio_prescrizione\"")
			.append(", \"data_inserimento\"").append(", \"organizzazione_creazione\"").append(", \"id_corrispondenza\"")
			.append(" from \"presentazione_istanza\".\"parere_soprintendenza\"").toString();

	@Override
	public List<ParereSoprintendenzaDTO> select()
	{
		return super.queryForListTxRead(selectAll, this.rowMapper);
	}
	
	private String generateWhereClause(String base, Map<String, Object> parameters, ParereSoprintendenzaSearch search)
	{
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(base);
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
		if (StringUtil.isNotEmpty(search.getNumeroProtocollo()))
		{
			sql.append(sep).append("\"numero_protocollo\" like :numero_protocollo");
			parameters.put("numero_protocollo", search.getNumeroProtocollo());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNominativoIstruttore()))
		{
			sql.append(sep).append("\"nominativo_istruttore\" like :nominativo_istruttore");
			parameters.put("nominativo_istruttore", search.getNominativoIstruttore());
			sep = " and ";
		}
		if (search.getEsitoParere() != null)
		{
			sql.append(sep).append("\"esito_parere\" like :esito_parere");
			parameters.put("esito_parere", search.getEsitoParere().name());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getNote()))
		{
			sql.append(sep).append("\"note\" like :note");
			parameters.put("note", StringUtil.convertLike(search.getNote()));
			sep = " and ";
		}
		if (search.getDataUtenteCreazione() != null)
		{
			sql.append(sep).append("\"data_utente_creazione\" = :data_creazione");
			parameters.put("data_creazione", search.getDataUtenteCreazione());
			sep = " and ";
		}
		if (search.getDataInserimento() != null)
		{
			sql.append(sep).append("\"data_inserimento\" = :data_inserimento");
			parameters.put("data_inserimento", search.getDataInserimento());
			sep = " and ";
		}
		if(search.getOrganizzazioneCreazione() != null)
		{
			sql.append(sep).append("\"tipo_gruppo_creazione\" = :organizzazione");
			parameters.put("organizzazione", search.getOrganizzazioneCreazione());
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
			case "numeroProtocollo":
				sql.append(" order by \"numero_protocollo\" ").append(sortType);
				break;
			case "nominativoIstruttore":
				sql.append(" order by \"nominativo_istruttore\" ").append(sortType);
				break;
			case "esitoParere":
				sql.append(" order by \"esito_parere\" ").append(sortType);
				break;
			case "note":
				sql.append(" order by \"note\" ").append(sortType);
				break;
			case "dataUtenteCreazione":
				sql.append(" order by \"data_utente_creazione\" ").append(sortType);
				break;
			case "dataInserimento":
				sql.append(" order by \"data_inserimento\" ").append(sortType);
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
				.append(" from \"presentazione_istanza\".\"parere_soprintendenza\"").toString();
		logger.info("Eseguo la query {}", sql);
		return super.queryForObjectTxRead(sql, Long.class);
	}
	
	public long count(ParereSoprintendenzaSearch search) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = generateWhereClause("select count(*) from \"presentazione_istanza\".\"parere_soprintendenza\"", parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public ParereSoprintendenzaDTO find(ParereSoprintendenzaDTO pk)
	{
		ParereSoprintendenzaSearch search = new ParereSoprintendenzaSearch();
		search.setId(pk.getId());
		search.setIdPratica(pk.getIdPratica());
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = generateWhereClause(selectAll, parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, rowMapper);
	}
	
	public ParereSoprintendenzaDTO find(Long id)
	{
		ParereSoprintendenzaDTO entity = new ParereSoprintendenzaDTO();
		entity.setId(id);
		return find(entity);
	}
	
	public ParereSoprintendenzaDTO find(UUID idPratica)
	{
		ParereSoprintendenzaDTO entity = new ParereSoprintendenzaDTO();
		entity.setIdPratica(idPratica);
		return find(entity);
	}
	
	/**
	 * search method
	 */
	@Override
	public PaginatedList<ParereSoprintendenzaDTO> search(ParereSoprintendenzaSearch search)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = generateWhereClause(selectAll, parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return super.paginatedList(sql, parameters, rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(ParereSoprintendenzaDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"parere_soprintendenza\"")
							.append("(\"id_pratica\"").append(",\"numero_protocollo\"")
							.append(",\"nominativo_istruttore\"").append(",\"esito_parere\"")
							.append(",\"note\"").append(",\"username_utente_creazione\"")
							.append(",\"organizzazione_creazione\"").append(",\"dettaglio_prescrizione\"")
							.append(") values ").append("(:id_pratica").append(",:numero_protocollo")
							.append(",:nominativo_istruttore").append(",:esito_parere").append(",:note")
							.append(",:username_utente_creazione").append(",:organizzazione")
							.append(", :dettaglio_prescrizione").append(") returning \"id\"").toString();
		final String esitoParere = entity.getEsitoParere() != null ? entity.getEsitoParere().name() : null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", entity.getIdPratica());
		parameters.put("numero_protocollo", entity.getNumeroProtocollo());
		parameters.put("nominativo_istruttore", entity.getNominativoIstruttore());
		parameters.put("esito_parere", esitoParere);
		parameters.put("note", entity.getNote());
		parameters.put("username_utente_creazione", entity.getUsernameUtenteCreazione());
		parameters.put("organizzazione", entity.getOrganizzazioneCreazione());
		parameters.put("dettaglio_prescrizione", entity.getDettaglioPrescrizione());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateWrite.queryForObject(sql, parameters, Long.class);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(ParereSoprintendenzaDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"parere_soprintendenza\"")
								.append(" set \"id_pratica\" = :id_pratica")
								.append(", \"numero_protocollo\" = :numero_protocollo")
								.append(", \"nominativo_istruttore\" = :nominativo_istruttore")
								.append(", \"esito_parere\" = :esito_parere")
								.append(", \"note\" = :note")
								.append(", \"dettaglio_prescrizione\" = :dettaglio_prescrizione")
								.append(" where \"id\" = :id").toString();
		final String esitoParere = entity.getEsitoParere() != null ? entity.getEsitoParere().name() : null;
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_pratica", entity.getIdPratica());
		parameters.put("numero_protocollo", entity.getNumeroProtocollo());
		parameters.put("nominativo_istruttore", entity.getNominativoIstruttore());
		parameters.put("esito_parere", esitoParere);
		parameters.put("note", entity.getNote());
		parameters.put("dettaglio_prescrizione", entity.getDettaglioPrescrizione());
		parameters.put("id", entity.getId());
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(ParereSoprintendenzaDTO entity)
	{
		ParereSoprintendenzaSearch search = new ParereSoprintendenzaSearch();
		search.setId(entity.getId());
		search.setIdPratica(entity.getIdPratica());
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = generateWhereClause("delete from \"presentazione_istanza\".\"parere_soprintendenza\"", parameters, search);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	public int delete(Long id)
	{
		ParereSoprintendenzaDTO entity = new ParereSoprintendenzaDTO();
		entity.setId(id);
		return delete(entity);
	}
	
	public int delete(UUID idPratica)
	{
		ParereSoprintendenzaDTO entity = new ParereSoprintendenzaDTO();
		entity.setIdPratica(idPratica);
		return delete(entity);
	}
	
	public int deleteAllegatoParere(UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"parere_soprintendenza_allegato\" ",
													  "where \"parere_soprintendenza_allegato\".\"id_allegato\" = :id_allegato");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_allegato", idAllegato);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	public List<UUID> getAllegatiID(Long idParere) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"id_allegato\" from ",
													  "\"presentazione_istanza\".\"parere_soprintendenza_allegato\" ",
													  "where \"id_parere\" = :id_parere");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_parere", idParere);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
	}
	
	public Integer insertParereAllegato(Long idParere, UUID idAllegato) throws Exception
	{
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"parere_soprintendenza_allegato\"",
													  "(\"id_parere\", \"id_allegato\") values",
													  "(:id_parere, :id_allegato)");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_parere", idParere);
		parameters.put("id_allegato", idAllegato);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedUpdate(sql, parameters);
	}
	
	public void insertIdCorrispondenza(Long idParere, Long idCorrispondenza) throws Exception
	{
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"parere_soprintendenza\" ",
													  "set \"id_corrispondenza\" = :id_corrispondenza ",
													  "where \"id\" = :id_parere");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_corrispondenza", idCorrispondenza);
		parameters.put("id_parere", idParere);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		namedUpdate(sql, parameters);
	}

}
