package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.ArrayList;
import java.util.List;

public class PareriAttiDto extends PraticaChild
{
	private static final long serialVersionUID = -7975223487474155034L;

	private List<TipologiaDettaglioDto> parreriInfo;
	private List<TipologiaDettaglioDto> attiInfo;

	
	public PareriAttiDto() {
		this.parreriInfo = new ArrayList<>();
		this.attiInfo    = new ArrayList<>();
	}

	public PareriAttiDto(List<TipologiaDettaglioDto> listaPareri, List<TipologiaDettaglioDto> listaAtti) {
		this.parreriInfo = listaPareri;
		this.attiInfo    = listaAtti;
	}
	
	
	public List<TipologiaDettaglioDto> getParreriInfo() {
		return parreriInfo;
	}

	public void setParreriInfo(List<TipologiaDettaglioDto> parreriInfo) {
		this.parreriInfo = parreriInfo;
	}

	public List<TipologiaDettaglioDto> getAttiInfo() {
		return attiInfo;
	}

	public void setAttiInfo(List<TipologiaDettaglioDto> attiInfo) {
		this.attiInfo = attiInfo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attiInfo == null) ? 0 : attiInfo.hashCode());
		result = prime * result + ((parreriInfo == null) ? 0 : parreriInfo.hashCode());
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
		PareriAttiDto other = (PareriAttiDto) obj;
		if (attiInfo == null) {
			if (other.attiInfo != null)
				return false;
		} else if (!attiInfo.equals(other.attiInfo))
			return false;
		if (parreriInfo == null) {
			if (other.parreriInfo != null)
				return false;
		} else if (!parreriInfo.equals(other.parreriInfo))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "PareriAttiDto [parreriInfo=" + parreriInfo + ", attiInfo=" + attiInfo + "]";
	}
	
}