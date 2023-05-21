package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Campionamento;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.CampionamentoSuccessivo;

/**
 * Row Mapper for table autpae.autor_paesagg
 */
public class CampionamentoRowMapper implements RowMapper<CampionamentoDTO> {

	public CampionamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CampionamentoDTO result = new CampionamentoDTO();
		
		result.setId(rs.getObject(Campionamento.id.columnName()) != null ? rs.getLong(Campionamento.id.columnName()) : null);
		result.setAttivo(rs.getObject(Campionamento.attivo.columnName()) != null ? rs.getBoolean(Campionamento.attivo.columnName()) : null);
		result.setIntervalloCamp(rs.getObject(Campionamento.intervallo_camp.columnName()) != null ? rs.getShort(Campionamento.intervallo_camp.columnName()) : null);
		result.setTipoSuccessivo(rs.getObject(Campionamento.tipo_successivo.columnName()) != null ? CampionamentoSuccessivo.valueOf(rs.getString(Campionamento.tipo_successivo.columnName())) : null);
		result.setPercentuale(rs.getObject(Campionamento.percentuale.columnName()) != null ? rs.getShort(Campionamento.percentuale.columnName()) : null);
		result.setNotificaCamp(rs.getObject(Campionamento.notifica_camp.columnName()) != null ? rs.getString(Campionamento.notifica_camp.columnName()) : null);
		result.setNotificaVer(rs.getObject(Campionamento.notifica_ver.columnName()) != null ? rs.getString(Campionamento.notifica_ver.columnName()) : null);
		result.setIntervalloVer(rs.getObject(Campionamento.intervallo_ver.columnName()) != null ? rs.getShort(Campionamento.intervallo_ver.columnName()) : null);
		result.setEsitoPubb(rs.getObject(Campionamento.esito_pubb.columnName()) != null ? rs.getBoolean(Campionamento.esito_pubb.columnName()) : null);
		result.setCustomized(rs.getObject(Campionamento.customized.columnName()) != null ? rs.getBoolean(Campionamento.customized.columnName()) : null);
		result.setDataInizio(rs.getObject(Campionamento.data_inizio.columnName()) != null ? rs.getDate(Campionamento.data_inizio.columnName()) : null);
		result.setDataCampionatura(rs.getObject(Campionamento.data_campionatura.columnName()) != null ? rs.getDate(Campionamento.data_campionatura.columnName()) : null);
				
		return result;
	}
   
}
