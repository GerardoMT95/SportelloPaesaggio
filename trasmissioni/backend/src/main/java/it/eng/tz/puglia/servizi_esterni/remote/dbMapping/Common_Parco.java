package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Parco implements _Interfaccia_dbMapping
{
	id,
	denominazione,
	codiceap,
	decreto,
	pubblicazione,
	area_ha,
	id_classificazione;
	
	
	@Override
	public String tableName()
	{
		return "common.parco";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Parco> ids()
	{
		return Collections.singletonList(Common_Parco.id);
	}
	
	public static String getTableName()
	{
		return "common.parco";
	}
	
	
}
