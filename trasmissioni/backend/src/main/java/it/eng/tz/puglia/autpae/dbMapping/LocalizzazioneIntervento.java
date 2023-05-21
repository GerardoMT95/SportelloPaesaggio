package it.eng.tz.puglia.autpae.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum LocalizzazioneIntervento implements _Interfaccia_dbMapping
{
	pratica_id,
	comune_id,
	indirizzo,
	num_civico,
	piano,
	interno,
	dest_uso_att,
	dest_uso_prog,
	comune,
	sigla_provincia,
	data_riferimento_catastale,
	strade;
	
	
	@Override
	public String tableName()
	{
		return "localizzazione_intervento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<LocalizzazioneIntervento> ids()
	{
		List<LocalizzazioneIntervento> pk = new ArrayList<>();
		pk.add(LocalizzazioneIntervento.comune_id);
		pk.add(LocalizzazioneIntervento.pratica_id);
		return pk;
	}
	
	public static String getTableName()
	{
		return "localizzazione_intervento";
	}
	
}