package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class CommissioneLocaleSearch implements Serializable
{
	private static final long serialVersionUID = -3759315268312693766L;
	
	private String denominazione;
	private String username;
	private Date dataValiditaDa;
	private Date dataValiditaA;
	
	@JsonIgnore
	private List<Long> ids;
	
	private String colonna;
	private Direction direzione;
	private Integer pagina;
	private Integer limite;
	
	public CommissioneLocaleSearch()
	{
		super();
		colonna = "dataCreazione";
		direzione = Direction.DESC;
	}
	
	public String getDenominazione()
	{
		return denominazione;
	}
	public void setDenominazione(String denominazione)
	{
		this.denominazione = denominazione;
	}
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public Date getDataValiditaDa()
	{
		return dataValiditaDa;
	}
	public void setDataValiditaDa(Date dataValiditaDa)
	{
		this.dataValiditaDa = dataValiditaDa;
	}

	public Date getDataValiditaA()
	{
		return dataValiditaA;
	}
	public void setDataValiditaA(Date dataValiditaA)
	{
		this.dataValiditaA = dataValiditaA;
	}

	public List<Long> getIds()
	{
		return ids;
	}
	public void setIds(List<Long> ids)
	{
		this.ids = ids;
	}

	public String getColonna()
	{
		return colonna;
	}
	public void setColonna(String colonna)
	{
		this.colonna = colonna;
	}
	
	public Direction getDirezione()
	{
		return direzione;
	}
	public void setDirezione(Direction direzione)
	{
		this.direzione = direzione;
	}
	
	public Integer getPagina()
	{
		return pagina;
	}
	public void setPagina(Integer pagina)
	{
		this.pagina = pagina;
	}
	
	public Integer getLimite()
	{
		return limite;
	}
	public void setLimite(Integer limite)
	{
		this.limite = limite;
	}

	public enum Direction
	{
		ASC,
		DESC
	}
		
}
