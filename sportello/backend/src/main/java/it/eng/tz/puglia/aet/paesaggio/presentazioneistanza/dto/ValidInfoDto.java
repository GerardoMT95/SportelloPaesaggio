package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;

public class ValidInfoDto implements Serializable
{
	private static final long serialVersionUID = -1222306435172494082L;
	private Boolean istanza;
	private Boolean schedaTecnica;
	private Boolean allegati;
	private String avviso;
	
	public boolean isValidAll() {
		return (
				(this.getIstanza()!=null && this.getIstanza()==true) &&
				(this.getSchedaTecnica()!=null && this.getSchedaTecnica()==true) && 
				(this.getAllegati()!=null && this.getAllegati()==true));
	}
	
	public Boolean getIstanza()
	{
		return istanza;
	}
	public void setIstanza(Boolean istanza)
	{
		this.istanza = istanza;
	}
	public Boolean getSchedaTecnica()
	{
		return schedaTecnica;
	}
	public void setSchedaTecnica(Boolean schedaTecnica)
	{
		this.schedaTecnica = schedaTecnica;
	}
	public Boolean getAllegati()
	{
		return allegati;
	}
	public void setAllegati(Boolean allegati)
	{
		this.allegati = allegati;
	}
	public String getAvviso() 
	{
		return avviso;
	}
	public void setAvviso(String avviso) 
	{
		this.avviso = avviso;
	}
	
}
