package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

/**
 * DTO for table public.fascicolo_corrispondenza
 */
public class FascicoloCorrispondenzaDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private Long idCorrispondenza;
	private Long idFascicolo;
	
	public FascicoloCorrispondenzaDTO() { }

	public FascicoloCorrispondenzaDTO(Long id, Long idCorrispondenza, Long idFascicolo) {
		this.id = id;
		this.idCorrispondenza = idCorrispondenza;
		this.idFascicolo = idFascicolo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
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
		FascicoloCorrispondenzaDTO other = (FascicoloCorrispondenzaDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
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
		return "FascicoloCorrispondenzaDTO [id=" + id + ", idCorrispondenza=" + idCorrispondenza + ", idFascicolo=" + idFascicolo + "]";
	}

}
