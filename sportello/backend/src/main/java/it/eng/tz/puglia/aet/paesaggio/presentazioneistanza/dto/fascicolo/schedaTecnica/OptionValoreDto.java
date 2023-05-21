package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.AllegatoDto;

public class OptionValoreDto implements Serializable
{
	private static final long serialVersionUID = 5249046428660044708L;
	private String valore;
	private TipoOpzione tipoOpzione;
	private String specificaValore;
	private List<OptionValoreDto> valoriOpzione;
	private AllegatoDto allegato; 
	
	public OptionValoreDto()
	{
		super();
	}
	
	public String getValore()
	{
		return valore;
	}
	public void setValore(String valore)
	{
		this.valore = valore;
	}

	public TipoOpzione getTipoOpzione()
	{
		return tipoOpzione;
	}
	public void setTipoOpzione(TipoOpzione tipoOpzione)
	{
		this.tipoOpzione = tipoOpzione;
	}

	public String getSpecificaValore()
	{
		return specificaValore;
	}
	public void setSpecificaValore(String specificaValore)
	{
		this.specificaValore = specificaValore;
	}

	public List<OptionValoreDto> getValoriOpzione()
	{
		return valoriOpzione;
	}
	public void setValoriOpzione(List<OptionValoreDto> valoriOpzione)
	{
		this.valoriOpzione = valoriOpzione;
	}

	public AllegatoDto getAllegato()
	{
		return allegato;
	}
	public void setAllegato(AllegatoDto allegato)
	{
		this.allegato = allegato;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allegato == null) ? 0 : allegato.hashCode());
		result = prime * result + ((specificaValore == null) ? 0 : specificaValore.hashCode());
		result = prime * result + ((tipoOpzione == null) ? 0 : tipoOpzione.hashCode());
		result = prime * result + ((valore == null) ? 0 : valore.hashCode());
		result = prime * result + ((valoriOpzione == null) ? 0 : valoriOpzione.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionValoreDto other = (OptionValoreDto) obj;
		if (allegato == null)
		{
			if (other.allegato != null)
				return false;
		} else if (!allegato.equals(other.allegato))
			return false;
		if (specificaValore == null)
		{
			if (other.specificaValore != null)
				return false;
		} else if (!specificaValore.equals(other.specificaValore))
			return false;
		if (tipoOpzione != other.tipoOpzione)
			return false;
		if (valore == null)
		{
			if (other.valore != null)
				return false;
		} else if (!valore.equals(other.valore))
			return false;
		if (valoriOpzione == null)
		{
			if (other.valoriOpzione != null)
				return false;
		} else if (!valoriOpzione.equals(other.valoriOpzione))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "OptionValore [valore=" + valore + ", tipoOpzione=" + tipoOpzione + ", specificaValore="
				+ specificaValore + ", valoriOpzione=" + valoriOpzione + ", allegato=" + allegato + "]";
	}

	public enum TipoOpzione
	{
		INTERVENTO_RIGUARDA,
		TIPO_DI_INTER_DEG_ARTT3
	}
}
