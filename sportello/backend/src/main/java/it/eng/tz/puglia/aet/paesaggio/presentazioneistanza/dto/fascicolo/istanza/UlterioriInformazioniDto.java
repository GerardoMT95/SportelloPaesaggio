package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;

/**
 * vincolistica selezionata e selezionabile nel pannello di localizzazione del comune i-esimo
 * @author acolaianni
 *
 */
public class UlterioriInformazioniDto {
	private List<String> bpParchiEReserve; 
	private List<SelectOptionDto> bpParchiEReserveOptions; 
	private List<String> ucpPaesaggioRurale;
	private List<SelectOptionDto> ucpPaesaggioRuraleOptions;
	private List<String> bpImmobileAreePubblico;
	private List<SelectOptionDto> bpImmobileAreePubblicoOptions;
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
	
}
