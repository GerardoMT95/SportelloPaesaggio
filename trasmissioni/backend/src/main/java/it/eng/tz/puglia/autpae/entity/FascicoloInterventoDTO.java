package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

public class FascicoloInterventoDTO implements Serializable {

	private static final long serialVersionUID = 2641135800489877231L;

	private Long idFascicolo;
	private Long idTipiQualificazioni;

	public FascicoloInterventoDTO() { }

	public FascicoloInterventoDTO(Long idFascicolo, Long idTipiQualificazioni) {
		super();
		this.idFascicolo = idFascicolo;
		this.idTipiQualificazioni = idTipiQualificazioni;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public Long getIdTipiQualificazioni() {
		return idTipiQualificazioni;
	}

	public void setIdTipiQualificazioni(Long idTipiQualificazioni) {
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
		FascicoloInterventoDTO other = (FascicoloInterventoDTO) obj;
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
		return "FascicoloInterventoDTO [idFascicolo=" + idFascicolo + ", idTipiQualificazioni=" + idTipiQualificazioni + "]";
	}

}
