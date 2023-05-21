package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.ulteriori_contesti_paesaggistici
 */
public class UlterioriContestiPaesaggisticiDTO implements Serializable
{
	private static final long serialVersionUID = 3764587575L;
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
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date dataInizioVal;
	// COLUMN data_fine_val
	@JsonDeserialize(using = DateSqlDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date dataFineVal;
	// COLUMN gruppo
	private String gruppo;
	// COLUMN sezione
	private String sezione;
	
	private Integer order;

	
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
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
		return this.dataInizioVal;
	}
	public void setDataInizioVal(Date dataInizioVal)
	{
		this.dataInizioVal = dataInizioVal;
	}

	public Date getDataFineVal()
	{
		return this.dataFineVal;
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

}
