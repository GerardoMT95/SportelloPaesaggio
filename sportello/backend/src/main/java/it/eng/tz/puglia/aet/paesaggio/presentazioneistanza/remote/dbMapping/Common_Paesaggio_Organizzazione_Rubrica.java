package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Common_Paesaggio_Organizzazione_Rubrica implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	paesaggio_organizzazione_id,
	codice_applicazione,
	nome,
	pec,
	mail,
	data_creazione,
	data_modifica,
	username_creazione,
	username_modifica;
	
	
	@Override
	public String tableName()
	{
		return "common.paesaggio_organizzazione_rubrica";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Paesaggio_Organizzazione_Rubrica> ids()
	{
		List<Common_Paesaggio_Organizzazione_Rubrica> pk = new ArrayList<>();
		pk.add(Common_Paesaggio_Organizzazione_Rubrica.id);
		pk.add(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id);
		pk.add(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione);
		return pk;
	}
	
	public static String getTableName()
	{
		return "common.paesaggio_organizzazione_rubrica";
	}
	
}
