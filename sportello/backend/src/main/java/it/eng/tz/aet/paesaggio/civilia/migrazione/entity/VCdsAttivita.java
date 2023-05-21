package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.io.Serializable;
import java.util.Objects;

public class VCdsAttivita implements Serializable{

	/**
	 * @author Alessio Bottalico
	 * @date 22 lug 2022
	 */
	private static final long serialVersionUID = -3179009002641768030L;
	
	private String value;
	private String label;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(label, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VCdsAttivita other = (VCdsAttivita) obj;
		return Objects.equals(label, other.label) && Objects.equals(value, other.value);
	}
	
}
