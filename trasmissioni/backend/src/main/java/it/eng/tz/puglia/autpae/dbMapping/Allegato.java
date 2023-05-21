package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Allegato implements _Interfaccia_dbMapping
{
	id,
	nome,
	titolo,
	descrizione,
	mime_type,
	data_caricamento,
	contenuto,
	checksum,
	deleted,
	dimensione,
	numero_protocollo_in,
	numero_protocollo_out,
	data_protocollo_in,
	data_protocollo_out,
	utente_inserimento,
	username_inserimento,
	id_richiesta_post_trasmissione,
	id_annotazione_interna,
	path_cms,
	id_diogene,
	last_send_diogene,
	path_diogene,
	path_reale_diogene,
	last_update_diogene,
	deletable
	;
	
	@Override
	public String tableName()
	{
		return "allegato";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Allegato> ids()
	{
		return Collections.singletonList(Allegato.id);
	}
	
	public static String getTableName()
	{
		return "allegato";
	}
}
