/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.exception;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;

/**
 * @author Adriano Colaianni
 * @date 28 apr 2021
 */
public class CorrispondenzaException extends BaseMigrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param infoPratica
	 * @param message
	 * @param cause
	 */
	public CorrispondenzaException(InfoAutPaesPptrAlfa infoPratica, String message, Throwable cause) {
		super(infoPratica, message, cause);
		// TODO Auto-generated constructor stub
	}
	public CorrispondenzaException(InfoAutPaesAlfaBean infoPratica, String message, Throwable cause) {
		super(infoPratica, message, cause);
		// TODO Auto-generated constructor stub
	}

}
