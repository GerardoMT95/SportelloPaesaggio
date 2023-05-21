package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Paesaggio_Organizzazione_Attributi implements _Interfaccia_dbMapping
{
	id,
	applicazione_id,
	paesaggio_organizzazione_id,
	data_creazione,
	data_termine;
	
	
	@Override
	public String tableName()
	{
		return "common.paesaggio_organizzazione_attributi";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Paesaggio_Organizzazione_Attributi> ids()
	{
		return Collections.singletonList(Common_Paesaggio_Organizzazione_Attributi.id);
	}
	
	public static String getTableName()
	{
		return "common.paesaggio_organizzazione_attributi";
	}
	
}
