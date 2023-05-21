package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;

public class InfoMultitipoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5249010165874539293L;

	private Long idFascicolo;
	private List<TipoAllegato> tipiAllegato;
	public Long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public List<TipoAllegato> getTipiAllegato() {
		return tipiAllegato;
	}
	public void setTipiAllegato(List<TipoAllegato> tipiAllegato) {
		this.tipiAllegato = tipiAllegato;
	}
	

}
