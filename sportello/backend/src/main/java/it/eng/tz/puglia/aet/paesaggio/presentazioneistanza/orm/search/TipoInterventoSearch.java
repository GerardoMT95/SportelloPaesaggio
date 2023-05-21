package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tipo_intervento
 */
public class TipoInterventoSearch extends BaseSearchRequestBean
{

	private static final long serialVersionUID = 5818872373L;
	// COLUMN codice
	private String codice;
	// COLUMN id_pratica
	private String idPratica;
	// COLUMN artt
	private String artt;
	// COLUMN data_approvazione
	private String dataApprovazione;
	// COLUMN con
	private String con;

	public String getCodice()
	{
		return this.codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getIdPratica()
	{
		return this.idPratica;
	}
	public void setIdPratica(String idPratica)
	{
		this.idPratica = idPratica;
	}

	public String getArtt()
	{
		return this.artt;
	}
	public void setArtt(String artt)
	{
		this.artt = artt;
	}

	public String getDataApprovazione()
	{
		return this.dataApprovazione;
	}
	public void setDataApprovazione(String dataApprovazione)
	{
		this.dataApprovazione = dataApprovazione;
	}

	public String getCon()
	{
		return this.con;
	}
	public void setCon(String con)
	{
		this.con = con;
	}

}
