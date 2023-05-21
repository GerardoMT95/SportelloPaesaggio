package it.eng.tz.puglia.servizi_esterni.jasper.dto;

import java.text.SimpleDateFormat;

import it.eng.tz.puglia.autpae.entity.FascicoloDTO;

public class JasperCampionamentoListaFascicoliDTO {
	
	private String codicePratica;
	private String enteDiRegistrazione;
	private String intervento;
	private String tipoProcedimento;
	private String numeroProvvedimento;
	private String dataProvvedimento;	// dataRilascioAutorizzazione
	private String esitoProvvedimento;
	private String rup;
	private String dataTrasmissione;
	
	
	public JasperCampionamentoListaFascicoliDTO() { }
	
	public JasperCampionamentoListaFascicoliDTO(FascicoloDTO fascicolo) { 
		this.enteDiRegistrazione = null;	// devo per forza popolarlo nel service
		this.codicePratica = fascicolo.getCodice();
		this.intervento = fascicolo.getOggettoIntervento();
		this.tipoProcedimento = fascicolo.getTipoProcedimento().getTextValue();
		this.numeroProvvedimento = fascicolo.getNumeroProvvedimento();
		this.dataProvvedimento = new SimpleDateFormat("dd/MM/yyyy").format(fascicolo.getDataRilascioAutorizzazione());
		this.esitoProvvedimento = fascicolo.getEsito().getTextValue();
		this.rup = fascicolo.getRup();
		this.dataTrasmissione = new SimpleDateFormat("dd/MM/yyyy").format(fascicolo.getDataTrasmissione());
	}
	
	
	public String getCodicePratica() {
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica) {
		this.codicePratica = codicePratica;
	}
	public String getEnteDiRegistrazione() {
		return enteDiRegistrazione;
	}
	public void setEnteDiRegistrazione(String enteDiRegistrazione) {
		this.enteDiRegistrazione = enteDiRegistrazione;
	}
	public String getIntervento() {
		return intervento;
	}
	public void setIntervento(String intervento) {
		this.intervento = intervento;
	}
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	public String getDataProvvedimento() {
		return dataProvvedimento;
	}
	public void setDataProvvedimento(String dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	public String getEsitoProvvedimento() {
		return esitoProvvedimento;
	}
	public void setEsitoProvvedimento(String esitoProvvedimento) {
		this.esitoProvvedimento = esitoProvvedimento;
	}
	public String getRup() {
		return rup;
	}
	public void setRup(String rup) {
		this.rup = rup;
	}
	public String getDataTrasmissione() {
		return dataTrasmissione;
	}
	public void setDataTrasmissione(String dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codicePratica == null) ? 0 : codicePratica.hashCode());
		result = prime * result + ((dataProvvedimento == null) ? 0 : dataProvvedimento.hashCode());
		result = prime * result + ((dataTrasmissione == null) ? 0 : dataTrasmissione.hashCode());
		result = prime * result + ((enteDiRegistrazione == null) ? 0 : enteDiRegistrazione.hashCode());
		result = prime * result + ((esitoProvvedimento == null) ? 0 : esitoProvvedimento.hashCode());
		result = prime * result + ((intervento == null) ? 0 : intervento.hashCode());
		result = prime * result + ((numeroProvvedimento == null) ? 0 : numeroProvvedimento.hashCode());
		result = prime * result + ((rup == null) ? 0 : rup.hashCode());
		result = prime * result + ((tipoProcedimento == null) ? 0 : tipoProcedimento.hashCode());
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
		JasperCampionamentoListaFascicoliDTO other = (JasperCampionamentoListaFascicoliDTO) obj;
		if (codicePratica == null) {
			if (other.codicePratica != null)
				return false;
		} else if (!codicePratica.equals(other.codicePratica))
			return false;
		if (dataProvvedimento == null) {
			if (other.dataProvvedimento != null)
				return false;
		} else if (!dataProvvedimento.equals(other.dataProvvedimento))
			return false;
		if (dataTrasmissione == null) {
			if (other.dataTrasmissione != null)
				return false;
		} else if (!dataTrasmissione.equals(other.dataTrasmissione))
			return false;
		if (enteDiRegistrazione == null) {
			if (other.enteDiRegistrazione != null)
				return false;
		} else if (!enteDiRegistrazione.equals(other.enteDiRegistrazione))
			return false;
		if (esitoProvvedimento == null) {
			if (other.esitoProvvedimento != null)
				return false;
		} else if (!esitoProvvedimento.equals(other.esitoProvvedimento))
			return false;
		if (intervento == null) {
			if (other.intervento != null)
				return false;
		} else if (!intervento.equals(other.intervento))
			return false;
		if (numeroProvvedimento == null) {
			if (other.numeroProvvedimento != null)
				return false;
		} else if (!numeroProvvedimento.equals(other.numeroProvvedimento))
			return false;
		if (rup == null) {
			if (other.rup != null)
				return false;
		} else if (!rup.equals(other.rup))
			return false;
		if (tipoProcedimento == null) {
			if (other.tipoProcedimento != null)
				return false;
		} else if (!tipoProcedimento.equals(other.tipoProcedimento))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "JasperCampionamentoListaFascicoliDTO [codicePratica=" + codicePratica + ", enteDiRegistrazione="
				+ enteDiRegistrazione + ", intervento=" + intervento + ", tipoProcedimento=" + tipoProcedimento
				+ ", numeroProvvedimento=" + numeroProvvedimento + ", dataProvvedimento=" + dataProvvedimento
				+ ", esitoProvvedimento=" + esitoProvvedimento + ", rup=" + rup + ", dataTrasmissione="
				+ dataTrasmissione + "]";
	}
	
}