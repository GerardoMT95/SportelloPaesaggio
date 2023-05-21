package it.eng.tz.puglia.servizi_esterni.remote.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.rowmapper.SelectOptionDtoRowMapper;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Comune;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Parchi;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Applicazione;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente_Attribute;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_CodiceFascicolo;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Email;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Organizzazione;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Organizzazione_Attributi;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Organizzazione_Competenze;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Organizzazione_Rubrica;
import it.eng.tz.puglia.servizi_esterni.remote.dto.CodiceFascicoloDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteWIstatDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.pk.CodiceFascicoloPK;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.CodiceFascicoloRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.EnteRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.EnteWIstatRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.PaesaggioEmailRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.TipologicaAnagraficaRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.search.CodiceFascicoloSearch;
import it.eng.tz.puglia.servizi_esterni.remote.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Repository for schema 'common'
 */
@Repository
public class CommonRepository
{
	private final Logger logger = LoggerFactory.getLogger(CommonRepository.class);
	
	@Autowired
	@Qualifier("jdbcTemplate_common")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	final static SelectOptionDtoRowMapper selectOptionDtoRowMapper =new SelectOptionDtoRowMapper();
	

  	public int getIdApplicazione(String codiceApplicazione) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString ( " SELECT "
								    			, "id"
								    			, " FROM "
								    			, Common_Applicazione.getTableName()
								    			, " WHERE "
								    			, "codice"
								    			, " ILIKE :codice"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codice", codiceApplicazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class); 
  	}
  	
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	
	public EnteDTO findEnteById(int idEnte) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.id.columnName()
												, " = :idEnte"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public EnteWIstatDTO findEnteWIstatById(int idEnte) throws Exception {
		logger.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select ", 
				Common_Ente.getTableName(), 
				".* , "
				, "(select ",
				Anagrafica_Comune.cod_istat.getCompleteName()
				, " from ", Anagrafica_Comune.getTableName()
				, " where ",
				Anagrafica_Comune.cod_catastale.getCompleteName()
				, " = "
				, Common_Ente.codice.getCompleteName()
				," limit 1 )"
				, " from "
				, Common_Ente.getTableName(), " where ", Common_Ente.id.columnName(),
				" = :idEnte");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);

		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteWIstatRowMapper());
	}

	public List<EnteWIstatDTO> selectEnteWIstatById(List<Integer> idEnti) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select "
												, Common_Ente.getTableName()
												, ".* , "
												, "(select "
												,Anagrafica_Comune.cod_istat.getCompleteName()
												," from "
												,Anagrafica_Comune.getTableName()
												," where "
												,Anagrafica_Comune.cod_catastale.getCompleteName()
												," = "
												,Common_Ente.codice.getCompleteName()
												," limit 1 )"
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.id.columnName()
												, " IN (:idEnti) "
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnti", idEnti);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new EnteWIstatRowMapper());
	}
	
	public List<EnteDTO> selectEntiById(Collection<Integer> idEnti) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.id.columnName()
												, " IN (:idEnti)"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnti", (idEnti!=null && idEnti.isEmpty()) ? null : idEnti);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public EnteDTO findRegione() throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.codice.columnName()
												, " = :regione"
												, " and "
												, Common_Ente.tipo.columnName()
												, " = :regione"
												, " and "
												, Common_Ente.parent_id.columnName()
												, " IS null"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("regione", "R");
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
/*	public EnteDTO findEnteByIdOrganizzazione(int idOrganizzazione) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select "
												, Common_Ente.id		 .getCompleteName(), ", "
												, Common_Ente.nome		 .getCompleteName(), ", "
												, Common_Ente.descrizione.getCompleteName(), ", "
												, Common_Ente.codice	 .getCompleteName(), ", "
												, Common_Ente.pec		 .getCompleteName(), ", "
												, Common_Ente.parent_id	 .getCompleteName(), ", "
												, Common_Ente.tipo		 .getCompleteName()
												, " from "
												, Common_Ente.getTableName()
												, ", "
												, Common_Paesaggio_Organizzazione.getTableName()
												, " where "
												, Common_Ente.id.getCompleteName()
												, " = "
												, Common_Paesaggio_Organizzazione.ente_id.getCompleteName()
												, " and "
												, Common_Paesaggio_Organizzazione.id.getCompleteName()
												, " = :idOrganizzazione"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}	*/
	
	public EnteDTO findEnteByCodice(String codiceEnte) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.codice.columnName()
												, " = :codiceEnte"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceEnte", codiceEnte);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
    public String getTipoEnte(int idEnte) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.tipo.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			, " where "
    			, Common_Ente.id.columnName()
    			, " = :idEnte"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
    
    public String getCodiceEnte(int idEnte) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.codice.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			, " where "
    			, Common_Ente.id.columnName()
    			, " = :idEnte"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }

  	public List<TipologicaDTO> selectInfoEntiByCodici(List<String> codiciEnti) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
														  				, Common_Ente.nome	.getCompleteName() , " as " , Anagrafica_Parchi.descrizione.columnName()
														  				, " , "
														  				, Common_Ente.codice.getCompleteName()
														  				, " from "
														  				, Common_Ente.getTableName()
														  				, " where "
														  				, Common_Ente.codice.columnName()
														  				, " IN ("
														  				, ":codiciEnti"
														  				, ")"
														  				));

  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("codiciEnti", (codiciEnti!=null && codiciEnti.isEmpty()) ? null : codiciEnti);

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.query(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
  	}
  	
    public List<Integer> getAllEnti() throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.id.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, null);
		return namedJdbcTemplate.queryForList(sql.toString(), new HashMap<String, Object>(), Integer.class);
    }
    
    public List<Integer> getAllEntiCOMUNI() throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.id.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			, " where "
    			, Common_Ente.tipo.columnName()
    			, " = :tipo"
    			);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", "CO");
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }

    public List<String> getAllCodiciEntiCOMUNI() throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.codice.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			, " where "
    			, Common_Ente.tipo.columnName()
    			, " = :tipo"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", "CO");
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, String.class);
    }

	public List<String> selectCodiciEntiComuniInProvince(Collection<String> codiciProvince) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString (" select "
												, Common_Ente.codice.columnName()
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.parent_id.columnName()
												, " IN ("
														, " select "
														, Common_Ente.id.columnName()
														, " from ", Common_Ente.getTableName()
														, " where "
														, Common_Ente.codice.columnName()
														, " IN ( :codiciProvince )"
												, " ) "
								    			, " and "
								    			, Common_Ente.tipo.columnName()
								    			, " = :tipo"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("province", (codiciProvince!=null && codiciProvince.isEmpty()) ? null : codiciProvince);
		parameters.put("tipo", "CO");
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, codiciProvince);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, String.class); /* new RowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String codice = null;
				if(rs != null)
				{
					codice = rs.getString(Common_Ente.codice.columnName());
				}
				return codice;
			}
		});	*/
	}

    public List<Integer> selectEntiComuniInProvincia(int idProvincia) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.id.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			, " where "
    			, Common_Ente.parent_id.columnName()
    			, " = :idProvincia"
    			, " and "
    			, Common_Ente.tipo.columnName()
    			, " = :tipo"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idProvincia", idProvincia);
		parameters.put("tipo", "CO");
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    
  	public List<Integer> selectProvinceByCodiciComuni(List<String> codici) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
														  				, Common_Ente.parent_id.getCompleteName()
														  				, " from "
														  				, Common_Ente.getTableName()
														  				, " where "
														  				, Common_Ente.tipo.columnName()
														  				, " = :tipo"
														  				, " and "
														  				, Common_Ente.codice.columnName()
														  				, " IN ("
														  				, ":codici"
														  				, ")"
														  				));
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("codici", (codici!=null && codici.isEmpty()) ? null : codici);
  		parameters.put("tipo", "CO");

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
  	}
  	
  	public List<Integer> selectProvinceByIdComuni(Collection<Long> idEntiComuni) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
														  				, Common_Ente.parent_id.getCompleteName()
														  				, " from "
														  				, Common_Ente.getTableName()
														  				, " where "
														  				, Common_Ente.tipo.columnName()
														  				, " = :tipo"
														  				, " and "
														  				, Common_Ente.id.columnName()
														  				, " IN ("
														  				, ":idEntiComuni"
														  				, ")"
														  				));
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("idEntiComuni", (idEntiComuni!=null && idEntiComuni.isEmpty()) ? null : idEntiComuni);
  		parameters.put("tipo", "CO");

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
  	}	

  	/**
  	 * andrebbe inserito check su paesaggio_organizzazione_attributi per l'applicazione i-esima
  	 * @autor Adriano Colaianni
  	 * @date 10 set 2021
  	 * @param idProvincia
  	 * @return
  	 * @throws Exception
  	 */
  	@Deprecated
  	public Integer findEnteSoprintendenzaByProvincia(int idProvincia) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
											 , Common_Paesaggio_Organizzazione.ente_id.getCompleteName()
											 , " from "
											 , Common_Paesaggio_Organizzazione.getTableName()
											 , " , "
											 , Common_Paesaggio_Organizzazione_Competenze.getTableName()
											 , " where "
											 , Common_Paesaggio_Organizzazione_Competenze.ente_id.getCompleteName()
											 , " = :idProvincia"
											 , " and "
								    		 , Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.columnName()
								    		 , " < :today"
								    		 , " and "
								    		 , Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.columnName()
								    		 , " > :today"
											 , " and "
											 , Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.getCompleteName()
											 , " = "
											 , Common_Paesaggio_Organizzazione.id.getCompleteName()
											 , " and "
											 , Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName()
											 , " ILIKE :tipo"
											 , " and "
								    		 , Common_Paesaggio_Organizzazione.data_creazione.columnName()
								    		 , " < :today"
								    		 , " and "
								    		 , Common_Paesaggio_Organizzazione.data_termine.columnName()
								    		 , " > :today"
										   ));
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idProvincia", idProvincia);
		parameters.put("tipo", Gruppi.SBAP_.getTextValue());
		parameters.put("today", new Date());
		
		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
  	}	
  	
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public String getTipoOrganizzazione(int idOrganizzazione) {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Paesaggio_Organizzazione.tipo_organizzazione.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione.id.columnName()
    			, " = :idOrganizzazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
    
  	public String getCodiceCiviliaByIdOrganizzazione(int idOrganizzazione) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
  				, Common_Paesaggio_Organizzazione.codice_civilia.columnName()
  				, " from "
  				, Common_Paesaggio_Organizzazione.getTableName()
  				, " where "
  				, Common_Paesaggio_Organizzazione.id.columnName()
  				, " = :idOrganizzazione"
  				));

  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("idOrganizzazione", idOrganizzazione);

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
  	}	
  	
/*  public Integer getRiferimentoOrganizzazione(int idOrganizzazione) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Paesaggio_Organizzazione.riferimento_organizzazione.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione.id.columnName()
    			, " = :idOrganizzazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class); 
	}	*/

    public void checkValiditaOrganizzazione(int idOrganizzazione) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select count (*) from "
    			, Common_Paesaggio_Organizzazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione.id.columnName()
    			, " = :idOrganizzazione" 
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_creazione.columnName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_termine.columnName()
    			, " > :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		Integer count = namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
		
		if (count!=1) throw new Exception("Verifica validità del gruppo fallita!");
    }
    
    public void checkValiditaDelegaOrganizzazione(int idOrganizzazione) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select count (*) from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.columnName()
    			, " = :idOrganizzazione" 
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.columnName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.columnName()
    			, " > :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		Integer count = namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
		
		if (count<1) throw new Exception("Verifica validità della delega fallita!");
    }

    public void checkPermessoOrganizzazioneApplicazione(int idOrganizzazione, String codiceApplicazione) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select count (*) from "
    			, Common_Paesaggio_Organizzazione_Attributi.getTableName()
    			, " , "
    			, Common_Applicazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Attributi.applicazione_id.columnName()
    			, " = " 
    			, Common_Applicazione.id.getCompleteName()
    			, " and "
    			, Common_Applicazione.codice.columnName()
    			, " ILIKE :codiceApplicazione"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.columnName()
    			, " = :idOrganizzazione"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_creazione.columnName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_termine.columnName()
    			, " > :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		Integer count = namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
		
		if (count<1) throw new Exception("Verifica dei permessi gruppo\\applicazione fallita!");	// in teoria potrebbe correttamente essere > 1
    }
    
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Integer> getEntiDiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select distinct "
    			, Common_Paesaggio_Organizzazione_Competenze.ente_id.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.columnName()
    			, " = :idOrganizzazione"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.columnName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.columnName()
    			, " > :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    // nel service cercare di risolvere idEnti.....
    public List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione, Gruppi tipoOrganizzazione, TipoOrganizzazioneSpecifica tipoOrganizzazioneSpecifica) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	Map<String, Object> parameters = new HashMap<String, Object>();
    	
    	String sql = StringUtil.concateneString("select distinct "
    			, Common_Paesaggio_Organizzazione.id.getCompleteName()
    			, " from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName(), ", "
    			, Common_Paesaggio_Organizzazione			.getTableName(), ", "
    			, Common_Paesaggio_Organizzazione_Attributi .getTableName(), ", "
    			, Common_Applicazione						.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.ente_id.getCompleteName(), " IN (:idEnti)"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.getCompleteName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.getCompleteName()
    			, " > :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.getCompleteName(), " = ", Common_Paesaggio_Organizzazione.id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_creazione.getCompleteName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_termine.getCompleteName()
    			, " > :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.id.getCompleteName(), " = ", Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName()
    			, " > :today"
    			," and "
    			, Common_Paesaggio_Organizzazione_Attributi.applicazione_id.getCompleteName(), " = ", Common_Applicazione.id.getCompleteName()
    			," and "
    			, Common_Applicazione.codice.getCompleteName(), " ILIKE :codiceApplicazione"
    			);
    	
    	if (tipoOrganizzazione!=null) {
    		sql = sql 
    			+ " and "
    			+ Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName()
    			+ " = :tipoOrganizzazione" ;
    		parameters.put("tipoOrganizzazione", tipoOrganizzazione.name().replaceAll("_",""));
    	}
    	if (tipoOrganizzazioneSpecifica!=null) {
    		sql = sql 
    			+ " and "
    			+ Common_Paesaggio_Organizzazione.tipo_organizzazione_specifica.getCompleteName()
    			+ " = :tipoOrganizzazioneSpecifica" ;
    		parameters.put("tipoOrganizzazioneSpecifica", tipoOrganizzazioneSpecifica.name());
    	}
    	
		parameters.put("idEnti", (idEnti!=null && idEnti.isEmpty()) ? null : idEnti);
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }

  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * from common.paesaggio_organizzazione, common.paesaggio_organizzazione_attributi, common.paesaggio_
     * @return
     */
    private String wherePaesaggioOrganizzazioniAttive() {
    	return StringUtil.concateneString( 
    			 " from "
    			, Common_Paesaggio_Organizzazione.getTableName()
    			, " , "
    			, Common_Paesaggio_Organizzazione_Attributi.getTableName()
    			, " , "
    			, Common_Applicazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.columnName()
    			, " = "
    			, Common_Paesaggio_Organizzazione.id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_creazione.getCompleteName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_termine.getCompleteName()
    			, " > :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName()
    			, " > :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.applicazione_id.columnName()
    			, " = " 
    			, Common_Applicazione.id.getCompleteName()
    			, " and "
    			, Common_Applicazione.codice.columnName()
    			, " ILIKE :codiceApplicazione"
    			);
    }
    
    /**
     * 
     * @param codiceApplicazione
     * @param idsOrganizzazioni (es. [ED_4,ETI_18,SBAP_5 ...]) codice gruppo senza parte ruolo
     * @return [{ED_4,"associazione comuni xyz"},....
     * @throws Exception
     */
	public List<TipologicaDTO> getOrganizzazioniInfoValidePerApplicazione(String codiceApplicazione, List<String>  idsOrganizzazioni) throws Exception {
		logger.trace("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString(" select ", " concat( ",
					Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName(), ",'_',",
					Common_Paesaggio_Organizzazione.id.getCompleteName(), ") ", " as ",
					Anagrafica_Parchi.codice.columnName(), " , ", Common_Ente.descrizione.getCompleteName(), " as ",
					Anagrafica_Parchi.descrizione.columnName(), " from ", 
					Common_Ente.getTableName(), " , ",
					Common_Paesaggio_Organizzazione.getTableName(), " , ",
					Common_Paesaggio_Organizzazione_Attributi.getTableName(), " , ", 
					Common_Applicazione.getTableName(),
					" where ", Common_Ente.id.getCompleteName(), " = ",
					Common_Paesaggio_Organizzazione.ente_id.getCompleteName(), " and ",
					Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName(), " = ",
					Common_Paesaggio_Organizzazione.id.getCompleteName(), " and ",
					Common_Paesaggio_Organizzazione.data_creazione.getCompleteName(), " <= :today", " and ",
					Common_Paesaggio_Organizzazione.data_termine.getCompleteName(), " > :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName(), " <= :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName(), " > :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.applicazione_id.getCompleteName(), " = ",
					Common_Applicazione.id.getCompleteName(), " and ", Common_Applicazione.codice.getCompleteName(),
					" ILIKE :codiceApplicazione"));
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (idsOrganizzazioni != null && idsOrganizzazioni.size() > 0) {
			sql.append(" AND ").append(" concat( ")
					.append(Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName()).append(",'_',")
					.append(Common_Paesaggio_Organizzazione.id.getCompleteName()).append(") ")
					.append(" IN (:idsOrganizzazioni) ").append(" GROUP BY  ")
					.append(Common_Paesaggio_Organizzazione.id.getCompleteName())
					.append(" , ")
					.append(Common_Ente.descrizione.getCompleteName());
			parameters.put("idsOrganizzazioni", idsOrganizzazioni);
		}
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("today", new Date());
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
	}
    
    public List<Integer> 	   getOrganizzazioniValidePerApplicazione	 (String codiceApplicazione, List<Integer> idsOrganizzazioni) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	StringBuilder sql = new StringBuilder(
    			StringUtil.concateneString(" select " 
    			, Common_Paesaggio_Organizzazione.id.getCompleteName() 
    			,this.wherePaesaggioOrganizzazioniAttive()));
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	if(idsOrganizzazioni!=null && idsOrganizzazioni.size()>0) {
    		sql.append( " AND ")
    		.append(Common_Paesaggio_Organizzazione.id.getCompleteName())
    		.append( " IN (:idsOrganizzazioni) ")
    		.append( " GROUP BY  ")
    		.append(Common_Paesaggio_Organizzazione.id.getCompleteName());
    		parameters.put("idsOrganizzazioni", idsOrganizzazioni);
    	}
    	// TODO: dove sta il "codiceApplicazione" ???
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    
    public List<Integer> 	   getOrganizzazioniValidePerApplicazione	 (String codiceApplicazione) 								  throws Exception {
    	return this.getOrganizzazioniValidePerApplicazione(codiceApplicazione, null);
    }
    
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*  public List<Integer> getOrganizzazioniDiCompetenza(Set<Integer> idEnti) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select distinct "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.ente_id.columnName()
    			, " IN (:idEnti)"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.columnName()
    			, " < :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.columnName()
    			, " > :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnti", (idEnti!=null && idEnti.isEmpty()) ? null : idEnti);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }	*/
    
/*	public List<String> selectSoprintendenzaByCodIstat(List<String> codiciIstat) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
											 , Common_SoprintendenzaComuni.soprintendenza.columnName()
											 , " from "
											 , Common_SoprintendenzaComuni.getTableName()
											 , " where "
											 , Common_SoprintendenzaComuni.codice_istat.columnName()
											 , " IN ("
											 , ":codici"
											 , ")"
										   ));
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codici", (codici!=null && codici.isEmpty()) ? null : codici);
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplateCommon.queryForList(sql.toString(), parameters, String.class);
	}	*/

/*  public List<String> getAllCodiciIstatForSoprintendenza(String codiceSoprintendenza) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql = StringUtil.concateneString ( " select "
								    			, Common_SoprintendenzaComuni.codice_istat.columnName()
								    			, " from " 
								    			, Common_SoprintendenzaComuni.getTableName()
								    			, " where "
								    			, Common_SoprintendenzaComuni.soprintendenza.columnName()
								    			, " ILIKE "
								    			, ":codiceSoprintendenza"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceSoprintendenza", codiceSoprintendenza.trim());
		
		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplateCommon.query(sql.toString(), parameters, new RowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String codice = null;
				if(rs != null)
				{
					codice = rs.getString(Common_SoprintendenzaComuni.codice_istat.columnName());
				}
				return codice;
			}
		});
	}	*/

/*  public List<Long> getOrganizzazioniAttive(List<String> idsOrganizzazioni, String codiceApplicazione) {
  		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
  		
  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
  				, Common_Paesaggio_Organizzazione.id.getCompleteName()
  				, " from "
  				, Common_Paesaggio_Organizzazione.getTableName()
  				, " , "
  				, Common_Paesaggio_Organizzazione_Competenze.getTableName()
  				, " , "
  				, Common_Paesaggio_Organizzazione_Attributi.getTableName()
  				, " , "
  				, Common_Applicazione.getTableName()
  				, " where "
  				, Common_Paesaggio_Organizzazione.id.getCompleteName()
  				, " = "
  				, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.getCompleteName()
  				, " AND "
  				, Common_Paesaggio_Organizzazione.id.getCompleteName()
  				, " = "
  				, Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName()
  				, " AND "
  				, Common_Paesaggio_Organizzazione_Attributi.applicazione_id.getCompleteName()
  				," = "
  				, Common_Applicazione.id.getCompleteName()
  				," AND "
  				, Common_Paesaggio_Organizzazione.data_creazione.getCompleteName()
  				, " < :today"
  				, " AND "
  				, Common_Paesaggio_Organizzazione.data_termine.getCompleteName()
  				, " > :today"
  				," AND "
  				, Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName()
  				, " < :today"
  				, " and "
  				, Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName()
  				, " > :today"
  				," AND "
  				, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.getCompleteName()
  				, " < :today"
  				, " AND "
  				, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.getCompleteName()
  				, " > :today"
  				, " AND "
  				, Common_Paesaggio_Organizzazione.id.getCompleteName()
  				," IN (:listaIds) "
  				, " AND "
  				, Common_Applicazione.codice.getCompleteName()
  				, " ILIKE :codiceApplicazione "
  				," GROUP BY "
  				, Common_Paesaggio_Organizzazione.id.getCompleteName()
  				));
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("listaIds", (idsOrganizzazioni!=null && idsOrganizzazioni.isEmpty()) ? null : idsOrganizzazioni);
  		parameters.put("today", new Date());
  		parameters.put("codiceApplicazione", codiceApplicazione);
  		
  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Long.class);
  	}	*/
  	
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	
  	public long insertRubricaEnte(RubricaEnteDTO info) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
  		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.getTableName())
		   
		   .append(StringUtil.concateneString("(", Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName(), ","))
		   .append(StringUtil.concateneString(	   Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione		  .columnName(), ","))
		   .append(StringUtil.concateneString(	   Common_Paesaggio_Organizzazione_Rubrica.pec 						  .columnName(), ","))
		   .append(StringUtil.concateneString(	   Common_Paesaggio_Organizzazione_Rubrica.mail						  .columnName(), ","))
		   .append(StringUtil.concateneString(	   Common_Paesaggio_Organizzazione_Rubrica.nome						  .columnName(), ","))		   
		   .append(StringUtil.concateneString(	   Common_Paesaggio_Organizzazione_Rubrica.username_creazione		  .columnName(), ","))
		   .append(StringUtil.concateneString(	   Common_Paesaggio_Organizzazione_Rubrica.data_creazione			  .columnName(), ")"))
		   .append(" values (")
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName(), ","))
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione		  .columnName(), ","))
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.pec						  .columnName(), ","))
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.mail						  .columnName(), ","))
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.nome						  .columnName(), ","))
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.username_creazione		  .columnName(), ","))
		   .append(StringUtil.concateneString(":", Common_Paesaggio_Organizzazione_Rubrica.data_creazione			  .columnName(), ")"))
		   .append(" returning ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.id.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName(), info.getPaesaggioOrganizzazioneId());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione		  .columnName(), info.getCodiceApplicazione());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.pec						  .columnName(), info.getPec());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.mail						  .columnName(), info.getMail());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.nome						  .columnName(), info.getNome());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.username_creazione		  .columnName(), SecurityUtil.getUsername());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.data_creazione			  .columnName(), new Date());

		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
  	}	
  	
  	public int updateRubricaEnte(RubricaEnteDTO info) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.getTableName())
		   .append(" set ")
		   .append(StringUtil.concateneString(Common_Paesaggio_Organizzazione_Rubrica.pec 			   .columnName(), " = :", Common_Paesaggio_Organizzazione_Rubrica.pec 			   .columnName(), ","))
		   .append(StringUtil.concateneString(Common_Paesaggio_Organizzazione_Rubrica.mail			   .columnName(), " = :", Common_Paesaggio_Organizzazione_Rubrica.mail			   .columnName(), ","))
		   .append(StringUtil.concateneString(Common_Paesaggio_Organizzazione_Rubrica.nome			   .columnName(), " = :", Common_Paesaggio_Organizzazione_Rubrica.nome			   .columnName(), ","))
		   .append(StringUtil.concateneString(Common_Paesaggio_Organizzazione_Rubrica.username_modifica.columnName(), " = :", Common_Paesaggio_Organizzazione_Rubrica.username_modifica.columnName(), ","))
		   .append(StringUtil.concateneString(Common_Paesaggio_Organizzazione_Rubrica.data_modifica    .columnName(), " = :", Common_Paesaggio_Organizzazione_Rubrica.data_modifica    .columnName()     ))
		   .append(" where ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.id.getCompleteName())
		   .append(" = :")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.id.columnName())
		   .append(" and ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.getCompleteName())
		   .append(" = :")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName())
		   .append(" and ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.getCompleteName())
		   .append(" ILIKE :")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.id						  .columnName(), info.getId());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName(), info.getPaesaggioOrganizzazioneId());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione		  .columnName(), info.getCodiceApplicazione());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.pec						  .columnName(), info.getPec());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.mail						  .columnName(), info.getMail());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.nome						  .columnName(), info.getNome());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.username_modifica		  .columnName(), SecurityUtil.getUsername());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.data_modifica			  .columnName(), new Date());

		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
  	}
  	
  	public int deleteRubricaEnte(RubricaEnteDTO info) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder();
		sql.append("delete from ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.getTableName())
		   .append(" where ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.id.getCompleteName())
		   .append(" = :")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.id.columnName())
		   .append(" and ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.getCompleteName())
		   .append(" = :")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName())
		   .append(" and ")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.getCompleteName())
		   .append(" ILIKE :")
		   .append(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.id						  .columnName(), info.getId());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName(), info.getPaesaggioOrganizzazioneId());
		parameters.put(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione		  .columnName(), info.getCodiceApplicazione());

		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
  	}	
  	
  	public int updateRubricaIstituzionale(RubricaIstituzionaleDTO info) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update ")
		   .append(Common_Ente_Attribute.getTableName())
		   .append(" set ")
		   .append(StringUtil.concateneString(Common_Ente_Attribute.pec .columnName(), " = :", Common_Ente_Attribute.pec .columnName(), ","))
		   .append(StringUtil.concateneString(Common_Ente_Attribute.mail.columnName(), " = :", Common_Ente_Attribute.mail.columnName()     ))
		   .append(" where ")
		   .append(Common_Ente_Attribute.ente_id.getCompleteName())
		   .append(" = :")
		   .append(Common_Ente_Attribute.ente_id.columnName())
		   .append(" and ")
		   .append(Common_Ente_Attribute.applicazione_id.getCompleteName())
		   .append(" = :")
		   .append(Common_Ente_Attribute.applicazione_id.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Common_Ente_Attribute.ente_id		.columnName(), info.getEnteId());
		parameters.put(Common_Ente_Attribute.applicazione_id.columnName(), info.getApplicazioneId());
		parameters.put(Common_Ente_Attribute.pec			.columnName(), info.getPec());
		parameters.put(Common_Ente_Attribute.mail			.columnName(), info.getMail());

		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
  	}
  	
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	
  	public String getConfigurationValue(String key, String applicationCode) throws Exception {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
  				, " value "
  				, " from "
  				, " \"common\".\"sit_puglia_configuration\" "
  				, " where "
  				, " key"
  				, " = :keyval"
  				, " and application_code = :appcode"
  				));

  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("keyval", key);
  		parameters.put("appcode", applicationCode);

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
  	}

  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	
	public long countCodiceFascicolo(CodiceFascicoloSearch filter) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(
				   StringUtil.concateneString("SELECT count (*) from ", Common_Paesaggio_CodiceFascicolo.getTableName()));

		filter.getSqlWhereClause(sql);
				   
		logger.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}

	public CodiceFascicoloDTO findCodiceFascicolo(CodiceFascicoloPK pk) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.searchCodiceFascicolo(new CodiceFascicoloSearch(pk.getCodiceEnte(), pk.getAnno(), pk.getPrefisso(), null)).get(0);
	}

	public CodiceFascicoloPK insertCodiceFascicolo(CodiceFascicoloDTO entity) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneString("insert into "+Common_Paesaggio_CodiceFascicolo.getTableName()+" ( "
                	, Common_Paesaggio_CodiceFascicolo.codice_ente.columnName()
                ,",", Common_Paesaggio_CodiceFascicolo.anno.columnName()
                ,",", Common_Paesaggio_CodiceFascicolo.prefisso.columnName()
                ,",", Common_Paesaggio_CodiceFascicolo.seriale.columnName()
                ," )");
		String sql2 = StringUtil.concateneString("values ("
                ," :" + Common_Paesaggio_CodiceFascicolo.codice_ente.columnName()
                ,",:" + Common_Paesaggio_CodiceFascicolo.anno.columnName()
                ,",:" + Common_Paesaggio_CodiceFascicolo.prefisso.columnName()
                ,",:" + Common_Paesaggio_CodiceFascicolo.seriale.columnName()
                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName(), entity.getCodiceEnte())
				.addValue(Common_Paesaggio_CodiceFascicolo.anno.columnName(), entity.getAnno())
				.addValue(Common_Paesaggio_CodiceFascicolo.prefisso.columnName(), entity.getPrefisso())
				.addValue(Common_Paesaggio_CodiceFascicolo.seriale.columnName(), entity.getSeriale());
		
		String sql = StringUtil.concateneString(sql1, sql2);
		
		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
		namedJdbcTemplate.update(sql.toString(), parameters);
		return new CodiceFascicoloPK(entity.getCodiceEnte(), entity.getAnno(), entity.getPrefisso());
	}
	
	public int updateCodiceFascicolo(CodiceFascicoloDTO entity) throws Exception {
		logger.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder("update ")
				.append(Common_Paesaggio_CodiceFascicolo.getTableName())
				.append(" set ")
				.append(Common_Paesaggio_CodiceFascicolo.seriale    .columnName())	.append(" = :")		.append(Common_Paesaggio_CodiceFascicolo.seriale.columnName())
				.append(" where ")
				.append(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName())	.append(" = :") 	.append(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName())
				.append(" and ")
				.append(Common_Paesaggio_CodiceFascicolo.anno	   .columnName())	.append(" = :") 	.append(Common_Paesaggio_CodiceFascicolo.anno.columnName())
				.append(" and ")
				.append(Common_Paesaggio_CodiceFascicolo.prefisso   .columnName())	.append(" = :") 	.append(Common_Paesaggio_CodiceFascicolo.prefisso.columnName());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Common_Paesaggio_CodiceFascicolo.seriale    .columnName(), entity.getSeriale());
		parameters.put(Common_Paesaggio_CodiceFascicolo.codice_ente.columnName(), entity.getCodiceEnte());
		parameters.put(Common_Paesaggio_CodiceFascicolo.anno		  .columnName(), entity.getAnno());
		parameters.put(Common_Paesaggio_CodiceFascicolo.prefisso	  .columnName(), entity.getPrefisso());
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedJdbcTemplate.update(sql.toString(), parameters);
	}

	private List<CodiceFascicoloDTO> searchCodiceFascicolo(CodiceFascicoloSearch filter) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from "));
		sql.append(Common_Paesaggio_CodiceFascicolo.getTableName());
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		logger.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.query(sql.toString(), filter.getSqlParameters(), new CodiceFascicoloRowMapper());
	}
  	
  	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*	public int getPaesaggioOrganizzazioneCompetenzeId(int idEnte, int idOrganizzazione) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
				, " id "
				, " from "
				, " \"common\".\"paesaggio_organizzazione_competenze\" "
				, " where "
				, " paesaggio_organizzazione_id"
				, " = :idOrganizzazione"
				, " and "
				, " ente_id"
				, " = :idEnte"
				, " and "
				, " data_inizio_delega"
				, " < :today"
				, " and "
				, " data_fine_delega"
				, " > :today"
				));

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());

		logger.trace("Sql -> {} Parameters -> {}", sql , parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}	*/
	
	public long countRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("SELECT count (*) from ", Common_Paesaggio_Email.getTableName()));

		filter.getSqlWhereClause(sql);
				   
	    logger.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
	    return namedJdbcTemplate.queryForObject(sql.toString(), filter.getSqlParameters(), Long.class);
	}
	
/*	public long insertRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		String sql1 = StringUtil.concateneSt)ring("insert into "+Common_Paesaggio_Email.getTableName()+" ( "
                	, Common_Paesaggio_Email.codice_applicazione.columnName()
                ,",", Common_Paesaggio_Email.email.columnName()
                ,",", Common_Paesaggio_Email.denominazione.columnName()
                ,",", Common_Paesaggio_Email.pec.columnName()
                ,",", Common_Paesaggio_Email.organizzazione_id.columnName()
                ,",", Common_Paesaggio_Email.ente_id.columnName()
                ," )");
		String sql2 = StringUtil.concateneString("values ("
                ," :" + Common_Paesaggio_Email.codice_applicazione.columnName()
                ,",:" + Common_Paesaggio_Email.email.columnName()
                ,",:" + Common_Paesaggio_Email.denominazione.columnName()
                ,",:" + Common_Paesaggio_Email.pec.columnName()
                ,",:" + Common_Paesaggio_Email.organizzazione_id.columnName()
                ,",:" + Common_Paesaggio_Email.ente_id.columnName()
                ," )");
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue(Common_Paesaggio_Email.codice_applicazione.columnName(), entity.getCodiceApplicazione())
				.addValue(Common_Paesaggio_Email.email.columnName(), entity.getDenominazione())
				.addValue(Common_Paesaggio_Email.denominazione.columnName(), entity.getDenominazione())
				.addValue(Common_Paesaggio_Email.pec.columnName(), entity.getPec())
				.addValue(Common_Paesaggio_Email.organizzazione_id.columnName(), entity.getOrganizzazioneId())
				.addValue(Common_Paesaggio_Email.ente_id.columnName(), entity.getEnteId());
		
		String sql = StringUtil.concateneString(sql1, sql2, " returning ", Common_Paesaggio_Email.id.columnName());
		
		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
	}	*/
	
/*	public int deleteRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("delete from ", Common_Paesaggio_Email.getTableName()));

		filter.getSqlWhereClause(sql);
			   
		logger.trace("Sql -> {} Parameters -> {}", sql , filter.getSqlParameters());
        return namedJdbcTemplate.update(sql.toString(), filter.getSqlParameters());
	}	*/
	
	public List<PaesaggioEmailDTO> searchRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from ", Common_Paesaggio_Email.getTableName()));
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		logger.trace("Sql -> {} Parameters -> {}", sql, filter.getSqlParameters());
		return namedJdbcTemplate.query(sql.toString(), filter.getSqlParameters(), new PaesaggioEmailRowMapper());
	}

	public String getDenominazioneOrganizzazione(int idOrganizzazione) {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Paesaggio_Organizzazione.denominazione.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione.id.columnName()
    			, " = :idOrganizzazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
	}

	/**
	 * se non esiste il record torna null
	 * @param idEnte
	 * @param idApplicazione
	 * @return
	 */
	public TipologicaDTO searchAutomatizzataRubricaIstituzionale(int idEnte, int idApplicazione) {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		TipologicaDTO ret=null;
		String sql = StringUtil.concateneString(" select "
    			, Common_Ente_Attribute.pec.columnName()
    			, " as codice"
    			, " , "
    			, Common_Ente_Attribute.mail.columnName()
    			, " as descrizione"
    			, " from "
    			, Common_Ente_Attribute.getTableName()
    			, " where "
    			, Common_Ente_Attribute.ente_id.columnName()
    			, " = :idEnte"
    			, " and "
    			, Common_Ente_Attribute.applicazione_id.columnName()
    			, " = :idApplicazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);
		parameters.put("idApplicazione", idApplicazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		try {
		   ret=namedJdbcTemplate.queryForObject(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
		}catch(EmptyResultDataAccessException e) {
			logger.info("Nessun record per ente_attribute per ente: " + idEnte);
		}
		return ret;
	}

	public Integer getEnteDiRiferimentoForOrganizzazione(int idOrganizzazione) {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Paesaggio_Organizzazione.ente_id.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione.id.columnName()
    			, " = :idOrganizzazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}

	public Integer findProvinciaForComune(int idEnte) {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
														  				, Common_Ente.parent_id.getCompleteName()
														  				, " from "
														  				, Common_Ente.getTableName()
														  				, " where "
														  				, Common_Ente.tipo.columnName()
														  				, " = :tipo"
														  				, " and "
														  				, Common_Ente.id.columnName()
														  				, " = :idEnte"
														  				));
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("idEnte", idEnte);
  		parameters.put("tipo", "CO");

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}
	
	public List<Integer> findSoprintendenzeByDenominazione(String nomeProv) {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
														  				, Common_Ente.id.getCompleteName()
														  				, " from "
														  				, Common_Ente.getTableName()
														  				, " where "
														  				, Common_Ente.tipo.columnName()
														  				, " = :tipo"
														  				, " AND ( "
														  				, Common_Ente.nome.columnName()
														  				, " ILIKE :nomeProvincia"
														  				, " or "
														  				, Common_Ente.descrizione.columnName()
														  				, " ILIKE :nomeProvincia"
														  				, " )"
														  				));
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("nomeProvincia", StringUtil.convertLike(nomeProv));
  		parameters.put("tipo", "SI");

  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
	}
	
	public List<SelectOptionDto> getSezioniCatastali() {
		String sql=
  				StringUtil.concateneString("SELECT cod_catastale as value,sezione as linked,descrizione as description ",
  		"FROM common.sezioni_catastali ",
  		" ");
		return namedJdbcTemplate.query(sql,selectOptionDtoRowMapper);
	}

	public Integer findEnteDelegatoFromCodiceCivilia(String istatAmm,Gruppi tipo) {
  		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
														  				, Common_Paesaggio_Organizzazione.id.getCompleteName()
														  				, " from "
														  				, Common_Paesaggio_Organizzazione.getTableName()
														  				, " where "
														  				, Common_Paesaggio_Organizzazione.codice_civilia.columnName()
														  				, " = :codiceCivilia"
														  				, " and "
														  				, Common_Paesaggio_Organizzazione.tipo_organizzazione.columnName()
														  				, " = :tipo limit 1"
														  				));
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("codiceCivilia", istatAmm);
  		parameters.put("tipo", tipo.getTipoOrganizzazione());
  		logger.trace("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}

	public List<EnteDTO> getAllEntiData() throws Exception {
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public List<EnteBean> getEntiByTipoOrganizzazione(TipoPaesaggioOrganizzazione tipo) throws Exception 
	{
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if(!Arrays.asList(TipoPaesaggioOrganizzazione.ETI, TipoPaesaggioOrganizzazione.ED, TipoPaesaggioOrganizzazione.SBAP).contains(tipo))
		{
			logger.error("Errore, il metodo getEntiByTipoOrganizzazione non può ricavare gli enti per la tipologia di organizzazione {}", tipo);
			throw new Exception("Errore, il metodo getEntiByTipoOrganizzazione non può ricavare gli enti per la tipologia di organizzazione " + tipo);
		}
		final StringBuilder sql = new StringBuilder("select \"descrizione\", \"tipo\", \"id\"  from \"common\".\"ente\"");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		if(tipo.equals(TipoPaesaggioOrganizzazione.ED) || tipo.equals(TipoPaesaggioOrganizzazione.SBAP))
		{
			sql.append( "where \"tipo\" in (:tipiammessi)");
			parameters.put("tipiammessi", Arrays.asList(TipoEnte.comune.getCodice(), 
														TipoEnte.provincia.getCodice()));
		}
		else
		{
			sql.append( "where \"tipo\" in (:tipiammessi)");
			parameters.put("tipiammessi", Arrays.asList(TipoEnte.comune.getCodice(), 
														TipoEnte.provincia.getCodice(),
														TipoEnte.regione.getCodice()
														));
		}
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new RowMapper<EnteBean>()
		{
			@Override
			public EnteBean mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				EnteBean bean = null;
				if(rs != null)
				{
					bean = new EnteBean();
					bean.setIdEnte(rs.getLong("id"));
					bean.setDenominazione(rs.getString("descrizione"));
					bean.setTipoEnte(TipoEnte.fromCodice(rs.getString("tipo")));
				}
				return bean;
			}
		});
	}	
	
	public List<EnteBean> getEntiByTipo(List<TipoEnte> tipi) throws Exception 
	{
		logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		List<String> tipiStr = Collections.emptyList();
		if(tipi != null && !tipi.isEmpty())
			tipiStr = tipi.stream().map(TipoEnte::getCodice).collect(Collectors.toList());
		final StringBuilder sql = new StringBuilder("select \"descrizione\", \"tipo\", \"id\"  from \"common\".\"ente\" where \"tipo\" in (:tipi)");
		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipi", tipiStr);
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new RowMapper<EnteBean>()
		{
			@Override
			public EnteBean mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				EnteBean bean = null;
				if(rs != null)
				{
					bean = new EnteBean();
					bean.setIdEnte(rs.getLong("id"));
					bean.setDenominazione(rs.getString("descrizione"));
					bean.setTipoEnte(TipoEnte.fromCodice(rs.getString("tipo")));
				}
				return bean;
			}
		});
	}
	
	/**
	 * organizzazioni valide in paesaggioOrganizzazione filtrate per ente_id
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @param codiceApplicazione
	 * @param idsEnteCommon lista degli ente_id
	 * @return
	 * @throws Exception
	 */
	public List<Integer> 	getOrganizzazioniValidePerApplicazioneByIdsEnte(String codiceApplicazione, List<Integer> idsEnteCommon) throws Exception {
    	logger.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	StringBuilder sql = new StringBuilder(
    			StringUtil.concateneString(" select " 
    			, Common_Paesaggio_Organizzazione.id.getCompleteName() 
    			,this.wherePaesaggioOrganizzazioniAttive()));
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	if(idsEnteCommon!=null && idsEnteCommon.size()>0) {
    		sql.append( " AND ")
    		.append(Common_Paesaggio_Organizzazione.ente_id.getCompleteName())
    		.append( " IN (:idsEnteCommon) ")
    		.append( " GROUP BY  ")
    		.append(Common_Paesaggio_Organizzazione.id.getCompleteName());
    		parameters.put("idsEnteCommon", idsEnteCommon);
    	}
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("today", new Date());
		
		logger.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
}