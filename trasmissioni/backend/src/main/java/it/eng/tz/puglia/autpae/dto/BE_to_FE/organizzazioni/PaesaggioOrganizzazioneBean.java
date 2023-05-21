package it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneAttributiDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;

public class PaesaggioOrganizzazioneBean extends PaesaggioOrganizzazioneDTO
{
	private static final long serialVersionUID = 6279986713718874857L;
	
	private java.util.Date dataScadenzaAbilitazione;
	private boolean enabled;
	private java.util.List<EnteBean> enti;
	
	

	public java.util.Date getDataScadenzaAbilitazione()
	{
		return dataScadenzaAbilitazione;
	}
	public void setDataScadenzaAbilitazione(java.util.Date dataScadenzaAbilitazione)
	{
		this.dataScadenzaAbilitazione = dataScadenzaAbilitazione;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public java.util.List<EnteBean> getEnti()
	{
		return enti;
	}
	public void setEnti(java.util.List<EnteBean> enti)
	{
		this.enti = enti;
	}
	
	public PaesaggioOrganizzazioneAttributiDTO getBeanAttivazione(Integer idApplicazione)
	{
		PaesaggioOrganizzazioneAttributiDTO attivaBean = new PaesaggioOrganizzazioneAttributiDTO();
		attivaBean.setDataCreazione(new java.util.Date());
		attivaBean.setPaesaggioOrganizzazioneId(getId());
		if(getDataScadenzaAbilitazione() == null)
			attivaBean.setDataTermine(getDataTermine());
		else
			attivaBean.setDataTermine(getDataScadenzaAbilitazione());
		if(idApplicazione != null) attivaBean.setApplicazioneId(idApplicazione);
		return attivaBean;
	}
	public PaesaggioOrganizzazioneAttributiDTO getBeanAttivazione()
	{
		return getBeanAttivazione(null);
	}
}
