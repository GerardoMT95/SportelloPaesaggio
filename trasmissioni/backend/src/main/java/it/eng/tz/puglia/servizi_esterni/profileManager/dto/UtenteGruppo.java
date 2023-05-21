package it.eng.tz.puglia.servizi_esterni.profileManager.dto;

import java.io.Serializable;
import java.util.List;

public class UtenteGruppo implements Serializable {

	private static final long serialVersionUID = -5285302811229490075L;

    private String usernameUtente;
    private String nomeUtente;
    private String cognomeUtente;
    private String email;
    
    private Object id;
    private Object utenteEsterno;
    private Object utenteAbilitato;
    private Object ultimoAggiornamento;
    private Object idIam;
    private Object dataUltimoImport;
    private List<Object> gruppi;
    private List<Object> ruoli;
    private List<Object> applicazioni;
    private Object numGruppiAssociati;
    private Object numRuoliAssociati;
    private Object numAppAssociati;
    private List<Object> gruppiInput;
    private Object idApplicazione;
    private Object dataInizio;
    private Object dataFine;
    private Object associaRuoliTotale;
    
    
    public UtenteGruppo() { }
    
    
	public String getUsernameUtente() {
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getUtenteEsterno() {
		return utenteEsterno;
	}
	public void setUtenteEsterno(Object utenteEsterno) {
		this.utenteEsterno = utenteEsterno;
	}
	public Object getUtenteAbilitato() {
		return utenteAbilitato;
	}
	public void setUtenteAbilitato(Object utenteAbilitato) {
		this.utenteAbilitato = utenteAbilitato;
	}
	public Object getUltimoAggiornamento() {
		return ultimoAggiornamento;
	}
	public void setUltimoAggiornamento(Object ultimoAggiornamento) {
		this.ultimoAggiornamento = ultimoAggiornamento;
	}
	public Object getIdIam() {
		return idIam;
	}
	public void setIdIam(Object idIam) {
		this.idIam = idIam;
	}
	public Object getDataUltimoImport() {
		return dataUltimoImport;
	}
	public void setDataUltimoImport(Object dataUltimoImport) {
		this.dataUltimoImport = dataUltimoImport;
	}
	public List<Object> getGruppi() {
		return gruppi;
	}
	public void setGruppi(List<Object> gruppi) {
		this.gruppi = gruppi;
	}
	public List<Object> getRuoli() {
		return ruoli;
	}
	public void setRuoli(List<Object> ruoli) {
		this.ruoli = ruoli;
	}
	public List<Object> getApplicazioni() {
		return applicazioni;
	}
	public void setApplicazioni(List<Object> applicazioni) {
		this.applicazioni = applicazioni;
	}
	public Object getNumGruppiAssociati() {
		return numGruppiAssociati;
	}
	public void setNumGruppiAssociati(Object numGruppiAssociati) {
		this.numGruppiAssociati = numGruppiAssociati;
	}
	public Object getNumRuoliAssociati() {
		return numRuoliAssociati;
	}
	public void setNumRuoliAssociati(Object numRuoliAssociati) {
		this.numRuoliAssociati = numRuoliAssociati;
	}
	public Object getNumAppAssociati() {
		return numAppAssociati;
	}
	public void setNumAppAssociati(Object numAppAssociati) {
		this.numAppAssociati = numAppAssociati;
	}
	public List<Object> getGruppiInput() {
		return gruppiInput;
	}
	public void setGruppiInput(List<Object> gruppiInput) {
		this.gruppiInput = gruppiInput;
	}
	public Object getIdApplicazione() {
		return idApplicazione;
	}
	public void setIdApplicazione(Object idApplicazione) {
		this.idApplicazione = idApplicazione;
	}
	public Object getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Object dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Object getDataFine() {
		return dataFine;
	}
	public void setDataFine(Object dataFine) {
		this.dataFine = dataFine;
	}
	public Object getAssociaRuoliTotale() {
		return associaRuoliTotale;
	}
	public void setAssociaRuoliTotale(Object associaRuoliTotale) {
		this.associaRuoliTotale = associaRuoliTotale;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicazioni == null) ? 0 : applicazioni.hashCode());
		result = prime * result + ((associaRuoliTotale == null) ? 0 : associaRuoliTotale.hashCode());
		result = prime * result + ((cognomeUtente == null) ? 0 : cognomeUtente.hashCode());
		result = prime * result + ((dataFine == null) ? 0 : dataFine.hashCode());
		result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
		result = prime * result + ((dataUltimoImport == null) ? 0 : dataUltimoImport.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gruppi == null) ? 0 : gruppi.hashCode());
		result = prime * result + ((gruppiInput == null) ? 0 : gruppiInput.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idApplicazione == null) ? 0 : idApplicazione.hashCode());
		result = prime * result + ((idIam == null) ? 0 : idIam.hashCode());
		result = prime * result + ((nomeUtente == null) ? 0 : nomeUtente.hashCode());
		result = prime * result + ((numAppAssociati == null) ? 0 : numAppAssociati.hashCode());
		result = prime * result + ((numGruppiAssociati == null) ? 0 : numGruppiAssociati.hashCode());
		result = prime * result + ((numRuoliAssociati == null) ? 0 : numRuoliAssociati.hashCode());
		result = prime * result + ((ruoli == null) ? 0 : ruoli.hashCode());
		result = prime * result + ((ultimoAggiornamento == null) ? 0 : ultimoAggiornamento.hashCode());
		result = prime * result + ((usernameUtente == null) ? 0 : usernameUtente.hashCode());
		result = prime * result + ((utenteAbilitato == null) ? 0 : utenteAbilitato.hashCode());
		result = prime * result + ((utenteEsterno == null) ? 0 : utenteEsterno.hashCode());
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
		UtenteGruppo other = (UtenteGruppo) obj;
		if (applicazioni == null) {
			if (other.applicazioni != null)
				return false;
		} else if (!applicazioni.equals(other.applicazioni))
			return false;
		if (associaRuoliTotale == null) {
			if (other.associaRuoliTotale != null)
				return false;
		} else if (!associaRuoliTotale.equals(other.associaRuoliTotale))
			return false;
		if (cognomeUtente == null) {
			if (other.cognomeUtente != null)
				return false;
		} else if (!cognomeUtente.equals(other.cognomeUtente))
			return false;
		if (dataFine == null) {
			if (other.dataFine != null)
				return false;
		} else if (!dataFine.equals(other.dataFine))
			return false;
		if (dataInizio == null) {
			if (other.dataInizio != null)
				return false;
		} else if (!dataInizio.equals(other.dataInizio))
			return false;
		if (dataUltimoImport == null) {
			if (other.dataUltimoImport != null)
				return false;
		} else if (!dataUltimoImport.equals(other.dataUltimoImport))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gruppi == null) {
			if (other.gruppi != null)
				return false;
		} else if (!gruppi.equals(other.gruppi))
			return false;
		if (gruppiInput == null) {
			if (other.gruppiInput != null)
				return false;
		} else if (!gruppiInput.equals(other.gruppiInput))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idApplicazione == null) {
			if (other.idApplicazione != null)
				return false;
		} else if (!idApplicazione.equals(other.idApplicazione))
			return false;
		if (idIam == null) {
			if (other.idIam != null)
				return false;
		} else if (!idIam.equals(other.idIam))
			return false;
		if (nomeUtente == null) {
			if (other.nomeUtente != null)
				return false;
		} else if (!nomeUtente.equals(other.nomeUtente))
			return false;
		if (numAppAssociati == null) {
			if (other.numAppAssociati != null)
				return false;
		} else if (!numAppAssociati.equals(other.numAppAssociati))
			return false;
		if (numGruppiAssociati == null) {
			if (other.numGruppiAssociati != null)
				return false;
		} else if (!numGruppiAssociati.equals(other.numGruppiAssociati))
			return false;
		if (numRuoliAssociati == null) {
			if (other.numRuoliAssociati != null)
				return false;
		} else if (!numRuoliAssociati.equals(other.numRuoliAssociati))
			return false;
		if (ruoli == null) {
			if (other.ruoli != null)
				return false;
		} else if (!ruoli.equals(other.ruoli))
			return false;
		if (ultimoAggiornamento == null) {
			if (other.ultimoAggiornamento != null)
				return false;
		} else if (!ultimoAggiornamento.equals(other.ultimoAggiornamento))
			return false;
		if (usernameUtente == null) {
			if (other.usernameUtente != null)
				return false;
		} else if (!usernameUtente.equals(other.usernameUtente))
			return false;
		if (utenteAbilitato == null) {
			if (other.utenteAbilitato != null)
				return false;
		} else if (!utenteAbilitato.equals(other.utenteAbilitato))
			return false;
		if (utenteEsterno == null) {
			if (other.utenteEsterno != null)
				return false;
		} else if (!utenteEsterno.equals(other.utenteEsterno))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UtenteGruppo [usernameUtente=" + usernameUtente + ", nomeUtente=" + nomeUtente + ", cognomeUtente="
				+ cognomeUtente + ", email=" + email + ", id=" + id + ", utenteEsterno=" + utenteEsterno
				+ ", utenteAbilitato=" + utenteAbilitato + ", ultimoAggiornamento=" + ultimoAggiornamento + ", idIam="
				+ idIam + ", dataUltimoImport=" + dataUltimoImport + ", gruppi=" + gruppi + ", ruoli=" + ruoli
				+ ", applicazioni=" + applicazioni + ", numGruppiAssociati=" + numGruppiAssociati
				+ ", numRuoliAssociati=" + numRuoliAssociati + ", numAppAssociati=" + numAppAssociati + ", gruppiInput="
				+ gruppiInput + ", idApplicazione=" + idApplicazione + ", dataInizio=" + dataInizio + ", dataFine="
				+ dataFine + ", associaRuoliTotale=" + associaRuoliTotale + "]";
	}

}