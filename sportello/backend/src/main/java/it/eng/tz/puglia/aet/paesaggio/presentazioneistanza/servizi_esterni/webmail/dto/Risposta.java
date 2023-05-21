package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.dto;

import it.eng.tz.regione.puglia.webmail.be.dto.InvioMailResultDto;

public class Risposta {
	
	private Integer codiceEsitoOperazione;
	private String  descrizioneEsitoOperazione;
	private InvioMailResultDto payload;
	
	public Integer getCodiceEsitoOperazione() {
		return codiceEsitoOperazione;
	}
	
	public void setCodiceEsitoOperazione(Integer codiceEsitoOperazione) {
		this.codiceEsitoOperazione = codiceEsitoOperazione;
	}
	
	public String getDescrizioneEsitoOperazione() {
		return descrizioneEsitoOperazione;
	}
	
	public void setDescrizioneEsitoOperazione(String descrizioneEsitoOperazione) {
		this.descrizioneEsitoOperazione = descrizioneEsitoOperazione;
	}
	
	public InvioMailResultDto getPayload() {
		return payload;
	}
	
	public void setPayload(InvioMailResultDto payload) {
		this.payload = payload;
	}
	
}
