package it.eng.tz.puglia.autpae.enumeratori;

public enum ParchiPaesaggiImmobiliSigla {

	P("BP - parchi e riserve"),
	R("UCP - paesaggi rurali"),
	I("BP - immobili ed aree di notevole interesse pubblico");
	
	
	private String label;
	
	private ParchiPaesaggiImmobiliSigla(String label) {
		this.label=label;
	}

	public String getLabel() {
		return label;
	}
	
}