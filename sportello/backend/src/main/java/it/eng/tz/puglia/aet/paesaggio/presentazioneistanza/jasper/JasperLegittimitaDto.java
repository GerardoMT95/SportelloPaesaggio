package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologiaDettaglioDto;

public class JasperLegittimitaDto {
	
	private Integer tipoLegittimitaUrbanistica;
	private String legittimitaUrbanstica;
	private List<TipologiaDettaglioDto> legittimitaInfo;
	
	private Integer tipoLegittimitaPaesaggistica;
	//imposizione del vincolo dto -->
	private String tipologiaVincolo;
	private Date dataIntervento;
    private Date dataImposizioneVincolo;
    // <--
	private List<TipologiaDettaglioDto> autorizzatoPaesaggisticamenteInfo;
	
	public JasperLegittimitaDto() {
		super();
	}
	
	public Integer getTipoLegittimitaUrbanistica() {
		return tipoLegittimitaUrbanistica;
	}
	public void setTipoLegittimitaUrbanistica(Integer tipoLegittimitaUrbanistica) {
		this.tipoLegittimitaUrbanistica = tipoLegittimitaUrbanistica;
	}
	public String getLegittimitaUrbanstica() {
		return legittimitaUrbanstica;
	}
	public void setLegittimitaUrbanstica(String legittimitaUrbanstica) {
		this.legittimitaUrbanstica = legittimitaUrbanstica;
	}
	public List<TipologiaDettaglioDto> getLegittimitaInfo() {
		return legittimitaInfo;
	}
	public void setLegittimitaInfo(List<TipologiaDettaglioDto> legittimitaInfo) {
		this.legittimitaInfo = legittimitaInfo;
	}
	public Integer getTipoLegittimitaPaesaggistica() {
		return tipoLegittimitaPaesaggistica;
	}
	public void setTipoLegittimitaPaesaggistica(Integer tipoLegittimitaPaesaggistica) {
		this.tipoLegittimitaPaesaggistica = tipoLegittimitaPaesaggistica;
	}
	public String getTipologiaVincolo() {
		return tipologiaVincolo;
	}
	public void setTipologiaVincolo(String tipologiaVincolo) {
		this.tipologiaVincolo = tipologiaVincolo;
	}
	public Date getDataIntervento() {
		return dataIntervento;
	}
	public void setDataIntervento(Date dataIntervento) {
		this.dataIntervento = dataIntervento;
	}
	public Date getDataImposizioneVincolo() {
		return dataImposizioneVincolo;
	}
	public void setDataImposizioneVincolo(Date dataImposizioneVincolo) {
		this.dataImposizioneVincolo = dataImposizioneVincolo;
	}
	public List<TipologiaDettaglioDto> getAutorizzatoPaesaggisticamenteInfo() {
		return autorizzatoPaesaggisticamenteInfo;
	}
	public void setAutorizzatoPaesaggisticamenteInfo(List<TipologiaDettaglioDto> autorizzatoPaesaggisticamenteInfo) {
		this.autorizzatoPaesaggisticamenteInfo = autorizzatoPaesaggisticamenteInfo;
	}

}
