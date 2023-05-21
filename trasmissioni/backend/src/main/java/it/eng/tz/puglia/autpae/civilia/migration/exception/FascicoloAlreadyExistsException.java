/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.exception;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class FascicoloAlreadyExistsException extends BaseMigrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param infoPratica
	 * @param message
	 * @param cause
	 */
	public FascicoloAlreadyExistsException(InfoAutPaesPptrAlfa infoPratica, String message, Throwable cause) {
		super(infoPratica, message, cause);
	}
	
	public FascicoloAlreadyExistsException(InfoAutPaesAlfaBean infoPratica, String message, Throwable cause) {
		super(infoPratica, message, cause);
	}

}
