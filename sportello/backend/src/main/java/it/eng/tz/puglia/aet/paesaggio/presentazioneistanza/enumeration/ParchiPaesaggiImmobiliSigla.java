package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum ParchiPaesaggiImmobiliSigla {
	/**
	 *BP parchi e riserve 
	 */
	P("BP - parchi e riserve"),
	/**
	 * UCP - paesaggi rurali
	 */
	R("UCP - paesaggi rurali"),
	/**
	 * BP - immobili ed aree di notevole interesse pubblico
	 */
	I("BP - immobili ed aree di notevole interesse pubblico");
	
	private String label;
	
	private ParchiPaesaggiImmobiliSigla(String label) {
		this.label=label;
	}

	public String getLabel() {
		return label;
	}

	
}
