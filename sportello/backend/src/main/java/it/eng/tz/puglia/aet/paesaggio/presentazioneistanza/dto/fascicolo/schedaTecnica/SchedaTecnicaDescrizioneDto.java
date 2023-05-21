package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.List;

public class SchedaTecnicaDescrizioneDto extends PraticaChild
{
	private static final long serialVersionUID = -4813639925468219626L;
	
	private String descrizione;
	private TipoInterventoDto tipoIntervento;
	private CaratterizzazioneInterventoDto caratterizzazioneIntervento;
	private List<ConfigOptionValue> qualificazioneIntervento;
	
	
	public SchedaTecnicaDescrizioneDto()
	{
		super();
	}

	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public TipoInterventoDto getTipoIntervento()
	{
		return tipoIntervento;
	}
	public void setTipoIntervento(TipoInterventoDto tipoIntervento)
	{
		this.tipoIntervento = tipoIntervento;
	}

	public CaratterizzazioneInterventoDto getCaratterizzazioneIntervento()
	{
		return caratterizzazioneIntervento;
	}
	public void setCaratterizzazioneIntervento(CaratterizzazioneInterventoDto caratterizzazioneIntervento)
	{
		this.caratterizzazioneIntervento = caratterizzazioneIntervento;
	}

	public List<ConfigOptionValue> getQualificazioneIntervento()
	{
		return qualificazioneIntervento;
	}
	public void setQualificazioneIntervento(List<ConfigOptionValue> qualificazioneIntervento)
	{
		this.qualificazioneIntervento = qualificazioneIntervento;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((caratterizzazioneIntervento == null) ? 0 : caratterizzazioneIntervento.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((qualificazioneIntervento == null) ? 0 : qualificazioneIntervento.hashCode());
		result = prime * result + ((tipoIntervento == null) ? 0 : tipoIntervento.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedaTecnicaDescrizioneDto other = (SchedaTecnicaDescrizioneDto) obj;
		if (caratterizzazioneIntervento == null)
		{
			if (other.caratterizzazioneIntervento != null)
				return false;
		} else if (!caratterizzazioneIntervento.equals(other.caratterizzazioneIntervento))
			return false;
		if (descrizione == null)
		{
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (qualificazioneIntervento == null)
		{
			if (other.qualificazioneIntervento != null)
				return false;
		} else if (!qualificazioneIntervento.equals(other.qualificazioneIntervento))
			return false;
		if (tipoIntervento == null)
		{
			if (other.tipoIntervento != null)
				return false;
		} else if (!tipoIntervento.equals(other.tipoIntervento))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SchedaTecnicaDescrizioneDto [descrizione=" + descrizione + ", tipoIntervento=" + tipoIntervento
				+ ", caratterizzazioneIntervento=" + caratterizzazioneIntervento + ", qualificazioneIntervento="
				+ qualificazioneIntervento + "]";
	}

}
