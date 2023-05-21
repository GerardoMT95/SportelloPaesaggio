package it.eng.tz.puglia.autpae.enumeratori;

public enum TipoOrganizzazioneSpecifica {
	
	REGIONE("Regione"),
	PROVINCIA("Provincia"),
	COMUNE("Comune"),
	UNIONE("Unione"),
	SOPRINTENDENZA("Soprintendenza"),
	COMMISSIONE_LOCALE("Commissione locale");
	//ASSOCIAZIONE("Associazione");//di comuni MA NON PIU' ATTIVE/UTILIZZATE
	
	private String value;
	private TipoOrganizzazioneSpecifica(String val) {
		this.value=val;
	}
	public String getValue() {
		return this.value;
	}
	
	public static TipoOrganizzazioneSpecifica fromValue(String str)
	{
		switch(str)
		{
		case "Regione": return REGIONE;
		case "Provincia": return PROVINCIA;
		case "Comune": return COMUNE;
		case "Unione": return UNIONE;
		case "Soprintendenza": return SOPRINTENDENZA;
		case "Commissione locale": return COMMISSIONE_LOCALE;
		//case "Associazione": return ASSOCIAZIONE;
		default: return null;
		}
	}
	
	
}