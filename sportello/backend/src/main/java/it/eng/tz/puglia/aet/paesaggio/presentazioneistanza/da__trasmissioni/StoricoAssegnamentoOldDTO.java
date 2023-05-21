package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoOperazione;

/**
 * DTO for table storico_assegnamento
 */
public class StoricoAssegnamentoOldDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private UUID idFascicolo;
	private int idOrganizzazione;
	private String fase;
	private boolean rup;
	private String usernameUtente;
	private String denominazioneUtente;
	private TipoOperazione tipoOperazione;
	private Date dataOperazione;
	
	
	public StoricoAssegnamentoOldDTO() { }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UUID getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(UUID idFascicolo) {
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
	public boolean isRup() {
		return rup;
	}
	public void setRup(boolean rup) {
		this.rup = rup;
	}
	public String getUsernameUtente() {
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente) {
		this.usernameUtente = usernameUtente;
	}
	public String getDenominazioneUtente() {
		return denominazioneUtente;
	}
	public void setDenominazioneUtente(String denominazioneUtente) {
		this.denominazioneUtente = denominazioneUtente;
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
		result = prime * result + ((denominazioneUtente == null) ? 0 : denominazioneUtente.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + idOrganizzazione;
		result = prime * result + (rup ? 1231 : 1237);
		result = prime * result + ((tipoOperazione == null) ? 0 : tipoOperazione.hashCode());
		result = prime * result + ((usernameUtente == null) ? 0 : usernameUtente.hashCode());
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
		StoricoAssegnamentoOldDTO other = (StoricoAssegnamentoOldDTO) obj;
		if (dataOperazione == null) {
			if (other.dataOperazione != null)
				return false;
		} else if (!dataOperazione.equals(other.dataOperazione))
			return false;
		if (denominazioneUtente == null) {
			if (other.denominazioneUtente != null)
				return false;
		} else if (!denominazioneUtente.equals(other.denominazioneUtente))
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
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (idOrganizzazione != other.idOrganizzazione)
			return false;
		if (rup != other.rup)
			return false;
		if (tipoOperazione != other.tipoOperazione)
			return false;
		if (usernameUtente == null) {
			if (other.usernameUtente != null)
				return false;
		} else if (!usernameUtente.equals(other.usernameUtente))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StoricoAssegnamentoDTO [id=" + id + ", idFascicolo=" + idFascicolo + ", idOrganizzazione="
				+ idOrganizzazione + ", fase=" + fase + ", rup=" + rup + ", usernameUtente=" + usernameUtente
				+ ", denominazioneUtente=" + denominazioneUtente + ", tipoOperazione=" + tipoOperazione
				+ ", dataOperazione=" + dataOperazione + "]";
	}
	
}