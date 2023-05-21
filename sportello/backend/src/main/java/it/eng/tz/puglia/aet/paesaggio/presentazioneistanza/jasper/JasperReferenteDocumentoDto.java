package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;

public class JasperReferenteDocumentoDto
{
	private Integer tipoDocumento;
	private String numero;
    private Date dataRilascio;
    private String enteRilascio;
    private Date dataScadenza;
    
    public JasperReferenteDocumentoDto()
    {
    	super();
    }
    
	public Integer getTipoDocumento()
	{
		return tipoDocumento;
	}
	public void setTipoDocumento(Integer tipoDocumento)
	{
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getNumero()
	{
		return numero;
	}
	public void setNumero(String numero)
	{
		this.numero = numero;
	}
	
	public Date getDataRilascio()
	{
		return dataRilascio;
	}
	public void setDataRilascio(Date dataRilascio)
	{
		this.dataRilascio = dataRilascio;
	}
	
	public String getEnteRilascio()
	{
		return enteRilascio;
	}
	public void setEnteRilascio(String enteRilascio)
	{
		this.enteRilascio = enteRilascio;
	}
	
	public Date getDataScadenza()
	{
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza)
	{
		this.dataScadenza = dataScadenza;
	}
    
    
}
