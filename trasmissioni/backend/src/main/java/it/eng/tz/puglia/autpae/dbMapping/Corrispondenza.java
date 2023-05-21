package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Corrispondenza implements _Interfaccia_dbMapping
{
	id,
	message_id,
	id_eml_cmis,
	mittente_email,
	mittente_denominazione,
	mittente_ente,
	mittente_username,
	oggetto,
	text,
	data_invio,
	stato,
	comunicazione,
	bozza;
	
	@Override
	public String tableName()
	{
		return "corrispondenza";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Corrispondenza> ids()
	{
		return Collections.singletonList(Corrispondenza.id);
	}
	
	public static String getTableName()
	{
		return "corrispondenza";
	}
}
