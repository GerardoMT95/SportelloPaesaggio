package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperDescrizioneDto {

	private String descrizione;
	private List<JasperCaratterizzazioneInterventoDto> caratterizzazioneIntervento;
	private List<JasperQualificazioneInterventoDto> qualificazioneIntervento;
	private List<JasperTipologiaInterventoDto> tipologiaIntervento;
	
	public JasperDescrizioneDto() {
		super();
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<JasperCaratterizzazioneInterventoDto> getCaratterizzazioneIntervento() {
		return caratterizzazioneIntervento;
	}

	public void setCaratterizzazioneIntervento(List<JasperCaratterizzazioneInterventoDto> caratterizzazioneIntervento) {
		this.caratterizzazioneIntervento = caratterizzazioneIntervento;
	}

	public List<JasperQualificazioneInterventoDto> getQualificazioneIntervento() {
		return qualificazioneIntervento;
	}

	public void setQualificazioneIntervento(List<JasperQualificazioneInterventoDto> qualificazioneIntervento) {
		this.qualificazioneIntervento = qualificazioneIntervento;
	}

	public List<JasperTipologiaInterventoDto> getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setTipologiaIntervento(List<JasperTipologiaInterventoDto> tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}
	
}
