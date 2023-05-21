package it.eng.tz.puglia.servizi_esterni.remote.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import it.eng.tz.puglia.servizi_esterni.remote.dto.AnagraficaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.AnagraficaRowMapper;
import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.CommonRowMapper;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Base Repository for schema Anagrafica
 */
@Repository
public class AnagraficaBaseRepository  extends RemoteRepository<AnagraficaDTO>
{

	private static final int ID_ITALIA = 1;

	@Autowired
	@Qualifier("jdbcTemplate_anagrafica")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("namedJdbcTemplate_anagrafica")
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	protected CommonRowMapper<AnagraficaDTO> getRowMapper() {
		return new AnagraficaRowMapper();
	}

	@Override
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	
	@Override
	protected String getQueryForSelectAll() {
		return StringUtil.concateneString("SELECT \"id\", \"id_regione\", \"id_provincia\", ",
				"\"nome\", \"cod_istat\", \"shape_leng\", \"shape_area\", \"cod_catastale\" ", 
				"FROM \"comune\" ");
	}

	/**
	 * find by pk method
	 */
	@Override
	protected String getQueryForFind(String codice) {
		return StringUtil.concateneString("SELECT \"id\", \"id_regione\", \"id_provincia\", ",
				"\"nome\", \"cod_istat\", \"shape_leng\", \"shape_area\", \"cod_catastale\" ", 
				"FROM \"comune\" ",
				"WHERE \"cod_catastale\" = ? ");
	}

	/**
	 * findAll by pks method
	 */
	@Override
	protected String getQueryForFindAll(List<String> codices) {
 		return StringUtil.concateneString("SELECT \"id\", \"id_regione\", \"id_provincia\", ",
				"\"nome\", \"cod_istat\", \"shape_leng\", \"shape_area\", \"cod_catastale\" ", 
				"FROM \"comune\" ",
				"WHERE \"cod_catastale\" IN (",
				codices.stream().collect(Collectors.joining("', '", "'", "'")), ") ");
	}
	
/*  public List<AnagraficaDTO> getListaBPparchiRiserve() {		// vecchio metodo di Fabrizio
         
    	List<AnagraficaDTO> list = null;
    	 
        if(!getQueryForSelectAll().isEmpty()) {	
        	list = jdbcTemplate.query(getQueryForSelectAll(), new AnagraficaRowMapper());
        }
        return list;       
    }	*/
    
/*  public List<AnagraficaDTO> getListaUcpPaesaggioRurale() {	// vecchio metodo di Fabrizio
         
    	List<AnagraficaDTO> list = null;
    	 
        if(!getQueryForSelectAll().isEmpty()) {	
        	list = jdbcTemplate.query(getQueryForSelectAll(), new AnagraficaRowMapper());
        }
        return list;       
    }	*/
    
/*  public List<AnagraficaDTO> getListaBPimmobiliAree() {		// vecchio metodo di Fabrizio
         
    	List<AnagraficaDTO> list = null;
    	 
        if(!getQueryForSelectAll().isEmpty()) {	
        	list =  jdbcTemplate.query(getQueryForSelectAll(), new AnagraficaRowMapper());
        }
        return list;       
    }	*/
    
	String dummy;
	
	
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
	
}