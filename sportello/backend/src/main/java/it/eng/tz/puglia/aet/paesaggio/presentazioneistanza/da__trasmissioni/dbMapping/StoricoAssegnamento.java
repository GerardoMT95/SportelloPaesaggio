package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum StoricoAssegnamento implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	id_fascicolo,
	id_organizzazione,
	fase,
	rup,
	username_utente,
	denominazione_utente,
	tipo_operazione,
	data_operazione;
	
	
	@Override
	public String tableName()
	{
		return "storico_assegnamento";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<StoricoAssegnamento> ids()
	{
		return Collections.singletonList(StoricoAssegnamento.id);
	}
	
	public static String getTableName()
	{
		return "storico_assegnamento";
	}
}