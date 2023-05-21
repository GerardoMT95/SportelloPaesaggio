package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums;

public enum CaratterizzazioneIntervento {
	
	CAR_INT_1("Rimessa in ripristino"),
	CAR_INT_2("Demolizione"),
	CAR_INT_3("Nuovi insediamenti in area urbana"),
	CAR_INT_4("Nuovi insediamenti rurali"),
	CAR_INT_5("Interventi su manufatti rurali in pietra a secco"),
	CAR_INT_6("Interventi su manufatti rurali non in pietra a secco"),
	CAR_INT_7("Nuovi insediamenti industriali e commerciali"),
	CAR_INT_8("Interventi su insediamenti industriali e commerciali"),
	CAR_INT_9("Recinzioni"),
	CAR_INT_10("Impianti per la produzione di energia rinnovabile"),
	CAR_INT_11("Linee telefoniche o elettriche"),
	CAR_INT_12("Infrastrutture primarie (viarie, acqua, gas, ecc.)"),
	CAR_INT_13("Miglioramenti fondiari"),
	CAR_INT_14("Altro:");

	private String label;

	private CaratterizzazioneIntervento(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}
