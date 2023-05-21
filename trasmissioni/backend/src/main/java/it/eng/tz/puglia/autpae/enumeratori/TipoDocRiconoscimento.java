package it.eng.tz.puglia.autpae.enumeratori;

public enum TipoDocRiconoscimento {
	
	CARTA_DI_IDENTITA("Carta di identità"),
	PATENTE("Patente"),
	PASSAPORTO("Passaporto");
	
	TipoDocRiconoscimento(String value){
		this.valore=value;
	}
	
	private String valore;
	
	public String getValore() {
		return this.valore;
	};

}
