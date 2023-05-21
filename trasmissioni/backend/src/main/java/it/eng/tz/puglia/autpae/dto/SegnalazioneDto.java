package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

public class SegnalazioneDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6952129718760874344L;
	private String nome;
	private String cognome;
	private String username;
	private String notifica;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNotifica() {
		return notifica;
	}
	public void setNotifica(String notifica) {
		this.notifica = notifica;
	}
	
}
