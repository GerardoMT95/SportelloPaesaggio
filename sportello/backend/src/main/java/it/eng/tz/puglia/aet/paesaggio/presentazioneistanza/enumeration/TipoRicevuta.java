package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

/**
 * copiato da
 * it.eng.tz.regione.puglia.webmail.be.enumerations.TipoPostaCertificataEnum di
 * webmail.commons
 * 
 * @author acolaianni
 *
 */
public enum TipoRicevuta {

	/*
	 * accettazione | non-accettazione | presa-in-carico | avvenuta-consegna |
	 * posta-certificata | errore-consegna | preavviso-errore-consegna |
	 * rilevazione-virus
	 */
	ACCETTAZIONE("accettazione"), 							// 5 OK blue
	NON_ACCETTAZIONE("non-accettazione"), 					// 3 KO red
	
	PRESA_IN_CARICO("presa-in-carico"),						// 4 OK blue
	POSTA_CERTIFICATA("posta-certificata"), 				// 4 OK blue
	
	AVVENUTA_CONSEGNA("avvenuta-consegna"),					// 1 OK green
	
	PREAVVISO_ERRORE_CONSEGNA("preavviso-errore-consegna"), // 2 KO red
	ERRORE_CONSEGNA("errore-consegna"),						// 2 KO red
	RILEVAZIONE_VIRUS("rilevazione-virus");					// 2 KO red
	
	
	private String value;

	private TipoRicevuta(String text) {
		this.value = text;
	}

	public String getTextValue() {
		return value;
	}

	public static TipoRicevuta fromString(String tipo) {
		switch (tipo) {
		case "accettazione":
			return ACCETTAZIONE;
		case "non-accettazione":
			return NON_ACCETTAZIONE;
		case "presa-in-carico":
			return PRESA_IN_CARICO;
		case "avvenuta-consegna":
			return AVVENUTA_CONSEGNA;
		case "posta-certificata":
			return POSTA_CERTIFICATA;
		case "errore-consegna":
			return ERRORE_CONSEGNA;
		case "preavviso-errore-consegna":
			return PREAVVISO_ERRORE_CONSEGNA;
		case "rilevazione-virus":
			return RILEVAZIONE_VIRUS;
		default:
			return null;
		}
	}
}