package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.io.Serializable;

import org.springframework.jdbc.core.RowMapper;

public interface RemoteRowMapper<T extends Serializable> extends RowMapper<T> { }
