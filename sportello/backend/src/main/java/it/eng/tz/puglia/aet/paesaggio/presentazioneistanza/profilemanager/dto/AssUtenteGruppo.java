package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;

public class AssUtenteGruppo implements Serializable{

	/**
	 * @author Antonio La Gatta
	 * @date 8 ott 2020
	 */
	private static final long serialVersionUID = 2394339737709504921L;

	private String username;
	private String nome;
	private String cognome;
	private String mail;
	private String codiceGruppo;
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the codiceGruppo
	 */
	public String getCodiceGruppo() {
		return codiceGruppo;
	}
	/**
	 * @param codiceGruppo the codiceGruppo to set
	 */
	public void setCodiceGruppo(String codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}
	
}
