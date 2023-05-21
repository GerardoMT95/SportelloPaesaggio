package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AllegatoEnte implements _Interfaccia_dbMapping
{
	id_allegato,
	codice,
	descrizione;
	
	@Override
	public String tableName()
	{
		return "allegato_ente";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AllegatoEnte> ids()
	{
		List<AllegatoEnte> pk = new ArrayList<AllegatoEnte>();
		pk.add(AllegatoEnte.id_allegato);
		pk.add(AllegatoEnte.codice);
		return pk;
	}
	
	public static String getTableName()
	{
		return "allegato_ente";
	}
}
