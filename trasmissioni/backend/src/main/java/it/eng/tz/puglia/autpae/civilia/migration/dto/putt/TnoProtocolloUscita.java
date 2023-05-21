package it.eng.tz.puglia.autpae.civilia.migration.dto.putt;

import java.sql.Blob;
import java.util.Date;

public class TnoProtocolloUscita
{
	private Blob binPdfProtContent;
	private Blob binPdfContent;
	private String codicePratica;
	private String numeroProtocollo;
	private String titolarioProtocollo;
	private Date dataProtocollo;
	private String tKeDocStId;
	private String esitoProtocollazione; //SI NO
	
	
	public String getEsitoProtocollazione() {
		return esitoProtocollazione;
	}
	public void setEsitoProtocollazione(String esitoProtocollazione) {
		this.esitoProtocollazione = esitoProtocollazione;
	}
	public Blob getBinPdfProtContent()
	{
		return binPdfProtContent;
	}
	public void setBinPdfProtContent(Blob binPdfProtContent)
	{
		this.binPdfProtContent = binPdfProtContent;
	}

	public Blob getBinPdfContent()
	{
		return binPdfContent;
	}
	public void setBinPdfContent(Blob binPdfContent)
	{
		this.binPdfContent = binPdfContent;
	}
	
	public String getCodicePratica()
	{
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica)
	{
		this.codicePratica = codicePratica;
	}
	
	public String getNumeroProtocollo()
	{
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo)
	{
		this.numeroProtocollo = numeroProtocollo;
	}
	
	public String getTitolarioProtocollo()
	{
		return titolarioProtocollo;
	}
	public void setTitolarioProtocollo(String titolarioProtocollo)
	{
		this.titolarioProtocollo = titolarioProtocollo;
	}
	
	public Date getDataProtocollo()
	{
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo)
	{
		this.dataProtocollo = dataProtocollo;
	}
	
	public String gettKeDocStId()
	{
		return tKeDocStId;
	}
	public void settKeDocStId(String tKeDocStId)
	{
		this.tKeDocStId = tKeDocStId;
	}

}
