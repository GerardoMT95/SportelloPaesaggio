package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class PraticaChild implements Serializable
{
	private static final long serialVersionUID = -4419001378996241609L;
	
	@JsonIgnore
	private UUID idPratica;

	public UUID getIdPratica()
	{
		return idPratica;
	}
	public void setIdPratica(UUID idPratica)
	{
		this.idPratica = idPratica;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPratica == null) ? 0 : idPratica.hashCode());
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
		PraticaChild other = (PraticaChild) obj;
		if (idPratica == null)
		{
			if (other.idPratica != null)
				return false;
		} else if (!idPratica.equals(other.idPratica))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "PraticaChild [idPratica=" + idPratica + "]";
	}
	
}