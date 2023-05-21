package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum TipoProcedimento implements _Interfaccia_dbMapping
{
	codice,
	descrizione,
	invia_email,
	sanatoria,
	inizio_validita,
	fine_validita,
	applicativo;
	
	@Override
	public String tableName()
	{
		return "tipo_procedimento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TipoProcedimento> ids()
	{
		return Collections.singletonList(TipoProcedimento.codice);
	}
	
	public static String getTableName()
	{
		return "tipo_procedimento";
	}
}
