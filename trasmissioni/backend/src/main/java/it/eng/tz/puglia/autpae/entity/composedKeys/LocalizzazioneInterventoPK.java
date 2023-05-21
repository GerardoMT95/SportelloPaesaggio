package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class LocalizzazioneInterventoPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long praticaId;
	private Long comuneId;
	
	public LocalizzazioneInterventoPK() { }

	public LocalizzazioneInterventoPK(Long praticaId, Long comuneId) {
		this.praticaId = praticaId;
		this.comuneId = comuneId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comuneId == null) ? 0 : comuneId.hashCode());
		result = prime * result + ((praticaId == null) ? 0 : praticaId.hashCode());
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
		LocalizzazioneInterventoPK other = (LocalizzazioneInterventoPK) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "LocalizzazioneInterventoPK [praticaId=" + praticaId + ", comuneId=" + comuneId + "]";
	}

}