package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;

public class FascicoloStatoBean implements Serializable {
	/**
	 * 
	 */
	

	private static final long serialVersionUID = 1L;
	private PraticaDTO pratica;
	private List<SezioneAllegati> sezioniAllegati;
	
	public PraticaDTO getPratica() {
		return pratica;
	}

	public void setPratica(PraticaDTO pratica) {
		this.pratica = pratica;
	}

	public List<SezioneAllegati> getSezioniAllegati() {
		return sezioniAllegati;
	}

	public void setSezioniAllegati(List<SezioneAllegati> sezioniAllegati) {
		this.sezioniAllegati = sezioniAllegati;
	}

	}
