package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;

public abstract class JasperAbstractDomandaDto
{
	private String codiceFascicolo;
	private Integer tipoProcedimento;
	private String oggettoIntervento;
	private String descrstampasottotitolo;
	private List<JasperTecnicoIncaricatoDto> tecnicoIncaricato;
	private List<JasperLocalizzazioneDto> localizzazione;
	private List<JasperRichiedenteDto> richiedente;
	private List<JasperDelegatoDto> delegato;
	private List<JasperTipoProcedimentoDto> listTipoProcedimento;
	private String testoPrivacyHtml;
	
	public JasperAbstractDomandaDto()
	{
		super();
	}
	
	
	
	/**
	 * @return the testoPrivacyHtml
	 */
	public String getTestoPrivacyHtml() {
		return testoPrivacyHtml;
	}



	/**
	 * @param testoPrivacyHtml the testoPrivacyHtml to set
	 */
	public void setTestoPrivacyHtml(String testoPrivacyHtml) {
		this.testoPrivacyHtml = testoPrivacyHtml;
	}



	public String getCodiceFascicolo()
	{
		return codiceFascicolo;
	}
	public void setCodiceFascicolo(String codiceFascicolo)
	{
		this.codiceFascicolo = codiceFascicolo;
	}
	
	public Integer getTipoProcedimento()
	{
		return tipoProcedimento;
	}
	public void setTipoProcedimento(Integer tipoProcedimento)
	{
		this.tipoProcedimento = tipoProcedimento;
	}

	public String getOggettoIntervento()
	{
		return oggettoIntervento;
	}
	public void setOggettoIntervento(String oggettoIntervento)
	{
		this.oggettoIntervento = oggettoIntervento;
	}

	public List<JasperTecnicoIncaricatoDto> getTecnicoIncaricato()
	{
		return tecnicoIncaricato;
	}
	public void setTecnicoIncaricato(List<JasperTecnicoIncaricatoDto> tecnicoIncaricato)
	{
		this.tecnicoIncaricato = tecnicoIncaricato;
	}
	public List<JasperLocalizzazioneDto> getLocalizzazione()
	{
		return localizzazione;
	}
	public void setLocalizzazione(List<JasperLocalizzazioneDto> localizzazione)
	{
		this.localizzazione = localizzazione;
	}

	public List<JasperRichiedenteDto> getRichiedente()
	{
		return richiedente;
	}
	public void setRichiedente(List<JasperRichiedenteDto> richiedente)
	{
		this.richiedente = richiedente;
	}



	/**
	 * @return the listTipoProcedimento
	 */
	public List<JasperTipoProcedimentoDto> getListTipoProcedimento() {
		return listTipoProcedimento;
	}



	/**
	 * @param listTipoProcedimento the listTipoProcedimento to set
	 */
	public void setListTipoProcedimento(List<JasperTipoProcedimentoDto> listTipoProcedimento) {
		this.listTipoProcedimento = listTipoProcedimento;
	}



	public String getDescrstampasottotitolo() {
		return descrstampasottotitolo;
	}



	public void setDescrstampasottotitolo(String descrstampasottotitolo) {
		this.descrstampasottotitolo = descrstampasottotitolo;
	}



	/**
	 * @return the delegato
	 */
	public List<JasperDelegatoDto> getDelegato() {
		return delegato;
	}



	/**
	 * @param delegato the delegato to set
	 */
	public void setDelegato(List<JasperDelegatoDto> delegato) {
		this.delegato = delegato;
	}
	
	
	
}
