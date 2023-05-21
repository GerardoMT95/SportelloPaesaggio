package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.repository.base.FascicoloBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloPublicRowMapper;
import it.eng.tz.puglia.autpae.rowmapper.custom.LineaTemporaleRowMapper;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.MyCompetenzaService;
import it.eng.tz.puglia.autpae.utility.Stringhe;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for table fascicolo
 */
@Repository
public class FascicoloFullRepository extends FascicoloBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(FascicoloFullRepository.class);
	
	@Autowired private UserUtil userUtil;
	@Autowired private ApplicationProperties props;
	@Autowired CommonService commonService;
	@Autowired AnagraficaService anagraficaService;
	
	//campi  della tabella derivata dalla union delle 2 viste
	final private StringBuilder fieldsVistaFascicoloPublic = 
			new StringBuilder()
			.append("id,").append("ufficio,").append("org_creazione,").append("tipo_procedimento,")
			.append("oggetto_intervento,").append("codice,").append("codice_interno_ente,")
			.append("numero_interno_ente,").append("protocollo,").append("data_protocollo,").append("comune_id,")
			.append("rup,").append("numero_provvedimento,").append("data_rilascio_autorizzazione,")
			.append("esito_data_rilascio_autorizzazione,").append("esito,").append("esito_verifica,").append("stato,")
			.append("tipo_intervento,").append("label_intervento,").append("applicazione");
    
/*	public AcceleratoriFascicoloDTO getCountForAccelerator(boolean isPublic) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		/*
		 * select count(case when fascicolo.stato = 'WORKING'     then 1 else null end) as nWorking,
				  count(case when fascicolo.stato = 'TRANSMITTED' then 1 else null end) as nTrasmitted,
				  count(case when fascicolo.stato = 'CANCELLED'   then 1 else null end) as nCancelled,
				  count(case when fascicolo.stato = 'SELECTED'    then 1 else null end) as nSelected,
				  count(case when fascicolo.stato = 'FINISHED'    then 1 else null end) as nFinished,
				  count(case when fascicolo.stato = 'ON_MODIFY'   then 1 else null end) as nOnModify
			from autpae.fascicolo;
		**
		StringBuilder sql = new StringBuilder();
		String sqlToAdd = "";
		if(!isPublic)
		{
		   sqlToAdd = "count(case when fascicolo.stato = 'WORKING'   then 1 else null end) as nWorking,    ";
		}
		sql .append("select ")
			.append(sqlToAdd)
			.append("count(case when fascicolo.stato = 'TRANSMITTED' then 1 else null end) as nTrasmitted, ")
			.append("count(case when fascicolo.stato = 'CANCELLED'   then 1 else null end) as nCancelled,  ")
			.append("count(case when fascicolo.stato = 'SELECTED'    then 1 else null end) as nSelected,   ")
			.append("count(case when fascicolo.stato = 'FINISHED'    then 1 else null end) as nFinished,   ")
			.append("count(case when fascicolo.stato = 'ON_MODIFY'   then 1 else null end) as nOnModify    ")
			.append("from ")
			.append(Fascicolo.getTableName());
		log.trace("Eseguo la query {}", sql.toString());
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<AcceleratoriFascicoloDTO>()
		{
			@Override
			public AcceleratoriFascicoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				AcceleratoriFascicoloDTO dto = null;
				if(rs != null)
				{
					dto = new AcceleratoriFascicoloDTO();
					if(!isPublic)
					dto.setnWorking   (rs.getObject("nWorking")    != null ? rs.getLong("nWorking")   : 0);
					dto.setnTrasmitted(rs.getObject("nTrasmitted") != null ? rs.getLong("nTrasmitted"): 0);
					dto.setnCancelled (rs.getObject("nCancelled")  != null ? rs.getLong("nCancelled") : 0);
					dto.setnSelected  (rs.getObject("nSelected")   != null ? rs.getLong("nSelected")  : 0);
					dto.setnFinished  (rs.getObject("nFinished")   != null ? rs.getLong("nFinished")  : 0);
					dto.setnOnModify  (rs.getObject("nOnModify")   != null ? rs.getLong("nOnModify")  : 0);
				}
				return dto;
			}
		});	
	}	*/
	
	/**
	 * rowmapper utilizzato per il fetch dalla vista pubblica
	 */
	final static RowMapper<FascicoloPublicDto> fascicoloPublicRowMapper = new FascicoloPublicRowMapper();
	
	
	public int aggiornaJsonInfo(final long idFascicolo, final PGobject jsonInfo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.info_complete.columnName())
		   .append(" = :")
		   .append(Fascicolo.info_complete.columnName())
		   .append(" where ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.id.columnName(), idFascicolo);
		parameters.put(Fascicolo.info_complete.columnName(), jsonInfo);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int setStatoInTrasmissione(final long idFascicolo, final boolean flag) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		Map<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.stato_trasmissione.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato_trasmissione.columnName());
		
		sql.append(" where ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());
		
		parameters.put(Fascicolo.id.columnName(), idFascicolo);
		parameters.put(Fascicolo.stato_trasmissione.columnName(), flag);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int cambiaStato(final long idFascicolo, final StatoFascicolo stato, final Boolean verifica) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		Map<String, Object> parameters = new HashMap<String, Object>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.stato.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato.columnName());
		
		if (stato==StatoFascicolo.FINISHED && verifica==true) {
			 sql.append(" , ")
			    .append(Fascicolo.data_verifica.columnName())
			    .append(" = :")
			    .append(Fascicolo.data_verifica.columnName());
			 
			parameters.put(Fascicolo.data_verifica.columnName(), new Date());
			}
		
   else if (stato==StatoFascicolo.FINISHED && verifica==false) {
			 sql.append(" , ")
			    .append(Fascicolo.esito_verifica.columnName())
			    .append(" = :")
			    .append(Fascicolo.esito_verifica.columnName())
			    .append(" , ")
			    .append(Fascicolo.data_campionamento.columnName())
			    .append(" = :")
			    .append(Fascicolo.data_campionamento.columnName());
			 
			parameters.put(Fascicolo.data_campionamento.columnName(), new Date());
			parameters.put(Fascicolo.esito_verifica.columnName(), EsitoVerifica.NOT_SELECTED.name());
			}
		
   else if (stato==StatoFascicolo.SELECTED) {
			 sql.append(" , ")
			    .append(Fascicolo.esito_verifica.columnName())
			    .append(" = :")
			    .append(Fascicolo.esito_verifica.columnName())
			    .append(" , ")
			    .append(Fascicolo.data_campionamento.columnName())
			    .append(" = :")
			    .append(Fascicolo.data_campionamento.columnName());
			 
			parameters.put(Fascicolo.data_campionamento.columnName(), new Date());
			parameters.put(Fascicolo.esito_verifica.columnName(), EsitoVerifica.CHECK_IN_PROGRESS.name());
			}
		
   else if (stato==StatoFascicolo.TRANSMITTED) {
			 sql.append(" , ")
				.append(Fascicolo.data_trasmissione.columnName())
				.append(" = :")
				.append(Fascicolo.data_trasmissione.columnName())
				.append(" , ")
				.append(Fascicolo.username_utente_trasmissione.columnName())
				.append(" = :")
				.append(Fascicolo.username_utente_trasmissione.columnName())
				.append(" , ")
				.append(Fascicolo.denominazione_utente_trasmissione.columnName())
				.append(" = :")
				.append(Fascicolo.denominazione_utente_trasmissione.columnName())
				.append(" , ")
				.append(Fascicolo.email_utente_trasmissione.columnName())
				.append(" = :")
				.append(Fascicolo.email_utente_trasmissione.columnName())
				.append(" , ")
			    .append(Fascicolo.stato_precedente.columnName())
			    .append(" = :")
			    .append(Fascicolo.stato_precedente.columnName())
			    .append(" , ")
			    .append(Fascicolo.esito_verifica_precedente.columnName())
			    .append(" = :")
			    .append(Fascicolo.esito_verifica_precedente.columnName())
			    .append(" , ")
			    .append(Fascicolo.modificabile_fino.columnName())
			    .append(" = :")
			    .append(Fascicolo.modificabile_fino.columnName())
			    .append(" , ")
			    .append(Fascicolo.esito_verifica.columnName())
			    .append(" = :")
			    .append(Fascicolo.esito_verifica.columnName());
			
			parameters.put(Fascicolo.data_trasmissione.columnName(), new Date());
			parameters.put(Fascicolo.username_utente_trasmissione.columnName(), SecurityUtil.getUsername());
			parameters.put(Fascicolo.denominazione_utente_trasmissione.columnName(), userUtil.hasUserLogged() ?
																					 userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome()) :
																					 props.getBatchUsr());
			parameters.put(Fascicolo.email_utente_trasmissione.columnName(), userUtil.hasUserLogged() ? userUtil.getMyProfile().getEmail() : null);
			parameters.put(Fascicolo.stato_precedente.columnName(), null);
			parameters.put(Fascicolo.esito_verifica_precedente.columnName(), null);
			parameters.put(Fascicolo.modificabile_fino.columnName(), null);
			parameters.put(Fascicolo.esito_verifica.columnName(), EsitoVerifica.NOT_SAMPLED.name());//per adesso, poi se è attivo il campionamento....
		}
		
		// TODO: else if --> per gli altri stati con le rispettive date
		
		sql.append(" where ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());
		
		parameters.put(Fascicolo.id.columnName(), idFascicolo);
		parameters.put(Fascicolo.stato.columnName(), stato.name());
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int consentiModifica(final long idFascicolo, final Date modificabileFino) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" = ")
		   .append(Fascicolo.stato.columnName())
		   .append(" , ")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" = ")
		   .append(Fascicolo.esito_verifica.columnName())
		   .append(" , ")
		   .append(Fascicolo.stato.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato.columnName())
		   .append(" , ")
		   .append(Fascicolo.esito_verifica.columnName())
		   .append(" = :")
		   .append(Fascicolo.esito_verifica.columnName())
		   .append(" , ")
		   .append(Fascicolo.modificabile_fino.columnName())
		   .append(" = :")
		   .append(Fascicolo.modificabile_fino.columnName())
		   .append(" where ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.id.columnName(), idFascicolo);
		parameters.put(Fascicolo.stato.columnName(), StatoFascicolo.ON_MODIFY.name());
		parameters.put(Fascicolo.esito_verifica.columnName(), StatoFascicolo.ON_MODIFY.name());
		parameters.put(Fascicolo.modificabile_fino.columnName(), modificabileFino);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int bloccaModifica(final long idFascicolo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.stato.columnName())
		   .append(" = ")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.esito_verifica.columnName())
		   .append(" = ")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" = :")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.modificabile_fino.columnName())
		   .append(" = :")
		   .append(Fascicolo.modificabile_fino.columnName())
		   .append(" where ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName())
		   .append(" and ")
		   .append(Fascicolo.stato.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.id.columnName(), idFascicolo);
		parameters.put(Fascicolo.stato.columnName(), StatoFascicolo.ON_MODIFY.name());	// effettuo queste operazioni solo se il Fascicolo è ancora "in modifica"
		parameters.put(Fascicolo.stato_precedente.columnName(), null);
		parameters.put(Fascicolo.esito_verifica_precedente.columnName(), null);
		parameters.put(Fascicolo.modificabile_fino.columnName(), null);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int bloccaModificheScadute() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.stato.columnName())
		   .append(" = ")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.esito_verifica.columnName())
		   .append(" = ")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" = :")
		   .append(Fascicolo.esito_verifica_precedente.columnName())
		   .append(" , ")
		   .append(Fascicolo.modificabile_fino.columnName())
		   .append(" = :")
		   .append(Fascicolo.modificabile_fino.columnName())
		   .append(" where ")
		   .append(Fascicolo.modificabile_fino)
		   .append(" < :today ")
		   .append(" and ")
		   .append(Fascicolo.stato.columnName())
		   .append(" = :")
		   .append(Fascicolo.stato.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.stato.columnName(), StatoFascicolo.ON_MODIFY.name());	// effettuo queste operazioni solo se il Fascicolo è ancora "in modifica"
		parameters.put(Fascicolo.stato_precedente.columnName(), null);
		parameters.put(Fascicolo.esito_verifica_precedente.columnName(), null);
		parameters.put(Fascicolo.modificabile_fino.columnName(), null);
		parameters.put("today", new Date());
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public int cancella(final long idFascicolo, final boolean deleted) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(Fascicolo.deleted.columnName())
		   .append(" = :")
		   .append(Fascicolo.deleted.columnName())
		   .append(" where ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());
		   
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.id.columnName(), idFascicolo);
		parameters.put(Fascicolo.deleted.columnName(), deleted);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	public Date getModificabileFino(final long id) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		
			sql.append("select ")
			   .append(Fascicolo.modificabile_fino.columnName())
			   .append(" from ")
			   .append(Fascicolo.getTableName());
			sql.append(" where ")
			   .append(Fascicolo.id.columnName())
			   .append(" = ")
			   .append(id);
		   
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), id);
		return super.queryForObject(sql.toString(), Date.class);
	}
	
	/**
	 * fetch dei codici pratica da vista ad hoc per popolare autocomplete di codici di fascicolo annullabili o su cui è possibile attivare
	 * la modifica 
	 * @autor Adriano Colaianni
	 * @date 20 mag 2022
	 * @param codice
	 * @param limit
	 * @param isAnnullabile
	 * @param isModificabile
	 * @return
	 * @throws Exception
	 */
	public List<String> autocompleteCodiceAnnMod(final String codice, final Integer limit,
			Boolean isAnnullabile, 
			Boolean isModificabile,
			Boolean isInModifica) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder();
		String sep =" where ";
		sql.append("select distinct ")
		   .append(Fascicolo.codice.columnName())
		   .append(" from ")
		   .append(" v_codice_fascicolo_annullabile_modificabile ");
		if (!StringUtil.isEmpty(codice)) {
			sql.append(sep)
			   .append(Fascicolo.codice.columnName())
			   .append(" ILIKE ? ");
			
			sep=" and ";
		}
		if(isAnnullabile!=null) {
			sql.append(sep)
			   .append(" is_annullabile = ")
			   .append(isAnnullabile);
			sep=" and ";
		}
		if(isModificabile!=null) {
			sql.append(sep)
			   .append(" is_modificabile = ")
			   .append(isModificabile);
			sep=" and ";
		}
		if(isInModifica!=null) {
			sql.append(sep)
			   .append(" is_in_modifica = ")
			   .append(isInModifica);
			sep=" and ";
		}
		sql.append(" order by ")
		   .append(Fascicolo.codice.columnName())
		   .append(" limit ")
		   .append(limit);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), StringUtil.convertLike(codice));
		return super.queryForList(sql.toString(), String.class,List.of(StringUtil.convertLike(codice)));
	}
	
	public List<String> autocompleteCodice(final String codice, final Integer limit) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
			sql.append("select distinct ")
			   .append(Fascicolo.codice.columnName())
			   .append(" from ")
			   .append(Fascicolo.getTableName())
			   .append(" where ")
			   .append(Fascicolo.deleted.columnName())
			   .append(" = false ");
			
		if (!StringUtil.isEmpty(codice)) {
			sql.append(" and ")
			   .append(Fascicolo.codice.columnName())
			   .append(" ILIKE ? ");
		}
		if (!userUtil.hasUserLogged()) {	// utente non loggato
			sql .append(" AND ( ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.TRANSMITTED.name()))
				.append(" or ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.SELECTED   .name()))
				.append(" or ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.FINISHED   .name()))
				.append(" or ")
				.append(Fascicolo.stato.getCompleteName()+"  = "+Stringhe.apicizza(StatoFascicolo.ON_MODIFY  .name()))
				.append(" )");
			sql .append(" AND ( ")
				.append(Fascicolo.esito.getCompleteName()+" != "+Stringhe.apicizza(EsitoProvvedimento.NON_AUTORIZZATO.name()))
				.append(" or ")
				.append(Fascicolo.esito.getCompleteName()+" IS NULL ")
				.append(" )");
		}
		sql.append(" order by ")
		   .append(Fascicolo.codice.columnName())
		   .append(" limit ")
		   .append(limit);
		   
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), StringUtil.convertLike(codice));
		return super.queryForList(sql.toString(), String.class,StringUtil.convertLike(codice));
	}
	
	public List<String> autocompleteRup(final int idOrganizzazione, final String rup) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<Object> param=new ArrayList<>();
		StringBuilder sql = new StringBuilder();
			sql.append("select distinct ")
			   .append(Fascicolo.rup.columnName())
			   .append(" from ")
			   .append(Fascicolo.getTableName())
			   .append(" where ")
			   .append(Fascicolo.org_creazione.columnName())
			   .append(" = ")
			   .append(" ? ");
		param.add(idOrganizzazione);	
		if (!StringUtil.isEmpty(rup)) {
			sql.append(" and ")
			   .append(Fascicolo.rup.columnName())
			   .append(" ILIKE ")
			   .append(" ? ");
			param.add(StringUtil.convertLike(rup));
		}
		sql.append(" order by ")
		.append(Fascicolo.rup.columnName());
		log.trace("Eseguo la query: {} con i seguenti parametri: [codiceEnte={}, rup={}]", sql.toString(), idOrganizzazione, StringUtil.convertLike(rup));
		return super.queryForList(sql.toString(), String.class,param);
	}
	
	public int aggiornaEsito(final Long idFascicolo,final EsitoVerifica esito, final Date data, final String numero) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(StringUtil.concateneString(Fascicolo.esito_verifica.columnName(),               		 " = :", Fascicolo.esito_verifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.esito_data_rilascio_autorizzazione.columnName(), " = :", Fascicolo.esito_data_rilascio_autorizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.esito_numero_provvedimento.columnName(),         " = :", Fascicolo.esito_numero_provvedimento.columnName()))
		   .append(" WHERE ")
		   .append(Fascicolo.id.columnName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.esito_verifica.columnName(),               	  esito.name());
		parameters.put(Fascicolo.esito_data_rilascio_autorizzazione.columnName(), data  );
		parameters.put(Fascicolo.esito_numero_provvedimento.columnName(),         numero);
		parameters.put(Fascicolo.id.columnName(),         idFascicolo);
		   
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	public LineaTemporaleDTO lineaTemporale(final Long idFascicolo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "));
		
		sql				 .append(Fascicolo	.data_creazione.getCompleteName())
		   .append(", ") .append(Fascicolo	.data_trasmissione.getCompleteName())
		   .append(", ") .append(Fascicolo	.data_campionamento.getCompleteName())
		   .append(", ") .append(Fascicolo	.data_verifica.getCompleteName())
		   .append(", ") .append(Richiedente.nome.getCompleteName())
		   .append(", ") .append(Richiedente.cognome.getCompleteName())
		   .append(", ") .append(TipoProcedimento.descrizione.getCompleteName())
		   .append(", ") .append(Fascicolo	.ufficio.getCompleteName());
		
		sql.append(" FROM ")      
		   .append(Fascicolo.getTableName())
		   .append(" left join ") 
		   .append(Richiedente.getTableName())
		   .append(" on ") 
		   .append(Fascicolo.id.getCompleteName()) .append(" = ") .append(Richiedente.id_fascicolo.getCompleteName())
		   .append(", ") 	      
		   .append(TipoProcedimento.getTableName());
		
		sql.append(" WHERE ")
		   .append(Fascicolo.tipo_procedimento.getCompleteName()) 
		   .append(" = ") 
		   .append(TipoProcedimento.codice.getCompleteName()) 
		   .append(" AND ")
		   .append(Fascicolo.id.getCompleteName())
		   .append(" = ")
		   .append(":idFascicolo");
			
			Map<String, Object> parameters = new HashMap<String, Object>();			
			parameters.put("idFascicolo", idFascicolo);
			log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), idFascicolo);
			return namedJdbcTemplate.queryForObject(sql.toString(), parameters, new LineaTemporaleRowMapper());
	}

	
	private void sqlWherePubblica(final FascicoloSearch filter, final StringBuilder sql, final Map<String,Object> parameters) throws Exception {
		if(StringUtil.isNotEmpty(filter.getComuneIntervento())) {
			sql.append(" and ")
			.append(" comune_id =  :comuneId");
			String codiceCatastale = anagraficaService.getCodCatastaleFromIstat(filter.getComuneIntervento());
			int idEnte=Integer.MIN_VALUE; //
			try {
				idEnte = commonService.findEnteByCodice(codiceCatastale).getId();
			}catch(Exception e) {
				log.warn("Comune con catastale " +codiceCatastale +" non trovato in common.ente");
			}
			parameters.put("comuneId",idEnte);
		}
		if (filter.getTipologiaIntervento()!=null) {
			sql.append(" and ")
			   .append(" tipo_intervento::text = :tipoIntervento " );
			parameters.put("tipoIntervento",filter.getTipologiaIntervento().getTextValue());
		}
		if (filter.getCodice()!=null) {
			sql.append(" and ")
			   .append(" codice like :codice " );
			parameters.put("codice",StringUtil.convertLike(filter.getCodice()));
		}
		if (filter.getTipoProcedimento()!=null) {
			sql.append(" and ")
			   .append(" tipo_procedimento = :tipoProcedimento");
			parameters.put("tipoProcedimento",filter.getTipoProcedimento().name());
		}
		if(StringUtil.isNotEmpty(filter.getOggettoIntervento()))
		{
			sql.append(" and ")
			   .append(" oggetto_intervento ILIKE :oggettoIntervento ");
			parameters.put("oggettoIntervento", StringUtil.convertLike(filter.getOggettoIntervento()));
		}
		if(filter.getDataRilascioAutorizzazioneDa() != null)
		{
			sql.append(" and ")
			   .append(" data_rilascio_autorizzazione >= :dataRilascioAutorizzazioneDa ");
			parameters.put("dataRilascioAutorizzazioneDa", filter.getDataRilascioAutorizzazioneDa() );
		}
		if(filter.getDataRilascioAutorizzazioneA() != null)
		{
			sql.append(" and ")
			   .append(" data_rilascio_autorizzazione <= :dataRilascioAutorizzazioneA ");
			parameters.put("dataRilascioAutorizzazioneA", filter.getDataRilascioAutorizzazioneA() );
		}
		if(filter.getEsito()!= null)
		{
			sql.append(" and ")
			   .append(" esito = :esito ");
			parameters.put("esito", filter.getEsito().name() );
		}
		if(filter.getEsitoVerifica()!= null)
		{
			sql.append(" and ")
			   .append(" esito_verifica = :esitoVerifica ");
			parameters.put("esitoVerifica", filter.getEsitoVerifica().name() );
		}
		if(filter.getStatoRegistrazione()!= null)
		{
			sql.append(" and ")
			   .append(" stato = :statoRegistrazione ");
			parameters.put("statoRegistrazione", filter.getStatoRegistrazione().name() );
		}
		if(StringUtil.isNotEmpty(filter.getCodiceInternoEnte()))
		{
			sql.append(" and ")
			   .append(" codice_interno_ente ILIKE :codiceInternoEnte ");
			parameters.put("codiceInternoEnte", StringUtil.convertLike(filter.getCodiceInternoEnte()));
		}
		if(StringUtil.isNotEmpty(filter.getNumeroInternoEnte()))
		{
			sql.append(" and ")
			   .append(" numero_interno_ente ILIKE :numeroInternoEnte ");
			parameters.put("numeroInternoEnte", StringUtil.convertLike(filter.getNumeroInternoEnte()));
		}
		if(StringUtil.isNotEmpty(filter.getProtocollo()))
		{
			sql.append(" and ")
			   .append(" protocollo ILIKE :protocollo ");
			parameters.put("protocollo", StringUtil.convertLike(filter.getProtocollo()));
		}
		if(filter.getDataProtocolloDa() != null)
		{
			sql.append(" and ")
			   .append(" data_protocollo >= :dataProtocolloDa ");
			parameters.put("dataProtocolloDa", filter.getDataProtocolloDa() );
		}
		if(filter.getDataProtocolloA() != null)
		{
			sql.append(" and ")
			   .append(" data_protocollo <= :dataProtocolloA ");
			parameters.put("dataProtocolloA", filter.getDataProtocolloA() );
		}
	}
	
	/**
	 * lavora sulla vista v_fascicolo_public
	 * e nel caso di utente autenticato attiva l'accesso anche alle pratiche NON_AUTORIZZATE 
	 * accendendo il flag userCanAccess
	 * @param filter
	 * @return
	 * @throws Exception 
	 */
	public PaginatedList<FascicoloPublicDto> publicSearch(final FascicoloSearch filter,final Map<String, MyOrganizzazioniBean> myOrgMap) throws Exception {
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder fields = new StringBuilder();
		String userCanAccessNegative = this.sqlQueryVisibilitaEsitoNegativo(myOrgMap,parameters);
		fields
		.append("id,")
		.append("ufficio,")
		.append("org_creazione,")
		.append("tipo_procedimento,")
				.append("oggetto_intervento,").append("codice,").append("rup,").append("numero_provvedimento,")
				.append("data_rilascio_autorizzazione,").append("esito,").append("esito_verifica,")
				.append("codice_interno_ente,")
				.append("numero_interno_ente,")
				.append("data_protocollo,")
				.append("protocollo,")
				.append("stato,")
				.append("applicazione");
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT "
				,fields
				," FROM (  ", 
				"SELECT   "))
				.append(fieldsVistaFascicoloPublic).append(" FROM v_fascicolo_public");
		if (props.isAutPae() && props.getVistaPubblicaIntegrata()) { // se è attiva in configurazione la fusione della
																		// vista pubblica.
			sql.append(" UNION  ").append(" SELECT   ").append(fieldsVistaFascicoloPublic)
					.append(" FROM presentazione_istanza.v_fascicolo_public ");
		}
		sql.append(" )").append(" as autorizzazioni ")
		.append(" WHERE ")
		.append(" ( ")
		.append(" esito <> :nonAutorizzato OR ")
		.append(userCanAccessNegative)
		.append(") ");
		//in caso di utente autenticato e che 
		//ha competenza di accesso al dettaglio della pratica (o per territorio o per organizzazione)
		//dovremmo mostrare anche quelle nonAutorizzate
		parameters.put("nonAutorizzato", EsitoProvvedimento.NON_AUTORIZZATO.name());
		this.sqlWherePubblica(filter, sql, parameters);
		sql = sql.append(" GROUP BY ") // raggruppa per comune intervento...
				.append(fields)
				.append(" ");
		if (filter.getColonna() != null) {
			sql.append(" ORDER BY ").append(filter.getColonna()).append(" ").append(filter.getDirezione().name());
		} else {
			sql.append(" ORDER BY data_rilascio_autorizzazione DESC"); // ordinamento default
		}
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return super.paginatedList(sql.toString(), parameters, fascicoloPublicRowMapper,
				filter.getPage(), filter.getLimit());
	}
	
	/**
	 * query che permette di filtrare le pratiche pubbliche non autorizzate se l'utente ha permessi di accesso a
	 *  sportello o autpae ed ha competenza su territorio o per organizzazione (anche se Funzionario non assegnatario)
	 * @autor Adriano Colaianni
	 * @date 21 giu 2022
	 * @param myOrgMap
	 * @param parameters 
	 * @return
	 */
	private String sqlQueryVisibilitaEsitoNegativo(Map<String, MyOrganizzazioniBean> myOrgMap, Map<String, Object> parameters) {
		StringBuilder query=new StringBuilder();
		String sep=" ";
		if(myOrgMap==null){
			return " false ";	
		} 
		MyOrganizzazioniBean orgAutpae = myOrgMap.get(props.getCodiceApplicazione());
		MyOrganizzazioniBean orgSportello = myOrgMap.get(MyCompetenzaService.PAE_PRES_IST);
		if(orgAutpae!=null && orgAutpae.getOrganizzazioniMap()!=null && 
				ListUtil.isNotEmpty(orgAutpae.getOrganizzazioniMap().keySet())) {
			Set<Integer> idOrgAutpaes = orgAutpae.getOrganizzazioniMap().keySet().stream().map(Integer::parseInt).collect(Collectors.toSet());
			Set<Integer> idComuniAutpaes=new HashSet<>();
			orgAutpae.getOrganizzazioniMap().values().stream().forEach(listaComuni->{
				if(ListUtil.isNotEmpty(listaComuni)) {
				listaComuni.forEach(comune->idComuniAutpaes.add(comune));
				}
				});
			query
			.append("( applicazione='autpae' AND ")//è costante per tutte le trasmissioni
			.append(" ( ")
			.append(" org_creazione in (:idOrgAutpaes) ")
			.append(" OR  ")
			.append(" comune_id in (:idComuniAutpaes) ")
			.append(" ) ")
			.append(") ");
			parameters.put("idOrgAutpaes", idOrgAutpaes);
			parameters.put("idComuniAutpaes", idComuniAutpaes);
			sep=" OR ";
		}
		if(orgSportello!=null && orgSportello.getOrganizzazioniMap()!=null && 
				ListUtil.isNotEmpty(orgSportello.getOrganizzazioniMap().keySet())) {
			Set<Integer> idOrgSportellos = orgSportello.getOrganizzazioniMap().keySet().stream().map(Integer::parseInt).collect(Collectors.toSet());
			Set<Integer> idComuniSportellos=new HashSet<>();
			orgSportello.getOrganizzazioniMap().values().stream().forEach(listaComuni->{
				if(ListUtil.isNotEmpty(listaComuni)) {
					listaComuni.forEach(comune->idComuniSportellos.add(comune));	
				}
				});
			parameters.put("paePresIst", MyCompetenzaService.PAE_PRES_IST);
			query
			.append(sep)
			.append("( applicazione= :paePresIst ")
			.append(" AND ")//è costante per tutte le trasmissioni
			.append(" ( ")
			.append(" org_creazione in (:idOrgSportellos) ");
			parameters.put("idOrgSportellos", idOrgSportellos);
			if(ListUtil.isNotEmpty(idComuniSportellos)) {
				query
				.append(" OR  ")
				.append(" comune_id in (:idComuniSportellos) ");
				parameters.put("idComuniSportellos", idComuniSportellos);
			}
			query
			.append(" ) ")
			.append(") ");
		}
		if(query.length()==0) {
			query.append(" false ");
		}
		return query.toString();
	}

	/**
	 * restituisce i comuneId 
	 * @param codice pratica/fascicolo in v_fascicolo_public
	 * @return
	 */
	public List<Integer> getComuniId(final String codice){
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT comune_id ", " FROM (  ", "SELECT   "))
				.append(fieldsVistaFascicoloPublic).append(" FROM v_fascicolo_public");
		if (props.isAutPae() && props.getVistaPubblicaIntegrata()) { // se è attiva in configurazione la fusione della
																		// vista pubblica.
			sql.append(" UNION  ").append(" SELECT   ").append(fieldsVistaFascicoloPublic)
					.append(" FROM presentazione_istanza.v_fascicolo_public ");
		}
		sql.append(" )").append(" as autorizzazioni ").append(" WHERE  codice = ? ");
		parameters.add(codice);
		return super.queryForList(sql.toString(), Integer.class, parameters);
	}
	
	
	public Integer findByTPraticaId(final Long tPraticaId){
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT id FROM fascicolo WHERE t_pratica_id= ? "));
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(tPraticaId);
		return super.queryForObject(sql.toString(), Integer.class, parameters);
	}
	
	/**
	 * per le putt ci sono casi di stessa tPraticaId e codice diverso 
	 * SELECT * FROM INFO_AUT_PAES_ALFA WHERE T_PRATICA_ID IN(51646,89106,52299,78167,80131,57735,78242,76668,65526,64482,86866,66698);
	 * @autor Adriano Colaianni
	 * @date 6 dic 2021
	 * @param tPraticaId
	 * @param codiceSuap
	 * @return
	 */
	public Integer findByTPraticaIdAndCodice(final Long tPraticaId,final String codiceSuap){
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT id FROM fascicolo WHERE t_pratica_id= ? and codice= ?"));
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(tPraticaId);
		parameters.add(codiceSuap);
		return super.queryForObject(sql.toString(), Integer.class, parameters);
	}
	
	public void aggiornaDataCreazione(final long idFascicolo, final Date dataCreazione) throws Exception
	{
		log.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {}.getClass().getEnclosingMethod().getName() + "'");
		final StringBuilder sql = new StringBuilder("update fascicolo set data_creazione = :data_creazione where id = :id");
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", idFascicolo);
		parameters.put("data_creazione", dataCreazione);
		super.namedUpdate(sql.toString(), parameters);
	}
	
}