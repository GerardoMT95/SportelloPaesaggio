package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;

public class RubricaEnteDTO implements Serializable
{
	private static final long serialVersionUID = 1950139697991161830L;
	
	private Long id;
	private Integer paesaggioOrganizzazioneId;
	private String codiceApplicazione;
	private String nome;
	private String pec;
	private String mail;
	
	
	public RubricaEnteDTO() {}
	
	public RubricaEnteDTO(RubricaSearchDTO rubrica) {
									this.id  =rubrica.getId   ();
									this.nome=rubrica.getNome ();
		if (rubrica.isPec()==true ) this.pec =rubrica.getEmail();
		if (rubrica.isPec()==false) this.mail=rubrica.getEmail();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPaesaggioOrganizzazioneId() {
		return paesaggioOrganizzazioneId;
	}
	public void setPaesaggioOrganizzazioneId(Integer paesaggioOrganizzazioneId) {
		this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
	}
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceApplicazione == null) ? 0 : codiceApplicazione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((paesaggioOrganizzazioneId == null) ? 0 : paesaggioOrganizzazioneId.hashCode());
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
		RubricaEnteDTO other = (RubricaEnteDTO) obj;
		if (codiceApplicazione == null) {
			if (other.codiceApplicazione != null)
				return false;
		} else if (!codiceApplicazione.equals(other.codiceApplicazione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (paesaggioOrganizzazioneId == null) {
			if (other.paesaggioOrganizzazioneId != null)
				return false;
		} else if (!paesaggioOrganizzazioneId.equals(other.paesaggioOrganizzazioneId))
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
		return "RubricaEnteDTO [id=" + id + ", paesaggioOrganizzazioneId=" + paesaggioOrganizzazioneId
				+ ", codiceApplicazione=" + codiceApplicazione + ", nome=" + nome + ", pec=" + pec + ", mail=" + mail + "]";
	}
	
}