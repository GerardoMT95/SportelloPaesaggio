package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ValoriAssegnamento implements _Interfaccia_dbMapping
{
	id_organizzazione,
	fase,
	id_comune_tipo_procedimento,
	username_funzionario,
	denominazione_funzionario;
	
	
	@Override
	public String tableName()
	{
		return "valori_assegnamento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ValoriAssegnamento> ids()
	{
		List<ValoriAssegnamento> pk = new ArrayList<>();
		pk.add(ValoriAssegnamento.id_organizzazione);
		pk.add(ValoriAssegnamento.fase);
		pk.add(ValoriAssegnamento.id_comune_tipo_procedimento);
		pk.add(ValoriAssegnamento.username_funzionario);
		return pk;
	}
	
	public static String getTableName()
	{
		return "valori_assegnamento";
	}
}