package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;

public class FascicoloPublicRowMapper implements RowMapper<FascicoloPublicDto>{

	@Override
	public FascicoloPublicDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		FascicoloPublicDto dto= new FascicoloPublicDto();
		if(rs != null)
		{
			dto.setId(rs.getObject(Fascicolo.id.columnName()) != null ? rs.getLong(Fascicolo.id.columnName()) : null);
			dto.setCodice(rs.getObject(Fascicolo.codice.columnName()) != null ? rs.getString(Fascicolo.codice.columnName()) : null);
			dto.setStatoRegistrazione(FascicoloPublicDto.calcolaStatoRegistrazione(StatoFascicolo.valueOf(rs.getString(Fascicolo.stato.columnName()))));
			dto.setCodiceInternoEnte(rs.getObject(Fascicolo.codice_interno_ente.columnName()) != null ? rs.getString(Fascicolo.codice_interno_ente.columnName()) : null);
			dto.setDataProtocollo(rs.getObject(Fascicolo.data_protocollo.columnName()) != null ? rs.getDate(Fascicolo.data_protocollo.columnName()) : null);
			dto.setDataRilascioAutorizzazione(rs.getObject(Fascicolo.data_rilascio_autorizzazione.columnName()) != null ? rs.getDate(Fascicolo.data_rilascio_autorizzazione.columnName()) : null);
			dto.setEsito(rs.getObject(Fascicolo.esito.columnName()) != null ? EsitoProvvedimento.valueOf(rs.getString(Fascicolo.esito.columnName())) : null);
			dto.setNumeroInternoEnte(rs.getObject(Fascicolo.numero_interno_ente.columnName()) != null ? rs.getString(Fascicolo.numero_interno_ente.columnName()) : null);
			dto.setNumeroProvvedimento(rs.getObject(Fascicolo.numero_provvedimento.columnName()) != null ? rs.getString(Fascicolo.numero_provvedimento.columnName()) : null);
			dto.setOggettoIntervento(rs.getObject(Fascicolo.oggetto_intervento.columnName()) != null ? rs.getString(Fascicolo.oggetto_intervento.columnName()) : null);
			dto.setProtocollo(rs.getObject(Fascicolo.protocollo.columnName()) != null ? rs.getString(Fascicolo.protocollo.columnName()) : null);
			dto.setRup(rs.getObject(Fascicolo.rup.columnName()) != null ? rs.getString(Fascicolo.rup.columnName()) : null);
			dto.setStato(rs.getObject(Fascicolo.stato.columnName()) != null ? StatoFascicolo.valueOf(rs.getString(Fascicolo.stato.columnName())) : null);
			dto.setTipoProcedimento(rs.getObject(Fascicolo.tipo_procedimento.columnName()) != null ? 
					it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento.valueOf(rs.getString(Fascicolo.tipo_procedimento.columnName())) : null);
			dto.setUfficio(rs.getObject(Fascicolo.ufficio.columnName()) != null ? rs.getString(Fascicolo.ufficio.columnName()) : null);
			dto.setOrgCreazione(rs.getObject(Fascicolo.org_creazione.columnName()) != null ? rs.getInt(Fascicolo.org_creazione.columnName()) : null);
			dto.setEsitoVerifica(rs.getObject(Fascicolo.esito_verifica.columnName()) != null ? EsitoVerifica.valueOf(rs.getString(Fascicolo.esito_verifica.columnName())) : null);
			dto.setApplicazione(rs.getObject("applicazione") != null ? rs.getString("applicazione") : null);
		}
	return dto;
}
}
