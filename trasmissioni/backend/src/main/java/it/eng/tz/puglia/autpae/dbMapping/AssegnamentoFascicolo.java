package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AssegnamentoFascicolo implements _Interfaccia_dbMapping
{
	id_fascicolo,
	id_organizzazione,
	fase,
	username_funzionario,
	denominazione_funzionario,
	num_assegnazioni,
	data_operazione;
	
	
	@Override
	public String tableName()
	{
		return "assegnamento_fascicolo";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AssegnamentoFascicolo> ids()
	{
		List<AssegnamentoFascicolo> pk = new ArrayList<>();
		pk.add(AssegnamentoFascicolo.id_fascicolo);
		pk.add(AssegnamentoFascicolo.id_organizzazione);
		pk.add(AssegnamentoFascicolo.fase);
		return pk;
	}
	
	public static String getTableName()
	{
		return "assegnamento_fascicolo";
	}
}