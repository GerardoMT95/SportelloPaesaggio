package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum AssegnamentoFascicolo implements Trasmissioni_Interfaccia_dbMapping
{
	id_fascicolo,
	id_organizzazione,
	fase,
	rup,
	username_utente,
	denominazione_utente,
	num_assegnazioni,
	data_operazione;
	
	
	@Override
	public String tableName()
	{
		return "assegnamento_fascicolo";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AssegnamentoFascicolo> ids()
	{
		List<AssegnamentoFascicolo> pk = new ArrayList<>();
		pk.add(AssegnamentoFascicolo.id_fascicolo);
		pk.add(AssegnamentoFascicolo.id_organizzazione);
		pk.add(AssegnamentoFascicolo.fase);
		pk.add(AssegnamentoFascicolo.rup);
		return pk;
	}
	
	public static String getTableName()
	{
		return "assegnamento_fascicolo";
	}
}