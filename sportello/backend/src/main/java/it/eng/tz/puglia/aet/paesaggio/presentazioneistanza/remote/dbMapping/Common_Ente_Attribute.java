package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Common_Ente_Attribute implements Trasmissioni_Interfaccia_dbMapping
{
	ente_id,
	applicazione_id,
	pec,
	mail,
	
	nome;				// per qualche ragione serve al FE nella "adminSearch"
	
	
	@Override
	public String tableName()
	{
		return "common.ente_attribute";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Ente_Attribute> ids()
	{
		List<Common_Ente_Attribute> pk = new ArrayList<>();
		pk.add(Common_Ente_Attribute.ente_id);
		pk.add(Common_Ente_Attribute.applicazione_id);
		return pk;
	}
	
	public static String getTableName()
	{
		return "common.ente_attribute";
	}
	
	
}
