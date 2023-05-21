package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Campionamento implements _Interfaccia_dbMapping {
	
	id,
	attivo,
	intervallo_camp,
	tipo_successivo,
	percentuale,
	notifica_camp,
	notifica_ver,
	intervallo_ver,
	esito_pubb,
	customized,
	data_inizio,
	data_campionatura;

	@Override
	public String tableName()
	{
		return "campionamento";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Campionamento> ids()
	{
		return Collections.singletonList(Campionamento.id);
	}
	
	public static String getTableName()
	{
		return "campionamento";
	}

}
