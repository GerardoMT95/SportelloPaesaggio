package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Anagrafica_Regione implements Trasmissioni_Interfaccia_dbMapping {
	
	id,
	nome;
	
	@Override
	public String tableName()
	{
		return "anagrafica.regione";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_Regione> ids()
	{
		return Collections.singletonList(Anagrafica_Regione.id);
	}
	
	public static String getTableName()
	{
		return "anagrafica.regione";
	}

	
}
