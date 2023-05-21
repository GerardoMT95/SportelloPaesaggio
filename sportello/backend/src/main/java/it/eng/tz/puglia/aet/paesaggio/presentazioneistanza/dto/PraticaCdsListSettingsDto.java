package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

public class PraticaCdsListSettingsDto implements Serializable{

	
	/**
	 * @author Simone Verna
	 * @date 25 lug 2022
	 */
	private static final long serialVersionUID = 9025307760603166455L;
	private String id;
	private Long idCds;
	private String tipo;
	private String attivita;
	private String azione;
	private Boolean completed;
	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}
	/**
	 * @return the idCds
	 */
	public Long getIdCds() {
		return this.idCds;
	}
	/**
	 * @param idCds the idCds to set
	 */
	public void setIdCds(final Long idCds) {
		this.idCds = idCds;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return this.tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(final String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the attivita
	 */
	public String getAttivita() {
		return this.attivita;
	}
	/**
	 * @param attivita the attivita to set
	 */
	public void setAttivita(final String attivita) {
		this.attivita = attivita;
	}
	/**
	 * @return the azione
	 */
	public String getAzione() {
		return this.azione;
	}
	/**
	 * @param azione the azione to set
	 */
	public void setAzione(final String azione) {
		this.azione = azione;
	}
	/**
	 * @return the completed
	 */
	public Boolean getCompleted() {
		return this.completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(final Boolean completed) {
		this.completed = completed;
	}
}
