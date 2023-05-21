package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

public class ConfigurazioneCasellaPostaleDTO implements Serializable {

	private static final long serialVersionUID = 5239954062116311651L;
	
	private String email;
	private String configurazione;
	
	public ConfigurazioneCasellaPostaleDTO() {
		super();
	}
	public ConfigurazioneCasellaPostaleDTO(String email, String configurazione) {
		super();
		this.email = email;
		this.configurazione = configurazione;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getConfigurazione() {
		return configurazione;
	}
	public void setConfigurazione(String configurazione) {
		this.configurazione = configurazione;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((configurazione == null) ? 0 : configurazione.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigurazioneCasellaPostaleDTO other = (ConfigurazioneCasellaPostaleDTO) obj;
		if (configurazione == null) {
			if (other.configurazione != null)
				return false;
		} else if (!configurazione.equals(other.configurazione))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ConfigurazioneCasellaPostaleDTO [email=" + email + ", configurazione=" + configurazione + "]";
	}
	
}
