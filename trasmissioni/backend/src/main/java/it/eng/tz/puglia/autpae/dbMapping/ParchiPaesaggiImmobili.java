package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ParchiPaesaggiImmobili implements _Interfaccia_dbMapping
{
	pratica_id,
	comune_id,
	tipo_vincolo,
	codice,
	descrizione,
	selezionato,
	info,
	data_inserimento;
	
	
	@Override
	public String tableName()
	{
		return "parchi_paesaggi_immobili";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ParchiPaesaggiImmobili> ids()
	{
		List<ParchiPaesaggiImmobili> pk = new ArrayList<>();
		pk.add(ParchiPaesaggiImmobili.comune_id);
		pk.add(ParchiPaesaggiImmobili.pratica_id);
		pk.add(ParchiPaesaggiImmobili.tipo_vincolo);
		pk.add(ParchiPaesaggiImmobili.codice);
		return pk;
	}
	
	public static String getTableName()
	{
		return "parchi_paesaggi_immobili";
	}
	
}