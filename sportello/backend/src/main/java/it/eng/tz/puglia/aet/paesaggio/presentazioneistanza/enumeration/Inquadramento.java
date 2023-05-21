package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum Inquadramento
{
	RESIDENZIALE("residenziale"),
	RICETTIVA_TURISTICA("ricettiva/turistica"),
	INDUSTRIALE_ARTIGIANALE("industriale/artigianale"),
	AGRICOLO("agricolo"),
	COMMERCIALE_DIREZIONALE("commerciale direzionale"),
	CENTRO_E_NUCLEO_STORICO("centro storico"),
	AREA_URBANA("area urbana"),
	AREA_PERIURBANA("area periurbana"),
	AREA_AGRICOLA("area agricola"),
	INSEDIAMENTO_RURALE("insediamento rurale"),
	AREA_NATURALE("area naturale"),
	AREA_BOSCHIVA("area boschiva"),
	AMBITO_FLUVIALE("ambito fluviale"),
	AMBITO_LACUSTRE("ambito lacustre"),
	COSTA("costa (bassa/alta)"),
	CRINALE("crinale (collinare/montano)"),
	PIANURA("pianura"),
	VERSANTE("versante"),
	ALTOPIANO_PROMONTORIO("altopiano/promontorio"),
	PIANA_VALLIVA("piana valliva (montana/collinare)"),
	ALTRO("altro");
	
	private String valCivilia;
	
	private Inquadramento(String valCivilia) {
		this.valCivilia=valCivilia;
	}
	
	public String getValCivilia() {
		return this.valCivilia; 
	}
	
	/**
	 * restituisce il nostro valore a partire dal vecchio civilia, null se no match
	 * @author acolaianni
	 *
	 * @param value
	 * @return
	 */
	public static Inquadramento fromCivilia(String value) {
		Inquadramento ret=null;
		for(Inquadramento enumItem:Inquadramento.values()) {
			if(enumItem.getValCivilia().equalsIgnoreCase(value)) {
				ret=enumItem;
				break;
			}
		}
		return ret;
	}
	
}