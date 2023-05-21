package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrApprovatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;

public class PptrDto extends PraticaChild
{
	private static final long serialVersionUID = -2073527263802843238L;
	
	private String ambitoPaesaggistico;
    private String figura;
    private Boolean art103;
    private Boolean art142;
    private String descrizione;  //non  utilizzato
    private List<PptrSelezioniDTO> ulteririContestiPaesaggistici;
    
    public PptrDto()
    {
    	super();
    }
    public PptrDto(PptrApprovatoDTO pptr)
    {
    	this.ambitoPaesaggistico = pptr.getAmbitoPaesaggistico();
    	this.figura = pptr.getFigureAmbito();
    	this.art103 = pptr.getRicadeTerrArt103Co56();
    	this.art142 = pptr.getRicadeTerrArt142Co2();
    }
    
	public String getAmbitoPaesaggistico()
	{
		return ambitoPaesaggistico;
	}
	public void setAmbitoPaesaggistico(String ambitoPaesaggistico)
	{
		this.ambitoPaesaggistico = ambitoPaesaggistico;
	}
	
	public String getFigura()
	{
		return figura;
	}
	public void setFigura(String figura)
	{
		this.figura = figura;
	}
	
	public Boolean getArt103()
	{
		return art103;
	}
	public void setArt103(Boolean art103)
	{
		this.art103 = art103;
	}
	
	public Boolean getArt142()
	{
		return art142;
	}
	public void setArt142(Boolean art142)
	{
		this.art142 = art142;
	}
	
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	
	public List<PptrSelezioniDTO> getUlteririContestiPaesaggistici()
	{
		return ulteririContestiPaesaggistici;
	}
	public void setUlteririContestiPaesaggistici(List<PptrSelezioniDTO> ulteririContestiPaesaggistici)
	{
		this.ulteririContestiPaesaggistici = ulteririContestiPaesaggistici;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ambitoPaesaggistico == null) ? 0 : ambitoPaesaggistico.hashCode());
		result = prime * result + ((art103 == null) ? 0 : art103.hashCode());
		result = prime * result + ((art142 == null) ? 0 : art142.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((figura == null) ? 0 : figura.hashCode());
		result = prime * result
				+ ((ulteririContestiPaesaggistici == null) ? 0 : ulteririContestiPaesaggistici.hashCode());
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
		PptrDto other = (PptrDto) obj;
		if (ambitoPaesaggistico == null)
		{
			if (other.ambitoPaesaggistico != null)
				return false;
		} else if (!ambitoPaesaggistico.equals(other.ambitoPaesaggistico))
			return false;
		if (art103 == null)
		{
			if (other.art103 != null)
				return false;
		} else if (!art103.equals(other.art103))
			return false;
		if (art142 == null)
		{
			if (other.art142 != null)
				return false;
		} else if (!art142.equals(other.art142))
			return false;
		if (descrizione == null)
		{
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (figura == null)
		{
			if (other.figura != null)
				return false;
		} else if (!figura.equals(other.figura))
			return false;
		if (ulteririContestiPaesaggistici == null)
		{
			if (other.ulteririContestiPaesaggistici != null)
				return false;
		} else if (!ulteririContestiPaesaggistici.equals(other.ulteririContestiPaesaggistici))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "PptrDto [ambitoPaesaggistico=" + ambitoPaesaggistico + ", figura=" + figura + ", art103=" + art103
				+ ", art142=" + art142 + ", descrizione=" + descrizione + ", ulteririContestiPaesaggistici="
				+ ulteririContestiPaesaggistici + "]";
	}

}
