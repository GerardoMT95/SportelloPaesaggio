/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.exception;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;

/**
 * eccezione base, dovrebbe essere raccolta e loggata nel job di migrazione, ha i dati relativi alla pratica i-esima
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class BaseMigrationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InfoAutPaesPptrAlfa infoPratica;
	private InfoAutPaesAlfaBean puttPratica;
	
	
	public InfoAutPaesPptrAlfa getInfoPratica() {
		return infoPratica;
	}
	public void setInfoPratica(InfoAutPaesPptrAlfa infoPratica) {
		
		this.infoPratica = infoPratica;
	}
	
	public InfoAutPaesAlfaBean getPuttPratica()
	{
		return puttPratica;
	}
	public void setPuttPratica(InfoAutPaesAlfaBean puttPratica)
	{
		this.puttPratica = puttPratica;
	}


	public BaseMigrationException(InfoAutPaesPptrAlfa infoPratica,String message,Throwable cause) {
		super(message,cause);
	};
	
	public BaseMigrationException(InfoAutPaesAlfaBean infoPratica,String message,Throwable cause) {
		super(message,cause);
	};
 
}
