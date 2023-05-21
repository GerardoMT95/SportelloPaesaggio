package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

import java.util.Comparator;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.GruppiRuoliDTO;

public class CustomGroupsComparator implements Comparator<GruppiRuoliDTO>
{
	@Override
	public int compare(GruppiRuoliDTO o1, GruppiRuoliDTO o2)
	{
		int value = 0;
		if(o1.getTipoGruppo().equals(o2.getTipoGruppo()))
			value = o1.getGruppo().compareTo(o2.getGruppo());
		else
			value = baseValueGroup(o1.getTipoGruppo()) - baseValueGroup(o2.getTipoGruppo());
		
		return value;
	}
	
	private int baseValueGroup(Gruppi g)
	{
		switch(g)
		{
		case ADMIN:
			return 1;
		case REG_:
			return 2;
		case ED_:
			return 3;
		case SBAP_:
			return 4;
		case ETI_:
			return 5;
		case CL_:
			return 6;
		default: return 0;
		}
	}
}


