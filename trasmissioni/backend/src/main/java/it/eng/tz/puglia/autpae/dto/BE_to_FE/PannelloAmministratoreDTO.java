package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;

public class PannelloAmministratoreDTO extends ConfigurazioneCampionamentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> comunicazioni;
	private List<String> documentazione;
	private List<String> osservazioni;
	private boolean		 statoRegistrazione;
	private boolean		 esitoVerifica;
	private boolean		 comunicazioniAttivo;
	private boolean		 documentazioneAttivo;
	
	
	public boolean isComunicazioniAttivo() {
		return comunicazioniAttivo;
	}


	public void setComunicazioniAttivo(boolean comunicazioniAttivo) {
		this.comunicazioniAttivo = comunicazioniAttivo;
	}


	public boolean isDocumentazioneAttivo() {
		return documentazioneAttivo;
	}


	public void setDocumentazioneAttivo(boolean documentazioneAttivo) {
		this.documentazioneAttivo = documentazioneAttivo;
	}


	public PannelloAmministratoreDTO() { 
		this.comunicazioni  = new ArrayList<>();
		this.documentazione = new ArrayList<>();
		this.osservazioni   = new ArrayList<>();
	}
	
	
	public PannelloAmministratoreDTO(ConfigurazioneCampionamentoDTO configCampionamento) { 
		this.comunicazioni  = new ArrayList<>();
		this.documentazione = new ArrayList<>();
		this.osservazioni   = new ArrayList<>();
		this.campionamentoAttivo = configCampionamento.isCampionamentoAttivo();
		this.intervalloCampionamento = configCampionamento.getIntervalloCampionamento();
		this.tipoCampionamentoSuccessivo = configCampionamento.getTipoCampionamentoSuccessivo();
		this.percentualeIstanze = configCampionamento.getPercentualeIstanze();
		this.giorniNotificaCampionamento = configCampionamento.getGiorniNotificaCampionamento();
		this.giorniNotificaVerifica = configCampionamento.getGiorniNotificaVerifica();
		this.intervalloVerifica = configCampionamento.getIntervalloVerifica();
		this.esitoPubblico = configCampionamento.isEsitoPubblico();
		this.applicaInCorso = configCampionamento.isApplicaInCorso();
	}
	
	
	public List<String> getComunicazioni() {
		return comunicazioni;
	}
	public void setComunicazioni(List<String> comunicazioni) {
		this.comunicazioni = comunicazioni;
	}
	public List<String> getDocumentazione() {
		return documentazione;
	}
	public void setDocumentazione(List<String> documentazione) {
		this.documentazione = documentazione;
	}
	public List<String> getOsservazioni() {
		return osservazioni;
	}
	public void setOsservazioni(List<String> osservazioni) {
		this.osservazioni = osservazioni;
	}
	public boolean isStatoRegistrazione() {
		return statoRegistrazione;
	}
	public void setStatoRegistrazione(boolean statoRegistrazione) {
		this.statoRegistrazione = statoRegistrazione;
	}
	public boolean isEsitoVerifica() {
		return esitoVerifica;
	}
	public void setEsitoVerifica(boolean esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((comunicazioni == null) ? 0 : comunicazioni.hashCode());
		result = prime * result + ((documentazione == null) ? 0 : documentazione.hashCode());
		result = prime * result + (esitoVerifica ? 1231 : 1237);
		result = prime * result + ((osservazioni == null) ? 0 : osservazioni.hashCode());
		result = prime * result + (statoRegistrazione ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PannelloAmministratoreDTO other = (PannelloAmministratoreDTO) obj;
		if (comunicazioni == null) {
			if (other.comunicazioni != null)
				return false;
		} else if (!comunicazioni.equals(other.comunicazioni))
			return false;
		if (documentazione == null) {
			if (other.documentazione != null)
				return false;
		} else if (!documentazione.equals(other.documentazione))
			return false;
		if (esitoVerifica != other.esitoVerifica)
			return false;
		if (osservazioni == null) {
			if (other.osservazioni != null)
				return false;
		} else if (!osservazioni.equals(other.osservazioni))
			return false;
		if (statoRegistrazione != other.statoRegistrazione)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PannelloAmministratoreDTO [comunicazioni=" + comunicazioni + ", documentazione=" + documentazione
				+ ", osservazioni=" + osservazioni + ", statoRegistrazione=" + statoRegistrazione + ", esitoVerifica="
				+ esitoVerifica + "]";
	}
	
}