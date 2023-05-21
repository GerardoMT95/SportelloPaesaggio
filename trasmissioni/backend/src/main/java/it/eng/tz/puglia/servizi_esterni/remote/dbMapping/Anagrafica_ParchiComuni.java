package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Anagrafica_ParchiComuni implements _Interfaccia_dbMapping
{
	codice,
	codice_catastale,
	codice_istat,
	mail;
	
	@Override
	public String tableName()
	{
		return "anagrafica.parchi_comuni";  // nome del db e dello schema
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_ParchiComuni> ids()
	{
		return Collections.singletonList(Anagrafica_ParchiComuni.codice);
	}
	
	public static String getTableName()
	{
		return "anagrafica.parchi_comuni";	// nome del db e dello schema
	}
	
}
