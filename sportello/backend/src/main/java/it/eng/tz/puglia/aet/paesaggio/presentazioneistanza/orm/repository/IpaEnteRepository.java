package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.IpaEnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.IpaEnteRowMapper;

/**
 * Dao ipa ente
 * @author Antonio La Gatta
 * @date 9 giu 2022
 */
@Repository
public class IpaEnteRepository extends GenericDao{
	
	private final static String SQL_LIST = "select id,denominazione_ente,codice_ipa,codice_fiscale,codice_ateco from opendata.ipa_enti where codice_fiscale = :cf";

	
	private static final RowMapper<IpaEnteDto> ROW_MAPPER = new IpaEnteRowMapper();
	
	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @param id
	 * @return lista ente
	 */
	public List<IpaEnteDto> getList(final String codiceFiscale) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("cf", codiceFiscale);
		return super.namedJdbcTemplateWrite.query(SQL_LIST, parameters, ROW_MAPPER);
	}
}
