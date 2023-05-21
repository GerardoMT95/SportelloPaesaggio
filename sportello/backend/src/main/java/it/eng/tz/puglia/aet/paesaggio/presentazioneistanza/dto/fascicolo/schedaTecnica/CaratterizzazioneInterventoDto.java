package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DescrizioneSchedaTecnicaValuesDTO;

public class CaratterizzazioneInterventoDto implements Serializable
{
	private static final long serialVersionUID = -4743927731156432690L;
	
	private List<ConfigOptionValue> caratterizzazione;
	private String durata;
	
	public CaratterizzazioneInterventoDto()
	{
		super();
	}
	public CaratterizzazioneInterventoDto(List<DescrizioneSchedaTecnicaValuesDTO> entity)
	{
		if(entity != null && entity.size() > 0)
		{
			caratterizzazione = new ArrayList<ConfigOptionValue>();
			for(DescrizioneSchedaTecnicaValuesDTO e: entity)
			{
				if(e.getSezione().equals(TipoQualificazione.CAR_INT))
					this.caratterizzazione.add(new ConfigOptionValue(e.getCodice(), e.getText()));
				else if(e.getSezione().equals(TipoQualificazione.CAR_INT_TP))
					this.durata = e.getCodice();
			}
		}
	}

	public List<ConfigOptionValue> getCaratterizzazione()
	{
		return caratterizzazione;
	}
	public void setCaratterizzazione(List<ConfigOptionValue> caratterizzazione)
	{
		this.caratterizzazione = caratterizzazione;
	}

	public String getDurata()
	{
		return durata;
	}
	public void setDurata(String durata)
	{
		this.durata = durata;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caratterizzazione == null) ? 0 : caratterizzazione.hashCode());
		result = prime * result + ((durata == null) ? 0 : durata.hashCode());
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
		CaratterizzazioneInterventoDto other = (CaratterizzazioneInterventoDto) obj;
		if (caratterizzazione == null)
		{
			if (other.caratterizzazione != null)
				return false;
		} else if (!caratterizzazione.equals(other.caratterizzazione))
			return false;
		if (durata == null)
		{
			if (other.durata != null)
				return false;
		} else if (!durata.equals(other.durata))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CaratterizzazioneIntervento [caratterizzazione=" + caratterizzazione + ", durata=" + durata + "]";
	}
	
}
