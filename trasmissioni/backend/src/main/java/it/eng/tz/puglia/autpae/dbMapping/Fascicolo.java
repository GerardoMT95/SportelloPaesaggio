package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Fascicolo implements _Interfaccia_dbMapping
{
	id,
	ufficio,
	org_creazione,
	tipo_procedimento,
	oggetto_intervento,
	sanatoria,
	codice_interno_ente,
	numero_interno_ente,
	protocollo,
	data_protocollo,
	note,
	stato,							// stato pratica (o stato fascicolo)
	stato_precedente,
	codice,
	numero_provvedimento,
	data_rilascio_autorizzazione,
	esito,							// esito procedimento (o esito autorizzazione)
	rup,
	esito_data_rilascio_autorizzazione,
	esito_numero_provvedimento,
	intervento_dettaglio,
	intervento_specifica,
	data_creazione,
	data_trasmissione,
	data_campionamento,
	data_verifica,
	data_ultima_modifica,
	username_utente_creazione,
	username_utente_ultima_modifica,
	username_utente_trasmissione,
	denominazione_utente_trasmissione,
	email_utente_trasmissione,
	esito_verifica,
	esito_verifica_precedente,
//	stato_registrazione,
	data_delibera,
	delibera_num,
	oggetto_delibera,
	info_delibere_precedenti,
	descrizione_intervento,
	vers_fascicolo,
	vers_richiedente,
	vers_intervento,
	vers_localizzazione,
	vers_allegati,
	vers_provvedimento,
	info_complete,
	deleted,
	has_shape,
	tipo_selezione_localizzazione,
	modificabile_fino,
	codice_pratica_appptr,
	t_pratica_id,
	stato_trasmissione;
	
	
	@Override
	public String tableName()
	{
		return "fascicolo";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Fascicolo> ids()
	{
		return Collections.singletonList(Fascicolo.id);
	}
	
	public static String getTableName()
	{
		return "fascicolo";
	}
}
