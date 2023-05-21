package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;
import java.util.List;

public class JasperInformazioniPersonaliDto
{
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String sesso;
	private Date natoIl;
	private String natoStato;
	private String natoProvincia;
	private String natoComune;
	private String natoCitta; //caso estero
	
	private List<JasperReferenteDocumentoDto> documento;
	
	public JasperInformazioniPersonaliDto()
	{
		super();
	}
	
	public String getCognome()
	{
		return cognome;
	}
	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}
	
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale)
	{
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getSesso()
	{
		return sesso;
	}
	public void setSesso(String sesso)
	{
		this.sesso = sesso;
	}
	
	public Date getNatoIl()
	{
		return natoIl;
	}
	public void setNatoIl(Date natoIl)
	{
		this.natoIl = natoIl;
	}
	
	public String getNatoStato()
	{
		return natoStato;
	}
	public void setNatoStato(String natoStato)
	{
		this.natoStato = natoStato;
	}
	
	public String getNatoProvincia()
	{
		return natoProvincia;
	}
	public void setNatoProvincia(String natoProvincia)
	{
		this.natoProvincia = natoProvincia;
	}
	
	public String getNatoComune()
	{
		return natoComune;
	}
	public void setNatoComune(String natoComune)
	{
		this.natoComune = natoComune;
	}
	
	public String getNatoCitta()
	{
		return natoCitta;
	}
	public void setNatoCitta(String natoCitta)
	{
		this.natoCitta = natoCitta;
	}

	public List<JasperReferenteDocumentoDto> getDocumento()
	{
		return documento;
	}
	public void setDocumento(List<JasperReferenteDocumentoDto> documento)
	{
		this.documento = documento;
	}
	
}
