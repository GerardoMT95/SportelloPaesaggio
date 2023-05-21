package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AllegatoCorrispondenza implements _Interfaccia_dbMapping
{
	id_allegato,
	id_corrispondenza,
	is_url;
	
	@Override
	public String tableName()
	{
		return "allegato_corrispondenza";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AllegatoCorrispondenza> ids()
	{
		List<AllegatoCorrispondenza> pk = new ArrayList<AllegatoCorrispondenza>();
		pk.add(AllegatoCorrispondenza.id_allegato);
		pk.add(AllegatoCorrispondenza.id_corrispondenza);
		return pk;
	}
	
	public static String getTableName()
	{
		return "allegato_corrispondenza";
	}
}
