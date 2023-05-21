package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tipi_e_qualificazioni
 */
public class TipiEQualificazioniSearch extends BaseSearchRequestBean
{

	private static final long serialVersionUID = 4626585175L;
	// COLUMN codice
	private String codice;
	// COLUMN stile
	private String stile;
	// COLUMN raggruppamento
	private String raggruppamento;
	// COLUMN label
	private String label;
	// COLUMN sezione
	private String sezione;

	//ricerca per tipo procedimento
	private Integer tipoProcedimento;

	public String getCodice()
	{
		return this.codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getStile()
	{
		return this.stile;
	}
	public void setStile(String stile)
	{
		this.stile = stile;
	}

	public String getRaggruppamento()
	{
		return this.raggruppamento;
	}
	public void setRaggruppamento(String raggruppamento)
	{
		this.raggruppamento = raggruppamento;
	}

	public String getLabel()
	{
		return this.label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String getSezione()
	{
		return sezione;
	}
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}
	
	public Integer getTipoProcedimento()
	{
		return tipoProcedimento;
	}
	public void setTipoProcedimento(Integer tipoProcedimento)
	{
		this.tipoProcedimento = tipoProcedimento;
	}
	
}
