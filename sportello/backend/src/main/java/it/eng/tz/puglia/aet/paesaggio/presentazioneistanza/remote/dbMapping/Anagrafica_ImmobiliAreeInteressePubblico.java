package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Anagrafica_ImmobiliAreeInteressePubblico implements Trasmissioni_Interfaccia_dbMapping
{
	codice,
	oggetto,
	descrizione;
	
	@Override
	public String tableName()
	{
		return "anagrafica.immobili_aree_interesse_pubblico";  // nome del db e dello schema
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_ImmobiliAreeInteressePubblico> ids()
	{
		return Collections.singletonList(Anagrafica_ImmobiliAreeInteressePubblico.codice);
	}
	
	public static String getTableName()
	{
		return "anagrafica.immobili_aree_interesse_pubblico";	// nome del db e dello schema
	}
	
	
}
