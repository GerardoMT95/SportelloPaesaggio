package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

public class AllegatoFascicoloPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long idAllegato;
	private TipoAllegato type;
	private Long idFascicolo;
	
	
	public AllegatoFascicoloPK() { }

	public AllegatoFascicoloPK(Long idAllegato, TipoAllegato type) {
		this.idAllegato = idAllegato;
		this.type = type;
	}

	
	public Long getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}

	public TipoAllegato getType() {
		return type;
	}

	public void setType(TipoAllegato type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AllegatoFascicoloPK other = (AllegatoFascicoloPK) obj;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoFascicoloPK [idAllegato=" + idAllegato + ", type=" + type + "]";
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

}