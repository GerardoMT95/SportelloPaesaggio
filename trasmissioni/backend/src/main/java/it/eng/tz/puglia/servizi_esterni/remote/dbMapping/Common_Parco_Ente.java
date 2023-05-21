package it.eng.tz.puglia.servizi_esterni.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.autpae.dbMapping._Interfaccia_dbMapping;

public enum Common_Parco_Ente implements _Interfaccia_dbMapping
{
	id,
	id_parco,
	id_ente,
	data_inizio_validita,
	data_fine_validita;
	
	
	@Override
	public String tableName()
	{
		return "common.parco_ente";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Common_Parco_Ente> ids()
	{
		return Collections.singletonList(Common_Parco_Ente.id);
	}
	
	public static String getTableName()
	{
		return "common.parco_ente";
	}
	
	
}
