package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class ParchiPaesaggiImmobiliPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long praticaId;
	private Long comuneId;
	private String tipoVincolo;
	private String codice;
	
	public ParchiPaesaggiImmobiliPK() { }

	public ParchiPaesaggiImmobiliPK(Long praticaId, Long comuneId, String tipoVincolo, String codice) {
		this.praticaId = praticaId;
		this.comuneId = comuneId;
		this.tipoVincolo = tipoVincolo;
		this.codice = codice;
	}

	public Long getPraticaId() {
		return praticaId;
	}

	public void setPraticaId(Long praticaId) {
		this.praticaId = praticaId;
	}

	public Long getComuneId() {
		return comuneId;
	}

	public void setComuneId(Long comuneId) {
		this.comuneId = comuneId;
	}

	public String getTipoVincolo() {
		return tipoVincolo;
	}

	public void setTipoVincolo(String tipoVincolo) {
		this.tipoVincolo = tipoVincolo;
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
		result = prime * result + ((comuneId == null) ? 0 : comuneId.hashCode());
		result = prime * result + ((praticaId == null) ? 0 : praticaId.hashCode());
		result = prime * result + ((tipoVincolo == null) ? 0 : tipoVincolo.hashCode());
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
		ParchiPaesaggiImmobiliPK other = (ParchiPaesaggiImmobiliPK) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (comuneId == null) {
			if (other.comuneId != null)
				return false;
		} else if (!comuneId.equals(other.comuneId))
			return false;
		if (praticaId == null) {
			if (other.praticaId != null)
				return false;
		} else if (!praticaId.equals(other.praticaId))
			return false;
		if (tipoVincolo == null) {
			if (other.tipoVincolo != null)
				return false;
		} else if (!tipoVincolo.equals(other.tipoVincolo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParchiPaesaggiImmobiliPK [praticaId=" + praticaId + ", comuneId=" + comuneId + ", tipoVincolo="
				+ tipoVincolo + ", codice=" + codice + "]";
	}

}