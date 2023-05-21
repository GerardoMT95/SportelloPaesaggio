package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.List;

public enum PlaceholderDoc implements _Interfaccia_dbMapping
{
	codice,
	info;
	
	@Override
	public String tableName()
	{
		return "placeholder_doc";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PlaceholderDoc> ids()
	{
		return List.of(PlaceholderDoc.codice);
	}
	
	public static String getTableName()
	{
		return "placeholder_doc";
	}
}
