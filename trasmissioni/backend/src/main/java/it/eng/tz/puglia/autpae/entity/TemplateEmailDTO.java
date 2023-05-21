package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;

public class TemplateEmailDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	protected TipoTemplate codice;
	protected String oggetto;
	protected String testo;
	protected String descrizione;
	protected String nome;
	protected List<PlaceholderDTO> placeholders;
	protected List<String> destinatariAutomatici;
	
	
	
	public TemplateEmailDTO() { 
		this.placeholders 		   = new ArrayList<>();
		this.destinatariAutomatici = new ArrayList<>();
	}

	public TemplateEmailDTO(TipoTemplate codice, String oggetto, String testo, String descrizione, String nome) {
		this.codice = codice;
		this.oggetto = oggetto;
		this.testo = testo;
		this.descrizione = descrizione;
		this.nome = nome;
	}
	
	
	
	public List<String> getDestinatariAutomatici() {
		return destinatariAutomatici;
	}
	public void setDestinatariAutomatici(List<String> destinatariAutomatici) {
		this.destinatariAutomatici = destinatariAutomatici;
	}
	public List<PlaceholderDTO> getPlaceholders() {
		return placeholders;
	}
	public void setPlaceholders(List<PlaceholderDTO> placeholders) {
		this.placeholders = placeholders;
	}
	public TipoTemplate getCodice() {
		return codice;
	}
	public void setCodice(TipoTemplate codice) {
		this.codice = codice;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((oggetto == null) ? 0 : oggetto.hashCode());
		result = prime * result + ((testo == null) ? 0 : testo.hashCode());
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
		TemplateEmailDTO other = (TemplateEmailDTO) obj;
		if (codice != other.codice)
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (oggetto == null) {
			if (other.oggetto != null)
				return false;
		} else if (!oggetto.equals(other.oggetto))
			return false;
		if (testo == null) {
			if (other.testo != null)
				return false;
		} else if (!testo.equals(other.testo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TemplateEmailDTO [codice=" + codice + ", oggetto=" + oggetto + ", testo=" + testo + ", descrizione="
				+ descrizione + ", nome=" + nome + "]";
	}

}
