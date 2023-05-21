package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.ulteriori_contesti_paesaggistici
 */
public class UlterioriContestiPaesaggisticiSearch extends BaseSearchRequestBean
{
	private static final long serialVersionUID = 9670324858L;
	// COLUMN codice
	private String codice;
	// COLUMN label
	private String label;
	// COLUMN art1
	private String art1;
	// COLUMN definizione
	private String definizione;
	// COLUMN disposizioni
	private String disposizioni;
	// COLUMN art2
	private String art2;
	// COLUMN type
	private String type;
	// COLUMN hasText
	private Boolean hastext;
	// COLUMN data_inizio_val
	private Date dataInizioVal;
	// COLUMN data_fine_val
	private Date dataFineVal;
	// COLUMN gruppo
	private String gruppo;
	// COLUMN sezione
	private String sezione;
	
	private Integer idTipoProcedimento;
	
	private Boolean isnowValid;

	public String getCodice()
	{
		return this.codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getLabel()
	{
		return this.label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getArt1()
	{
		return this.art1;
	}
	public void setArt1(String art1)
	{
		this.art1 = art1;
	}

	public String getDefinizione()
	{
		return this.definizione;
	}
	public void setDefinizione(String definizione)
	{
		this.definizione = definizione;
	}

	public String getDisposizioni()
	{
		return this.disposizioni;
	}
	public void setDisposizioni(String disposizioni)
	{
		this.disposizioni = disposizioni;
	}

	public String getArt2()
	{
		return this.art2;
	}
	public void setArt2(String art2)
	{
		this.art2 = art2;
	}

	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public Boolean getHastext()
	{
		return this.hastext;
	}
	public void setHastext(Boolean hastext)
	{
		this.hastext = hastext;
	}

	public Date getDataInizioVal()
	{
		return dataInizioVal;
	}
	public void setDataInizioVal(Date dataInizioVal)
	{
		this.dataInizioVal = dataInizioVal;
	}
	
	public Date getDataFineVal()
	{
		return dataFineVal;
	}
	public void setDataFineVal(Date dataFineVal)
	{
		this.dataFineVal = dataFineVal;
	}
	
	public String getGruppo()
	{
		return this.gruppo;
	}
	public void setGruppo(String gruppo)
	{
		this.gruppo = gruppo;
	}
	
	public String getSezione()
	{
		return sezione;
	}
	public void setSezione(String sezione)
	{
		this.sezione = sezione;
	}
	
	public Integer getIdTipoProcedimento()
	{
		return idTipoProcedimento;
	}
	public void setIdTipoProcedimento(Integer idTipoProcedimento)
	{
		this.idTipoProcedimento = idTipoProcedimento;
	}
	
	public Boolean getIsnowValid()
	{
		return isnowValid;
	}
	public void setIsnowValid(Boolean isnowValid)
	{
		this.isnowValid = isnowValid;
	}

}
