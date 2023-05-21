package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

public class PlainStringUtentiValueLabel extends PlainStringValueLabel {

	private static final long serialVersionUID = 1L;
	
	
	private boolean rup;

	
	public PlainStringUtentiValueLabel() { }


	public boolean isRup() {
		return rup;
	}
	public void setRup(boolean rup) {
		this.rup = rup;
	}

	@Override
	public String toString() {
		return super.toString() + "[ rup=" + rup + "]";
	}
	
}