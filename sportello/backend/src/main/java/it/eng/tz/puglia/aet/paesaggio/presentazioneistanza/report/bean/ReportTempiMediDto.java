package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportTempiMediDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = 180367069040112117L;
	

	private Long avgAssegnazione;
	private Long avgAvvio;
	private Long avgConclusione;
	/**
	 * @return the avgAssegnazione
	 */
	public Long getAvgAssegnazione() {
		return this.avgAssegnazione;
	}
	/**
	 * @param avgAssegnazione the avgAssegnazione to set
	 */
	public void setAvgAssegnazione(final Long avgAssegnazione) {
		this.avgAssegnazione = avgAssegnazione;
	}
	/**
	 * @return the avgAvvio
	 */
	public Long getAvgAvvio() {
		return this.avgAvvio;
	}
	/**
	 * @param avgAvvio the avgAvvio to set
	 */
	public void setAvgAvvio(final Long avgAvvio) {
		this.avgAvvio = avgAvvio;
	}
	/**
	 * @return the avgConclusione
	 */
	public Long getAvgConclusione() {
		return this.avgConclusione;
	}
	/**
	 * @param avgConclusione the avgConclusione to set
	 */
	public void setAvgConclusione(final Long avgConclusione) {
		this.avgConclusione = avgConclusione;
	}
}
