package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo;

import java.io.Serializable;

public class SimplePraticaDTO implements Serializable
{
	private static final long serialVersionUID = 7853076907952791898L;
	private String oggetto;
	private String codicePraticaAppptr;
	
	public String getOggetto()
	{
		return oggetto;
	}
	public void setOggetto(String oggetto)
	{
		this.oggetto = oggetto;
	}
	
	public String getCodicePraticaAppptr()
	{
		return codicePraticaAppptr;
	}
	public void setCodicePraticaAppptr(String codicePraticaAppptr)
	{
		this.codicePraticaAppptr = codicePraticaAppptr;
	}
	
}
