package it.eng.tz.puglia.servizi_esterni.remote.utility;

public enum TipoEnte
{
	regione("R"),
	comune("CO"),
	provincia("P"),
	soprintendenza("SI"),
	unione_di_comuni("UC"),
	associazione_di_comuni("AC"),
	
	ente("E"),
	consorzio("CN"),
	sito_sic("SS"),
	sito_zps("SZ"),
	ufficio_regionale("UR"),
	altro("ZZ");
	
	
	private String codice;
	
	public String getCodice()
	{
		return codice;
	}
	
	public static TipoEnte fromCodice(String codice)
	{
		switch(codice)
		{
		case "R":
			return regione;
		case "CO":
			return comune;
		case "P":
			return provincia;
		case "SI":
			return soprintendenza;
		case "UC":
			return unione_di_comuni;
		case "AC":
			return associazione_di_comuni;
		
		case "E":
			return ente;
		case "CN":
			return consorzio;
		case "SS":
			return sito_sic;
		case "SZ":
			return sito_zps;
		case "UR":
			return ufficio_regionale;
		case "ZZ":
			return altro;
		default:
			return null;
		}
	}
	
	private TipoEnte(String codice)
	{
		this.codice = codice;
	}
}
