package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

public class SelectItemDto implements Serializable, Comparable<SelectItemDto>{


	/**
	 * @author Antonio La Gatta
	 * @date 29 giu 2021
	 */
	private static final long serialVersionUID = -6905720953675135120L;
	
	
	private String label;
	private String value;
	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 1 dic 2021
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final SelectItemDto o) {
		if(o.label == null && this.label == null)
			return 0;
		if(o.label == null && this.label != null)
			return 1;
		if(o.label != null && this.label == null)
			return -1;
		return this.label.compareTo(o.label);
	}
}

