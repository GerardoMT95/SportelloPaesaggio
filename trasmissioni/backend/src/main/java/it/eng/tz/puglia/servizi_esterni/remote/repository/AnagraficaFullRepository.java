package it.eng.tz.puglia.servizi_esterni.remote.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Comune;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_ImmobiliAreeInteressePubblico;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_ImmobiliAreeInteressePubblicoComuni;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_PaesaggiRurali;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_PaesaggiRuraliComuni;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Parchi;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_ParchiComuni;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Provincia;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Anagrafica_Regione;
import it.eng.tz.puglia.servizi_esterni.remote.dto.AutoCompleteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.AutocompleteRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.TipologicaAnagraficaRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for schema Anagrafica
 */
@Repository
public class AnagraficaFullRepository
{
	private static final Logger log = LoggerFactory.getLogger(AnagraficaFullRepository.class);
	
	@Autowired
	@Qualifier("jdbcTemplate_anagrafica")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("namedJdbcTemplate_anagrafica")
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	
    public List<AutoCompleteDTO> autocompleteNazioni (String filter, int limit) throws Exception
    {
    	AutocompleteRowMapper rowMapper = new AutocompleteRowMapper("nome", "sigla");
    	StringBuilder sql = new StringBuilder("select \"nome\", \"sigla\" from \"anagrafica\".\"nazione\" where lower(\"nome\"::text) like :filter");
    	if(limit > 0)
    		sql.append(" limit :limit");
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	parameters.put("filter", StringUtil.convertRightLike(filter.toLowerCase()));
    	parameters.put("limit", limit);
    	return namedJdbcTemplate.query(sql.toString(), parameters, rowMapper);
    }
    
    public List<AutoCompleteDTO> autocompleteProvince(String filter, int limit) throws Exception
    {
    	AutocompleteRowMapper rowMapper = new AutocompleteRowMapper("nome", "id");
    	StringBuilder sql = new StringBuilder("select \"nome\", (\"id\"::text) from "+Anagrafica_Provincia.getTableName()+" where lower(\"nome\"::text) like :filter");
    	if(limit > 0)
    		sql.append(" limit :limit");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("filter", StringUtil.convertRightLike(filter.toLowerCase()));
    	parameters.put("limit", limit);
    	return namedJdbcTemplate.query(sql.toString(), parameters, rowMapper);
    }
    
    public List<AutoCompleteDTO> autocompleteComuni(String filter, String denominazioneProvincia, Integer idProvincia, Integer limit,Integer idRegione) throws Exception
    {
    	AutocompleteRowMapper rowMapper = new AutocompleteRowMapper("nome", "cod_istat");
    	Map<String, Object> parameters = new HashMap<>();
    	StringBuilder sql = new StringBuilder("select com.nome, com.cod_istat from anagrafica.comune com ")
    				      .append("join anagrafica.provincia p on p.id = com.id_provincia ")
    				      .append("where lower(com.nome) like :filter");
    	if(denominazioneProvincia != null)
    	{
    	    sql.append(" and p.nome ilike :nomeProvincia");
    	    parameters.put("nomeProvincia", denominazioneProvincia);
    	}
    	if(idProvincia != null)
    		sql.append(" and com.id_provincia = :idProvincia");
    	if(idRegione!=null) {
    		sql.append(" and com.id_regione = :idRegione");
    	}
    	if(limit != null && limit > 0)
    		sql.append(" limit :limit");
    	parameters.put("filter", StringUtil.convertRightLike(filter.toLowerCase()));
    	parameters.put("idProvincia", idProvincia);
    	parameters.put("idRegione", idRegione);
    	parameters.put("limit", limit);
    	return namedJdbcTemplate.query(sql.toString(), parameters, rowMapper);
    }
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public List<TipologicaDTO> getListaBPparchiRiserve(Set<String> listaCodici) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select distinct "
    			, Anagrafica_Parchi.codice.getCompleteName()
    			, " , "
    			, Anagrafica_Parchi.descrizione.getCompleteName()
    			, " from " , Anagrafica_Parchi.getTableName()
    			, " , "    , Anagrafica_ParchiComuni.getTableName()
    			, " where ", Anagrafica_Parchi.codice.getCompleteName()
    			, " = "    , Anagrafica_ParchiComuni.codice.getCompleteName()
    			, " and "  , Anagrafica_ParchiComuni.codice_catastale.getCompleteName()
    			, " IN ( :listaCodici )"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("listaCodici", (listaCodici!=null && listaCodici.isEmpty()) ? null : listaCodici);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
    }
    
    public List<TipologicaDTO> getListaUcpPaesaggioRurale(Set<String> listaCodici) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select distinct "
    			, Anagrafica_PaesaggiRurali.codice.getCompleteName()
    			, " , "
    			, Anagrafica_PaesaggiRurali.descrizione.getCompleteName()
    			, " from " , Anagrafica_PaesaggiRurali.getTableName()
    			, " , "    , Anagrafica_PaesaggiRuraliComuni.getTableName()
    			, " where ", Anagrafica_PaesaggiRurali.codice.getCompleteName()
    			, " = "    , Anagrafica_PaesaggiRuraliComuni.codice.getCompleteName()
    			, " and "  , Anagrafica_PaesaggiRuraliComuni.codice_catastale.getCompleteName()
    			, " IN ( :listaCodici )"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("listaCodici", (listaCodici!=null && listaCodici.isEmpty()) ? null : listaCodici);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
    }
    
    public List<TipologicaDTO> getListaBPimmobiliAree(Set<String> listaCodici) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select distinct "
    			, Anagrafica_ImmobiliAreeInteressePubblico.codice.getCompleteName()
    			, " , "
    			, Anagrafica_ImmobiliAreeInteressePubblico.oggetto.getCompleteName()
    			, " as "
    			, Anagrafica_ImmobiliAreeInteressePubblico.descrizione.columnName()
    			, " from " , Anagrafica_ImmobiliAreeInteressePubblico.getTableName()
    			, " , "    , Anagrafica_ImmobiliAreeInteressePubblicoComuni.getTableName()
    			, " where ", Anagrafica_ImmobiliAreeInteressePubblico.codice.getCompleteName()
    			, " = "    , Anagrafica_ImmobiliAreeInteressePubblicoComuni.codice.getCompleteName()
    			, " and "  , Anagrafica_ImmobiliAreeInteressePubblicoComuni.codice_catastale.getCompleteName()
    			, " IN ( :listaCodici )"
    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("listaCodici", (listaCodici!=null && listaCodici.isEmpty()) ? null : listaCodici);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
    }
    
    public List<TipologicaDTO> selectAllBPparchiRiserve() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Anagrafica_Parchi.codice.getCompleteName()
    			, " , "
    			, Anagrafica_Parchi.descrizione.getCompleteName()
    			, " from " , Anagrafica_Parchi.getTableName()
    			);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, null);
    	return namedJdbcTemplate.query(sql.toString(), new TipologicaAnagraficaRowMapper());
    }
    
    public List<TipologicaDTO> selectAllUcpPaesaggioRurale() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Anagrafica_PaesaggiRurali.codice.getCompleteName()
    			, " , "
    			, Anagrafica_PaesaggiRurali.descrizione.getCompleteName()
    			, " from " , Anagrafica_PaesaggiRurali.getTableName()
    			);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, null);
    	return namedJdbcTemplate.query(sql.toString(), new TipologicaAnagraficaRowMapper());
    }
    
    public List<TipologicaDTO> selectAllBPimmobiliAree() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString(" select "
    			, Anagrafica_ImmobiliAreeInteressePubblico.codice.getCompleteName()
    			, " , "
    			, Anagrafica_ImmobiliAreeInteressePubblico.oggetto.getCompleteName()
    			, " as "
    			, Anagrafica_ImmobiliAreeInteressePubblico.descrizione.columnName()
    			, " from " , Anagrafica_ImmobiliAreeInteressePubblico.getTableName()
    			);
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, null);
    	return namedJdbcTemplate.query(sql.toString(), new TipologicaAnagraficaRowMapper());
    }
	
	public long countParchi(String codiceCatastale) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
												," from ", Anagrafica_ParchiComuni.getTableName()
												," where "
												,Anagrafica_ParchiComuni.codice_catastale.columnName()
												," = :"
												,Anagrafica_ParchiComuni.codice_catastale.columnName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Anagrafica_ParchiComuni.codice_catastale.columnName(), codiceCatastale);
		log.trace("Eseguo la query: {} con il seguente parametro: codiceCatastale={}", sql, codiceCatastale);
		return namedJdbcTemplate.queryForObject(sql, parameters, Long.class);
	}
	
	public long countPaesaggiRurali(String codiceCatastale) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
												," from ", Anagrafica_PaesaggiRuraliComuni.getTableName()
												," where "
												,Anagrafica_PaesaggiRuraliComuni.codice_catastale.columnName()
												," = :"
												,Anagrafica_PaesaggiRuraliComuni.codice_catastale.columnName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Anagrafica_PaesaggiRuraliComuni.codice_catastale.columnName(), codiceCatastale);
		log.trace("Eseguo la query: {} con il seguente parametro: codiceCatastale={}", sql, codiceCatastale);
		return namedJdbcTemplate.queryForObject(sql, parameters, Long.class);
	}
	
	public long countImmobiliAreeInteressePubblico(String codiceCatastale) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select count(*) "                                           
												," from ", Anagrafica_ImmobiliAreeInteressePubblicoComuni.getTableName()
												," where "
												,Anagrafica_ImmobiliAreeInteressePubblicoComuni.codice_catastale.columnName()
												," = :"
												,Anagrafica_ImmobiliAreeInteressePubblicoComuni.codice_catastale.columnName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(Anagrafica_ImmobiliAreeInteressePubblicoComuni.codice_catastale.columnName(), codiceCatastale);
		log.trace("Eseguo la query: {} con il seguente parametro: codiceCatastale={}", sql, codiceCatastale);
		return namedJdbcTemplate.queryForObject(sql, parameters, Long.class);
	}
	
	/**
	 * retreive delle mail associate ai parchi, 
	 * vengono prelevate anche quelle specifiche sui singoli comuni (se esistenti) 
	 * @autor Adriano Colaianni
	 * @date 19 ago 2022
	 * @param codici elenco codici parchi
	 * @return elenco distinto di descrizione,mail es.:
	 *    [{"parco nome 1","mailparco1@mail.it"},{"parco nome 1","mailcomunespecificaparco1@mail.it"}]
	 * @throws Exception
	 */
	public List<TipologicaDTO> emailParchi(List<String> codici) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		StringBuilder sql = new StringBuilder(StringUtil.concateneString("select "
											," DISTINCT "
											, Anagrafica_Parchi.descrizione.getCompleteName()
											, " ,"
											, " coalesce( "
									   		, Anagrafica_ParchiComuni.mail.getCompleteName()
									   		, " ,"
									   		, Anagrafica_Parchi.mail.getCompleteName()
									   		, " ) "
									   		, " AS "
									   		, Anagrafica_Parchi.codice.columnName()
										    , " from "
										    , Anagrafica_Parchi.getTableName()
										    , " LEFT JOIN "
										    , Anagrafica_ParchiComuni.getTableName()
										    , " ON  "
										    , Anagrafica_Parchi.codice.getCompleteName()
										    , " =  "
										    , Anagrafica_ParchiComuni.codice.getCompleteName()
										    , " where "
										    , Anagrafica_Parchi.codice.getCompleteName()
										    , " IN ("
										    , ":codici"
										    , ") "
										    , " GROUP BY "
										    , Anagrafica_Parchi.codice.getCompleteName()
										    , " , "
										    , " coalesce( "
									   		, Anagrafica_ParchiComuni.mail.getCompleteName()
									   		, " ,"
									   		, Anagrafica_Parchi.mail.getCompleteName()
									   		, " ) "
										  ));
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codici", (codici!=null && codici.isEmpty()) ? null : codici);

		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		return namedJdbcTemplate.query(sql.toString(), parameters, new TipologicaAnagraficaRowMapper());
	}
	
    public List<String> getAllCodiciIstat() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String regione = "PUGLIA";
		
    	String sql = StringUtil.concateneString ( " select "
								    			, Anagrafica_Comune .cod_istat.getCompleteName()
								    			, " from " 
								    			, Anagrafica_Comune .getTableName()
								    			, " , "    
								    			, Anagrafica_Regione.getTableName()
								    			, " where "
								    			, Anagrafica_Comune .id_regione.getCompleteName()
								    			, " = "
								    			, Anagrafica_Regione.id.getCompleteName()
								    			, " and "
								    			, Anagrafica_Regione.nome.getCompleteName()
								    			, " ILIKE "
								    			, ":nomeRegione"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nomeRegione", regione.trim());
		
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new RowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String codice = null;
				if(rs != null)
				{
					codice = rs.getString(Anagrafica_Comune.cod_istat.columnName());
				}
				return codice;
			}
		});
    }
	
    public List<String> getAllCodiciCatastali() throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String regione = "PUGLIA";
		
    	String sql = StringUtil.concateneString ( " select "
								    			, Anagrafica_Comune .cod_catastale.getCompleteName()
								    			, " from " 
								    			, Anagrafica_Comune .getTableName()
								    			, " , "    
								    			, Anagrafica_Regione.getTableName()
								    			, " where "
								    			, Anagrafica_Comune .id_regione.getCompleteName()
								    			, " = "
								    			, Anagrafica_Regione.id.getCompleteName()
								    			, " and "
								    			, Anagrafica_Regione.nome.getCompleteName()
								    			, " ILIKE "
								    			, ":nomeRegione"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nomeRegione", regione.trim());
		
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new RowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String codice = null;
				if(rs != null)
				{
					codice = rs.getString(Anagrafica_Comune.cod_catastale.columnName());
				}
				return codice;
			}
		});
    }
	
    public List<String> getAllCodiciCatastaliInProvincia(String siglaProvincia) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString ( " select "
								    			, Anagrafica_Comune.cod_catastale.getCompleteName()
								    			, " from " 
								    			, Anagrafica_Comune.getTableName()
								    			, " , "    
								    			, Anagrafica_Provincia.getTableName()
								    			, " where "
								    			, Anagrafica_Comune.id_provincia.getCompleteName()
								    			, " = "
								    			, Anagrafica_Provincia.id.getCompleteName()
								    			, " and "
								    			, Anagrafica_Provincia.sigla.getCompleteName()
								    			, " ILIKE "
								    			, ":siglaProvincia"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("siglaProvincia", siglaProvincia.trim());
		
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new RowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String codice = null;
				if(rs != null)
				{
					codice = rs.getString(Anagrafica_Comune.cod_catastale.columnName());
				}
				return codice;
			}
		});
    }
    
    public List<String> getAllCodiciIstatInProvincia(String siglaProvincia) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

    	String sql = StringUtil.concateneString ( " select "
								    			, Anagrafica_Comune.cod_istat.getCompleteName()
								    			, " from " 
								    			, Anagrafica_Comune.getTableName()
								    			, " , "    
								    			, Anagrafica_Provincia.getTableName()
								    			, " where "
								    			, Anagrafica_Comune.id_provincia.getCompleteName()
								    			, " = "
								    			, Anagrafica_Provincia.id.getCompleteName()
								    			, " and "
								    			, Anagrafica_Provincia.sigla.getCompleteName()
								    			, " ILIKE "
								    			, ":siglaProvincia"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("siglaProvincia", siglaProvincia.trim());
		
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new RowMapper<String>()
		{
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				String codice = null;
				if(rs != null)
				{
					codice = rs.getString(Anagrafica_Comune.cod_istat.columnName());
				}
				return codice;
			}
		});
    }
    
    public String getCodIstatFromCatastale(String codiceCatastale) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
    	String sql = StringUtil.concateneString ( " select "
								    			, Anagrafica_Comune.cod_istat.columnName()
								    			, " from " 
								    			, Anagrafica_Comune.getTableName()
								    			, " where "
								    			, Anagrafica_Comune.cod_catastale.columnName()
								    			, " ILIKE "
								    			, ":codiceCatastale"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceCatastale", codiceCatastale.trim());
		
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
	
    public String getCodCatastaleFromIstat(String codiceIstat) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
    	String sql = StringUtil.concateneString ( " select "
								    			, Anagrafica_Comune.cod_catastale.columnName()
								    			, " from " 
								    			, Anagrafica_Comune.getTableName()
								    			, " where "
								    			, Anagrafica_Comune.cod_istat.columnName()
								    			, " ILIKE "
								    			, ":codiceIstat"
								    			);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codiceIstat", codiceIstat.trim());
		
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
	
}