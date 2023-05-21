package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;

public class JasperDichiarazioniArticoliDto
{
	private Boolean art136;
	private Boolean art136a;
	private Boolean art136b;
	private Boolean art136c;
	private Boolean art136d; 
	private Boolean art142;
	private Boolean inCasoDiVariante; 
	private String numero;
	private String da;
	private Date inData;
	
	
	public Boolean getInCasoDiVariante() {
		return inCasoDiVariante;
	}

	public void setInCasoDiVariante(Boolean inCasoDiVariante) {
		this.inCasoDiVariante = inCasoDiVariante;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDa() {
		return da;
	}

	public void setDa(String da) {
		this.da = da;
	}

	public Date getInData() {
		return inData;
	}

	public void setInData(Date inData) {
		this.inData = inData;
	}
	
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: TERRITORI_COSTIERI
	 */
	private Boolean territoriCostieri;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: TERRITORI_LAGHI
	 */
	private Boolean territoriContermini;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: ACQUE_PUBBLICHE
	 */
	private Boolean fiumiTorrenti;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: PARCHI_E_RISERVE
	 */
	private Boolean parchiRiserve;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: BOSCHI
	 */
	private Boolean boschi;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: ZONE_USI_CIVICI
	 */
	private Boolean usiCivici;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: ZONE_UMIDE_RAMSAR
	 */
	private Boolean zoneUmide;
	/**
	 * Tabella: ulteriori_contesti_paesaggistici
	 * Codice: ZONE_INT_ARCH
	 */
	private Boolean interesseArcheologico;
	
	private Boolean art134;
	
	public JasperDichiarazioniArticoliDto()
	{
		art136 = false;
		art134 = false;
		art136a =false;
		art136b = false;
		art136c = false;
		art136d = false;
		art142 = false;
		territoriCostieri = false;
		territoriContermini = false;
		fiumiTorrenti = false;
		parchiRiserve = false;
		boschi = false;
		usiCivici = false;
		zoneUmide = false;
		interesseArcheologico = false;
	}
	
	public Boolean getArt136()
	{
		return art136;
	}
	public void setArt136(Boolean art136)
	{
		this.art136 = art136;
	}

	public Boolean getArt136a()
	{
		return art136a;
	}
	public void setArt136a(Boolean art136a)
	{
		this.art136a = art136a;
	}

	public Boolean getArt136b()
	{
		return art136b;
	}
	public void setArt136b(Boolean art136b)
	{
		this.art136b = art136b;
	}

	public Boolean getArt136c()
	{
		return art136c;
	}
	public void setArt136c(Boolean art136c)
	{
		this.art136c = art136c;
	}

	public Boolean getArt136d()
	{
		return art136d;
	}
	public void setArt136d(Boolean art136d)
	{
		this.art136d = art136d;
	}

	public Boolean getArt142()
	{
		return art142;
	}
	public void setArt142(Boolean art142)
	{
		this.art142 = art142;
	}

	public Boolean getTerritoriCostieri()
	{
		return territoriCostieri;
	}
	public void setTerritoriCostieri(Boolean territoriCostieri)
	{
		this.territoriCostieri = territoriCostieri;
	}

	public Boolean getTerritoriContermini()
	{
		return territoriContermini;
	}
	public void setTerritoriContermini(Boolean territoriContermini)
	{
		this.territoriContermini = territoriContermini;
	}

	public Boolean getFiumiTorrenti()
	{
		return fiumiTorrenti;
	}
	public void setFiumiTorrenti(Boolean fiumiTorrenti)
	{
		this.fiumiTorrenti = fiumiTorrenti;
	}

	public Boolean getParchiRiserve()
	{
		return parchiRiserve;
	}
	public void setParchiRiserve(Boolean parchiRiserve)
	{
		this.parchiRiserve = parchiRiserve;
	}

	public Boolean getBoschi()
	{
		return boschi;
	}
	public void setBoschi(Boolean boschi)
	{
		this.boschi = boschi;
	}

	public Boolean getUsiCivici()
	{
		return usiCivici;
	}
	public void setUsiCivici(Boolean usiCivici)
	{
		this.usiCivici = usiCivici;
	}

	public Boolean getZoneUmide()
	{
		return zoneUmide;
	}
	public void setZoneUmide(Boolean zoneUmide)
	{
		this.zoneUmide = zoneUmide;
	}

	public Boolean getInteresseArcheologico()
	{
		return interesseArcheologico;
	}
	public void setInteresseArcheologico(Boolean interesseArcheologico)
	{
		this.interesseArcheologico = interesseArcheologico;
	}

	public Boolean getArt134()
	{
		return art134;
	}
	public void setArt134(Boolean art134)
	{
		this.art134 = art134;
	}
	
	
}
