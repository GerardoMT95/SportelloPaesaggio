package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum FascicoloCorrispondenza implements _Interfaccia_dbMapping
{
	id,
	id_corrispondenza,
	id_fascicolo;
	
	@Override
	public String tableName()
	{
		return "fascicolo_corrispondenza";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<FascicoloCorrispondenza> ids()
	{
		List<FascicoloCorrispondenza> pk = new ArrayList<FascicoloCorrispondenza>();
		pk.add(FascicoloCorrispondenza.id_fascicolo);
		pk.add(FascicoloCorrispondenza.id_corrispondenza);
		return pk;
	}
	
	public static String getTableName()
	{
		return "fascicolo_corrispondenza";
	}
}
