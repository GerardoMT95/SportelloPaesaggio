package it.eng.tz.puglia.autpae.civilia.migration.dto.putt;

public class LocalizzazionePuttBean
{
	private Long praticaId;
	private String codicePratica;
	private String comune;
	private String codCat;
	private String livello;
	private String sezione;
	private String foglio;
	private String particella;
	private String sub;
	private String dataRiferimentoCatastale;
	private Integer codIstat;
	
	public Long getPraticaId()
	{
		return praticaId;
	}
	public void setPraticaId(Long praticaId)
	{
		this.praticaId = praticaId;
	}
	
	public String getCodicePratica()
	{
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica)
	{
		this.codicePratica = codicePratica;
	}
	
	public String getComune()
	{
		return comune;
	}
	public void setComune(String comune)
	{
		this.comune = comune;
	}
	
	public String getCodCat()
	{
		return codCat;
	}
	public void setCodCat(String codCat)
	{
		this.codCat = codCat;
	}
	
	public String getLivello()
	{
		return livello;
	}
	public void setLivello(String livello)
	{
		this.livello = livello;
	}
	
	public String getSezione()
	{
		return sezione;
	}
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}
	
	public String getFoglio()
	{
		return foglio;
	}
	public void setFoglio(String foglio)
	{
		this.foglio = foglio;
	}
	
	public String getParticella()
	{
		return particella;
	}
	public void setParticella(String particella)
	{
		this.particella = particella;
	}
	
	public String getSub()
	{
		return sub;
	}
	public void setSub(String sub)
	{
		this.sub = sub;
	}
	
	public String getDataRiferimentoCatastale()
	{
		return dataRiferimentoCatastale;
	}
	public void setDataRiferimentoCatastale(String dataRiferimentoCatastale)
	{
		this.dataRiferimentoCatastale = dataRiferimentoCatastale;
	}
	
	public Integer getCodIstat()
	{
		return codIstat;
	}
	public void setCodIstat(Integer codIstat)
	{
		this.codIstat = codIstat;
	}
	
	
}
