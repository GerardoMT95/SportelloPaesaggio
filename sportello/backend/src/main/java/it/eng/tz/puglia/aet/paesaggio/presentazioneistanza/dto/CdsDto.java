package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;


import java.io.Serializable;

public class CdsDto implements Serializable{
	
	/**
	 * @author Simone Verna
	 * @date 25 lug 2022
	 */
	private static final long serialVersionUID = -3416592819573108791L;
	
	private Long id;
	private String riferimentoIstanza;
	private String stato;
	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}
	/**
	 * @return the riferimentoIstanza
	 */
	public String getRiferimentoIstanza() {
		return this.riferimentoIstanza;
	}
	/**
	 * @param riferimentoIstanza the riferimentoIstanza to set
	 */
	public void setRiferimentoIstanza(final String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}
	/**
	 * @return the stato
	 */
	public String getStato() {
		return this.stato;
	}
	/**
	 * @param stato the stato to set
	 */
	public void setStato(final String stato) {
		this.stato = stato;
	}

}