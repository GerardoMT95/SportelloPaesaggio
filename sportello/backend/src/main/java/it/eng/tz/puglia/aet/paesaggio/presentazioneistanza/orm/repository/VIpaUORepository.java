package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.IpaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper.IpaEnteRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

@Repository
public class VIpaUORepository extends GenericDao
{
    private final static Logger LOGGER = LoggerFactory.getLogger(VIpaUORepository.class);
    
    public List<IpaEnteDTO> autocompleteIpaEnte(final String query) throws Exception
    {
	final String sql = new StringBuilder("select descrizione, codice_uo, codice_ipa, codice_fiscale ")
		.append("from opendata.v_ipa_uo ").append("where descrizione ilike :query ").append("limit 25")
		.toString();
	final MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("query", StringUtil.convertFullLike(query));
	LOGGER.info("Sql -> {} Parameters -> {}", sql, parameters);
	return namedJdbcTemplateRead.query(sql, parameters, new IpaEnteRowMapper());
    }
}
