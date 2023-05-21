package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum StoricoRichiesta
{
	SOSPENSIONE("NOT_SOSP"),
	ARCHIVIAZIONE("NOT_ARC"),
	ATTIVAZIONE("NOT_ATT");
	
	private String codiceTemplate;
	
	StoricoRichiesta(String codice)
	{
		codiceTemplate = codice;
	}
	
	public String getCodice()
	{
		return codiceTemplate;
	}
}
