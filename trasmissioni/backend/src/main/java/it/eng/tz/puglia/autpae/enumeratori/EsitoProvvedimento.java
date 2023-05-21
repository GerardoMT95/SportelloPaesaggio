package it.eng.tz.puglia.autpae.enumeratori;

public enum EsitoProvvedimento {
	
	AUTORIZZATO("Autorizzato"), 
	NON_AUTORIZZATO("Non autorizzato"), 
	AUT_CON_PRESCRIZ("Autorizzato con Prescrizioni"); 
	
	
	private String value;
	
	private EsitoProvvedimento(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
	
	public static EsitoProvvedimento fromString(String value)
	{
		if(value == null) return null;
		switch(value.toUpperCase())
		{
		case "AUTORIZZATO": 
			return AUTORIZZATO;
		case "NON AUTORIZZATO": 
			return NON_AUTORIZZATO;
		case "AUTORIZZATO CON PRESCRIZIONE":
		case "AUTORIZZATO CON PRESCRIZIONI":
			return AUT_CON_PRESCRIZ;
		default: return null;
		}
	}
	
	/**
	 * A="Autorizzato"  N="Non autorizzato" P="Autorizzato con prescrizione"
	 */
	public static EsitoProvvedimento fromCiviliaValue(String value)
	{
		if(value == null) return null;
		switch(value.toUpperCase())
		{
		case "A": 
			return AUTORIZZATO;
		case "N": 
			return NON_AUTORIZZATO;
		case "P":
			return AUT_CON_PRESCRIZ;
		default: return null;
		}
	}
}