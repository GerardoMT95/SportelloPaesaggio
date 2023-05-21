package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AllegatoObbligatorio implements _Interfaccia_dbMapping
{
	type,
	codice_tipo_procedimento;
	
	@Override
	public String tableName()
	{
		return "allegato_obbligatorio";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AllegatoObbligatorio> ids()
	{
		List<AllegatoObbligatorio> pk = new ArrayList<AllegatoObbligatorio>();
		pk.add(AllegatoObbligatorio.type);
		pk.add(AllegatoObbligatorio.codice_tipo_procedimento);
		return pk;
	}
	
	public static String getTableName()
	{
		return "allegato_obbligatorio";
	}
}
