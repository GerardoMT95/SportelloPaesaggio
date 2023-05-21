package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.tz.puglia.autpae.dbMapping.Fascicolo;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

public class FascicoloRowMapper implements RowMapper<FascicoloDTO>
{

	@Override
	public FascicoloDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		FascicoloDTO dto = new FascicoloDTO();
		if(rs != null)
		{
			dto.setId(rs.getObject(Fascicolo.id.columnName()) != null ? rs.getLong(Fascicolo.id.columnName()) : null);
			dto.setCodice(rs.getObject(Fascicolo.codice.columnName()) != null ? rs.getString(Fascicolo.codice.columnName()) : null);
			dto.setCodiceInternoEnte(rs.getObject(Fascicolo.codice_interno_ente.columnName()) != null ? rs.getString(Fascicolo.codice_interno_ente.columnName()) : null);
			dto.setDataCampionamento(rs.getObject(Fascicolo.data_campionamento.columnName()) != null ? Date.from((rs.getObject(Fascicolo.data_campionamento.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
			dto.setDataCreazione(rs.getObject(Fascicolo.data_creazione.columnName()) != null ? Date.from((rs.getObject(Fascicolo.data_creazione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
			dto.setDataProtocollo(rs.getObject(Fascicolo.data_protocollo.columnName()) != null ? rs.getDate(Fascicolo.data_protocollo.columnName()) : null);
			dto.setDataRilascioAutorizzazione(rs.getObject(Fascicolo.data_rilascio_autorizzazione.columnName()) != null ? rs.getDate(Fascicolo.data_rilascio_autorizzazione.columnName()) : null);
			dto.setDataTrasmissione(rs.getObject(Fascicolo.data_trasmissione.columnName()) != null ? Date.from((rs.getObject(Fascicolo.data_trasmissione.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
			dto.setDataUltimaModifica(rs.getObject(Fascicolo.data_ultima_modifica.columnName()) != null ? Date.from((rs.getObject(Fascicolo.data_ultima_modifica.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
			dto.setDataVerifica(rs.getObject(Fascicolo.data_verifica.columnName()) != null ? Date.from((rs.getObject(Fascicolo.data_verifica.columnName(), LocalDateTime.class)).atZone(ZoneId.systemDefault()).toInstant()) : null);
			dto.setEsito(rs.getObject(Fascicolo.esito.columnName()) != null ? EsitoProvvedimento.valueOf(rs.getString(Fascicolo.esito.columnName())) : null);
			dto.setNote(rs.getObject(Fascicolo.note.columnName()) != null ? rs.getString(Fascicolo.note.columnName()) : null);
			dto.setNumeroInternoEnte(rs.getObject(Fascicolo.numero_interno_ente.columnName()) != null ? rs.getString(Fascicolo.numero_interno_ente.columnName()) : null);
			dto.setNumeroProvvedimento(rs.getObject(Fascicolo.numero_provvedimento.columnName()) != null ? rs.getString(Fascicolo.numero_provvedimento.columnName()) : null);
			dto.setOggettoIntervento(rs.getObject(Fascicolo.oggetto_intervento.columnName()) != null ? rs.getString(Fascicolo.oggetto_intervento.columnName()) : null);
			dto.setProtocollo(rs.getObject(Fascicolo.protocollo.columnName()) != null ? rs.getString(Fascicolo.protocollo.columnName()) : null);
			dto.setInterventoSpecifica(rs.getObject(Fascicolo.intervento_specifica.columnName()) != null ? rs.getString(Fascicolo.intervento_specifica.columnName()) : null);
			dto.setInterventoDettaglio(rs.getObject(Fascicolo.intervento_dettaglio.columnName()) != null ? rs.getString(Fascicolo.intervento_dettaglio.columnName()) : null);
			dto.setRup(rs.getObject(Fascicolo.rup.columnName()) != null ? rs.getString(Fascicolo.rup.columnName()) : null);
			dto.setEsitoDataRilascioAutorizzazione(rs.getObject(Fascicolo.esito_data_rilascio_autorizzazione.columnName()) != null ? rs.getDate(Fascicolo.esito_data_rilascio_autorizzazione.columnName()) : null);
			dto.setEsitoNumeroProvvedimento(rs.getObject(Fascicolo.esito_numero_provvedimento.columnName()) != null ? rs.getString(Fascicolo.esito_numero_provvedimento.columnName()) : null);
			dto.setSanatoria(rs.getObject(Fascicolo.sanatoria.columnName()) != null ? rs.getBoolean(Fascicolo.sanatoria.columnName()) : null);
			dto.setStato(rs.getObject(Fascicolo.stato.columnName()) != null ? StatoFascicolo.valueOf(rs.getString(Fascicolo.stato.columnName())) : null);
			dto.setStatoPrecedente(rs.getObject(Fascicolo.stato_precedente.columnName()) != null ? StatoFascicolo.valueOf(rs.getString(Fascicolo.stato_precedente.columnName())) : null);
			dto.setTipoProcedimento(rs.getObject(Fascicolo.tipo_procedimento.columnName()) != null ? TipoProcedimento.valueOf(rs.getString(Fascicolo.tipo_procedimento.columnName())) : null);
			dto.setUfficio(rs.getObject(Fascicolo.ufficio.columnName()) != null ? rs.getString(Fascicolo.ufficio.columnName()) : null);
			dto.setOrgCreazione(rs.getObject(Fascicolo.org_creazione.columnName()) != null ? rs.getInt(Fascicolo.org_creazione.columnName()) : null);
			dto.setUsernameUtenteCreazione(rs.getObject(Fascicolo.username_utente_creazione.columnName()) != null ? rs.getString(Fascicolo.username_utente_creazione.columnName()) : null);
			dto.setUsernameUtenteUltimaModifica(rs.getObject(Fascicolo.username_utente_ultima_modifica.columnName()) != null ? rs.getString(Fascicolo.username_utente_ultima_modifica.columnName()) : null);
			dto.setUsernameUtenteTrasmissione(rs.getObject(Fascicolo.username_utente_trasmissione.columnName()) != null ? rs.getString(Fascicolo.username_utente_trasmissione.columnName()) : null);
			dto.setDenominazioneUtenteTrasmissione(rs.getObject(Fascicolo.denominazione_utente_trasmissione.columnName()) != null ? rs.getString(Fascicolo.denominazione_utente_trasmissione.columnName()) : null);
			dto.setEmailUtenteTrasmissione(rs.getObject(Fascicolo.email_utente_trasmissione.columnName()) != null ? rs.getString(Fascicolo.email_utente_trasmissione.columnName()) : null);
			dto.setEsitoVerifica(rs.getObject(Fascicolo.esito_verifica.columnName()) != null ? EsitoVerifica.valueOf(rs.getString(Fascicolo.esito_verifica.columnName())) : null);
			dto.setEsitoVerificaPrecedente(rs.getObject(Fascicolo.esito_verifica_precedente.columnName()) != null ? EsitoVerifica.valueOf(rs.getString(Fascicolo.esito_verifica_precedente.columnName())) : null);
			dto.setStatoRegistrazione(FascicoloDTO.calcolaStatoRegistrazione(StatoFascicolo.valueOf(rs.getString(Fascicolo.stato.columnName()))));
			dto.setDataDelibera(rs.getObject(Fascicolo.data_delibera.columnName()) != null ? rs.getDate(Fascicolo.data_delibera.columnName()) : null);
			dto.setDeliberaNum(rs.getObject(Fascicolo.delibera_num.columnName()) != null ? rs.getString(Fascicolo.delibera_num.columnName()) : null);
			dto.setOggettoDelibera(rs.getObject(Fascicolo.oggetto_delibera.columnName()) != null ? rs.getString(Fascicolo.oggetto_delibera.columnName()) : null);
			dto.setInfoDeliberePrecedenti(rs.getObject(Fascicolo.info_delibere_precedenti.columnName()) != null ? rs.getString(Fascicolo.info_delibere_precedenti.columnName()) : null);
			dto.setDescrizioneIntervento(rs.getObject(Fascicolo.descrizione_intervento.columnName()) != null ? rs.getString(Fascicolo.descrizione_intervento.columnName()) : null);
			dto.setVersFascicolo(rs.getObject(Fascicolo.vers_fascicolo.columnName()) != null ? rs.getInt(Fascicolo.vers_fascicolo.columnName()) : null);
			dto.setVersRichiedente(rs.getObject(Fascicolo.vers_richiedente.columnName()) != null ? rs.getInt(Fascicolo.vers_richiedente.columnName()) : null);
			dto.setVersIntervento(rs.getObject(Fascicolo.vers_intervento.columnName()) != null ? rs.getInt(Fascicolo.vers_intervento.columnName()) : null);
			dto.setVersLocalizzazione(rs.getObject(Fascicolo.vers_localizzazione.columnName()) != null ? rs.getInt(Fascicolo.vers_localizzazione.columnName()) : null);
			dto.setVersAllegati(rs.getObject(Fascicolo.vers_allegati.columnName()) != null ? rs.getInt(Fascicolo.vers_allegati.columnName()) : null);
			dto.setVersProvvedimento(rs.getObject(Fascicolo.vers_provvedimento.columnName()) != null ? rs.getInt(Fascicolo.vers_provvedimento.columnName()) : null);
			dto.setDeleted(rs.getBoolean(Fascicolo.deleted.columnName()));
			dto.setModificabileFino(rs.getObject(Fascicolo.modificabile_fino.columnName()) != null ? rs.getDate(Fascicolo.modificabile_fino.columnName()) : null);
			dto.setHasShape(rs.getBoolean(Fascicolo.has_shape.columnName()));
			dto.setTipoSelezioneLocalizzazione(rs.getObject(Fascicolo.tipo_selezione_localizzazione.columnName()) != null ? 
					TipoLocalizzazione.valueOf(rs.getString(Fascicolo.tipo_selezione_localizzazione.columnName())) : null);
            // --> JSON
            Object elem = rs.getObject(Fascicolo.info_complete.columnName());
            if(elem != null)
            {
            	 String jsonValue = String.valueOf(elem);
            	 jsonValue = jsonValue.substring(17);						// elimino la stringa iniziale: '{"informazioni": '
            	 jsonValue = jsonValue.substring(0, jsonValue.length()-1);	// elimino l'ultima graffa '}'
            	 Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
            	 InformazioniDTO informazioni = gson.fromJson(jsonValue, InformazioniDTO.class);
                 dto.setInfoComplete(informazioni);
            }
            dto.setCodicePraticaAppptr(rs.getObject(Fascicolo.codice_pratica_appptr.columnName()) != null ? rs.getString(Fascicolo.codice_pratica_appptr.columnName()) : null);
            dto.settPraticaId(rs.getObject(Fascicolo.t_pratica_id.columnName()) != null ? rs.getLong(Fascicolo.t_pratica_id.columnName()) : null);
            dto.setStatoTrasmissione(rs.getBoolean(Fascicolo.stato_trasmissione.columnName()));
        }
		return dto;
	}

}
