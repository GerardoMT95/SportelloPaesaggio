package it.eng.tz.puglia.servizi_esterni.remote.rowmapper;

import java.io.Serializable;

import org.springframework.jdbc.core.RowMapper;

public interface CommonRowMapper<T extends Serializable> extends RowMapper<T> {
}
