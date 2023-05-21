/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.exception;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;

/**
 * @author Adriano Colaianni
 * @date 20 lug 2021
 */
public class FileSizeToLargeException extends BaseMigrationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param infoPratica
	 * @param message
	 * @param cause
	 */
	public FileSizeToLargeException(InfoAutPaesAlfaBean infoPratica, String message, Throwable cause) {
		super(infoPratica, message, cause);
	}
	

}
