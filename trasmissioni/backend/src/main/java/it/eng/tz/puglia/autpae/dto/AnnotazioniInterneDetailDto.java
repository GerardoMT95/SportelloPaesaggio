/**
 * 
 */
package it.eng.tz.puglia.autpae.dto;

import java.util.List;

import it.eng.tz.puglia.autpae.generated.orm.dto.AnnotazioniInterneDTO;

/**
 * @author Raffaele Del Basso, Marta Zecchillo
 * @date 07 set 2021
 **/
public class AnnotazioniInterneDetailDto  {

	/**
	 * 
	 **/
	private static final long serialVersionUID = 1L;
	
	private AnnotazioniInterneDTO annotazioneBase;

	private List<AllegatoCustomDTO> allegati;

	public List<AllegatoCustomDTO> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatoCustomDTO> allegati) {
		this.allegati = allegati;
	}

	public AnnotazioniInterneDTO getAnnotazioneBase() {
		return annotazioneBase;
	}

	public void setAnnotazioneBase(AnnotazioniInterneDTO annotazioneBase) {
		this.annotazioneBase = annotazioneBase;
	}
	
}
