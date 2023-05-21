package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

import it.eng.tz.puglia.autpae.enumeratori.TipoOperazione;

/**
 * DTO for table storico_assegnamento
 */
public class StoricoAssegnamentoDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private long idFascicolo;
	private int idOrganizzazione;
	private String fase;
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private TipoOperazione tipoOperazione;
	private Date dataOperazione;
	
	
	public StoricoAssegnamentoDTO() { }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public int getIdOrganizzazione() {
		return idOrganizzazione;
	}
	public void setIdOrganizzazione(int idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getUsernameFunzionario() {
		return usernameFunzionario;
	}
	public void setUsernameFunzionario(String usernameFunzionario) {
		this.usernameFunzionario = usernameFunzionario;
	}
	public String getDenominazioneFunzionario() {
		return denominazioneFunzionario;
	}
	public void setDenominazioneFunzionario(String denominazioneFunzionario) {
		this.denominazioneFunzionario = denominazioneFunzionario;
	}
	public TipoOperazione getTipoOperazione() {
		return tipoOperazione;
	}
	public void setTipoOperazione(TipoOperazione tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	public Date getDataOperazione() {
		return dataOperazione;
	}
	public void setDataOperazione(Date dataOperazione) {
		this.dataOperazione = dataOperazione;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataOperazione == null) ? 0 : dataOperazione.hashCode());
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (idFascicolo ^ (idFascicolo >>> 32));
		result = prime * result + idOrganizzazione;
		result = prime * result + ((tipoOperazione == null) ? 0 : tipoOperazione.hashCode());
		result = prime * result + ((usernameFunzionario == null) ? 0 : usernameFunzionario.hashCode());
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
		StoricoAssegnamentoDTO other = (StoricoAssegnamentoDTO) obj;
		if (dataOperazione == null) {
			if (other.dataOperazione != null)
				return false;
		} else if (!dataOperazione.equals(other.dataOperazione))
			return false;
		if (denominazioneFunzionario == null) {
			if (other.denominazioneFunzionario != null)
				return false;
		} else if (!denominazioneFunzionario.equals(other.denominazioneFunzionario))
			return false;
		if (fase == null) {
			if (other.fase != null)
				return false;
		} else if (!fase.equals(other.fase))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idFascicolo != other.idFascicolo)
			return false;
		if (idOrganizzazione != other.idOrganizzazione)
			return false;
		if (tipoOperazione != other.tipoOperazione)
			return false;
		if (usernameFunzionario == null) {
			if (other.usernameFunzionario != null)
				return false;
		} else if (!usernameFunzionario.equals(other.usernameFunzionario))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StoricoAssegnamentoDTO [id=" + id + ", idFascicolo=" + idFascicolo + ", idOrganizzazione="
				+ idOrganizzazione + ", fase=" + fase + ", usernameFunzionario=" + usernameFunzionario
				+ ", denominazioneFunzionario=" + denominazioneFunzionario + ", tipoOperazione=" + tipoOperazione
				+ ", dataOperazione=" + dataOperazione + "]";
	}
	
}