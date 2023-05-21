package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;

public class InformazioniDTO extends FascicoloTabDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private RichiedenteDTO richiedente;
	private InterventoTabDTO intervento;
	private List<Long> interventoSelezionati;
	private LocalizzazioneTabDTO localizzazione;
	private ArrayList<AllegatoCustomDTO> allegati;
	private ProvvedimentoTabDTO provvedimento;
	private List<ComuniCompetenzaDTO> comuniCompetenza;
	private boolean comunicazioniEnabled;				// verifico se l'organizzazione dell'utente loggato è abilitata a vedere il pannello "Comunicazioni"
	private boolean ultDocEnabled;						// verifico se l'organizzazione dell'utente loggato è abilitata a vedere il pannello "Ulteriore Documentazione"
	@JsonIgnore
	private boolean multiComune;
	
	
	public InformazioniDTO() { }

	public InformazioniDTO(final FascicoloTabDTO fascicolo, final RichiedenteDTO richiedente, final InterventoTabDTO intervento, final List<Long> interventoSelezionati,
			   			   final LocalizzazioneTabDTO localizzazione, final ArrayList<AllegatoCustomDTO> allegati,
			   			   final ProvvedimentoTabDTO provvedimento, final List<ComuniCompetenzaDTO> comuniCompetenza,
			   			   final boolean comunicazioniEnabled, final boolean ultDocEnabled) {
		super(fascicolo);
		this.richiedente = richiedente;
		this.intervento = intervento;
		this.interventoSelezionati = interventoSelezionati;
		this.localizzazione = localizzazione;
		this.allegati = allegati;
		this.provvedimento = provvedimento;
		this.comuniCompetenza = comuniCompetenza;
		this.comunicazioniEnabled = comunicazioniEnabled;
		this.ultDocEnabled = ultDocEnabled;
	}

//	public InformazioniDTO(							  RichiedenteDTO richiedente, InterventoTabDTO intervento, List<Long> interventoSelezionati,
//						   LocalizzazioneTabDTO localizzazione, ArrayList<AllegatoCustomDTO> allegati, ProvvedimentoTabDTO provvedimento) {
//		super();
//		this.richiedente = richiedente;
//		this.intervento = intervento;
//		this.interventoSelezionati = interventoSelezionati;
//		this.localizzazione = localizzazione;
//		this.allegati = allegati;
//		this.provvedimento = provvedimento;
//	}

	public RichiedenteDTO getRichiedente() {
		return this.richiedente;
	}

	public void setRichiedente(final RichiedenteDTO richiedente) {
		this.richiedente = richiedente;
	}

	public InterventoTabDTO getIntervento() {
		return this.intervento;
	}

	public void setIntervento(final InterventoTabDTO intervento) {
		this.intervento = intervento;
	}

	public LocalizzazioneTabDTO getLocalizzazione() {
		return this.localizzazione;
	}

	public void setLocalizzazione(final LocalizzazioneTabDTO localizzazione) {
		this.localizzazione = localizzazione;
	}

	public ArrayList<AllegatoCustomDTO> getAllegati() {
		return this.allegati;
	}

	public void setAllegati(final ArrayList<AllegatoCustomDTO> allegati) {
		this.allegati = allegati;
	}

	public List<ComuniCompetenzaDTO> getComuniCompetenza() {
		return this.comuniCompetenza;
	}

	public void setComuniCompetenza(final List<ComuniCompetenzaDTO> comuniCompetenza) {
		this.comuniCompetenza = comuniCompetenza;
	}

	public ProvvedimentoTabDTO getProvvedimento() {
		return this.provvedimento;
	}

	public void setProvvedimento(final ProvvedimentoTabDTO provvedimento) {
		this.provvedimento = provvedimento;
	}

	public List<Long> getInterventoSelezionati() {
		return this.interventoSelezionati;
	}

	public void setInterventoSelezionati(final List<Long> interventoSelezionati) {
		this.interventoSelezionati = interventoSelezionati;
	}

	public boolean isComunicazioniEnabled() {
		return this.comunicazioniEnabled;
	}

	public void setComunicazioniEnabled(final boolean comunicazioniEnabled) {
		this.comunicazioniEnabled = comunicazioniEnabled;
	}

	public boolean isUltDocEnabled() {
		return this.ultDocEnabled;
	}

	public void setUltDocEnabled(final boolean ultDocEnabled) {
		this.ultDocEnabled = ultDocEnabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.allegati == null) ? 0 : this.allegati.hashCode());
		result = prime * result + ((this.comuniCompetenza == null) ? 0 : this.comuniCompetenza.hashCode());
		result = prime * result + (this.comunicazioniEnabled ? 1231 : 1237);
		result = prime * result + ((this.intervento == null) ? 0 : this.intervento.hashCode());
		result = prime * result + ((this.interventoSelezionati == null) ? 0 : this.interventoSelezionati.hashCode());
		result = prime * result + ((this.localizzazione == null) ? 0 : this.localizzazione.hashCode());
		result = prime * result + ((this.provvedimento == null) ? 0 : this.provvedimento.hashCode());
		result = prime * result + ((this.richiedente == null) ? 0 : this.richiedente.hashCode());
		result = prime * result + (this.ultDocEnabled ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final InformazioniDTO other = (InformazioniDTO) obj;
		if (this.allegati == null) {
			if (other.allegati != null)
				return false;
		} else if (!this.allegati.equals(other.allegati))
			return false;
		if (this.comuniCompetenza == null) {
			if (other.comuniCompetenza != null)
				return false;
		} else if (!this.comuniCompetenza.equals(other.comuniCompetenza))
			return false;
		if (this.comunicazioniEnabled != other.comunicazioniEnabled)
			return false;
		if (this.intervento == null) {
			if (other.intervento != null)
				return false;
		} else if (!this.intervento.equals(other.intervento))
			return false;
		if (this.interventoSelezionati == null) {
			if (other.interventoSelezionati != null)
				return false;
		} else if (!this.interventoSelezionati.equals(other.interventoSelezionati))
			return false;
		if (this.localizzazione == null) {
			if (other.localizzazione != null)
				return false;
		} else if (!this.localizzazione.equals(other.localizzazione))
			return false;
		if (this.provvedimento == null) {
			if (other.provvedimento != null)
				return false;
		} else if (!this.provvedimento.equals(other.provvedimento))
			return false;
		if (this.richiedente == null) {
			if (other.richiedente != null)
				return false;
		} else if (!this.richiedente.equals(other.richiedente))
			return false;
		if (this.ultDocEnabled != other.ultDocEnabled)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InformazioniDTO [richiedente=" + this.richiedente + ", intervento=" + this.intervento + ", interventoSelezionati="
				+ this.interventoSelezionati + ", localizzazione=" + this.localizzazione + ", allegati=" + this.allegati
				+ ", provvedimento=" + this.provvedimento + ", comuniCompetenza=" + this.comuniCompetenza
				+ ", comunicazioniEnabled=" + this.comunicazioniEnabled + ", ultDocEnabled=" + this.ultDocEnabled + "]";
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public String toJson()
	{
		final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
		return gson.toJson(new Intern(this), Intern.class);
	}
	
	static class Intern
	{
		private InformazioniDTO informazioni;

		public Intern() {}

		public Intern(final InformazioniDTO informazioni) {
			this.informazioni = informazioni;
		}

		public InformazioniDTO getInformazioni() {
			return this.informazioni;
		}

		public void setInformazioni(final InformazioniDTO informazioni) {
			this.informazioni = informazioni;
		}
	}

	/**
	 * @return the multiComune
	 */
	public boolean isMultiComune() {
		return this.multiComune;
	}

	/**
	 * @param multiComune the multiComune to set
	 */
	public void setMultiComune(final boolean multiComune) {
		this.multiComune = multiComune;
	}
	
}