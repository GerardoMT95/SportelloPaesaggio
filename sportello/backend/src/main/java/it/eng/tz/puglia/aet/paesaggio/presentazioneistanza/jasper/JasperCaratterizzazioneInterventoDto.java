package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;

public class JasperCaratterizzazioneInterventoDto {

	private List<ConfigOptionValue> caratterizzazione; // value -> label della checkbox scelta; text -> stringa scritta nel campo "specifica" in FE
	private String durata;
	
	public JasperCaratterizzazioneInterventoDto() {
		super();
	}
	public List<ConfigOptionValue> getCaratterizzazione() {
		return caratterizzazione;
	}
	public void setCaratterizzazione(List<ConfigOptionValue> caratterizzazione) {
		this.caratterizzazione = caratterizzazione;
	}
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata = durata;
	}
	
}
