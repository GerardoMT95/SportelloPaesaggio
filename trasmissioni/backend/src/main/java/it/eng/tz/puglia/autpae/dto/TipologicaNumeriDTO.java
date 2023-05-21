package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

public class TipologicaNumeriDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private Long  codice;
	private String label;
	
	
	public TipologicaNumeriDTO() { }

	
	public TipologicaNumeriDTO(Long codice, String label) {
		this.codice = codice;
		this.label  = label;
	}

	
	public Long getCodice() {
		return codice;
	}
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipologicaNumeriDTO other = (TipologicaNumeriDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipologicaNumeriDTO [codice=" + codice + ", label=" + label + "]";
	}

}
