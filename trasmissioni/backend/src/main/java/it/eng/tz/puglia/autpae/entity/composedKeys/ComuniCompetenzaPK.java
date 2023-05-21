package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class ComuniCompetenzaPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long praticaId;
	private Long enteId;
	private boolean creazione;
	
	
	public ComuniCompetenzaPK() { }

	public ComuniCompetenzaPK(Long praticaId, Long enteId, boolean creazione) {
		this.praticaId = praticaId;
		this.enteId = enteId;
		this.creazione = creazione;
	}

	
	public Long getPraticaId() {
		return praticaId;
	}

	public void setPraticaId(Long praticaId) {
		this.praticaId = praticaId;
	}

	public Long getEnteId() {
		return enteId;
	}

	public void setEnteId(Long enteId) {
		this.enteId = enteId;
	}

	public boolean isCreazione() {
		return creazione;
	}

	public void setCreazione(boolean creazione) {
		this.creazione = creazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (creazione ? 1231 : 1237);
		result = prime * result + ((enteId == null) ? 0 : enteId.hashCode());
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
		ComuniCompetenzaPK other = (ComuniCompetenzaPK) obj;
		if (creazione != other.creazione)
			return false;
		if (enteId == null) {
			if (other.enteId != null)
				return false;
		} else if (!enteId.equals(other.enteId))
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
		return "ComuniCompetenzaPK [praticaId=" + praticaId + ", enteId=" + enteId + ", creazione=" + creazione + "]";
	}

}