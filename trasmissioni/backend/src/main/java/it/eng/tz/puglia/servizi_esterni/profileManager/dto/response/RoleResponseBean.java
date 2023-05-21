package it.eng.tz.puglia.servizi_esterni.profileManager.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RoleResponseBean implements Serializable {

	private static final long serialVersionUID = -695916811071760887L;

	private Integer id;
	private String name;
	private String codice;
	@JsonIgnore
	private String codiceEnte;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCodiceEnte() {
		return codiceEnte;
	}

	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	@Override
	public String toString() {
		return "RoleResponseBean [id=" + id + ", name=" + name + ", codice=" + codice + ", codiceEnte=" + codiceEnte
				+ "]";
	}

}
