package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.enumeration;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;

public enum TipoOrganizzazione
{
	ED,
	CL,
	SBAP,
	REG, 
	ETI;
	
	public static Gruppi getGruppo(TipoOrganizzazione tipo)
	{
		switch(tipo)
		{
		case ED:
			return Gruppi.ED_;
		case CL:
			return Gruppi.SBAP_;
		case ETI:
			return Gruppi.ETI_;
		case REG:
			return Gruppi.REG_;
		case SBAP:
			return Gruppi.SBAP_;
		default:
			return null;
		}
	}
}
