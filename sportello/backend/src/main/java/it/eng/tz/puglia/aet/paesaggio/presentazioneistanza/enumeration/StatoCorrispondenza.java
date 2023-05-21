package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum StatoCorrispondenza {
	
	INVIATA("Inviata"),
	ESITO_POSITIVO("Esito positivo"),      // 'accettata'
	ESITO_CON_ERRORE("Esito con errore");
	
	private String value;
	
	private StatoCorrispondenza(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
}
