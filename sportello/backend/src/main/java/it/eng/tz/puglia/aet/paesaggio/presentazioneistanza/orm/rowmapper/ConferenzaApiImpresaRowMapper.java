package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.cds.bean.ConferenzaApiImpresaDto;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaFormaGiuridicaEnum;

public class ConferenzaApiImpresaRowMapper implements RowMapper<ConferenzaApiImpresaDto>{

	@Override
	public ConferenzaApiImpresaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ConferenzaApiImpresaDto result = new ConferenzaApiImpresaDto();
        result.setCodiceFiscale(rs.getString("codice_fiscale"));
        result.setDenominazione(rs.getString("denominazione"));
        result.setIndirizzo(rs.getString("indirizzo"));
        result.setPartitaIva(rs.getString("partita_iva"));
        result.setComune(rs.getString("comune"));
        result.setProvincia(rs.getString("provincia"));
        result.setFormaGiuridica(ConferenzaFormaGiuridicaEnum.fromCodice(rs.getString("forma_giuridica")));
        return result;
	}

}
