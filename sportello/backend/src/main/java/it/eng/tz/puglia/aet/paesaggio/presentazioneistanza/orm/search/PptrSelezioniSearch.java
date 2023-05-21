package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pptr_selezioni
 */
public class PptrSelezioniSearch extends BaseSearchRequestBean
{
	private static final long serialVersionUID = 9630401797L;
	// COLUMN id_pratica
	private UUID idPratica;
	// COLUMN codice
	private String codice;
	// COLUMN specifica
	private String specifica;
	// COLUMN sezione
	private String sezione;

	public UUID getIdPratica()
	{
		return this.idPratica;
	}
	public void setIdPratica(UUID idPratica)
	{
		this.idPratica = idPratica;
	}

	public String getCodice()
	{
		return this.codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getSpecifica()
	{
		return this.specifica;
	}
	public void setSpecifica(String specifica)
	{
		this.specifica = specifica;
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
