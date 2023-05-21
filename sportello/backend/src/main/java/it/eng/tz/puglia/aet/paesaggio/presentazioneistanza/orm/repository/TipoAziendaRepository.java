package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoAziendaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper.TipoAziendaRowMapper;

/**
 * Repository for table presentazione_istanza.tipo_azienda
 */
@Repository
public class TipoAziendaRepository extends GenericDao {

	private final static String SQL_GET_NOME = "select nome from presentazione_istanza.tipo_azienda where id = :id";
	private final static String SQL_GET_PRIVATO = "select privato from presentazione_istanza.tipo_azienda where id = :id";

	private final static String SQL_LIST = "select id,nome,privato from presentazione_istanza.tipo_azienda order by nome";
	private final static String SQL_FIND_BY_PRIVATO = "select id,nome,privato from presentazione_istanza.tipo_azienda where privato = :privato";
	private final static RowMapper<TipoAziendaDTO> ROW_MAPPER = new TipoAziendaRowMapper();
	
	
	
	public String getNome(final String id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return super.namedJdbcTemplateWrite.queryForObject(SQL_GET_NOME, parameters, String.class);
	}

	public Boolean getPrivato(final String id) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return super.namedJdbcTemplateWrite.queryForObject(SQL_GET_PRIVATO, parameters, Boolean.class);
	}

	public List<TipoAziendaDTO> getList() {
		final Map<String, Object> parameters = new HashMap<>();
		return super.namedJdbcTemplateWrite.query(SQL_LIST, parameters, ROW_MAPPER);
	}

	public TipoAziendaDTO privato() {
		return this.getByPrivato(true);
	}

	public TipoAziendaDTO pubblico() {
		return this.getByPrivato(false);
	}

	private TipoAziendaDTO getByPrivato(final boolean privato) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("privato", privato);
		return super.namedJdbcTemplateWrite.queryForObject(SQL_FIND_BY_PRIVATO, parameters, ROW_MAPPER);
	}
	

}
