package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum ConfigurazioneCasellaPostale implements _Interfaccia_dbMapping {
	
	email,
	configurazione;

	@Override
	public String tableName()
	{
		return "configurazione_casella_postale";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ConfigurazioneCasellaPostale> ids()
	{
		return Collections.singletonList(ConfigurazioneCasellaPostale.email);
	}
	
	public static String getTableName()
	{
		return "configurazione_casella_postale";
	}

}
