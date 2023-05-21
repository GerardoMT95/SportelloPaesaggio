package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import it.eng.tz.puglia.util.json.DateSerializer;
//import it.eng.tz.puglia.util.json.DateSqlDeserializer;

public class RichiestaAbilitazioneDto implements Serializable {

	private static final long serialVersionUID = 2791986527397575891L;
	/**
	 * 
	 */
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	private String fax;
	private String capoNome;
	private String capoCognome;
	private String capoEmail;
	private String capoTelefono;
	private String organizzazione;
	private String ruolo;
	private String inQualitaDi;
	private Boolean privacyAccettato;
	private Boolean dichMendaciAccettato;
	private String privacy;
	private String dichMendaci;
	
	
	public Boolean getPrivacyAccettato() {
		return privacyAccettato;
	}
	public void setPrivacyAccettato(Boolean privacyAccettato) {
		this.privacyAccettato = privacyAccettato;
	}
	public Boolean getDichMendaciAccettato() {
		return dichMendaciAccettato;
	}
	public void setDichMendaciAccettato(Boolean dichMendaciAccettato) {
		this.dichMendaciAccettato = dichMendaciAccettato;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	public String getDichMendaci() {
		return dichMendaci;
	}
	public void setDichMendaci(String dichMendaci) {
		this.dichMendaci = dichMendaci;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCapoNome() {
		return capoNome;
	}
	public void setCapoNome(String capoNome) {
		this.capoNome = capoNome;
	}
	public String getCapoCognome() {
		return capoCognome;
	}
	public void setCapoCognome(String capoCognome) {
		this.capoCognome = capoCognome;
	}
	public String getCapoEmail() {
		return capoEmail;
	}
	public void setCapoEmail(String capoEmail) {
		this.capoEmail = capoEmail;
	}
	public String getCapoTelefono() {
		return capoTelefono;
	}
	public void setCapoTelefono(String capoTelefono) {
		this.capoTelefono = capoTelefono;
	}
	public String getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getInQualitaDi() {
		return inQualitaDi;
	}
	public void setInQualitaDi(String inQualitaDi) {
		this.inQualitaDi = inQualitaDi;
	}
	

}