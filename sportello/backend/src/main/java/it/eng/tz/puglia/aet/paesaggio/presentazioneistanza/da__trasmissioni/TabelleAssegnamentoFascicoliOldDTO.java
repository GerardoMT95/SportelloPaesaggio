package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;

public class TabelleAssegnamentoFascicoliOldDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;
	
	private UUID id;
	private AttivitaDaEspletare stato;
	private String codice;
	private int tipoProcedimento;
	private String oggettoIntervento;
	
	private List<String> comuni;
	
	private boolean rup;
	private String usernameUtente;
	private String denominazioneUtente;
	private short  riassegnazioni;
	private Date ultimaAssegnazione;
	private Date ultimaOperazione;
	private String usernameRup;
	private String denominazioneRup;
	
	public TabelleAssegnamentoFascicoliOldDTO() { 
		this.comuni  = new ArrayList<>();
//		this.storico = new ArrayList<>();
	}
	
	public TabelleAssegnamentoFascicoliOldDTO(PraticaDTO other)
	{
		id = other.getId();
		stato = other.getStato();
		codice = other.getCodicePraticaAppptr();
		tipoProcedimento = other.getTipoProcedimento();
		oggettoIntervento = other.getOggetto();
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


	
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public AttivitaDaEspletare getStato() {
		return stato;
	}
	public void setStato(AttivitaDaEspletare stato) {
		this.stato = stato;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public int getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(int tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getOggettoIntervento() {
		return oggettoIntervento;
	}
	public void setOggettoIntervento(String oggettoIntervento) {
		this.oggettoIntervento = oggettoIntervento;
	}
	public List<String> getComuni() {
		return comuni;
	}
	public void setComuni(List<String> comuni) {
		this.comuni = comuni;
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
	public short getRiassegnazioni() {
		return riassegnazioni;
	}
	public void setRiassegnazioni(short riassegnazioni) {
		this.riassegnazioni = riassegnazioni;
	}
	public Date getUltimaAssegnazione() {
		return ultimaAssegnazione;
	}
	public void setUltimaAssegnazione(Date ultimaAssegnazione) {
		this.ultimaAssegnazione = ultimaAssegnazione;
	}
	public Date getUltimaOperazione() {
		return ultimaOperazione;
	}
	public void setUltimaOperazione(Date ultimaOperazione) {
		this.ultimaOperazione = ultimaOperazione;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((comuni == null) ? 0 : comuni.hashCode());
		result = prime * result + ((denominazioneRup == null) ? 0 : denominazioneRup.hashCode());
		result = prime * result + ((denominazioneUtente == null) ? 0 : denominazioneUtente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((oggettoIntervento == null) ? 0 : oggettoIntervento.hashCode());
		result = prime * result + riassegnazioni;
		result = prime * result + (rup ? 1231 : 1237);
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + tipoProcedimento;
		result = prime * result + ((ultimaAssegnazione == null) ? 0 : ultimaAssegnazione.hashCode());
		result = prime * result + ((ultimaOperazione == null) ? 0 : ultimaOperazione.hashCode());
		result = prime * result + ((usernameRup == null) ? 0 : usernameRup.hashCode());
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
		TabelleAssegnamentoFascicoliOldDTO other = (TabelleAssegnamentoFascicoliOldDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (comuni == null) {
			if (other.comuni != null)
				return false;
		} else if (!comuni.equals(other.comuni))
			return false;
		if (denominazioneRup == null) {
			if (other.denominazioneRup != null)
				return false;
		} else if (!denominazioneRup.equals(other.denominazioneRup))
			return false;
		if (denominazioneUtente == null) {
			if (other.denominazioneUtente != null)
				return false;
		} else if (!denominazioneUtente.equals(other.denominazioneUtente))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (oggettoIntervento == null) {
			if (other.oggettoIntervento != null)
				return false;
		} else if (!oggettoIntervento.equals(other.oggettoIntervento))
			return false;
		if (riassegnazioni != other.riassegnazioni)
			return false;
		if (rup != other.rup)
			return false;
		if (stato != other.stato)
			return false;
		if (tipoProcedimento != other.tipoProcedimento)
			return false;
		if (ultimaAssegnazione == null) {
			if (other.ultimaAssegnazione != null)
				return false;
		} else if (!ultimaAssegnazione.equals(other.ultimaAssegnazione))
			return false;
		if (ultimaOperazione == null) {
			if (other.ultimaOperazione != null)
				return false;
		} else if (!ultimaOperazione.equals(other.ultimaOperazione))
			return false;
		if (usernameRup == null) {
			if (other.usernameRup != null)
				return false;
		} else if (!usernameRup.equals(other.usernameRup))
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
		return "TabelleAssegnamentoFascicoliOldDTO [id=" + id + ", stato=" + stato + ", codice=" + codice
				+ ", tipoProcedimento=" + tipoProcedimento + ", oggettoIntervento=" + oggettoIntervento + ", comuni="
				+ comuni + ", rup=" + rup + ", usernameUtente=" + usernameUtente + ", denominazioneUtente="
				+ denominazioneUtente + ", riassegnazioni=" + riassegnazioni + ", ultimaAssegnazione="
				+ ultimaAssegnazione + ", ultimaOperazione=" + ultimaOperazione + ", usernameRup=" + usernameRup
				+ ", denominazioneRup=" + denominazioneRup + "]";
	}
	
	
	
}