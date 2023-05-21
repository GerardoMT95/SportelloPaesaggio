package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo;

public class SelectOptionDto {

	private String value;
	private String description;
	private String linked;
	private boolean privato;
	public String getValue() {
		return this.value;
	}
	public void setValue(final String value) {
		this.value = value;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public String getLinked() {
		return this.linked;
	}
	public void setLinked(final String linked) {
		this.linked = linked;
	}
	/**
	 * @return the privato
	 */
	public boolean isPrivato() {
		return this.privato;
	}
	/**
	 * @param privato the privato to set
	 */
	public void setPrivato(final boolean privato) {
		this.privato = privato;
	}

	
}
