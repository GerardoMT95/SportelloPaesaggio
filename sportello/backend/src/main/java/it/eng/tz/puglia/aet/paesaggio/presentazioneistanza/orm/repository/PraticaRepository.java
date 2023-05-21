package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.Pratica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.TabelleAssegnamentoFascicoliRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.FasiAssegnazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.IstrPraticaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PraticaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table presentazione_istanza.pratica
 * Nasce come repository autogenerato, ma è stato fortemente customizzato. (acolaianni 09.10.2020)
 */
@Repository
public class PraticaRepository extends GenericCrudDao<PraticaDTO, PraticaSearch>
{
	@Autowired RemoteSchemaService remoteSchemaService;
	@Autowired GruppiRuoliService gruppiRuoliService;
	@Autowired UserUtil userUtil;
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedCommonJdbcTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(PraticaRepository.class);
	
	private final PraticaRowMapper rowMapper = new PraticaRowMapper();
	private final String selectField=StringUtil.concateneString(
			"\"pratica\".\"id\"", ",\"codice_pratica_appptr\"",
			",\"ente_delegato\"", ",\"in_sanatoria\"", ",\"tipo_procedimento\"", ",\"stato\"", ",\"data_creazione\"",
			",\"oggetto\"", ",\"user_id\"", ",\"data_modifica\"", ",\"privacy_id\"", ",\"validazione_istanza\"",
			",\"validazione_scheda_tecnica\"", ",\"validazione_allegati\"", ",\"tipo_selezione_localizzazione\"",
			",\"has_shape\"" , ",\"numero_protocollo\"", ",\"data_protocollo\"",",\"data_presentazione\"" ,
			",\"data_trasmissione_provvedimento_finale\"", ", \"ultimo_stato_valido\"", ",\"stato_seduta_commissione\"",
			",\"stato_relazione_ente\"", ", \"stato_parere_soprintendenza\"",",\"data_inizio_lavorazione\"", 
			",\"data_trasmissione_verbale\"", ",\"data_trasmissione_relazione\"",
			",\"data_trasmissione_parere\" ",
			",\"codice_trasmissione\" ",
			",\"t_pratica_id\" "
			,",\"ruolo_pratica\" "
			,",\"owner\" "
			,",\"codice_segreto\" "
			,",\"user_updated\" "
			,",\"esonero_oneri\" "
			,",\"esonero_bollo\" "
			,",\"validazione_richiedente\" "
			);
	private final String selectAll = StringUtil.concateneString("select ",selectField);
	
	
	//questa from è utilizzata per le ricerche in ambiente istruttoria....
	private final String fromSearchIstr=StringUtil.concateneString(
			" FROM ",
			"\"presentazione_istanza\".\"pratica\" left join ",
			"\"presentazione_istanza\".\"localizzazione_intervento\" ",
			"on \"pratica_id\" = \"id\"",
			//aggiunta join con vista referenti per permettere ai referenti di vedere le pratiche (in lettura) 
			" left join presentazione_istanza.v_referente_fascicolo vrf on vrf.id=pratica.id");
//			," left join \"presentazione_istanza\".\"assegnamento_fascicolo\" as af",
//			" ON \"pratica\".\"id\" = \"af\".\"id_fascicolo\""); //l'id_organizzazione dell'assegnazione fascicolo è nella where
	
	/**
	 * select senza alcun filtro.... non lo implementiamo... poi se dovesse service ci pensiamo...(acolaianni)
	 */
	@Override
	public List<PraticaDTO> select()
	{
		throw new RuntimeException("SELECT NON IMPLEMENTATO.....");
//		StringBuilder sql = new StringBuilder(
//				StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\""));
//		return super.queryForListTxRead(sql.toString(), this.rowMapper);
	}

	/**
	 * count all method
	 */
	@Override
	public long count()
	{
		StringBuilder sql = new StringBuilder(StringUtil.concateneString(
				"select count(*) ",
				" from \"presentazione_istanza\".\"pratica\"" ));
		return super.queryForObjectTxRead(sql.toString(), Long.class);
	}

	@Override
	public PraticaDTO find(PraticaDTO pk)
	{
		return findTxWrite(pk.getId());
	}

	/** 
	 * nessun controllo di accesso...
	 * @author acolaianni
	 *
	 * @param id
	 * @return
	 */
	public PraticaDTO find(UUID id)
	{
		final String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\"",
				" where \"id\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	
	public PraticaDTO findTxWrite(UUID id)
	{
		final String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\"",
				" where \"id\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		return super.queryForObject(sql, this.rowMapper, parameters);
	}

	public PraticaDTO findByCodice(String codicePraticaApptr)
	{
		final String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\"",
				" where \"codice_pratica_appptr\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(codicePraticaApptr);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public String findIdByCodice(String codicePraticaApptr)
	{
		final String sql = StringUtil.concateneString("select id", " from \"presentazione_istanza\".\"pratica\"",
				" where \"codice_pratica_appptr\" = :codice");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("codice", codicePraticaApptr);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
	}
	
	public String findCodiceById(String idPratica)
	{
		final String sql = StringUtil.concateneString("select codice_pratica_appptr", " from \"presentazione_istanza\".\"pratica\"",
				" where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", UUID.fromString(idPratica));
		return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
	}
	
	public String findEnteDelegatoById(String idPratica)
	{
		final String sql = StringUtil.concateneString("select ente_delegato", " from \"presentazione_istanza\".\"pratica\"",
				" where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", UUID.fromString(idPratica));
		return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
	}
	
	
	public String getOwnerById(final String idPratica)
	{
		final String sql = StringUtil.concateneString("select owner", " from \"presentazione_istanza\".\"pratica\"",
				" where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", UUID.fromString(idPratica));
		return namedJdbcTemplateRead.queryForObject(sql, parameters, String.class);
	}
	
	
	public PraticaDTO findByCodiceTrasmissione(String codicePraticaApptr)
	{
		final String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\"",
				" where \"codice_trasmissione\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(codicePraticaApptr);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}
	
	public PraticaDTO findByTPraticaId(Long tPraticaId)
	{
		final String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\"",
				" where \"t_pratica_id\" = ? limit 1");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(tPraticaId);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	public PraticaDTO findByCodice(PraticaDTO pk)
	{
		return this.findByCodice(pk.getCodicePraticaAppptr());
	}
	
	private String generateWhereClause(PraticaSearch search, Map<String, Object> parameters, String baseSql)
	{
		String sep = " where ";
		StringBuilder sql = new StringBuilder(baseSql);
		if (search.getId() != null)
		{
			sql.append(sep).append("\"pratica\".\"id\" = :id");
			parameters.put("id", search.getId());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodicePraticaAppptr()))
		{
			sql.append(sep).append("\"codice_pratica_appptr\" = :codice");
			parameters.put("codice", search.getCodicePraticaAppptr());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getEnteDelegato()))
		{
			sql.append(sep).append("\"ente_delegato\" = :ente_delegato");
			parameters.put("ente_delegato", search.getEnteDelegato());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getInSanatoria()))
		{
			sql.append(sep).append("\"in_sanatoria\"= :in_sanatoria");
			parameters.put("in_sanatoria", search.getInSanatoria());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getTipoProcedimento()))
		{
			try
			{
				int tipoProcedimento = Integer.valueOf(search.getTipoProcedimento());
				sql.append(sep).append("\"tipo_procedimento\"= :tipo_procedimento");
				parameters.put("tipo_procedimento", tipoProcedimento);
				sep = " and ";
			} catch (Exception e)
			{
				logger.error("Errore nella conversione del tipoProcedimento nella search ", e);
			}
		}
		if (StringUtil.isNotEmpty(search.getStato()))
		{
			sql.append(sep).append("\"stato\" = :stato");
			parameters.put("stato", search.getStato());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getDataCreazione()))
		{
			sql.append(sep).append("\"data_creazione\" = :data_creazione");
			parameters.put("data_creazione", search.getDataCreazione());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getOggetto()))
		{
			sql.append(sep).append("lower(\"oggetto\"::text) like :oggetto");
			parameters.put("oggetto", StringUtil.convertLike(search.getOggetto().toLowerCase()));
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getCodiceFiscale()) 
			|| StringUtil.isNotEmpty(search.getUserId())
			|| StringUtil.isNotEmpty(search.getOwner())) {
			String sep2="";
			sql.append(sep).append("(");
			//Nel caso sia presente il codice fiscale ci sarà la where in or tra codice fiscale del referente e
			//user id della pratica. Se c'è solo lo user id ci sarà la where singola
			if (StringUtil.isNotEmpty(search.getCodiceFiscale())) {
				sql.append("vrf.codice_fiscale=:codice_fiscale");
				parameters.put("codice_fiscale", search.getCodiceFiscale());
				sep2=" OR ";
			}
			if (StringUtil.isNotEmpty(search.getUserId()))
			{
				sql.append(sep2).append("\"user_id\"= :user_id");
				parameters.put("user_id", search.getUserId());
				sep = " and ";
			}
			if (StringUtil.isNotEmpty(search.getOwner()))
			{
				sql.append(sep2).append("\"owner\"= :owner");
				parameters.put("owner", search.getOwner());
				sep = " and ";
			}
			sql.append(")");
		}
		if (StringUtil.isNotEmpty(search.getDataModifica()))
		{
			sql.append(sep).append("lower(\"data_modifica\"::text) like :data_modifica");
			parameters.put("data_modifica", search.getDataModifica());
			sep = " and ";
		}
		if (search.getInIstruttoria() != null)
		{
			List<String> statiPresentazione = AttivitaDaEspletare.getStatiPresentazioneIstanzaName();
			parameters.put("stati", statiPresentazione);
			sql.append(sep).append("\"pratica\".\"stato\"").append(search.getInIstruttoria() ? " not in " : " in ").append("(:stati)");
			sep = " and ";
		}
		if(search.getIdSeduta() != null)
		{
			sql.append(sep).append("\"pratica\".\"id\" in (select \"id_pratica\"")
						   .append(" from \"presentazione_istanza\".\"seduta_pratica\"")
						   .append(" where \"id_seduta\" = :id_seduta)");
			parameters.put("id_seduta", search.getIdSeduta());
			sep = " and ";
			if(search.getEsaminato() != null && search.getEsaminato().equals(true))
			{
				sql.append(sep).append("\"pratica\".\"id\" in (select \"id_pratica\"")
				   .append(" from \"presentazione_istanza\".\"seduta_pratica_allegati\"")
				   .append(" where \"id_seduta\" = :id_seduta)");
			}
		}
		if(search.getEditable() != null && search.getEditable().equals(true))
		{
			List<String> nonEditabili = AttivitaDaEspletare.getStatiNonEditabiliName();
			sql.append(sep).append("\"pratica\".\"stato\" not in (:non_editabili)");
			parameters.put("non_editabili", nonEditabili);
			sep = " and ";
		}
		if(search.getEscludiAccertamento() != null && search.getEscludiAccertamento().equals(true))
		{
			final String selectIdTipiAccertamento=" select id from \"presentazione_istanza\".\"tipo_procedimento\" where accertamento = true ";
			//List<Integer> tipologieEscluse = Arrays.asList(3, 4, 5); 
			sql.append(sep).append("\"pratica\".\"tipo_procedimento\" not in ")
			.append("(")
			.append(selectIdTipiAccertamento)
			.append(")");
			//parameters.put("tipologieEscluse", tipologieEscluse);
			sep = " and ";
		}
		if(search.getPraticheSeduta() != null && search.getPraticheSeduta().equals(true))
		{
			sql.append(sep).append("(select case when ")
			   			   .append("count(*) = 0 then ',1,2,' ")
			   			   .append("else \"tipo_pratica_seduta\" end ")
			   			   .append("from \"presentazione_istanza\".\"configurazioni_ente\" as conf ")
			   			   .append("where \"id_organizzazione\"::text = \"pratica\".\"ente_delegato\" ")
			   			   .append("group by \"tipo_pratica_seduta\") ")
			   			   .append("like concat('%', \"tipo_procedimento\", '%')");
			sep = " and ";
		}
		if(search.getRangeRicercaDa() != null && search.getRangeRicercaA() != null)
		{
			sql.append(sep).append("\"pratica\".\"data_inizio_lavorazione\" between :rangeDa and :rangeA");
			parameters.put("rangeDa", search.getRangeRicercaDa());
			parameters.put("rangeA", search.getRangeRicercaA());
			sep = " and ";
		}
		if(search.isRicercaPubblica()) {
			List<String> pubblici = Arrays.asList(AttivitaDaEspletare.TRANSMITTED.name());
			sql.append(sep).append("\"pratica\".\"stato\" not in (:pubblici)");
			parameters.put("pubblici", pubblici);
			sep = " and ";
		}
		if(search.getStatiAmmessi()!=null && search.getStatiAmmessi().size()>0) {
			List<String> statiAmmessi = search.getStatiAmmessi().stream().map(el->el.name()).collect(Collectors.toList());
			sql.append(sep).append("\"pratica\".\"stato\" in (:stati_ammessi)");
			parameters.put("stati_ammessi", statiAmmessi);
			sep = " and ";
		}
		//deve essere in from anche la localizzazione_intervento
		if(search.getComuniIntervento()!=null && search.getComuniIntervento().size()>0) {
			sql.append(sep)
			.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza) ");
			parameters.put("entiDiCompetenza",search.getComuniIntervento());
			sep = " and ";
		}
		if(StringUtil.isNotEmpty(search.getUserAssegnatario())) {
			if(search.getOrganizzazioneAutenticata()==null) {
				//errore concettuale.... vanno sempre assieme
				throw new RuntimeException("PraticaSearch incongruente... userAssegnatario pieno e OrganizzazioneAutenticata vuota");
			}	
		   sql.append(sep)
			.append(" \"pratica\".\"id\" in (")
			.append(" select id_fascicolo ")
			.append(" from v_assegnazione v_af where  ")
			.append(" (v_af.username_utente  = :userAssegnatario or v_af.username_rup = :userAssegnatario )") //anche se sono il rup della pratica dovrei avere la stessa visibilità
			.append(" and v_af.id_organizzazione = :organizzazioneAutenticata ")
			.append(" and v_af.fase = :faseIstruttoria )");
			parameters.put("userAssegnatario",search.getUserAssegnatario());
			parameters.put("organizzazioneAutenticata",search.getOrganizzazioneAutenticata());
			parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
			sep = " and ";
		}
		if (search.getEntiDelegatiRiferimento()!=null && search.getEntiDelegatiRiferimento().size()>0)
		{
			sql.append(sep)
			.append(" ente_delegato  IN (:entiDelegatiRiferimento) ");
			parameters.put("entiDelegatiRiferimento",search.getEntiDelegatiRiferimento().stream().map(e->e+"").collect(Collectors.toList()));
			sep = " and ";
		}
		//deve essere in from anche la assegnazione_fascicolo as af
		if (search.getNonAssegnate() != null)
		{
			if(search.getNonAssegnate())
			{
				if(search.getOrganizzazioneAutenticata()==null) {
					//errore concettuale.... vanno sempre assieme
					throw new RuntimeException("PraticaSearch incongruente... nonAssegnate pieno e OrganizzazioneAutenticata vuota");
				}
				sql.append(sep)
				.append(" \"pratica\".\"id\" NOT in (")
				.append(" select id_fascicolo ")
				.append(" from v_assegnazione v_af where  ")
				.append(" v_af.id_organizzazione = :organizzazioneAutenticata ")
				.append(" and v_af.fase = :faseIstruttoria ")
				.append(" and (NOT v_af.username_utente is null OR  v_af.username_utente <> '') )");
				parameters.put("organizzazioneAutenticata",search.getOrganizzazioneAutenticata());
				parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
				sep = " and ";
			}
			else
			{
				if(search.getOrganizzazioneAutenticata()==null) {
					//errore concettuale.... vanno sempre assieme
					throw new RuntimeException("PraticaSearch incongruente... nonAssegnate pieno e OrganizzazioneAutenticata vuota");
				}
				sql.append(sep)
				.append(" \"pratica\".\"id\" in (")
				.append(" select id_fascicolo ")
				.append(" from v_assegnazione v_af where  ")
				.append(" v_af.id_organizzazione = :organizzazioneAutenticata ")
				.append(" and v_af.fase = :faseIstruttoria ")
				.append(" and (NOT v_af.username_utente is null OR  v_af.username_utente <> '') )");
				parameters.put("organizzazioneAutenticata",search.getOrganizzazioneAutenticata());
				parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
				sep = " and ";
			}
			
		}
		if(search.getComune() != null)
		{
			sql.append(sep)
			   .append("(select count(*) ")
			   .append("from \"presentazione_istanza\".\"localizzazione_intervento\" ")
			   .append("where \"pratica_id\" = \"pratica\".\"id\" and ")
			   .append("\"comune_id\" = :comune_id) > 0");
			parameters.put("comune_id", search.getComune());
			sep = " and ";
		}
		if (StringUtil.isNotEmpty(search.getLikeCodicePraticaAppptr()))
		{
			sql.append(sep).append("\"codice_pratica_appptr\" ILIKE :likeCodice");
			parameters.put("likeCodice", StringUtil.convertFullLike(search.getLikeCodicePraticaAppptr()));
			sep = " and ";
		}
		if (ListUtil.isNotEmpty(search.getCodiciTrasmissione()))
		{
			sql.append(sep).append("\"codice_trasmissione\" in (:codiciTrasmissione) ");
			parameters.put("codiciTrasmissione", search.getCodiciTrasmissione());
			sep = " and ";
		}
		return sql.toString();
	}
	
	private String generateOrderByClause(PraticaSearch search, String baseSql)
	{
		StringBuilder sql = new StringBuilder(baseSql);
		
		if (StringUtil.isNotEmpty(search.getSortBy()))
		{
			String sortType = search.getSortType() == null ? "" : StringUtil.concateneString(" ", search.getSortType());
			switch (search.getSortBy())
			{
			case "id":
				sql.append(" ORDER by \"id\" ").append(sortType);
				break;
			case "codicePraticaAppptr":
				sql.append(" ORDER by \"codice_pratica_appptr\" ").append(sortType);
				break;
			case "enteDelegato":
				sql.append(" ORDER by \"ente_delegato\" ").append(sortType);
				break;
			case "inSanatoria":
				sql.append(" ORDER by \"in_sanatoria\" ").append(sortType);
				break;
			case "tipoProcedimento":
				sql.append(" ORDER by \"tipo_procedimento\" ").append(sortType);
				break;
			case "stato":
				sql.append(" ORDER by \"stato\" ").append(sortType);
				break;
			case "dataCreazione":
				sql.append(" ORDER by \"data_creazione\" ").append(sortType);
				break;
			case "oggetto":
				sql.append(" ORDER by \"oggetto\" ").append(sortType);
				break;
			case "userId":
				sql.append(" ORDER by \"user_id\" ").append(sortType);
				break;
			case "dataModifica":
				sql.append(" ORDER by \"data_modifica\" ").append(sortType);
				break;
			case "attivitaDaEspletare":
				sql.append(" ORDER by \"stato\" ").append(sortType);
				break;	
			default:
				logger.warn(StringUtil.concateneString("value ", search.getSortBy(), " not valid for sort by"));
				break;
			}
		}else {
		 sql.append(" ORDER by \"data_creazione\" desc , \"id\" desc");
		}
		return sql.toString();
	}
	
	
	/**
	* NON LOGGATO																Stati consentiti: 
	* RICHIEDENTI non viene applicato questo filtro
	* ADMIN 	  non viene applicato questo filtro
	* REG_  	  vede tutto, 													Stati consentiti: 
	* ETI_  	  vede in base alla competenza territoriale, 					Stati consentiti: 
	* SBAP_ 	  vede in base alla competenza territoriale, 					Stati consentiti: 
	* ED_   	  vede solo le pratiche associate direttamente al suo gruppo,	Stati consentiti: 
    * CL_   	  vede solo le pratiche associate direttamente al relativo ED_, Stati consentiti: 
	*/
	@Deprecated
	private String estensioneWhereClause(boolean ricercaPubblica, Map<String, Object> parameters, String baseSql) {

		StringBuilder sql = new StringBuilder(baseSql);
		
		List<String> statiAmmessi = new ArrayList<String>();
		
		if (ricercaPubblica==false) {  // cioè nel caso di utente loggato
			try {
				Gruppi gruppoLoggato = userUtil.getGruppo();

				if (gruppoLoggato==Gruppi.ADMIN) {
				}
				else if (gruppoLoggato==Gruppi.RICHIEDENTI) {
					//il richiedente dovrebbe vedere sempre tutto.... al massimo mascheriamo lo stato effettivo quando è nella fase di istruttoria (acolaianni 01.10.2020)
//					sql .append(" AND ")
//						.append(Pratica.stato.getCompleteName()+" IN (:statiAmmessi)");
//					statiAmmessi.add(AttivitaDaEspletare.COMPILA_DOMANDA.name());					// TODO: vedere quali sono
//					statiAmmessi.add(AttivitaDaEspletare.GENERA_STAMPA_DOMANDA.name());
//					statiAmmessi.add(AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI.name());
//					statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
//					parameters.put("statiAmmessi", statiAmmessi);
				}
//				else if (gruppoLoggato==Gruppi.REG_) {
//					sql .append(" AND ")
//						.append(Pratica.stato.getCompleteName()+" IN (:statiAmmessi)");
//					statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());	// TODO: vedere quali sono
//					statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
//					statiAmmessi.add(AttivitaDaEspletare.NON_ASSEGNATA.name());
//					statiAmmessi.add(AttivitaDaEspletare.IN_PROTOCOLLAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
//					statiAmmessi.add(AttivitaDaEspletare.IN_LAVORAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.RELAZIONE_DA_TRASMETTERE.name());
//					statiAmmessi.add(AttivitaDaEspletare.ATTESA_PARERE_SOPRINTENDENZA.name());
//					statiAmmessi.add(AttivitaDaEspletare.IN_TRASMISSIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.SOSPESA.name());
//					statiAmmessi.add(AttivitaDaEspletare.ARCHIVIATA.name());
//					statiAmmessi.add(AttivitaDaEspletare.TRANSMITTED.name());
//					parameters.put("statiAmmessi", statiAmmessi);
//				}
				else if (gruppoLoggato==Gruppi.ETI_) {
					sql .append(" AND ( ")
						.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza)")
						.append(" and ") 
						.append(Pratica					.stato    .getCompleteName()+" IN (:statiAmmessi)")
						.append(" )");
					List<Integer> entiDiCompetenza = gruppiRuoliService.entiForGruppoUtenteLoggato();
					parameters.put("entiDiCompetenza", (entiDiCompetenza!=null && entiDiCompetenza.isEmpty()) ? null : entiDiCompetenza);
					statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());	// TODO: vedere quali sono
//					statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.NON_ASSEGNATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.IN_PROTOCOLLAZIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_LAVORAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.RELAZIONE_DA_TRASMETTERE.name());
//					statiAmmessi.add(AttivitaDaEspletare.ATTESA_PARERE_SOPRINTENDENZA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_TRASMISSIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.SOSPESA.name());
					statiAmmessi.add(AttivitaDaEspletare.ARCHIVIATA.name());
					statiAmmessi.add(AttivitaDaEspletare.TRANSMITTED.name());
					parameters.put("statiAmmessi", statiAmmessi);
				}	 
				else if (gruppoLoggato==Gruppi.SBAP_) {
					sql .append(" AND ( ")
						.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza)")
						.append(" and ") 
						.append(Pratica					.stato    .getCompleteName()+" IN (:statiAmmessi)")
						.append(" )");
					List<Integer> entiDiCompetenza = gruppiRuoliService.entiForGruppoUtenteLoggato();
					parameters.put("entiDiCompetenza", (entiDiCompetenza!=null && entiDiCompetenza.isEmpty()) ? null : entiDiCompetenza);
					statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());	// TODO: vedere quali sono
//					statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.NON_ASSEGNATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.IN_PROTOCOLLAZIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_LAVORAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.RELAZIONE_DA_TRASMETTERE.name());
//					statiAmmessi.add(AttivitaDaEspletare.ATTESA_PARERE_SOPRINTENDENZA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_TRASMISSIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.SOSPESA.name());
					statiAmmessi.add(AttivitaDaEspletare.ARCHIVIATA.name());
					statiAmmessi.add(AttivitaDaEspletare.TRANSMITTED.name());
					parameters.put("statiAmmessi", statiAmmessi);
				}
				else if (gruppoLoggato == Gruppi.ED_ || gruppoLoggato == Gruppi.REG_) {
					sql .append(" AND ( ")
						.append(Pratica.ente_delegato.getCompleteName()+" = :organizzazioneLoggato")
						.append(" and ") 
						.append(Pratica.stato  		 .getCompleteName()+" IN (:statiAmmessi)")
						.append(" )");
					parameters.put("organizzazioneLoggato", Integer.toString(userUtil.getIntegerId())); //userUtil.getGruppo_Id()
					statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());	// TODO: vedere quali sono
//					statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.NON_ASSEGNATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.IN_PROTOCOLLAZIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_LAVORAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.RELAZIONE_DA_TRASMETTERE.name());
//					statiAmmessi.add(AttivitaDaEspletare.ATTESA_PARERE_SOPRINTENDENZA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_TRASMISSIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.SOSPESA.name());
					statiAmmessi.add(AttivitaDaEspletare.ARCHIVIATA.name());
					statiAmmessi.add(AttivitaDaEspletare.TRANSMITTED.name());
					parameters.put("statiAmmessi", statiAmmessi);
				}	 
				else if (gruppoLoggato==Gruppi.CL_) 
				{
					sql .append(" AND ( ")
					.append(Pratica.ente_delegato.getCompleteName()+" in (:entiRiferimento)")
					.append(" and ") 
					.append(Pratica.stato  		 .getCompleteName()+" IN (:statiAmmessi)")
					.append(" )");
					List<Integer> entiRiferimento = remoteSchemaService.getPaesaggiEntiForCommissione(userUtil.getIntegerId());
					//sono sicuro che entiRiferimento non sia null altrimenti sarebbe andato in errore al "checkGruppo"
					parameters.put("entiRiferimento", entiRiferimento.stream().map(m -> m.toString()).collect(Collectors.toList()));
					// TODO: vedere quali sono
					statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());	
//					statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.NON_ASSEGNATA.name());
					//statiAmmessi.add(AttivitaDaEspletare.IN_PROTOCOLLAZIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_LAVORAZIONE.name());
//					statiAmmessi.add(AttivitaDaEspletare.RELAZIONE_DA_TRASMETTERE.name());
//					statiAmmessi.add(AttivitaDaEspletare.ATTESA_PARERE_SOPRINTENDENZA.name());
					statiAmmessi.add(AttivitaDaEspletare.IN_TRASMISSIONE.name());
					statiAmmessi.add(AttivitaDaEspletare.SOSPESA.name());
					statiAmmessi.add(AttivitaDaEspletare.ARCHIVIATA.name());
					statiAmmessi.add(AttivitaDaEspletare.TRANSMITTED.name());
					parameters.put("statiAmmessi", statiAmmessi);
				}	 
				else {
					throw new Exception("Gruppo "+gruppoLoggato+" non riconosciuto!");	
				}
					if (userUtil.getRuolo()==Ruoli.FUNZIONARIO && 
							(userUtil.getGruppo()==Gruppi.ED_ || userUtil.getGruppo()==Gruppi.ETI_ || userUtil.getGruppo()==Gruppi.SBAP_)
							&& baseSql.contains(AssegnamentoFascicolo.getTableName())) {
						sql .append(" AND ")
							.append(" \"af\"."+AssegnamentoFascicolo.username_utente.name() + " = :usernameRupFunzionario");
						parameters.put("usernameRupFunzionario", SecurityUtil.getUsername());
					}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		else {	// cioè nel caso di utente NON loggato
			sql .append(" AND ")
				.append(Pratica.stato.getCompleteName()+" IN (:statiAmmessi)");
			statiAmmessi.add(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());	// TODO: vedere quali sono
//			statiAmmessi.add(AttivitaDaEspletare.ISTANZA_PRESENTATA.name());
			//statiAmmessi.add(AttivitaDaEspletare.NON_ASSEGNATA.name());
			//statiAmmessi.add(AttivitaDaEspletare.IN_PROTOCOLLAZIONE.name());
			statiAmmessi.add(AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
			statiAmmessi.add(AttivitaDaEspletare.IN_LAVORAZIONE.name());
//			statiAmmessi.add(AttivitaDaEspletare.RELAZIONE_DA_TRASMETTERE.name());
//			statiAmmessi.add(AttivitaDaEspletare.ATTESA_PARERE_SOPRINTENDENZA.name());
			statiAmmessi.add(AttivitaDaEspletare.IN_TRASMISSIONE.name());
			statiAmmessi.add(AttivitaDaEspletare.SOSPESA.name());
			statiAmmessi.add(AttivitaDaEspletare.ARCHIVIATA.name());
			statiAmmessi.add(AttivitaDaEspletare.TRANSMITTED.name());
			parameters.put("statiAmmessi", statiAmmessi);
		}
		return sql.toString();
	}
	
	public Long count(PraticaSearch search) throws Exception
	{
//		String sql = StringUtil.concateneString("select count(*) from ",
//												"\"presentazione_istanza\".\"pratica\" left join ",
//												"\"presentazione_istanza\".\"localizzazione_intervento\" ",
//												"on \"pratica_id\" = \"id\"",
//												" left join \"presentazione_istanza\".\"assegnamento_fascicolo\" as af",
//												" ON \"pratica\".\"id\" = \"af\".\"id_fascicolo\""
//												);
		
		String sql =StringUtil.concateneString(
		"SELECT count(*) FROM ",
		"(",
		selectAll,
		fromSearchIstr);
		final Map<String, Object> parameters = new HashMap<String, Object>();
		sql = generateWhereClause(search, parameters, sql.toString());
		//sql = estensioneWhereClause(false, parameters, sql); //sostituita dal prepareForSearch()
		sql=sql+" GROUP BY "+selectField+") as derivata ";
		logger.info("SQL-> "+sql.toString()+" PARAMETERS-> "+parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, Long.class);
	}
	
	
	
	private String queryForSearch(PraticaSearch search,Map<String, Object> parameters,String ...fieldsAggiuntivi) {
		String sqlFieldAggiuntivi="";
		if(fieldsAggiuntivi.length>0) {
			sqlFieldAggiuntivi=','+String.join(",",fieldsAggiuntivi);
		}
		
		String sql = StringUtil.concateneString(
				"SELECT * FROM (",
				selectAll,sqlFieldAggiuntivi,
				fromSearchIstr);
//								" from \"presentazione_istanza\".\"pratica\""
//											   			 + " left join \"presentazione_istanza\".\"localizzazione_intervento\""
//														 + " ON \"pratica\".\"id\" = \"localizzazione_intervento\".\"pratica_id\"");
		
		sql = generateWhereClause(search, parameters, sql);
		//sql = estensioneWhereClause(false, parameters, sql); sostituito da 
		
		sql=sql+ " GROUP BY "+selectField+sqlFieldAggiuntivi;
		sql = generateOrderByClause(search, sql);
		sql=sql+") as derivata ";
		return sql;
	}
	
	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> searchForAssegnamento(PraticaSearch search) throws Exception
	{
		String fields=Pratica  			   .id				  		 .getCompleteName() + ", "
					+ Pratica  			   .stato			  		 .getCompleteName() + ", " 
					+ Pratica  			   .codice_pratica_appptr	 .getCompleteName() + ", " 
					+ Pratica  			   .tipo_procedimento 		 .getCompleteName() + ", " 
					+ Pratica  			   .oggetto					 .getCompleteName() + ", "
					+ AssegnamentoFascicolo.rup	 		 			 .getCompleteName() + ", "
					+ AssegnamentoFascicolo.username_utente	 		 .getCompleteName() + ", " 
					+ AssegnamentoFascicolo.denominazione_utente	 .getCompleteName() + ", " 
					+ AssegnamentoFascicolo.num_assegnazioni		 .getCompleteName() + ", "
					+ AssegnamentoFascicolo.data_operazione			 .getCompleteName() + ", "
					+ "username_rup , " 
					+ "denominazione_rup ";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = StringUtil.concateneString ("select ", fields, " from ", Pratica.getTableName(),
				    							 " left join v_assegnazione as ", AssegnamentoFascicolo.getTableName(),
				    							 " on ", Pratica.id.getCompleteName(), " = ", 
				    							 AssegnamentoFascicolo.id_fascicolo.getCompleteName(),
				    							 " and ", AssegnamentoFascicolo.id_organizzazione.getCompleteName(),
				    							 " = :id_organizzazione",
				    							 " left join \"presentazione_istanza\".\"localizzazione_intervento\"",
				    							 " on \"pratica\".\"id\" = \"localizzazione_intervento\".\"pratica_id\"");
		sql = generateWhereClause(search, parameters, sql);
		parameters.put("id_organizzazione", userUtil.getIntegerId());
		logger.info("SQL-> "+sql.toString()+" PARAMETERS-> "+parameters);
		return super.paginatedList(sql.toString(), parameters, new TabelleAssegnamentoFascicoliRowMapper(), search.getPage(), search.getLimit());
	}
	
	/**
	 * search method
	 */
	@Override
	public PaginatedList<PraticaDTO> search(PraticaSearch search)
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
//		String sql = StringUtil.concateneString(
//				"SELECT * FROM (",
//				selectAll,
//				fromSearchIstr); 
////								" from \"presentazione_istanza\".\"pratica\""
////											   			 + " left join \"presentazione_istanza\".\"localizzazione_intervento\""
////														 + " ON \"pratica\".\"id\" = \"localizzazione_intervento\".\"pratica_id\"");
//		sql = generateWhereClause(search, parameters, sql);
//		//sql = estensioneWhereClause(false, parameters, sql); sostituito da 
//		sql = generateOrderByClause(search, sql);
//		sql=sql+ " GROUP BY ("+selectField+")) as derivata ";
		String sql=this.queryForSearch(search, parameters);
		logger.info("SQL-> "+sql.toString()+" PARAMETERS-> "+parameters);
		return super.paginatedList(sql.toString(), parameters, this.rowMapper, search.getPage(), search.getLimit());
	}
	
	/**
	 * Metodo che, senza paginazione, fa tornare tutte le pratiche che rientrano 
	 * nei criteri di ricerca stabiliti.
	 * @param search
	 * @return
	 * @throws Exception
	 */
	public List<PraticaDTO> searchAll(PraticaSearch search) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
//		String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\""
//											   			 + " left join \"presentazione_istanza\".\"localizzazione_intervento\""
//														 + " ON \"pratica\".\"id\" = \"localizzazione_intervento\".\"pratica_id\"");
//		sql = generateWhereClause(search, parameters, sql);
//		sql = generateOrderByClause(search, sql);
		String sql=this.queryForSearch(search, parameters);
		logger.info("SQL-> "+sql.toString()+" PARAMETERS-> "+parameters);
		return namedJdbcTemplateRead.query(sql, parameters, rowMapper);
	}
	
	public List<PraticaDTO> getDetails(List<UUID> ids) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sql = StringUtil.concateneString(selectAll,
												" from \"presentazione_istanza\".\"pratica\"",
												" where \"id\" in (:ids) ",
												" order by \"pratica\".\"codice_pratica_appptr\" ASC");
		parameters.put("ids", ids);
		logger.info("SQL-> "+sql.toString()+" PARAMETERS-> "+parameters);
		return namedJdbcTemplateRead.query(sql, parameters, rowMapper);
	}
	
	public PaginatedList<IstrPraticaDTO> searchForIstr(PraticaSearch search)
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		String sqlUsernameUtenteOrganizzazione=StringUtil.concateneString(
				" (select username_utente "
				," from \"presentazione_istanza\".\"v_assegnazione\" "
				," where " 
				," id_fascicolo=id and id_organizzazione= :idOrganizzazioneLoggata and fase= :fase)"
				," as username_assegnatario_organizzazione ");
		String sqlDenominazioneUtenteOrganizzazione=StringUtil.concateneString(
				" (select denominazione_utente "
				," from \"presentazione_istanza\".\"v_assegnazione\" "
				," where " 
				," id_fascicolo=id and id_organizzazione= :idOrganizzazioneLoggata and fase= :fase)"
				," as denominazione_assegnatario_organizzazione ");
		String sql = StringUtil.concateneString(
				"SELECT * FROM ("
				,selectAll
				," ,"
				,sqlUsernameUtenteOrganizzazione
				," ,"
				,sqlDenominazioneUtenteOrganizzazione
				,", (case af.\"username_rup\" when :username then true else false end)  as  rup"
				,", af.\"username_rup\" "
				,", af.\"denominazione_rup\" "
				,", af.\"username_utente\" "
				,", af.\"denominazione_utente\" "
									," from \"presentazione_istanza\".\"pratica\""
								   	," left join \"presentazione_istanza\".\"localizzazione_intervento\""
									," on \"pratica\".\"id\" = \"localizzazione_intervento\".\"pratica_id\""
									," left join \"presentazione_istanza\".\"v_assegnazione\" as af"
									," on \"ente_delegato\" = (\"id_organizzazione\"::text) and \"id_fascicolo\" = \"id\" and "
									," af.\"fase\" = :fase  and "
									," ( af.\"username_utente\" = :username or af.\"username_rup\" = :username ) ");
		sql = generateWhereClause(search, parameters, sql);
		//sql = estensioneWhereClause(false, parameters, sql);
		parameters.put("username", search.getUsername());
		parameters.put("fase", FasiAssegnazione.ISTRUTTORIA.name());
		parameters.put("idOrganizzazioneLoggata", search.getOrganizzazioneAutenticata());
		sql=sql+ " GROUP BY "+selectField+
				", af.username_rup "+
				", af.\"denominazione_rup\" "+
				", af.\"username_utente\" "+
				", af.\"denominazione_utente\" "
				;
		sql = generateOrderByClause(search, sql);
		sql=sql+") as derivata ";
		logger.info("SQL-> "+sql.toString()+" PARAMETERS-> "+parameters);
		PaginatedList<IstrPraticaDTO> ret = super.paginatedList(sql.toString(), parameters, new IstrPraticaRowMapper(), search.getPage(), search.getLimit());
		//popolo tutti gli altri dati...
		return ret;
	}

	/**
	 * insert all method
	 */
	@Override
	public long insert(PraticaDTO entity)
	{
		boolean statoValido = statoValido(entity.getStato());
		final String sql = StringUtil.concateneString("insert into \"presentazione_istanza\".\"pratica\"", "(\"id\"",
				",\"codice_pratica_appptr\"", ",\"ente_delegato\"", ",\"in_sanatoria\"", ",\"tipo_procedimento\"",
				",\"stato\"", ",\"data_creazione\"", ",\"oggetto\"", ",\"user_id\"", ",\"data_modifica\"",
				",\"privacy_id\"", ",\"tipo_selezione_localizzazione\"", 
				(statoValido ? ",\"ultimo_stato_valido\"" : ""), 
				", stato_seduta_commissione, stato_relazione_ente, stato_parere_soprintendenza"
				," ,t_pratica_id"
				," ,owner"
				," ,ruolo_pratica"
				," ,user_updated"
				," ) values ",
				"(?", ",?", ",?", ",?", ",?", ",?", ",?", ",?", ",?", ",?", ",?", ",?", (statoValido ? ",?" : ""), 
				", ?, ?, ? ,?"
				,",?,?,?"
				,")");
		final List<Object> parameters = new ArrayList<Object>();
		
		StatoSeduta 	seduta = entity.getStatoSedutaCommissione() != null ? entity.getStatoSedutaCommissione() : StatoSeduta.VERBALE_SEDUTA_ASSENTE;
		StatoRelazione 	relazione = entity.getStatoRelazioneEnte() != null ? entity.getStatoRelazioneEnte() : StatoRelazione.RELAZIONE_NON_TRASMESSA;
		StatoParere 	parere = entity.getStatoParereSoprintendenza() != null ? entity.getStatoParereSoprintendenza() : StatoParere.PARERE_NON_ALLEGATO;
		
		parameters.add(entity.getId());
		parameters.add(entity.getCodicePraticaAppptr());
		parameters.add(entity.getEnteDelegato());
		parameters.add(entity.getInSanatoria());
		parameters.add(entity.getTipoProcedimento());
		parameters.add(entity.getStato().name());
		parameters.add(entity.getDataCreazione());
		parameters.add(entity.getOggetto());
		parameters.add(entity.getUserId());
		parameters.add(entity.getDataModifica());
		parameters.add(entity.getPrivacyId());
		parameters.add(entity.getTipoSelezioneLocalizzazione());
		if(statoValido)
			parameters.add(entity.getStato().name());
		parameters.add(seduta.name());
		parameters.add(relazione.name());
		parameters.add(parere.name());
		parameters.add(entity.gettPraticaId());
		parameters.add(entity.getOwner());
		parameters.add(entity.getRuoloPratica());
		parameters.add(entity.getUserUpdated());		
		return super.update(sql, parameters);
	}

	/**
	 * update by pk method
	 */
	@Override
	public int update(PraticaDTO entity)
	{
		boolean statoValido = statoValido(entity.getStato());
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\"",
				" set \"codice_pratica_appptr\" = ?", ", \"ente_delegato\" = ?", ", \"in_sanatoria\" = ?",
				", \"tipo_procedimento\" = ?", ", \"stato\" = ?", ", \"data_creazione\" = ?", ", \"oggetto\" = ?",
				", \"user_id\" = ?", ", \"data_modifica\" = ?", ", \"privacy_id\" = ?", ", \"tipo_selezione_localizzazione\" = ?", 
				", \"has_shape\" = ?",", \"numero_protocollo\" = ?", ", \"data_protocollo\" = ?", 
				" ,\"codice_trasmissione\" = ? ",
				" ,\"user_updated\" = ? ",
				(statoValido ? ",\"ultimo_stato_valido\" = ?" : "")
				, " where \"codice_pratica_appptr\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodicePraticaAppptr());
		parameters.add(entity.getEnteDelegato());
		parameters.add(entity.getInSanatoria());
		parameters.add(entity.getTipoProcedimento());
		parameters.add(entity.getStato().name());
		parameters.add(entity.getDataCreazione());
		parameters.add(entity.getOggetto());
		parameters.add(entity.getUserId());
		parameters.add(entity.getDataModifica());
		parameters.add(entity.getPrivacyId());
		parameters.add(entity.getTipoSelezioneLocalizzazione());
		parameters.add(entity.getHasShape());
		parameters.add(entity.getNumeroProtocollo());
		parameters.add(entity.getDataProtocollo());
		parameters.add(entity.getCodiceTrasmissione());
		parameters.add(entity.getUserUpdated());
		if(statoValido)
			parameters.add(entity.getStato().name());
		parameters.add(entity.getCodicePraticaAppptr());
		logger.info("Eseguo la query: {} con i parametri: {}", sql, parameters);
		return super.update(sql, parameters);
	}
	
	
	
	public int updateWithoutCheck(PraticaDTO entity)
	{
		boolean statoValido = statoValido(entity.getStato());
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\"",
				" set \"codice_pratica_appptr\" = ?", ", \"ente_delegato\" = ?", ", \"in_sanatoria\" = ?",
				", \"tipo_procedimento\" = ?",
				", \"stato\" = ?",
				", \"data_creazione\" = ?",
				", \"oggetto\" = ?",
				", \"user_id\" = ?", 
				", \"data_modifica\" = ?", 
				", \"privacy_id\" = ?", 
				", \"tipo_selezione_localizzazione\" = ?", 
				", \"has_shape\" = ?",
				", \"numero_protocollo\" = ?", 
				", \"data_protocollo\" = ?", 
				" ,\"codice_trasmissione\" = ? ", 
				(statoValido ? ",\"ultimo_stato_valido\" = ?" : ""),
				" ,\"validazione_istanza\" = ? ", 
				" ,\"validazione_scheda_tecnica\" = ? ", 
				" ,\"validazione_allegati\" = ? ",
				" ,\"data_presentazione\" = ? ",
				" ,\"data_trasmissione_provvedimento_finale\" = ? ",
				" ,\"stato_seduta_commissione\" = ? ",
				" ,\"stato_relazione_ente\" = ? ",
				" ,\"stato_parere_soprintendenza\" = ? ",
				" ,\"data_inizio_lavorazione\" = ? ",
				" ,\"data_trasmissione_verbale\" = ? ",
				" ,\"data_trasmissione_relazione\" = ? ",
				" ,\"data_trasmissione_parere\" = ? ",
				" where \"codice_pratica_appptr\" = ?");
		final List<Object> parameters = new ArrayList<Object>();
		StatoSeduta 	seduta = entity.getStatoSedutaCommissione() != null ? entity.getStatoSedutaCommissione() : StatoSeduta.VERBALE_SEDUTA_ASSENTE;
		StatoRelazione 	relazione = entity.getStatoRelazioneEnte() != null ? entity.getStatoRelazioneEnte() : StatoRelazione.RELAZIONE_NON_TRASMESSA;
		StatoParere 	parere = entity.getStatoParereSoprintendenza() != null ? entity.getStatoParereSoprintendenza() : StatoParere.PARERE_NON_ALLEGATO;
		
		parameters.add(entity.getCodicePraticaAppptr());
		parameters.add(entity.getEnteDelegato());
		parameters.add(entity.getInSanatoria());
		parameters.add(entity.getTipoProcedimento());
		parameters.add(entity.getStato().name());
		parameters.add(entity.getDataCreazione());
		parameters.add(entity.getOggetto());
		parameters.add(entity.getUserId());
		parameters.add(entity.getDataModifica());
		parameters.add(entity.getPrivacyId());
		parameters.add(entity.getTipoSelezioneLocalizzazione());
		parameters.add(entity.getHasShape());
		parameters.add(entity.getNumeroProtocollo());
		parameters.add(entity.getDataProtocollo());
		parameters.add(entity.getCodiceTrasmissione());
		if(statoValido)
			parameters.add(entity.getStato().name());
		parameters.add(entity.getValidazioneIstanza());
		parameters.add(entity.getValidazioneSchedaTecnica());
		parameters.add(entity.getValidazioneAllegati());
		parameters.add(entity.getDataPresentazione());
		parameters.add(entity.getDataTrasmissioneProvvedimentoFinale());
		parameters.add(seduta.name());
		parameters.add(relazione.name());
		parameters.add(parere.name());
		parameters.add(entity.getDataInizioLavorazione());
		parameters.add(entity.getDataTrasmissioneVerbale());
		parameters.add(entity.getDataTrasmissioneRelazione());
		parameters.add(entity.getDataTrasmissioneParere());
		
		parameters.add(entity.getCodicePraticaAppptr());
		logger.info("Eseguo la query: {} con i parametri: {}", sql, parameters);
		return super.update(sql, parameters);
	}

	/**
	 * Metodo per aggiornare i campi legati alla validazione della pratica
	 * 
	 * @param id            id della pratica da aggiornare
	 * @param istanza       booleano legato alla sezione "istanza" del FE
	 * @param schedaTecnica booleano legato alla sezione "schedaTecnica" del FE
	 * @param allegati      booleano legato alla sezione "allegati" del FE
	 * @return
	 * @throws Exception
	 */
	public Integer updateValidation(UUID id, Boolean istanza, Boolean schedaTecnica, Boolean allegati) throws Exception
	{
		if (id != null && (istanza != null || schedaTecnica != null || allegati != null))
		{
			String sql;
			StringBuilder sqlBuilder = new StringBuilder("update \"presentazione_istanza\".\"pratica\"");
			String separator = " set ";
			List<Object> parameters = new ArrayList<Object>();
			if (istanza != null)
			{
				sqlBuilder.append(separator).append("\"validazione_istanza\" = ?");
				parameters.add(istanza);
				separator = ", ";
			}
			if (schedaTecnica != null)
			{
				sqlBuilder.append(separator).append("\"validazione_scheda_tecnica\" = ?");
				parameters.add(schedaTecnica);
				separator = ", ";
			}
			if (allegati != null)
			{
				sqlBuilder.append(separator).append("\"validazione_allegati\" = ?");
				parameters.add(allegati);
				separator = ", ";
			}
			sql = sqlBuilder.append(" where \"id\" = ?").toString();
			parameters.add(id);
			return update(sql, parameters);
		} else
		{
			throw new Exception("Non è possibile eseguire l'aggiornamento richiesto: id nullo o informazoni insufficenti per effettuare la query");
		}

	}

	/**
	 * delete by pk method
	 */
	@Override
	public int delete(PraticaDTO entity)
	{
		final String sql = StringUtil.concateneString("delete from \"presentazione_istanza\".\"pratica\"",
				" where \"codice_pratica_appptr\" = ? AND \"user_id\"=? ");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodicePraticaAppptr());
		parameters.add(entity.getUserId());
		return super.update(sql, parameters);
	}

	public int updateStatoPratica(UUID idPratica, AttivitaDaEspletare stato) throws Exception
	{
		boolean statoValido = statoValido(stato);
		String dataSql = "";
		if(stato.equals(AttivitaDaEspletare.IN_LAVORAZIONE))
			dataSql = ", \"data_inizio_lavorazione\" = :data";
		else if(stato.equals(AttivitaDaEspletare.TRANSMITTED))
			dataSql = ", \"data_trasmissione_provvedimento_finale\" = :data";
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" set \"stato\" = :stato",
													  (statoValido ? ", \"ultimo_stato_valido\" = :stato" : ""),
													  dataSql,
													  " where \"id\"=:id");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stato", stato.name());
		parameters.put("data", new Date());
		parameters.put("id", idPratica);
		return super.namedUpdate(sql, parameters);
	}
	
	public int updateDataPresentazione(UUID idPratica,Date data) throws Exception
	{
		final String sql = "update \"presentazione_istanza\".\"pratica\" set \"data_presentazione\" = :data_presentazione where \"id\"=:id";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("data_presentazione", data);
		parameters.put("id", idPratica);
		return super.namedUpdate(sql, parameters);
	}
	
	public int updateDataTrasmissioneProvvedimentoFinale(UUID idPratica,Date data) throws Exception
	{
		final String sql = "update \"presentazione_istanza\".\"pratica\" set \"data_trasmissione_provvedimento_finale\" = :data where \"id\"=:id";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("data", data);
		parameters.put("id", idPratica);
		return super.namedUpdate(sql, parameters);
	}

	public AttivitaDaEspletare getStato(UUID idPratica) throws Exception
	{
		final String sql = "select \"stato\" from \"presentazione_istanza\".\"pratica\" where \"id\" = :id";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", idPratica);
		String attivitaString = super.namedQueryForObject(sql, String.class, parameters);
		return AttivitaDaEspletare.valueOf(attivitaString);
	}
	
	public AttivitaDaEspletare getUltimoStatoValido(UUID idPratica) throws Exception
	{
		final String sql = "select \"ultimo_stato_valido\" from \"presentazione_istanza\".\"pratica\" where \"id\" = :id";
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", idPratica);
		String attivitaString = super.namedQueryForObject(sql, String.class, parameters);
		return AttivitaDaEspletare.valueOf(attivitaString);
	}
	
	public Map<String, Long> getCountersIstruttoria(PraticaSearch filters, boolean withoutAssignment) throws Exception
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		List<String> tmp = Arrays.asList(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name(), 
										 AttivitaDaEspletare.IN_PREISTRUTTORIA.name(),
										 AttivitaDaEspletare.IN_LAVORAZIONE.name(),
										 AttivitaDaEspletare.IN_TRASMISSIONE.name(),
										 AttivitaDaEspletare.TRANSMITTED.name(),
										 AttivitaDaEspletare.SOSPESA.name(),
										 AttivitaDaEspletare.ARCHIVIATA.name());	
		final List<String >countersName = new ArrayList<String>(tmp);
		String sql = StringUtil.concateneString("SELECT ",
				countersName
				.stream()
				.map(m -> " count(case when \"derivata\".\"stato\" = '" 
						 + m 
						 + "' then 1 else null end) as " 
						 + "n_" + m.toLowerCase())
				.collect(Collectors.joining(",")),
				" FROM ",
				"(",
		selectAll,
		fromSearchIstr);
		sql = generateWhereClause(filters, parameters, sql.toString());
		//sql = estensioneWhereClause(false, parameters, sql); //sostituita dal prepareForSearch()
		sql=sql+" GROUP BY "+selectField+") as derivata ";
		//	sql = generateOrderByClause(filters, sql);
		Map<String, Long> ret = super.namedJdbcTemplateRead.queryForObject(sql, parameters, new RowMapper<Map<String, Long>>()
		{
			@Override
			public Map<String, Long> mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				Map<String, Long> results = new HashMap<String, Long>();
				if(rs != null)
				{
					for(String c: countersName)
					{
						String key = "n_" + c.toLowerCase();
						results.put(key, rs.getLong(key));
					}
				}
				return results;
			}
		});
		if(!withoutAssignment && filters.getUserAssegnatario()==null) {
			filters.setNonAssegnate(true);
			Long inassegnate = this.count(filters);
			//conteggio delle non assegnate....è un admin o dirigente....
//			parameters.clear();
//			 sql = StringUtil.concateneString("select",
//					 " count(distinct \"pratica\".\"id\") ",
//					 " from \"presentazione_istanza\".\"pratica\""
//							  						   + " left join \"presentazione_istanza\".\"localizzazione_intervento\""
//							  						   + " ON \"pratica\".\"id\" = \"localizzazione_intervento\".\"pratica_id\""
//							  						 + " left join \"presentazione_istanza\".\"assegnamento_fascicolo\" \"af\" "
//							  						   + " ON \"pratica\".\"id\" = \"af\".\"id_fascicolo\""
//							  						 + " AND \"af\".\"id_organizzazione\" =   :myOrganizzazione "
//							  						+ " AND \"af\".\"fase\" =  :fase " );
//			 
//			 parameters.put("myOrganizzazione", myOrganizzazione);
//			 parameters.put("fase", FasiAssegnazione.ISTRUTTORIA.name());
//				sql = generateWhereClause(filters, parameters, sql);
//				sql = estensioneWhereClause(false, parameters, sql);
//				sql=sql+" AND \"af\".\"id_organizzazione\" is null  ";
//				Long retCount = super.namedJdbcTemplateRead.queryForObject(sql, parameters, Long.class);
//				ret.put("n_non_assegnata", retCount);
			ret.put("n_non_assegnata", inassegnate);
		};
		return ret;
	}
	
	public void updateStatoParere(UUID idPratica, StatoParere stato) throws Exception
	{
		String setdata = ""; 
		if(stato.equals(StatoParere.PARERE_INSERITO_ENTE) ||
		   stato.equals(StatoParere.PARERE_INSERITO_SOPRINTENDENZA))
			setdata = ", \"data_trasmissione_parere\" = :data";
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" set ",
													  "\"stato_parere_soprintendenza\" = :stato_parere",
													  setdata, " where \"pratica\".\"id\" = :id_pratica");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stato_parere", stato.name());
		parameters.put("data", new Date());
		parameters.put("id_pratica", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedUpdate(sql, parameters);
	}
	
	public StatoParere getStatoParere(UUID idPratica) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"stato_parere_soprintendenza\" from ",
													  "\"presentazione_istanza\".\"pratica\" ",
													  "where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, StatoParere.class);
	}
	
	public void updateStatoSeduta(UUID idPratica, StatoSeduta stato) throws Exception
	{
		String setdata = ""; 
		if(stato.equals(StatoSeduta.VERBALE_SEDUTA_ALLEGATO))
			setdata = ", \"data_trasmissione_verbale\" = :data";
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" set ",
													  "\"stato_seduta_commissione\" = :stato_seduta",
													  setdata, " where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stato_seduta", stato.name());
		parameters.put("data", new Date());
		parameters.put("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedUpdate(sql, parameters);
	}
	
	public void updateStatoSeduta(Long idSeduta, StatoSeduta stato) throws Exception
	{
		String setdata = ""; 
		if(stato.equals(StatoSeduta.VERBALE_SEDUTA_ALLEGATO))
			setdata = ", \"data_trasmissione_verbale\" = :data";
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" set ", 
													  "\"stato_seduta_commissione\" = :stato_seduta",
													  setdata,
													  " where \"id\" in (select \"id_pratica\" from ",
													  "\"presentazione_istanza\".\"seduta_pratica\" ",
													  "where \"id_seduta\" = :id_seduta) ",
													  "and \"stato_seduta_commissione\" != 'VERBALE_SEDUTA_ALLEGATO'");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stato_seduta", stato.name());
		parameters.put("data", new Date());
		parameters.put("id_seduta", idSeduta);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedUpdate(sql, parameters);
	}
	
	public StatoSeduta getStatoSeduta(UUID idPratica) throws Exception
	{
		final String sql = StringUtil.concateneString("select \"stato_seduta_commissione\" from ",
													  "\"presentazione_istanza\".\"pratica\" ",
													  "where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		return namedJdbcTemplateRead.queryForObject(sql, parameters, StatoSeduta.class);
	}
	
	private boolean statoValido(AttivitaDaEspletare stato)
	{
		List<AttivitaDaEspletare> toIgnore = Arrays.asList(AttivitaDaEspletare.SOSPESA, AttivitaDaEspletare.ARCHIVIATA);
		return !toIgnore.contains(stato);
	}
	
	public void updateStatoRelazione(UUID idPratica, StatoRelazione stato) throws Exception
	{
		String setdata = ""; 
		if(stato.equals(StatoRelazione.RELAZIONE_TRASMESSA_CON_AVVIO) ||
		   stato.equals(StatoRelazione.RELAZIONE_TRASMESSA_SENZA_AVVIO))
			setdata = ", \"data_trasmissione_relazione\" = :data";
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" ",
													  "set \"stato_relazione_ente\" = :stato_relazione",
													  setdata,
													  " where \"id\" = :id");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stato_relazione", stato.name());
		parameters.put("data", new Date());
		parameters.put("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedUpdate(sql, parameters);
	}
	
	public void trasmettiPratica(UUID idPratica) throws Exception
	{
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" ",
													  "set \"stato\" = :stato, ",
													  "\"data_trasmissione_provvedimento_finale\" = :data ",
													  "where \"id\" = :id");
		parameters.put("stato", AttivitaDaEspletare.TRANSMITTED.name());
		parameters.put("data", new Date());
		parameters.put("id", idPratica);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		namedUpdate(sql, parameters);
	}
	
	/**
	 * passa dalla vista delle pratiche pubbliche
	 * @author acolaianni
	 *
	 * @param codice
	 * @return
	 * @throws CustomOperationToAdviceException 
	 */
	public PraticaDTO findPraticaPubblica(String codice) throws CustomOperationToAdviceException {
	  final String sql = StringUtil.concateneString("SELECT codice from \"presentazione_istanza\".\"v_fascicolo_public\"",
					" where \"codice\" = ?");
			final List<Object> parameters = new ArrayList<Object>();
			parameters.add(codice);
		String codicePubblico = super.queryForObjectTxRead(sql, String.class, parameters);
		if(codicePubblico==null)
			throw new CustomOperationToAdviceException("Pratica non pubblicata o inesistente !!!");
		PraticaDTO pratica = this.findByCodiceTrasmissione(codicePubblico);
		return pratica;
	}

	public PraticaDTO findPraticaFromCorrispondenza(Long idCorrispondenza) {
		final String sql = StringUtil.concateneString(selectAll, " from \"presentazione_istanza\".\"pratica\" p",
				" inner join fascicolo_corrispondenza f on f.id_pratica=p.id",
				" where f.id_corrispondenza = ?");
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(idCorrispondenza);
		return super.queryForObjectTxRead(sql, this.rowMapper, parameters);
	}

	public int updateEsoneri(UUID id, Boolean esoneroOneri, Boolean esoneroBollo,String userUpdated) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		final String sql = StringUtil.concateneString("update \"presentazione_istanza\".\"pratica\" ",
													  " set \"esonero_bollo\" = :esoneroBollo ",
													  " ,\"esonero_oneri\" = :esoneroOneri ",
													  " ,\"user_updated\" = :userUpdated ",
													  " where \"id\" = :id ");
		parameters.put("esoneroBollo", esoneroBollo);
		parameters.put("esoneroOneri", esoneroOneri);
		parameters.put("userUpdated", userUpdated);
		parameters.put("id", id);
		logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
		int ret = namedUpdate(sql, parameters);
		return ret;
	}
	
	public String updateCodiceSegreto(String codicePratica) throws Exception
	{
	    final Map<String, Object> parameters = new HashMap<>();
	    final String codiceSegretoNew = UUID.randomUUID().toString();
	    final String sql = "update presentazione_istanza.pratica set codice_segreto = :codice_segreto where codice_pratica_appptr = :codice";
	    parameters.put("codice_segreto", codiceSegretoNew);
	    parameters.put("codice", codicePratica);
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    namedJdbcTemplateWrite.update(sql, parameters);
	    return codiceSegretoNew;
	}
	
	public void validazioneRichiedente(String codicePratica) throws Exception
	{
	    final Map<String, Object> parameters = new HashMap<>();
	    final String sql = "update presentazione_istanza.pratica set validazione_richiedente = true where codice_pratica_appptr = :codice";
	    parameters.put("codice", codicePratica);
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public boolean getValidazioneRichiedente(String idPratica) throws Exception
	{
	    final Map<String, Object> parameters = new HashMap<>();
	    final String sql = "select validazione_richiedente from presentazione_istanza.pratica where (id::text) = :id";
	    parameters.put("id", idPratica);
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    return namedJdbcTemplateRead.queryForObject(sql, parameters, Boolean.class);
	}
	
	public void changeOwner(UUID idPratica) throws Exception
	{
	    final Map<String, Object> parameters = new HashMap<>();
	    final String sql = "update presentazione_istanza.pratica set owner = :owner where id = :id";
	    parameters.put("owner", SecurityUtil.getUsername());
	    parameters.put("id", idPratica);
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    namedJdbcTemplateWrite.update(sql, parameters);
	}
	public void annullaValidazione(UUID idPratica) throws Exception
	{
	    final Map<String, Object> parameters = new HashMap<>();
	    final String sql = StringUtil.concateneString(
	    		"update presentazione_istanza.pratica set "
	    		,"validazione_allegati = false "
	    		,",validazione_scheda_tecnica=false "
	    		,",validazione_istanza = false where id = :id");
	    parameters.put("id", idPratica);
	    logger.info("Eseguo la query {} con i seguenti parametri {}", sql, parameters);
	    namedJdbcTemplateWrite.update(sql, parameters);
	}
	
	public List<String> getDenominazioneFunzionario(final UUID id, final int idOrganizzazione, final String fase) {
    	String sql=StringUtil.concateneString(
    			 "select denominazione_utente "
    			,"from \"presentazione_istanza\".\"v_assegnazione\" " 
    			,"where id_fascicolo = ? "
    				  ,"and id_organizzazione=? "
    				  ,"and fase=? "
				);
    	final List<Object> parameters = new ArrayList<Object>();
    	parameters.add(id);
    	parameters.add(idOrganizzazione);
    	parameters.add(fase);
    	return super.queryForList(sql.toString(), String.class, parameters);
    	
    }
	
	public List<String> getDenominazioneRup(final UUID id, final int idOrganizzazione, final String fase) {
    	String sql=StringUtil.concateneString(
    			 "select denominazione_rup "
    			,"from \"presentazione_istanza\".\"v_assegnazione\" " 
    			,"where id_fascicolo = ? "
    				  ,"and id_organizzazione=? "
    				  ,"and fase=? "
				);
    	final List<Object> parameters = new ArrayList<Object>();
    	parameters.add(id);
    	parameters.add(idOrganizzazione);
    	parameters.add(fase);
    	return super.queryForList(sql.toString(), String.class, parameters);
    }
}
