package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Common_Applicazione implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	nome,
	codice;
	
	
	@Override
	public String tableName()
	{
		return "common.applicazione";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Applicazione> ids()
	{
		return Collections.singletonList(Common_Applicazione.id);
	}
	
	public static String getTableName()
	{
		return "common.applicazione";
	}
	
	
}
