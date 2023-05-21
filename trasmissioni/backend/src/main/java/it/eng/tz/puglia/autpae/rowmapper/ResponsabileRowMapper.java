package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Responsabile;
import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDocRiconoscimento;

public class ResponsabileRowMapper implements RowMapper<ResponsabileDTO> {

	@Override
	public ResponsabileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResponsabileDTO dto = new ResponsabileDTO();
		
		if (rs.getObject(Responsabile.id.columnName()) != null)	dto.setId(rs.getLong(Responsabile.id.columnName()));
		if (rs.getObject(Responsabile.nome.columnName()) != null)	dto.setNome(rs.getString(Responsabile.nome.columnName()));
		if (rs.getObject(Responsabile.cognome.columnName()) != null)	dto.setCognome(rs.getString(Responsabile.cognome.columnName()));
		if (rs.getObject(Responsabile.in_qualita_di.columnName()) != null)	dto.setInQualitaDi(rs.getString(Responsabile.in_qualita_di.columnName()));
		if (rs.getObject(Responsabile.servizio_settore_ufficio.columnName()) != null)	dto.setServizioSettoreUfficio(rs.getString(Responsabile.servizio_settore_ufficio.columnName()));
		if (rs.getObject(Responsabile.pec.columnName()) != null)	dto.setPec(rs.getString(Responsabile.pec.columnName()));
		if (rs.getObject(Responsabile.mail.columnName()) != null)	dto.setMail(rs.getString(Responsabile.mail.columnName()));
		if (rs.getObject(Responsabile.telefono.columnName()) != null)	dto.setTelefono(rs.getString(Responsabile.telefono.columnName()));
		if (rs.getObject(Responsabile.riconoscimento_tipo.columnName()) != null)	dto.setRiconoscimentoTipo(TipoDocRiconoscimento.valueOf(rs.getString(Responsabile.riconoscimento_tipo.columnName())));
		if (rs.getObject(Responsabile.riconoscimento_numero.columnName()) != null)	dto.setRiconoscimentoNumero(rs.getString(Responsabile.riconoscimento_numero.columnName()));
		if (rs.getObject(Responsabile.riconoscimento_data_rilascio.columnName()) != null)	dto.setRiconoscimentoDataRilascio(rs.getDate(Responsabile.riconoscimento_data_rilascio.columnName()));
		if (rs.getObject(Responsabile.riconoscimento_data_scadenza.columnName()) != null)	dto.setRiconoscimentoDataScadenza(rs.getDate(Responsabile.riconoscimento_data_scadenza.columnName()));
		if (rs.getObject(Responsabile.riconoscimento_rilasciato_da.columnName()) != null)	dto.setRiconoscimentoRilasciatoDa(rs.getString(Responsabile.riconoscimento_rilasciato_da.columnName()));
		if (rs.getObject(Responsabile.id_fascicolo.columnName()) != null)	dto.setIdFascicolo(rs.getLong(Responsabile.id_fascicolo.columnName()));
	
		return dto;
	}

}
