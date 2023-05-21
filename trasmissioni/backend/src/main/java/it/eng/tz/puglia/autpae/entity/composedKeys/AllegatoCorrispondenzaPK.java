package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class AllegatoCorrispondenzaPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long idAllegato;
	private Long idCorrispondenza;
	
	public AllegatoCorrispondenzaPK() { }

	public AllegatoCorrispondenzaPK(Long idAllegato, Long idCorrispondenza) {
		this.idAllegato = idAllegato;
		this.idCorrispondenza = idCorrispondenza;
	}

	public Long getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}

	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
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
		AllegatoCorrispondenzaPK other = (AllegatoCorrispondenzaPK) obj;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoCorrispondenzaPK [idAllegato=" + idAllegato + ", idCorrispondenza=" + idCorrispondenza + "]";
	}

}
