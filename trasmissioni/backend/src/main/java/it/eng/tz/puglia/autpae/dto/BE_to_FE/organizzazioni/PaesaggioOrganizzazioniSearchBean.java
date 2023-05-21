package it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni;

import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.generated.orm.search.PaesaggioOrganizzazioneSearch;

public class PaesaggioOrganizzazioniSearchBean extends PaesaggioOrganizzazioneSearch
{
	private static final long serialVersionUID = -3036924504262899474L;
	
	private java.util.Date dataCreazioneDa;
	private java.util.Date dataCreazioneA;
	private java.util.Date dataTermineDa;
	private java.util.Date dataTermineA;
	private Boolean enabled;
	
	private java.util.List<TipoPaesaggioOrganizzazione> tipiCercati;
	private java.util.List<TipoOrganizzazioneSpecifica> tipiSpecCercati;
	
	public java.util.Date getDataCreazioneDa()
	{
		return dataCreazioneDa;
	}
	public void setDataCreazioneDa(java.util.Date dataCreazioneDa)
	{
		this.dataCreazioneDa = dataCreazioneDa;
	}
	
	public java.util.Date getDataCreazioneA()
	{
		return dataCreazioneA;
	}
	public void setDataCreazioneA(java.util.Date dataCreazioneA)
	{
		this.dataCreazioneA = dataCreazioneA;
	}
	
	public java.util.Date getDataTermineDa()
	{
		return dataTermineDa;
	}
	public void setDataTermineDa(java.util.Date dataTermineDa)
	{
		this.dataTermineDa = dataTermineDa;
	}
	
	public java.util.Date getDataTermineA()
	{
		return dataTermineA;
	}
	public void setDataTermineA(java.util.Date dataTermineA)
	{
		this.dataTermineA = dataTermineA;
	}
	
	public Boolean getEnabled()
	{
		return enabled;
	}
	public void setEnabled(Boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public java.util.List<TipoPaesaggioOrganizzazione> getTipiCercati()
	{
		return tipiCercati;
	}
	public void setTipiCercati(java.util.List<TipoPaesaggioOrganizzazione> tipiCercati)
	{
		this.tipiCercati = tipiCercati;
	}
	
	public java.util.List<TipoOrganizzazioneSpecifica> getTipiSpecCercati()
	{
		return tipiSpecCercati;
	}
	public void setTipiSpecCercati(java.util.List<TipoOrganizzazioneSpecifica> tipiSpecCercati)
	{
		this.tipiSpecCercati = tipiSpecCercati;
	}
	
}
