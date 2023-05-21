package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

public class ReferenteFascicoloSearch extends BaseSearchRequestBean {

	private static final long serialVersionUID = 1934201649996417644L;
	
	// COLUMN id
	private UUID id;
	// COLUMN nome
	private String nome;
	// COLUMN cognome
	private String cognome;
	// COLUMN pec
	private String pec;
	// COLUMN mail
	private String mail;
	// COLUMN codice_fiscale
	private String codiceFiscale;
	// COLUMN tipo_referente
	private String tipoReferente;

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPec() {
		return this.pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getTipoReferente() {
		return this.tipoReferente;
	}

	public void setTipoReferente(String tipoReferente) {
		this.tipoReferente = tipoReferente;
	}

}
