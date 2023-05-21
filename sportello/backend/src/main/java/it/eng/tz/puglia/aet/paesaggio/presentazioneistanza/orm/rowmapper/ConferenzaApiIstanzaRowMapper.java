package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.cds.bean.ConferenzaApiIstanzaDto;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaAttivitaEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaAzioneEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaTipologiaPraticaEnum;

public class ConferenzaApiIstanzaRowMapper implements RowMapper<ConferenzaApiIstanzaDto>{

	@Override
	public ConferenzaApiIstanzaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ConferenzaApiIstanzaDto result = new ConferenzaApiIstanzaDto();
        result.setCodice(rs.getString("codice"));
        result.setOggetto(rs.getString("oggetto"));
        result.setDataCreazione(rs.getDate("data_creazione"));
        return result;
	}

}
