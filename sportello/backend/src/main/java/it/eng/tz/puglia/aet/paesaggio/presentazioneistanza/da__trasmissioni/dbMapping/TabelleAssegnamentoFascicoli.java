package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum TabelleAssegnamentoFascicoli implements Trasmissioni_Interfaccia_dbMapping
{
	id,
	stato,
	codice,
	tipo_procedimento,
	oggetto_intervento,
	username_funzionario,
	denominazione_funzionario,
	num_assegnazioni,
	data_operazione;
//	data_assegnazione;

	
	@Override
	public String tableName()
	{
		return "Tabella_DUMY";
	}
	
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TabelleAssegnamentoFascicoli> ids()
	{
		return Collections.singletonList(TabelleAssegnamentoFascicoli.id);
	}
	
	public static String getTableName()
	{
		return "Tabella_DUMY";
	}
}
