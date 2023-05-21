package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProcedimentiContenziosoDTO;

public class ProcedimentoContenziosoDto extends PraticaChild
{
	private static final long serialVersionUID = -7975223487474155034L;
	
	private Boolean contenzisoAtto;
	private String descrizione;
	
	
	public ProcedimentoContenziosoDto()
	{
		super();
	}
	
	public ProcedimentoContenziosoDto(ProcedimentiContenziosoDTO entity) {
		 if (entity.getFlagContenziosoInAtto()==null) 		this.contenzisoAtto = null;
	else if (entity.getFlagContenziosoInAtto().equals("S")) this.contenzisoAtto = true;
	else if (entity.getFlagContenziosoInAtto().equals("N")) this.contenzisoAtto = false;
	else 											  		this.contenzisoAtto = null;
	this.descrizione = entity.getDescrizione();
	}
	
	
	public Boolean getContenzisoAtto()
	{
		return contenzisoAtto;
	}
	public void setContenzisoAtto(Boolean contenzisoAtto)
	{
		this.contenzisoAtto = contenzisoAtto;
	}
	
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contenzisoAtto == null) ? 0 : contenzisoAtto.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
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
		ProcedimentoContenziosoDto other = (ProcedimentoContenziosoDto) obj;
		if (contenzisoAtto == null)
		{
			if (other.contenzisoAtto != null)
				return false;
		} else if (!contenzisoAtto.equals(other.contenzisoAtto))
			return false;
		if (descrizione == null)
		{
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ProcedimentoContenziosoDto [contenzisoAtto=" + contenzisoAtto + ", descrizione=" + descrizione + "]";
	}
	
}