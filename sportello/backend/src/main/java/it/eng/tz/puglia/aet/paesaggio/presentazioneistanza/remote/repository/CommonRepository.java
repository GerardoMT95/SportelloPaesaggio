package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CLEntiCommissioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.ComuniCompetenzaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PlainNumberValueLabelRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PlainStringValueLabelRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.SelectOptionDtoRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SezioneCatastaleSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Applicazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Ente_Attribute;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Email;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Organizzazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Organizzazione_Attributi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Organizzazione_Competenze;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Organizzazione_Rubrica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.IpaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.CommissioneLocaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.EnteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.EntiCommissioniRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.IpaEnteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.PaesaggioEmailRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.PaesaggioOrganizzazioneRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.RemoteRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.TipologicaAnagraficaRowMapper;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Accesso al DB common
 */
@Repository
public class CommonRepository extends RemoteRepository<EnteDTO> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonRepository.class);
	
	@Value("${datasource.common.schema}")
	private String schema;

	@Override
	protected String getSchema() {
		return schema;
	}

	@Autowired
	@Qualifier("jdbcTemplate_common")
	private JdbcTemplate commonJdbcTemplate;
	
	@Autowired
	@Qualifier("namedJdbcTemplate_common")
	private NamedParameterJdbcTemplate namedCommonJdbcTemplate;

	@PostConstruct
	private void initJdbcTemplate() throws Exception {
		this.jdbcTemplate = commonJdbcTemplate;
	}

	@Override
	protected RemoteRowMapper<EnteDTO> getRowMapper() {
		return new EnteRowMapper();
	}
	
	final static SelectOptionDtoRowMapper selectOptionDtoRowMapper =new SelectOptionDtoRowMapper(); 
	
	
	@Override
	protected String getQueryForSelectAll() {
		return StringUtil.concateneString("SELECT * ", "FROM \"ente\" e ");
	}

	/**
	 * find by pk method
	 */
	@Override
	protected String getQueryForFind(String codice) {
		return StringUtil.concateneString("SELECT * FROM \"ente\" e WHERE e.\"codice\" = ? ");
	}

	/**
	 * findAll by pks method
	 */
	@Override
	protected String getQueryForFindAll(List<String> codices) {
		return StringUtil.concateneString("SELECT * ", "FROM \"ente\" e ",
				"WHERE e.\"codice\" IN (",
				codices.stream().collect(Collectors.joining("', '", "'", "'")), ") ");
	}

	
	
/*	public int insertEnteAttribute(String codiceEnte) {
		String getQueryForInsertEnteAttribute =	StringUtil.concateneString(
												"INSERT INTO \"ente_attribute\"(\"applicazione_id\",\"ente_id\",\"pec\") " + "SELECT * FROM ",
												"(SELECT ", "(SELECT a.\"id\" FROM \"applicazione\" a WHERE a.\"codice\"='PAE_PRES_IST') a_id,",
												"(SELECT e.\"id\" FROM \"ente\" e WHERE e.\"codice\"=?) e_id, ",
												"(SELECT e.\"pec\" FROM \"ente\" e WHERE e.\"codice\"=?) e_pec ", ") AS ", "\"PIPPO\" ",
												" WHERE NOT EXISTS ", " (SELECT 1 FROM \"ente_attribute\" ea WHERE ",
												" ea.\"applicazione_id\"=\"PIPPO\".\"a_id\" AND ea.\"ente_id\"=\"PIPPO\".\"e_id\")");
		int ret = 0;
		if (!getQueryForInsertEnteAttribute.isEmpty()) {
			ret = this.jdbcTemplate.update(getQueryForInsertEnteAttribute, codiceEnte, codiceEnte);
		}
		return ret;
	}	*/

	/**
	 * trova gli Enti di tipo "Provincia" con il campo "nome" compatibile in Like alla stringa in ingresso
	 */
	public List<EnteDTO> findEntiProvinceLike(String patternNome) {
		String getQueryForAutocompleteProvincePuglia = StringUtil.concateneString("SELECT e.\"pec\" pec, '' mail, e.\"codice\" codice, ",
													   "e.\"nome\" nome, e.\"descrizione\" descrizione ", ",e.\"id\" id ", "FROM \"ente\" e ",
													   "WHERE e.\"tipo\" = 'P'  AND e.\"nome\" ILIKE  ?");
		try {
			if (patternNome == null) {
				patternNome = "";
			}
			return this.jdbcTemplate.query(getQueryForAutocompleteProvincePuglia, getRowMapper(),
					StringUtil.convertLike(patternNome));
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
	public List<PlainNumberValueLabel> getEntiRiceventi() {
		String getQueryForEntiRiceventi = StringUtil.concateneString("SELECT ", "po.id as value , e.descrizione as label " 
										+ "FROM common.applicazione a ," + "common.paesaggio_organizzazione_attributi poa ," 
										+ "common.paesaggio_organizzazione po ," + "common.ente e " 
										+ "WHERE " + "a.codice ='PAE_PRES_IST' and " + "poa.applicazione_id =a.id "
										+ "and po.id =poa.paesaggio_organizzazione_id " + "and e.id =po.ente_id "
										+ "and poa.data_termine >= now() " 
										+ "and poa.data_creazione <= now() "
										+ "and po.tipo_organizzazione in ('ED','REG') "
										);
		try {
			return this.jdbcTemplate.query(getQueryForEntiRiceventi, new PlainNumberValueLabelRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * restituisce tutti gli enti riceventi anche quelli non piu' attivi
	 * @author acolaianni
	 *
	 * @return
	 */
	public List<PlainNumberValueLabel> getAllEntiRiceventi() {
		String getQueryForEntiRiceventi = StringUtil.concateneString("SELECT ", "po.id as value , e.descrizione as label " 
										+ "FROM common.applicazione a ," + "common.paesaggio_organizzazione_attributi poa ," 
										+ "common.paesaggio_organizzazione po ," + "common.ente e " 
										+ "WHERE " + "a.codice ='PAE_PRES_IST' and " + "poa.applicazione_id =a.id "
										+ "and po.id =poa.paesaggio_organizzazione_id " + "and e.id =po.ente_id "
										+ "and po.tipo_organizzazione in ('ED','REG') "
										);
		try {
			return this.jdbcTemplate.query(getQueryForEntiRiceventi, new PlainNumberValueLabelRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
	
	public List<PlainNumberValueLabel> getEnteRegione() {
		String getQueryForEntiRiceventi = StringUtil.concateneString("SELECT ", "po.id as value , e.descrizione as label " 
										+ "FROM common.applicazione a ," + "common.paesaggio_organizzazione_attributi poa ," 
										+ "common.paesaggio_organizzazione po ," + "common.ente e " 
										+ "WHERE " + "a.codice ='PAE_PRES_IST' and " + "poa.applicazione_id =a.id "
										+ "and po.id =poa.paesaggio_organizzazione_id " + "and e.id =po.ente_id "
										+ "and poa.data_termine >= now() " 
										+ "and poa.data_creazione <= now() "
										+ "and po.tipo_organizzazione='REG' "
										);
		try {
			return this.jdbcTemplate.query(getQueryForEntiRiceventi, new PlainNumberValueLabelRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
	
	private List<ComuniCompetenzaDTO> getComuniDiCompetenza(int enteRicevente, Date riferimento) {
		EnteDTO reg=null;
		try {
			reg = this.findRegione();
		} catch (Exception e1) {
			LOGGER.error("Errore in findRegione .. l'elaborazione continua",e1);
		}
		List<ComuniCompetenzaDTO> ret=null;
		String sql = StringUtil.concateneString(
				"SELECT "
				+ "null as pratica_id, "
				+ "e.id as ente_id, "
				+ "now() as data_inserimento, "
				+ "e.descrizione as descrizione, "
				+ "e.codice as cod_cat, "
				+ "null as cod_istat, "
				+ "null as vincolo_art_38, "
				+ "null as vincolo_art_100, "
				+ "null as notifica "
				+ " FROM common.paesaggio_organizzazione po ,"
				+ "common.paesaggio_organizzazione_competenze poc ,common.ente e " + 
				" WHERE " + 
				" po.id = poc.paesaggio_organizzazione_id  AND " + 
				" e.id =poc.ente_id " + 
				" AND po.id =:enteRicevente " +
				" AND po.data_termine >= :riferimento " + 
				" AND po.data_creazione <= :riferimento ");
        LOGGER.debug("QUERY Enti di competenza:"+sql);        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("enteRicevente", enteRicevente);
        parameters.put("riferimento", riferimento);	
        try {
        	ret = this.namedCommonJdbcTemplate.query(sql.toString(), parameters,new ComuniCompetenzaRowMapper());
        } catch (EmptyResultDataAccessException empty) {		
		}
        if(reg!=null && ret!=null && ret.size()==1 && ret.get(0).getEnteId()==reg.getId()) {
        	//e' regione e devo esplodere i comuni....
        	List<EnteDTO> allComuni=null;
        	try {
        	 allComuni = this.getAllEntiComuniDetails();
        	}catch(Exception e) {
        		LOGGER.error("errore nella chiamata a getAllEntiComuniDetails ... l'elaborazione continua",e);
        	}
        	if(allComuni==null) {
        		ret=null;		
        	}else {
        		ret=allComuni.stream().map(el->{
        			ComuniCompetenzaDTO cDto=new ComuniCompetenzaDTO();
        			cDto.setEnteId(el.getId());
        			cDto.setDescrizione(el.getDescrizione());
        			cDto.setCodCat(el.getCodice());
        			cDto.setDataInserimento(new Timestamp(new Date().getTime()));
        			return cDto;
        		}).collect(Collectors.toList());
        	}
        }
        return ret;
	}
	
	/**
	 * in caso di lista vuota restituisce null 
	 * @param enteRicevente
	 * @param riferimento
	 * @return
	 */
	public List<PlainNumberValueLabel> getEntiDiCompetenza(int enteRicevente, Date riferimento)  {
		List<PlainNumberValueLabel> ret=Collections.emptyList();
//		String sql = StringUtil.concateneString(
//				"SELECT e.id as value, e.descrizione as label "
//				+ "FROM common.paesaggio_organizzazione po ,"
//				+ "common.paesaggio_organizzazione_competenze poc ,common.ente e " + 
//				" WHERE " + 
//				" po.id = poc.paesaggio_organizzazione_id  AND " + 
//				" e.id =poc.ente_id " + 
//				" AND po.id =:enteRicevente " +
//				" AND po.data_termine >= :riferimento " + 
//				" AND po.data_creazione <= :riferimento ");
//        LOGGER.debug("QUERY Enti di competenza:"+sql);        
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("enteRicevente", enteRicevente);
//        parameters.put("riferimento", riferimento);
//        
        
		try {
        	List<ComuniCompetenzaDTO> comuni = this.getComuniDiCompetenza(enteRicevente, riferimento);
        	if(comuni==null) throw new EmptyResultDataAccessException(0);
        	ret=comuni.stream().map(el->{
        		PlainNumberValueLabel pn = new PlainNumberValueLabel();
        		pn.setValue((long)el.getEnteId());
        		pn.setDescription(el.getDescrizione());
        		return pn;
        	}).collect(Collectors.toList());
        	//return this.namedCommonJdbcTemplate.query(sql.toString(), parameters, new PlainNumberValueLabelRowMapper());
		} catch (EmptyResultDataAccessException empty) {}
        return ret;
    }
	
	/**
	 * in caso di lista vuota restituisce null altrimenti restituisce la lista completa a meno dei campi 
	 * cod_istat (che sarà null) e pratica_id
	 * @param enteRicevente
	 * @param riferimento
	 * @return
	 */
	public List<ComuniCompetenzaDTO> getEntiDiCompetenzaConCodici(int enteRicevente, Date riferimento)  {
		return this.getComuniDiCompetenza(enteRicevente, riferimento);
//		String sql = StringUtil.concateneString(
//				"SELECT "
//				+ "null as pratica_id, "
//				+ "e.id as ente_id, "
//				+ "now() as data_inserimento, "
//				+ "e.descrizione as descrizione, "
//				+ "e.codice as cod_cat, "
//				+ "null as cod_istat, "
//				+ "null as vincolo_art_38, "
//				+ "null as vincolo_art_100, "
//				+ "null as notifica "
//				+ " FROM common.paesaggio_organizzazione po ,"
//				+ "common.paesaggio_organizzazione_competenze poc ,common.ente e " + 
//				" WHERE " + 
//				" po.id = poc.paesaggio_organizzazione_id  AND " + 
//				" e.id =poc.ente_id " + 
//				" AND po.id =:enteRicevente " +
//				" AND po.data_termine >= :riferimento " + 
//				" AND po.data_creazione <= :riferimento ");
//        LOGGER.debug("QUERY Enti di competenza:"+sql);        
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.put("enteRicevente", enteRicevente);
//        parameters.put("riferimento", riferimento);
//        try {
//        	List<ComuniCompetenzaDTO> ret = this.namedCommonJdbcTemplate.query(sql.toString(), parameters,new ComuniCompetenzaRowMapper());
//        	return ret;
//		} catch (EmptyResultDataAccessException empty) {
//			return null;
//		}
    }

	public String getCodiceCatastaleFromId(long id)  {
		String sql = StringUtil.concateneString(
				  "SELECT "
				+ "codice"
				+ " FROM common.ente"
				+ " WHERE " 
				+ "id = " 
				+ ":id"
				+ " AND "
				+ "tipo = "
				+ ":tipo"
				);      
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        parameters.put("tipo", "CO");
		LOGGER.debug("QUERY: "+sql);
        return this.namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
  	public int getIdApplicazione(String codiceApplicazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class); 
  	}
  	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	
	public EnteDTO findEnteById(int idEnte) throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.id.columnName()
												, " = :idEnte"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnte", idEnte);
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public List<EnteDTO> selectEntiById(Collection<Integer> idEnti) throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.id.columnName()
												, " IN (:idEnti)"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnti", (idEnti!=null && idEnti.isEmpty()) ? null : idEnti);
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public EnteDTO findRegione() throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public EnteDTO findEnteByIdOrganizzazione(int idOrganizzazione) throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
	public EnteDTO findEnteByCodice(String codiceEnte) throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString ( " select * "
												, " from ", Common_Ente.getTableName()
												, " where "
												, Common_Ente.codice.columnName()
												, " = :codiceEnte"
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceEnte", codiceEnte);
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, new EnteRowMapper());
	}
	
    public String getTipoEnte(int idEnte) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
    
    public String getCodiceEnte(int idEnte) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }

  	public List<PlainStringValueLabel> selectInfoEntiByCodici(List<String> codiciEnti) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select distinct "
														  				, Common_Ente.nome	.getCompleteName() , " as description"
														  				, " , "
														  				, Common_Ente.codice.getCompleteName() , " as value"
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

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new PlainStringValueLabelRowMapper());
  	}
  	
    public List<Integer> getAllEnti() throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Common_Ente.id.columnName()
    			, " from "
    			, Common_Ente.getTableName()
    			);
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, null);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), new HashMap<String, Object>(), Integer.class);
    }
    
    public List<Integer> getAllEntiCOMUNI() throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    
    public List<EnteDTO> getAllEntiComuniDetails() throws Exception 
    {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	String sql = StringUtil.concateneString("select  * from ", 
    											Common_Ente.getTableName(), " where ", 
    											Common_Ente.tipo.columnName(), " = :tipo ORDER BY " + Common_Ente.descrizione);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", "CO");
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
    }

    public List<String> getAllCodiciEntiCOMUNI() throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, String.class);
    }

	public List<String> selectCodiciEntiComuniInProvince(Collection<String> codiciProvince) throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, codiciProvince);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, String.class); /* new RowMapper<String>()
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
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    
    //selectEntiComuniInProvinciaDetails
    public List<EnteDTO> selectEntiComuniInProvinciaDetails(int idProvincia) throws Exception 
    {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	String sql = StringUtil.concateneString(" select * from ", Common_Ente.getTableName(),
    											" where ", Common_Ente.parent_id.columnName(), " = :idProvincia",
    											" and ", Common_Ente.tipo.columnName(), " = :tipo");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idProvincia", idProvincia);
		parameters.put("tipo", "CO");
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
    }
    
    
  	public List<Integer> selectProvinceByCodiciComuni(List<String> codici) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
  	}
  	
  	public List<Integer> selectProvinceByIdComuni(Collection<Long> idEntiComuni) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
  	}	

  	public Integer findEnteSoprintendenzaByProvincia(int idProvincia) throws Exception {
		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
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
		
		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
  	}	
  	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public String getTipoOrganizzazione(int idOrganizzazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
    
  	public String getCodiceCiviliaByIdOrganizzazione(int idOrganizzazione) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
  	}	
  	
    public Integer getRiferimentoOrganizzazione(int idOrganizzazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class); 
	}

    public void checkValiditaOrganizzazione(int idOrganizzazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		Integer count = namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
		
		if (count!=1) throw new Exception("Verifica validità del gruppo fallita!");
    }
    
    public void checkValiditaDelegaOrganizzazione(int idOrganizzazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		Integer count = namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
		
		if (count<1) throw new Exception("Verifica validità della delega fallita!");
    }

    public void checkPermessoOrganizzazioneApplicazione(int idOrganizzazione, String codiceApplicazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_termine.columnName()
    			, " >= :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		Integer count = namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
		
		if (count<1) throw new Exception("Verifica dei permessi gruppo\\applicazione fallita!");	// in teoria potrebbe correttamente essere > 1
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Integer> getEntiDiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select distinct "
    			, Common_Paesaggio_Organizzazione_Competenze.ente_id.columnName()
    			, " from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.columnName()
    			, " = :idOrganizzazione"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.columnName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.columnName()
    			, " >= :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    
    public List<EnteDTO> getEntiDiCompetenzaForOrganizzazioneDetails(int idOrganizzazione) throws Exception
    {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	String sql = StringUtil.concateneString("select distinct ente.* "
    			, " from common.ente join "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName()
    			, " as poc on ente.id = poc.ente_id"
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.columnName()
    			, " = :idOrganizzazione"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.columnName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.columnName()
    			, " >= :today"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idOrganizzazione", idOrganizzazione);
		parameters.put("today", new Date());
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new EnteRowMapper());
    }
    
    public List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString("select distinct "
    			, Common_Paesaggio_Organizzazione.id.getCompleteName()
    			, " from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName(), ", "
    			, Common_Paesaggio_Organizzazione			.getTableName(), ", "
    			, Common_Paesaggio_Organizzazione_Attributi .getTableName(), ", "
    			, Common_Applicazione						.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.ente_id.getCompleteName(), " IN ( :idEnti )"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.getCompleteName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.getCompleteName()
    			, " >= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.getCompleteName(), " = ", Common_Paesaggio_Organizzazione.id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_creazione.getCompleteName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_termine.getCompleteName()
    			, " >= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.id.getCompleteName(), " = ", Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName()
    			, " >= :today"
    			," and "
    			, Common_Paesaggio_Organizzazione_Attributi.applicazione_id.getCompleteName(), " = ", Common_Applicazione.id.getCompleteName()
    			," and "
    			, Common_Applicazione.codice.getCompleteName(), " ILIKE :codiceApplicazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnti", (idEnti!=null && idEnti.isEmpty()) ? null : idEnti);
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("today", new Date());
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
    
    public List<PaesaggioOrganizzazioneDTO> getOrganizzazioniCompetentiOnEntiDetails(Collection<Integer> idEnti, String codiceApplicazione) throws Exception {
    	LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString("select distinct common.paesaggio_organizzazione.* "
    			, " from "
    			, Common_Paesaggio_Organizzazione_Competenze.getTableName(), ", "
    			, Common_Paesaggio_Organizzazione			.getTableName(), ", "
    			, Common_Paesaggio_Organizzazione_Attributi .getTableName(), ", "
    			, Common_Applicazione						.getTableName()
    			, " where "
    			, Common_Paesaggio_Organizzazione_Competenze.ente_id.getCompleteName(), " IN ( :idEnti )"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_inizio_delega.getCompleteName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.data_fine_delega.getCompleteName()
    			, " >= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Competenze.paesaggio_organizzazione_id.getCompleteName(), " = ", Common_Paesaggio_Organizzazione.id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_creazione.getCompleteName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.data_termine.getCompleteName()
    			, " >= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione.id.getCompleteName(), " = ", Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName()
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName()
    			, " <= :today"
    			, " and "
    			, Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName()
    			, " >= :today"
    			," and "
    			, Common_Paesaggio_Organizzazione_Attributi.applicazione_id.getCompleteName(), " = ", Common_Applicazione.id.getCompleteName()
    			," and "
    			, Common_Applicazione.codice.getCompleteName(), " ILIKE :codiceApplicazione"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idEnti", (idEnti!=null && idEnti.isEmpty()) ? null : idEnti);
		parameters.put("codiceApplicazione", codiceApplicazione);
		parameters.put("today", new Date());
		
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new PaesaggioOrganizzazioneRowMapper());
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * 
     * @param codiceApplicazione
     * @param idsOrganizzazioni (es. [ED_4,ETI_18,SBAP_5 ...]) codice gruppo senza parte ruolo
     * @return [{ED_4,"associazione comuni xyz"},....
     * @throws Exception
     */
	public List<PlainStringValueLabel> getOrganizzazioniInfoValidePerApplicazione(String codiceApplicazione, List<String>  idsOrganizzazioni) throws Exception {
		LOGGER.info("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString(" select ", " concat( ",
					Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName(), ",'_',",
					Common_Paesaggio_Organizzazione.id.getCompleteName(), ") ", " as value",
					" , ", Common_Ente.descrizione.getCompleteName(), " as label",
					" , null as linked ",
					" from ", 
					Common_Ente.getTableName(), " , ",
					Common_Paesaggio_Organizzazione.getTableName(), " , ",
					Common_Paesaggio_Organizzazione_Attributi.getTableName(), " , ", 
					Common_Applicazione.getTableName(),
					" where ", 
					Common_Ente.id.getCompleteName(), " = ",
					Common_Paesaggio_Organizzazione.ente_id.getCompleteName(), " and ",
					Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName(), " = ",
					Common_Paesaggio_Organizzazione.id.getCompleteName(), " and ",
					Common_Paesaggio_Organizzazione.data_creazione.getCompleteName(), " <= :today", " and ",
					Common_Paesaggio_Organizzazione.data_termine.getCompleteName(), " >= :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName(), " <= :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName(), " >= :today", " and ",
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
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new PlainStringValueLabelRowMapper());
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
     * 
     * @param codiceApplicazione
     * @param idsOrganizzazioni (es. [ED_4,ETI_18,SBAP_5 ...]) codice gruppo senza parte ruolo
     * @return [{ED_4,"associazione comuni xyz"},....
     * @throws Exception
     */
	public List<PlainStringValueLabel> getOrganizzazioniInfoValidePerApplicazioneConCL(String codiceApplicazione, List<String>  idsOrganizzazioni) throws Exception {
		LOGGER.info("Entrato nel repository: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString(" select ", " concat( ",
					Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName(), ",'_',",
					Common_Paesaggio_Organizzazione.id.getCompleteName(), ") ", " as value",
					" , COALESCE(", Common_Ente.descrizione.getCompleteName(),",",Common_Paesaggio_Organizzazione.denominazione.getCompleteName(), ") as label",
					" , null as linked ",
					" from ", 
					Common_Ente.getTableName(), " RIGHT JOIN ",
					Common_Paesaggio_Organizzazione.getTableName(), " ON ",
					Common_Paesaggio_Organizzazione.ente_id.getCompleteName(), " = ",
					Common_Ente.id.getCompleteName(), ", ",
					Common_Paesaggio_Organizzazione_Attributi.getTableName(), " , ", 
					Common_Applicazione.getTableName(),
					" where ", 
					Common_Paesaggio_Organizzazione_Attributi.paesaggio_organizzazione_id.getCompleteName(), " = ",
					Common_Paesaggio_Organizzazione.id.getCompleteName(), " and ",
					Common_Paesaggio_Organizzazione.data_creazione.getCompleteName(), " <= :today", " and ",
					Common_Paesaggio_Organizzazione.data_termine.getCompleteName(), " >= :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.data_creazione.getCompleteName(), " <= :today", " and ",
					Common_Paesaggio_Organizzazione_Attributi.data_termine.getCompleteName(), " >= :today", " and ",
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
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.query(sql.toString(), parameters, new PlainStringValueLabelRowMapper());
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	
  	public long insertRubricaEnte(RubricaEnteDTO info) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
  		
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

		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
  	}	
  	
  	public int updateRubricaEnte(RubricaEnteDTO info) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedCommonJdbcTemplate.update(sql.toString(), parameters);
  	}
  	
  	public int deleteRubricaEnte(RubricaEnteDTO info) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedCommonJdbcTemplate.update(sql.toString(), parameters);
  	}	
  	
  	public int updateRubricaIstituzionale(RubricaIstituzionaleDTO info) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql.toString(), parameters);
		return namedCommonJdbcTemplate.update(sql.toString(), parameters);
  	}
  	
  	/**
  	 * Metodo che ricerca l'id organizzazione di una commissione locale dato l'id organizzazione
  	 * dell'ente delegato a cui fa riferimento e la data in cui tale commissione deve essere valida
  	 * @param idOrganizzazioneEnte
  	 * @param dataValidita
  	 * @return id organizzazione della commissione locale
  	 * @throws Exception
  	 */
  	public Integer getIdCommissioneByEnte(Integer idOrganizzazioneEnte, Date dataValidita) throws Exception
  	{
  		final String sql = StringUtil.concateneString("select cl.\"id\" ",
  													  "from \"common\".\"paesaggio_organizzazione\" as cl ",
  													  "join \"common\".\"paesaggio_commissione_locale\" as pcl ",
  													  "		on pcl.\"id_commissione_locale\" = cl.\"id\" ",
  													  "join \"common\".\"paesaggio_organizzazione\" as ente ",
  													  "		on pcl.\"id_ente_delegato\" = ente.\"id\" ",
  													  "where ente.\"id\" = :id_ente ",
  													  "		and ente.\"data_termine\" >= CURRENT_DATE ",
  													  "		and :data_validita between cl.\"data_creazione\" ",
  													  "				and cl.\"data_termine\" ",
  													  "limit 1");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id_ente", idOrganizzazioneEnte);
  		parameters.put("data_validita", dataValidita);
		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql, parameters, Integer.class);
  	}
  	
  	/**
  	 * Metodo per ottenere gli id degli enti associati ad una determinata commissione locale
  	 * @param idCommissione
  	 * @return {@link List} di id degli enti associati ad una determinata commissione locale
  	 * @throws Exception
  	 */
  	public List<Integer> getEntiForCommissioneLocale(Integer idCommissione) throws Exception
  	{
  		final String sql = StringUtil.concateneString("select \"id_ente_delegato\" from ",
  													  "\"common\".\"paesaggio_commissione_locale\" ",
  													  "where \"id_commissione_locale\" = :id_cl");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id_cl", idCommissione);
  		LOGGER.info("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForList(sql, parameters, Integer.class);
  	}
  	
  	//---------------------------------------------------------------------------------------------------------------
  	
  	public List<PaesaggioEmailDTO> searchRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception 
  	{
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select * from ", Common_Paesaggio_Email.getTableName() ));
		
		filter.getSqlWhereClause(sql);
		filter.getSqlOrderByClause(sql);
		
		return namedCommonJdbcTemplate.query(sql.toString(), filter.getSqlParameters(), new PaesaggioEmailRowMapper());
	}
  	
  	
  	public long insertRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		String sql1 = StringUtil.concateneString("insert into "+Common_Paesaggio_Email.getTableName()+" ( "
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

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Long.class);
  	} 
  		
  	
  	
  	public long updateRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

  		String sql = StringUtil.concateneString("update "+Common_Paesaggio_Email.getTableName()+" set "
  				     , Common_Paesaggio_Email.codice_applicazione.columnName(), " = :", Common_Paesaggio_Email.codice_applicazione.columnName()
  				,", ", Common_Paesaggio_Email.email.columnName()			  , " = :", Common_Paesaggio_Email.email.columnName()
  				,", ", Common_Paesaggio_Email.denominazione.columnName()	  , " = :", Common_Paesaggio_Email.denominazione.columnName()
  				,", ", Common_Paesaggio_Email.pec.columnName()				  , " = :", Common_Paesaggio_Email.pec.columnName()
  				,", ", Common_Paesaggio_Email.organizzazione_id.columnName()  , " = :", Common_Paesaggio_Email.organizzazione_id.columnName()
  				,", ", Common_Paesaggio_Email.ente_id.columnName()			  , " = :", Common_Paesaggio_Email.ente_id.columnName()
  				," where "
  					 , Common_Paesaggio_Email.id.columnName()				  , " = :", Common_Paesaggio_Email.id.columnName());
  		
  		SqlParameterSource parameters = new MapSqlParameterSource()
  				.addValue(Common_Paesaggio_Email.codice_applicazione.columnName(), entity.getCodiceApplicazione())
  				.addValue(Common_Paesaggio_Email.email.columnName(), entity.getDenominazione())
  				.addValue(Common_Paesaggio_Email.denominazione.columnName(), entity.getDenominazione())
  				.addValue(Common_Paesaggio_Email.pec.columnName(), entity.getPec())
  				.addValue(Common_Paesaggio_Email.organizzazione_id.columnName(), entity.getOrganizzazioneId())
  				.addValue(Common_Paesaggio_Email.ente_id.columnName(), entity.getEnteId())
  				.addValue(Common_Paesaggio_Email.id.columnName(), entity.getId());

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.update(sql.toString(), parameters);
  	} 
  	
  	
  	
  	public String getDenominazioneOrganizzazione(int idOrganizzazione) 
  	{
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
		
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
	}

  	public List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione, Gruppi tipoOrganizzazione, TipoOrganizzazioneSpecifica tipoOrganizzazioneSpecifica) throws Exception 
  	{

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
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
    }
  	
  	public TipologicaDTO searchAutomatizzataRubricaIstituzionale(int idEnte, int idApplicazione) 
  	{
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
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
		try {
			  ret= namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
			}catch(EmptyResultDataAccessException e) {
				LOGGER.info("Nessun record per ente_attribute per ente: " + idEnte);
			}
		return ret;
	}
  	
  	public Integer getEnteDiRiferimentoForOrganizzazione(int idOrganizzazione) 
  	{
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
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}
  	
  	public Integer findProvinciaForComune(int idEnte) 
  	{

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
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, Integer.class);
	}
  	
  	public List<Integer> findSoprintendenzeByDenominazione(String nomeProv) 
  	{

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
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForList(sql.toString(), parameters, Integer.class);
	}
  	
  	public Long insertPaesaggioOrganizzazione(PaesaggioOrganizzazioneDTO entity) throws Exception
  	{
  		String sql = StringUtil.concateneString("insert into \"common\".\"paesaggio_organizzazione\"",
  												"(\"ente_id\", \"denominazione\", \"tipo_organizzazione\", ",
  												"\"tipo_organizzazione_specifica\", \"data_creazione\", \"data_termine\") ",
  												"values (:ente_id, :denominazione, :tipo_organizzazione, ",
  												":tipo_organizzazione_specifica, :data_creazione, :data_termine) ",
  												"returning \"id\"");	
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("ente_id", entity.getEnteId());
  		parameters.put("denominazione", entity.getDenominazione());
  		parameters.put("tipo_organizzazione", entity.getTipoOrganizzazione() != null ? entity.getTipoOrganizzazione().name() : null);
  		parameters.put("tipo_organizzazione_specifica", entity.getTipoOrganizzazioneSpecifica());
  		parameters.put("data_creazione", entity.getDataCreazione());
  		parameters.put("data_termine", entity.getDataTermine());
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql, parameters, Long.class);
  	}
  	
  	
  	public Long insertPaesaggioOrganizzazioneAttributi(Integer appId,Long organizzazioneId,Date dataCreazione,Date dataTermine) throws Exception
  	{
  		String sql = StringUtil.concateneString("INSERT INTO common.paesaggio_organizzazione_attributi ",
  	  	" ( applicazione_id, paesaggio_organizzazione_id, data_creazione, data_termine) ",
  	  	" VALUES( :app_id, :pae_org_id, :data_creazione, :data_termine ) ",
  				"returning \"id\"");
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("app_id", appId);
  		parameters.put("pae_org_id",organizzazioneId);
  		parameters.put("data_creazione", dataCreazione);
  		parameters.put("data_termine", dataTermine);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql, parameters, Long.class);
  	}
  	
  	
  	public long updatePaesaggioOrganizzazione(PaesaggioOrganizzazioneDTO entity) throws Exception
  	{
  		String sql = StringUtil.concateneString("update \"common\".\"paesaggio_organizzazione\" set ",
  												"\"denominazione\" = :denominazione, ",
  												"\"data_creazione\" = :data_creazione, ",
  												"\"data_termine\" = :data_termine ",
  												"where \"id\" = :id"); 				
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("denominazione", entity.getDenominazione());
  		parameters.put("data_creazione", entity.getDataCreazione());
  		parameters.put("data_termine", entity.getDataTermine());
  		parameters.put("id", entity.getId());
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.update(sql, parameters);
  	}
  	
  	public long updatePaesaggioOrganizzazioneAttributi(Integer appId,Long organizzazioneId,Date dataCreazione,
  			Date dataTermine,Date newDataCreazione,Date newDataTermine) throws Exception
  	{
  		String sql = StringUtil.concateneString("update \"common\".\"paesaggio_organizzazione_attributi\" set ",
  												"\"data_creazione\" = :new_data_creazione, ",
  												"\"data_termine\" = :new_data_termine ",
  												"where  ",
  												"\"applicazione_id\" = :app_id AND ",
  												"\"paesaggio_organizzazione_id\" = :org_id AND ",
  												"\"data_creazione\" = :data_creazione AND ",
  												"\"data_termine\" = :data_termine "
  												); 				
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("new_data_creazione", newDataCreazione);
  		parameters.put("new_data_termine", newDataTermine);
  		parameters.put("app_id", appId);
  		parameters.put("org_id", organizzazioneId);
  		parameters.put("data_creazione", dataCreazione);
  		parameters.put("data_termine", dataTermine);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.update(sql, parameters);
  	}
  	
  	public long insertPaesaggioCommissioneLocale(Long idCommissione, List<Long> enti) throws Exception
  	{
  		StringBuilder sql = new StringBuilder("insert into \"common\".\"paesaggio_commissione_locale\"(\"id_ente_delegato\", \"id_commissione_locale\")");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		Integer j = 0;
  		String sep = " values ";
  		parameters.put("id_commissione", idCommissione);
  		for(Long ente: enti)
  		{
  			String key = "id_ente_" + j++;
  			sql.append(sep).append("(:").append(key).append(", :id_commissione)");
  			parameters.put(key, ente);
  			sep = ", ";
  		}
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.update(sql.toString(), parameters);
  	}
  	
  	public long deletePaesaggioCommissioneLocale(Long idCommissione) throws Exception
  	{
  		if(idCommissione == null)
  		{
  			throw new Exception("Errore: l'id organizzazione non può essere nullo");
  		}
  		StringBuilder sql = new StringBuilder("delete from \"common\".\"paesaggio_commissione_locale\" where \"id_commissione_locale\" = :id_commissione");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id_commissione", idCommissione);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.update(sql.toString(), parameters);
  	}
  	
  	public List<PlainNumberValueLabel> searchPaesaggioCommissioneLocale(Long idCommissione) throws Exception
  	{
  		if(idCommissione == null)
  		{
  			throw new Exception("Errore: l'id organizzazione non può essere nullo");
  		}
  		String sql = StringUtil.concateneString("select \"id_ente_delegato\" as value, \"denominazione\" as label ",
  												"from \"common\".\"paesaggio_commissione_locale\" ",
  												"join \"common\".\"paesaggio_organizzazione\" as po ",
  												"on \"id_ente_delegato\" = po.\"id\" ",
  												"where \"id_commissione_locale\" = :id_commissione");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id_commissione", idCommissione);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.query(sql, parameters, new PlainNumberValueLabelRowMapper());
  	}
  	
  	private String commissioneLocaleSql(CommissioneLocaleSearch search, Map<String, Object> parameters)
  	{
  		StringBuilder sql = new StringBuilder("select * from \"common\".\"paesaggio_organizzazione\" where \"tipo_organizzazione\" = 'CL'");
  		String sep = " and ";
  		if(StringUtil.isNotEmpty(search.getDenominazione()))
  		{
  			sql.append(sep).append("lower(\"denominazione\") like :denominazione");
  			parameters.put("denominazione", StringUtil.convertRightLike(search.getDenominazione().toLowerCase()));
  			sep = " and ";
  		}
  		if(search.getDataValiditaDa() != null)
  		{
  			sql.append(sep).append("\"data_creazione\" >= :data_validazione_da");
  			parameters.put("data_validazione_da", search.getDataValiditaDa());
  			sep = " and ";
  		}
  		if(search.getDataValiditaA() != null)
  		{
  			sql.append(sep).append("\"data_termine\" <= :data_validazione_a");
  			parameters.put("data_validazione_a", search.getDataValiditaA());
  			sep = " and ";
  		}
  		if(search.getIds() != null && !search.getIds().isEmpty())
  		{
  			sql.append(sep).append("\"id\" in (:ids)");
  			parameters.put("ids", search.getIds());
  			sep = " and ";
  		}
  		if(StringUtil.isNotEmpty(search.getColonna()))
  		{
  			sql.append(" order by \"").append(search.getColonna()).append("\" ");
  			if(search.getDirezione() != null)
  				sql.append(search.getDirezione().name());
  		}
  		return sql.toString();
  	}
  	
  	public PaginatedList<PaesaggioOrganizzazioneDTO> searchCommissioneLocale(CommissioneLocaleSearch search) throws Exception
  	{
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		final String sql = commissioneLocaleSql(search, parameters);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return paginatedList(sql.toString(), parameters, new PaesaggioOrganizzazioneRowMapper(), search.getPagina(), search.getLimite());
  	}
  	
  	public PaesaggioOrganizzazioneDTO findPaesaggioOrganizzazione(Long id, boolean onlyCL) throws Exception
  	{
  		String sql = "select * from \"common\".\"paesaggio_organizzazione\" where \"id\" = :id";
  		if(onlyCL)
  			sql += " and \"tipo_organizzazione\" = 'CL'";
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id", id);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql, parameters, new PaesaggioOrganizzazioneRowMapper());
  	}
  	
  	public PaesaggioOrganizzazioneDTO findCommissioneLocale(Long idCommissione) throws Exception
  	{
  		return findPaesaggioOrganizzazione(idCommissione, true);
  	}
  	
  	public PaesaggioOrganizzazioneDTO findCommissioneByEnte(Long idEnte, Date dataValidita) throws Exception
  	{
  		final String sql = StringUtil.concateneString("select cl.* ",
  													  "from \"common\".\"paesaggio_organizzazione\" as cl ",
  													  "join \"common\".\"paesaggio_commissione_locale\" as pcl ",
  													  "		on cl.\"id\" = pcl.\"id_commissione_locale\" ",
  													  "where pcl.\"id_ente_delegato\" = :id_ente_delegato ",
  													  "		and :data_validita between cl.\"data_creazione\" ",
  													  "				and cl.\"data_termine\" ",
  													  "order by cl.\"data_termine\" desc ",
  													  "limit 1");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id_ente_delegato", idEnte);
  		parameters.put("data_validita", dataValidita);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql, parameters, new PaesaggioOrganizzazioneRowMapper());
  	}
  	
  	public List<PaesaggioEmailDTO> findCommissioneEmailByEnte(Long idEnte, Date dataValidita) throws Exception
  	{
  		final String sql = StringUtil.concateneString("select pe.* ",
  													  "from \"common\".\"paesaggio_email\" as pe ",
  													  "join \"common\".\"paesaggio_organizzazione\" as cl ",
													  "		on cl.\"id\" = pe.\"organizzazione_id\" ",
  													  "join \"common\".\"paesaggio_commissione_locale\" as pcl ",
  													  "		on cl.\"id\" = pcl.\"id_commissione_locale\" ",
  													  "where pcl.\"id_ente_delegato\" = :id_ente_delegato ",
  													  "		and :data_validita between cl.\"data_creazione\" ",
  													  "				and cl.\"data_termine\" ",
  													  "order by cl.\"data_termine\" desc ");
  		final Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("id_ente_delegato", idEnte);
  		parameters.put("data_validita", dataValidita);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.query(sql, parameters, new PaesaggioEmailRowMapper());
  	}
  	
  	public List<PaesaggioOrganizzazioneDTO> findEnti() throws Exception
  	{	
  		String sql = StringUtil.concateneString("select ente.* from \"common\".\"paesaggio_organizzazione\" as ente ",
  												"where \"tipo_organizzazione\" = 'ED' and ente.\"data_termine\" >= :now ",
  												"and \"ente_id\" != 0 ",
  												"order by ente.\"denominazione\"");	//escludo regione
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("now", new Date());
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.query(sql, parameters, new PaesaggioOrganizzazioneRowMapper());
  	}
  	
  	/**
  	 * Valuta se ci sono commissioni valide per gli enti passati nella lista in input nelle date passate in input. 
  	 * idCommissione (se presente) esclude la commissine con tale id
  	 * @param enti
  	 * @param idCommisione
  	 * @param dataInizioVal
  	 * @param dataFineVal
  	 * @return
  	 * @throws Exception
  	 */
  	public List<CLEntiCommissioniDTO> checkEntiForCommissione(List<Long>enti, Long idCommisione, Date dataInizioVal, Date dataFineVal) throws Exception
  	{
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		String sql = StringUtil.concateneString("select ente.\"id\" as id_ente, ente.\"denominazione\" ",
											  	"as nome_ente, cl.\"id\" as id_commissione, ",
											  	"cl.\"denominazione\" as nome_commissione, cl.\"data_creazione\" ",
											  	"as data_inizio_val, cl.\"data_termine\" as data_termine_val ",
											  	"from \"common\".\"paesaggio_organizzazione\" as ente ",
											  	"join \"common\".\"paesaggio_commissione_locale\" as pcl ",
											  	"on ente.\"id\" = pcl.\"id_ente_delegato\" ",
											  	"join \"common\".\"paesaggio_organizzazione\" as cl ",
											  	"on pcl.\"id_commissione_locale\" = cl.\"id\" ",
											  	"where (cl.\"data_creazione\" between :data_creazione and :data_termine or ",
											  	"		cl.\"data_termine\"   between :data_creazione and :data_termine or ",
											  	"		:data_creazione between cl.\"data_creazione\" and cl.\"data_termine\" or ",
											  	"		:data_termine between cl.\"data_creazione\"   and cl.\"data_termine\") ",
											  	"and ente.\"id\" in (:enti)");
  		
  		if(idCommisione != null)
  		{
  			sql = sql + " and cl.\"id\" != :id_commissione";
  			parameters.put("id_commissione", idCommisione);
  		}
  		
  		parameters.put("data_creazione", dataInizioVal);
  		parameters.put("data_termine", dataFineVal);
  		parameters.put("enti", enti);
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.query(sql, parameters, new EntiCommissioniRowMapper());
  	}
  	
  	public List<PaesaggioOrganizzazioneDTO> getAllEntiDelegati() throws Exception
  	{
  		String sql = StringUtil.concateneString("select * from \"common\".\"paesaggio_organizzazione\" ",
  												"where \"tipo_organizzazione\" = 'ED' ",
  												"and \"data_termine\" > :today ",
  												"order by \"denominazione\"");
  		Map<String, Object> parameters = new HashMap<String, Object>();
  		parameters.put("today", new Date());
  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.query(sql, parameters, new PaesaggioOrganizzazioneRowMapper());
  	}
  	
  	/**
	 * Metodo che resituisce tutti i comuni e le province della regione puglia
	 * @return lista di province e comuni della regione puglia
	 * @throws Exception
	 */
	public List<EnteDTO> findComuniPuglia() throws Exception
	{
		/**
		 * Query riconrsiva per ottenere tutte le province ed i comuni appartenenti alla regione puglia. 
		 * Da questa in un secondo momento filtro solo i comuni:
		 
			with recursive puglia_co as 
			(
				select * 
				from "common"."ente"
				where "ente"."tipo" in ('CO', 'P')
					and "ente"."parent_id" = 0
			
				union
			
				select "common"."ente".*
				from "common"."ente"
				join puglia_co 
					on puglia_co."id" = "ente"."parent_id"
				where "ente"."tipo" in ('CO', 'P')
			)
			select * from puglia_co where "tipo" = 'CO'
		 */
		String sql = StringUtil.concateneString("with recursive puglia_co as(",
												"select * from \"common\".\"ente\" ",
												"where \"ente\".\"tipo\" in ('CO', 'P') ",
												"and \"ente\".\"parent_id\" = 0 ",
												"union ",
												"select \"common\".\"ente\".* ",
												"from \"common\".\"ente\" ",
												"join puglia_co on puglia_co.\"id\" = \"ente\".\"parent_id\" ",
												"where \"ente\".\"tipo\" in ('CO', 'P')) ",
												"select * from puglia_co where \"tipo\" = 'CO' order by \"nome\"");
		LOGGER.info("Eseguo la query {}", sql);
		return namedCommonJdbcTemplate.query(sql, new EnteRowMapper());
	}
  	
  	private <T> PaginatedList<T> paginatedList(final String sql, final Map<String, Object> paramMap, final RowMapper<T> rowMapper, final int page, final int limit) 
  	{
		final String  countSql     = this.getCountSql(sql);
		final String  paginatedSql = this.getPaginatedSql(sql, page, limit);
		final int     count        = namedCommonJdbcTemplate.queryForObject(countSql, paramMap, Integer.class);
		final List<T> list         = namedCommonJdbcTemplate.query(paginatedSql, paramMap, rowMapper);
		return new PaginatedList<T>(list, count);
	}  
  	
  	private String getCountSql(final String sql) {
		return StringUtil.concateneString("select count(*) FROM (", sql , ") t");
	}
  	private String getPaginatedSql(final String sql, final int page, final int limit) 
  	{
		if (page == 0 && limit == 0) 
			return sql;
		else 
		{
			final int offset = (page - 1) * limit;
			return StringUtil.concateneString(sql, this.getLimitCondition(limit), " OFFSET ", offset);
		}
	}
  	private String getLimitCondition(final int limit) 
  	{
		return StringUtil.concateneString(" LIMIT ", limit);
	}
  	
  	/**
  	 *  se non esiste crea la riga con 1
  	 * @author acolaianni
  	 *
  	 * @param prefisso
  	 * @param codiceEnteCivilia
  	 * @param anno
  	 * @return 
  	 */
  	public Integer getSerialeCodice(String prefisso,String codiceEnteCivilia,Integer anno) {
  		Integer ret=null;
  		Map<String,Object> parameters=new HashMap<>();
  		String sql=
  				StringUtil.concateneString("SELECT seriale ",
  		"FROM common.paesaggio_codice_fascicolo ",
  		"WHERE codice_ente= :codiceEnteCivilia AND anno= :anno AND prefisso= :prefisso ");
  		parameters.put("codiceEnteCivilia",codiceEnteCivilia);
  		parameters.put("anno",anno);
  		parameters.put("prefisso",prefisso);
  		try {
  			ret=namedCommonJdbcTemplate.queryForObject(sql, parameters, Integer.class);
  			ret++;
  			sql=StringUtil.concateneString("UPDATE common.paesaggio_codice_fascicolo ", 
  	  	  			" set seriale= :seriale " ,
  	  	  			"WHERE codice_ente= :codiceEnteCivilia AND anno= :anno AND prefisso= :prefisso ");
  	  		parameters.put("seriale",ret);
  	  		this.namedCommonJdbcTemplate.update(sql, parameters);
  		}catch(EmptyResultDataAccessException e) {
  			sql=
  				StringUtil.concateneString("INSERT INTO common.paesaggio_codice_fascicolo ", 
  	  			"(codice_ente, anno, prefisso, seriale) " ,
  	  			"VALUES(:codiceEnteCivilia, :anno , :prefisso , :seriale ) " );
  			ret=1;
  			parameters.put("seriale",ret);
  			this.namedCommonJdbcTemplate.update(sql, parameters);
  		}
  		return ret;
  	}

	public List<SelectOptionDto> getSezioniCatastali() {
		String sql=
  				StringUtil.concateneString("SELECT cod_catastale as value,sezione as linked,descrizione as description ",
  		"FROM common.sezioni_catastali ",
  		" ");
		return namedCommonJdbcTemplate.query(sql,selectOptionDtoRowMapper);
	}

	
	public PaginatedList<SelectOptionDto> sezioneCatastaleSearch(SezioneCatastaleSearchDTO filter)  {
		Map<String,Object> param=new HashMap<>();
		String sep=" WHERE "; 
		StringBuilder sql = new StringBuilder(StringUtil.concateneString(
		"SELECT cod_catastale as value,sezione as linked,descrizione as description ",
  		"FROM common.sezioni_catastali "));
		if(StringUtil.isNotEmpty(filter.getNome())){
			sql.append(sep)
			.append(" (cod_catastale LIKE :pattern " )
			.append("  OR sezione LIKE :pattern " )
			.append("  OR descrizione LIKE :pattern )" );
			param.put("pattern",StringUtil.convertFullLike(filter.getNome()));
			sep=" AND ";
		}
		if(StringUtil.isNotEmpty(filter.getCodCatastale())){
			sql.append(sep).append(" cod_catastale = :cod_catastale");
			param.put("cod_catastale",filter.getCodCatastale());
			sep=" AND ";
		}
		if(StringUtil.isNotEmpty(filter.getSezione())){
			sql.append(sep).append(" sezione = :sezione");
			param.put("sezione",filter.getSezione());
			sep=" AND ";
		}
		sql.append(" ORDER BY cod_catastale,sezione ");
		return this.paginatedList(sql.toString(), param, new SelectOptionDtoRowMapper(), filter.getPage(), filter.getLimit());
	}
  	
	public int sezioneCatastaleDelete(String codCatastale, String sezione) {
		Map<String,Object> param=new HashMap<>();
		StringBuilder sql = new StringBuilder(StringUtil.concateneString(
				"DELETE ",
		  		"FROM common.sezioni_catastali WHERE "));
				sql.append(" cod_catastale = :cod_catastale");
				param.put("cod_catastale",codCatastale);
				sql.append(" AND ");
				sql.append(" sezione = :sezione");
				param.put("sezione",sezione);
		return namedCommonJdbcTemplate.update(sql.toString(), param);
	}

	public int sezioneCatastaleUpdate(SelectOptionDto item) {
		Map<String,Object> param=new HashMap<>();
		StringBuilder sql = new StringBuilder(StringUtil.concateneString(
		"UPDATE  ",
  		" common.sezioni_catastali  set descrizione= :descrizione WHERE cod_catastale= :cod_catastale AND sezione= :sezione"));
		param.put("cod_catastale",item.getValue());
		param.put("sezione",item.getLinked());
		param.put("descrizione",item.getDescription());
		return namedCommonJdbcTemplate.update(sql.toString(), param);
	}

	public int sezioneCatastaleInsert(SelectOptionDto item) {
		Map<String,Object> param=new HashMap<>();
		StringBuilder sql = new StringBuilder(StringUtil.concateneString(
		"INSERT INTO ",
  		" common.sezioni_catastali (cod_catastale,sezione,descrizione) VALUES(:cod_catastale,:sezione,:descrizione)"));
		param.put("cod_catastale",item.getValue());
		param.put("sezione",item.getLinked());
		param.put("descrizione",item.getDescription());
		return namedCommonJdbcTemplate.update(sql.toString(), param);
	}
	
	/**
	 * restituisce il paesaggio_organizzazione.id della soprintendenza a partire dal common.ente.id del comune
	 * altrimenti eccezione (se il resulset non è lungo 1)
	 * @author acolaianni
	 *
	 * @param comuneId chiave in common.ente
	 * @param dataRiferimento
	 * @return
	 */
	public Long getIdSoprintendenzaByComuneId(Long comuneId,Date dataRiferimento) {
		Map<String,Object> param=new HashMap<>();
		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select po.id ",
		//"ente.id idprovincia,ente.descrizione descrProvincia,ente_comune.id ente_comune_id,ente_comune.descrizione",  
		" from common.paesaggio_organizzazione_competenze poc, ",
		" common.paesaggio_organizzazione po ,common.paesaggio_organizzazione_attributi poa ,common.ente ente,common.ente ente_comune ",
		" where ",  										
		" ente.id =poc.ente_id ", 
		" and ente_comune.parent_id =ente.id ", 
		" and poc.paesaggio_organizzazione_id =po.id ", 
		" and poa.paesaggio_organizzazione_id =po.id  ",
		" and poa.applicazione_id in (select id from common.applicazione a where a.codice ='PAE_PRES_IST') ",
		" and poa.data_creazione <= :dataRiferimento and poa.data_termine >= :dataRiferimento ",
		" and po.tipo_organizzazione='SBAP' ",
		" and ente_comune.id= :comuneId "));
		param.put("dataRiferimento",dataRiferimento);
		param.put("comuneId",comuneId);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), param, Long.class);
	}
	
	public String getCfUtenteByUsername(final String username) {
		String sql = StringUtil.concateneString("SELECT cf " 
				+ "FROM common.utente "
				+ "WHERE username = :username"
				);
		Map<String,Object> param=new HashMap<>();
		param.put("username",username);
		return namedCommonJdbcTemplate.queryForObject(sql.toString(), param, String.class);
	}
	
	public int setCfUtenteByUsername(final String username, final String cf) {
		String sql = StringUtil.concateneString("UPDATE common.utente " 
				+ "SET cf = :cf "
				+ "WHERE username = :username"
				);
		Map<String,Object> param=new HashMap<>();
		param.put("cf",cf);
		param.put("username",username);
		return namedCommonJdbcTemplate.update(sql.toString(), param);
	}


	/**
	 * 
	 * @author acolaianni
	 *
	 * @param key
	 * @param applicationCode
	 * @return
	 * @throws Exception
	 */
	public String getConfigurationValue(String key, String applicationCode) throws Exception {
  		LOGGER.info("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

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

  		LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
  		return namedCommonJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
  	}
}