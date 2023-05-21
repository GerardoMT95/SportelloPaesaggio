package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import java.io.Serializable;

import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;

public class EnteBean implements Serializable
{
	private static final long serialVersionUID = 891668320427485215L;
	
	private Long idEnte;
	private Long idOrganizzazioneCompetenze;
	private String denominazione;
	private TipoEnte tipoEnte;
	private java.util.Date dataTermine;
	
	public Long getIdEnte()
	{
		return idEnte;
	}
	public void setIdEnte(Long id)
	{
		this.idEnte = id;
	}
	
	public Long getIdOrganizzazioneCompetenze()
	{
		return idOrganizzazioneCompetenze;
	}
	public void setIdOrganizzazioneCompetenze(Long idOrganizzazioneCompetenze)
	{
		this.idOrganizzazioneCompetenze = idOrganizzazioneCompetenze;
	}
	
	public String getDenominazione()
	{
		return denominazione;
	}
	public void setDenominazione(String denominazione)
	{
		this.denominazione = denominazione;
	}
	
	public TipoEnte getTipoEnte()
	{
		return tipoEnte;
	}
	public void setTipoEnte(TipoEnte tipoEnte)
	{
		this.tipoEnte = tipoEnte;
	}
	
	public java.util.Date getDataTermine()
	{
		return dataTermine;
	}
	public void setDataTermine(java.util.Date dataTermine)
	{
		this.dataTermine = dataTermine;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataTermine == null) ? 0 : dataTermine.hashCode());
		result = prime * result + ((denominazione == null) ? 0 : denominazione.hashCode());
		result = prime * result + ((idEnte == null) ? 0 : idEnte.hashCode());
		result = prime * result + ((idOrganizzazioneCompetenze == null) ? 0 : idOrganizzazioneCompetenze.hashCode());
		result = prime * result + ((tipoEnte == null) ? 0 : tipoEnte.hashCode());
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
		EnteBean other = (EnteBean) obj;
		if (dataTermine == null)
		{
			if (other.dataTermine != null)
				return false;
		} else if (!dataTermine.equals(other.dataTermine))
			return false;
		if (denominazione == null)
		{
			if (other.denominazione != null)
				return false;
		} else if (!denominazione.equals(other.denominazione))
			return false;
		if (idEnte == null)
		{
			if (other.idEnte != null)
				return false;
		} else if (!idEnte.equals(other.idEnte))
			return false;
		if (idOrganizzazioneCompetenze == null)
		{
			if (other.idOrganizzazioneCompetenze != null)
				return false;
		} else if (!idOrganizzazioneCompetenze.equals(other.idOrganizzazioneCompetenze))
			return false;
		if (tipoEnte != other.tipoEnte)
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "EnteBean [idEnte=" + idEnte + ", idOrganizzazioneCompetenze=" + idOrganizzazioneCompetenze + ", denominazione="
				+ denominazione + ", tipoEnte=" + tipoEnte + ", dataTermine=" + dataTermine + "]";
	}
	
	
}
