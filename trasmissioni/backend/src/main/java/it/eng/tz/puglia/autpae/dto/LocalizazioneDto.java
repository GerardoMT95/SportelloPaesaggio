package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;

public class LocalizazioneDto implements Serializable{

	private static final long serialVersionUID = 3458509810375884829L;
	
	protected List<LocalizzazioneInterventoDTO> localizzazioneComuni;

	
	public LocalizazioneDto() {
		this.localizzazioneComuni = new ArrayList<>();
	}
	
	
	public List<LocalizzazioneInterventoDTO> getLocalizzazioneComuni() {
		return localizzazioneComuni;
	}
	public void setLocalizzazioneComuni(List<LocalizzazioneInterventoDTO> localizzazioneComuni) {
		this.localizzazioneComuni = localizzazioneComuni;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localizzazioneComuni == null) ? 0 : localizzazioneComuni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalizazioneDto other = (LocalizazioneDto) obj;
		if (localizzazioneComuni == null) {
			if (other.localizzazioneComuni != null)
				return false;
		} else if (!localizzazioneComuni.equals(other.localizzazioneComuni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocalizazioneDto [localizzazioneComuni=" + localizzazioneComuni + "]";
	}
	
}