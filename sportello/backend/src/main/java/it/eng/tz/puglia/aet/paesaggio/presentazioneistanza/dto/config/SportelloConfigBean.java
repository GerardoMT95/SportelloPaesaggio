package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config;

import java.io.Serializable;

/**
 * bean di configurazioni lato sportello
 * per aggiungere altre ricordarsi
 * @author acolaianni
 *
 */
public class SportelloConfigBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String esoneroOneriLabel;
	private String esoneroBolloLabel;
	private int giorniScadenzaPagamento;
	/**
	 * @return the esoneroOneriLabel
	 */
	public String getEsoneroOneriLabel() {
		return esoneroOneriLabel;
	}
	/**
	 * @param esoneroOneriLabel the esoneroOneriLabel to set
	 */
	public void setEsoneroOneriLabel(String esoneroOneriLabel) {
		this.esoneroOneriLabel = esoneroOneriLabel;
	}
	/**
	 * @return the esoneroBolloLabel
	 */
	public String getEsoneroBolloLabel() {
		return esoneroBolloLabel;
	}
	/**
	 * @param esoneroBolloLabel the esoneroBolloLabel to set
	 */
	public void setEsoneroBolloLabel(String esoneroBolloLabel) {
		this.esoneroBolloLabel = esoneroBolloLabel;
	}

	/**
	 * @return the giorniScadenzaPagamento
	 */
	public int getGiorniScadenzaPagamento() {
		return giorniScadenzaPagamento;
	}
	
	/**
	 * @param giorniScadenzaPagamento the giorniScadenzaPagamento to set
	 */
	public void setGiorniScadenzaPagamento(int giorniScadenzaPagamento) {
		this.giorniScadenzaPagamento = giorniScadenzaPagamento;
	}
	
	
	@Override
	public String toString() {
		return "SportelloConfigBean [esoneroOneriLabel=" + esoneroOneriLabel + ", esoneroBolloLabel="
				+ esoneroBolloLabel + ", giorniScadenza=" + giorniScadenzaPagamento + "]";
	}
	
	


}
