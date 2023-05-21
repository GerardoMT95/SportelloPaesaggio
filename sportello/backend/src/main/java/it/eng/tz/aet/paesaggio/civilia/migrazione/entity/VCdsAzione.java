package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Objects;

public class VCdsAzione {
	
	private String value;
	private String attivita;
	private String label;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(attivita, label, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VCdsAzione other = (VCdsAzione) obj;
		return Objects.equals(attivita, other.attivita) && Objects.equals(label, other.label)
				&& Objects.equals(value, other.value);
	}
	
	

}
