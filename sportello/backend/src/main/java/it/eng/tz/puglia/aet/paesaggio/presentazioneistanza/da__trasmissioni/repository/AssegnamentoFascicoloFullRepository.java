package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.AssegnamentoFascicolo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping.Pratica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.rowMapper.TabelleAssegnamentoFascicoliRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.FasiAssegnazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for AssegnamentoFascicolo
 */
@Repository
public class AssegnamentoFascicoloFullRepository extends AssegnamentoFascicoloBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(AssegnamentoFascicoloFullRepository.class);
	
	@Autowired private UserUtil userUtil;
	
	
	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> getFascicoliAssegnati(TabelleAssegnamentoFascicoliSearch filters) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		String sql = StringUtil.concateneString ("SELECT "
				 								+ fields
				 								+ " FROM "
				 								+ Pratica.getTableName()
				 							    + " left join "
				 							    + " v_assegnazione as "+ AssegnamentoFascicolo.getTableName()
				 							    + " on "
				 							    + Pratica.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 								+ " WHERE "
				 								+ AssegnamentoFascicolo.id_organizzazione.getCompleteName()
				 								+ " = "
				 								+ ":idOrganizzazione"
				 								+ " AND "
				 								+ AssegnamentoFascicolo.fase.getCompleteName()
				 								+ " = "
				 								+ ":faseIstruttoria "
				 								+ " AND "
				 							    + Pratica.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 								+ AssegnamentoFascicolo.username_utente.getCompleteName()
				 								+ " IS NOT NULL"
				 								);
		
		if (StringUtils.isNotEmpty(filters.getCodice())) {
															sql = sql.concat( " AND "
																			+ Pratica.codice_pratica_appptr.getCompleteName()
																			+ " ILIKE "
																			+ ":codice "
																			);
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("statiConsentiti" , AttivitaDaEspletare.getStatiIstruttoriaName());
					parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
					parameters.put("codice"    		 , (filters.getCodice()!=null) ? StringUtil.convertRightLike(filters.getCodice()) : "DUMMY");
		sql = sql.concat( " GROUP BY " +fields);			
		log.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return super.paginatedList(sql.toString(), parameters, new TabelleAssegnamentoFascicoliRowMapper(), filters.getPage(), filters.getLimit());
	}
	
	public List<String> autocompleteCodiceFascicoliAssegnati(String codice) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "SELECT distinct "
				 								+ Pratica.codice_pratica_appptr.getCompleteName()
				 								+ " FROM "
				 								+ Pratica.getTableName()
				 							    + " left join "
				 							    + AssegnamentoFascicolo.getTableName()
				 							    + " on "
				 							    + Pratica.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 								+ " WHERE "
				 								+ AssegnamentoFascicolo.id_organizzazione.getCompleteName()
				 								+ " = "
				 								+ ":idOrganizzazione"
				 								+ " AND "
				 								+ AssegnamentoFascicolo.fase.getCompleteName()
				 								+ " = "
				 								+ ":faseIstruttoria "
				 								+ " AND "
				 								+ Pratica.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 								+ AssegnamentoFascicolo.username_utente.getCompleteName()
				 								+ " IS NOT NULL"
				 								);
		
		if (StringUtils.isNotEmpty(codice)) {
												sql = sql.concat( " AND "
																+ Pratica.codice_pratica_appptr.getCompleteName()
																+ " ILIKE "
																+ ":codice "
																);
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("statiConsentiti" , AttivitaDaEspletare.getStatiIstruttoriaName());
					parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
					parameters.put("codice"    		 , (codice!=null) ? StringUtil.convertRightLike(codice) : "DUMMY");

		log.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTrasmissioniTemplateRead.queryForList(sql.toString(), parameters, String.class);
	}
	
	
	/**
	 * da rivedere la selezione in base alle pratiche di mia competenza.... in teoria dovrei utilizzare la stessa logica
	 * della search delle pratiche di mia competenza e poi su questa effettuare lookup sui record di AssegnamentoFascicolo 
	 * @author acolaianni
	 *
	 * @param filters
	 * @param entiDiCompetenza
	 * @return
	 * @throws Exception
	 */
	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> getFascicoliNonAssegnati(TabelleAssegnamentoFascicoliSearch filters, Collection<Integer> entiDiCompetenza) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String fields=Pratica  			   .id				  	 .getCompleteName() + ", "
					+ Pratica  			   .stato			  	 .getCompleteName() + ", " 
					+ Pratica  			   .codice_pratica_appptr.getCompleteName() + ", " 
					+ Pratica  			   .tipo_procedimento 	 .getCompleteName() + ", " 
					+ Pratica  			   .oggetto				 .getCompleteName() + ", " 
					+ AssegnamentoFascicolo.rup	 		 		 .getCompleteName() + ", "
					+ AssegnamentoFascicolo.username_utente	 	 .getCompleteName() + ", " 
					+ AssegnamentoFascicolo.denominazione_utente .getCompleteName() + ", " 
					+ AssegnamentoFascicolo.num_assegnazioni	 .getCompleteName() + ", "
					+ AssegnamentoFascicolo.data_operazione		 .getCompleteName() + ", "
					+ "username_rup , " 
					+ "denominazione_rup ";
		String sql = StringUtil.concateneString ("SELECT "
				 								+fields
				 								+ " FROM "
				 								+ LocalizzazioneIntervento.getTableName()	
				 								+ ", "
				 								+ Pratica.getTableName()
				 							    + " left join "
				 							    + " v_assegnazione as assegnamento_fascicolo " //uso la vista con alias pari al nome tabella...
				 							    + " on "
				 							    + Pratica.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 							    + " AND " + AssegnamentoFascicolo.id_organizzazione.getCompleteName()
				 								+ " = "
				 								+ ":idOrganizzazione"
				 								+ " AND " + AssegnamentoFascicolo.fase.getCompleteName()
				 								+ " = "
				 								+ ":faseIstruttoria "
				 								+ " WHERE "
				 								+ LocalizzazioneIntervento.pratica_id.getCompleteName()
				 								+ " = "
				 								+ Pratica.id.getCompleteName()
				 								+ " AND "
				 								+ LocalizzazioneIntervento.comune_id.getCompleteName()
				 								+ " IN (:entiDiCompetenza)"
				 								+ " AND "
				 							    + Pratica.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti) ");
		sql = sql.concat(" AND "+ AssegnamentoFascicolo.username_utente.getCompleteName() + " IS NULL "); 
//		if(filters.getRup()) {
//			sql = sql.concat( 			         " AND "
//				 							    + "("
//				 							    + 	  "("
//				 								+ 		AssegnamentoFascicolo.username_utente.getCompleteName() + " IS NULL"
//					 							+ 		" AND ("
//					 							+ 				AssegnamentoFascicolo.rup.getCompleteName() + " IS TRUE"
//					 							+ 				" OR "
//					 							+ 				AssegnamentoFascicolo.rup.getCompleteName() + " IS NULL"
//					 							+ 	   		 ")"
//					 							+ 	  ")"
//				 								+ " OR ("
//				 								+ 		AssegnamentoFascicolo.username_utente  .getCompleteName() + " IS NOT NULL"
//				 								+ 		" AND "
//				 								+ 		AssegnamentoFascicolo.rup.getCompleteName() + " IS TRUE "
//				 								+ 		" AND "
//				 								+ 		AssegnamentoFascicolo.id_organizzazione.getCompleteName() + " <> :idOrganizzazione"
//				 								+ 	  ")"
//				 								+ ")"
//				 								);
//		}else { //prendo solo le righe non RUP
//			sql = sql.concat( 			         " AND "
//					    + "("
//					    + 	  "("
//						+ 		AssegnamentoFascicolo.username_utente.getCompleteName() + " IS NULL"
//						+ 		" AND ("
//						+ 				AssegnamentoFascicolo.rup.getCompleteName() + " IS FALSE"
//						+ 	   		 ")"
//						+ 	  ")"
//						+ " OR ("
//						+ 		AssegnamentoFascicolo.username_utente  .getCompleteName() + " IS NOT NULL"
//						+ 		" AND "
//						+ 		AssegnamentoFascicolo.rup.getCompleteName() + " IS TRUE "
//						+ 		" AND "
//						+ 		AssegnamentoFascicolo.id_organizzazione.getCompleteName() + " <> :idOrganizzazione"
//						+ 	  ")"
//						+ ")"
//						);
//		}
		if (userUtil.getGruppo()==Gruppi.ED_) {
															sql = sql.concat( " AND "
																			+ Pratica.ente_delegato.getCompleteName()
																			+ " = :idOrg_String"
																			);
		}
		if (StringUtils.isNotEmpty(filters.getCodice())) {
															sql = sql.concat( " AND "
																			+ Pratica.codice_pratica_appptr.getCompleteName()
																			+ " ILIKE :codice"
																			);
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
				 	parameters.put("entiDiCompetenza", (entiDiCompetenza!=null && entiDiCompetenza.isEmpty()) ? null : entiDiCompetenza);
					parameters.put("statiConsentiti" , AttivitaDaEspletare.getStatiIstruttoriaName());
					parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("idOrg_String"	 , ((Integer)userUtil.getIntegerId()).toString());
					parameters.put("codice"    		 , (filters.getCodice()!=null) ? StringUtil.convertRightLike(filters.getCodice()) : "DUMMY");
		sql = sql.concat( " GROUP BY "+fields);
		log.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return super.paginatedList(sql.toString(), parameters, new TabelleAssegnamentoFascicoliRowMapper(), filters.getPage(), filters.getLimit());
	}
	
	public List<String> autocompleteCodiceFascicoliNonAssegnati(String codice, Collection<Integer> entiDiCompetenza) throws Exception {
		log.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( "SELECT DISTINCT "
				 								+ Pratica.codice_pratica_appptr.getCompleteName()
				 								+ " FROM "
				 								+ LocalizzazioneIntervento.getTableName()
				 								+ ", "
				 								+ Pratica.getTableName()
				 							    + " left join "
				 							    + " v_assegnazione  as " + AssegnamentoFascicolo.getTableName() //uso la vista con alias pari al nome tabella...
				 							    + " on "
				 							    + Pratica.id.getCompleteName()
				 							    + " = "
				 							    + AssegnamentoFascicolo.id_fascicolo.getCompleteName()
				 							    + " AND " + AssegnamentoFascicolo.fase.getCompleteName()
				 								+ " = "
				 								+ ":faseIstruttoria "
				 								+ " WHERE "
				 								+ LocalizzazioneIntervento.pratica_id.getCompleteName()
				 								+ " = "
				 								+ Pratica.id.getCompleteName()
				 								+ " AND "
				 								+ LocalizzazioneIntervento.comune_id.getCompleteName()
				 								+ " IN (:entiDiCompetenza)"
				 								+ " AND "
				 							    + Pratica.stato.getCompleteName() 
				 							    + " IN (:statiConsentiti)"
				 							    + " AND "
				 							    + AssegnamentoFascicolo.username_utente.getCompleteName() + " IS NULL " //non hanno la riga in v_assegnazione
//				 							    + "("
//				 							    + 	  "("
//				 								+ 		AssegnamentoFascicolo.username_utente.getCompleteName() + " IS NULL"
//					 							+ 		" AND ("
//					 							+ 				AssegnamentoFascicolo.rup.getCompleteName() + " IS TRUE"
//					 							+ 				" OR "
//					 							+ 				AssegnamentoFascicolo.rup.getCompleteName() + " IS NULL"
//					 							+ 	   		 ")"
//					 							+ 	  ")"
//				 								+ " OR ("
//				 								+ 		AssegnamentoFascicolo.username_utente  .getCompleteName() + " IS NOT NULL"
//				 								+ 		" AND "
//				 								+ 		AssegnamentoFascicolo.rup.getCompleteName() + " IS TRUE "
//				 								+ 		" AND "
//				 								+ 		AssegnamentoFascicolo.id_organizzazione.getCompleteName() + " <> :idOrganizzazione"
//				 								+ 	  ")"
//				 								+ ")"
				 								);
		if (userUtil.getGruppo()==Gruppi.ED_) {
												sql = sql.concat( " AND "
																+ Pratica.ente_delegato.getCompleteName()
																+ " = :idOrg_String"
																);
		}
		if (StringUtils.isNotEmpty(codice)) {
												sql = sql.concat( " AND "
																+ Pratica.codice_pratica_appptr.getCompleteName()
																+ " ILIKE :codice"
																);
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("entiDiCompetenza", (entiDiCompetenza!=null && entiDiCompetenza.isEmpty()) ? null : entiDiCompetenza);
					parameters.put("idOrganizzazione", userUtil.getIntegerId());
					parameters.put("idOrg_String"	 , ((Integer)userUtil.getIntegerId()).toString());
					parameters.put("faseIstruttoria",FasiAssegnazione.ISTRUTTORIA.name());
					parameters.put("statiConsentiti" , AttivitaDaEspletare.getStatiIstruttoriaName());
					parameters.put("codice"    		 , (codice!=null) ? StringUtil.convertRightLike(codice) : "DUMMY");

		log.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTrasmissioniTemplateRead.queryForList(sql.toString(), parameters, String.class);
	}
	
}