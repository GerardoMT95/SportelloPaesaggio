package it.eng.tz.puglia.autpae.dbMapping;

import java.util.Collection;
import java.util.Collections;

public enum Responsabile implements _Interfaccia_dbMapping {
	
	id,
	nome,
	cognome,
	in_qualita_di,
	servizio_settore_ufficio,
	pec,
	mail,
	telefono,
	riconoscimento_tipo,
	riconoscimento_numero,
	riconoscimento_data_rilascio,
	riconoscimento_data_scadenza,
	riconoscimento_rilasciato_da,
	id_fascicolo;

	@Override
	public String tableName()
	{
		return "responsabile";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Responsabile> ids()
	{
		return Collections.singletonList(Responsabile.id);
	}
	
	public static String getTableName()
	{
		return "responsabile";
	}

}
