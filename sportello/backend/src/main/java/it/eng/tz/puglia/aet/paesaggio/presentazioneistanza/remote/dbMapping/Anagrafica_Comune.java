package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping;

import java.util.Collection;
import java.util.Collections;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Interfaccia_dbMapping;

public enum Anagrafica_Comune implements Trasmissioni_Interfaccia_dbMapping {
	
	id_regione,
	id_provincia,
	cod_istat,
	cod_catastale,
	nome;
	
	@Override
	public String tableName()
	{
		return "anagrafica.comune";
	}
	@Override
	public String columnName()
	{
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Anagrafica_Comune> ids()
	{
		return Collections.singletonList(Anagrafica_Comune.nome);
	}
	
	public static String getTableName()
	{
		return "anagrafica.comune";
	}

	
	
}
