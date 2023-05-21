package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum ValoriAssegnamento implements Trasmissioni_Interfaccia_dbMapping
{
	id_organizzazione,
	fase,
	id_comune_tipo_procedimento,
	rup,
	username_utente,
	denominazione_utente;
	
	
	@Override
	public String tableName()
	{
		return "valori_assegnamento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ValoriAssegnamento> ids()
	{
		List<ValoriAssegnamento> pk = new ArrayList<>();
		pk.add(ValoriAssegnamento.id_organizzazione);
		pk.add(ValoriAssegnamento.fase);
		pk.add(ValoriAssegnamento.id_comune_tipo_procedimento);
		pk.add(ValoriAssegnamento.rup);
	//	pk.add(ValoriAssegnamento.username_utente);
		return pk;
	}
	
	public static String getTableName()
	{
		return "valori_assegnamento";
	}
}