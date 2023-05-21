package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;


public class SelectParentItemDto extends SelectItemDto{


	/**
	 * @author Antonio La Gatta
	 * @date 29 giu 2021
	 */
	private static final long serialVersionUID = -6905720953675135120L;
	
	private String parent;

	/**
	 * @return the parent
	 */
	public String getParent() {
		return this.parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(final String parent) {
		this.parent = parent;
	}
}
