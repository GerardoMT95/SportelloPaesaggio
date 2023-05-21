package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Ricevuta implements _Interfaccia_dbMapping
{
	id,
	id_corrispondenza,
	id_destinatario,
	tipo_ricevuta,
	errore,
	descrizione_errore,
	id_cms_eml,
	id_cms_dati_cert,
	id_cms_smime,
	data,
	id_allegato_dati_cert;
	
	@Override
	public String tableName()
	{
		return "ricevuta";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Ricevuta> ids()
	{
		return Collections.singletonList(Ricevuta.id);
	}
	
	public static String getTableName()
	{
		return "ricevuta";
	}
}
