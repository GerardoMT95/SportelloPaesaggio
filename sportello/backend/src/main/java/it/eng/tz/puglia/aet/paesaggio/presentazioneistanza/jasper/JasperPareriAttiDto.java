package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologiaDettaglioDto;

public class JasperPareriAttiDto {
	
	private List<TipologiaDettaglioDto> parreriInfo;
	private List<TipologiaDettaglioDto> attiInfo;
	
	public JasperPareriAttiDto() {
		super();
	}
	
	public List<TipologiaDettaglioDto> getParreriInfo() {
		return parreriInfo;
	}
	public void setParreriInfo(List<TipologiaDettaglioDto> parreriInfo) {
		this.parreriInfo = parreriInfo;
	}
	public List<TipologiaDettaglioDto> getAttiInfo() {
		return attiInfo;
	}
	public void setAttiInfo(List<TipologiaDettaglioDto> attiInfo) {
		this.attiInfo = attiInfo;
	}
	
}
