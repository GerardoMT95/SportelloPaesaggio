package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

public class PlaceholderDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String info;
	
	
	public PlaceholderDTO() { }
	
	public PlaceholderDTO(String codice, String info) {
		this.codice = codice;
		this.info 	= info;
	}
	
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
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
		PlaceholderDTO other = (PlaceholderDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PlaceholderDocDTO [codice=" + codice + ", info=" + info + "]";
	}
	
}