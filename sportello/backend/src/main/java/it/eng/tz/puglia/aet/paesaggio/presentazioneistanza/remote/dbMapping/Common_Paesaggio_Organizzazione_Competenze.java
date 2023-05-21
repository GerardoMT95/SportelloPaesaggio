package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Common_Paesaggio_Organizzazione_Competenze implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	paesaggio_organizzazione_id,
	ente_id,
	data_inizio_delega,
	data_fine_delega;
	
	
	@Override
	public String tableName()
	{
		return "common.paesaggio_organizzazione_competenze";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Paesaggio_Organizzazione_Competenze> ids()
	{
		return Collections.singletonList(Common_Paesaggio_Organizzazione_Competenze.id);
	}
	
	public static String getTableName()
	{
		return "common.paesaggio_organizzazione_competenze";
	}
	
}
