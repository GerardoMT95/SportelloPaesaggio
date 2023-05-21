package it.eng.tz.puglia.servizi_esterni.profileManager.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayloadUserResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String cognome;
	private String username;
	private List<String> ruoliIam = null;
	private List<PM_GruppiRuoli> gruppiRuoli = null;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	
	public PayloadUserResponse() {
	}

	public PayloadUserResponse(String nome, String cognome, String username, List<String> ruoliIam, List<PM_GruppiRuoli> gruppiRuoli,
			Map<String, Object> additionalProperties) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.ruoliIam = ruoliIam;
		this.gruppiRuoli = gruppiRuoli;
		this.additionalProperties = additionalProperties;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRuoliIam() {
		return ruoliIam;
	}

	public void setRuoliIam(List<String> ruoliIam) {
		this.ruoliIam = ruoliIam;
	}

	public List<PM_GruppiRuoli> getGruppiRuoli() {
		return gruppiRuoli;
	}

	public void setGruppiRuoli(List<PM_GruppiRuoli> gruppiRuoli) {
		this.gruppiRuoli = gruppiRuoli;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalProperties == null) ? 0 : additionalProperties.hashCode());
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((gruppiRuoli == null) ? 0 : gruppiRuoli.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ruoliIam == null) ? 0 : ruoliIam.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		PayloadUserResponse other = (PayloadUserResponse) obj;
		if (additionalProperties == null) {
			if (other.additionalProperties != null)
				return false;
		} else if (!additionalProperties.equals(other.additionalProperties))
			return false;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (gruppiRuoli == null) {
			if (other.gruppiRuoli != null)
				return false;
		} else if (!gruppiRuoli.equals(other.gruppiRuoli))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (ruoliIam == null) {
			if (other.ruoliIam != null)
				return false;
		} else if (!ruoliIam.equals(other.ruoliIam))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payload [nome=" + nome + ", cognome=" + cognome + ", username=" + username + ", ruoliIam=" + ruoliIam
				+ ", gruppiRuoli=" + gruppiRuoli + ", additionalProperties=" + additionalProperties + "]";
	}
	
}
