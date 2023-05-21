package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Common_Ente implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	nome,
	descrizione,
	codice,
	pec,
	parent_id,
	tipo;
	
	
	@Override
	public String tableName()
	{
		return "common.ente";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Ente> ids()
	{
		return Collections.singletonList(Common_Ente.id);
	}
	
	public static String getTableName()
	{
		return "common.ente";
	}
	
	
}
