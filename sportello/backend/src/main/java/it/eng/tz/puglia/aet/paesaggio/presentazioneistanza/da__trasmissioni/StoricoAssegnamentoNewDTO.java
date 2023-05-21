package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoOperazione;

/**
 * DTO for table storico_assegnamento
 */
public class StoricoAssegnamentoNewDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private UUID idFascicolo;
	private int idOrganizzazione;
	private String fase;
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private String usernameRup;
	private String denominazioneRup;
	private TipoOperazione tipoOperazione;
	private Date dataOperazione;
	
	
	public StoricoAssegnamentoNewDTO() { }
	
	
	public StoricoAssegnamentoNewDTO(StoricoAssegnamentoOldDTO old_1, StoricoAssegnamentoOldDTO old_2) {

		this.id = old_1.getId();
		this.idFascicolo = old_1.getIdFascicolo();
		this.idOrganizzazione = old_1.getIdOrganizzazione();
		this.fase = old_1.getFase();
		this.tipoOperazione = old_1.getTipoOperazione();
		this.dataOperazione = old_1.getDataOperazione();
		this.usernameFunzionario = 		old_1.isRup()==false ? old_1.getUsernameUtente() 	  : old_2.getUsernameUtente();
		this.denominazioneFunzionario = old_1.isRup()==false ? old_1.getDenominazioneUtente() : old_2.getDenominazioneUtente();
		this.usernameRup = 				old_1.isRup()==true  ? old_1.getUsernameUtente() 	  : old_2.getUsernameUtente();
		this.denominazioneRup = 		old_1.isRup()==true  ? old_1.getDenominazioneUtente() : old_2.getDenominazioneUtente();
	}

	
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
	public String getUsernameRup() {
		return usernameRup;
	}
	public void setUsernameRup(String usernameRup) {
		this.usernameRup = usernameRup;
	}
	public String getDenominazioneRup() {
		return denominazioneRup;
	}
	public void setDenominazioneRup(String denominazioneRup) {
		this.denominazioneRup = denominazioneRup;
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
		result = prime * result + ((denominazioneRup == null) ? 0 : denominazioneRup.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + idOrganizzazione;
		result = prime * result + ((tipoOperazione == null) ? 0 : tipoOperazione.hashCode());
		result = prime * result + ((usernameFunzionario == null) ? 0 : usernameFunzionario.hashCode());
		result = prime * result + ((usernameRup == null) ? 0 : usernameRup.hashCode());
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
		StoricoAssegnamentoNewDTO other = (StoricoAssegnamentoNewDTO) obj;
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
		if (denominazioneRup == null) {
			if (other.denominazioneRup != null)
				return false;
		} else if (!denominazioneRup.equals(other.denominazioneRup))
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
		if (tipoOperazione != other.tipoOperazione)
			return false;
		if (usernameFunzionario == null) {
			if (other.usernameFunzionario != null)
				return false;
		} else if (!usernameFunzionario.equals(other.usernameFunzionario))
			return false;
		if (usernameRup == null) {
			if (other.usernameRup != null)
				return false;
		} else if (!usernameRup.equals(other.usernameRup))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StoricoAssegnamentoNewDTO [id=" + id + ", idFascicolo=" + idFascicolo + ", idOrganizzazione="
				+ idOrganizzazione + ", fase=" + fase + ", usernameFunzionario=" + usernameFunzionario
				+ ", denominazioneFunzionario=" + denominazioneFunzionario + ", usernameRup=" + usernameRup
				+ ", denominazioneRup=" + denominazioneRup + ", tipoOperazione=" + tipoOperazione + ", dataOperazione="
				+ dataOperazione + "]";
	}
	
}