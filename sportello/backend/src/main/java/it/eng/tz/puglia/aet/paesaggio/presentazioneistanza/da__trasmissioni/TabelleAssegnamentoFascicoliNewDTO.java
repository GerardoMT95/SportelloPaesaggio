package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;

public class TabelleAssegnamentoFascicoliNewDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;
	
	private UUID id;
	private AttivitaDaEspletare stato;
	private String codice;
	private int tipoProcedimento;
	private String oggettoIntervento;
	
	private List<String> comuni;
	
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private String usernameRup;
	private String denominazioneRup;
	private short  riassegnazioni;
	private Date ultimaAssegnazione;
	private Date ultimaOperazione;
	
//	private List<StoricoAssegnamentoNewDTO> storico;
	
	
	public TabelleAssegnamentoFascicoliNewDTO() { 
		this.comuni  = new ArrayList<>();
//		this.storico = new ArrayList<>();
	}
	
	public TabelleAssegnamentoFascicoliNewDTO(TabelleAssegnamentoFascicoliOldDTO old_1, TabelleAssegnamentoFascicoliOldDTO old_2) {
		this.id = old_1.getId();
		this.stato = old_1.getStato();
		this.codice = old_1.getCodice();
		this.tipoProcedimento = old_1.getTipoProcedimento();
		this.oggettoIntervento = old_1.getOggettoIntervento();
		this.comuni = old_1.getComuni();
		this.riassegnazioni = old_1.getRiassegnazioni();
		this.ultimaAssegnazione = old_1.getUltimaAssegnazione();
		this.ultimaOperazione = old_1.getUltimaOperazione();
		this.usernameFunzionario = 		old_1.isRup()==false ? old_1.getUsernameUtente() 	  : (old_2!=null?old_2.getUsernameUtente():null);
		this.denominazioneFunzionario = old_1.isRup()==false ? old_1.getDenominazioneUtente() : (old_2!=null?old_2.getDenominazioneUtente():null);
		this.usernameRup = 				old_1.isRup()==true  ? old_1.getUsernameUtente() 	  : (old_2!=null?old_2.getUsernameUtente():null);
		this.denominazioneRup = 		old_1.isRup()==true  ? old_1.getDenominazioneUtente() : (old_2!=null?old_2.getDenominazioneUtente():null);
	}


	public TabelleAssegnamentoFascicoliNewDTO(TabelleAssegnamentoFascicoliOldDTO oldDTO) {
		this.id = oldDTO.getId();
		this.stato = oldDTO.getStato();
		this.codice = oldDTO.getCodice();
		this.tipoProcedimento = oldDTO.getTipoProcedimento();
		this.oggettoIntervento = oldDTO.getOggettoIntervento();
		this.comuni = oldDTO.getComuni();
		this.riassegnazioni = oldDTO.getRiassegnazioni();
		this.ultimaAssegnazione = oldDTO.getUltimaAssegnazione();
		this.ultimaOperazione = oldDTO.getUltimaOperazione();
		this.usernameFunzionario = 		oldDTO.getUsernameUtente();
		this.denominazioneFunzionario = oldDTO.getDenominazioneUtente();
		this.usernameRup = 				oldDTO.getUsernameRup();
		this.denominazioneRup = 		oldDTO.getDenominazioneRup();
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
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + ((denominazioneRup == null) ? 0 : denominazioneRup.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((oggettoIntervento == null) ? 0 : oggettoIntervento.hashCode());
		result = prime * result + riassegnazioni;
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + tipoProcedimento;
		result = prime * result + ((ultimaAssegnazione == null) ? 0 : ultimaAssegnazione.hashCode());
		result = prime * result + ((ultimaOperazione == null) ? 0 : ultimaOperazione.hashCode());
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
		TabelleAssegnamentoFascicoliNewDTO other = (TabelleAssegnamentoFascicoliNewDTO) obj;
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
		return "TabelleAssegnamentoFascicoliNewDTO [id=" + id + ", stato=" + stato + ", codice=" + codice
				+ ", tipoProcedimento=" + tipoProcedimento + ", oggettoIntervento=" + oggettoIntervento + ", comuni="
				+ comuni + ", usernameFunzionario=" + usernameFunzionario + ", denominazioneFunzionario="
				+ denominazioneFunzionario + ", usernameRup=" + usernameRup + ", denominazioneRup=" + denominazioneRup
				+ ", riassegnazioni=" + riassegnazioni + ", ultimaAssegnazione=" + ultimaAssegnazione
				+ ", ultimaOperazione=" + ultimaOperazione + "]";
	}
	
}