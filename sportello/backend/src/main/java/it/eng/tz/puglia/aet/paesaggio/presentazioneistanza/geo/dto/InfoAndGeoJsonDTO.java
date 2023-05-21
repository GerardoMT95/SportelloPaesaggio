package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.AllegatoCustomDTO;

/**
 * classe in cui avvolgo la FeatureCollection per dare eventuali messaggi di errore in validazione
 * @author acolaianni
 *
 */
public class InfoAndGeoJsonDTO implements Serializable{
	private static final long serialVersionUID = 533808554551970976L;
	it.eng.tz.puglia.geo.util.FeatureCollection fc;
	UUID idAllegato;
	int codeValidazione=0; //0==OK -1=errore validazione
	String messaggioValidazione="";
	private AllegatoCustomDTO allegatoDto;
	
	public AllegatoCustomDTO getAllegatoDto() {
		return allegatoDto;
	}
	public it.eng.tz.puglia.geo.util.FeatureCollection getFc() {
		return fc;
	}
	public UUID getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(UUID idAllegato) {
		this.idAllegato = idAllegato;
	}
	public void setFc(it.eng.tz.puglia.geo.util.FeatureCollection fc) {
		this.fc = fc;
	}
	public int getCodeValidazione() {
		return codeValidazione;
	}
	public void setCodeValidazione(int codeValidazione) {
		this.codeValidazione = codeValidazione;
	}
	public String getMessaggioValidazione() {
		return messaggioValidazione;
	}
	public void setMessaggioValidazione(String messaggioValidazione) {
		this.messaggioValidazione = messaggioValidazione;
	}
	public void setAllegatoDto(AllegatoCustomDTO ret) {
		this.allegatoDto=ret;
	}

}