package it.eng.tz.puglia.autpae.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.FascicoloIntervento;
import it.eng.tz.puglia.autpae.repository.base.FascicoloInterventoBaseRepository;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for table fascicolo_intervento
 */
@Repository
public class FascicoloInterventoFullRepository extends FascicoloInterventoBaseRepository
{	
	private static final Logger log = LoggerFactory.getLogger(FascicoloInterventoFullRepository.class);
	
	
	public int insertMultiple(List<Long> listaIdTipiQualificazioni, Long idFascicolo) throws Exception	{
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
	
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String sql = StringUtil.concateneString(" insert into ", FascicoloIntervento.getTableName()
												," ( "
												, FascicoloIntervento.id_tipi_qualificazioni.columnName(), ", "
												, FascicoloIntervento.id_fascicolo.columnName()
												, " ) "
												, " values ");
		
		for (int i = 0; i < listaIdTipiQualificazioni.size(); i++) 
		{
				   sql = StringUtil.concateneString( sql
													, " ( "
													, " :", FascicoloIntervento.id_tipi_qualificazioni.columnName(),i
													, ", ", idFascicolo
													, " ) "
													, ","
													);
				   parameters.put(FascicoloIntervento.id_tipi_qualificazioni.columnName()+i, listaIdTipiQualificazioni.get(i));				
		}
		
		sql = sql.substring(0,sql.length()-1) + ";";

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}

}