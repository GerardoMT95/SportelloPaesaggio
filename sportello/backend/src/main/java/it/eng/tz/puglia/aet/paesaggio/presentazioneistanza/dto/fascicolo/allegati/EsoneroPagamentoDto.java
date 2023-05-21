package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati;

import java.io.Serializable;


public class EsoneroPagamentoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean esoneroOneri;
	private Boolean esoneroBollo;
	/**
	 * @return the esoneroOneri
	 */
	public Boolean getEsoneroOneri() {
		return esoneroOneri;
	}
	/**
	 * @param esoneroOneri the esoneroOneri to set
	 */
	public void setEsoneroOneri(Boolean esoneroOneri) {
		this.esoneroOneri = esoneroOneri;
	}
	/**
	 * @return the esoneroBollo
	 */
	public Boolean getEsoneroBollo() {
		return esoneroBollo;
	}
	/**
	 * @param esoneroBollo the esoneroBollo to set
	 */
	public void setEsoneroBollo(Boolean esoneroBollo) {
		this.esoneroBollo = esoneroBollo;
	}
	

}
