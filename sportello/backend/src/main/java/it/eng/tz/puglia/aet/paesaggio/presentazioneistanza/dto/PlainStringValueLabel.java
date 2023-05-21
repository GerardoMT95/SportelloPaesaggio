package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;

/**
 * pari alla SelectOption del Front-End
 * 
 * @author acolaianni
 *
 */
public class PlainStringValueLabel extends GenericPlainValueLabel<String>
{
	private static final long serialVersionUID = -5882927868673063082L;

	public PlainStringValueLabel()
	{
		super();
	}
	public PlainStringValueLabel(String codice, String descrizione)
	{
		super(codice, descrizione);
	}
	public PlainStringValueLabel(String codice, String descrizione, String linked)
	{
		super(codice, descrizione, linked);
	}

	public PlainStringValueLabel(UtenteGruppo m)
	{
		if (m != null)
		{
			setDescription(m.getNomeUtente() + " " + m.getCognomeUtente() + " [" + m.getUsernameUtente() + "]");
			setValue(m.getUsernameUtente());
		}
	}
	
	public PlainStringValueLabel(AssUtenteGruppo m)
	{
		if (m != null)
		{
			setDescription(m.getNome() + " " + m.getCognome() + " [" + m.getUsername() + "]");
			setValue(m.getUsername());
		}
	}

	@Override
	public String toString()
	{
		return "PlainStringValueLabel [value=" + getValue() + ", description=" + getDescription() + ", linked=" + getLinked() + "]";
	}

}