/**
 * 
 */
package it.eng.tz.puglia.autpae.dto;

import java.util.List;

import it.eng.tz.puglia.autpae.generated.orm.dto.RichiestePostTrasmissioneDTO;

/**
 * @author Adriano Colaianni
 * @date 30 ago 2021
 */
public class RichiestePostTrasmissioneDetailDto  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RichiestePostTrasmissioneDTO richiestaBase;

	private List<AllegatoCustomDTO> allegati;

	public List<AllegatoCustomDTO> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatoCustomDTO> allegati) {
		this.allegati = allegati;
	}

	public RichiestePostTrasmissioneDTO getRichiestaBase() {
		return richiestaBase;
	}

	public void setRichiestaBase(RichiestePostTrasmissioneDTO richiestaBase) {
		this.richiestaBase = richiestaBase;
	}
	
	
}
