package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperTecnicoIncaricatoDto extends JasperInformazioniPersonaliDto
{
	private List<JasperIndirizzoCompletoDto> residenteIn;
	private List<JasperIndirizzoCompletoDto> conStudioIn;
	private String iscritoAllOrdine;
	private String di;
	private String n;
	private String recapitoTelefonico;
	private String fax;
	private String cellulare;
	private String pec;
	
	public JasperTecnicoIncaricatoDto()
	{
		super();
	}
	
	public List<JasperIndirizzoCompletoDto> getResidenteIn()
	{
		return residenteIn;
	}
	public void setResidenteIn(List<JasperIndirizzoCompletoDto> residenteIn)
	{
		this.residenteIn = residenteIn;
	}
	
	public List<JasperIndirizzoCompletoDto> getConStudioIn()
	{
		return conStudioIn;
	}
	public void setConStudioIn(List<JasperIndirizzoCompletoDto> conStudioIn)
	{
		this.conStudioIn = conStudioIn;
	}
	
	public String getIscritoAllOrdine()
	{
		return iscritoAllOrdine;
	}
	public void setIscritoAllOrdine(String iscritoAllOrdine)
	{
		this.iscritoAllOrdine = iscritoAllOrdine;
	}
	
	public String getDi()
	{
		return di;
	}
	public void setDi(String di)
	{
		this.di = di;
	}
	
	public String getN()
	{
		return n;
	}
	public void setN(String n)
	{
		this.n = n;
	}
	
	public String getRecapitoTelefonico()
	{
		return recapitoTelefonico;
	}
	public void setRecapitoTelefonico(String recapitoTelefonico)
	{
		this.recapitoTelefonico = recapitoTelefonico;
	}
	
	public String getFax()
	{
		return fax;
	}
	public void setFax(String fax)
	{
		this.fax = fax;
	}
	
	public String getCellulare()
	{
		return cellulare;
	}
	public void setCellulare(String cellulare)
	{
		this.cellulare = cellulare;
	}
	
	public String getPec()
	{
		return pec;
	}
	public void setPec(String pec)
	{
		this.pec = pec;
	}
	
}
