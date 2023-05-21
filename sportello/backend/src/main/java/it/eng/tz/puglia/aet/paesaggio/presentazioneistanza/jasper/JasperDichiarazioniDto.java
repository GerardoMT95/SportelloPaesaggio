package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JasperDichiarazioniDto
{
	private Integer titolarita;
	private String immobile;
	private Integer tipoProcedimento;
	private String titoloRappSpec;
	private String titoloAltroSpec;
	/**
	 * titolo procedimento
	 */
	private String descrstampatitolo;
	

	List<StringWrapper> disclaimer;
	
	public JasperDichiarazioniDto()
	{
		super();
		disclaimer = new ArrayList<StringWrapper>();
	}
	
	public Integer getTitolarita()
	{
		return titolarita;
	}
	public void setTitolarita(Integer titolarita)
	{
		this.titolarita = titolarita;
	}

	public String getImmobile()
	{
		return immobile;
	}
	public void setImmobile(String immobile)
	{
		this.immobile = immobile;
	}
	
	public Integer getTipoProcedimento()
	{
		return tipoProcedimento;
	}
	public void setTipoProcedimento(Integer tipoProcedimento)
	{
		this.tipoProcedimento = tipoProcedimento;
	}

	public List<StringWrapper> getDisclaimer()
	{
		return disclaimer;
	}
	public void setDisclaimer(List<StringWrapper> disclaimer)
	{
		this.disclaimer = disclaimer;
	}

	public String getTitoloAltroSpec() {
		return titoloAltroSpec;
	}

	public void setTitoloAltroSpec(String titoloAltroSpec) {
		this.titoloAltroSpec = titoloAltroSpec;
	}

	public String getTitoloRappSpec() {
		return titoloRappSpec;
	}

	public void setTitoloRappSpec(String titoloRappSpec) {
		this.titoloRappSpec = titoloRappSpec;
	}

	public String getDescrstampatitolo() {
		return descrstampatitolo;
	}

	public void setDescrstampatitolo(String descrstampatitolo) {
		this.descrstampatitolo = descrstampatitolo;
	}
	
	
	
}
