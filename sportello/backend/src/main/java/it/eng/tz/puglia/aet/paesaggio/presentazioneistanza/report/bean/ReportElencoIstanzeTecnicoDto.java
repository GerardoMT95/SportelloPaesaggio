package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportElencoIstanzeTecnicoDto implements Serializable {

	
	private static final long serialVersionUID = -246334896717260656L;
	
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String mail;
	private String pec;
	/**
	 * @return the nome
	 */
	public String getNome() {
		return this.nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(final String nome) {
		this.nome = nome;
	}
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return this.cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(final String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}
	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(final String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return this.mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}
	/**
	 * @return the pec
	 */
	public String getPec() {
		return this.pec;
	}
	/**
	 * @param pec the pec to set
	 */
	public void setPec(final String pec) {
		this.pec = pec;
	}
	
}
