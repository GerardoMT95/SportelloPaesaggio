package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.ProcedimentoQualificazioni;
import it.eng.tz.puglia.autpae.entity.ProcedimentoQualificazioniDTO;

public class ProcedimentoQualificazioniRowMapper implements RowMapper<ProcedimentoQualificazioniDTO> {

	@Override
	public ProcedimentoQualificazioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProcedimentoQualificazioniDTO dto = new ProcedimentoQualificazioniDTO();
		if (rs.getObject(ProcedimentoQualificazioni.codice_tipo_procedimento.columnName()) != null) {
			dto.setCodiceTipoProcedimento(rs.getString(ProcedimentoQualificazioni.codice_tipo_procedimento.columnName()));
		}
		if (rs.getObject(ProcedimentoQualificazioni.id_tipi_qualificazioni.columnName()) != null) {
			dto.setIdTipiQualificazioni(rs.getLong(ProcedimentoQualificazioni.id_tipi_qualificazioni.columnName()));
		}
		if (rs.getObject(ProcedimentoQualificazioni.date_start.columnName()) != null) {
			dto.setDateStart(rs.getTimestamp(ProcedimentoQualificazioni.date_start.columnName()));
		}
		if (rs.getObject(ProcedimentoQualificazioni.date_end.columnName()) != null) {
			dto.setDateEnd(rs.getTimestamp(ProcedimentoQualificazioni.date_end.columnName()));
		}
		if (rs.getObject(ProcedimentoQualificazioni.escluso_eti.columnName()) != null) {
			dto.setEsclusoEti(rs.getBoolean(ProcedimentoQualificazioni.escluso_eti.columnName()));
		}
		return dto;
	}

}
