package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Paesaggio_Organizzazione implements _Interfaccia_dbMapping
{
	id,
	ente_id,
	denominazione,
	codice_istat,
	codice_catastale,
	codice_civilia,
	tipo_organizzazione,
	tipo_organizzazione_specifica,
	riferimento_organizzazione,
	data_creazione,
	data_termine;
	
	
	@Override
	public String tableName()
	{
		return "common.paesaggio_organizzazione";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Paesaggio_Organizzazione> ids()
	{
		return Collections.singletonList(Common_Paesaggio_Organizzazione.id);
	}
	
	public static String getTableName()
	{
		return "common.paesaggio_organizzazione";
	}
	
	
}
