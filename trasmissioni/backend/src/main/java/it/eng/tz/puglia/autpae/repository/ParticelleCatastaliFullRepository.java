package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ParticelleCatastali;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;
import it.eng.tz.puglia.autpae.repository.base.ParticelleCatastaliBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.ParticelleCatastaliRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for table autpae.particelle_catastali
 */
@Repository
public class ParticelleCatastaliFullRepository extends ParticelleCatastaliBaseRepository {

	private static final Logger log = LoggerFactory.getLogger(ParticelleCatastaliFullRepository.class);
	private final ParticelleCatastaliRowMapper rowMapper = new ParticelleCatastaliRowMapper();
	
	
	/**
	 * select all 
	 */
	final String selectAll = new StringBuilder("select").append(" \"pratica_id\"").append(",\"comune_id\"")
			.append(",\"id\"").append(",\"livello\"").append(",\"sezione\"").append(",\"foglio\"")
			.append(",\"particella\"")
			.append(",\"sub\"")
			.append(",\"cod_cat\"")
			.append(",")
			.append(ParticelleCatastali.oid.columnName())
			.append(",")
			.append(ParticelleCatastali.note.columnName())
			.append(",")
			.append(ParticelleCatastali.descr_sezione.columnName())
			.append(" from ").append(ParticelleCatastali.getTableName()).toString();
	
	public List<ParticelleCatastaliDTO> select(Long praticaId, long comuneId) {
		final String sql = new StringBuilder(selectAll).append(" where \"pratica_id\" = ?")
				.append(" and \"comune_id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		parameters.add(comuneId);
		return super.queryForList(sql, this.rowMapper,parameters);
	}

	public int deleteByKeyLoc(Long praticaId, long comuneId) {
		final String sql = new StringBuilder("delete from ").append(ParticelleCatastali.getTableName())
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		parameters.add(comuneId);
		return super.update(sql, parameters);
	}
	
	public int insertMultiple(List<ParticelleCatastaliDTO> particella, long idFascicolo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String sql = StringUtil.concateneString(" insert into ", ParticelleCatastali.getTableName()
												," ( "
													  , ParticelleCatastali.pratica_id.columnName()
												, ", ", ParticelleCatastali.comune_id.columnName()
												, ", ", ParticelleCatastali.id.columnName()
//												, ", ", ParticelleCatastali.cod_cat.columnName()
												, ", ", ParticelleCatastali.livello.columnName()
												, ", ", ParticelleCatastali.sezione.columnName()
												, ", ", ParticelleCatastali.foglio.columnName()
												, ", ", ParticelleCatastali.particella.columnName()
												, ", ", ParticelleCatastali.sub.columnName()
												, ", ", ParticelleCatastali.cod_cat.columnName()
												, ", ", ParticelleCatastali.oid.columnName()
												, ", ", ParticelleCatastali.note.columnName()
												, ", ", ParticelleCatastali.descr_sezione.columnName()
												, " ) "
												, " values ");
		
		for (int i = 0; i < particella.size(); i++) {
		
				   sql = StringUtil.concateneString( sql
													, " ( "
													      ,      idFascicolo
													, ", "," :", ParticelleCatastali.comune_id.columnName(),i
													, ", "," :", ParticelleCatastali.id.columnName(),i
//													, ", "," :", ParticelleCatastali.cod_cat.columnName(),i
													, ", "," :", ParticelleCatastali.livello.columnName(),i
													, ", "," :", ParticelleCatastali.sezione.columnName(),i
													, ", "," :", ParticelleCatastali.foglio.columnName(),i
													, ", "," :", ParticelleCatastali.particella.columnName(),i
													, ", "," :", ParticelleCatastali.sub.columnName(),i
													, ", "," :", ParticelleCatastali.cod_cat.columnName(),i
													, ", "," :", ParticelleCatastali.oid.columnName(),i
													, ", "," :", ParticelleCatastali.note.columnName(),i
													, ", "," :", ParticelleCatastali.descr_sezione.columnName(),i
													, " ) "
													, ","
													);
				   parameters.put(ParticelleCatastali.comune_id.columnName()+i, particella.get(i).getComuneId());
				   parameters.put(ParticelleCatastali.id.columnName()+i, particella.get(i).getId());
//				   parameters.put(ParticelleCatastali.cod_cat.columnName()+i, particella.get(i).getCodCat());
				   parameters.put(ParticelleCatastali.livello.columnName()+i, particella.get(i).getLivello());
				   parameters.put(ParticelleCatastali.sezione.columnName()+i, particella.get(i).getSezione());
				   parameters.put(ParticelleCatastali.foglio.columnName()+i, particella.get(i).getFoglio());
				   parameters.put(ParticelleCatastali.particella.columnName()+i, particella.get(i).getParticella());
				   parameters.put(ParticelleCatastali.sub.columnName()+i, particella.get(i).getSub());
				   parameters.put(ParticelleCatastali.cod_cat.columnName()+i, particella.get(i).getCodCat());
				   parameters.put(ParticelleCatastali.oid.columnName()+i, particella.get(i).getOid());
				   parameters.put(ParticelleCatastali.note.columnName()+i, particella.get(i).getNote());
				   parameters.put(ParticelleCatastali.descr_sezione.columnName()+i, particella.get(i).getDescrSezione());
		}
		
		sql = sql.substring(0,sql.length()-1) + ";";

		log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
		return namedJdbcTemplate.update(sql, parameters);
	}
	
	public List<String> selectComuniIdInteressati(long idFascicolo) throws Exception {
		log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		String sql = StringUtil.concateneString(" select distinct "
												, ParticelleCatastali.comune_id.columnName()
												, " from ", ParticelleCatastali.getTableName()
												, " where "
												, ParticelleCatastali.pratica_id.columnName()
												, " = :"
												, ParticelleCatastali.pratica_id.columnName()
												);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ParticelleCatastali.pratica_id.columnName(), idFascicolo);
		log.trace("Eseguo la query: {} con il seguente parametro: idFascicolo={}", sql, idFascicolo);
		return namedJdbcTemplate.queryForList(sql, parameters, String.class);
	}
	
	/**
	 * aggiorna elaborato e last_start_time
	 * 
	 * @param string
	 */
	public void updateElaborato(final int id, final String elaborato) {
		final String sql = StringUtil.concateneString("UPDATE \"particelle_catastali\"", " SET \"elaborato\"= ? ",
				",\"last_start_time\" = now()::timestamp ", " WHERE \"id\"= ? ");
		super.update(sql, List.of(elaborato, id));
	}

	public void updateElaborato(final int id, final String elaborato, final Long oid) {
		final String sql = StringUtil.concateneString("UPDATE \"particelle_catastali\"", " SET \"elaborato\"= ? ",
				",\"last_start_time\" = now()::timestamp ", ",oid = ?", " WHERE \"id\"= ? ");
		super.update(sql, elaborato, oid, id);
	}
	
	public List<ParticelleCatastaliDTO> findLocationParticelleNonElaborati() {
		final String sql = StringUtil.concateneString(
				"SELECT  		 \"pl\".\"id\", "
							   ,"\"pl\".\"comune_id\", "
							   ,"\"pl\".\"pratica_id\", "
							   ,"\"pl\".\"cod_cat\", "
							   ,"\"pl\".\"sezione\", "
							   ,"\"pl\".\"foglio\", "
							   ,"\"pl\".\"particella\", "
							   ,"\"pl\".\"sub\", "
							   ,"\"pl\".\"livello\", "
							   ,"\"pl\".\"note\", "
							   ,"\"pl\".\"oid\", "
							   ,"\"f\" .\"tipo_selezione_localizzazione\", "
							   ,"\"f\" .\"stato\" "
			   ,"FROM 			 \"particelle_catastali\" \"pl\", "
							   ,"\"fascicolo\" \"f\" "
			   ,"WHERE	  		 \"pl\".\"pratica_id\" = \"f\".\"id\" "
						 ," AND (\"pl\".\"oid\" is null  or \"pl\".\"oid\"=0 ) " // nessuna geometria
						 ," AND \"f\".\"tipo_selezione_localizzazione\"='", TipoLocalizzazione.PTC.name(), "' "
						 ," AND \"f\".\"stato\" != ", "'", StatoFascicolo.WORKING.name(), "' "
						 ," AND \"f\".\"stato\" != ", "'", StatoFascicolo.CANCELLED.name(), "' "
						 ," AND ( \"pl\".\"elaborato\" is null OR  \"pl\".\"elaborato\"='I' OR  (\"pl\".\"elaborato\"='A' AND (extract(epoch from now()::timestamp ) - extract(epoch from \"pl\".\"last_start_time\" )> 3600) ) )"
		);
		final List<ParticelleCatastaliDTO> ret = super.queryForList(sql, this.rowMapper);
		return ret;
	}

}