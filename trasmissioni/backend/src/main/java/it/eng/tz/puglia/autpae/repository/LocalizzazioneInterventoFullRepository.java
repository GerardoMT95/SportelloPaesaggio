package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.LocalizzazioneIntervento;
import it.eng.tz.puglia.autpae.dbMapping.ParchiPaesaggiImmobili;
import it.eng.tz.puglia.autpae.dbMapping.ParticelleCatastali;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.repository.base.LocalizzazioneInterventoBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.LocalizzazioneInterventoRowMapper;

/**
 * Full Repository for table autpae.localizzazione_intervento
 */
@Repository
public class LocalizzazioneInterventoFullRepository	extends LocalizzazioneInterventoBaseRepository {

	private static final Logger log = LoggerFactory.getLogger(LocalizzazioneInterventoFullRepository.class);
	private final LocalizzazioneInterventoRowMapper rowMapper = new LocalizzazioneInterventoRowMapper();

	/**
	 * select all 
	 */
	final String selectAll = new StringBuilder("select").append(" \"pratica_id\"").append(",\"comune_id\"")
			.append(",\"indirizzo\"").append(",\"num_civico\"").append(",\"piano\"").append(",\"interno\"")
			.append(",\"dest_uso_att\"").append(",\"dest_uso_prog\"").append(",\"comune\"")
			.append(",\"sigla_provincia\"").append(",\"data_riferimento_catastale\"").append(",\"strade\"")
			.append(" from ").append(LocalizzazioneIntervento.getTableName()).toString();

	/**
	 * select by praticaId method
	 */
	public List<LocalizzazioneInterventoDTO> select(Long praticaId) {
		final String sql = new StringBuilder(selectAll)
				.append(" where \"pratica_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		return super.queryForList(sql, this.rowMapper, parameters);
	}
	
	/**
	 * delete by pk method
	 */
	public int delete(LocalizzazioneInterventoDTO entity) {
		final String sql = new StringBuilder("delete from ")
				.append(LocalizzazioneIntervento.getTableName())
				.append(" where \"pratica_id\" = ?").append(" and \"comune_id\" = ?").toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getPraticaId());
		parameters.add(entity.getComuneId());
		return super.update(sql, parameters);
	}
	
	/**
	 * seleziona gli idEnti sui quali la pratica sta effettivamente operando in localizzazione
	 */
	public List<Long> selectEffettivo(long idPratica) {
		
		final String sql = new StringBuilder("select distinct ")
									.append(LocalizzazioneIntervento.comune_id.name())
									.append(" from ")
									.append(LocalizzazioneIntervento.getTableName())
									.append(" where ")
									.append(LocalizzazioneIntervento.pratica_id.name())
									.append(" = :idPratica")
									.append(" UNION ")
									.append(" select distinct ")
									.append(ParticelleCatastali.comune_id.name())
									.append(" from ")
									.append(ParticelleCatastali.getTableName())
									.append(" where ")
									.append(ParticelleCatastali.pratica_id.name())
									.append(" = :idPratica")
									.append(" UNION ")
									.append(" select distinct ")
									.append(ParchiPaesaggiImmobili.comune_id.name())
									.append(" from ")
									.append(ParchiPaesaggiImmobili.getTableName())
									.append(" where ")
									.append(ParchiPaesaggiImmobili.pratica_id.name())
									.append(" = :idPratica")
									.append(" and ")
									.append(ParchiPaesaggiImmobili.selezionato.name())
									.append(" ilike :selezionato")
									.toString();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idPratica", idPratica);
		parameters.put("selezionato", 'S');
		log.trace("Sql -> {} Parameters -> {}", sql, parameters);
		
		return namedJdbcTemplate.queryForList(sql.toString(), parameters, Long.class);
	}
	
	public LocalizzazioneInterventoDTO findByIdPraticaAndIdComune(Long praticaId,Long idComune) {
		final String sql = new StringBuilder(selectAll)
				.append(" where \"pratica_id\" = ?")
				.append(" and \"comune_id\" = ?")
				.toString();
		final List<Object> parameters = new ArrayList<Object>();
		parameters.add(praticaId);
		parameters.add(idComune);
		return super.queryForObject(sql, this.rowMapper, parameters);
	}
	
}