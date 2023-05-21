package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum TipoAllegatoSeduta
{
	VERBALE(900),
	SCHEDA_TECNICA(901),
	ALTRO(902);
	
	private Integer value;
	
	TipoAllegatoSeduta(Integer value)
	{
		this.value = value;
	}	
	public Integer getValue()
	{
		return value;
	}
	public static TipoAllegatoSeduta fromValue(Integer value)
	{
		switch(value)
		{
		case 900: return VERBALE;
		case 901: return SCHEDA_TECNICA;
		case 902: return ALTRO;
		default : return null;
		}
	}

}
