package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum Ruoli {
	
	ADMIN		   ("Amministratore di Applicazione"),	// in realtà non è un RUOLO ma è un GRUPPO
	RICHIEDENTE	   ("Richiedente"),						// in realtà non è un RUOLO ma è un GRUPPO
	AMMINISTRATORE ("Amministratore"),
	FUNZIONARIO	   ("Funzionario"),
	DIRIGENTE	   ("Dirigente");
	
	
	private String value;
	
	
	private Ruoli(String text) {
		this.value = text;
	}
	
	
	public String getTextValue() {
		return value;
	}
	
}