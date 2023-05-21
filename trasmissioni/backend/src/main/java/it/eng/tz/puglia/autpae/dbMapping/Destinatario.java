package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Destinatario implements _Interfaccia_dbMapping
{
	id,
	type,
	email,
	stato,
	id_corrispondenza,
	denominazione,
	pec,
	data_ricezione;
	
	@Override
	public String tableName()
	{
		return "destinatario";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Destinatario> ids()
	{
		return Collections.singletonList(Destinatario.id);
	}
	
	public static String getTableName()
	{
		return "destinatario";
	}
}
