package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;

public class LocalizzazioneTabDTO extends LocalizazioneDto implements Serializable
{
	private static final long serialVersionUID = 6985990040232694483L;
	
	private List<AllegatoCustomDTO> geoAllegati;
	
	
	public LocalizzazioneTabDTO() {
		this.localizzazioneComuni = new ArrayList<>();
		this.geoAllegati 		  = new ArrayList<>();
	}

	public LocalizzazioneTabDTO(List<LocalizzazioneInterventoDTO> localizzazioneComuni, List<AllegatoCustomDTO> geoAllegati) {
		this.localizzazioneComuni = localizzazioneComuni;
		this.geoAllegati = geoAllegati;
	}

	public List<AllegatoCustomDTO> getGeoAllegati() {
		return geoAllegati;
	}

	public void setGeoAllegati(List<AllegatoCustomDTO> geoAllegati) {
		this.geoAllegati = geoAllegati;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((geoAllegati == null) ? 0 : geoAllegati.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalizzazioneTabDTO other = (LocalizzazioneTabDTO) obj;
		if (geoAllegati == null) {
			if (other.geoAllegati != null)
				return false;
		} else if (!geoAllegati.equals(other.geoAllegati))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocalizzazioneTabDTO [geoAllegati=" + geoAllegati + "]";
	}
	
}