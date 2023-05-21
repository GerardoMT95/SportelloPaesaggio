package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class ParcoPaesaggioAreaPK implements Serializable
{
	private static final long serialVersionUID = 2102844356184310124L;
	
	private String codice;
	private Long idFascicolo;
	
	
	public ParcoPaesaggioAreaPK() { }
	
	public ParcoPaesaggioAreaPK(String codice, Long idFascicolo) {
		this.codice = codice;
		this.idFascicolo = idFascicolo;
	}
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
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
		ParcoPaesaggioAreaPK other = (ParcoPaesaggioAreaPK) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ParcoPaesaggioAreaPK [codice=" + codice + ", idFascicolo=" + idFascicolo + "]";
	}

}
