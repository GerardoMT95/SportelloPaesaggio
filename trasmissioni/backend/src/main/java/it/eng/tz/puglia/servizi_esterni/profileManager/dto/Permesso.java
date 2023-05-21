package it.eng.tz.puglia.servizi_esterni.profileManager.dto;

import java.io.Serializable;

public class Permesso implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nomePermesso;
	private String descrizionePermesso;
	private String codicePermesso;
	private String applicazione;
	private String nomeApplicazione;
	
	
	public Permesso() {
	}
	
	public Permesso(String id, String nomePermesso, String descrizionePermesso, String codicePermesso,
			String applicazione, String nomeApplicazione) {
		super();
		this.id = id;
		this.nomePermesso = nomePermesso;
		this.descrizionePermesso = descrizionePermesso;
		this.codicePermesso = codicePermesso;
		this.applicazione = applicazione;
		this.nomeApplicazione = nomeApplicazione;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomePermesso() {
		return nomePermesso;
	}

	public void setNomePermesso(String nomePermesso) {
		this.nomePermesso = nomePermesso;
	}

	public String getDescrizionePermesso() {
		return descrizionePermesso;
	}

	public void setDescrizionePermesso(String descrizionePermesso) {
		this.descrizionePermesso = descrizionePermesso;
	}

	public String getCodicePermesso() {
		return codicePermesso;
	}

	public void setCodicePermesso(String codicePermesso) {
		this.codicePermesso = codicePermesso;
	}

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public String getNomeApplicazione() {
		return nomeApplicazione;
	}

	public void setNomeApplicazione(String nomeApplicazione) {
		this.nomeApplicazione = nomeApplicazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicazione == null) ? 0 : applicazione.hashCode());
		result = prime * result + ((codicePermesso == null) ? 0 : codicePermesso.hashCode());
		result = prime * result + ((descrizionePermesso == null) ? 0 : descrizionePermesso.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeApplicazione == null) ? 0 : nomeApplicazione.hashCode());
		result = prime * result + ((nomePermesso == null) ? 0 : nomePermesso.hashCode());
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
		Permesso other = (Permesso) obj;
		if (applicazione == null) {
			if (other.applicazione != null)
				return false;
		} else if (!applicazione.equals(other.applicazione))
			return false;
		if (codicePermesso == null) {
			if (other.codicePermesso != null)
				return false;
		} else if (!codicePermesso.equals(other.codicePermesso))
			return false;
		if (descrizionePermesso == null) {
			if (other.descrizionePermesso != null)
				return false;
		} else if (!descrizionePermesso.equals(other.descrizionePermesso))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeApplicazione == null) {
			if (other.nomeApplicazione != null)
				return false;
		} else if (!nomeApplicazione.equals(other.nomeApplicazione))
			return false;
		if (nomePermesso == null) {
			if (other.nomePermesso != null)
				return false;
		} else if (!nomePermesso.equals(other.nomePermesso))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permesso [id=" + id + ", nomePermesso=" + nomePermesso + ", descrizionePermesso=" + descrizionePermesso
				+ ", codicePermesso=" + codicePermesso + ", applicazione=" + applicazione + ", nomeApplicazione="
				+ nomeApplicazione + "]";
	}
	
}
