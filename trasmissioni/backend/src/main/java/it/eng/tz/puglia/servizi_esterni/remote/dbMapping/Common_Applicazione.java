package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Applicazione implements _Interfaccia_dbMapping
{
	id,
	nome,
	codice;
	
	
	@Override
	public String tableName()
	{
		return "common.applicazione";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Applicazione> ids()
	{
		return Collections.singletonList(Common_Applicazione.id);
	}
	
	public static String getTableName()
	{
		return "common.applicazione";
	}
	
	
}
