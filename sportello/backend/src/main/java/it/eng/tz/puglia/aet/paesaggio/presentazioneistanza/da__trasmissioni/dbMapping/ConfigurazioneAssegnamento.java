package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum ConfigurazioneAssegnamento implements Trasmissioni_Interfaccia_dbMapping
{
	id_organizzazione,
	fase,
	rup,
	criterio_assegnamento;

	
	@Override
	public String tableName()
	{
		return "configurazione_assegnamento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ConfigurazioneAssegnamento> ids()
	{
		List<ConfigurazioneAssegnamento> pk = new ArrayList<>();
		pk.add(ConfigurazioneAssegnamento.id_organizzazione);
		pk.add(ConfigurazioneAssegnamento.fase);
		pk.add(ConfigurazioneAssegnamento.rup);
		return pk;
	}
	
	public static String getTableName()
	{
		return "configurazione_assegnamento";
	}
}