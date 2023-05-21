package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;

public class PMApplication implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String descrizione;
	private String codiceApplicazione;
	private String nomeApplicazione;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getDescrizione()
	{
		return descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}
	
	public String getCodiceApplicazione()
	{
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione)
	{
		this.codiceApplicazione = codiceApplicazione;
	}
	
	public String getNomeApplicazione()
	{
		return nomeApplicazione;
	}
	public void setNomeApplicazione(String nomeApplicazione)
	{
		this.nomeApplicazione = nomeApplicazione;
	}
	
	
}
