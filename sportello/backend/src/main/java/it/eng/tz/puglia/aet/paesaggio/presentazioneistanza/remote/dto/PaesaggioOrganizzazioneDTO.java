package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.enumeration.TipoOrganizzazione;

public class PaesaggioOrganizzazioneDTO implements Serializable
{
	private static final long serialVersionUID = 1385557025062805137L;
	private Long id;
	@JsonIgnore
	private Long enteId;
	private String denominazione;
	@JsonIgnore
	private TipoOrganizzazione tipoOrganizzazione;
	@JsonIgnore
	private String tipoOrganizzazioneSpecifica;
	@JsonIgnore
	private String codiceCivilia;
	@JsonIgnore
	private Long riferimentoOrganizzazione;
	private Date dataCreazione;
	private Date dataTermine;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getEnteId()
	{
		return enteId;
	}
	public void setEnteId(Long enteId)
	{
		this.enteId = enteId;
	}
	
	public String getDenominazione()
	{
		return denominazione;
	}
	public void setDenominazione(String denominazione)
	{
		this.denominazione = denominazione;
	}
	
	public TipoOrganizzazione getTipoOrganizzazione()
	{
		return tipoOrganizzazione;
	}
	public void setTipoOrganizzazione(TipoOrganizzazione tipoOrganizzazione)
	{
		this.tipoOrganizzazione = tipoOrganizzazione;
	}
	
	public String getTipoOrganizzazioneSpecifica()
	{
		return tipoOrganizzazioneSpecifica;
	}
	public void setTipoOrganizzazioneSpecifica(String tipoOrganizzazioneSpecifica)
	{
		this.tipoOrganizzazioneSpecifica = tipoOrganizzazioneSpecifica;
	}
	
	public String getCodiceCivilia()
	{
		return codiceCivilia;
	}
	public void setCodiceCivilia(String codiceCivilia)
	{
		this.codiceCivilia = codiceCivilia;
	}
	
	public Long getRiferimentoOrganizzazione()
	{
		return riferimentoOrganizzazione;
	}
	public void setRiferimentoOrganizzazione(Long riferimentoOrganizzazione)
	{
		this.riferimentoOrganizzazione = riferimentoOrganizzazione;
	}
	
	public Date getDataCreazione()
	{
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione)
	{
		this.dataCreazione = dataCreazione;
	}
	
	public Date getDataTermine()
	{
		return dataTermine;
	}
	public void setDataTermine(Date dataTermine)
	{
		this.dataTermine = dataTermine;
	}
	
	
}
