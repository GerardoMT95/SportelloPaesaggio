package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoStileQual;

/**
 * DTO for table presentazione_istanza.tipi_e_qualificazioni
 */
public class TipiEQualificazioniDTO implements Serializable
{

	private static final long serialVersionUID = 7098382015L;
	// COLUMN codice
	private String codice;
	// COLUMN stile
	private TipoStileQual stile;
	// COLUMN raggruppamento
	private TipoQualificazione raggruppamento;
	// COLUMN label
	private String label;
	// COLUMN ordine
	private int ordine;
	// COLUMN key
	private Integer key;
	// COLUMN dependency
	private Integer dependency;
	// COLUMN hasText
	private Boolean hastext;
	// COLUMN sezione
	private String sezione;

	public String getCodice()
	{
		return this.codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public TipoStileQual getStile()
	{
		return this.stile;
	}
	public void setStile(TipoStileQual stile)
	{
		this.stile = stile;
	}

	public TipoQualificazione getRaggruppamento()
	{
		return this.raggruppamento;
	}
	public void setRaggruppamento(TipoQualificazione raggruppamento)
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

	public int getOrdine()
	{
		return this.ordine;
	}
	public void setOrdine(int ordine)
	{
		this.ordine = ordine;
	}

	public Integer getKey()
	{
		return this.key;
	}
	public void setKey(Integer key)
	{
		this.key = key;
	}

	public Integer getDependency()
	{
		return this.dependency;
	}
	public void setDependency(Integer dependency)
	{
		this.dependency = dependency;
	}

	public Boolean getHastext()
	{
		return this.hastext;
	}
	public void setHastext(Boolean hastext)
	{
		this.hastext = hastext;
	}
	
	public String getSezione()
	{
		return sezione;
	}
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}

	
	
}
