package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

public enum EsitoParere {
	AUTORIZZATO("Autorizzato"), NON_AUTORIZZATO("Non autorizzato"), AUT_CON_PRESCRIZ("Autorizzato con prescrizioni");

	String value;

	EsitoParere(String value) {
		this.value = value;
	}

	public String getTextValue() {
		return value;
	}

	public static EsitoParere fromCiviliaValue(String flagEsito) {
		EsitoParere ret = null;
		switch (flagEsito) {
		case "A":
			ret = EsitoParere.AUTORIZZATO;
			break;
		case "N":
			ret = EsitoParere.NON_AUTORIZZATO;
			break;
		case "P":
			ret = EsitoParere.AUT_CON_PRESCRIZ;
			break;
		default:
			break;
		}
		return ret;
	}
}
