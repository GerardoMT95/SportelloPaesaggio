package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Anagrafica_Provincia implements _Interfaccia_dbMapping {
	
	id,
	sigla;
	
	@Override
	public String tableName()
	{
		return "anagrafica.provincia";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_Provincia> ids()
	{
		return Collections.singletonList(Anagrafica_Provincia.id);
	}
	
	public static String getTableName()
	{
		return "anagrafica.provincia";
	}

	
}
