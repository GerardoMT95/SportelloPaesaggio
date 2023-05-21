package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PlainNumberValueLabelRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.PlainStringValueLabelRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.AnagraficaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.AnagraficaRowMapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.RemoteRowMapper;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Accesso al DB anagrafica
 */
@Repository
public class AnagraficaRepository extends RemoteRepository<AnagraficaDTO> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AnagraficaRepository.class);

	private static final int ID_ITALIA = 1;
	
	@Value("${datasource.anagrafica.schema}")
	private String schema;
	
	@Override
	protected String getSchema() {
		return schema;
	}
	
	@Autowired
	@Qualifier("jdbcTemplate_anagrafica")
	private JdbcTemplate anagraficaJdbcTemplate;
	
	@Autowired
	@Qualifier("namedJdbcTemplate_anagrafica")
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@PostConstruct
	private void initJdbcTemplate() throws Exception {
		this.jdbcTemplate = anagraficaJdbcTemplate;
	}
	
	@Override
	protected RemoteRowMapper<AnagraficaDTO> getRowMapper() {
		return new AnagraficaRowMapper();
	}

	
	//"anagrafica".
	@Override
	protected String getQueryForSelectAll() {
		return StringUtil.concateneString("SELECT \"id\", \"id_regione\", \"id_provincia\", ",
				"\"nome\", \"cod_istat\", \"shape_leng\", \"shape_area\", \"cod_catastale\" ", 
				"FROM \"anagrafica\".\"comune\" ");
	}
	
	/**
	 * find by pk method
	 */
	@Override
	protected String getQueryForFind(String codice) {
		return StringUtil.concateneString("SELECT \"id\", \"id_regione\", \"id_provincia\", ",
				"\"nome\", \"cod_istat\", \"shape_leng\", \"shape_area\", \"cod_catastale\" ", 
				"FROM \"anagrafica\".\"comune\" ",
				"WHERE \"cod_catastale\" = ? ");
	}

	/**
	 * findAll by pks method
	 */
	@Override
	protected String getQueryForFindAll(List<String> codices) {
 		return StringUtil.concateneString("SELECT \"id\", \"id_regione\", \"id_provincia\", ",
				"\"nome\", \"cod_istat\", \"shape_leng\", \"shape_area\", \"cod_catastale\" ", 
				"FROM \"anagrafica\".\"comune\" ",
				"WHERE \"cod_catastale\" IN (",
				codices.stream().collect(Collectors.joining("', '", "'", "'")), ") ");
	}
	
	protected String getQueryForAutocompletePuglia() {
 		return StringUtil.concateneString("SELECT c.\"id\", c.\"id_regione\", c.\"id_provincia\", ",
				"c.\"nome\", c.\"cod_istat\", c.\"shape_leng\", c.\"shape_area\", c.\"cod_catastale\" ", 
				"FROM \"anagrafica\".\"comune\" c, \"anagrafica\".\"regione\" r ",
				"WHERE c.\"id_regione\"=r.\"id\" ",
				" AND UPPER(r.\"nome\")='PUGLIA' ",
				" AND c.\"nome\" LIKE  ?");
	}
	
	
	
    public PlainNumberValueLabel findByCatastale(String codCatastale) throws Exception
    {
    	StringBuilder sql = new StringBuilder("select \"id\" as value,"
    			+ " \"cod_istat\" as label FROM \"anagrafica\".\"comune\" where cod_catastale =  :filter limit 1");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("filter", codCatastale);
    	return namedJdbcTemplate.queryForObject(sql.toString(), parameters,new PlainNumberValueLabelRowMapper() );
    }
    
	public String getCodiceIstatFromCatastale(String codiceCatastale) {
		String sql = StringUtil.concateneString(
				  "SELECT "
				+ "cod_istat"
				+ " FROM anagrafica.comune"
				+ " WHERE " 
				+ "cod_catastale ILIKE " 
				+ ":codiceCatastale"
				);      
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codiceCatastale", StringUtil.convertLike(codiceCatastale));
        return this.namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
	
	public List<AnagraficaDTO> findComuniPugliaLike(String pattern) {
		try {
			if (pattern == null) {
				pattern="";
			}
			return this.jdbcTemplate.query(getQueryForAutocompletePuglia(), getRowMapper(),StringUtil.convertLike(pattern));
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
	public List<PlainNumberValueLabel> autocompleteNazioni(String filter, int limit) throws Exception {
    	StringBuilder sql = new StringBuilder("select \"id\" as value, \"nome\" as label from \"anagrafica\".\"nazione\" where lower(\"nome\"::text) like :filter");
    	if(limit > 0) {
    		sql.append(" limit :limit");
    	}
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	parameters.put("filter", StringUtil.convertRightLike(filter.toLowerCase()));
    	parameters.put("limit", limit);
    	return this.namedJdbcTemplate.query(sql.toString(), parameters, new PlainNumberValueLabelRowMapper());
    }
    
    public List<PlainNumberValueLabel> autocompleteProvince(String filter, int limit) throws Exception
    {
    	StringBuilder sql = new StringBuilder("select \"nome\" as label, \"id\" as value from \"anagrafica\".\"provincia\" where lower(\"nome\"::text) like :filter");
    	if(limit > 0) {
    		sql.append(" limit :limit");
    	}
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("filter", StringUtil.convertRightLike(filter.toLowerCase()));
    	parameters.put("limit", limit);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new PlainNumberValueLabelRowMapper());
    }
    
    public List<PlainNumberValueLabel> autocompleteComuni(String filter, Integer idProvincia, Integer limit) throws Exception
    {
    	StringBuilder sql = new StringBuilder("select \"nome\" as label, \"id\" as value from \"anagrafica\".\"comune\" where lower(\"nome\"::text) like :filter");
    	if(idProvincia != null) {
    		sql.append(" and id_provincia = :idProvincia");
    	}
    	if(limit != null && limit > 0) {
    		sql.append(" limit :limit");
    	}
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("filter", StringUtil.convertRightLike(filter.toLowerCase()));
    	parameters.put("idProvincia", idProvincia);
    	parameters.put("limit", limit);
    	return namedJdbcTemplate.query(sql.toString(), parameters, new PlainNumberValueLabelRowMapper());
    }
    
    public List<PlainStringValueLabel> findBpParchi(Set<String> codIstat) throws Exception
    {
    	StringBuilder sql = new StringBuilder(
    			"select p.\"codice\" as value ,p.\"descrizione\" as label, p.\"mail\" as linked " + 
    			"from " + 
    			"\"anagrafica\".\"parchi\" p ,\"anagrafica\".\"parchi_comuni\" pc " + 
    			"where p.\"codice\" = pc.\"codice\" and "
    			+ " pc.\"codice_istat\" IN ( :listaCodIstat )");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("listaCodIstat", codIstat);
    	return namedJdbcTemplate.query(sql.toString(), parameters,new PlainStringValueLabelRowMapper() );
    }
    
    public List<PlainStringValueLabel> findUcpPaesaggi(Set<String> codIstat) throws Exception
    {
    	StringBuilder sql = new StringBuilder(
    			"select p.\"codice\" as value ,p.\"descrizione\" as label ,null as linked " + 
    			"from " + 
    			"\"anagrafica\".\"paesaggi_rurali\" p ,\"anagrafica\".\"paesaggi_rurali_comuni\" pc " + 
    			"where p.\"codice\" = pc.\"codice\" and "
    			+ " pc.\"codice_istat\" IN ( :listaCodIstat )");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("listaCodIstat", codIstat);
    	return namedJdbcTemplate.query(sql.toString(), parameters,new PlainStringValueLabelRowMapper() );
    }
    
    public List<PlainStringValueLabel> findBpImmobili(Set<String> codIstat) throws Exception
    {
    	StringBuilder sql = new StringBuilder(
    			"select p.\"codice\" as value ,p.\"oggetto\" as label ,null as linked " + 
    			"from " + 
    			"\"anagrafica\".\"immobili_aree_interesse_pubblico\" p ,\"anagrafica\".\"immobili_aree_interesse_pubblico_comuni\" pc " + 
    			"where p.\"codice\" = pc.\"codice\" and "
    			+ " pc.\"codice_istat\" IN ( :listaCodIstat )");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("listaCodIstat", codIstat);
    	return namedJdbcTemplate.query(sql.toString(), parameters,new PlainStringValueLabelRowMapper() );
    }
    
    
    public String getCodiceCatastale(Integer idNazione, Integer idComune) {
		if(idNazione != null && ID_ITALIA != idNazione.intValue()) {
			String sql = "select trim(cod_catastale) from anagrafica.nazione where id = ?";
			List<String> list = this.jdbcTemplate.queryForList(sql, String.class, idNazione);
			return ListUtil.isNotEmpty(list)? list.get(0): null;
		}else if(idNazione != null && ID_ITALIA == idNazione.intValue()) {
			if(idComune != null) {
				String sql = "select cod_catastale from anagrafica.comune where id = ?";
				List<String> list = this.jdbcTemplate.queryForList(sql, String.class, idComune);
				return ListUtil.isNotEmpty(list) ? list.get(0): null;
			}
		}
		return null;
	}
    
    public String findSiglaProvinciaById(int id) {
    	StringBuilder sql = new StringBuilder(
    			"select p.\"sigla\"  " + 
    			"from " + 
    			"\"anagrafica\".\"provincia\" p " + 
    			"where p.\"id\" = :id ");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("id", id);
    	return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
    
    public String findSiglaNazioneById(int id) {
    	StringBuilder sql = new StringBuilder(
    			"select p.\"sigla\"  " + 
    			"from " + 
    			"\"anagrafica\".\"nazione\" p " + 
    			"where p.\"id\" = :id ");
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("id", id);
    	return namedJdbcTemplate.queryForObject(sql.toString(), parameters, String.class);
    }
    
}