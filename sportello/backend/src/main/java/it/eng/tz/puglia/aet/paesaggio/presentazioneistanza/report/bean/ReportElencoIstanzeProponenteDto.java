package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean;

import java.io.Serializable;

public class ReportElencoIstanzeProponenteDto implements Serializable {

	
	private static final long serialVersionUID = 818636385335714982L;

	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String mail;
	private String pec;
	private String codiceFiscaleAziendaEnte;
	private String azienda;
	private String ente;
	private String cIpa;
	private String partitaIva;
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
	/**
	 * @return the codiceFiscaleAziendaEnte
	 */
	public String getCodiceFiscaleAziendaEnte() {
		return this.codiceFiscaleAziendaEnte;
	}
	/**
	 * @param codiceFiscaleAziendaEnte the codiceFiscaleAziendaEnte to set
	 */
	public void setCodiceFiscaleAziendaEnte(final String codiceFiscaleAziendaEnte) {
		this.codiceFiscaleAziendaEnte = codiceFiscaleAziendaEnte;
	}
	/**
	 * @return the azienda
	 */
	public String getAzienda() {
		return this.azienda;
	}
	/**
	 * @param azienda the azienda to set
	 */
	public void setAzienda(final String azienda) {
		this.azienda = azienda;
	}
	/**
	 * @return the ente
	 */
	public String getEnte() {
		return this.ente;
	}
	/**
	 * @param ente the ente to set
	 */
	public void setEnte(final String ente) {
		this.ente = ente;
	}
	/**
	 * @return the cIpa
	 */
	public String getcIpa() {
		return this.cIpa;
	}
	/**
	 * @param cIpa the cIpa to set
	 */
	public void setcIpa(final String cIpa) {
		this.cIpa = cIpa;
	}
	/**
	 * @return the partitaIva
	 */
	public String getPartitaIva() {
		return this.partitaIva;
	}
	/**
	 * @param partitaIva the partitaIva to set
	 */
	public void setPartitaIva(final String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
}
