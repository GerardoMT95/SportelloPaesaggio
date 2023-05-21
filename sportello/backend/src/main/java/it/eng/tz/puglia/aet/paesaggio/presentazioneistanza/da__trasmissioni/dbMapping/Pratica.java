package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Pratica implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	ente_delegato,
	codice_pratica_appptr,
	tipo_procedimento,
	stato,
	oggetto;
	
	
	@Override
	public String tableName()
	{
		return "pratica";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Pratica> ids()
	{
		return Collections.singletonList(Pratica.id);
	}
	
	public static String getTableName()
	{
		return "pratica";
	}
}