package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

/**
 * classe in cui avvolgo la FeatureCollection per dare eventuali messaggi di errore in validazione
 * @author acolaianni
 *
 */
public class InfoAndGeoJsonDTO implements Serializable{
	private static final long serialVersionUID = 533808554551970976L;
	it.eng.tz.puglia.geo.util.FeatureCollection fc;
	long idAllegato;
	int codeValidazione=0; //0==OK -1=errore validazione
	String messaggioValidazione="";
	private AllegatoCustomDTO allegatoDto;
	
	public AllegatoCustomDTO getAllegatoDto() {
		return allegatoDto;
	}
	public it.eng.tz.puglia.geo.util.FeatureCollection getFc() {
		return fc;
	}
	public long getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(long idAllegato) {
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