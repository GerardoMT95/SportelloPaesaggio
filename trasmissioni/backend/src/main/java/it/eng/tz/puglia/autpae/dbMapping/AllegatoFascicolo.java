package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AllegatoFascicolo implements _Interfaccia_dbMapping
{
	id_allegato,
	type,
	id_fascicolo;
	
	@Override
	public String tableName()
	{
		return "allegato_fascicolo";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AllegatoFascicolo> ids()
	{
		List<AllegatoFascicolo> pk = new ArrayList<>();
		pk.add(AllegatoFascicolo.id_allegato);
		pk.add(AllegatoFascicolo.type);
		return pk;
	}
	
	public static String getTableName()
	{
		return "allegato_fascicolo";
	}
}
