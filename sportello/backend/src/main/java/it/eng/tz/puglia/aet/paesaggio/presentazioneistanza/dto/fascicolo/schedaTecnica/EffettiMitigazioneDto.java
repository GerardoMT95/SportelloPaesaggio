package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.EffettiMitigazioneDTO;

public class EffettiMitigazioneDto extends PraticaChild
{
	private static final long serialVersionUID = -3160267444451601380L;
	
	private String descrizione;
    private String effeti;
    private String misure;
    private String contenutiPercettivi;
    
    
    public EffettiMitigazioneDto()
    {
    	super();
    }
	
    public EffettiMitigazioneDto(EffettiMitigazioneDTO entity)
    {
    	this.descrizione = entity.getDescrStatoAttuale();
    	this.effeti = entity.getEffettiRealizOpera();
    	this.misure = entity.getMitigazioneImpInterv();
    	this.contenutiPercettivi = entity.getIndicazContenutiPercettivi();
    }
    
    
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public String getEffeti()
	{
		return effeti;
	}
	public void setEffeti(String effeti)
	{
		this.effeti = effeti;
	}
	
	public String getMisure()
	{
		return misure;
	}
	public void setMisure(String misure)
	{
		this.misure = misure;
	}
	
	public String getContenutiPercettivi()
	{
		return contenutiPercettivi;
	}
	public void setContenutiPercettivi(String contenutiPercettivi)
	{
		this.contenutiPercettivi = contenutiPercettivi;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contenutiPercettivi == null) ? 0 : contenutiPercettivi.hashCode());
		result = prime * result
				+ ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((effeti == null) ? 0 : effeti.hashCode());
		result = prime * result + ((misure == null) ? 0 : misure.hashCode());
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
		EffettiMitigazioneDto other = (EffettiMitigazioneDto) obj;
		if (contenutiPercettivi == null)
		{
			if (other.contenutiPercettivi != null)
				return false;
		} else if (!contenutiPercettivi.equals(other.contenutiPercettivi))
			return false;
		if (descrizione == null)
		{
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (effeti == null)
		{
			if (other.effeti != null)
				return false;
		} else if (!effeti.equals(other.effeti))
			return false;
		if (misure == null)
		{
			if (other.misure != null)
				return false;
		} else if (!misure.equals(other.misure))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "EffetiMitigazioneDto [descrizione=" + descrizione + ", effeti="
				+ effeti + ", misure=" + misure + ", contenutiPercettivi=" + contenutiPercettivi + "]";
	}
    
}
