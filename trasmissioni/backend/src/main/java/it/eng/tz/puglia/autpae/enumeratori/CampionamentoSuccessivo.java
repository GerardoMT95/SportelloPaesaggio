package it.eng.tz.puglia.autpae.enumeratori;

public enum CampionamentoSuccessivo
{
	PREDEFINITO ("Il campionamento verrà effettuato a partire dalla data di ultimo campionamento effettuato"), 
	TRASMISSIONE("Il campionamento verrà effettuato a partire dalla data di trasmissione della prima pratica rispetto all''ultimo campionamento");
	
	private String value;
	
	private CampionamentoSuccessivo(String text) {
		this.value = text;
	}
	
	public String getTextValue() {
		return value;
	}
}
