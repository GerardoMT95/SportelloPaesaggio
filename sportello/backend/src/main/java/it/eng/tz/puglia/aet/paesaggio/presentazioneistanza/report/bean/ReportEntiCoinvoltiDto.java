package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportEntiCoinvoltiDto implements Serializable{
	
	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 */
	private static final long serialVersionUID = 3316412978997272889L;
	
	private String nomeEnte;
	private Long numero;
	/**
	 * @return the nomeEnte
	 */
	public String getNomeEnte() {
		return this.nomeEnte;
	}
	/**
	 * @param nomeEnte the nomeEnte to set
	 */
	public void setNomeEnte(final String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}
	/**
	 * @return the numero
	 */
	public Long getNumero() {
		return this.numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(final Long numero) {
		this.numero = numero;
	}

}
