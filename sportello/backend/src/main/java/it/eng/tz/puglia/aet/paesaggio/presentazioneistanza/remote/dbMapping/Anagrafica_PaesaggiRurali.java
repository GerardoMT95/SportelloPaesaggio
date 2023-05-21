package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Anagrafica_PaesaggiRurali implements Trasmissioni_Interfaccia_dbMapping
{
	codice,
	descrizione;
	
	@Override
	public String tableName()
	{
		return "anagrafica.paesaggi_rurali";  // nome del db e dello schema
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_PaesaggiRurali> ids()
	{
		return Collections.singletonList(Anagrafica_PaesaggiRurali.codice);
	}
	
	public static String getTableName()
	{
		return "anagrafica.paesaggi_rurali";	// nome del db e dello schema
	}
	
	
}
