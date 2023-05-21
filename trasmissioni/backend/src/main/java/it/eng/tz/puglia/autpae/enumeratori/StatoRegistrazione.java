package it.eng.tz.puglia.autpae.enumeratori;

public enum StatoRegistrazione {
	
	SELECTED("In corso"),
	FINISHED("Conclusa"),
	ON_MODIFY("In modifica"),
	CANCELLED("Annullata");
	
	
	private String value;
	
	private StatoRegistrazione(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
	
//	static public StatoRegistrazione getRegistrationStatus(String value) {
//		switch (value) {
//		case "Annullata":
//			return StatoRegistrazione.CANCELLED;
//		case "In corso":
//			return StatoRegistrazione.SELECTED;
//		case "Concluso":
//			return StatoRegistrazione.FINISHED;
//		case "In modifica":
//			return StatoRegistrazione.ON_MODIFY;
//		default:
//			return null;
//		}
//	}
}