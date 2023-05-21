package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum TipoAllegato implements _Interfaccia_dbMapping
{
	type,
	descrizione;
	
	@Override
	public String tableName()
	{
		return "tipo_allegato";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TipoAllegato> ids()
	{
		return Collections.singletonList(TipoAllegato.type);
	}
	
	public static String getTableName()
	{
		return "tipo_allegato";
	}
}
