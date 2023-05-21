package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;

/**
 * DTO for destinatario Aggiornato (ORP)
 */
public class DestinatarioAggDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idDestinatario;					         // DestinatarioDTO --> id
	private Long idCorrispondenza;  			// aggiunto  // DestinatarioDTO --> idCorrispondenza
	private TipoDestinatario tipoDestinatario;				 // DestinatarioDTO --> type
	private String indirizzo;								 // DestinatarioDTO --> email
	private String denominazione;							 // DestinatarioDTO --> denominazione
	private String nominativo;					// inutile
	private Boolean pec;									 // DestinatarioDTO --> pec
	private StatoCorrispondenza stato;			// aggiunto  // DestinatarioDTO --> stato
	private List<RicevutaDTO> ricevute;						 // DestinatarioDTO --> ricevute
	
	
	public DestinatarioAggDTO() { }
	
	
	public DestinatarioAggDTO(DestinatarioDTO destinatario) { 
		
		this.idDestinatario=destinatario.getId();
		this.idCorrispondenza=destinatario.getIdCorrispondenza();
		this.tipoDestinatario=destinatario.getTipo();
		this.indirizzo=destinatario.getEmail();
		this.denominazione=destinatario.getNome();
		this.nominativo=null;
		this.pec=destinatario.isPec();
		this.stato=destinatario.getStato();
		this.ricevute=destinatario.getRicevute();
	}
	
	
	public Long getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(Long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}
	public TipoDestinatario getTipoDestinatario() {
		return tipoDestinatario;
	}
	public void setTipoDestinatario(TipoDestinatario tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getNominativo() {
		return nominativo;
	}
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public StatoCorrispondenza getStato() {
		return stato;
	}
	public void setStato(StatoCorrispondenza stato) {
		this.stato = stato;
	}
	public List<RicevutaDTO> getRicevute() {
		return ricevute;
	}
	public void setRicevute(List<RicevutaDTO> ricevute) {
		this.ricevute = ricevute;
	}
	public Boolean getPec() {
		return pec;
	}
	public void setPec(Boolean pec) {
		this.pec = pec;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
		result = prime * result + ((idDestinatario == null) ? 0 : idDestinatario.hashCode());
		result = prime * result + ((indirizzo == null) ? 0 : indirizzo.hashCode());
		result = prime * result + ((nominativo == null) ? 0 : nominativo.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((ricevute == null) ? 0 : ricevute.hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + ((tipoDestinatario == null) ? 0 : tipoDestinatario.hashCode());
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
		DestinatarioAggDTO other = (DestinatarioAggDTO) obj;
		if (denominazione == null) {
			if (other.denominazione != null)
				return false;
		} else if (!denominazione.equals(other.denominazione))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		if (idDestinatario == null) {
			if (other.idDestinatario != null)
				return false;
		} else if (!idDestinatario.equals(other.idDestinatario))
			return false;
		if (indirizzo == null) {
			if (other.indirizzo != null)
				return false;
		} else if (!indirizzo.equals(other.indirizzo))
			return false;
		if (nominativo == null) {
			if (other.nominativo != null)
				return false;
		} else if (!nominativo.equals(other.nominativo))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		if (ricevute == null) {
			if (other.ricevute != null)
				return false;
		} else if (!ricevute.equals(other.ricevute))
			return false;
		if (stato != other.stato)
			return false;
		if (tipoDestinatario != other.tipoDestinatario)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "DestinatarioAggDTO [idDestinatario=" + idDestinatario + ", idCorrispondenza=" + idCorrispondenza
				+ ", tipoDestinatario=" + tipoDestinatario + ", indirizzo=" + indirizzo + ", denominazione="
				+ denominazione + ", nominativo=" + nominativo + ", pec=" + pec + ", stato=" + stato + ", ricevute="
				+ ricevute + "]";
	}
	
}
