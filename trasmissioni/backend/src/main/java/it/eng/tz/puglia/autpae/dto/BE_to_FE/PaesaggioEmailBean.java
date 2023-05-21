package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;

public class PaesaggioEmailBean extends PaesaggioEmailDTO
{
	private static final long serialVersionUID = -6827297470861866250L;
	
	private String denominazioneEnte;
	private String denominazioneOrganizzazione;
	private TipoOrganizzazioneSpecifica tipoOrganizzazione;

	public String getDenominazioneEnte()
	{
		return denominazioneEnte;
	}
	public void setDenominazioneEnte(String denominazioneEnte)
	{
		this.denominazioneEnte = denominazioneEnte;
	}
	
	public String getDenominazioneOrganizzazione()
	{
		return denominazioneOrganizzazione;
	}
	public void setDenominazioneOrganizzazione(String denominazioneOrganizzazione)
	{
		this.denominazioneOrganizzazione = denominazioneOrganizzazione;
	}
	
	public TipoOrganizzazioneSpecifica getTipoOrganizzazione()
	{
		return tipoOrganizzazione;
	}
	public void setTipoOrganizzazione(TipoOrganizzazioneSpecifica tipoOrganizzazione)
	{
		this.tipoOrganizzazione = tipoOrganizzazione;
	}
	
	
}
