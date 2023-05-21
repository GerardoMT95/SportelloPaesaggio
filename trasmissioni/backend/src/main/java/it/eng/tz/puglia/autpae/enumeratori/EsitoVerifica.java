package it.eng.tz.puglia.autpae.enumeratori;

public enum EsitoVerifica {
	
	NOT_SAMPLED("Non campionata"),			   // da quando viene trasmessa in poi							, se il campionamento non è attivo (al momento della trasmissione)
	SAMPLING_PENDING("In attesa di selezione"),// da quando viene trasmessa fino al giorno del campionamento, se il campionamento     è attivo (al momento della trasmissione)
	NOT_SELECTED("Non selezionata"),		   // dal giorno del campionamento in poi							 , se non è stata selezionata (per la verifica)
	CHECK_IN_PROGRESS("Verifica in corso"),	   // dal giorno del campionamento fino al giorno dell'esito verifica, se 	  è stata selezionata (per la verifica)
	POSITIVE("Verifica Positiva"),			   // trasmessa --> campionamento attivo --> selezionata --> esito verifica --> Positivo
    NEGATIVE("Verifica negativa"),			   // trasmessa --> campionamento attivo --> selezionata --> esito verifica --> Negativo
    ON_MODIFY("In modifica"),
    CANCELLED("Annullata");
	
	
	private String value;
	
	private EsitoVerifica(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
}