package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Paesaggio_Email implements _Interfaccia_dbMapping
{
	id,
	codice_applicazione,
	email,
	denominazione,
	pec,
	organizzazione_id,
	ente_id;
	
	
	@Override
	public String tableName()
	{
		return "common.paesaggio_email";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Paesaggio_Email> ids()
	{
		return Collections.singletonList(Common_Paesaggio_Email.id);
	}
	
	public static String getTableName()
	{
		return "common.paesaggio_email";
	}
}
