package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportMediaEntiCoinvoltiDto implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 */
	private static final long serialVersionUID = 4701301071554609686L;
	
	private String nomeFamiglia;
	private String nomeProcedimenti;
	private Double avgEntiCoinvolti;

	/**
	 * @return the nomeFamiglia
	 */
	public String getNomeFamiglia() {
		return this.nomeFamiglia;
	}
	/**
	 * @param nomeFamiglia the nomeFamiglia to set
	 */
	public void setNomeFamiglia(final String nomeFamiglia) {
		this.nomeFamiglia = nomeFamiglia;
	}
	/**
	 * @return the nomeProcedimenti
	 */
	public String getNomeProcedimenti() {
		return this.nomeProcedimenti;
	}
	/**
	 * @param nomeProcedimenti the nomeProcedimenti to set
	 */
	public void setNomeProcedimenti(final String nomeProcedimenti) {
		this.nomeProcedimenti = nomeProcedimenti;
	}
	/**
	 * @return the avgEntiCoinvolti
	 */
	public Double getAvgEntiCoinvolti() {
		return this.avgEntiCoinvolti;
	}
	/**
	 * @param avgEntiCoinvolti the avgEntiCoinvolti to set
	 */
	public void setAvgEntiCoinvolti(final Double avgEntiCoinvolti) {
		this.avgEntiCoinvolti = avgEntiCoinvolti;
	}
	
}
