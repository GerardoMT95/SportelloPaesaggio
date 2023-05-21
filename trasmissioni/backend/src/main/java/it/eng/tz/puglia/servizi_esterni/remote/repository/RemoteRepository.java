package it.eng.tz.puglia.servizi_esterni.remote.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import it.eng.tz.puglia.servizi_esterni.remote.rowmapper.CommonRowMapper;

public abstract class RemoteRepository<T extends Serializable> 
{
	protected abstract CommonRowMapper<T> getRowMapper();
	protected abstract String getQueryForSelectAll();
	protected abstract String getQueryForFind(String codice);
	protected abstract String getQueryForFindAll(List<String> codices);

	/**
	 * find by pk method
	 */
	public List<T> selectAll() {
		try {
	        return getJdbcTemplate().query(getQueryForSelectAll(), getRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}

	/**
	 * find by pk method
	 */
	public Optional<T> find(String codice) {
		try {
			return Optional.of(getJdbcTemplate().queryForObject(getQueryForFind(codice), getRowMapper(), codice));
		} catch (EmptyResultDataAccessException empty) {
			return Optional.empty();
		}
	}

	/**
	 * findAll by pks method
	 */
	public List<T> findAll(List<String> codices) 
	{
		if (codices == null) {
			return Collections.emptyList();
		}
		codices = codices.stream().filter(Objects::nonNull).filter(code -> !code.isBlank())
				.collect(Collectors.toList());
		if (codices.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			return getJdbcTemplate().query(getQueryForFindAll(codices), getRowMapper());
		} catch (EmptyResultDataAccessException empty) {
			return Collections.emptyList();
		}
	}
	
	protected abstract JdbcTemplate getJdbcTemplate();
}
