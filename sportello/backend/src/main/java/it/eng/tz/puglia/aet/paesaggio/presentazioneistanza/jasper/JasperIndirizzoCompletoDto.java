package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

public class JasperIndirizzoCompletoDto
{
	private String stato;
	private String citta;// caso estero
	private String comune;
	private String provincia;
	private String via;
	private String n;
	private String cap;
	
	public JasperIndirizzoCompletoDto()
	{
		super();
	}
	
	public String getStato()
	{
		return stato;
	}
	public void setStato(String stato)
	{
		this.stato = stato;
	}
	
	public String getCitta()
	{
		return citta;
	}
	public void setCitta(String citta)
	{
		this.citta = citta;
	}
	
	public String getComune()
	{
		return comune;
	}
	public void setComune(String comune)
	{
		this.comune = comune;
	}
	
	public String getProvincia()
	{
		return provincia;
	}
	public void setProvincia(String provincia)
	{
		this.provincia = provincia;
	}
	
	public String getVia()
	{
		return via;
	}
	public void setVia(String via)
	{
		this.via = via;
	}
	
	public String getN()
	{
		return n;
	}
	public void setN(String n)
	{
		this.n = n;
	}
	
	public String getCap()
	{
		return cap;
	}
	public void setCap(String cap)
	{
		this.cap = cap;
	}
	
	
}
