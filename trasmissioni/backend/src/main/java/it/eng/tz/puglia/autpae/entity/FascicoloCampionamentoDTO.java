package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

public class FascicoloCampionamentoDTO implements Serializable {

	private static final long serialVersionUID = -9006909330847718297L;
	
	private Long idCampionamento;
	private Long idFascicolo;

	
	public FascicoloCampionamentoDTO() { }
	
	
	public FascicoloCampionamentoDTO(Long idCampionamento, Long idFascicolo) {
		this.idCampionamento = idCampionamento;
		this.idFascicolo = idFascicolo;
	}


	public Long getIdCampionamento() {
		return idCampionamento;
	}

	public void setIdCampionamento(Long idCampionamento) {
		this.idCampionamento = idCampionamento;
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
		result = prime * result + ((idCampionamento == null) ? 0 : idCampionamento.hashCode());
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
		FascicoloCampionamentoDTO other = (FascicoloCampionamentoDTO) obj;
		if (idCampionamento == null) {
			if (other.idCampionamento != null)
				return false;
		} else if (!idCampionamento.equals(other.idCampionamento))
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
		return "FascicoloCampionamentoDTO [idCampionamento=" + idCampionamento + ", idFascicolo=" + idFascicolo + "]";
	}
	
}
