package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum StoricoAssegnamento implements _Interfaccia_dbMapping
{
	id,
	id_fascicolo,
	id_organizzazione,
	fase,
	username_funzionario,
	denominazione_funzionario,
	tipo_operazione,
	data_operazione;
	
	
	@Override
	public String tableName()
	{
		return "storico_assegnamento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<StoricoAssegnamento> ids()
	{
		return Collections.singletonList(StoricoAssegnamento.id);
	}
	
	public static String getTableName()
	{
		return "storico_assegnamento";
	}
}