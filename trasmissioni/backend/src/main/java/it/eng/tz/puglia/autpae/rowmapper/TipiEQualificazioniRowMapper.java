package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.TipiEQualificazioni;
import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;

public class TipiEQualificazioniRowMapper implements RowMapper<TipiEQualificazioniDTO> {

	@Override
	public TipiEQualificazioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipiEQualificazioniDTO dto = new TipiEQualificazioniDTO();
		if (rs.getObject(TipiEQualificazioni.id.columnName()) != null) {
			dto.setId(rs.getLong(TipiEQualificazioni.id.columnName()));
		}
		if (rs.getObject(TipiEQualificazioni.zona.columnName()) != null) {
			dto.setZona(rs.getInt(TipiEQualificazioni.zona.columnName()));
		}
		if (rs.getObject(TipiEQualificazioni.label.columnName()) != null) {
			dto.setLabel(rs.getString(TipiEQualificazioni.label.columnName()));
		}
		if (rs.getObject(TipiEQualificazioni.ordine.columnName()) != null) {
			dto.setOrdine(rs.getInt(TipiEQualificazioni.ordine.columnName()));
		}
		if (rs.getObject(TipiEQualificazioni.stile.columnName()) != null) {
			dto.setStile(rs.getString(TipiEQualificazioni.stile.columnName()));
		}
		if (rs.getObject(TipiEQualificazioni.raggruppamento.columnName()) != null) {
			dto.setRaggruppamento(rs.getString(TipiEQualificazioni.raggruppamento.columnName()));
		}
		return dto;
	}

}
