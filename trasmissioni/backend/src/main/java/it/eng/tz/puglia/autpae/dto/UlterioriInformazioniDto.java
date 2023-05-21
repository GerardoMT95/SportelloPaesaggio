package it.eng.tz.puglia.autpae.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * vincolistica selezionata e selezionabile nel pannello di localizzazione del
 * comune i-esimo
 * 
 * @author acolaianni
 */
public class UlterioriInformazioniDto {

	private List<String> bpParchiEReserve;
	private List<SelectOptionDto> bpParchiEReserveOptions;
	private List<String> ucpPaesaggioRurale;
	private List<SelectOptionDto> ucpPaesaggioRuraleOptions;
	private List<String> bpImmobileAreePubblico;
	private List<SelectOptionDto> bpImmobileAreePubblicoOptions;

	public UlterioriInformazioniDto() {
		this.bpParchiEReserve = new ArrayList<>();
		this.bpParchiEReserveOptions = new ArrayList<>();
		this.ucpPaesaggioRurale = new ArrayList<>();
		this.ucpPaesaggioRuraleOptions = new ArrayList<>();
		this.bpImmobileAreePubblico = new ArrayList<>();
		this.bpImmobileAreePubblicoOptions = new ArrayList<>();
	}

	public List<String> getBpParchiEReserve() {
		return bpParchiEReserve;
	}

	public void setBpParchiEReserve(List<String> bpParchiEReserve) {
		this.bpParchiEReserve = bpParchiEReserve;
	}

	public List<SelectOptionDto> getBpParchiEReserveOptions() {
		return bpParchiEReserveOptions;
	}

	public void setBpParchiEReserveOptions(List<SelectOptionDto> bpParchiEReserveOptions) {
		this.bpParchiEReserveOptions = bpParchiEReserveOptions;
	}

	public List<String> getUcpPaesaggioRurale() {
		return ucpPaesaggioRurale;
	}

	public void setUcpPaesaggioRurale(List<String> ucpPaesaggioRurale) {
		this.ucpPaesaggioRurale = ucpPaesaggioRurale;
	}

	public List<SelectOptionDto> getUcpPaesaggioRuraleOptions() {
		return ucpPaesaggioRuraleOptions;
	}

	public void setUcpPaesaggioRuraleOptions(List<SelectOptionDto> ucpPaesaggioRuraleOptions) {
		this.ucpPaesaggioRuraleOptions = ucpPaesaggioRuraleOptions;
	}

	public List<String> getBpImmobileAreePubblico() {
		return bpImmobileAreePubblico;
	}

	public void setBpImmobileAreePubblico(List<String> bpImmobileAreePubblico) {
		this.bpImmobileAreePubblico = bpImmobileAreePubblico;
	}

	public List<SelectOptionDto> getBpImmobileAreePubblicoOptions() {
		return bpImmobileAreePubblicoOptions;
	}

	public void setBpImmobileAreePubblicoOptions(List<SelectOptionDto> bpImmobileAreePubblicoOptions) {
		this.bpImmobileAreePubblicoOptions = bpImmobileAreePubblicoOptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bpImmobileAreePubblico == null) ? 0 : bpImmobileAreePubblico.hashCode());
		result = prime * result
				+ ((bpImmobileAreePubblicoOptions == null) ? 0 : bpImmobileAreePubblicoOptions.hashCode());
		result = prime * result + ((bpParchiEReserve == null) ? 0 : bpParchiEReserve.hashCode());
		result = prime * result + ((bpParchiEReserveOptions == null) ? 0 : bpParchiEReserveOptions.hashCode());
		result = prime * result + ((ucpPaesaggioRurale == null) ? 0 : ucpPaesaggioRurale.hashCode());
		result = prime * result + ((ucpPaesaggioRuraleOptions == null) ? 0 : ucpPaesaggioRuraleOptions.hashCode());
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
		UlterioriInformazioniDto other = (UlterioriInformazioniDto) obj;
		if (bpImmobileAreePubblico == null) {
			if (other.bpImmobileAreePubblico != null)
				return false;
		} else if (!bpImmobileAreePubblico.equals(other.bpImmobileAreePubblico))
			return false;
		if (bpImmobileAreePubblicoOptions == null) {
			if (other.bpImmobileAreePubblicoOptions != null)
				return false;
		} else if (!bpImmobileAreePubblicoOptions.equals(other.bpImmobileAreePubblicoOptions))
			return false;
		if (bpParchiEReserve == null) {
			if (other.bpParchiEReserve != null)
				return false;
		} else if (!bpParchiEReserve.equals(other.bpParchiEReserve))
			return false;
		if (bpParchiEReserveOptions == null) {
			if (other.bpParchiEReserveOptions != null)
				return false;
		} else if (!bpParchiEReserveOptions.equals(other.bpParchiEReserveOptions))
			return false;
		if (ucpPaesaggioRurale == null) {
			if (other.ucpPaesaggioRurale != null)
				return false;
		} else if (!ucpPaesaggioRurale.equals(other.ucpPaesaggioRurale))
			return false;
		if (ucpPaesaggioRuraleOptions == null) {
			if (other.ucpPaesaggioRuraleOptions != null)
				return false;
		} else if (!ucpPaesaggioRuraleOptions.equals(other.ucpPaesaggioRuraleOptions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UlterioriInformazioniDto [bpParchiEReserve=" + bpParchiEReserve + ", bpParchiEReserveOptions="
				+ bpParchiEReserveOptions + ", ucpPaesaggioRurale=" + ucpPaesaggioRurale
				+ ", ucpPaesaggioRuraleOptions=" + ucpPaesaggioRuraleOptions + ", bpImmobileAreePubblico="
				+ bpImmobileAreePubblico + ", bpImmobileAreePubblicoOptions=" + bpImmobileAreePubblicoOptions + "]";
	}

}