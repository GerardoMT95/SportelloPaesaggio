package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ParchiPaesaggiImmobili;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.repository.base.ParchiPaesaggiImmobiliBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.ParchiPaesaggiImmobiliRowMapper;

/**
 * Full Repository for table autpae.parchi_paesaggi_immobili
 */
@Repository
public class ParchiPaesaggiImmobiliFullRepository extends ParchiPaesaggiImmobiliBaseRepository {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ParchiPaesaggiImmobiliFullRepository.class);
    private final ParchiPaesaggiImmobiliRowMapper rowMapper = new ParchiPaesaggiImmobiliRowMapper();

    /**
     * select all 
     */
        final String selectAll = new StringBuilder("select")
                                     .append(" \"pratica_id\"")
                                     .append(",\"comune_id\"")
                                     .append(",\"tipo_vincolo\"")
                                     .append(",\"codice\"")
                                     .append(",\"descrizione\"")
                                     .append(",\"selezionato\"")
                                     .append(",\"info\"")
                                     .append(",\"data_inserimento\"")
                                     //.append(" from \"autpae\".\"parchi_paesaggi_immobili\"")
                                     .append(" from ")
                                     .append(ParchiPaesaggiImmobili.getTableName())
                                     .toString();

    public List<ParchiPaesaggiImmobiliDTO> select(Long praticaId, Long comuneId, String tipoVincolo){
    	StringBuilder sb=new StringBuilder(selectAll)
    			.append(" where \"pratica_id\" = ?")
                .append(" and \"comune_id\" = ?")
                .append(" and \"tipo_vincolo\" = ?");
    	final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(comuneId);
        parameters.add(tipoVincolo);
        return super.queryForList(sb.toString(), this.rowMapper,parameters);
    }
    
    /**
     * resetta il campo selezionato a null in tutte le possibili selezioni
     * @param praticaId
     * @param comuneId
     * @param tipoVincolo
     * @return
     */
    private int resetSelezioni(Long praticaId,Long comuneId, String tipoVincolo){
        final String sql = new StringBuilder("update ")
        							.append(ParchiPaesaggiImmobili.getTableName())
                                     .append(" set \"selezionato\" = null ")
                                     .append(" where \"pratica_id\" = ?")
                                     .append(" and \"comune_id\" = ?")
                                     .append(" and \"tipo_vincolo\" = ?")
                                     .toString();
        final List<Object> parameters = new ArrayList<Object>();
        parameters.add(praticaId);
        parameters.add(comuneId);
        parameters.add(tipoVincolo);
        return super.update(sql, parameters);
    }
    
    /**
     * imposta il campo selezionato=S sulla lista di codici passati
     * @param praticaId
     * @param comuneId
     * @param tipoVincolo
     * @param codici
     * @return
     */
    public int setSelezioni(Long praticaId, Long comuneId, String tipoVincolo, List<String> codici){
    	this.resetSelezioni(praticaId, comuneId, tipoVincolo);
    	if(codici==null || codici.size()<=0) return 0;
    // 	Set<String> codiciSet=codici.stream().collect(Collectors.toSet());
    	final String sql = new StringBuilder("update ")
    								 .append(ParchiPaesaggiImmobili.getTableName())
                                     .append(" set \"selezionato\" = 'S' ")
                                     .append(" where \"pratica_id\" = :pratica_id ")
                                     .append(" and \"comune_id\" = :comune_id ")
                                     .append(" and \"tipo_vincolo\" = :tipo_vincolo ")
                                     .append(" and \"codice\" IN ( :codici ) ")
                                     .toString();
        final Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("pratica_id",praticaId);
        parameters.put("comune_id",comuneId);
        parameters.put("tipo_vincolo",tipoVincolo);
        parameters.put("codici", (codici!=null && codici.isEmpty()) ? null : codici);
        return super.namedUpdate(sql, parameters);
    }

/*	public int insertMultiple(List<TipologicaDTO> tipologica, long idFascicolo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		    String sql = StringUtil.concateneString(" insert into ", ParchiPaesaggiImmobili.getTableName()
													," ( "
													, ParchiPaesaggiImmobili.codice		 .columnName(), ", "
													, ParchiPaesaggiImmobili.pratica_id.columnName(), ", "
													, ParchiPaesaggiImmobili.selezionato .columnName()
													, " ) "
													, " values ");
		
		for (int i = 0; i < tipologica.size(); i++) {
			
				   sql = StringUtil.concateneString( sql
													, " ( "
													, 		":", ParchiPaesaggiImmobili.codice	   .columnName(), i
												    , ", ", 	 idFascicolo
												    , ", ", ":", ParchiPaesaggiImmobili.selezionato.columnName(), i
													, " ) "
													, ","
													);
				   parameters.put(ParchiPaesaggiImmobili.codice		.columnName()+i, tipologica.get(i).getCodice()							  );
				   parameters.put(ParchiPaesaggiImmobili.selezionato.columnName()+i, tipologica.get(i).getLabel ().equals("true") ? "S" : null);
		}
		
		sql = sql.substring(0,sql.length()-1) + ";";

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}	*/
    
}