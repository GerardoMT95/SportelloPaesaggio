package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperRichiedenteDto extends JasperInformazioniPersonaliDto
{
	private List<JasperIndirizzoCompletoDto> residenteIn;
	private String recapitoTelefonico;
	private String indirizzoEmail;
	private String pec;
	private Boolean nelCaso;
	private Integer inQualitaDi;
	private String dittaInQualitaDiDescr;
	private String descAltroDitta;
	private String societa;
	private String partitaIva;
	private String societaCodiceFiscale;
	private String codiceIpa;
	private String tipoAzienda;
	
	public JasperRichiedenteDto()
	{
		super();
	}
	
	public List<JasperIndirizzoCompletoDto> getResidenteIn()
	{
		return this.residenteIn;
	}
	public void setResidenteIn(final List<JasperIndirizzoCompletoDto> residenteIn)
	{
		this.residenteIn = residenteIn;
	}
	
	public String getRecapitoTelefonico()
	{
		return this.recapitoTelefonico;
	}
	public void setRecapitoTelefonico(final String recapitoTelefonico)
	{
		this.recapitoTelefonico = recapitoTelefonico;
	}
	
	public String getIndirizzoEmail()
	{
		return this.indirizzoEmail;
	}
	public void setIndirizzoEmail(final String indirizzoEmail)
	{
		this.indirizzoEmail = indirizzoEmail;
	}
	
	public String getPec()
	{
		return this.pec;
	}
	public void setPec(final String pec)
	{
		this.pec = pec;
	}
	
	public Boolean getNelCaso()
	{
		return this.nelCaso;
	}
	public void setNelCaso(final Boolean nelCaso)
	{
		this.nelCaso = nelCaso;
	}
	
	public Integer getInQualitaDi()
	{
		return this.inQualitaDi;
	}
	public void setInQualitaDi(final Integer inQualitaDi)
	{
		this.inQualitaDi = inQualitaDi;
	}
	
	public String getDescAltroDitta()
	{
		return this.descAltroDitta;
	}
	public void setDescAltroDitta(final String descAltroDitta)
	{
		this.descAltroDitta = descAltroDitta;
	}
	
	public String getSocieta()
	{
		return this.societa;
	}
	public void setSocieta(final String societa)
	{
		this.societa = societa;
	}
	
	public String getPartitaIva()
	{
		return this.partitaIva;
	}
	public void setPartitaIva(final String partitaIva)
	{
		this.partitaIva = partitaIva;
	}
	
	public String getSocietaCodiceFiscale()
	{
		return this.societaCodiceFiscale;
	}
	public void setSocietaCodiceFiscale(final String societaCodiceFiscale)
	{
		this.societaCodiceFiscale = societaCodiceFiscale;
	}

	public String getDittaInQualitaDiDescr() {
		return this.dittaInQualitaDiDescr;
	}

	public void setDittaInQualitaDiDescr(final String dittaInQualitaDiDescr) {
		this.dittaInQualitaDiDescr = dittaInQualitaDiDescr;
	}

	/**
	 * @return the codiceIpa
	 */
	public String getCodiceIpa() {
		return this.codiceIpa;
	}

	/**
	 * @param codiceIpa the codiceIpa to set
	 */
	public void setCodiceIpa(final String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}

	/**
	 * @return the tipoAzienda
	 */
	public String getTipoAzienda() {
		return this.tipoAzienda;
	}

	/**
	 * @param tipoAzienda the tipoAzienda to set
	 */
	public void setTipoAzienda(final String tipoAzienda) {
		this.tipoAzienda = tipoAzienda;
	}
	
	
}
