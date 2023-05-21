package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class TipologicaAllegatoDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private UUID codice;
	private String label;

	public TipologicaAllegatoDTO()
	{
	}
	public TipologicaAllegatoDTO(UUID codice, String label)
	{
		this.codice = codice;
		this.label = label;
	}
	public TipologicaAllegatoDTO(AllegatiDTO dto)
	{
		codice = dto.getId();
		label = dto.getNomeFile();
	}

	public UUID getCodice()
	{
		return codice;
	}
	public void setCodice(UUID codice)
	{
		this.codice = codice;
	}

	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		TipologicaAllegatoDTO other = (TipologicaAllegatoDTO) obj;
		if (codice == null)
		{
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (label == null)
		{
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "TipologicaNumeriDTO [codice=" + codice + ", label=" + label + "]";
	}

}
