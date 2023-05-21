package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum ConfigurazioneCampionamento implements _Interfaccia_dbMapping {
	
	id,
	campionamento_attivo,
	intervallo_campionamento,
	tipo_campionamento_successivo,
	percentuale_istanze,
	giorni_notifica_campionamento,
	giorni_notifica_verifica,
	intervallo_verifica,
	esito_pubblico,
	applica_in_corso;

	@Override
	public String tableName()
	{
		return "configurazione_campionamento";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ConfigurazioneCampionamento> ids()
	{
		return Collections.singletonList(ConfigurazioneCampionamento.id);
	}
	
	public static String getTableName()
	{
		return "configurazione_campionamento";
	}

}
