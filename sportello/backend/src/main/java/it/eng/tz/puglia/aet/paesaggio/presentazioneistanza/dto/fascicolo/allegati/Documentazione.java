package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class Documentazione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4507264985492125324L;
	private List<AllegatiDTO> grigliaAllegatiCaricati;
	public List<AllegatiDTO> getGrigliaAllegatiCaricati() {
		return grigliaAllegatiCaricati;
	}
	public void setGrigliaAllegatiCaricati(List<AllegatiDTO> grigliaAllegatiCaricati) {
		this.grigliaAllegatiCaricati = grigliaAllegatiCaricati;
	}
	
	
	
}
