package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.io.Serializable;

public class JasperCheckBox implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean isChecked;
	private String label;
	/**
	 * @return the isChecked
	 */
	public Boolean getIsChecked() {
		return isChecked;
	}
	/**
	 * @param isChecked the isChecked to set
	 */
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

}
