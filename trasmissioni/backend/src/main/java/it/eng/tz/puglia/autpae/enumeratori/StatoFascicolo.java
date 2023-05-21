package it.eng.tz.puglia.autpae.enumeratori;

public enum StatoFascicolo {
	
	WORKING    ("In lavorazione"),  		  // solo il gruppo creazione SA
	TRANSMITTED("Trasmesso"),		// RicPub // tutti quelli che hanno competenza territoriale
	SELECTED   ("In corso"),		// RicPub // tutti quelli che hanno competenza territoriale	// TODO: la dicitura "in corso" è corretta ??????
	FINISHED   ("Concluso"),		// RicPub // tutti quelli che hanno competenza territoriale
	ON_MODIFY  ("In modifica"),		// RicPub // tutti quelli che hanno competenza territoriale (probabilm escluse gli eti) il pubblico non deve avere percezione
	CANCELLED  ("Annullato"),				  // super-amministratore // anche chi l'ha creato
	DELETED    ("Eliminato");				  // super-amministratore // cancellazione logica dei Fascicoli (in Working). Attualmente usiamo il campo 'deleted'
	
	
	private String value;
	
	private StatoFascicolo(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
	
//	static public StatoFascicolo getRegistrationStatus(String value) {
//		switch (value) {
//		case "In lavorazione":
//			return StatoFascicolo.WORKING;
//		case "Trasmessa":
//			return StatoFascicolo.TRANSMITTED;
//		case "Annullata":
//			return StatoFascicolo.CANCELLED;
//		case "In corso":
//			return StatoFascicolo.SELECTED;
//		case "Concluso":
//			return StatoFascicolo.FINISHED;
//		case "In modifica":
//			return StatoFascicolo.ON_MODIFY;
//		default:
//			return null;
//		}
//	}
	
	public static StatoFascicolo[] statiAmmessiDiogene() {
		return new StatoFascicolo[] {
				FINISHED,
				}; 
	}
}