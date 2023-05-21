package it.eng.tz.puglia.servizi_esterni.profileManager.dto;

import java.io.Serializable;

public class EnteResponseBean implements Serializable {

	private static final long serialVersionUID = 8158745069839306496L;
	
	private Long   id;
	private String codiceGruppo;
	private String name;		  //e' il nome del gruppo...
	private String denominazione; //e' il nome dell'ente da common.ente 
//	private String codIstat;
//	private String codiceEnte;

	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getCodIstat() {
//		return codIstat;
//	}
//	public void setCodIstat(String codIstat) {
//		this.codIstat = codIstat;
//	}
//	public String getCodiceEnte() {
//		return codiceEnte;
//	}
//	public void setCodiceEnte(String codiceEnte) {
//		this.codiceEnte = codiceEnte;
//	}
	public String getCodiceGruppo() {
		return codiceGruppo;
	}
	public void setCodiceGruppo(String codiceGruppo) {
		this.codiceGruppo = codiceGruppo;
	}

	
	@Override
	public String toString() {
		return "EnteResponseBean [id=" + id + ", codiceGruppo=" + codiceGruppo + ", name=" + name + "]";
	}
	
}