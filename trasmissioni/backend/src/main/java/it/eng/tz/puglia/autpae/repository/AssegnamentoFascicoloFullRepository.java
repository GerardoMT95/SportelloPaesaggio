package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.autpae.dto.TabelleAssegnamentoFascicoliDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.repository.base.AssegnamentoFascicoloBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.custom.TabelleAssegnamentoFascicoliRowMapper;
import it.eng.tz.puglia.autpae.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for AssegnamentoFascicolo
 */
@Repository
public class AssegnamentoFascicoloFullRepository extends AssegnamentoFascicoloBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(AssegnamentoFascicoloFullRepository.class);
	
	@Autowired private UserUtil userUtil;
	
	
	public PaginatedList<TabelleAssegnamentoFascicoliDTO> getFascicoliAssegnati(TabelleAssegnamentoFascicoliSearch filters) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "SELECT "
				 								+ Fascicolo			   .id				  		 .getCompleteName() + ", "
				 								+ Fascicolo			   .stato			  		 .getCompleteName() + ", " 
				 								+ Fascicolo			   .codice			  		 .getCompleteName() + ", " 
				 								+ Fascicolo			   .tipo_procedimento 		 .getCompleteName() + ", " 
				 								+ Fascicolo			   .oggetto_intervento		 .getCompleteName() + ", " 
				 								+ AssegnamentoFascicolo.username_funzionario	 .getCompleteName() + ", " 
				 								+ AssegnamentoFascicolo.denominazione_funzionario.getCompleteName() + ", " 
				 								+ AssegnamentoFascicolo.num_assegnazioni		 .getCompleteName() + ", "
				 					//			+ AssegnamentoFascicolo.data_assegnazione		 .getCompleteName() + ", "	// per ora non c'è sul DB in questa tabella 
				 								+ AssegnamentoFascicolo.data_operazione			 .getCompleteName()
				 								+ " FROM "
				 								+ Fascicolo.getTableName()
				 							    + " left join "
				 							    + AssegnamentoFascicolo.getTableName()
				 							    + " on "
				 							    + Fascicolo.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 								+ " WHERE "
				 								+ AssegnamentoFascicolo.id_organizzazione.getCompleteName()
				 								+ " = "
				 								+ ":idOrganizzazione"
				 								+ " AND "
				 							    + Fascicolo.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 								+ AssegnamentoFascicolo.username_funzionario.getCompleteName()
				 								+ " IS NOT NULL"
				 								);
		
		if (StringUtils.isNotEmpty(filters.getCodice())) {
															sql = sql.concat( " AND "
																			+ Fascicolo.codice.getCompleteName()
																			+ " ILIKE "
																			+ ":codice "
																			);
		}
		
		List<String> statiConsentiti = new ArrayList<>();
					 statiConsentiti.add(StatoFascicolo.TRANSMITTED.name());
					 statiConsentiti.add(StatoFascicolo.SELECTED.name());
					 statiConsentiti.add(StatoFascicolo.FINISHED.name());
					 statiConsentiti.add(StatoFascicolo.ON_MODIFY.name());
		//			 statiConsentiti.add(StatoFascicolo.CANCELLED.name());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("statiConsentiti" , statiConsentiti);
					parameters.put("codice"    		 , (filters.getCodice()!=null) ? StringUtil.convertRightLike(filters.getCodice()) : "DUMMY");

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return super.paginatedList(sql.toString(), parameters, new TabelleAssegnamentoFascicoliRowMapper(), filters.getPage(), filters.getLimit());
	}
	
	public List<String> autocompleteCodiceFascicoliAssegnati(String codice) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "SELECT "
				 								+ Fascicolo.codice.getCompleteName()
				 								+ " FROM "
				 								+ Fascicolo.getTableName()
				 							    + " left join "
				 							    + AssegnamentoFascicolo.getTableName()
				 							    + " on "
				 							    + Fascicolo.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 								+ " WHERE "
				 								+ AssegnamentoFascicolo.id_organizzazione.getCompleteName()
				 								+ " = "
				 								+ ":idOrganizzazione"
				 								+ " AND "
				 							    + Fascicolo.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 								+ AssegnamentoFascicolo.username_funzionario.getCompleteName()
				 								+ " IS NOT NULL"
				 								);
		
		if (StringUtils.isNotEmpty(codice)) {
												sql = sql.concat( " AND "
																+ Fascicolo.codice.getCompleteName()
																+ " ILIKE "
																+ ":codice "
																);
		}
		
		List<String> statiConsentiti = new ArrayList<>();
					 statiConsentiti.add(StatoFascicolo.TRANSMITTED.name());
					 statiConsentiti.add(StatoFascicolo.SELECTED.name());
					 statiConsentiti.add(StatoFascicolo.FINISHED.name());
					 statiConsentiti.add(StatoFascicolo.ON_MODIFY.name());
		//			 statiConsentiti.add(StatoFascicolo.CANCELLED.name());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("statiConsentiti" , statiConsentiti);
					parameters.put("codice"    		 , (codice!=null) ? StringUtil.convertRightLike(codice) : "DUMMY");

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, String.class);
	}
	
	public PaginatedList<TabelleAssegnamentoFascicoliDTO> getFascicoliNonAssegnati(TabelleAssegnamentoFascicoliSearch filters, Collection<Integer> entiDiCompetenza) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "SELECT "
				 								+ Fascicolo			   .id				  		 .getCompleteName() + ", "
				 								+ Fascicolo			   .stato			  		 .getCompleteName() + ", " 
				 								+ Fascicolo			   .codice			  		 .getCompleteName() + ", " 
				 								+ Fascicolo			   .tipo_procedimento 		 .getCompleteName() + ", " 
				 								+ Fascicolo			   .oggetto_intervento		 .getCompleteName() + ", " 
				 								+ AssegnamentoFascicolo.username_funzionario	 .getCompleteName() + ", " 
				 								+ AssegnamentoFascicolo.denominazione_funzionario.getCompleteName() + ", " 
				 								+ AssegnamentoFascicolo.num_assegnazioni		 .getCompleteName() + ", "
				 					//			+ AssegnamentoFascicolo.data_assegnazione		 .getCompleteName() + ", "	// per ora non c'è sul DB in questa tabella 
				 								+ AssegnamentoFascicolo.data_operazione			 .getCompleteName()
				 								+ " FROM "
				 								+ LocalizzazioneIntervento.getTableName()
				 								+ ", "
				 								+ Fascicolo.getTableName()
				 							    + " left join "
				 							    + AssegnamentoFascicolo.getTableName()
				 							    + " on "
				 							    + Fascicolo.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 								+ " WHERE "
				 								+ LocalizzazioneIntervento.pratica_id.getCompleteName()
				 								+ " = "
				 								+ Fascicolo.id.getCompleteName()
				 								+ " AND "
				 								+ LocalizzazioneIntervento.comune_id.getCompleteName()
				 								+ " IN (:entiDiCompetenza)"
				 								+ " AND "
				 							    + Fascicolo.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 							    + "("
				 								+ AssegnamentoFascicolo.username_funzionario.getCompleteName() + " IS NULL"
				 								+ " OR ("
				 								+ 		AssegnamentoFascicolo.username_funzionario.getCompleteName() + " IS NOT NULL"
				 								+ 		" AND "
				 								+ 		AssegnamentoFascicolo.id_organizzazione   .getCompleteName() + " <> :idOrganizzazione"
				 								+ 	  ")"
				 								+ ")"
				 								);
		
		if (StringUtils.isNotEmpty(filters.getCodice())) {
															sql = sql.concat( " AND "
																			+ Fascicolo.codice.getCompleteName()
																			+ " ILIKE "
																			+ ":codice "
																			);
		}
		
		List<String> statiConsentiti = new ArrayList<>();
					 statiConsentiti.add(StatoFascicolo.TRANSMITTED.name());
					 statiConsentiti.add(StatoFascicolo.SELECTED.name());
					 statiConsentiti.add(StatoFascicolo.FINISHED.name());
					 statiConsentiti.add(StatoFascicolo.ON_MODIFY.name());
		//			 statiConsentiti.add(StatoFascicolo.CANCELLED.name());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
				 	parameters.put("entiDiCompetenza", (entiDiCompetenza!=null && entiDiCompetenza.isEmpty()) ? null : entiDiCompetenza);
				 	parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("statiConsentiti" , statiConsentiti);
					parameters.put("codice"    		 , (filters.getCodice()!=null) ? StringUtil.convertRightLike(filters.getCodice()) : "DUMMY");

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return super.paginatedList(sql.toString(), parameters, new TabelleAssegnamentoFascicoliRowMapper(), filters.getPage(), filters.getLimit());
	}
	
	public List<String> autocompleteCodiceFascicoliNonAssegnati(String codice, Collection<Integer> entiDiCompetenza) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "SELECT "
				 								+ Fascicolo.codice.getCompleteName()
				 								+ " FROM "
				 								+ LocalizzazioneIntervento.getTableName()
				 								+ ", "
				 								+ Fascicolo.getTableName()
				 							    + " left join "
				 							    + AssegnamentoFascicolo.getTableName()
				 							    + " on "
				 							    + Fascicolo.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 								+ " WHERE "
				 								+ LocalizzazioneIntervento.pratica_id.getCompleteName()
				 								+ " = "
				 								+ Fascicolo.id.getCompleteName()
				 								+ " AND "
				 								+ LocalizzazioneIntervento.comune_id.getCompleteName()
				 								+ " IN (:entiDiCompetenza)"
				 								+ " AND "
				 							    + Fascicolo.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 							    + "("
				 								+ AssegnamentoFascicolo.username_funzionario.getCompleteName() + " IS NULL"
				 								+ " OR ("
				 								+ 		AssegnamentoFascicolo.username_funzionario.getCompleteName() + " IS NOT NULL"
				 								+ 		" AND "
				 								+ 		AssegnamentoFascicolo.id_organizzazione   .getCompleteName() + " <> :idOrganizzazione"
				 								+ 	  ")"
				 								+ ")"
				 								);
		
		if (StringUtils.isNotEmpty(codice)) {
												sql = sql.concat( " AND "
																+ Fascicolo.codice.getCompleteName()
																+ " ILIKE "
																+ ":codice "
																);
		}
		
		List<String> statiConsentiti = new ArrayList<>();
					 statiConsentiti.add(StatoFascicolo.TRANSMITTED.name());
					 statiConsentiti.add(StatoFascicolo.SELECTED.name());
					 statiConsentiti.add(StatoFascicolo.FINISHED.name());
					 statiConsentiti.add(StatoFascicolo.ON_MODIFY.name());
		//			 statiConsentiti.add(StatoFascicolo.CANCELLED.name());
		
		Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("entiDiCompetenza", (entiDiCompetenza!=null && entiDiCompetenza.isEmpty()) ? null : entiDiCompetenza);
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("statiConsentiti" , statiConsentiti);
					parameters.put("codice"    		 , (codice!=null) ? StringUtil.convertRightLike(codice) : "DUMMY");

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, String.class);
	}
	
}