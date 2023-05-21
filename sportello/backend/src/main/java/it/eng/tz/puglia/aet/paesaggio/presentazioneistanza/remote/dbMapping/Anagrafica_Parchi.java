package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Anagrafica_Parchi implements Trasmissioni_Interfaccia_dbMapping
{
	codice,
	nome,
	descrizione,
	mail;
	
	@Override
	public String tableName()
	{
		return "anagrafica.parchi";  // nome del db e dello schema
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_Parchi> ids()
	{
		return Collections.singletonList(Anagrafica_Parchi.codice);
	}
	
	public static String getTableName()
	{
		return "anagrafica.parchi";	// nome del db e dello schema
	}
	
	
}
