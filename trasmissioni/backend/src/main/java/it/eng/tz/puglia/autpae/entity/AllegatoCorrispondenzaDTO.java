package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

/**
 * DTO for table public.allegato_corrispondenza
 */
public class AllegatoCorrispondenzaDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idAllegato;
	private Long idCorrispondenza;
	private boolean isUrl; //true se è stato inviato tramite url nel corpo della mail e non direttamente come allegato.
	
	public AllegatoCorrispondenzaDTO() { }

	public AllegatoCorrispondenzaDTO(Long idAllegato, Long idCorrispondenza) {
		this.idAllegato = idAllegato;
		this.idCorrispondenza = idCorrispondenza;
	}
	
	public AllegatoCorrispondenzaDTO(Long idAllegato, Long idCorrispondenza,boolean isUrl) {
		this(idAllegato,idCorrispondenza);
		this.isUrl=isUrl;
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
		AllegatoCorrispondenzaDTO other = (AllegatoCorrispondenzaDTO) obj;
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
		return "AllegatoCorrispondenzaDTO [idAllegato=" + idAllegato + ", idCorrispondenza=" + idCorrispondenza + "]";
	}

	public boolean getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(boolean isUrl) {
		this.isUrl = isUrl;
	}

}
