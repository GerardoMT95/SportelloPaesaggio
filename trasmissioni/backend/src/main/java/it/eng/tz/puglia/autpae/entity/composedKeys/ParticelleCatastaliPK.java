package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class ParticelleCatastaliPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long praticaId;
	private Long comuneId;
	private Integer id;
	
	
	public ParticelleCatastaliPK() { }

	public ParticelleCatastaliPK(Long praticaId, Long comuneId, Integer id) {
		this.praticaId = praticaId;
		this.comuneId = comuneId;
		this.id = id;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comuneId == null) ? 0 : comuneId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ParticelleCatastaliPK other = (ParticelleCatastaliPK) obj;
		if (comuneId == null) {
			if (other.comuneId != null)
				return false;
		} else if (!comuneId.equals(other.comuneId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "LocalizzazioneInterventoPK [praticaId=" + praticaId + ", comuneId=" + comuneId + ", id=" + id + "]";
	}
	
}