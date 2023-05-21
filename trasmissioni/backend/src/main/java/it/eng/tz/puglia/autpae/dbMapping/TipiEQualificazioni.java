package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum TipiEQualificazioni implements _Interfaccia_dbMapping {
	
	id,
	stile,
	zona,
	label,
	raggruppamento,
	ordine;
	
	@Override
	public String tableName()
	{
		return "tipi_e_qualificazioni";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TipiEQualificazioni> ids()
	{
		return Collections.singletonList(TipiEQualificazioni.id);
	}
	
	public static String getTableName()
	{
		return "tipi_e_qualificazioni";
	}

}
