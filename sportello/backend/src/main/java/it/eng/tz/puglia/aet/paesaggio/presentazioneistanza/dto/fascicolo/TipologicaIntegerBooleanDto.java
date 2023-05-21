package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo;

public class TipologicaIntegerBooleanDto {

	private Integer intero;
	private Boolean booleano;
	
	
	public Integer getIntero() {
		return intero;
	}
	public void setIntero(Integer intero) {
		this.intero = intero;
	}
	public Boolean getBooleano() {
		return booleano;
	}
	public void setBooleano(Boolean booleano) {
		this.booleano = booleano;
	}
	
	@Override
	public String toString() {
		return "TiplogicaIntegerBooleanDto [intero=" + intero + ", booleano=" + booleano + "]";
	}
	
}