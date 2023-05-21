package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for table assegnamento_fascicolo
 */
public class AssegnamentoFascicoloNewDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private UUID   idFascicolo;
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private String usernameRup;
	private String denominazioneRup;
	private Short numAssegnazioni;
	private Date dataOperazione;
	
	
	public AssegnamentoFascicoloNewDTO() { }

	
	public AssegnamentoFascicoloNewDTO(AssegnamentoFascicoloOldDTO rup, AssegnamentoFascicoloOldDTO funzionario) {
		this.idFascicolo = rup.getIdFascicolo();
		this.dataOperazione = rup.getDataOperazione();
		this.numAssegnazioni = rup.getNumAssegnazioni();
		this.usernameRup = rup.getUsernameUtente();
		this.denominazioneRup = rup.getDenominazioneUtente();
		this.usernameFunzionario = funzionario.getUsernameUtente();
		this.denominazioneFunzionario = funzionario.getDenominazioneUtente();
	}


	public UUID getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(UUID idFascicolo) {
		this.idFascicolo = idFascicolo;
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

	public Short getNumAssegnazioni() {
		return numAssegnazioni;
	}

	public void setNumAssegnazioni(Short numAssegnazioni) {
		this.numAssegnazioni = numAssegnazioni;
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
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((numAssegnazioni == null) ? 0 : numAssegnazioni.hashCode());
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
		AssegnamentoFascicoloNewDTO other = (AssegnamentoFascicoloNewDTO) obj;
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
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (numAssegnazioni == null) {
			if (other.numAssegnazioni != null)
				return false;
		} else if (!numAssegnazioni.equals(other.numAssegnazioni))
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
		return "AssegnamentoFascicoloNewDTO [idFascicolo=" + idFascicolo + ", usernameFunzionario="
				+ usernameFunzionario + ", denominazioneFunzionario=" + denominazioneFunzionario + ", usernameRup="
				+ usernameRup + ", denominazioneRup=" + denominazioneRup + ", numAssegnazioni=" + numAssegnazioni
				+ ", dataOperazione=" + dataOperazione + "]";
	}
	
}