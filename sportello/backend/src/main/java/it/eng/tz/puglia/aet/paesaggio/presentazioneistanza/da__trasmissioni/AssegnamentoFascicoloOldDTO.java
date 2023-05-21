package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for table assegnamento_fascicolo
 */
public class AssegnamentoFascicoloOldDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private UUID idFascicolo;
	private int idOrganizzazione;
	private String fase;
	private boolean rup;
	private String usernameUtente;
	private String denominazioneUtente;
	private Short numAssegnazioni;
	private Date dataOperazione;
	
	
	public AssegnamentoFascicoloOldDTO() { }
	
	public AssegnamentoFascicoloOldDTO(AssegnamentoFascicoloNewDTO newDTO, boolean rup) {
		this.idFascicolo = newDTO.getIdFascicolo();
		this.numAssegnazioni = newDTO.getNumAssegnazioni();
		this.dataOperazione = newDTO.getDataOperazione();
		this.rup = rup;
		if (rup==true) {
			this.usernameUtente = newDTO.getUsernameRup();
			this.denominazioneUtente = newDTO.getDenominazioneRup();
		}
		else {
			this.usernameUtente = newDTO.getUsernameFunzionario();
			this.denominazioneUtente = newDTO.getDenominazioneFunzionario();
		}
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
		result = prime * result + ((denominazioneUtente == null) ? 0 : denominazioneUtente.hashCode());
		result = prime * result + ((fase == null) ? 0 : fase.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + idOrganizzazione;
		result = prime * result + ((numAssegnazioni == null) ? 0 : numAssegnazioni.hashCode());
		result = prime * result + (rup ? 1231 : 1237);
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
		AssegnamentoFascicoloOldDTO other = (AssegnamentoFascicoloOldDTO) obj;
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
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (idOrganizzazione != other.idOrganizzazione)
			return false;
		if (numAssegnazioni == null) {
			if (other.numAssegnazioni != null)
				return false;
		} else if (!numAssegnazioni.equals(other.numAssegnazioni))
			return false;
		if (rup != other.rup)
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
		return "AssegnamentoFascicoloDTO [idFascicolo=" + idFascicolo + ", idOrganizzazione=" + idOrganizzazione
				+ ", fase=" + fase + ", rup=" + rup + ", usernameUtente=" + usernameUtente + ", denominazioneUtente="
				+ denominazioneUtente + ", numAssegnazioni=" + numAssegnazioni + ", dataOperazione=" + dataOperazione
				+ "]";
	}
	
}