package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Paesaggio_CodiceFascicolo implements _Interfaccia_dbMapping
{
	codice_ente,
	anno,
	prefisso,
	seriale;
	
	@Override
	public String tableName()
	{
		return "common.paesaggio_codice_fascicolo";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Paesaggio_CodiceFascicolo> ids()
	{
		List<Common_Paesaggio_CodiceFascicolo> pk = new ArrayList<>();
		pk.add(Common_Paesaggio_CodiceFascicolo.codice_ente);
		pk.add(Common_Paesaggio_CodiceFascicolo.anno);
		pk.add(Common_Paesaggio_CodiceFascicolo.prefisso);
		return pk;
	}
	
	public static String getTableName()
	{
		return "common.paesaggio_codice_fascicolo";
	}
}
