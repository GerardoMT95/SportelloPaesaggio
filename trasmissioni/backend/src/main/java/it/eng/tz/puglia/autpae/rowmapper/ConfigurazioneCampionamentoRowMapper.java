package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ConfigurazioneCampionamento;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;

public class ConfigurazioneCampionamentoRowMapper implements RowMapper<ConfigurazioneCampionamentoDTO> {

	@Override
	public ConfigurazioneCampionamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ConfigurazioneCampionamentoDTO dto = new ConfigurazioneCampionamentoDTO();
		
//		if (rs.getObject(ConfigurazioneCampionamento.id.columnName()) != null) {
//			dto.setId(rs.getLong(ConfigurazioneCampionamento.id.columnName()));
//		}
		if (rs.getObject(ConfigurazioneCampionamento.campionamento_attivo.columnName()) != null) {
			dto.setCampionamentoAttivo(rs.getBoolean(ConfigurazioneCampionamento.campionamento_attivo.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.intervallo_campionamento.columnName()) != null) {
			dto.setIntervalloCampionamento(rs.getShort(ConfigurazioneCampionamento.intervallo_campionamento.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName()) != null) {
			dto.setTipoCampionamentoSuccessivo(CampionamentoSuccessivo.valueOf(rs.getString(ConfigurazioneCampionamento.tipo_campionamento_successivo.columnName())));
		}
		if (rs.getObject(ConfigurazioneCampionamento.percentuale_istanze.columnName()) != null) {
			dto.setPercentualeIstanze(rs.getShort(ConfigurazioneCampionamento.percentuale_istanze.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName()) != null) {
			dto.setGiorniNotificaCampionamento(rs.getString(ConfigurazioneCampionamento.giorni_notifica_campionamento.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.giorni_notifica_verifica.columnName()) != null) {
			dto.setGiorniNotificaVerifica(rs.getString(ConfigurazioneCampionamento.giorni_notifica_verifica.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.intervallo_verifica.columnName()) != null) {
			dto.setIntervalloVerifica(rs.getShort(ConfigurazioneCampionamento.intervallo_verifica.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.esito_pubblico.columnName()) != null) {
			dto.setEsitoPubblico(rs.getBoolean(ConfigurazioneCampionamento.esito_pubblico.columnName()));
		}
		if (rs.getObject(ConfigurazioneCampionamento.applica_in_corso.columnName()) != null) {
			dto.setApplicaInCorso(rs.getBoolean(ConfigurazioneCampionamento.applica_in_corso.columnName()));
		}
		
		return dto;
	}

}
