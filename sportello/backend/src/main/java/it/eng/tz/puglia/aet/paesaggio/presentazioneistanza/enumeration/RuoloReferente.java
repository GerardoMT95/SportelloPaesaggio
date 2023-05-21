package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum RuoloReferente {
	/**
	 * metto solo i due ruoli a cui è associata una specifica....
	 */
	RappLegale(5,"Rappresentante Legale"),
	Altro(9,"Altro");  
	
	private int value;
	private String nome;
	
	private RuoloReferente(int value, String nome) {
		this.value=value;
		this.nome=nome;
	}
	
	private RuoloReferente(int value) {
		this.value=value;
	}
	
	public int getValue() {
		return value;
	}
  
	public String getNome() {
		return nome;
	}
	
	public static String getNomeFromValue(int value) {
		return "nome";	// TODO: vedere come farlo
	}
	
}