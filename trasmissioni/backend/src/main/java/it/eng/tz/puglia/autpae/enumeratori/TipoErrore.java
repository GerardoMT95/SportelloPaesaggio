package it.eng.tz.puglia.autpae.enumeratori;

public enum TipoErrore {
	/**
	 * errore (nessuno | no-dest | no-dominio | virus | altro) "nessuno">
	 */
	NESSUNO("nessuno"),
	NO_DEST("no-dest"),
	NO_DOMINIO("no-dominio"), 
	VIRUS("virus"),
	ALTRO("altro");

	private String value;

	private TipoErrore(String text) {
		this.value = text;
	}

	public String getTextValue() {
		return value;
	}
	
	public static TipoErrore fromString(String errore) {
		switch (errore) {
		case "nessuno":
			return NESSUNO;
		case "no-dest":
			return NO_DEST;
		case "no-dominio":
			return NO_DOMINIO;
		case "virus":
			return VIRUS;
		case "altro":
			return ALTRO;
		default:
			return null;
		}
	}

}