package it.eng.tz.puglia.autpae.repository.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneAssegnamento;
import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dbMapping.FascicoloIntervento;
import it.eng.tz.puglia.autpae.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.autpae.dbMapping.TipiEQualificazioni;
import it.eng.tz.puglia.autpae.dbMapping.TipoProcedimento;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.FaseProcedimento;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAssegnamento;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloRowMapper;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.utility.Stringhe;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.orm.repository.GenericCrudDao;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for table fascicolo
 */
public class FascicoloBaseRepository extends GenericCrudDao<FascicoloDTO, FascicoloSearch, Long>
{
	private static final Logger log = LoggerFactory.getLogger(FascicoloBaseRepository.class);
	private FascicoloRowMapper rowMapper = new FascicoloRowMapper();
	
	@Autowired UserUtil userUtil;
	@Autowired GruppiRuoliService gruppiRuoliService;
	@Autowired CommonService commonService;
	@Autowired AnagraficaService anagraficaService;
	
	@Autowired ApplicationProperties props;
	
	@Override
	public List<FascicoloDTO> select() throws Exception	{
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
	}

	@Override
	public long count(FascicoloSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		String schema = props.schemaName() + ".";
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select count(distinct "+Fascicolo.id.getCompleteName()+") AS numero FROM ", Fascicolo.getTableName()));
		aggiungiJoin(filter, schema, sql);
		filter.getSqlWhereClause(sql);
		this.estensioneWhereClause(filter, sql);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filter.getSqlParameters());
		return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	@Override
	public FascicoloDTO find(Long pk) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Fascicolo.getTableName())
		   .append(" left join ")
		   .append(TipoProcedimento.getTableName())
		   .append(" on ")
		   .append(Fascicolo.tipo_procedimento.getCompleteName())
		   .append(" = ")
		   .append(TipoProcedimento.codice.getCompleteName())
		   .append(" where ")
		   .append(Fascicolo.id.getCompleteName())
		   .append(" = :id");
		parameters.put("id", pk);
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), pk);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, rowMapper);
	}

	@Override
	public Long insert(FascicoloDTO entity) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("insert into ")
		   .append(Fascicolo.getTableName())
		   .append(StringUtil.concateneString("(", Fascicolo.ufficio.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.org_creazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.tipo_procedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.oggetto_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.sanatoria.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_delibera.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.numero_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.note.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.stato.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_fascicolo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_richiedente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_localizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_allegati.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_provvedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.deleted.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice_pratica_appptr.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.t_pratica_id.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.username_utente_creazione.columnName(), ")"))
		   .append(" values(")
		   .append(StringUtil.concateneString(":", Fascicolo.ufficio.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.org_creazione.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.tipo_procedimento.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.oggetto_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.sanatoria.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.data_delibera.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.codice_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.numero_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.data_protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.note.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.stato.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.codice.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.vers_fascicolo.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.vers_richiedente.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.vers_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.vers_localizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.vers_allegati.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.vers_provvedimento.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.deleted.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.codice_pratica_appptr.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.t_pratica_id.columnName(), ","))
		   .append(StringUtil.concateneString(":", Fascicolo.username_utente_creazione.columnName(), ")"))
		   
		   .append(" returning ")
		   .append(Fascicolo.id.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.ufficio.columnName(), entity.getUfficio());
		parameters.put(Fascicolo.org_creazione.columnName(), userUtil.getIntegerId(entity.getUfficio()));
		parameters.put(Fascicolo.tipo_procedimento.columnName(), entity.getTipoProcedimento() != null ? entity.getTipoProcedimento().name() : null);
		parameters.put(Fascicolo.oggetto_intervento.columnName(), entity.getOggettoIntervento());
		parameters.put(Fascicolo.sanatoria.columnName(), entity.getSanatoria());
		parameters.put(Fascicolo.data_delibera.columnName(), entity.getDataDelibera());
		parameters.put(Fascicolo.codice_interno_ente.columnName(), entity.getCodiceInternoEnte());
		parameters.put(Fascicolo.numero_interno_ente.columnName(), entity.getNumeroInternoEnte());
		parameters.put(Fascicolo.protocollo.columnName(), entity.getProtocollo());
		parameters.put(Fascicolo.data_protocollo.columnName(), entity.getDataProtocollo());
		parameters.put(Fascicolo.note.columnName(), entity.getNote());
		parameters.put(Fascicolo.stato.columnName(), entity.getStato() != null ? entity.getStato().name() : null);
		parameters.put(Fascicolo.codice.columnName(), entity.getCodice());
		parameters.put(Fascicolo.vers_fascicolo.columnName(), entity.getVersFascicolo());
		parameters.put(Fascicolo.vers_richiedente.columnName(), entity.getVersRichiedente());
		parameters.put(Fascicolo.vers_intervento.columnName(), entity.getVersIntervento());
		parameters.put(Fascicolo.vers_localizzazione.columnName(), entity.getVersLocalizzazione());
		parameters.put(Fascicolo.vers_allegati.columnName(), entity.getVersAllegati());
		parameters.put(Fascicolo.vers_provvedimento.columnName(), entity.getVersProvvedimento());
		parameters.put(Fascicolo.deleted.columnName(), false);
		parameters.put(Fascicolo.username_utente_creazione.columnName(), SecurityUtil.getUsername());
		parameters.put(Fascicolo.codice_pratica_appptr.columnName(), entity.getCodicePraticaAppptr());
		parameters.put(Fascicolo.t_pratica_id.columnName(), entity.gettPraticaId());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
	  //return super.update(sql.toString(), parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}

	@Override
	public int update(FascicoloDTO entity) throws Exception	{
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(StringUtil.concateneString(Fascicolo.ufficio.columnName(), " = :", Fascicolo.ufficio.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.tipo_procedimento.columnName(), " = :", Fascicolo.tipo_procedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.oggetto_intervento.columnName(), " = :", Fascicolo.oggetto_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.sanatoria.columnName(), " = :", Fascicolo.sanatoria.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice_interno_ente.columnName(), " = :", Fascicolo.codice_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.numero_interno_ente.columnName(), " = :", Fascicolo.numero_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.protocollo.columnName(), " = :", Fascicolo.protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_protocollo.columnName(), " = :", Fascicolo.data_protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.note.columnName(), " = :", Fascicolo.note.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.stato.columnName(), " = :", Fascicolo.stato.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice.columnName(), " = :", Fascicolo.codice.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.numero_provvedimento.columnName(), " = :", Fascicolo.numero_provvedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_rilascio_autorizzazione.columnName(), " = :", Fascicolo.data_rilascio_autorizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.esito.columnName(), " = :", Fascicolo.esito.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.rup.columnName(), " = :", Fascicolo.rup.columnName(), ","))
		// .append(StringUtil.concateneString(Fascicolo.tipologia_intervento.columnName(), " = :", Fascicolo.tipologia_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.intervento_dettaglio.columnName(), " = :", Fascicolo.intervento_dettaglio.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.intervento_specifica.columnName(), " = :", Fascicolo.intervento_specifica.columnName(), ","))
		// .append(StringUtil.concateneString(Fascicolo.data_trasmissione.columnName(), " = :", Fascicolo.data_trasmissione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_campionamento.columnName(), " = :", Fascicolo.data_campionamento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_verifica.columnName(), " = :", Fascicolo.data_verifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_ultima_modifica.columnName(), " = :", Fascicolo.data_ultima_modifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.username_utente_ultima_modifica.columnName(), " = :", Fascicolo.username_utente_ultima_modifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.esito_verifica.columnName(), " = :", Fascicolo.esito_verifica.columnName(), ","))
		// .append(StringUtil.concateneString(Fascicolo.stato_registrazione.columnName(), " = :", Fascicolo.stato_registrazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_delibera.columnName(), " = :", Fascicolo.data_delibera.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.delibera_num.columnName(), " = :", Fascicolo.delibera_num.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.oggetto_delibera.columnName(), " = :", Fascicolo.oggetto_delibera.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.info_delibere_precedenti.columnName(), " = :", Fascicolo.info_delibere_precedenti.columnName(), ","))
//		   .append(StringUtil.concateneString(Fascicolo.stato_precedente.columnName(), " = :", Fascicolo.stato_precedente.columnName(), ","))
//		   .append(StringUtil.concateneString(Fascicolo.esito_verifica_precedente.columnName(), " = :", Fascicolo.esito_verifica_precedente.columnName(), ","))
//		   .append(StringUtil.concateneString(Fascicolo.modificabile_fino.columnName(), " = :", Fascicolo.modificabile_fino.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_fascicolo.columnName(), " = :", Fascicolo.vers_fascicolo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_richiedente.columnName(), " = :", Fascicolo.vers_richiedente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_intervento.columnName(), " = :", Fascicolo.vers_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_localizzazione.columnName(), " = :", Fascicolo.vers_localizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_allegati.columnName(), " = :", Fascicolo.vers_allegati.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_provvedimento.columnName(), " = :", Fascicolo.vers_provvedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.descrizione_intervento.columnName(), " = :", Fascicolo.descrizione_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.has_shape.columnName(), " = :", Fascicolo.has_shape.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.tipo_selezione_localizzazione.columnName(), " = :", Fascicolo.tipo_selezione_localizzazione.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.codice_pratica_appptr.columnName(), " = :", Fascicolo.codice_pratica_appptr.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.t_pratica_id.columnName(), " = :", Fascicolo.t_pratica_id.columnName()))
		   .append(" where ")
		   .append(Fascicolo.id.getCompleteName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.ufficio.columnName(), entity.getUfficio());
		parameters.put(Fascicolo.tipo_procedimento.columnName(), entity.getTipoProcedimento() != null ? entity.getTipoProcedimento().name() : null);
		parameters.put(Fascicolo.oggetto_intervento.columnName(), entity.getOggettoIntervento());
		parameters.put(Fascicolo.sanatoria.columnName(), entity.getSanatoria());
		parameters.put(Fascicolo.codice_interno_ente.columnName(), entity.getCodiceInternoEnte());
		parameters.put(Fascicolo.numero_interno_ente.columnName(), entity.getNumeroInternoEnte());
		parameters.put(Fascicolo.protocollo.columnName(), entity.getProtocollo());
		parameters.put(Fascicolo.data_protocollo.columnName(), entity.getDataProtocollo());
		parameters.put(Fascicolo.note.columnName(), entity.getNote());
		parameters.put(Fascicolo.stato.columnName(), entity.getStato() != null ? entity.getStato().name() : null);
		parameters.put(Fascicolo.codice.columnName(), entity.getCodice());
		parameters.put(Fascicolo.numero_provvedimento.columnName(), entity.getNumeroProvvedimento());
		parameters.put(Fascicolo.data_rilascio_autorizzazione.columnName(), entity.getDataRilascioAutorizzazione());
		parameters.put(Fascicolo.esito.columnName(), entity.getEsito() != null ? entity.getEsito().name() : null);
		parameters.put(Fascicolo.rup.columnName(), entity.getRup() != null? entity.getRup().toUpperCase() : null);
	//	parameters.put(Fascicolo.tipologia_intervento.columnName(), entity.getTipologiaIntervento() != null ? entity.getTipologiaIntervento().name() : null);
		parameters.put(Fascicolo.intervento_dettaglio.columnName(), entity.getInterventoDettaglio());
		parameters.put(Fascicolo.intervento_specifica.columnName(), entity.getInterventoSpecifica());
	//	parameters.put(Fascicolo.data_trasmissione.columnName(), entity.getDataTrasmissione());
		parameters.put(Fascicolo.data_campionamento.columnName(), entity.getDataCampionamento());
		parameters.put(Fascicolo.data_verifica.columnName(), entity.getDataVerifica());
		parameters.put(Fascicolo.data_ultima_modifica.columnName(), new Date());
		parameters.put(Fascicolo.username_utente_ultima_modifica.columnName(), SecurityUtil.getUsername());
		parameters.put(Fascicolo.esito_verifica.columnName(), entity.getEsitoVerifica() != null ? entity.getEsitoVerifica().name() : null);
	//	parameters.put(Fascicolo.stato_registrazione.columnName(), entity.getStatoRegistrazione() != null ? entity.getStatoRegistrazione().name() : null);
		parameters.put(Fascicolo.data_delibera.columnName(), entity.getDataDelibera());
		parameters.put(Fascicolo.delibera_num.columnName(), entity.getDeliberaNum());
		parameters.put(Fascicolo.oggetto_delibera.columnName(), entity.getOggettoDelibera());
		parameters.put(Fascicolo.info_delibere_precedenti.columnName(), entity.getInfoDeliberePrecedenti());
		parameters.put(Fascicolo.descrizione_intervento.columnName(), entity.getDescrizioneIntervento());
		parameters.put(Fascicolo.vers_fascicolo.columnName(), entity.getVersFascicolo());
		parameters.put(Fascicolo.vers_richiedente.columnName(), entity.getVersRichiedente());
		parameters.put(Fascicolo.vers_intervento.columnName(), entity.getVersIntervento());
		parameters.put(Fascicolo.vers_localizzazione.columnName(), entity.getVersLocalizzazione());
		parameters.put(Fascicolo.vers_allegati.columnName(), entity.getVersAllegati());
		parameters.put(Fascicolo.vers_provvedimento.columnName(), entity.getVersProvvedimento());
		parameters.put(Fascicolo.has_shape.columnName(), entity.isHasShape());
		parameters.put(Fascicolo.tipo_selezione_localizzazione.columnName(), entity.getTipoSelezioneLocalizzazione()==null?null:entity.getTipoSelezioneLocalizzazione().name());
		parameters.put(Fascicolo.codice_pratica_appptr.columnName(), entity.getCodicePraticaAppptr());
		parameters.put(Fascicolo.t_pratica_id.columnName(), entity.gettPraticaId());
//		parameters.put(Fascicolo.stato_precedente.columnName(), null);
//		parameters.put(Fascicolo.esito_verifica_precedente.columnName(), null);
//		parameters.put(Fascicolo.modificabile_fino.columnName(), null);
		parameters.put(Fascicolo.id.columnName(), entity.getId());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}
	
	
	/**
	 * ci sono anche altri campi oltre quelli dell'update base, ma usato solo per la migrazione
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateForMigration(FascicoloDTO entity) throws Exception	{
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Fascicolo.getTableName())
		   .append(" set ")
		   .append(StringUtil.concateneString(Fascicolo.ufficio.columnName(), " = :", Fascicolo.ufficio.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.tipo_procedimento.columnName(), " = :", Fascicolo.tipo_procedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.oggetto_intervento.columnName(), " = :", Fascicolo.oggetto_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.sanatoria.columnName(), " = :", Fascicolo.sanatoria.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice_interno_ente.columnName(), " = :", Fascicolo.codice_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.numero_interno_ente.columnName(), " = :", Fascicolo.numero_interno_ente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.protocollo.columnName(), " = :", Fascicolo.protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_protocollo.columnName(), " = :", Fascicolo.data_protocollo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.note.columnName(), " = :", Fascicolo.note.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.stato.columnName(), " = :", Fascicolo.stato.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.codice.columnName(), " = :", Fascicolo.codice.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.numero_provvedimento.columnName(), " = :", Fascicolo.numero_provvedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_rilascio_autorizzazione.columnName(), " = :", Fascicolo.data_rilascio_autorizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.esito.columnName(), " = :", Fascicolo.esito.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.rup.columnName(), " = :", Fascicolo.rup.columnName(), ","))
		// .append(StringUtil.concateneString(Fascicolo.tipologia_intervento.columnName(), " = :", Fascicolo.tipologia_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.intervento_dettaglio.columnName(), " = :", Fascicolo.intervento_dettaglio.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.intervento_specifica.columnName(), " = :", Fascicolo.intervento_specifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_trasmissione.columnName(), " = :", Fascicolo.data_trasmissione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_campionamento.columnName(), " = :", Fascicolo.data_campionamento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_verifica.columnName(), " = :", Fascicolo.data_verifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_ultima_modifica.columnName(), " = :", Fascicolo.data_ultima_modifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.username_utente_ultima_modifica.columnName(), " = :", Fascicolo.username_utente_ultima_modifica.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.esito_verifica.columnName(), " = :", Fascicolo.esito_verifica.columnName(), ","))
		// .append(StringUtil.concateneString(Fascicolo.stato_registrazione.columnName(), " = :", Fascicolo.stato_registrazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.data_delibera.columnName(), " = :", Fascicolo.data_delibera.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.delibera_num.columnName(), " = :", Fascicolo.delibera_num.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.oggetto_delibera.columnName(), " = :", Fascicolo.oggetto_delibera.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.info_delibere_precedenti.columnName(), " = :", Fascicolo.info_delibere_precedenti.columnName(), ","))
//		   .append(StringUtil.concateneString(Fascicolo.stato_precedente.columnName(), " = :", Fascicolo.stato_precedente.columnName(), ","))
//		   .append(StringUtil.concateneString(Fascicolo.esito_verifica_precedente.columnName(), " = :", Fascicolo.esito_verifica_precedente.columnName(), ","))
//		   .append(StringUtil.concateneString(Fascicolo.modificabile_fino.columnName(), " = :", Fascicolo.modificabile_fino.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_fascicolo.columnName(), " = :", Fascicolo.vers_fascicolo.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_richiedente.columnName(), " = :", Fascicolo.vers_richiedente.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_intervento.columnName(), " = :", Fascicolo.vers_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_localizzazione.columnName(), " = :", Fascicolo.vers_localizzazione.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_allegati.columnName(), " = :", Fascicolo.vers_allegati.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.vers_provvedimento.columnName(), " = :", Fascicolo.vers_provvedimento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.descrizione_intervento.columnName(), " = :", Fascicolo.descrizione_intervento.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.has_shape.columnName(), " = :", Fascicolo.has_shape.columnName(), ","))
		   .append(StringUtil.concateneString(Fascicolo.tipo_selezione_localizzazione.columnName(), " = :", Fascicolo.tipo_selezione_localizzazione.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.codice_pratica_appptr.columnName(), " = :", Fascicolo.codice_pratica_appptr.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.t_pratica_id.columnName(), " = :", Fascicolo.t_pratica_id.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.username_utente_creazione.columnName(), " = :", Fascicolo.username_utente_creazione.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.username_utente_trasmissione.columnName(), " = :", Fascicolo.username_utente_trasmissione.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.data_creazione.columnName(), " = :", Fascicolo.data_creazione.columnName(),","))
		   .append(StringUtil.concateneString(Fascicolo.info_complete.columnName(), " = :", Fascicolo.info_complete.columnName()))
		   
		   .append(" where ")
		   .append(Fascicolo.id.getCompleteName())
		   .append(" = :")
		   .append(Fascicolo.id.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Fascicolo.ufficio.columnName(), entity.getUfficio());
		parameters.put(Fascicolo.tipo_procedimento.columnName(), entity.getTipoProcedimento() != null ? entity.getTipoProcedimento().name() : null);
		parameters.put(Fascicolo.oggetto_intervento.columnName(), entity.getOggettoIntervento());
		parameters.put(Fascicolo.sanatoria.columnName(), entity.getSanatoria());
		parameters.put(Fascicolo.codice_interno_ente.columnName(), entity.getCodiceInternoEnte());
		parameters.put(Fascicolo.numero_interno_ente.columnName(), entity.getNumeroInternoEnte());
		parameters.put(Fascicolo.protocollo.columnName(), entity.getProtocollo());
		parameters.put(Fascicolo.data_protocollo.columnName(), entity.getDataProtocollo());
		parameters.put(Fascicolo.note.columnName(), entity.getNote());
		parameters.put(Fascicolo.stato.columnName(), entity.getStato() != null ? entity.getStato().name() : null);
		parameters.put(Fascicolo.codice.columnName(), entity.getCodice());
		parameters.put(Fascicolo.numero_provvedimento.columnName(), entity.getNumeroProvvedimento());
		parameters.put(Fascicolo.data_rilascio_autorizzazione.columnName(), entity.getDataRilascioAutorizzazione());
		parameters.put(Fascicolo.esito.columnName(), entity.getEsito() != null ? entity.getEsito().name() : null);
		parameters.put(Fascicolo.rup.columnName(), entity.getRup() != null? entity.getRup().toUpperCase() : null);
	//	parameters.put(Fascicolo.tipologia_intervento.columnName(), entity.getTipologiaIntervento() != null ? entity.getTipologiaIntervento().name() : null);
		parameters.put(Fascicolo.intervento_dettaglio.columnName(), entity.getInterventoDettaglio());
		parameters.put(Fascicolo.intervento_specifica.columnName(), entity.getInterventoSpecifica());
	    parameters.put(Fascicolo.data_trasmissione.columnName(), entity.getDataTrasmissione());
		parameters.put(Fascicolo.data_campionamento.columnName(), entity.getDataCampionamento());
		parameters.put(Fascicolo.data_verifica.columnName(), entity.getDataVerifica());
		parameters.put(Fascicolo.data_ultima_modifica.columnName(), new Date());
		parameters.put(Fascicolo.username_utente_ultima_modifica.columnName(), SecurityUtil.getUsername());
		parameters.put(Fascicolo.esito_verifica.columnName(), entity.getEsitoVerifica() != null ? entity.getEsitoVerifica().name() : null);
	//	parameters.put(Fascicolo.stato_registrazione.columnName(), entity.getStatoRegistrazione() != null ? entity.getStatoRegistrazione().name() : null);
		parameters.put(Fascicolo.data_delibera.columnName(), entity.getDataDelibera());
		parameters.put(Fascicolo.delibera_num.columnName(), entity.getDeliberaNum());
		parameters.put(Fascicolo.oggetto_delibera.columnName(), entity.getOggettoDelibera());
		parameters.put(Fascicolo.info_delibere_precedenti.columnName(), entity.getInfoDeliberePrecedenti());
		parameters.put(Fascicolo.descrizione_intervento.columnName(), entity.getDescrizioneIntervento());
		parameters.put(Fascicolo.vers_fascicolo.columnName(), entity.getVersFascicolo());
		parameters.put(Fascicolo.vers_richiedente.columnName(), entity.getVersRichiedente());
		parameters.put(Fascicolo.vers_intervento.columnName(), entity.getVersIntervento());
		parameters.put(Fascicolo.vers_localizzazione.columnName(), entity.getVersLocalizzazione());
		parameters.put(Fascicolo.vers_allegati.columnName(), entity.getVersAllegati());
		parameters.put(Fascicolo.vers_provvedimento.columnName(), entity.getVersProvvedimento());
		parameters.put(Fascicolo.has_shape.columnName(), entity.isHasShape());
		parameters.put(Fascicolo.tipo_selezione_localizzazione.columnName(),
				entity.getTipoSelezioneLocalizzazione()==null?null:entity.getTipoSelezioneLocalizzazione().name());
		parameters.put(Fascicolo.codice_pratica_appptr.columnName(), entity.getCodicePraticaAppptr());
		parameters.put(Fascicolo.t_pratica_id.columnName(), entity.gettPraticaId());
		parameters.put(Fascicolo.username_utente_creazione.columnName(), entity.getUsernameUtenteCreazione());
		parameters.put(Fascicolo.username_utente_ultima_modifica.columnName(), entity.getUsernameUtenteUltimaModifica());
		parameters.put(Fascicolo.username_utente_trasmissione.columnName(), entity.getUsernameUtenteTrasmissione());
		parameters.put(Fascicolo.data_creazione.columnName(), entity.getDataCreazione());
		parameters.put(Fascicolo.info_complete.columnName(), entity.getInfoComplete());
//		parameters.put(Fascicolo.stato_precedente.columnName(), null);
//		parameters.put(Fascicolo.esito_verifica_precedente.columnName(), null);
//		parameters.put(Fascicolo.modificabile_fino.columnName(), null);
		parameters.put(Fascicolo.id.columnName(), entity.getId());
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	@Override
	public int delete(FascicoloSearch filter) throws Exception {  // al momento la cancellazione fisica non è prevista!
		log.error("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		throw new Exception("Questo metodo non deve essere mai richiamato!");
		
	//	StringBuilder sql = new StringBuilder("delete from ").append(Fascicolo.getTableName());
	//	filter.getSqlWhereClause(sql);
	//	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filter.getSqlParameters());
	//	return namedJdbcTemplate.update(sql.toString(), filter.getSqlParameters());
	}

	
	@Override
	public PaginatedList<FascicoloDTO> search(FascicoloSearch filter) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		String schema = props.schemaName() + ".";
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "))			
						 .append(Fascicolo.id.getCompleteName())
		 	.append(", ").append(Fascicolo.ufficio.getCompleteName())
		 	.append(", ").append(Fascicolo.org_creazione.getCompleteName())
			.append(", ").append(Fascicolo.tipo_procedimento.getCompleteName())
			.append(", ").append(Fascicolo.oggetto_intervento.getCompleteName())
			.append(", ").append(Fascicolo.sanatoria.getCompleteName())
			.append(", ").append(Fascicolo.codice_interno_ente.getCompleteName())
			.append(", ").append(Fascicolo.numero_interno_ente.getCompleteName())
			.append(", ").append(Fascicolo.protocollo.getCompleteName())
			.append(", ").append(Fascicolo.data_protocollo.getCompleteName())
			.append(", ").append(Fascicolo.note.getCompleteName())
			.append(", ").append(Fascicolo.stato.getCompleteName())
			.append(", ").append(Fascicolo.stato_precedente.getCompleteName())
			.append(", ").append(Fascicolo.codice.getCompleteName())
			.append(", ").append(Fascicolo.data_creazione.getCompleteName())
			.append(", ").append(Fascicolo.data_ultima_modifica.getCompleteName())
			.append(", ").append(Fascicolo.username_utente_creazione.getCompleteName())
			.append(", ").append(Fascicolo.username_utente_ultima_modifica.getCompleteName())
			.append(", ").append(Fascicolo.username_utente_trasmissione.getCompleteName())
			.append(", ").append(Fascicolo.denominazione_utente_trasmissione.getCompleteName())
			.append(", ").append(Fascicolo.email_utente_trasmissione.getCompleteName())
			.append(", ").append(Fascicolo.data_trasmissione.getCompleteName())
			.append(", ").append(Fascicolo.data_campionamento.getCompleteName())
			.append(", ").append(Fascicolo.data_verifica.getCompleteName())
			.append(", ").append(Fascicolo.esito_verifica.getCompleteName())
			.append(", ").append(Fascicolo.esito_verifica_precedente.getCompleteName())
			.append(", ").append(Fascicolo.deleted.getCompleteName())
			.append(", ").append(Fascicolo.modificabile_fino.getCompleteName())
			.append(", ").append(Fascicolo.numero_provvedimento.getCompleteName())
			.append(", ").append(Fascicolo.data_rilascio_autorizzazione.getCompleteName())
			.append(", ").append(Fascicolo.esito.getCompleteName())
			.append(", ").append(Fascicolo.rup.getCompleteName())
			.append(", ").append(Fascicolo.esito_data_rilascio_autorizzazione.getCompleteName())
			.append(", ").append(Fascicolo.esito_numero_provvedimento.getCompleteName())																   
			.append(", ").append(Fascicolo.intervento_dettaglio.getCompleteName())
			.append(", ").append(Fascicolo.intervento_specifica.getCompleteName())
			.append(", ").append(Fascicolo.vers_fascicolo.getCompleteName())
			.append(", ").append(Fascicolo.vers_richiedente.getCompleteName())
			.append(", ").append(Fascicolo.vers_intervento.getCompleteName())
			.append(", ").append(Fascicolo.vers_localizzazione.getCompleteName())
			.append(", ").append(Fascicolo.vers_allegati.getCompleteName())
			.append(", ").append(Fascicolo.vers_provvedimento.getCompleteName())
			.append(", ").append(Fascicolo.info_complete.getCompleteName())
			.append(", ").append(Fascicolo.data_delibera.getCompleteName())
			.append(", ").append(Fascicolo.delibera_num.getCompleteName())
			.append(", ").append(Fascicolo.oggetto_delibera.getCompleteName())
			.append(", ").append(Fascicolo.info_delibere_precedenti.getCompleteName())
			.append(", ").append(Fascicolo.descrizione_intervento.getCompleteName())
			.append(", ").append(Fascicolo.has_shape.getCompleteName())
			.append(", ").append(Fascicolo.tipo_selezione_localizzazione.getCompleteName())
			.append(", ").append(Fascicolo.codice_pratica_appptr.getCompleteName())
			.append(", ").append(Fascicolo.t_pratica_id.getCompleteName())
			.append(", ").append(Fascicolo.stato_trasmissione.getCompleteName())
			;
		
		sql.append(" from ")
		   .append(schema).append(Fascicolo.getTableName())
		   .append(" left join ")
		   .append(schema).append(TipoProcedimento.getTableName())
		   .append(" on ")
		   .append(Fascicolo.tipo_procedimento.getCompleteName())
		   .append(" = ")
		   .append(TipoProcedimento.codice.getCompleteName());

		aggiungiJoin(filter, schema, sql);
	 // filter.setColonna(Fascicolo.codice);
		filter.getSqlWhereClause(sql);
		this.estensioneWhereClause(filter, sql);
		filter.getSqlOrderByClause(sql);
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), filter.getSqlParameters());
		return super.paginatedList(sql.toString(), filter.getSqlParameters(), rowMapper, filter.getPage(), filter.getLimit());
	}

	private void aggiungiJoin(FascicoloSearch filter, String schema, StringBuilder sql) {
		if (filter.isRicercaPubblica()==false || StringUtil.isNotEmpty(filter.getComuneIntervento())) {
			sql .append(" left join ")
				.append(schema)
				.append(LocalizzazioneIntervento.getTableName())
				.append(" on ")
				.append(Fascicolo.id.getCompleteName())
				.append(" = ")
				.append(LocalizzazioneIntervento.pratica_id.getCompleteName());
		}
		//if (filter.isRicercaPubblica()==false && (userUtil.getRuolo()==Ruoli.FUNZIONARIO && (userUtil.getGruppo()==Gruppi.ETI_ || userUtil.getGruppo()==Gruppi.SBAP_))) {
		if (filter.isRicercaPubblica()==false && (filter.getRuolo()==Ruoli.FUNZIONARIO && (filter.getGruppo()==Gruppi.ETI_ || filter.getGruppo()==Gruppi.SBAP_))) {
			sql .append(" left join ")
				.append(schema)
				.append(AssegnamentoFascicolo.getTableName())
				.append(" on ")
				.append(Fascicolo.id.getCompleteName())
				.append(" = ")
				.append(AssegnamentoFascicolo.id_fascicolo.getCompleteName());
			if(filter.getIdOrganizzazioneLoggato()!=null) {
				//l'assegnazione viene specializzata per l'organizzazione con cui sono loggato
				sql.append(" and ")
				.append(AssegnamentoFascicolo.id_organizzazione.getCompleteName())
				.append(" = ")
				.append(filter.getIdOrganizzazioneLoggato());
			}
			//nel caso fosse disabilitata l'assegnazione
			if(filter.getIdOrganizzazioneLoggato()!=null) {
				sql .append(" left join ")
				.append(schema)
				.append(ConfigurazioneAssegnamento.getTableName())
				.append(" on ")
				.append(ConfigurazioneAssegnamento.id_organizzazione.getCompleteName())
				.append(" = ")
				.append(filter.getIdOrganizzazioneLoggato())
				.append(" and ")
				.append(ConfigurazioneAssegnamento.fase.getCompleteName())
				.append(" = ")
				.append("'"+FaseProcedimento.REVISIONE.name()+"' ");	
			}
		}
		if (filter.getTipologiaIntervento()!=null) {
			sql .append(" left join ")
				.append(schema)
				.append(FascicoloIntervento.getTableName())
				.append(" on ")
				.append(Fascicolo.id.getCompleteName())
				.append(" = ")
				.append(FascicoloIntervento.id_fascicolo.getCompleteName())
				.append(" left join ")
				.append(schema)
				.append(TipiEQualificazioni.getTableName())
				.append(" on ")
				.append(TipiEQualificazioni.id.getCompleteName())
				.append(" = ")
				.append(FascicoloIntervento.id_tipi_qualificazioni.getCompleteName());
		}
	}

	
	private void estensioneWhereClause(FascicoloSearch filter, StringBuilder sql,
										List<Integer> entiDiCompetenza,
										String organizzazioneLoggato, 
										Gruppi gruppo, Ruoli ruolo,
										Integer idOrganizzazione,
										String usernameLogged) throws Exception {
		if(StringUtil.isNotEmpty(filter.getComuneIntervento()))
		{
			String codiceCatastale = anagraficaService.getCodCatastaleFromIstat(filter.getComuneIntervento());
			int idEnte = commonService.findEnteByCodice(codiceCatastale).getId();
			
			sql.append(" and ")
			   .append(LocalizzazioneIntervento.comune_id.getCompleteName())
			   .append(" = " + idEnte);
		}
		if (filter.getTipologiaIntervento()!=null) {
			sql.append(" and ")
			   .append(TipiEQualificazioni.id.getCompleteName())
			   .append(" = " + filter.getTipologiaIntervento().getTextValue())
			   .append(" and ")
			   .append(TipiEQualificazioni.zona.getCompleteName())
			   .append(" = 1");
		}
		if (filter.isRicercaPubblica()==false) {  // cioè solo nel caso di utente loggato
			try {
				
			 // StringBuilder sql = filter.getActualSqlWhereClause();

				if (!organizzazioneLoggato.toUpperCase().equals(Gruppi.ADMIN.name())) {
					sql .append(" AND ( ")
						.append("( "+Fascicolo.stato  .getCompleteName()+" = "  +Stringhe.apicizza(StatoFascicolo.WORKING.name()))
						.append(" and ")
						.append(	 Fascicolo.ufficio.getCompleteName()+" LIKE :organizzazioneLoggato )")
						.append(" OR ")
						.append("( "+Fascicolo.stato  .getCompleteName()+" = "  +Stringhe.apicizza(StatoFascicolo.TRANSMITTED.name()))
						.append(" and ( ")
						.append(	 Fascicolo.ufficio.getCompleteName()+" LIKE :organizzazioneLoggato")
						.append(" or ")
						.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza)"+" )"+" )")
						.append(" OR ")
						.append("( "+Fascicolo.stato  .getCompleteName()+" = "  +Stringhe.apicizza(StatoFascicolo.SELECTED.name()))
						.append(" and ( ")
						.append(	 Fascicolo.ufficio.getCompleteName()+" LIKE :organizzazioneLoggato")
						.append(" or ")
						.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza)"+" )"+" )")
						.append(" OR ")
						.append("( "+Fascicolo.stato  .getCompleteName()+" = "  +Stringhe.apicizza(StatoFascicolo.FINISHED.name()))
						.append(" and ( ")
						.append(	 Fascicolo.ufficio.getCompleteName()+" LIKE :organizzazioneLoggato")
						.append(" or ")
						.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza)"+" )"+" )")
						.append(" OR ")
						.append("( "+Fascicolo.stato  .getCompleteName()+" = "  +Stringhe.apicizza(StatoFascicolo.ON_MODIFY.name()))
						.append(" and ( ")
						.append(	 Fascicolo.ufficio.getCompleteName()+" LIKE :organizzazioneLoggato")
						.append(" or ")
						.append(LocalizzazioneIntervento.comune_id.getCompleteName()+" IN (:entiDiCompetenza)"+" )"+" )")
						.append(" OR ")
						.append("( "+Fascicolo.stato  .getCompleteName()+" = "  +Stringhe.apicizza(StatoFascicolo.CANCELLED.name()))
						.append(" and ")
						.append(	 Fascicolo.ufficio.getCompleteName()+" LIKE :organizzazioneLoggato )")
						.append(" )");
					filter.getSqlParameters().put("organizzazioneLoggato", StringUtil.convertRightLike(organizzazioneLoggato));
					//Fix: Se l'oranizzazione loggata è ED_, non bisogna guardare gli enti di competenza nella where (si passa quindi lista vuota), altrimenti, nel caso di ente delegato regione, 
					//verrebbero prese tutte le pratiche dato che ha tutti gli enti nella propria competenza
					boolean isEnteDelegato=organizzazioneLoggato.startsWith(Gruppi.ED_.name());
					filter.getSqlParameters().put("entiDiCompetenza"	 , (isEnteDelegato || (entiDiCompetenza!=null && entiDiCompetenza.isEmpty())) ? null : entiDiCompetenza);
					
					if (ruolo==Ruoli.FUNZIONARIO && (gruppo==Gruppi.ETI_ || gruppo==Gruppi.SBAP_)) {
						sql.append(" AND ")
							.append("( ")
							.append(AssegnamentoFascicolo.username_funzionario.getCompleteName() + " = :usernameFunzionario")
							.append(" OR ")
							.append(" NOT " +Fascicolo.t_pratica_id.getCompleteName() + " IS NULL "); //pratica migrata viene vista anche dai funzionari non assegnati
						if(idOrganizzazione!=null) {
							sql.append(" OR ")
							.append(Fascicolo.org_creazione.getCompleteName() + " = :idOrganizzazione "); //appartengo all'organizzazione che ha creato la pratica e quindi sono assimilabile ad ED (caso di ETI in putt con possibilità di trasmissione)
							filter.getSqlParameters().put("idOrganizzazione", idOrganizzazione);
							//altra condizione di esclusione del filtro per i funzionari è il caso di configurazione assegnazione DISATTIVATA
							sql
							.append(" OR ")
							.append(" ( ")
							.append(ConfigurazioneAssegnamento.id_organizzazione.getCompleteName() + " = :idOrganizzazione ") //ho trovato configurazione assegnamento per mia organizzazione
							.append(" AND  ")
							.append(ConfigurazioneAssegnamento.criterio_assegnamento.getCompleteName() + " = ")
							.append(" '"+TipoAssegnamento.DISATTIVATA.name()+"' ")//ed è disattivata
							.append(" ) ");
						}
						sql.append(" ) ");
						filter.getSqlParameters().put("usernameFunzionario", usernameLogged);
					}
					//se è soprintendenza devo filtrare solo sui tipi procedimento di sua competenza
					boolean isSBAP=organizzazioneLoggato.startsWith(Gruppi.SBAP_.name());
					if(isSBAP) {
						sql .append(" AND ")
						.append(Fascicolo.tipo_procedimento.getCompleteName())
						.append(" IN (SELECT codice FROM "+TipoProcedimento.getTableName()+" WHERE invia_email=true ) ");
					}
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	private void estensioneWhereClause(FascicoloSearch filter, StringBuilder sql) throws Exception {
		String userName=SecurityUtil.getUsername();
		if(StringUtil.isNotEmpty(filter.getUsernameLoggato())){
			userName=filter.getUsernameLoggato();
		}
		this.estensioneWhereClause(filter, sql, filter.getEntiDiCompetenza(),
				filter.getOrganizzazioneLoggato(),filter.getGruppo(),filter.getRuolo(),
				filter.getIdOrganizzazioneLoggato(),
				userName);
	}
	
}