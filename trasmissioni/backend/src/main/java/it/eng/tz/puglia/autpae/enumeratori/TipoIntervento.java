package it.eng.tz.puglia.autpae.enumeratori;

public enum TipoIntervento {

	// ENUM che collega le stringhe che mi arrivano FE agli "id" (statici) della tabella "tipi_e_qualificazioni"
	
	// autpae
	INTERVENTI_NON_EDILIZI("1"),
	MANUTENZIONE_RESTAURO("2"),
	NUOVA_COSTRUZIONE("3"),
	RISTR_EDILIZIA("4"),
	RISTR_URBANISTICA("5"),
	// pareri
	PIANI_URBANISTICI("166"),
	PIANI_PARTICOLAREGGIATI("167"),
	PIANI_EDILIZIA("168"),
	PIANI_LOTTIZZAZIONE("169"),
	PIANI_INSEDIAMENTI("170"), 
	PIANI_RECUPERO("171"),
	PIANI_ALTRO("172"),
	// putt
	INTERVENTI_IMPEGNO_TERRITORIALE("201");
	// INTERVENTI_NON_EDILIZI("1")
	// MANUTENZIONE_RESTAURO("2")
	// NUOVA_COSTRUZIONE("3")
	// RISTR_EDILIZIA("4")
	
	
	
	private String value;
	

	private TipoIntervento(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
	
}