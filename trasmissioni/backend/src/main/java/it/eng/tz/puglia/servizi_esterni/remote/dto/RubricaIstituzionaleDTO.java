package it.eng.tz.puglia.servizi_esterni.remote.dto;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaAdminSearchDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaSearchDTO;

public class RubricaIstituzionaleDTO implements Serializable
{
	private static final long serialVersionUID = 1950139697991161830L;
	
	private Integer enteId;
	private Integer applicazioneId;
	private String  nome;			// non è in tabella (campo "descrizione" di ENTE)
	private String  pec;
	private String  mail;
	
	
	public RubricaIstituzionaleDTO() {}
	
	public RubricaIstituzionaleDTO(RubricaSearchDTO rubrica) {
									this.nome=rubrica.getNome ();
		if (rubrica.isPec()==true ) this.pec =rubrica.getEmail();
		if (rubrica.isPec()==false) this.mail=rubrica.getEmail();
	}
	
	public RubricaIstituzionaleDTO(RubricaAdminSearchDTO rubrica) {
		this.enteId=rubrica.getId();
		this.nome  =rubrica.getNome ();
		this.mail  =rubrica.getEmail();
		this.pec   =rubrica.getPec	();
	}


	public Integer getEnteId() {
		return enteId;
	}
	public void setEnteId(Integer enteId) {
		this.enteId = enteId;
	}
	public Integer getApplicazioneId() {
		return applicazioneId;
	}
	public void setApplicazioneId(Integer applicazioneId) {
		this.applicazioneId = applicazioneId;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicazioneId == null) ? 0 : applicazioneId.hashCode());
		result = prime * result + ((enteId == null) ? 0 : enteId.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
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
		RubricaIstituzionaleDTO other = (RubricaIstituzionaleDTO) obj;
		if (applicazioneId == null) {
			if (other.applicazioneId != null)
				return false;
		} else if (!applicazioneId.equals(other.applicazioneId))
			return false;
		if (enteId == null) {
			if (other.enteId != null)
				return false;
		} else if (!enteId.equals(other.enteId))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RubricaIstituzionaleDTO [enteId=" + enteId + ", applicazioneId=" + applicazioneId + ", nome=" + nome
				+ ", pec=" + pec + ", mail=" + mail + "]";
	}
	
}