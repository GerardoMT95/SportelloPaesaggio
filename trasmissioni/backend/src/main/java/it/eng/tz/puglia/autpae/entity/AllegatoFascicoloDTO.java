package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

/**
 * DTO for table public.allegato_fascicolo
 */
public class AllegatoFascicoloDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idAllegato;
	private TipoAllegato type;
	private Long idFascicolo;
	
	public AllegatoFascicoloDTO() { }
	
	public AllegatoFascicoloDTO(Long idAllegato, TipoAllegato type, Long idFascicolo) {
		this.idAllegato = idAllegato;
		this.type = type;
		this.idFascicolo = idFascicolo;
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
		result = prime * result + ((idAllegato == null) ? 0 : idAllegato.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
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
		AllegatoFascicoloDTO other = (AllegatoFascicoloDTO) obj;
		if (idAllegato == null) {
			if (other.idAllegato != null)
				return false;
		} else if (!idAllegato.equals(other.idAllegato))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoDTO [idAllegato=" + idAllegato + ", type=" + type + ", idFascicolo=" + idFascicolo + "]";
	}
	
}
