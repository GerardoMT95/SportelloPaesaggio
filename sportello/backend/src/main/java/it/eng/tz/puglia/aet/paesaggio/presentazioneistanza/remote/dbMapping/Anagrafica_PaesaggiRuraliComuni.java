package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Anagrafica_PaesaggiRuraliComuni implements Trasmissioni_Interfaccia_dbMapping
{
	codice,
	codice_catastale,
	codice_istat;
	
	@Override
	public String tableName()
	{
		return "anagrafica.paesaggi_rurali_comuni";  // nome del db e dello schema
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_PaesaggiRuraliComuni> ids()
	{
		return Collections.singletonList(Anagrafica_PaesaggiRuraliComuni.codice);
	}
	
	public static String getTableName()
	{
		return "anagrafica.paesaggi_rurali_comuni";	// nome del db e dello schema
	}
	
	
}
