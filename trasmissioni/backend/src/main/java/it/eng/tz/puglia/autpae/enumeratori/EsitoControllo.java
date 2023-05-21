/**
 * 
 */
package it.eng.tz.puglia.autpae.enumeratori;

import java.util.Arrays;

/**
 * Esito controllo utilizzato nel pannello annotazioni interne, utilizzato per
 *  migrare le vecchie PptrSoprintendenzaNoteSt su cui avevamo alcuni esiti o note
 *  (0=Non controllato 1=positivo 2=negativo 3=non completato)
 * @author Raffaele Del Basso, Marta Zecchillo
 * @date 07 set 2021
 */
public enum EsitoControllo {
	NON_CONTROLLATO("Non controllato",0),
	POSITIVO("Positivo",1),
	NEGATIVO("Negativo",2),
	NON_COMPLETATO("Non completato",3);
	
	private String esito;
	private int civiliaCode;

	private EsitoControllo(String string,int civiliaCode) {
		this.esito = string;
		this.civiliaCode = civiliaCode;
	}
	
	public String getTextEsito() {
		return esito;
	}
	
	public int getCiviliaCode() {
		return civiliaCode;
	}
	
	/**
	 * dal codice civilia (0=Non controllato 1=positivo 2=negativo 3=non completato) restituisce enum corrispondente 
	 * @autor Adriano Colaianni
	 * @date 8 set 2021
	 * @param code
	 * @return
	 */
	public static EsitoControllo fromCiviliaCode(int code) {
		return Arrays.asList(EsitoControllo.values()).stream().filter(esito->esito.getCiviliaCode()==code).findAny().orElseGet(()->null);
	}
	
//	public static void main(String[] args) {
//		System.out.println("esito di 3:"+fromCiviliaCode(3));
//	}
	
}
