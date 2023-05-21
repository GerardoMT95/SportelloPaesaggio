package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Richiedente implements _Interfaccia_dbMapping {
	
	id,
	id_fascicolo,
	nome,
	cognome,
	codice_fiscale,
	sesso,
	data_nascita,
	stato_nascita,
	provincia_nascita,
	comune_nascita,
	stato_residenza,
	provincia_residenza,
	comune_residenza,
	via_residenza,
	numero_residenza,
	cap,
	pec,
	email,
	telefono,
	ditta_societa,
	ditta_in_qualita_di,
	ditta_qualita_altro,
	ditta_codice_fiscale,
	ditta_partita_iva;
	

	@Override
	public String tableName()
	{
		return "richiedente";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Richiedente> ids()
	{
		return Collections.singletonList(Richiedente.id);
	}
	
	public static String getTableName()
	{
		return "richiedente";
	}

}
