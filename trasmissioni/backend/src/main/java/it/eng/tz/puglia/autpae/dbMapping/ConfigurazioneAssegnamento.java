package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ConfigurazioneAssegnamento implements _Interfaccia_dbMapping
{
	id_organizzazione,
	fase,
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
		return pk;
	}
	
	public static String getTableName()
	{
		return "configurazione_assegnamento";
	}
}