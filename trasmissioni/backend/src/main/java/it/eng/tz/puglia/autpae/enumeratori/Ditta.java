package it.eng.tz.puglia.autpae.enumeratori;

public enum Ditta {
	
	RAPPRES_LEGALE("Rappresentante Legale"),
	TECNICO_INCARICATO("Tecnico Incaricato"),
	ALTRO("Altro");
	
	
	private String value;
	
	private Ditta(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
}
