package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Richiedente;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;

public class RichiedenteRowMapper implements RowMapper<RichiedenteDTO> {

	@Override
	public RichiedenteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		RichiedenteDTO dto = new RichiedenteDTO();
		if (rs.getObject(Richiedente.id.columnName()) != null) {
			dto.setId(rs.getLong(Richiedente.id.columnName()));
		}
		if (rs.getObject(Richiedente.nome.columnName()) != null) {
			dto.setNome(rs.getString(Richiedente.nome.columnName()));
		}
		if (rs.getObject(Richiedente.cognome.columnName()) != null) {
			dto.setCognome(rs.getString(Richiedente.cognome.columnName()));
		}
		if (rs.getObject(Richiedente.codice_fiscale.columnName()) != null) {
			dto.setCodiceFiscale(rs.getString(Richiedente.codice_fiscale.columnName()));
		}
		if (rs.getObject(Richiedente.sesso.columnName()) != null) {
			dto.setSesso(rs.getString(Richiedente.sesso.columnName()));
		}
		if (rs.getObject(Richiedente.data_nascita.columnName()) != null) {
			dto.setDataNascita(rs.getDate(Richiedente.data_nascita.columnName()));
		}
		if (rs.getObject(Richiedente.stato_nascita.columnName()) != null) {
			dto.setStatoNascita(rs.getString(Richiedente.stato_nascita.columnName()));
		}
		if (rs.getObject(Richiedente.provincia_nascita.columnName()) != null) {
			dto.setProvinciaNascita(rs.getString(Richiedente.provincia_nascita.columnName()));
		}
		if (rs.getObject(Richiedente.comune_nascita.columnName()) != null) {
			dto.setComuneNascita(rs.getString(Richiedente.comune_nascita.columnName()));
		}
		if (rs.getObject(Richiedente.stato_residenza.columnName()) != null) {
			dto.setStatoResidenza(rs.getString(Richiedente.stato_residenza.columnName()));
		}
		if (rs.getObject(Richiedente.provincia_residenza.columnName()) != null) {
			dto.setProvinciaResidenza(rs.getString(Richiedente.provincia_residenza.columnName()));
		}
		if (rs.getObject(Richiedente.comune_residenza.columnName()) != null) {
			dto.setComuneResidenza(rs.getString(Richiedente.comune_residenza.columnName()));
		}
		if (rs.getObject(Richiedente.via_residenza.columnName()) != null) {
			dto.setViaResidenza(rs.getString(Richiedente.via_residenza.columnName()));
		}
		if (rs.getObject(Richiedente.numero_residenza.columnName()) != null) {
			dto.setNumeroResidenza(rs.getString(Richiedente.numero_residenza.columnName()));
		}
		if (rs.getObject(Richiedente.cap.columnName()) != null) {
			dto.setCap(rs.getString(Richiedente.cap.columnName()));
		}
		if (rs.getObject(Richiedente.pec.columnName()) != null) {
			dto.setPec(rs.getString(Richiedente.pec.columnName()));
		}
		if (rs.getObject(Richiedente.email.columnName()) != null) {
			dto.setEmail(rs.getString(Richiedente.email.columnName()));
		}
		if (rs.getObject(Richiedente.telefono.columnName()) != null) {
			dto.setTelefono(rs.getString(Richiedente.telefono.columnName()));
		}
		if (rs.getObject(Richiedente.ditta_societa.columnName()) != null) {
			dto.setDittaSocieta(rs.getString(Richiedente.ditta_societa.columnName()));
		}
		if (rs.getObject(Richiedente.ditta_in_qualita_di.columnName()) != null) {
			dto.setDittaInQualitaDi(rs.getString(Richiedente.ditta_in_qualita_di.columnName()));
		}
		if (rs.getObject(Richiedente.ditta_qualita_altro.columnName()) != null) {
			dto.setDittaQualitaAltro(rs.getString(Richiedente.ditta_qualita_altro.columnName()));
		}
		if (rs.getObject(Richiedente.ditta_codice_fiscale.columnName()) != null) {
			dto.setDittaCodiceFiscale(rs.getString(Richiedente.ditta_codice_fiscale.columnName()));
		}
		if (rs.getObject(Richiedente.ditta_partita_iva.columnName()) != null) {
			dto.setDittaPartitaIva(rs.getString(Richiedente.ditta_partita_iva.columnName()));
		}
		if (rs.getObject(Richiedente.id_fascicolo.columnName()) != null) {
			dto.setIdFascicolo(rs.getLong(Richiedente.id_fascicolo.columnName()));
		}
		return dto;
	}

}
