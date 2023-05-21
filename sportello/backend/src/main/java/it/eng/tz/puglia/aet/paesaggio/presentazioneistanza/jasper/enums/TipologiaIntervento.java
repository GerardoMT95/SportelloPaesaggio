package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums;

public enum TipologiaIntervento {
	
	TIPO_INT_1("Interventi e/o opere non di edilizia"),
	TIPO_INT_2("Manutenzione, restauro e risanamento conservativo che alterano lo stato e l'aspetto esteriore dell'edificio (art. 3 DPR 380/01)"),
	TIPO_INT_3("Nuova costruzione (art. 3 DPR 380/01)"),
	TIPO_INT_4("Ristrutturazione edilizia (art. 3 DPR 380/01)"),
	TIPO_INT_5("Ristrutturazione urbanistica (art. 3 DPR 380/01)");

	private String label;

	private TipologiaIntervento(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
}
