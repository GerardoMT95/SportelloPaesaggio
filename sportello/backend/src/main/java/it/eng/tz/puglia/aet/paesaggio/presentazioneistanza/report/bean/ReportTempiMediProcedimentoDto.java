package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportTempiMediProcedimentoDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 3 mag 2022
	 */
	private static final long serialVersionUID = 180367069040112117L;
	
	private String procedimento;
	private Long avgAssegnazione;
	private Long avgAvvio;
	private Long avgConclusione;
	/**
	 * @return the procedimento
	 */
	public String getProcedimento() {
		return this.procedimento;
	}
	/**
	 * @param procedimento the procedimento to set
	 */
	public void setProcedimento(final String procedimento) {
		this.procedimento = procedimento;
	}
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
