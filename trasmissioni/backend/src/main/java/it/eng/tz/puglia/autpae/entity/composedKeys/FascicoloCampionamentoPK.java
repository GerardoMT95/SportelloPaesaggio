package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

public class FascicoloCampionamentoPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private Long idFascicolo;
	private Long idCampionamento;
	
	public FascicoloCampionamentoPK() { }

	public FascicoloCampionamentoPK(Long idFascicolo, Long idCampionamento) {
		this.idFascicolo = idFascicolo;
		this.idCampionamento = idCampionamento;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public Long getIdCampionamento() {
		return idCampionamento;
	}

	public void setIdCorrispondenza(Long idCampionamento) {
		this.idCampionamento = idCampionamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((idCampionamento == null) ? 0 : idCampionamento.hashCode());
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
		FascicoloCampionamentoPK other = (FascicoloCampionamentoPK) obj;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (idCampionamento == null) {
			if (other.idCampionamento != null)
				return false;
		} else if (!idCampionamento.equals(other.idCampionamento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FascicoloCorrispondenzaPK [idFascicolo=" + idFascicolo + ", idCampionamento=" + idCampionamento + "]";
	}

}
