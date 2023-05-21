package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class FascicoloInterventoPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long idFascicolo;
	private String idTipiQualificazioni;
	
	public FascicoloInterventoPK() { }
	
	public FascicoloInterventoPK(Long idFascicolo, String idTipiQualificazioni) {
		this.idFascicolo = idFascicolo;
		this.idTipiQualificazioni = idTipiQualificazioni;
	}
	
	public Long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public String getIdTipiQualificazioni() {
		return idTipiQualificazioni;
	}
	public void setIdTipiQualificazioni(String idTipiQualificazioni) {
		this.idTipiQualificazioni = idTipiQualificazioni;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((idTipiQualificazioni == null) ? 0 : idTipiQualificazioni.hashCode());
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
		FascicoloInterventoPK other = (FascicoloInterventoPK) obj;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (idTipiQualificazioni == null) {
			if (other.idTipiQualificazioni != null)
				return false;
		} else if (!idTipiQualificazioni.equals(other.idTipiQualificazioni))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FascicoloInterventoPK [idFascicolo=" + idFascicolo + ", idTipiQualificazioni=" + idTipiQualificazioni
				+ "]";
	}
	
}
