package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AllegatiRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.AllegatiTipoContenutoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.CorrispondenzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.DestinatarioRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.CorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.corrispondenza
 */
@Repository
public class CorrispondenzaRepository extends GenericCrudDao<CorrispondenzaDTO, CorrispondenzaSearch>
{

	private final CorrispondenzaRowMapper 	rowMapper 		= new CorrispondenzaRowMapper();
	private final DestinatarioRowMapper 	destrowMapper 	= new DestinatarioRowMapper();
	private final AllegatiRowMapper 		alleRowMapper 	= new AllegatiRowMapper();
	private final AllegatiTipoContenutoRowMapper alleTipoRowMapper = new AllegatiTipoContenutoRowMapper();
	
	/**
	 * select all method "corr".
	 */
	final String corrispondenzaFields = new StringBuilder()		
			.append(" \"corr\".\"id\"")
			.append(",\"corr\".\"id_eml_cmis\"")
			.append(",\"corr\".\"message_id\"")
			.append(",\"corr\".\"mittente_email\"")
			.append(",\"corr\".\"mittente_denominazione\"")
			.append(",\"corr\".\"mittente_username\"")
			.append(",\"corr\".\"mittente_ente\"")
			.append(",\"corr\".\"data_invio\"")
			.append(",\"corr\".\"oggetto\"")
			.append(",\"corr\".\"stato\"")
			.append(",\"corr\".\"comunicazione\"")
			.append(",\"corr\".\"bozza\"")
			.append(",\"corr\".\"text\"")
			.append(",\"corr\".\"codice_template\"")
			.append(",\"corr\".\"id_organizzazione_owner\"")
			.append(",\"corr\".\"tipo_organizzazione_owner\"")
			.append(",\"corr\".\"visibilita\"")
			.append(",\"corr\".\"riservata\" ")
			.append(",\"corr\".\"protocollo\" ")
			.append(",\"corr\".\"data_protocollo\" ")
			.append(",\"corr\".\"t_mailinviate_id\" ")
			.append(",\"corr\".\"t_pptr_mailinviate_id\" ")
			.toString();
	final String selectAll = new StringBuilder("select ")
			.append(corrispondenzaFields)
			.append(" from \"presentazione_istanza\".\"corrispondenza\" corr ").toString();

	@Override
	public List<CorrispondenzaDTO> select()
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
				.append(" from \"presentazione_istanza\".\"corrispondenza\"").toString();
		return super.queryForObjectTxRead(sql, Long.class);
	}

	/**
	 * find by pk method
	 */
	@Override
	public CorrispondenzaDTO find(CorrispondenzaDTO pk)
	{
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	/**
	 * search method
	 */
	@Override
	public PaginatedList<CorrispondenzaDTO> search(CorrispondenzaSearch search)
	{
		final List<Object> parameters = new ArrayList<Object>();
		String sep = " where ";
		final StringBuilder sql = new StringBuilder(selectAll);
		sql.append(" left join \"presentazione_istanza\".\"destinatario\" dest on \"dest\".\"id_corrispondenza\" = \"corr\".\"id\" ");
		sql.append(" left join \"presentazione_istanza\".\"fascicolo_corrispondenza\" fc on \"fc\".\"id_corrispondenza\" = \"corr\".\"id\" ");
		if (StringUtil.isNotEmpty(search.getId()))
		{
			sql.append(sep).append("lower(\"id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdEmlCmis()))
		{
			sql.append(sep).append("lower(\"id_eml_cmis\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdEmlCmis()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMessageId()))
		{
			sql.append(sep).append("lower(\"message_id\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMessageId()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMittenteEmail()))
		{
			sql.append(sep).append("lower(\"mittente_email\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMittenteEmail()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMittenteDenominazione()))
		{
			sql.append(sep).append("lower(\"mittente_denominazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMittenteDenominazione()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMittenteUsername()))
		{
			sql.append(sep).append("lower(\"mittente_username\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMittenteUsername()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMittenteEnte()))
		{
			sql.append(sep).append("lower(\"mittente_ente\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getMittenteEnte()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataInvio()))
		{
			sql.append(sep).append("lower(\"data_invio\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getDataInvio()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getOggetto()))
		{
			sql.append(sep).append("lower(\"oggetto\"::text) like ?");
			parameters.add(StringUtil.convertFullLike(search.getOggetto().toLowerCase()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getStato()))
		{
			sql.append(sep).append("lower(\"stato\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getStato()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getComunicazione()))
		{
			sql.append(sep).append("lower(\"comunicazione\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getComunicazione()));
			sep = " and ";
		}
		if (search.getBozza() != null)
		{
			sql.append(sep).append("\"bozza\" = ?");
			parameters.add(search.getBozza());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getText()))
		{
			sql.append(sep).append("lower(\"text\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getText()));
			sep = " and ";
		}
		//ha bisogno del join con fascicolo_corrispondenza
		if(search.getIdPratica() != null)
		{
			sql.append(sep)
			//.append("(select count(*) from \"presentazione_istanza\".\"fascicolo_corrispondenza\" where \"id_pratica\"=?) > 0"); 
			.append(" \"fc\".\"id_pratica\" =  ? ");
			parameters.add(search.getIdPratica());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getIdOrganizzazioneOwner()))
		{
			sql.append(sep).append("lower(\"id_organizzazione_owner\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getIdOrganizzazioneOwner()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTipoOrganizzazioneOwner()))
		{
			sql.append(sep).append("lower(\"tipo_organizzazione_owner\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getTipoOrganizzazioneOwner()).toLowerCase());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceTemplate()))
		{
			sql.append(sep).append("\"codice_template\" = ?");
			parameters.add(search.getCodiceTemplate());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getVisibilita()))
		{
			sql.append(sep).append(" (\"visibilita\" is null OR \"visibilita\" like ?)");
			parameters.add(StringUtil.convertLike("%"+search.getVisibilita()+"%"));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getRiservata()))
		{
			sql.append(sep).append("lower(\"riservata\"::text) like ?");
			parameters.add(StringUtil.convertLike(search.getRiservata()));
			sep = " and ";
		}
		if (search.getDataInvioDa()!=null)
		{
			sql.append(sep).append(" \"data_invio\"::date >= ? ");
			parameters.add(search.getDataInvioDa());
			sep = " and ";
		}
		if (search.getDataInvioA()!=null)
		{
			sql.append(sep).append(" \"data_invio\"::date <= ? ");
			parameters.add(search.getDataInvioA());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getMittente()))
		{
			sql.append(sep).append("( lower(\"mittente_email\"::text) like ? or  lower(\"mittente_denominazione\"::text) like ? )");
			parameters.add(StringUtil.convertLike(search.getMittente().toLowerCase()));
			parameters.add(StringUtil.convertLike(search.getMittente().toLowerCase()));
			sep = " and ";
		}
		//ha bisogno del join con destinatari
		if (StringUtil.isNotEmpty(search.getDestinatario()))
		{
			sql.append(sep).append("( lower(\"dest\".\"email\"::text) like ? or  lower(\"dest\".\"denominazione\"::text) like ? )");
			parameters.add(StringUtil.convertLike(search.getDestinatario().toLowerCase()));
			parameters.add(StringUtil.convertLike(search.getDestinatario().toLowerCase()));
			sep = " and ";
		}
		if (!search.isBozzeAutomatiche())
		{
			sql.append(sep).append(" not (\"bozza\" = true AND \"comunicazione\"=false )");
			sep = " and ";
		}
		if(search.getEscludiBozzeOrganizzazione()!=null) {
			sql.append(sep).append(" (\"bozza\" = false OR \"id_organizzazione_owner\"::text =  ? )");
			parameters.add(search.getEscludiBozzeOrganizzazione());
			sep = " and ";
		}
		if(search.getEscludiBozzeTipoOrganizzazione()!=null) {
			sql.append(sep).append(" (\"bozza\" = false OR \"tipo_organizzazione_owner\"::text =  ? )");
			parameters.add(search.getEscludiBozzeTipoOrganizzazione());
			sep = " and ";
		}
		//raggruppo per i campi di corrispondenza...
		sql
		.append(" group by ")
		.append(corrispondenzaFields);
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "id":
				sql.append(" order by \"id\" ").append(sortType);
				break;
			case "idEmlCmis":
				sql.append(" order by \"id_eml_cmis\" ").append(sortType);
				break;
			case "messageId":
				sql.append(" order by \"message_id\" ").append(sortType);
				break;
			case "mittenteEmail":
				sql.append(" order by \"mittente_email\" ").append(sortType);
				break;
			case "mittenteDenominazione":
				sql.append(" order by \"mittente_denominazione\" ").append(sortType);
				break;
			case "mittenteUsername":
				sql.append(" order by \"mittente_username\" ").append(sortType);
				break;
			case "mittenteEnte":
				sql.append(" order by \"mittente_ente\" ").append(sortType);
				break;
			case "dataInvio":
				sql.append(" order by \"data_invio\" ").append(sortType);
				break;
			case "oggetto":
				sql.append(" order by \"oggetto\" ").append(sortType);
				break;
			case "stato":
				sql.append(" order by \"stato\" ").append(sortType);
				break;
			case "comunicazione":
				sql.append(" order by \"comunicazione\" ").append(sortType);
				break;
			case "bozza":
				sql.append(" order by \"bozza\" ").append(sortType);
				break;
			case "text":
				sql.append(" order by \"text\" ").append(sortType);
				break;
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(CorrispondenzaDTO entity)
	{
		final String sql = new StringBuilder("insert into \"presentazione_istanza\".\"corrispondenza\"")
				.append("(\"id_eml_cmis\"").append(",\"message_id\"").append(",\"data_invio\"").append(",\"mittente_email\"")
				.append(",\"mittente_denominazione\"").append(",\"mittente_username\"").append(",\"mittente_ente\"")
				.append(",\"oggetto\"").append(",\"stato\"").append(",\"comunicazione\"").append(",\"bozza\"")
				.append(",\"text\"")
				.append(",\"codice_template\"")
				.append(",\"id_organizzazione_owner\"")
				.append(",\"tipo_organizzazione_owner\"")
				.append(",\"visibilita\"")
				.append(",\"riservata\"")
				.append(",\"protocollo\"")
				.append(",\"data_protocollo\"")
				.append(",\"mittente_ruolo\"")
				.append(",\"t_mailinviate_id\"")
				.append(",\"t_pptr_mailinviate_id\"")
				.append(") values ").append("(?").append(",?").append(",?").append(",?")
				.append(",?").append(",?").append(",?").append(",?").append(",?").append(",?").append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(",?")
				.append(")").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdEmlCmis());
		parameters.add(entity.getMessageId());
		parameters.add(entity.getDataInvio());
		parameters.add(entity.getMittenteEmail());
		parameters.add(entity.getMittenteDenominazione());
		parameters.add(entity.getMittenteUsername());
		parameters.add(entity.getMittenteEnte());
		parameters.add(entity.getOggetto());
		parameters.add(entity.getStato());
		parameters.add(entity.getComunicazione());
		parameters.add(entity.getBozza());
		parameters.add(entity.getTesto());
		parameters.add(entity.getCodiceTemplate());
		parameters.add(entity.getIdOrganizzazioneOwner());
		parameters.add(entity.getTipoOrganizzazioneOwner());
		parameters.add(entity.getVisibilita());
		parameters.add(entity.isRiservata());
		parameters.add(entity.getProtocollo());
		parameters.add(entity.getDataProtocollo());
		parameters.add(entity.getRuolo());
		parameters.add(entity.gettMailInviateId());
		parameters.add(entity.gettPptrMailInviateId());
		return super.insertAndGetAutoincrementValue(sql, parameters, "id");
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(CorrispondenzaDTO entity)
	{
		final String sql = new StringBuilder("update \"presentazione_istanza\".\"corrispondenza\"")
								.append(" set \"id_eml_cmis\" = ?").append(", \"message_id\" = ?").append(", \"mittente_email\" = ?")
								.append(", \"mittente_denominazione\" = ?").append(", \"mittente_username\" = ?")
								.append(", \"mittente_ente\" = ?").append(", \"data_invio\" = ?").append(", \"oggetto\" = ?")
								.append(", \"stato\" = ?").append(", \"comunicazione\" = ?").append(", \"bozza\" = ?")
								.append(", \"text\" = ?")
								.append(", \"codice_template\" = ?")
								//.append(", \"id_organizzazione_owner\" = ?")
								//.append(", \"tipo_organizzazione_owner\" = ?")
								.append(", \"visibilita\" = ?")
								.append(", \"riservata\" = ?")
								.append(", \"protocollo\" = ?")
								.append(", \"data_protocollo\" = ?")
								.append(",\"mittente_ruolo\"= ?")
								.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdEmlCmis());
		parameters.add(entity.getMessageId());
		parameters.add(entity.getMittenteEmail());
		parameters.add(entity.getMittenteDenominazione());
		parameters.add(entity.getMittenteUsername());
		parameters.add(entity.getMittenteEnte());
		parameters.add(entity.getDataInvio());
		parameters.add(entity.getOggetto());
		parameters.add(entity.getStato());
		parameters.add(entity.getComunicazione());
		parameters.add(true);
		parameters.add(entity.getTesto());
		parameters.add(entity.getCodiceTemplate());
		//parameters.add(entity.getIdOrganizzazioneOwner());
		//parameters.add(entity.getTipoOrganizzazioneOwner());
		parameters.add(entity.getVisibilita());
		parameters.add(entity.isRiservata());
		parameters.add(entity.getProtocollo());
		parameters.add(entity.getDataProtocollo());
		parameters.add(entity.getRuolo());
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}
	
	public int updateDraft(Long id,Timestamp dataInvio) throws Exception
	{
		String sql = new StringBuilder("update \"presentazione_istanza\".\"corrispondenza\" ")
							.append("set \"bozza\" = false, \"data_invio\" = ? ")
							.append("where \"id\" = ?")
							.toString();
		List<Object> parameters = new ArrayList<>();
		parameters.add(dataInvio);
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	public int updateProtocollo(Long id,String protocollo,Date dataProtocollo) throws Exception
	{
		String sql = new StringBuilder("update \"presentazione_istanza\".\"corrispondenza\" ")
							.append("set \"protocollo\" =  ? , \"data_protocollo\" = ? ")
							.append("where \"id\" = ?")
							.toString();
		List<Object> parameters = new ArrayList<>();
		parameters.add(protocollo);
		parameters.add(dataProtocollo);
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	public int updateTesto(Long id,String text) throws Exception
	{
		String sql = new StringBuilder("update \"presentazione_istanza\".\"corrispondenza\" ")
							.append("set \"text\" =  ? ")
							.append("where \"id\" = ?")
							.toString();
		List<Object> parameters = new ArrayList<>();
		parameters.add(text);
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	public int updateOggetto(Long id,String text) throws Exception
	{
		String sql = new StringBuilder("update \"presentazione_istanza\".\"corrispondenza\" ")
							.append("set \"oggetto\" =  ? ")
							.append("where \"id\" = ?")
							.toString();
		List<Object> parameters = new ArrayList<>();
		parameters.add(text);
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	public List<UUID> getIdAllegati(Long idCorrispondenza) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"id_allegato\" from \"presentazione_istanza\".\"allegato_corrispondenza\" ",
													  "where \"id_corrispondenza\" = :id_corrispondenza");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id_corrispondenza", idCorrispondenza);
		logger.info("Eseguo la query {} con i parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForList(sql, parameters, UUID.class);
	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(CorrispondenzaDTO entity)
	{
		final String sql = new StringBuilder("delete from \"presentazione_istanza\".\"corrispondenza\"")
								.append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return super.update(sql, parameters);
	}
	
	public boolean isInBozza(long id) throws Exception
	{
		String sql = "select count(*) from \"presentazione_istanza\".\"corrispondenza\" where \"id\"=? and \"bozza\" = false";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		return queryForObject(sql, Integer.class, parameters) == 0;
	}
	
	
	public DettaglioCorrispondenzaDTO getDettaglioCorrispondenza(long idCorrispondenza) {
		final String sql = new StringBuilder(selectAll).append(" where \"id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idCorrispondenza);
		CorrispondenzaDTO corrispondenza = super.queryForObjectTxRead(sql, this.rowMapper, parameters);
		 
		final String sqlDestinatatio = new StringBuilder().append("select * FROM destinatario where \"id_corrispondenza\" = ?").toString();
		final List<Object> parametersDest = new ArrayList<Object>();
		parametersDest.add(idCorrispondenza);
		List<DestinatarioDTO> destinatari = super.queryForListTxRead(sqlDestinatatio, this.destrowMapper, parametersDest);
		
		final String sqlAllegati = new StringBuilder().append("select * FROM allegati a "
									+ " INNER JOIN allegato_corrispondenza ac on a.id = ac.id_allegato"
									+ " where ac.id_corrispondenza = ?").toString();
		final List<Object> parametersAllegati = new ArrayList<Object>();
		parametersAllegati.add(idCorrispondenza);
		List<AllegatiDTO> allegatiInfo = super.queryForListTxRead(sqlAllegati, this.alleRowMapper, parametersAllegati);
		
		
		final String sqlAllegatiTipo = new StringBuilder().append("SELECT * FROM allegati_tipo_contenuto atc" + 
				" INNER JOIN allegati a on a.id = atc.allegati_id \r\n" + 
				" INNER JOIN allegato_corrispondenza ac on a.id = ac.id_allegato \r\n" + 
				" WHERE ac.id_corrispondenza = ?").toString();
		final List<Object> parametersAllegatiTipo = new ArrayList<Object>();
		parametersAllegatiTipo.add(idCorrispondenza);
		List<AllegatiTipoContenutoDTO> allegatitipo = super.queryForListTxRead(sqlAllegatiTipo, this.alleTipoRowMapper, parametersAllegatiTipo);

		
		DettaglioCorrispondenzaDTO dettaglio= new DettaglioCorrispondenzaDTO();
		dettaglio.setCorrispondenza(corrispondenza);
		dettaglio.setDestinatari(destinatari);
		dettaglio.setAllegatiInfo(allegatiInfo);
		dettaglio.setAllegati(allegatitipo);
		return dettaglio;
	}
	public int updateMessageId(long id,String messageid) throws Exception
	{
		String sql = new StringBuilder("update \"presentazione_istanza\".\"corrispondenza\" ")
							.append("set \"message_id\" = ?, \"data_invio\" = now() ")
							.append("where \"id\" = ?")
							.toString();
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(messageid);
		parameters.add(id);
		return super.update(sql, parameters);
	}
	
	public CorrispondenzaDTO getCorrispondenzaFromAllegato(UUID idAllegato) {
		final String sql = new StringBuilder(selectAll).append("INNER JOIN presentazione_istanza.allegato_corrispondenza ac "
				+ "on corr.id=ac.id_corrispondenza  where ac.id_allegato = ?").toString();
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(idAllegato);
		
		return super.queryForObjectTxRead(sql.toString(), this.rowMapper, parameters);
	}
	
}
