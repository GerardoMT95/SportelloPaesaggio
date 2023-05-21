package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazionePermessi;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;

public class ConfigurazionePermessiRowMapper implements RowMapper<ConfigurazionePermessiDTO> {

	@Override
	public ConfigurazionePermessiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ConfigurazionePermessiDTO dto = new ConfigurazionePermessiDTO();
		
		if (rs.getObject(ConfigurazionePermessi.codice_ente.columnName()) != null) {
			dto.setCodiceEnte(rs.getString(ConfigurazionePermessi.codice_ente.columnName()));
		}
		if (rs.getObject(ConfigurazionePermessi.permesso_documentazione.columnName()) != null) {
			dto.setPermessoDocumentazione(rs.getBoolean(ConfigurazionePermessi.permesso_documentazione.columnName()));
		}
		if (rs.getObject(ConfigurazionePermessi.permesso_osservazione.columnName()) != null) {
			dto.setPermessoOsservazione(rs.getBoolean(ConfigurazionePermessi.permesso_osservazione.columnName()));
		}
		if (rs.getObject(ConfigurazionePermessi.permesso_comunicazione.columnName()) != null) {
			dto.setPermessoComunicazione(rs.getBoolean(ConfigurazionePermessi.permesso_comunicazione.columnName()));
		}
		if (rs.getObject(ConfigurazionePermessi.stato_registrazione_pubblico.columnName()) != null) {
			dto.setStatoRegistrazionePubblico(rs.getBoolean(ConfigurazionePermessi.stato_registrazione_pubblico.columnName()));
		}
		if (rs.getObject(ConfigurazionePermessi.esito_verifica_pubblico.columnName()) != null) {
			dto.setEsitoVerificaPubblico(rs.getBoolean(ConfigurazionePermessi.esito_verifica_pubblico.columnName()));
		}
		
		return dto;
	}

}
