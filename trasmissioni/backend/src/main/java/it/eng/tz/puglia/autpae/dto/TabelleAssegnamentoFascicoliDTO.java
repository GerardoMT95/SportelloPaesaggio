package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;

public class TabelleAssegnamentoFascicoliDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;
	
	
	private long id;
	private StatoFascicolo stato;
	private String codice;
	private TipoProcedimento tipoProcedimento;
	private String oggettoIntervento;
	
	private List<String> comuni;
	
	private String usernameFunzionario;
	private String denominazioneFunzionario;
	private short  riassegnazioni;
	private Date ultimaAssegnazione;
	private Date ultimaOperazione;
	
	private List<StoricoAssegnamentoDTO> storico;
	
	
	
	public TabelleAssegnamentoFascicoliDTO() { 
		this.comuni  = new ArrayList<>();
		this.storico = new ArrayList<>();
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StatoFascicolo getStato() {
		return stato;
	}
	public void setStato(StatoFascicolo stato) {
		this.stato = stato;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
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
	public List<StoricoAssegnamentoDTO> getStorico() {
		return storico;
	}
	public void setStorico(List<StoricoAssegnamentoDTO> storico) {
		this.storico = storico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((comuni == null) ? 0 : comuni.hashCode());
		result = prime * result + ((denominazioneFunzionario == null) ? 0 : denominazioneFunzionario.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((oggettoIntervento == null) ? 0 : oggettoIntervento.hashCode());
		result = prime * result + riassegnazioni;
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + ((storico == null) ? 0 : storico.hashCode());
		result = prime * result + ((tipoProcedimento == null) ? 0 : tipoProcedimento.hashCode());
		result = prime * result + ((ultimaAssegnazione == null) ? 0 : ultimaAssegnazione.hashCode());
		result = prime * result + ((ultimaOperazione == null) ? 0 : ultimaOperazione.hashCode());
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
		TabelleAssegnamentoFascicoliDTO other = (TabelleAssegnamentoFascicoliDTO) obj;
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
		if (id != other.id)
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
		if (storico == null) {
			if (other.storico != null)
				return false;
		} else if (!storico.equals(other.storico))
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
		return true;
	}
	
	@Override
	public String toString() {
		return "TabelleAssegnamentoFascicoliDTO [id=" + id + ", stato=" + stato + ", codice=" + codice
				+ ", tipoProcedimento=" + tipoProcedimento + ", oggettoIntervento=" + oggettoIntervento + ", comuni="
				+ comuni + ", usernameFunzionario=" + usernameFunzionario + ", denominazioneFunzionario="
				+ denominazioneFunzionario + ", riassegnazioni=" + riassegnazioni + ", ultimaAssegnazione="
				+ ultimaAssegnazione + ", ultimaOperazione=" + ultimaOperazione + ", storico=" + storico + "]";
	}
	
}