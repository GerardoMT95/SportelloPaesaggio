package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean;

import java.io.Serializable;

public class ProtocollazioneBean implements Serializable {

	private static final long serialVersionUID = 7516934191851656665L;
	
	private String idPratica;
	private String username;
	private String ruolo;
	private int enteDelegato;
	
	public String getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public int getEnteDelegato() {
		return enteDelegato;
	}
	public void setEnteDelegato(int enteDelegato) {
		this.enteDelegato = enteDelegato;
	}
	
}
