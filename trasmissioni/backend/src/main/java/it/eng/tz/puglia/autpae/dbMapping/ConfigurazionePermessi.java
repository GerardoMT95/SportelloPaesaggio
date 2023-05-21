package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum ConfigurazionePermessi implements _Interfaccia_dbMapping {
	
	codice_ente,
	permesso_documentazione,
	permesso_osservazione,
	permesso_comunicazione,
	stato_registrazione_pubblico,
	esito_verifica_pubblico;
	

	@Override
	public String tableName()
	{
		return "configurazione_permessi";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ConfigurazionePermessi> ids()
	{
		return Collections.singletonList(ConfigurazionePermessi.codice_ente);
	}
	
	public static String getTableName()
	{
		return "configurazione_permessi";
	}

}
