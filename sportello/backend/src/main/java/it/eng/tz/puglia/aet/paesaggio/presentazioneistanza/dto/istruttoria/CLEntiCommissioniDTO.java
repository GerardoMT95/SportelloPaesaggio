package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CLEntiCommissioniDTO implements Serializable
{
	private static final long serialVersionUID = 2480150173783592781L;
	@JsonIgnore
	private Long idEnte;
	@JsonIgnore
	private Long idCommissione;
	private String nomeEnte;
	private String nomeCommissione;
	private Date dataInizioVal;
	private Date dataTermineVal;
	
	public Long getIdEnte()
	{
		return idEnte;
	}
	public void setIdEnte(Long idEnte)
	{
		this.idEnte = idEnte;
	}
	
	public String getNomeEnte()
	{
		return nomeEnte;
	}
	public void setNomeEnte(String nomeEnte)
	{
		this.nomeEnte = nomeEnte;
	}
	
	public String getNomeCommissione()
	{
		return nomeCommissione;
	}
	public void setNomeCommissione(String nomeCommissione)
	{
		this.nomeCommissione = nomeCommissione;
	}
	
	public Long getIdCommissione()
	{
		return idCommissione;
	}
	public void setIdCommissione(Long idCommissione)
	{
		this.idCommissione = idCommissione;
	}
	
	public Date getDataInizioVal()
	{
		return dataInizioVal;
	}
	public void setDataInizioVal(Date dataInizioVal)
	{
		this.dataInizioVal = dataInizioVal;
	}
	
	public Date getDataTermineVal()
	{
		return dataTermineVal;
	}
	public void setDataTermineVal(Date dataTermineVal)
	{
		this.dataTermineVal = dataTermineVal;
	}
	
	
}
