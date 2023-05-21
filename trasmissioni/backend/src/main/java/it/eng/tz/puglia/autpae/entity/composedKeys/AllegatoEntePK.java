package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class AllegatoEntePK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long idAllegato;
	private String codice;
	
	public AllegatoEntePK() { }

	public AllegatoEntePK(Long idAllegato, String codice) {
		this.idAllegato = idAllegato;
		this.codice = codice;
	}

	public Long getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
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
		AllegatoEntePK other = (AllegatoEntePK) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoEntePK [idAllegato=" + idAllegato + ", codice=" + codice + "]";
	}

}
