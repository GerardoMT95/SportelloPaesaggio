package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ModificheTab;
import it.eng.tz.puglia.autpae.entity.ModificheTabDTO;
import it.eng.tz.puglia.autpae.enumeratori.NomiTab;
import it.eng.tz.puglia.autpae.repository.base.ModificheTabBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.ModificheTabRowMapper;

/**
 * Full Repository for table modifiche_tab
 */
@Repository
public class ModificheTabFullRepository  extends ModificheTabBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(ModificheTabFullRepository.class);
    private final ModificheTabRowMapper rowMapper = new ModificheTabRowMapper();
    
	
	public ModificheTabDTO getLast(NomiTab tab) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * ")
		   .append(" from ")
		   .append(ModificheTab.getTableName())
		   .append(" where ")
		   .append(ModificheTab.tab.columnName())
		   .append(" = :")
		   .append(ModificheTab.tab.columnName())
		   .append(" ORDER BY ")
		   .append(ModificheTab.hashcode.columnName())
		   .append(" DESC ");    	   
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ModificheTab.tab.columnName(), tab.name());
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters.toString());
		return namedJdbcTemplate.query(sql.toString(), parameters, rowMapper).get(0);
	}
	

	public List<String> getInfoFromVersion(NomiTab tab, int versione) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		StringBuilder sql = new StringBuilder();
		sql.append("select ")
		   .append(ModificheTab.info)
		   .append(" from ")
		   .append(ModificheTab.getTableName())
		   .append(" where ")
		   .append(ModificheTab.tab.columnName())
		   .append(" = :")
		   .append(ModificheTab.tab.columnName())
		   .append(" and ")
		   .append(ModificheTab.hashcode.columnName())
		   .append(" > :")
		   .append(ModificheTab.hashcode.columnName())
		   .append(" ORDER BY ")
		   .append(ModificheTab.hashcode.columnName())
		   .append(" ASC ");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ModificheTab.tab.columnName(), tab.name());
		parameters.put(ModificheTab.hashcode.columnName(), versione);
		
		log.trace("Sql -> {} Parameters -> {}", sql, parameters.toString());
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, String.class);
	}
	

}