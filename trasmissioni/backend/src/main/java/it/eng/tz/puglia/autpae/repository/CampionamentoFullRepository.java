package it.eng.tz.puglia.autpae.repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.Campionamento;
import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dbMapping.FascicoloCampionamento;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.repository.base.CampionamentoBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.CampionamentoRowMapper;
import it.eng.tz.puglia.autpae.rowmapper.FascicoloRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for Campionamento
 */
@Repository
public class CampionamentoFullRepository extends CampionamentoBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(CampionamentoFullRepository.class);
    private final CampionamentoRowMapper rowMapper = new CampionamentoRowMapper();
   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*	/**
	 * Update percentage on active sampling 
	 * @param percent
	 * @param customized
	 **
	public void setPercentOnActiveSampling(short percent, boolean customized) {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString("update "+Campionamento.getTableName()+" set "
																			            	 , Campionamento.percentuale.columnName()
																			            	 , "=:"
																			            	 , Campionamento.percentuale.columnName()
																			            	 , " , "
																			            	 , Campionamento.customized.columnName()
																			            	 , "=:"
																			            	 , Campionamento.customized.columnName()
																			            	 , " where "
																							 , Campionamento.attivo.columnName()
																							 , "=:"
																							 , Campionamento.attivo.columnName());

		SqlParameterSource parameters = new MapSqlParameterSource()
						   			  .addValue(Campionamento.percentuale.columnName(), percent   )
						   			  .addValue(Campionamento.customized .columnName(), customized)
						   			  .addValue(Campionamento.attivo	 .columnName(), true      );
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql, parameters);
	}   */
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
/*	/**
	 * Select percentage of an active sampling 
	 * @return short			 - percentuale
	 **
	public short getMaxPercentage() {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		CampionamentoSearch searchC = new CampionamentoSearch();
		searchC.setAttivo(true);
		
		List<CampionamentoDTO> listaCampionamenti = null;
		try {
			listaCampionamenti = this.search(searchC).getList();
		} catch (Exception e) {
			log.error("Errore nella ricerca dei campionamenti!");
			e.printStackTrace();
		}
		
		if (listaCampionamenti==null || listaCampionamenti.size()!=1) {
			log.error("Errore. Trovati "+(listaCampionamenti==null ? 0 : listaCampionamenti.size())+" campionamenti attivi!");
		}

		return listaCampionamenti.get(0).getPercentuale();
	}	*/

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Select active sampling
	 * @return CampionamentoDTO
	 */
	public CampionamentoDTO getActiveSampling() {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("SELECT * "
				," FROM "
				, Campionamento.getTableName()
				," WHERE "
				, Campionamento.attivo.columnName()
				," = true");
//				, true);

	//	SqlParameterSource parameters = new MapSqlParameterSource()
	//			.addValue(Campionamento.attivo.columnName(), true);

	//	log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForObject(sql, this.rowMapper);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Set active field of sampling to false
	 */
	public void deactivateSampling() {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString("update "+Campionamento.getTableName()+" set "
																							 , Campionamento.attivo.columnName()
																							 , "="
																							 , false
																							 , " where "
																							 , Campionamento.attivo.columnName()
																							 , "="
																							 , true);
		SqlParameterSource parameters = null;
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql, parameters);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Get all the samplings having selected fascicoli not verified
	 * @return sampling list
	 * @throws Exception
	 */
	public List<CampionamentoDTO> getSamplingsNotVerified() {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString("SELECT * "
				," FROM "
				, Campionamento.getTableName()
				," JOIN "
				, FascicoloCampionamento.getTableName()
				," ON "
				, Campionamento.id.getCompleteName()
				," = "
				, FascicoloCampionamento.id_campionamento.getCompleteName()
				," JOIN "
				, Fascicolo.getTableName()
				," ON "
				, FascicoloCampionamento.id_fascicolo.getCompleteName()
				," = "
				, Fascicolo.id.getCompleteName()
				," WHERE "
				, Fascicolo.esito_verifica.getCompleteName()		// verification_outcome
				," = :"
				, Fascicolo.esito_verifica.columnName()
				," AND "
				, Fascicolo.stato.getCompleteName()					// registration_status
				," = :"
				, Fascicolo.stato.columnName()
				," GROUP BY "
				, Campionamento.id.getCompleteName()
				," ORDER BY "
				, Campionamento.id.getCompleteName()
				," DESC LIMIT 1");
		
		SqlParameterSource parameters = new MapSqlParameterSource()
						   			  .addValue(Fascicolo.esito_verifica.columnName(), EsitoVerifica .CHECK_IN_PROGRESS.name())
						   			  .addValue(Fascicolo.stato			.columnName(), StatoFascicolo.TRANSMITTED      .name());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForList(sql, this.rowMapper, parameters);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get number of fascicoli included in a sampling
	 * @param samplingId   id of the sampling
	 * @param publishDate
	 * @param samplingDate
	 * @return number of fascicoli included in active sampling
	 */
	public int findActiveSamplingFascicoli(long samplingId, Date publishDate, Date samplingDate) {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString("SELECT COUNT(*) "
				, " FROM "
				, Fascicolo.getTableName()
				, " LEFT JOIN "
				, FascicoloCampionamento.getTableName()
				, " ON "
				, FascicoloCampionamento.id_fascicolo.getCompleteName()
				, " = "
				, Fascicolo.id.getCompleteName()
				, " WHERE "
				, FascicoloCampionamento.id_campionamento.getCompleteName()
				, " = :"
				, FascicoloCampionamento.id_campionamento.columnName()
				, " AND "
				, Fascicolo.data_trasmissione.getCompleteName()				 // publish_time
				, " BETWEEN :"
				, Fascicolo.data_trasmissione.columnName()
				, " AND :"
				, Fascicolo.data_campionamento.columnName()
				);

		SqlParameterSource parameters = new MapSqlParameterSource()
	   			  .addValue(FascicoloCampionamento.id_campionamento  .columnName(), samplingId)
	   			  .addValue(Fascicolo             .data_trasmissione .columnName(),  publishDate.toLocalDate()            .atStartOfDay())
	   			  .addValue(Fascicolo             .data_campionamento.columnName(), samplingDate.toLocalDate().plusDays(1).atStartOfDay());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return super.queryForObject(sql, Integer.class, parameters);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Find all fascicoli included in a sampling
	 * @param samplingId id of the sampling where fascicoli should be sampled
	 * @return list of fascicoli
	 */
	public List<FascicoloDTO> getAllActiveFascicoli(long samplingId) {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String sql = StringUtil.concateneString("SELECT * "
				, " FROM "
				, Fascicolo.getTableName()
				, " LEFT JOIN "
				, FascicoloCampionamento.getTableName()
				, " ON "
				, FascicoloCampionamento.id_fascicolo.getCompleteName()
				, " = "
				, Fascicolo.id.getCompleteName()
				, " WHERE "
				, FascicoloCampionamento.id_campionamento.getCompleteName()
				, " = :"
				, FascicoloCampionamento.id_campionamento.columnName()
				, " AND "
				, Fascicolo.stato.getCompleteName()				 // registration_status
				, " = :"
				, Fascicolo.stato.columnName()
				, " ORDER BY "
				, Fascicolo.data_trasmissione.columnName()		 // publish_time
				, " ASC "
				);
		
		Map<String,Object> parameters = new HashMap<>();
	   		parameters.put(FascicoloCampionamento.id_campionamento.columnName(),samplingId);
	   		parameters.put(Fascicolo             .stato 		   .columnName(), StatoFascicolo.TRANSMITTED.name());

		return super.namedJdbcTemplate.query(sql,  parameters,new FascicoloRowMapper());
	}
	
}